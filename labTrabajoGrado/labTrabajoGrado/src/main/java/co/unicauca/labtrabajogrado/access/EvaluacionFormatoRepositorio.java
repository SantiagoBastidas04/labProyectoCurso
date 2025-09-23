/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.domain.enumEstadoProyecto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author edwin
 */
public class EvaluacionFormatoRepositorio implements IEvaluacionFormatoRepositorio{

  private final Connection conn;

    public EvaluacionFormatoRepositorio(Connection conn) {
        this.conn = conn;
        initDatabase();
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS EvaluacionFormato (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "codigoFormato INTEGER NOT NULL, " +
                "intento INTEGER NOT NULL, " +
                "estado TEXT NOT NULL, " +
                "observaciones TEXT, " +
                "fechaEvaluacion TEXT NOT NULL, " +
                "FOREIGN KEY(codigoFormato) REFERENCES FormatoA(id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException ex) {
        Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public boolean guardarEvaluacion(EvaluacionFormato evaluacion) {
       String sql = "INSERT INTO EvaluacionFormato(codigoFormato, intento, estado, observaciones, fechaEvaluacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, evaluacion.getCodigoFormato());
            pstmt.setInt(2, evaluacion.getIntento());
            pstmt.setString(3, evaluacion.getEstado().name()); // guardamos el enum como String
            pstmt.setString(4, evaluacion.getObservaciones());
            pstmt.setString(5, evaluacion.getFechaEvaluacion().toString());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<EvaluacionFormato> listarFormato(Long codigoFormato) {
      List<EvaluacionFormato> lista = new ArrayList<>();
        String sql = "SELECT * FROM EvaluacionFormato WHERE codigoFormato = ? ORDER BY intento ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, codigoFormato);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToEvaluacion(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista; 
    }

    @Override
    public EvaluacionFormato obtenerUltimaEvaluacion(Long codigoFormato) {
        String sql = "SELECT * FROM EvaluacionFormato WHERE codigoFormato = ? ORDER BY intento DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, codigoFormato);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEvaluacion(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     private EvaluacionFormato mapRowToEvaluacion(ResultSet rs) throws SQLException {
        return new EvaluacionFormato(
                rs.getLong("codigoFormato"),
                rs.getInt("intento"),
                enumEstadoProyecto.valueOf(rs.getString("estado")),
                rs.getString("observaciones"),
                LocalDateTime.parse(rs.getString("fechaEvaluacion"))
        );
    }
    
}

