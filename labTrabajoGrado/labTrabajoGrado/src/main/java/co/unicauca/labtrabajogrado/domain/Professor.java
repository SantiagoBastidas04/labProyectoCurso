/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

/**
 *
 * @author ING-SIS
 */
public class Professor extends Persona{
    
    private User myUser;
    private enumPrograma programa;
    public Professor(String nombre, String apellidos, String celular,enumPrograma programa, String email, String contraseña) {
        super(nombre, apellidos, validacionCelular(celular));
        myUser = new User(email,contraseña);
    }
    
    
}
