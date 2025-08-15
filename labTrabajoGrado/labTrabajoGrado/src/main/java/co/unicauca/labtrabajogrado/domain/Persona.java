/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

/**
 *
 * @author Acer
 */
public abstract class Persona {
    public String nombre;
    public String apellidos;
    public String celular;
    

    public Persona(String nombre, String apellidos, String celular) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
    }

    public Persona(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public static String validacionCelular(String celular){
      return (celular == null && celular.isEmpty()) ? "No tiene" : celular ;
    }

}
