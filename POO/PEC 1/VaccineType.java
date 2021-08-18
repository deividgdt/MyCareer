
/**
 * Clase de tipo enumerado para los tipos de vacuna
 * 
 * @author @Deividgdt
 * @version 0.8
 */
public enum VaccineType 
{
    // Los diferentes tipos de vacunas
    JOHNSON("JOHNSON", 0), 

    PFIZER("PFIZER", 1), 

    MODERNA("MODERNA", 2),
    
    UNKOWN("NO ASIGNADA", -1);
    
    private String vaccineString;
    private int vaccineRef;

    /**
     * Inicializamos los tipos enumerados pasando los parametros
     * @param vaccineString The vaccine string.
     * @param vaccineRef vaccine reference.
     */
    VaccineType(String vaccineString, int vaccineRef)
    {
        this.vaccineString = vaccineString;
        this.vaccineRef = vaccineRef;
    }

    /**
     * Devuelve la referencia de la vacuna
     *
     * @return int vaccineRef Referencia de la vacuna
     */
    public int getVaccineRef()
    {
        // put your code here
        return vaccineRef;
    }

    
    /**
     * Devuelve el nombre de la vacuna
     * 
     * @return String vaccineString Nombre de la vacuna
     */
    public String toString()
    {
        return vaccineString;
    }
}
