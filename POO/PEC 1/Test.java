/**
 * Super clase abstracta para las pruebas diagnosticas
 *
 * aqui se definen los campos y metodos genericos para las pruebas
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public abstract class Test
{
    protected boolean result;
    protected String name;
    protected Integer reference;
    protected Integer daysBetweenDoses;

    /**
     * Constructor para objetos de tipo Test
     */
    public Test()
    {
        result = false;
        name = null;
        reference = null;
        daysBetweenDoses = null;
    }

    /**
     * Metodo para obtener el numero de dias que debe pasar entre cada dosis
     *
     * @return int daysBetweenDoses Dias entre cada dosis
     */
    public int getDaysBetweenDoses(){
        return daysBetweenDoses;
    }

    /**
     * Metodo para obtener el nombre de la prueba diagnostica
     *
     * @return String name Nombre de la prueba diagnostica
     */
    public String getTestName()
    {
        return name;
    }

    /**
     * Metodo para establecer el valor del resultado de la prueba diagnostica
     *
     * @param  result El resultado de la prueba true || false
     */
    public void setResult(boolean result)
    {
        // put your code here
        this.result = result;
    }


    /**
     * Metodo que devulve un valor boolean dependiendo de si la prueba diagnostica
     * ha sido positiva o negativa
     *
     * @return boolean true == prueba positiva || false == prueba negativa
     */
    public abstract boolean isPositive();

}
