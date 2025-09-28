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
public class EvaluacionFormatoRepositorio implements IEvaluacionFormatoRepositorio {

    private final Connection conn;

    public EvaluacionFormatoRepositorio(Connection conn) {
        this.conn = conn;
        initDatabase();
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS EvaluacionFormato ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "codigoFormato INTEGER NOT NULL, "
                + "intento INTEGER NOT NULL, "
                + "estado TEXT NOT NULL, "
                + "observaciones TEXT, "
                + "fechaEvaluacion TEXT NOT NULL, "
                + "FOREIGN KEY(codigoFormato) REFERENCES FormatoA(id)"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean guardarEvaluacion(EvaluacionFormato evaluacion) {
        String sql = "INSERT INTO EvaluacionFormato(codigoFormato, intento, estado, observaciones, fechaEvaluacion) "
                + "VALUES (?, ?, ?, ?, ?)";

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
    public boolean actualizarEvaluacion(EvaluacionFormato evaluacion) {
        String sql = "UPDATE EvaluacionFormato SET estado = ?, observaciones = ?, fechaEvaluacion = ? "
                + "WHERE codigoFormato = ? AND intento = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, evaluacion.getEstado().name());
            pstmt.setString(2, evaluacion.getObservaciones());
            pstmt.setString(3, evaluacion.getFechaEvaluacion().toString());
            pstmt.setLong(4, evaluacion.getCodigoFormato());
            pstmt.setInt(5, evaluacion.getIntento());

            return pstmt.executeUpdate() > 0;
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
        EvaluacionFormato eval = new EvaluacionFormato();
        eval.setCodigoFormato(rs.getLong("codigoFormato"));

        String estado = rs.getString("estado");
        if (estado == null || estado.trim().isEmpty()) {
            eval.setEstado(enumEstadoProyecto.Pendiente);
        } else {
            eval.setEstado(enumEstadoProyecto.valueOf(estado));
        }

        String obs = rs.getString("observaciones");
        if (obs == null || obs.trim().isEmpty()) {
            eval.setObservaciones("En revisión");
        } else {
            eval.setObservaciones(obs);
        }

        eval.setIntento(rs.getInt("intento"));

        // ✅ Leer la fecha como String
        String fechaStr = rs.getString("fechaEvaluacion");
        if (fechaStr != null && !fechaStr.isBlank()) {
            try {
                eval.setFechaEvaluacion(LocalDateTime.parse(fechaStr));
            } catch (Exception e) {
                System.err.println("⚠️ Error parseando fecha: " + fechaStr + " -> " + e.getMessage());
                eval.setFechaEvaluacion(null);
            }
        } else {
            eval.setFechaEvaluacion(null);
        }

        // Estas columnas no siempre existen, valida antes de leer
        try {
            eval.setTituloProyecto(rs.getString("tituloProyecto"));
        } catch (SQLException ignore) {
        }
        try {
            eval.setModalidad(rs.getString("modalidad"));
        } catch (SQLException ignore) {
        }

        return eval;
    }

    @Override
    public EvaluacionFormato infoFormatoEnlazado(String email) {
        String sql = "SELECT fa.tituloProyecto, ef.codigoFormato, ef.intento, ef.estado, ef.observaciones, ef.fechaEvaluacion "
                + "FROM EvaluacionFormato ef "
                + "JOIN FormatoA fa ON ef.codigoFormato = fa.id "
                + "WHERE fa.emailEstudiante = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
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

    @Override
    public List<EvaluacionFormato> listarFormatosRechazados(String email) {
        List<EvaluacionFormato> lista = new ArrayList<>();
        String sql = "SELECT ef.codigoFormato, ef.intento, ef.estado, ef.observaciones, ef.fechaEvaluacion, "
                + "f.tituloProyecto, f.modalidad "
                + "FROM FormatoA f "
                + "JOIN EvaluacionFormato ef ON f.id = ef.codigoFormato "
                + "WHERE f.director = ? AND ef.estado = 'RECHAZADO'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionFormato evaluacion = new EvaluacionFormato(
                            rs.getLong("codigoFormato"),
                            rs.getInt("intento"),
                            enumEstadoProyecto.valueOf(rs.getString("estado")),
                            rs.getString("observaciones"),
                            LocalDateTime.parse(rs.getString("fechaEvaluacion")),
                            rs.getString("tituloProyecto"),
                            rs.getString("modalidad")
                    );
                    lista.add(evaluacion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public void actualizarFormatoPdf(Long idFormato, String rutaPdf) throws SQLException {

        String sqlFormato = "UPDATE FormatoA SET formatoPdf = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlFormato)) {
            stmt.setString(1, rutaPdf);
            stmt.setLong(2, idFormato);
            stmt.executeUpdate();
        }

        String sqlSelect = "SELECT MAX(intento) AS intento FROM EvaluacionFormato WHERE codigoFormato = ?";
        int intentosActuales = 0;
        try (PreparedStatement stmt = conn.prepareStatement(sqlSelect)) {
            stmt.setLong(1, idFormato);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    intentosActuales = rs.getInt("intento");
                }
            }
        }

        if (intentosActuales >= 3) {
            String sqlDelete = "DELETE FROM EvaluacionFormato WHERE codigoFormato = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {
                stmt.setLong(1, idFormato);
                stmt.executeUpdate();
            }
            String sqlDeleteFormato = "DELETE FROM FormatoA WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteFormato)) {
                stmt.setLong(1, idFormato);
                stmt.executeUpdate();
            }
            System.out.println(" SE SUPERÓ EL LÍMITE DE INTENTOS para formato " + idFormato);
        } else {
            // 4️⃣ Si aún no supera → actualizar estado e incrementar intento
            String sqlEval = "UPDATE EvaluacionFormato "
                    + "SET estado = 'Pendiente', intento = intento + 1, fechaEvaluacion = ? "
                    + "WHERE codigoFormato = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlEval)) {
                stmt2.setString(1, java.time.LocalDateTime.now().toString());
                stmt2.setLong(2, idFormato);
                stmt2.executeUpdate();
            }
        }
    }

    @Override
    public List<EvaluacionFormato> listarPorEmail(String emailProfesor) {
        List<EvaluacionFormato> lista = new ArrayList<>();
        String sql = "SELECT f.id AS codigoFormato, ef.intento, ef.estado, ef.observaciones, ef.fechaEvaluacion, "
                + "f.tituloProyecto, f.modalidad, f.emailEstudiante "
                + "FROM FormatoA f "
                + "LEFT JOIN EvaluacionFormato ef ON f.id = ef.codigoFormato "
                + "WHERE f.director = ? "
                + "ORDER BY ef.intento DESC";

        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailProfesor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionFormato evaluacion = new EvaluacionFormato(
                            rs.getLong("codigoFormato"),
                            rs.getInt("intento"),
                            enumEstadoProyecto.valueOf(rs.getString("estado")),
                            rs.getString("observaciones"),
                            LocalDateTime.parse(rs.getString("fechaEvaluacion")),
                            rs.getString("tituloProyecto"),
                            rs.getString("modalidad")
                    );

                    lista.add(evaluacion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<EvaluacionFormato> listarPorCodigoFormato(Long idFormato) {
        List<EvaluacionFormato> lista = new ArrayList<>();

        String sql = "SELECT ef.codigoFormato, ef.intento, ef.estado, ef.observaciones, ef.fechaEvaluacion, "
                + "f.tituloProyecto, f.modalidad "
                + "FROM EvaluacionFormato ef "
                + "LEFT JOIN FormatoA f ON ef.codigoFormato = f.id "
                + "WHERE ef.codigoFormato = ? "
                + "ORDER BY ef.intento ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idFormato);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionFormato eval = mapRowToEvaluacion(rs);
                    lista.add(eval);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionFormatoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
}
