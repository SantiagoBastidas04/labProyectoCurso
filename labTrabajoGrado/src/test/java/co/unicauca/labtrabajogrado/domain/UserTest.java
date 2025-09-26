/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 *
 * @author edwin
 */
public class UserTest {
    
    @Test
    public void testConstructorConParametros() {
        User user = new User("Santiago", "Pérez", "3245567546", enumPrograma.INGENIERIA_SISTEMAS, enumRol.Estudiante, "Santiago@unicauca.edu.co", "Clave123!");

        assertEquals("Santiago@unicauca.edu.co", user.getEmail());
        assertEquals("Clave123!", user.getContraseña());
        assertEquals(enumRol.Estudiante, user.getRol());
        assertEquals(enumPrograma.INGENIERIA_SISTEMAS, user.getPrograma());
    }

    @Test
    public void testGettersYSetters() {
        User user = new User();
        user.setEmail("ana@unicauca.edu.co");
        user.setContraseña("Pass@123");
        user.setRol(enumRol.Profesor);
        user.setPrograma(enumPrograma.AUTOMATICA_INDUSTRIAL);

        assertEquals("ana@unicauca.edu.co", user.getEmail());
        assertEquals("Pass@123", user.getContraseña());
        assertEquals(enumRol.Profesor, user.getRol());
        assertEquals(enumPrograma.AUTOMATICA_INDUSTRIAL, user.getPrograma());
    }
    
    @Test
    public void testToString() {
        User user = new User("pablo@unicauca.edu.co", "Pa123#", enumRol.Profesor, enumPrograma.TECNOLOGIA_TELEMATICA);
        String texto = user.toString();

        assertTrue(texto.contains("pablo@unicauca.edu.co"));
        assertTrue(texto.contains("Profesor"));
        assertTrue(texto.contains("TECNOLOGIA_TELEMATICA"));
    }

    
    
}
