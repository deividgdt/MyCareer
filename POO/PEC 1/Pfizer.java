
/**
 * Clase Pfizer que hereda de la clase Vaccine.
 * 
 * Esta clase es de un tipo de vacuna: Pfizer, la cual tiene dos dosis.
 * Entre cada dosis deben haber pasado 21 dias
 * 
 * @author @Deividgdt
 * @version 0.8
 */
public class Pfizer extends Vaccine
{
    /**
     * Constructor para objetos de la clase Pfizer
     */
    public Pfizer()
    {
        super();
        daysBetweenDosis = 21;
        numberOfDosis = 2;
        fullName = "Pfizer";
    }
}
