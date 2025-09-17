/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.service;

import co.unicauca.labtrabajogrado.access.IFormatoRepositorio;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.utility.EmailValidator;
import java.util.List;

/**
 *
 * @author edwin
 */
public class serviceFormatoA {
     private IFormatoRepositorio formatoRepositorio;
     
      public serviceFormatoA(IFormatoRepositorio repositorio) {
        this.formatoRepositorio = repositorio;
    }
     public serviceFormatoA(){
         
     }
    public boolean registrarFormato(FormatoA nuevoFormato) {

        if (nuevoFormato == null) {
            System.out.println("No se puede registrar: FormatoA es null");
            return false;
        }

        if (nuevoFormato.getTituloProyecto() == null || nuevoFormato.getTituloProyecto().isBlank() ||
            nuevoFormato.getModalidad() == null || nuevoFormato.getModalidad().isBlank() ||
            nuevoFormato.getDirector() == null || nuevoFormato.getDirector().isBlank() ||
            nuevoFormato.getEmailEstudiante() == null || nuevoFormato.getEmailEstudiante().isBlank() ||
            nuevoFormato.getFechaActual() == null) {
            
            System.out.println("No se puede registrar: faltan campos obligatorios");
            return false;
        }

        if (!EmailValidator.esCorreoInstitucional(nuevoFormato.getEmailEstudiante())) {
            System.out.println("Correo no es institucional");
            return false;
        }

        boolean guardado = formatoRepositorio.GuardarFormato(nuevoFormato);
        return guardado;
    }
    public List<FormatoA> listarFormatos() {
    return formatoRepositorio.listarTodos(); 
    }
    public List<FormatoA> listarPorEmail(String email){
        return formatoRepositorio.listarPorEmail(email);
    }
}
