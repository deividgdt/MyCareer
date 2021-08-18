/**
 * Clase para el tipo de prueba antigeno clasico o tipo PCR
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class PCRAntigen extends Antigen
{
    /**
     * Constructor para objetos de PCRAntigen
     */
    public PCRAntigen()
    {
        name = DiagnosticTestType.PCRANTIGEN.toString();
        reference = DiagnosticTestType.PCRANTIGEN.getDiagnosticTestRef();
    }
}
