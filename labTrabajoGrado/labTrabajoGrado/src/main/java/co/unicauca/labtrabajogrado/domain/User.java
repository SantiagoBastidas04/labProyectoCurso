/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

/**
 *
 * @author ING-SIS
 */
public class User extends Persona{
    
    private String email;
    private String contraseña;
    private enumRol rol;
    private enumPrograma programa;

    public User(String nombre, String apellidos, String celular, enumPrograma programa,enumRol rol,String email,String contraseña){
        super(nombre,apellidos,validacionCelular(celular));
        this.email = email; 
        this.contraseña = contraseña;
        this.rol = rol;
         this.programa = programa;
        
    }
    public User(String email, String contraseña,enumRol rol, enumPrograma programa) {
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
        this.programa = programa;

    }

    public User() {
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public enumRol getRol() {
        return rol;
    }
    public void setRol(enumRol rol) {
        this.rol = rol;
    }
    public enumPrograma getPrograma(){
        return programa;
    }
    public void setPrograma(enumPrograma programa) {
        this.programa = programa;
    }
    
}
