
/**
 * Clase de tipo enumero para los tipos de pruebas diagnosticas
 * 
 * @author @Deividgdt
 * @version 0.8
 */
public enum DiagnosticTestType 
{
    SEROLOGICAL("SEROLOGICO", 0), 

    PCR("PCR", 1), 

    QUICKANTIGEN("ANTIGINO RAPIDO", 2),
    
    PCRANTIGEN("ANTIGENO CLASICO", 3),
    
    UNKNOWN("DESCONOCIDO", -1);

    private String diagnosticTypeString;
    private int diagnosticTypeRef;

    /**
     * Inicializamos los tipos enumerado pasando los parametros
     * @param diagnosticTypeString El nombre de la prueba diagnostica en String
     * @param diagnosticTypeRef La referencia unica de la prueba diagnostica
     */
    DiagnosticTestType(String diagnosticTypeString, int diagnosticTypeRef)
    {
        this.diagnosticTypeString = diagnosticTypeString;
        this.diagnosticTypeRef = diagnosticTypeRef;
    }

    /**
     * Metodo para obtener la referencia la prueba diagnostica
     */
    public int getDiagnosticTestRef()
    {
        return diagnosticTypeRef;
    }

    
    /**
     * Metodo para obtener el nombre de la prueba diagnotica en String
     *
     * @return String El nombre de la prueba diagnostica
     */
    public String toString()
    {
        return diagnosticTypeString;
    }
}
