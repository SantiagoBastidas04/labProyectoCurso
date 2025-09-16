/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;



import co.unicauca.labtrabajogrado.domain.User;
import co.unicauca.labtrabajogrado.domain.enumPrograma;
import co.unicauca.labtrabajogrado.domain.enumRol;
import java.sql.Connection;
import org.junit.jupiter.api.*;



import static org.junit.jupiter.api.Assertions.*;

public class UserRepositorioTest {

    private UserRepositorio repo;
    private ConnectionManager conn;

    @BeforeEach
    public void setUp() throws Exception {
        // Usar BD en memoria para los tests
        ConnectionManager cn = new  ConnectionManager();
        repo = new UserRepositorio((Connection) conn){
            public void connect() {
                try {
                    conn = (ConnectionManager) java.sql.DriverManager.getConnection("jdbc:sqlite::memory:");
                } catch (Exception e) {
                    fail("Error conectando a la BD en memoria: " + e.getMessage());
                }
            }
        };
        cn.getConnection();

        // Inicializar esquema en memoria
        var initMethod = UserRepositorio.class.getDeclaredMethod("initDatabase");
        initMethod.setAccessible(true);
        initMethod.invoke(repo);
    }

    @AfterEach
    public void tearDown() {
        
    }

    /*
    * Inserta un usuario válido y confirma que se guardó.
    */
    @Test
    public void testGuardarUsuarioValido() {
        User user = new User("Carlos", "Gómez", "3242232456",
                enumPrograma.INGENIERIA_SISTEMAS, enumRol.Estudiante,
                "carlos@unicauca.edu.co", "Pas!123");

        boolean result = repo.guardar(user);

        assertTrue(result, "El usuario debería guardarse correctamente");
    }

    /*
    * Intenta guardar usuario vacío → debe fallar.
    */
    @Test
    public void testGuardarUsuarioInvalido() {
        User user = new User();
        user.setEmail(""); // inválido
        user.setContraseña(""); // inválido

        boolean result = repo.guardar(user);

        assertFalse(result, "El usuario inválido no debe guardarse");
    }
    /*
    *Guarda un usuario y luego hace login correcto.
    */
    @Test
    public void testIniciarSesionExitoso() {
        // Primero guardar usuario válido
        User user = new User("victor", "alvarez", "3145679890",
                enumPrograma.INGENIERIA_ELECTRONICA_TELECOMUNICACIONES, enumRol.Profesor,
                "victor@unicauca.edu.co", "Vic!123");
        repo.guardar(user);

        // Intentar login
        User result = repo.iniciarSesion("victor@unicauca.edu.co", "Vic!123");

        assertNotNull(result, "El login debería retornar un usuario válido");
        assertEquals("victor@unicauca.edu.co", result.getEmail());
        assertEquals("Vic!123", result.getContraseña());
        assertEquals(enumRol.Profesor, result.getRol());
    }
    /*
    Login con credenciales inexistentes → debe devolver null.
    */
    @Test
    public void testIniciarSesionFallido() {
        // Intentar login sin guardar usuario
        User result = repo.iniciarSesion("noexiste@unicauca.edu.co", "12345");

        assertNull(result, "El login debería fallar si no existe el usuario");
    }
    /*
    Login con parámetros inválidos → null.
    */
    @Test
    public void testIniciarSesionParametrosInvalidos() {
        User result = repo.iniciarSesion("", "");
        assertNull(result, "Debe retornar null si parámetros son inválidos");

        result = repo.iniciarSesion(null, null);
        assertNull(result, "Debe retornar null si parámetros son null");
    }
}
