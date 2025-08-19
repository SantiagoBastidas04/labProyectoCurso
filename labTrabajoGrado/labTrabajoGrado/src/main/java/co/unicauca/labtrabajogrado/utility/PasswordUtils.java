package co.unicauca.labtrabajogrado.utility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class PasswordUtils {

    // Expresión regular para validar contraseña
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!¿?.,;:_-])(?=\\S+$).{6,}$";

    public static boolean validarContrasenia(String contrasenia) {
        return Pattern.matches(PASSWORD_PATTERN, contrasenia);
    }

    public static String cifrarSHA256(String contrasenia) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(contrasenia.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Devuelve la contraseña cifrada en hex
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cifrar contraseña", e);
        }
    }
}
