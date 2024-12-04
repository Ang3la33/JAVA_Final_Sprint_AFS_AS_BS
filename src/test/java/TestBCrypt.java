import de.svws_nrw.ext.jbcrypt.BCrypt;

// Test for BCrypt functionality
public class TestBCrypt {
    public static void main(String[] args) {
        String password = "password123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Hashed password: " + hashedPassword);

        // Verify password
        if (BCrypt.checkpw(password, hashedPassword)) {
            System.out.println("Password verification successful!");
        }
        else {
            System.out.println("Password verification failed.");
        }

    }
}
