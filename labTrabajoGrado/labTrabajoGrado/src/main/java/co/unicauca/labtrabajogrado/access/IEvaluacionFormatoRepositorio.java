/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author edwin
 */
public interface IEvaluacionFormatoRepositorio {
  public boolean guardarEvaluacion(EvaluacionFormato evaluacion);
  public List<EvaluacionFormato> listarFormato(Long codigoFormato);
  public EvaluacionFormato obtenerUltimaEvaluacion(Long codigoFormato);
  public EvaluacionFormato infoFormatoEnlazado(String email);
  public List<EvaluacionFormato> listarFormatosRechazados(String email);
  public void actualizarFormatoPdf(Long id, String rutaPdf) throws SQLException;
  public List<EvaluacionFormato> listarPorEmail(String emailProfesor);
  public List<EvaluacionFormato> listarPorCodigoFormato(Long idFormato);
  public boolean actualizarEvaluacion(EvaluacionFormato evaluacion);
}
