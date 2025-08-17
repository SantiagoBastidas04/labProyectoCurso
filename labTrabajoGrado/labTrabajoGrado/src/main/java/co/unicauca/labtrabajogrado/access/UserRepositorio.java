/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.User;
import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private Connection conn;
    
    public UserRepositorio(){
        initDatabase();
    }
    
    @Override
    public boolean guardar(User newUser){
        try {
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
            return false;
        }
        String sql = "INSERT INTO Usuario ( email, contrasenia)"
                + "VALUES( ?, ?)";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newUser.getEmail());
        pstmt.setString(2, newUser.getContraseña());
        return true;
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName());
        }
        return false;
    }
    public User iniciarSesion(String email,String contrasenia){
        try{
         if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        String sql = "SELECT email, contrasenia FROM Usuario WHERE email = ? AND contrasenia = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, contrasenia); 
        
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User usuario = new User();
            usuario.setEmail(rs.getString("email"));
            usuario.setContraseña(rs.getString("contrasenia")); 
            return usuario;
        }
    } catch (SQLException ex) {
         Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
     private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "	UserId integer PRIMARY KEY,\n"
                + "	email text NOT NULL,\n"
                + "	contrasenia text NOT NULL\n"
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connect() {

        // SQLite connection string
        //String url = "jdbc:sqlite:./mydatabase.db";
        String url = "jdbc:sqlite::memory:";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
