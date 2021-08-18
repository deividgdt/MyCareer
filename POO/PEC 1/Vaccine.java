
/**
 * Super clase Vacuna de la cual heredan: Pfizer, Moderna y Johnson
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class Vaccine
{
    protected Integer numberOfDosis;
    protected Integer daysBetweenDosis;
    protected String fullName;

    /**
     * Constructor para objetos de la clase Vacuna
     */
    public Vaccine()
    {
        numberOfDosis = null;
        daysBetweenDosis = null;
        fullName = null;
    }

    /**
     * Obtiene los dias necesarios que deben pasar entre cada dosis
     * 
     * @return     Integer - daysBetweenDosis
     */
    public Integer getDaysBetweenDosis()
    {      
        return daysBetweenDosis;
    }

    /**
     * Devuelve el numero de dosis restantes de la vacuna
     *
     * @return     Integer - numberOfDosis
     */
    public Integer getNumberOfDosisLeft()
    {       
        return numberOfDosis;
    }

    /**
     * Metodo que usa una dosis de las totales disponibles de la vacuna
     */
    public void useOneDosis()
    {      
        if(numberOfDosis > 0){
            numberOfDosis=numberOfDosis-1;
        }
    }

    /**
     * Devuelve un boolean true o false dependiendo de si quedan dosis disponibles
     * o no en la vacuna
     *
     * @return     boolean - true : getNumberOfDosisLeft() == 0 | false : getNumberOfDosisLeft() > 0
     */
    public boolean isEmpty()
    {      
        return getNumberOfDosisLeft()==0;
    }
}
