/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.User;
import co.unicauca.labtrabajogrado.domain.enumRol;
import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ING-SIS
 */
public class UserRepositorio implements IUserRepositorio{
    private final Connection conn;
    
    public UserRepositorio(Connection conn){
        this.conn = conn;
        initDatabase();
    }
    
    @Override
    public boolean guardar(User newUser){
        try {
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
            return false;
        }
        String sql = "INSERT INTO Usuario (nombres, apellidos, celular, programa, rol, email, contrasenia) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newUser.getNombre());
            pstmt.setString(2, newUser.getApellidos());
            pstmt.setString(3, newUser.getCelular());
            pstmt.setString(4, newUser.getPrograma().name());
            pstmt.setString(5, newUser.getRol().name());
            pstmt.setString(6, newUser.getEmail());
            pstmt.setString(7, newUser.getContraseña());
            pstmt.executeUpdate();
        return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Service.class.getName());
        }
        return false;
    }
    @Override
    public User iniciarSesion(String email,String contrasenia){
        try{
         if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        String sql = "SELECT email, contrasenia , rol , nombres FROM Usuario WHERE email = ? AND contrasenia = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, contrasenia); 
        
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User usuario = new User();
            usuario.setEmail(rs.getString("email"));
            usuario.setContraseña(rs.getString("contrasenia"));
            usuario.setNombre(rs.getString("nombres"));
            String rolStr = rs.getString("rol"); 
            if (rolStr != null) {
                usuario.setRol(enumRol.valueOf(rolStr));
            }
            
            
            return usuario;
        }
    } catch (SQLException ex) {
         Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
     private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nombres TEXT NOT NULL," +
                     "apellidos TEXT NOT NULL," +
                     "celular TEXT," +
                     "programa TEXT NOT NULL," +
                     "rol TEXT NOT NULL," +
                     "email TEXT NOT NULL UNIQUE," +
                     "contrasenia TEXT NOT NULL" +
                     ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
