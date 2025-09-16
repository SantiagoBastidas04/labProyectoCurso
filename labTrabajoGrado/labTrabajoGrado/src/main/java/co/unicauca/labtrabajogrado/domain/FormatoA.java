/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Acer
 */
public class FormatoA {
    private Long id;                          // Identificador único
    private String tituloProyecto;            // Título del proyecto de grado
    private String modalidad;                 // "INVESTIGACION" o "PRACTICA_PROFESIONAL"
    private LocalDate fechaActual;            // Fecha de registro/subida
    private String director;                  // Nombre del director del proyecto
    private String codirector;                // Nombre del codirector (opcional, puede ser null)
    private String objetivoGeneral;           // Objetivo general del proyecto
    private String objetivosEspecificos;      // Objetivos específicos (puede ser un texto largo)
    private String formatoPdf;                // Ruta o nombre del archivo PDF subido
    private String rutaCartaAceptacion;       // Ruta o nombre del archivo de la carta de aceptación (solo si modalidad == PRACTICA_PROFESIONAL)
    private String emailEstudiante;              // ID del docente que sube el formato (relación con usuario/docente)
    

    public FormatoA(Long id, String tituloProyecto, String modalidad, LocalDate fechaActual, String director, String codirector, String objetivoGeneral, String objetivosEspecificos, String formatoPdf, String rutaCartaAceptacion,String emailEstudiante) {
        this.id = id;
        this.tituloProyecto = tituloProyecto;
        this.modalidad = modalidad;
        this.fechaActual = fechaActual;
        this.director = director;
        this.codirector = codirector;
        this.objetivoGeneral = objetivoGeneral;
        this.objetivosEspecificos = objetivosEspecificos;
        this.formatoPdf = formatoPdf;
        this.rutaCartaAceptacion = rutaCartaAceptacion;
        this.emailEstudiante = emailEstudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public LocalDate getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(LocalDate fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCodirector() {
        return codirector;
    }

    public void setCodirector(String codirector) {
        this.codirector = codirector;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getObjetivosEspecificos() {
        return objetivosEspecificos;
    }

    public void setObjetivosEspecificos(String objetivosEspecificos) {
        this.objetivosEspecificos = objetivosEspecificos;
    }

    public String getFormatoPdf() {
        return formatoPdf;
    }

    public void setFormatoPdf(String formatoPdf) {
        this.formatoPdf = formatoPdf;
    }

    public String getRutaCartaAceptacion() {
        return rutaCartaAceptacion;
    }

    public void setRutaCartaAceptacion(String rutaCartaAceptacion) {
        this.rutaCartaAceptacion = rutaCartaAceptacion;
    }


    public String getEmailEstudiante() {
        return emailEstudiante;
    }

    public void setEmailEstudiante(String emailEstudiante) {
        this.emailEstudiante = emailEstudiante;
    }
    
    
 
    
}
