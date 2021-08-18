
/**
 * Clase Johnson que hereda de la clase Vaccine.
 * 
 * Esta clase es de un tipo de vacuna Johnson la cual solo tiene una dosis.
 * 
 * @author @Deividgdt
 * @version 0.8
 */
public class Johnson extends Vaccine
{
    /**
    * Constructor para objetos de la clase Johnson
    */
    public Johnson()
    {
        super();
        numberOfDosis = 1;
        fullName = "Johnson & Johnson";
    }
}
