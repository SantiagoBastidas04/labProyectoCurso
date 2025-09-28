/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.domain;

import java.time.LocalDate;

/**
 *
 * @author Acer
 */
public class FormatoA {
    private Long id;                          
    private String tituloProyecto;           
    private String modalidad;                 
    private LocalDate fechaActual;            
    private String director;                 
    private String codirector;               
    private String objetivoGeneral;           
    private String objetivosEspecificos;      
    private String formatoPdf;               
    private String rutaCartaAceptacion;       
    private String emailEstudiante; 
    private String estado;


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
    
     public String getEstado(){
        return estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
}
