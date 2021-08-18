import java.util.Random;
/**
 * Clase para el tipo de prueba PCR
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class PCR extends Test
{
        
    /**
     * Constructor para objetos de la clase PCR
     *
     * En este constructor se inicializan los campos con los valores unicos de
     * las pruebas tipo PCR
     */
    public PCR()
    {
        super();    
        daysBetweenDoses = 15;
        name = DiagnosticTestType.PCR.toString();
        reference = DiagnosticTestType.PCR.getDiagnosticTestRef();
    }

    /**
     * Metodo que devulve  un valor boolean dependiendo de si la prueba es
     * positiva o no
     *
     * @return boolean true == prueba positiva || false == prueba negativa
     */
    public boolean isPositive()
    {
        return result;
    }
}
