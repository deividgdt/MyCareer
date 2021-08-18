
/**
 * Clase Moderna que hereda de la clase Vaccine.
 * 
 * Esta clase es de un tipo de vacuna: Moderna, la cual tiene dos dosis.
 * Entre cada dosis deben haber pasado 21 dias
 * 
 * @author @Deividgdt
 * @version 0.9
 */
public class Moderna extends Vaccine
{
    /**
     * Constructor para objetos de la clase Moderna
     */
    public Moderna()
    {
        super();
        daysBetweenDosis = 21;
        numberOfDosis = 2;
        fullName = "Moderna";
    }
}
