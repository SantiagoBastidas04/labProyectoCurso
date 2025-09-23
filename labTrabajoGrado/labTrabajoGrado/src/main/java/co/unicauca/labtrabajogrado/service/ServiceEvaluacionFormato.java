/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;
import co.unicauca.labtrabajogrado.access.IEvaluacionFormatoRepositorio;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.domain.enumEstadoProyecto;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ServiceEvaluacionFormato {
    private final IEvaluacionFormatoRepositorio repositorio;

    public ServiceEvaluacionFormato(IEvaluacionFormatoRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
     public boolean registrarEvaluacion(Long codigoFormato, enumEstadoProyecto estado, String observaciones) {
        List<EvaluacionFormato> historial = repositorio.listarFormato(codigoFormato);
        int intento = historial.size() + 1;

        if (intento > 3 && estado == enumEstadoProyecto.RECHAZADO) {
            // Proyecto rechazado definitivamente
            return false;
        }
         EvaluacionFormato evaluacion = new EvaluacionFormato(
                codigoFormato, intento, estado, observaciones, LocalDateTime.now()
        );
        return repositorio.guardarEvaluacion(evaluacion);
     }
     
      public List<EvaluacionFormato> obtenerHistorial(Long codigoFormato) {
        return repositorio.listarFormato(codigoFormato);
    }

    public EvaluacionFormato obtenerUltima(Long codigoFormato) {
        return repositorio.obtenerUltimaEvaluacion(codigoFormato);
    }
    
    public boolean evaluarFormato(FormatoA formato, enumEstadoProyecto estado, String observaciones) {
        if (formato == null || estado == null) {
            throw new IllegalArgumentException("Formato y estado no pueden ser nulos");
        }

        // Obtener el último intento
        EvaluacionFormato ultima = repositorio.obtenerUltimaEvaluacion(formato.getId());
        int intento = (ultima != null) ? ultima.getIntento() + 1 : 1;

        EvaluacionFormato nuevaEvaluacion = new EvaluacionFormato(
                formato.getId(),
                intento,
                estado,
                observaciones != null ? observaciones : "",
                LocalDateTime.now()
        );

        return repositorio.guardarEvaluacion(nuevaEvaluacion);
    }
}
