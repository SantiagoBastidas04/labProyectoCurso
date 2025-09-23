/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import java.util.List;
/**
 *
 * @author edwin
 */
public interface IEvaluacionFormatoRepositorio {
  public boolean guardarEvaluacion(EvaluacionFormato evaluacion);
  public List<EvaluacionFormato> listarFormato(Long codigoFormato);
  public EvaluacionFormato obtenerUltimaEvaluacion(Long codigoFormato);
  
}
