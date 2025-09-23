/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

import java.time.LocalDateTime;

/**
 *
 * @author edwin
 */
public class EvaluacionFormato {
      private int idEvaluacion;
    private Long codigoFormato;
    private int intento;
    private enumEstadoProyecto estado;  // Enum: EN_REVISION, APROBADO, RECHAZADO
    private String observaciones;
    private LocalDateTime fechaEvaluacion;

    public EvaluacionFormato(Long codigoFormato, int intento, enumEstadoProyecto estado,
                             String observaciones, LocalDateTime fechaEvaluacion) {
        this.codigoFormato = codigoFormato;
        this.intento = intento;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getCodigoFormato() {
        return codigoFormato;
    }

    public void setCodigoFormato(Long codigoFormato) {
        this.codigoFormato = codigoFormato;
    }

    public int getIntento() {
        return intento;
    }

    public void setIntento(int intento) {
        this.intento = intento;
    }

    public enumEstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(enumEstadoProyecto estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }
    
}
