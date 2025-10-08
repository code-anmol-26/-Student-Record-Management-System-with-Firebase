package firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * DBConnection holds Firebase base URL and helper to read from config.properties.
 */
public class DBConnection {
    private static String firebaseUrl;

    static {
        // Load from resources/config.properties if present
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
            firebaseUrl = props.getProperty("firebase.url");
        } catch (IOException e) {
            // fallback - user must edit config.properties or set url later
            firebaseUrl = "";
        }
        if (firebaseUrl == null) firebaseUrl = "";
        // ensure trailing slash
        if (!firebaseUrl.endsWith("/")) firebaseUrl = firebaseUrl + "/";
    }

    public static String getFirebaseUrl() {
        return firebaseUrl;
    }

    public static void setFirebaseUrl(String url) {
        if (url == null) url = "";
        if (!url.endsWith("/")) url = url + "/";
        firebaseUrl = url;
    }
}