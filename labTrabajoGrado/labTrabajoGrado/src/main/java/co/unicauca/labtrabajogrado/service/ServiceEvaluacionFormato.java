/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;

import co.unicauca.labtrabajogrado.access.IEvaluacionFormatoRepositorio;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.domain.enumEstadoProyecto;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public EvaluacionFormato obtenerInfoFiltrada(String email) {
        return repositorio.infoFormatoEnlazado(email);
    }

    
    public boolean evaluarFormato(FormatoA formato, enumEstadoProyecto estado, String observaciones) {
        if (formato == null || estado == null) {
            throw new IllegalArgumentException("Formato y estado no pueden ser nulos");
        }

       
        EvaluacionFormato ultimaPendiente = repositorio.obtenerUltimaEvaluacion(formato.getId());

        if (ultimaPendiente != null) {
            
            ultimaPendiente.setEstado(estado);
            ultimaPendiente.setObservaciones(observaciones);
            ultimaPendiente.setFechaEvaluacion(LocalDateTime.now());

            return repositorio.actualizarEvaluacion(ultimaPendiente);

        } else {
            
            EvaluacionFormato nuevaEvaluacion = new EvaluacionFormato(
                    formato.getId(),
                    1,
                    estado,
                    observaciones != null ? observaciones : "",
                    LocalDateTime.now()
            );
            return repositorio.guardarEvaluacion(nuevaEvaluacion);
        }
    }
  

    public boolean evaluarFormato(EvaluacionFormato evaluacion) {
        return repositorio.guardarEvaluacion(evaluacion);
    }

    public EvaluacionFormato obtenerUltimaEvaluacion(Long codigoFormato) {
        if (repositorio == null) {
            throw new IllegalStateException("Repositorio de evaluación no inicializado");
        }
        return repositorio.obtenerUltimaEvaluacion(codigoFormato);
    }

    public List<EvaluacionFormato> listarFormatosRechazados(String email) {
        return repositorio.listarFormatosRechazados(email);
    }

    public List<EvaluacionFormato> listarPorEmail(String emailProfesor) {
        return repositorio.listarPorEmail(emailProfesor);
    }

    public List<EvaluacionFormato> listarPorCodigoFormato(Long idFormato) {
        return repositorio.listarPorCodigoFormato(idFormato);
    }

    public void actualizarFormatoPdf(Long id, String absolutePath) {
        try {
            // Llama al repositorio para actualizar el PDF
            repositorio.actualizarFormatoPdf(id, absolutePath);
        } catch (SQLException ex) {
            Logger.getLogger(serviceFormatoA.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
}
