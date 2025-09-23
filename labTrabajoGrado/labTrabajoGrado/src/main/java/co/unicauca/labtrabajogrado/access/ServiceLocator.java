/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;
import java.sql.Connection;

public class ServiceLocator {
    // Mantiene las instancias (singletons)
   private static ServiceLocator instance;
    private static IUserRepositorio userRepository;
    private static IFormatoRepositorio formatoRepository;
    private static IEvaluacionFormatoRepositorio evaluacionRepository;
    
    private ServiceLocator(){
        init();
    }
    public static ServiceLocator getInstance(){
        if(instance == null){
            instance = new ServiceLocator();
        }
        return instance;
    }
    // Método de inicialización (reemplaza al Factory)
    private static void init() {
        Connection connection = ConnectionManager.getConnection();
        userRepository = new UserRepositorio(connection);
        formatoRepository = new FormatoRepositorio(connection);
        evaluacionRepository = new EvaluacionFormatoRepositorio(connection);
    }
    
    public IUserRepositorio getUserRepository() {
        return userRepository; // Siempre la misma instancia
    }
    
    public IFormatoRepositorio getFormatoRepository() {
        return formatoRepository; // Siempre la misma instancia
    }
    
    public IEvaluacionFormatoRepositorio getEvaluacionRepository(){
        return evaluacionRepository;
    }
}
