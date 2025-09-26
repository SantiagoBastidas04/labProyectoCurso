package co.unicauca.labtrabajogrado.utility;
import java.util.regex.Pattern;

public class EmailValidator {

    // Cambia el dominio según tu institución
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@unicauca\\.edu\\.co$";

    public static boolean esCorreoInstitucional(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}