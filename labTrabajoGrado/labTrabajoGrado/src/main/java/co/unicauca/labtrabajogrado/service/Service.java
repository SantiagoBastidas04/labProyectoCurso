/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;
import co.unicauca.labtrabajogrado.access.IFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.IUserRepositorio;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.domain.User;
import co.unicauca.labtrabajogrado.utility.*;
/**
 *
* @author edwin_
 */
public class Service {
    private IUserRepositorio repositorio;
    private IFormatoRepositorio formatoRepositorio;

    public Service() {
    }

    public Service(IUserRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public Service(IFormatoRepositorio repositorio) {
        this.formatoRepositorio = repositorio;
    }
    
    public boolean registrarUsuario(User newUser){
        
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
            System.out.println("No se puede registrar");
            return false;
            
        }
        // Validar correo institucional
        if (!EmailValidator.esCorreoInstitucional(newUser.getEmail())) {
      
        return false;
        }
        // Validar contraseña
        
         if(!PasswordUtils.validarContrasenia(newUser.getContraseña())) {
        
        return false;
        }
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(newUser.getContraseña());
        newUser.setContraseña(contraseniaCifrada);
        repositorio.guardar(newUser);
        return true;
    }

    public User iniciarSesion(String email, String contrasenia){
        
         if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        // Cifrar la contraseña ingresada antes de enviar al repositorio
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(contrasenia);

        return repositorio.iniciarSesion(email, contraseniaCifrada);
    }
    
    public boolean registrarFormato(FormatoA nuevoFormato) {

        if (nuevoFormato == null) {
            System.out.println("No se puede registrar: FormatoA es null");
            return false;
        }

        if (nuevoFormato.getTituloProyecto() == null || nuevoFormato.getTituloProyecto().isBlank() ||
            nuevoFormato.getModalidad() == null || nuevoFormato.getModalidad().isBlank() ||
            nuevoFormato.getDirector() == null || nuevoFormato.getDirector().isBlank() ||
            nuevoFormato.getEmailEstudiante() == null || nuevoFormato.getEmailEstudiante().isBlank() ||
            nuevoFormato.getFechaActual() == null) {
            
            System.out.println("No se puede registrar: faltan campos obligatorios");
            return false;
        }

        if (!EmailValidator.esCorreoInstitucional(nuevoFormato.getEmailEstudiante())) {
            System.out.println("Correo no es institucional");
            return false;
        }

        boolean guardado = formatoRepositorio.GuardarFormato(nuevoFormato);
        return guardado;
    }

}
