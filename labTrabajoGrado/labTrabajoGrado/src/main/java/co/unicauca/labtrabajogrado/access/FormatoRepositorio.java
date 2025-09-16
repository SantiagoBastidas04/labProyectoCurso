/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.FormatoA;


import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class FormatoRepositorio implements IFormatoRepositorio{
    Connection conn;
    
    public FormatoRepositorio(){
        connect();        
        initDatabase();
    }
    
   
    private void initDatabase() {
    String sql = "CREATE TABLE IF NOT EXISTS FormatoA (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tituloProyecto TEXT NOT NULL, " +
            "modalidad TEXT NOT NULL, " +
            "fechaActual DATE NOT NULL, " +  // LocalDate guardado como texto ISO
            "director TEXT NOT NULL, " +
            "codirector TEXT, " +
            "objetivoGeneral TEXT, " +
            "objetivosEspecificos TEXT, " +
            "formatoPdf TEXT, " +
            "rutaCartaAceptacion TEXT, " +
            "emailEstudiante TEXT NOT NULL, " +
            "fechaCreacion TEXT NOT NULL" +  // LocalDateTime guardado como texto ISO
            ");";
    try (Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException ex) {
        Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void connect() {

        // SQLite connection string
        //String url = "jdbc:sqlite:./mydatabase.db";
        String url = "jdbc:sqlite:database.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(Provider.Service.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public boolean GuardarFormato(FormatoA formato) {
    if (formato == null) {
        return false;
    }

    // Validar campos obligatorios
    if (formato.getTituloProyecto() == null || formato.getTituloProyecto().isBlank()
            || formato.getModalidad() == null || formato.getModalidad().isBlank()
            || formato.getDirector() == null || formato.getDirector().isBlank()
            || formato.getEmailEstudiante() == null || formato.getEmailEstudiante().isBlank()
            || formato.getFechaActual() == null
            ) {
        System.out.println("Campos obligatorios faltantes");
        return false;
    }

    String sql = "INSERT INTO FormatoA(" +
            "tituloProyecto, modalidad, fechaActual, director, codirector, " +
            "objetivoGeneral, objetivosEspecificos, formatoPdf, rutaCartaAceptacion, " +
            "emailEstudiante) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (var pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, formato.getTituloProyecto());
        pstmt.setString(2, formato.getModalidad());
        pstmt.setString(3, formato.getFechaActual().toString()); 
        pstmt.setString(4, formato.getDirector());
        pstmt.setString(5, formato.getCodirector());
        pstmt.setString(6, formato.getObjetivoGeneral());
        pstmt.setString(7, formato.getObjetivosEspecificos());
        pstmt.setString(8, formato.getFormatoPdf());
        pstmt.setString(9, formato.getRutaCartaAceptacion());
        pstmt.setString(10, formato.getEmailEstudiante());
        
        pstmt.executeUpdate();
        return true;

    } catch (SQLException ex) {
        Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}
}
