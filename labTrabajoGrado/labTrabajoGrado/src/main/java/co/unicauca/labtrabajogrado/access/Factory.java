/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.labtrabajogrado.access;

/**
 *
 * @author ING-SIS
 */
public class Factory {
    /**
     * Fabrica que se encarga de instanciar userRepositorio o cualquier otro que
     * se cree en el futuro.
    */
    
    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    /**
     * Método que crea una instancia concreta de la jerarquia IUserRepositorio
     *
     * @param type cadena que indica qué tipo de clase hija debe instanciar
     * @return una clase hija de la abstracción IProductRepository
     */
    public IUserRepositorio getRepository(String type) {

        IUserRepositorio result = null;

        switch (type) {
            case "default":
                result = new UserRepositorio();
                break;
        }
        return result;

    }
}
