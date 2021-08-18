/**
 * Clase para el tipo de prueba diagnostica antigeno rapido
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class QuickAntigen extends Antigen
{
    
    /**
     * Constructor para objetos de la clase QuickAntigen
     */
    public QuickAntigen()
    {
        name = DiagnosticTestType.QUICKANTIGEN.toString();
        reference = DiagnosticTestType.QUICKANTIGEN.getDiagnosticTestRef();
    }

}
