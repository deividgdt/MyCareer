
/**
 * Clase para el tipo de usuario tecnico de laboratorio
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class LabTech extends Employee
{
    
    /**
     * Constructor para objetos de la clase
     * Se asignan 4 pruebas diagnosticas maximas por semana
     * 
     * @param name Nombre del tecnico
     * @param age Edad del tecnico
     * @param DNI DNI del tecnico
     * @param employeeNumber Numero de empleado del tecnico
     */
    public LabTech(String name, int age, String DNI, int employeeNumber)
    {
        super(name, age, DNI, employeeNumber);
        maxTestsPerWeek = 4;
        availableTestsPerWeek = 4;
    }
}
