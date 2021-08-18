import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase para la gestión de la vacunacion
 * 
 * Esta clase crea dos estructuras de tipo HashMap donde se almacenan:
 *    1. Los pacientes prioritarios: firstPriorityGroup
 *    2. Los pacientes NO prioritarios: secondPriorityGroup
 *    
 * Como en el resto de clases, la clave es el DNI y en este caso el valor es un objeto del tipo VaccinationRegister
 * donde esta toda la informacion relacionada con la vacunacion del paciente
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class VaccinationManagement
{
    private Map<String, VaccinationRegister> firstPriorityGroup;
    private Map<String, VaccinationRegister> secondPriorityGroup;

    /**
     * Constructor para objetos de la clase VaccinationManagement
     */
    public VaccinationManagement()
    {
        firstPriorityGroup = new HashMap<>();
        secondPriorityGroup = new HashMap<>();
    }

    /**
     * Metodo encargado de programar una vacunacion, para poder realizar la cita se debe cumplir
     * que la cita sea 21 dias superior a la fecha de la primera vacuna.
     * 
     * Dependiendo de si el paciente es o no prioritario se añadira al HashMap firstPriorityGroup o secondPriorityGroup
     * 
     * @param patientToBeVaccinated El paciente a ser vacunado
     * @param AppointmentDate La fecha de la cita
     * @return boolean true == la creacion de la cita ha sido correcta || false == no se ha creado la cita
     */
    public boolean scheduleVaccination(Patient patientToBeVaccinated, LocalDate AppointmentDate)
    {
        if(patientIsScheduledToBeVaccinated(patientToBeVaccinated)){
            if(!secondDosesIsAfter21days(patientToBeVaccinated, AppointmentDate)){
                System.out.println("   +++ ATENCION: La segunda dosis no puede ser antes de 21 días.");
                return false;
            }else{
                scheduleSecondVaccination(patientToBeVaccinated, AppointmentDate);
                return true;
            }
        }else{
            VaccinationRegister newVaccinationRegister = new VaccinationRegister(patientToBeVaccinated, AppointmentDate);
            if(patientToBeVaccinated.isAPriorityPatient()){
                firstPriorityGroup.put(patientToBeVaccinated.getDNI(), newVaccinationRegister);
                return true;
            }else{
                secondPriorityGroup.put(patientToBeVaccinated.getDNI(), newVaccinationRegister);
                return true;
            }
        }
    }

    /**
     * Metodo privado para crear la segunda cita de vacunacion, si el paciente es prioritario
     * lo añade a firstPriorityGroup y no lo es lo añade a secondPriorityGroup
     *
     * @param  patientToBeVaccinated Paciente a vacunar
     * @param secondAppointmentDate La fecha de la segunda cita
     * @return true
     */
    private boolean scheduleSecondVaccination(Patient patientToBeVaccinated, LocalDate secondAppointmentDate)
    {
        if(patientToBeVaccinated.isAPriorityPatient()){
            firstPriorityGroup.get(patientToBeVaccinated.getDNI()).setNextDosesAppointment(secondAppointmentDate);
            return true;
        }else{
            secondPriorityGroup.get(patientToBeVaccinated.getDNI()).setNextDosesAppointment(secondAppointmentDate);
            return true;
        }
    }

    /**
     * Metodo para buscar un registro de vacunacion. Se comprueba si el paciente es prioritario
     * o no para buscarlo en un HashMap u otro
     *
     * @param  patient El paciente sobre el cual se busca el registro
     * @return VaccinationRegister El registro de vacunacion buscaddo
     */
    public VaccinationRegister searchForVaccinationRegister(Patient patient)
    {
        
        if(patient.isAPriorityPatient()){
            return firstPriorityGroup.get(patient.getDNI());
        }else{
            return secondPriorityGroup.get(patient.getDNI());
        }
    }

    /**
     * Metodo que comprueba si un paciente ya esta programado para ser vacunado
     *
     * @param  patient El paciente a comprobar si esta vacunado
     * @return boolean true == paciente programado || false == paciente no programado
     */
    public boolean patientIsScheduledToBeVaccinated(Patient patient)
    {
        
        if(firstPriorityGroup.get(patient.getDNI()) != null || secondPriorityGroup.get(patient.getDNI()) != null){
            return true;
        }
        return false;
    }

    /**
     * Metodo que comprueba si el paciente ya esta programado para la segunda cita de vacunacion
     *
     * @param  patient El paciente a comprobar si esta programado para la segunda cita
     * @return boolean true == paciente programado para segunda cita || false == paciente no programado
     */
    public boolean patientIsScheduledForSecondDosesAppointment(Patient patient)
    {
        
        if(patient.isAPriorityPatient() && firstPriorityGroup.get(patient.getDNI()).getNextDosesAppointment() != null){
            return true;
        }else if(!patient.isAPriorityPatient() && secondPriorityGroup.get(patient.getDNI()).getNextDosesAppointment() != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo que comprueba si todos los pacientes prioritarios estan vacunados
     *
     * Se recorre todo el HashMap firstPriorityGroup y se llama al metodo isPatientVaccinated()
     * Si se encuentra algun paciente en el que este metodo devuelva false se devuelve un
     * false
     *
     * @return boolean true == todos los pacientes prioritarios vacunados || false == hay pacientes prioritarios sin vacunar
     */
    public boolean allPriorityPatientsAreVaccinated()
    {
        for(Map.Entry<String, VaccinationRegister> register : firstPriorityGroup.entrySet()){
            if(!register.getValue().isPatientVaccinated()){
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo para buscar la fecha mas cercana de todas las segundas citas del grupo prioritario
     * se recorren todas las citas y se obtiene la fecha mas cercana a la fecha actual
     *
     * @return  LocalDate La fecha más cercana de la segunda vacunacion
     */
    public LocalDate getClosestPriorityVaccinationSecondDate()
    {
        
        LocalDate currentRegisterDate = null;
        LocalDate closestRegisterDate = LocalDate.now().plusYears(100);
        LocalDate defaultDate = LocalDate.now().plusYears(100);

        for(Map.Entry<String, VaccinationRegister> register : firstPriorityGroup.entrySet()){
            currentRegisterDate = register.getValue().getNextDosesAppointment();
            if(currentRegisterDate != null && currentRegisterDate.isBefore(closestRegisterDate)){
                closestRegisterDate = currentRegisterDate;
            }
        }

        if(closestRegisterDate.isEqual(defaultDate)){
            return getFarthestPriorityVaccinationFirstDosesDate();
        }

        return closestRegisterDate;
    }

    /**
     * Metodo para buscar la fecha mas lejana de la primera vacunacion del grupo prioritario
     * El funcionamiento es igual al metodo getClosestPriorityVaccinationSecondDate
     *
     * @return LocalDate La fecha mas lejana
     */
    public LocalDate getFarthestPriorityVaccinationFirstDosesDate()
    {
        LocalDate currentRegisterDate = null;
        LocalDate farthestRegisterDate = LocalDate.now();
        for(Map.Entry<String, VaccinationRegister> register : firstPriorityGroup.entrySet()){
            currentRegisterDate = register.getValue().getFirstDosesAppointment();
            if(currentRegisterDate != null && currentRegisterDate.isAfter(farthestRegisterDate)){
                farthestRegisterDate = currentRegisterDate;
            }
        }
        return farthestRegisterDate;
    }

    /**
     * Metodo para saber una fecha dada se encuentra entre le fecha mas lejana de la primera
     * vacunacion y la fecha mas cercana de la segunda vacunacion. Este metodo hace uso de los
     * metodos getClosestPriorityVaccinationSecondDate y getFarthestPriorityVaccinationFirstDosesDate
     * para devolver un valor boolean dependiendo de si la fecha dada se encuentra entre ambas fechas
     * devueltas por los metodos
     *
     * Este metodo se utiliza basicamente para saber si un paciente no prioritario puede ser vacunado en medio
     * de las vacunaciones de los pacientes prioritarios
     *
     * @param  vaccinationDate La fecha de vacunacion
     * @return  boolean true == fecha entre dosis correctas || false == fecha No esta entre dosis correctas
     */
    public boolean dateIsBetweenDoses(LocalDate vaccinationDate)
    {
        
        LocalDate farthestDate = getFarthestPriorityVaccinationFirstDosesDate();
        LocalDate closestDate = getClosestPriorityVaccinationSecondDate();
        LocalDate defaultDate = LocalDate.now().plusYears(100);
        // Si no existen aun segundas citas programadas
        if(closestDate.isEqual(defaultDate)){
            return true;
        }else if(vaccinationDate.isAfter(farthestDate) && vaccinationDate.isBefore(closestDate)){
            return true;
        }
        return false;
    }

    /**
     * Metodo que obtiene la primera fecha de vacunacion de un paciente
     *
     * @param  patient El paciente del que queremos obtener la primera fecha de vacunacion
     * @return String La fecha en formato String
     */
    public String getFirstDosesDateAppointment(Patient patient)
    {
        
        if(patient.isAPriorityPatient()){
            return firstPriorityGroup.get(patient.getDNI()).getFirstDosesAppointment().toString();
        }
        else{
            return secondPriorityGroup.get(patient.getDNI()).getFirstDosesAppointment().toString();
        }
    }

    /**
     * Metodo para obtener la segunda fecha de vacunacion de un paciente
     *
     * @param  patient El paciente del que queremos obtener la segunda fecha de vacunacion
     * @return String La fecha en formato String || NO PROG si la fecha no ha sido programada
     */
    public String getSecondDosesDateAppointment(Patient patient)
    {
        
        if(patientIsScheduledForSecondDosesAppointment(patient)){
            if(patient.isAPriorityPatient()){
                return firstPriorityGroup.get(patient.getDNI()).getNextDosesAppointment().toString();
            }else{
                return secondPriorityGroup.get(patient.getDNI()).getNextDosesAppointment().toString(); 
            }
        }else{
            return "NO PROG.";
        }
    }

    /**
     * Comprueba si la fecha de la segunda dosis esta 21 dias por delante de  la primera
     *
     * @param  patient, dateToSecondDosis - Paciente, fecha para la segunda dosis
     * @return     true == fecha correcta || false == fecha incorrecta
     */
    private boolean secondDosesIsAfter21days(Patient patient, LocalDate dateToSecondDosis)
    {
        
        LocalDate firstDosesAppointment;
        LocalDate firstDosesAppointmentPlus21days;
        if(patient.isAPriorityPatient()){
            firstDosesAppointment = firstPriorityGroup.get(patient.getDNI()).getFirstDosesAppointment();
            firstDosesAppointmentPlus21days = firstDosesAppointment.plusDays(21);
            if(dateToSecondDosis.isAfter(firstDosesAppointmentPlus21days)){
                return true;
            }
        }
        else{
            firstDosesAppointment = secondPriorityGroup.get(patient.getDNI()).getFirstDosesAppointment();
            firstDosesAppointmentPlus21days = firstDosesAppointment.plusDays(21);
            if(dateToSecondDosis.isAfter(firstDosesAppointmentPlus21days)){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo encargado de asignar un/a enfermero/a a un registro de vacunacion
     *
     * Este metodo hace uso del metodo searchForVaccinationRegister() para buscar el registro
     * y a continuacion asignar el/la enfermero/a
     *
     * @param  patient El paciente al cual se le quiere asignar el/la enfermero/a
     * @param nurse El/la enfermero/a a asignar
     */
    public void assignNurseToRegister(Patient patient, Nurse nurse)
    {
        
        VaccinationRegister registerToAssingNurse = searchForVaccinationRegister(patient);
        registerToAssingNurse.assignNurse(nurse);
    }

    /**
     * Metodo encargado de marcar a un paciente como vacunado
     *
     * Este metodo hace uso del metodo searchForVaccinationRegister() para buscar el registro
     * y a continuacion marcar al paciente como vacunado con el metodo setPatientAsVaccinated de la
     * clase register
     *
     * @param  patient El paciente el cual se quiere marcar como vacunado
     */
    public void vaccinatePatient(Patient patient)
    {
        
        VaccinationRegister register = searchForVaccinationRegister(patient);
        register.setPatientAsVaccinated();
    }

    /**
     * Metodo encargado de asignar la primera fecha de vacunacion
     *
     * Hace uso del metodo searchForVaccinationRegister()
     *
     * @param  patient El paciente al cual se le quiere asignar la primera fecha
     */
    public void setFirstDosesDone(Patient patient)
    {
        
        VaccinationRegister register = searchForVaccinationRegister(patient);
        register.setFirstDosesState(true);
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si la primera
     * dosis del paciente ha sido dada o no
     *
     * Hace uso del metodo isFirstDosesDone() de la clase Register
     *
     * @param  patient El paciente sobre el cual se quiere consultar el estado de la primera dosis
     * @return boolean true == la primera dosis ha sido dada || false == la primera dosis  no ha sido dada
     */
    public boolean isFirstDosesDone(Patient patient)
    {
        
        VaccinationRegister register = searchForVaccinationRegister(patient);
        return register.isFirstDosesDone();
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si la segunda
     * dosis ha sido dada o no
     *
     * Hace uso del metodo searchForVaccinationRegister() de la clase Register
     *
     * @param  patient El paciente sobre el cual se quiere consultar el estado de la segunda dosis
     */
    public boolean isSecondDosesNeeded(Patient patient){
        VaccinationRegister register = searchForVaccinationRegister(patient);
        return register.isSecondDosesNeeded();
    }

    
    /**
     * Metodo encargado de indicar que la segunda dosis para un paciente no es necesaria,
     * esto solo ocurre en el caso de que la vacuna asignada sea Johnson
     *
     * @param  patient El paciente al cual queremos indicar en el registro que no necesita segunda dosis
     */
    public void secondDosesIsNoNeeded(Patient patient)
    {
        VaccinationRegister register = searchForVaccinationRegister(patient);
        register.secondDosesIsNeeded(false);
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si el paciente ya ha sido
     * vacunado o no
     *
     * @param  patient El paciente sobre el cual queremos consultar si ha sido vacunado o no
     * @return boolean true == ha sido vacunado || false == no ha sido vacunado
     */
    public boolean isPatientVaccinated(Patient patient)
    {
        
        VaccinationRegister register = searchForVaccinationRegister(patient);
        if(register != null){
            return register.isPatientVaccinated();    
        }
        return false;
    }

    /**
     * Metodo encargado de obtener el tipo enumero del tipo de vacuna asignada a un paciente
     *
     * @param  patient El paciente sobre el cual queremos consultar el tipo de vacuna asignada
     * @return VaccineType Tipo enumerado de las vacunas = JOHNSON, MODERNA, PFIZER
     */
    public VaccineType getVaccineType(Patient patient)
    {
        VaccinationRegister register = searchForVaccinationRegister(patient);
        if(register != null){
            return register.getTypeOfVaccineAssigned();
        }
        return VaccineType.UNKOWN;
    }

    /**
     * Metodo encargado de devolver un ArrayList de Strings con DNIs con los pacientes
     * a los que se les ha asignado el/la enfermero/a para la vacunacion
     *
     * @param  nurseDNI El DNI de el/la enfermero/a
     * @return ArrayList Lista de DNIs con los pacientes asignados a el/la enfermera con DNI nurseDNI
     */
    public ArrayList<String> getPatientsByNurseDNI(String nurseDNI)
    {
        
        ArrayList<String> DNIs = new ArrayList<>();

        for(Map.Entry<String, VaccinationRegister> register : firstPriorityGroup.entrySet()){
            Nurse currentNurse = register.getValue().getNurseAssigned();
            if(currentNurse != null && currentNurse.getDNI().equals(nurseDNI)){
                DNIs.add(register.getKey());
            }
        }

        for(Map.Entry<String, VaccinationRegister> register : secondPriorityGroup.entrySet()){
            Nurse currentNurse = register.getValue().getNurseAssigned();
            if(currentNurse != null && currentNurse.getDNI().equals(nurseDNI)){
                DNIs.add(register.getKey());
            }
        }

        return DNIs;
    }

}
