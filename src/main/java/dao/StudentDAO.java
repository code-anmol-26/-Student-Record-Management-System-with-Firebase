package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Student;
import firebase.DBConnection;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * StudentDAO implements CRUD operations against Firebase Realtime Database using REST API.
 */
public class StudentDAO {
    private final Gson gson = new Gson();

    // Adds a student: POST to /students.json -> Firebase returns a name (key)
    public String addStudent(Student s) throws IOException {
        String url = DBConnection.getFirebaseUrl() + "students.json";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(gson.toJson(s), StandardCharsets.UTF_8));
            post.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse resp = client.execute(post)) {
                String body = EntityUtils.toString(resp.getEntity());
                // Firebase returns {"name":"-Mx..."}
                Map<String, String> map = gson.fromJson(body, Map.class);
                return map.get("name");
            }
        }
    }

    // Get all students: GET /students.json
    public List<Student> getAllStudents() throws IOException {
        String url = DBConnection.getFirebaseUrl() + "students.json";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse resp = client.execute(get)) {
                String body = EntityUtils.toString(resp.getEntity());
                if (body == null || body.trim().equals("null")) return Collections.emptyList();
                Type mapType = new TypeToken<Map<String, Student>>() {}.getType();
                Map<String, Student> map = gson.fromJson(body, mapType);
                List<Student> students = new ArrayList<>();
                for (Map.Entry<String, Student> e : map.entrySet()) {
                    Student s = e.getValue();
                    s.setId(e.getKey());
                    students.add(s);
                }
                return students;
            }
        }
    }

    // Get single student by id: GET /students/{id}.json
    public Student getStudentById(String id) throws IOException {
        String url = DBConnection.getFirebaseUrl() + "students/" + id + ".json";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse resp = client.execute(get)) {
                String body = EntityUtils.toString(resp.getEntity());
                if (body == null || body.trim().equals("null")) return null;
                Student s = gson.fromJson(body, Student.class);
                s.setId(id);
                return s;
            }
        }
    }

    // Update student by id: PUT /students/{id}.json
    public boolean updateStudent(String id, Student s) throws IOException {
        String url = DBConnection.getFirebaseUrl() + "students/" + id + ".json";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut put = new HttpPut(url);
            put.setEntity(new StringEntity(gson.toJson(s), StandardCharsets.UTF_8));
            put.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse resp = client.execute(put)) {
                String body = EntityUtils.toString(resp.getEntity());
                return body != null && !body.trim().equals("null");
            }
        }
    }

    // Delete student by id: DELETE /students/{id}.json
    public boolean deleteStudent(String id) throws IOException {
        String url = DBConnection.getFirebaseUrl() + "students/" + id + ".json";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete del = new HttpDelete(url);
            try (CloseableHttpResponse resp = client.execute(del)) {
                String body = EntityUtils.toString(resp.getEntity());
                // Firebase returns null after delete
                return body == null || body.trim().equals("null");
            }
        }
    }

    // Search by name (case-insensitive substring) - client-side since REST doesn't support complex queries without index
    public List<Student> searchByName(String name) throws IOException {
        List<Student> all = getAllStudents();
        List<Student> out = new ArrayList<>();
        String q = name == null ? "" : name.toLowerCase();
        for (Student s : all) {
            if (s.getName() != null && s.getName().toLowerCase().contains(q)) out.add(s);
        }
        return out;
    }
}