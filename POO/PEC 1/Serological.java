/**
 * Clase para la prueba diagnostica tipo Serologica
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class Serological extends Test
{
    private Integer serologicalTestResult;
    private int MINIUMTOBEPOSITIVE;

    /**
     * Constructor para objetos de la clase Serological
     *
     * Primeramente se llama al constructor de la clase padre y a continuacion se aniaden los
     * valores unicos de este tipo de prueba como
     *
     * Valor minimo para que laprueba sea considerada positiva: 2
     * Dias que deben pasar entre cada prueba: 183
     *
     * En este constructor se asgina por defecto el valor 0 al resultado de la prueba
     */
    public Serological()
    {
        super();
        name = DiagnosticTestType.SEROLOGICAL.toString();
        reference = DiagnosticTestType.SEROLOGICAL.getDiagnosticTestRef();
        daysBetweenDoses = 183;
        MINIUMTOBEPOSITIVE = 2;

        this.serologicalTestResult = 0;
    }

    /**
     * En este constructor, la diferencia es que el valor de la prueba es
     * pasado por argumento
     *
     * @param serologicalTestResult  Resultado de la prueba serologica
     */
    public Serological(Integer serologicalTestResult)
    {
        super();
        name = DiagnosticTestType.SEROLOGICAL.toString();
        reference = DiagnosticTestType.SEROLOGICAL.getDiagnosticTestRef();
        daysBetweenDoses = 183;
        MINIUMTOBEPOSITIVE = 2;

        this.serologicalTestResult = serologicalTestResult;
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si la prueba diagnostica
     * es positiva  o no
     *
     * @return boolean true == positiva || false == negativa
     */
    public boolean isPositive()
    {
        if(serologicalTestResult > MINIUMTOBEPOSITIVE){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo que devulve el valor de la prueba serologica
     *
     * @return int El valor de la prueba serologica
     */
    public int getSerologicalResult()
    {
        return serologicalTestResult;
    }

    /**
     * Metodo para establecer el resultado de la prueba serologica
     *
     * @param  serologicalTestResult El valor de la prueba serologica
     */
    public void setSerologicalResult(Integer serologicalTestResult)
    {
        this.serologicalTestResult = serologicalTestResult;
    }


}
