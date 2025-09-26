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
     * @return una clase hija de la abstracción IUserRepository
     */
    public static IUserRepositorio getUserRepository(String type) {

        IUserRepositorio result = null;
        switch (type.toLowerCase()) {
            case "profesor":
                result = new UserRepositorio(ConnectionManager.getConnection());
                break;
            case "estudiante":
                result = new UserRepositorio(ConnectionManager.getConnection());
                break;
        }
        return result;

    }
    
    public static IFormatoRepositorio getFormatoRepository(String type) {
        IFormatoRepositorio result = null;
        switch (type.toLowerCase()) {
            case "default":
                result = new FormatoRepositorio(ConnectionManager.getConnection());
                break;
        }
        return result;
    }
    
}
