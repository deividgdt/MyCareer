/**
 * Clase para los enfermeros/as
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class Nurse extends Employee
{
    /**
     * Constructor para objetos de la clase Nurse
     * maxTestsPerWeek = Asignamos un maximo de 5 tests para realizar por semana 
     * availableTestsPerWeek = Asignamos el valor de 5 test disponibles 
     * 
     * @param name Nombre del tecnico
     * @param age Edad del tecnico
     * @param DNI DNI del tecnico
     * @param employeeNumber Numero de empleado del tecnico
     */
    public Nurse(String name, int age, String DNI, int employeeNumber)
    {
        super(name, age, DNI, employeeNumber);
        maxTestsPerWeek = 5;
        availableTestsPerWeek = 5;
    }
}
