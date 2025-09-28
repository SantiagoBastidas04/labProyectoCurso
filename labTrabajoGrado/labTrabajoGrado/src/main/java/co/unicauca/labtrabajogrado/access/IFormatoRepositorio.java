/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

import co.unicauca.labtrabajogrado.domain.FormatoA;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IFormatoRepositorio {
    boolean GuardarFormato(FormatoA formato);
    public List<FormatoA> listarTodos();
    public FormatoA buscarPorId(Long id);
    public List<FormatoA> listarPorEmail(String emailProfesor);
    public List<FormatoA> listarPendientes();
    public List<FormatoA> listarPorEmailEstudiante(String emailEstudiante);
}
