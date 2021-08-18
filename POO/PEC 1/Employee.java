/**
 * Clase empleado
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class Employee extends User
{
    private int employeeNumber;
    protected Integer availableTestsPerWeek;
    protected Integer maxTestsPerWeek;

    /**
     * Constructor para objetos de la clase Employee 
     * 
     * En este constructor inicializamos las variables que almacenan los tests maximos por semana
     * y los tests disponibles de cada empleados a null, ya que posteriormente las clases que 
     * hereden de este asignaran los valores unicos de cada
     * 
     * @param name Nombre del tecnico
     * @param age Edad del tecnico
     * @param DNI DNI del tecnico
     * @param employeeNumber Numero de empleado del tecnico
     */
    public Employee(String name, int age, String DNI, int employeeNumber)
    {
        super(name, age, DNI);
        this.employeeNumber = employeeNumber;
        maxTestsPerWeek = null;
        availableTestsPerWeek = null;
    }

    /**
     * Metodo encargado de asignar el numero de empleado
     *
     * @param number El numero de empleado a asignar
     */
    public void setEmployeeNumber(int number)
    {
        this.employeeNumber = number;
    }

    /**
     * Metodo encargado de devolver el numero del empleado
     */
    public int getEmployeeNumber()
    {
        return this.employeeNumber;
    }

    /**
     * Metodo encargado de devolver el numero de test disponibles por semana
     *
     * @return int Test disponibles por semana
     */
    public int getAvailableTestsPerWeek()
    {
        return availableTestsPerWeek;
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si el empleado tiene
     * test disponibles para realizar o no
     * 
     * @return boolean true == availableTestsPerWeek>0 || false availableTestsPerWeek<=0
     */
    public boolean isAvailableToMakeTest()
    {
        if(availableTestsPerWeek>0){
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de reducir la cantidad de Test disponibles por semana del empleado en 1 unidad
     */
    public void makeATest()
    {
        if(isAvailableToMakeTest()){
            availableTestsPerWeek=availableTestsPerWeek-1;
        }
    }

    /**
     * Metodo encargado de reestablecer el numero de tests disponibles por semana al maximo permitido
     */
    public void resetTestsPerWeek()
    {
        availableTestsPerWeek = maxTestsPerWeek;
    }
}
