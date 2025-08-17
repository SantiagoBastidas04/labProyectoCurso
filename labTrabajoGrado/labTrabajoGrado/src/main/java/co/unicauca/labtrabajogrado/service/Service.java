/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;
import co.unicauca.labtrabajogrado.access;
import co.unicauca.labtrabajogrado.access.IUserRepositorio;
import co.unicauca.labtrabajogrado.domain.User;
/**
 *
 * @author edwin
 */
public class Service {
    private IUserRepositorio repositorio;

    public Service(IUserRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public boolean registrarUsuario(Usuario newUser){
     
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
            return false;
        }
        repositorio.guardar(new User);
        return true;
    }

    public User iniciarSesion(String email, String contrasenia){
        /
         if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        return repositorio.iniciarSesion(email, contrasenia);


    }

    
    
}
