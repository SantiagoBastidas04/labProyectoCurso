/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;
import co.unicauca.labtrabajogrado.access.IUserRepositorio;
import co.unicauca.labtrabajogrado.access.UserRepositorio;
import co.unicauca.labtrabajogrado.domain.User;
import co.unicauca.labtrabajogrado.utility.*;
/**
 *
* @author edwin_
 */
public class Service {
    private IUserRepositorio repositorio;

    public Service(IUserRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public boolean registrarUsuario(User newUser){
     
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
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

    
    
}
