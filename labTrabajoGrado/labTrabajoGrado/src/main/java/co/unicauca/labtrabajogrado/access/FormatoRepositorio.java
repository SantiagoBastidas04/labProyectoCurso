/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.FormatoA;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class FormatoRepositorio implements IFormatoRepositorio {

    private final Connection conn;

    public FormatoRepositorio(Connection conn) {
        this.conn = conn;
        initDatabase();
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS FormatoA ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tituloProyecto TEXT NOT NULL, "
                + "modalidad TEXT NOT NULL, "
                + "fechaActual TEXT NOT NULL, "
                + // LocalDate guardado como texto ISO
                "director TEXT NOT NULL, "
                + "codirector TEXT, "
                + "objetivoGeneral TEXT, "
                + "objetivosEspecificos TEXT, "
                + "formatoPdf TEXT, "
                + "rutaCartaAceptacion TEXT, "
                + "emailEstudiante TEXT NOT NULL "
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean GuardarFormato(FormatoA formato) {
        if (formato == null) {
            System.out.println("El formato a guardar no existe o es null");
            return false;
        }

        // Validar campos obligatorios
        if (formato.getTituloProyecto() == null || formato.getTituloProyecto().isBlank()
                || formato.getModalidad() == null || formato.getModalidad().isBlank()
                || formato.getDirector() == null || formato.getDirector().isBlank()
                || formato.getEmailEstudiante() == null || formato.getEmailEstudiante().isBlank()
                || formato.getFechaActual() == null) {
            System.out.println("Campos obligatorios faltantes");
            return false;
        }

        String sql = "INSERT INTO FormatoA("
                + "tituloProyecto, modalidad, fechaActual, director, codirector, "
                + "objetivoGeneral, objetivosEspecificos, formatoPdf, rutaCartaAceptacion, "
                + "emailEstudiante) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( var pstmt = conn.prepareStatement(sql)) {
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

    @Override
    public List<FormatoA> listarTodos() {
        List<FormatoA> lista = new ArrayList<>();
        String sql = "SELECT * FROM FormatoA";

        try ( var stmt = conn.createStatement();  var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FormatoA formato = new FormatoA(
                        rs.getLong("id"),
                        rs.getString("tituloProyecto"),
                        rs.getString("modalidad"),
                        java.time.LocalDate.parse(rs.getString("fechaActual")), // convertir a LocalDate
                        rs.getString("director"),
                        rs.getString("codirector"),
                        rs.getString("objetivoGeneral"),
                        rs.getString("objetivosEspecificos"),
                        rs.getString("formatoPdf"),
                        rs.getString("rutaCartaAceptacion"),
                        rs.getString("emailEstudiante")
                );
                lista.add(formato);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public List<FormatoA> listarPendientes() {
        List<FormatoA> lista = new ArrayList<>();
        String sql = "SELECT f.* "
                + "FROM FormatoA f "
                + "LEFT JOIN ( "
                + "   SELECT codigoFormato, MAX(intento) as maxIntento "
                + "   FROM EvaluacionFormato "
                + "   GROUP BY codigoFormato "
                + ") ult ON f.id = ult.codigoFormato "
                + "LEFT JOIN EvaluacionFormato ef ON f.id = ef.codigoFormato AND ef.intento = ult.maxIntento "
                + "WHERE ef.estado IS NULL OR ef.estado = 'Pendiente'";

        try ( var stmt = conn.createStatement();  var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FormatoA formato = new FormatoA(
                        rs.getLong("id"),
                        rs.getString("tituloProyecto"),
                        rs.getString("modalidad"),
                        java.time.LocalDate.parse(rs.getString("fechaActual")),
                        rs.getString("director"),
                        rs.getString("codirector"),
                        rs.getString("objetivoGeneral"),
                        rs.getString("objetivosEspecificos"),
                        rs.getString("formatoPdf"),
                        rs.getString("rutaCartaAceptacion"),
                        rs.getString("emailEstudiante")
                );
                lista.add(formato);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public FormatoA buscarPorId(Long id) {
        String sql = "SELECT * FROM FormatoA WHERE id = ?";
        try ( var pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try ( var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new FormatoA(
                            rs.getLong("id"),
                            rs.getString("tituloProyecto"),
                            rs.getString("modalidad"),
                            java.time.LocalDate.parse(rs.getString("fechaActual")),
                            rs.getString("director"),
                            rs.getString("codirector"),
                            rs.getString("objetivoGeneral"),
                            rs.getString("objetivosEspecificos"),
                            rs.getString("formatoPdf"),
                            rs.getString("rutaCartaAceptacion"),
                            rs.getString("emailEstudiante")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<FormatoA> listarPorEmail(String emailDirector) {
        List<FormatoA> lista = new ArrayList<>();
        String sql = "SELECT * FROM FormatoA WHERE director = ?";

        try ( var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailDirector);

            try ( var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FormatoA formato = new FormatoA(
                            rs.getLong("id"),
                            rs.getString("tituloProyecto"),
                            rs.getString("modalidad"),
                            java.time.LocalDate.parse(rs.getString("fechaActual")),
                            rs.getString("director"),
                            rs.getString("codirector"),
                            rs.getString("objetivoGeneral"),
                            rs.getString("objetivosEspecificos"),
                            rs.getString("formatoPdf"),
                            rs.getString("rutaCartaAceptacion"),
                            rs.getString("emailEstudiante")
                    );
                    lista.add(formato);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
    @Override
    public List<FormatoA> listarPorEmailEstudiante(String emailEstudiante) {
        List<FormatoA> lista = new ArrayList<>();
        String sql = "SELECT * FROM FormatoA WHERE emailEstudiante = ?";

        try ( var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailEstudiante);

            try ( var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FormatoA formato = new FormatoA(
                            rs.getLong("id"),
                            rs.getString("tituloProyecto"),
                            rs.getString("modalidad"),
                            java.time.LocalDate.parse(rs.getString("fechaActual")),
                            rs.getString("director"),
                            rs.getString("codirector"),
                            rs.getString("objetivoGeneral"),
                            rs.getString("objetivosEspecificos"),
                            rs.getString("formatoPdf"),
                            rs.getString("rutaCartaAceptacion"),
                            rs.getString("emailEstudiante")
                    );
                    lista.add(formato);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
}
