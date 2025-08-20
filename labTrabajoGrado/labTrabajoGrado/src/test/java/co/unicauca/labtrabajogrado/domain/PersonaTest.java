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


public class PersonaTest {

    @Test
   public void testConstructorConCelular() {
        Persona persona = new Persona("Carlos", "Pérez", "3234567654") {};
        assertEquals("Carlos", persona.getNombre());
        assertEquals("Pérez", persona.getApellidos());
        assertEquals("3234567654", persona.getCelular());
    }

    @Test
    public void testConstructorSinCelular() {
        Persona persona = new Persona("Juan", "López") {};
        assertEquals("Juan", persona.getNombre());
        assertEquals("López", persona.getApellidos());
        assertNull(persona.getCelular());
    }

    @Test
    public void testSettersYGetters() {
        Persona persona = new Persona() {};
        persona.setNombre("Cristian");
        persona.setApellidos("Gómez");
        persona.setCelular("3245678908");

        assertEquals("Cristian", persona.getNombre());
        assertEquals("Gómez", persona.getApellidos());
        assertEquals("3245678908", persona.getCelular());
    }

    @Test
    public void testValidacionCelularConValor() {
        String celular = Persona.validacionCelular("3245655908");
        assertEquals("3245655908", celular);
    }

    @Test
    public void testValidacionCelularNull() {
        String celular = Persona.validacionCelular(null);
        assertEquals("No tiene", celular);
    }

    @Test
    public void testValidacionCelularVacio() {
        String celular = Persona.validacionCelular("");
        assertEquals("No tiene", celular);
    }
}
    

