/**
 * Superclase abstracta donde se definen los campos y metodos por defecto
 * de una prueba diagnostica de tipo Antigeno
 * 
 * @author @Deividgdt 
 * @version 0.8
 */ 
public abstract class Antigen extends Test
{
    /**
     * Constructor para objetos de la clase Antigen
     */
    public Antigen()
    {
        super();
        daysBetweenDoses = 0;
    }
    
    /**
     * Metodo que devuelve un valor boolean dependiendo de si la prueba es positiva o no.
     *
     * @return boolean true == prueba positva || false == prueba negativa
     */
    public boolean isPositive()
    {
        return result;
    }
}
