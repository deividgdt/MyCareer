/**
 * Clase paciente
 * 
 * @author @Deividgdt
 * @version 1.1
 */
public class Patient extends User
{
    private final int PRIORITYPATIENT;

    /**
     * Constructor de la clase paciente
     */
    public Patient(String name, int age, String DNI)
    {
        super(name, age, DNI);
        PRIORITYPATIENT = 65;
    }

    /**
     * Metodo que devuelve un boolean si el paciente es un paciente prioratario
     * esto es si el paciente es mayor de 65 años
     *
     * @return boolean true == age >= 65 || false == age < 65
     */
    public boolean isAPriorityPatient()
    {
        return getAge()>=PRIORITYPATIENT;
    }

}
