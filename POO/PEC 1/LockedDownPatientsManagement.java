import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

/**
 * Clase que gestiona los pacientes confinados
 * 
 * Estos pacientes son almacenados en una estructura tipo HashMap donde la clave es el DNI
 * y el valor es un objeto tipo LockedDownPatient el cual es una clase especial para los pacientes
 * confinados
 * 
 * @author @Deividgdt
 * @version 1.0
 */
public class LockedDownPatientsManagement
{
    private Map<String, LockedDownPatient> LockedDownPatientsDB;

    /**
     * Constructor para objetos de las clase LockedDownPatientsManagement
     */
    public LockedDownPatientsManagement()
    {
        LockedDownPatientsDB = new HashMap<>();
    }

    /**
     * Metodo encargado de confinar a un paciente, como parametro se le pasa el paciente y
     * la fecha en la que el paciente va a ser confinado
     * 
     * @param patientToLockedDown El paciente a confinar
     * @param lockedDownSince Fecha desde la que el paciente va a quedar registrado como confinado
     */
    public void setLockedDown(Patient patientToLockedDown, LocalDate lockedDownSince)
    {
        LockedDownPatient newLockedDownPatient = new LockedDownPatient(patientToLockedDown, lockedDownSince);
        LockedDownPatientsDB.put(patientToLockedDown.getDNI(), newLockedDownPatient);
    }

    /**
     * Metodo encargado de retirar el confinamiento a un paciente
     * 
     * Basicamente el el objeto tipo LockedDownPatient es eliminado de la lista de pacientes
     * confinados. El paciente es buscado por su DNI
     *
     * @param DNI El DNI del paciente al que se le retirara el confinamiento
     */
    public void unsetLockedDown(String DNI)
    {
        LockedDownPatientsDB.remove(DNI);
    }

    /**
     * Metodo encargado de devolver una ficha de prueba diagnostica para un Test Serologico
     * 
     * En este metodo comprobamos si la fecha actual es superior a la fecha hasta la cual el paciente esta confinado
     * Si es correcto, se le progama una cita 7 dias despues de la fecha actual
     * 
     * Un objeto tipo DiagnosticTest es devuelto con el paciente asignad, el test serologico asignado y la fecha para la prueba
     * 
     * @param DNI El DNI del paciente
     * @param patientAssigned El paciente al cual se le programará la cita
     */
    public DiagnosticTest scheduleSerologicalTest(String DNI, Patient patientAssigned)
    {
        LocalDate dateTilPatientIsLockedDown = LockedDownPatientsDB.get(DNI).getLockedDownTil();
        
        if(LocalDate.now().isAfter(dateTilPatientIsLockedDown)){
            LocalDate dateToSerologicalTest = LocalDate.now().plusDays(7);
            Serological newSerologicalTest = new Serological();
            DiagnosticTest newDiagnosticTest = new DiagnosticTest(patientAssigned, newSerologicalTest, dateToSerologicalTest);
            return newDiagnosticTest;
        }
        
        return null;
    }

}
