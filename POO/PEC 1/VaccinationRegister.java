import java.time.LocalDate;
import java.util.Random;

/**
 * Clase que define lo que es un registro de vacunacion
 *
 * Esta clase contiene diferentes metodos para llevar un control de la vacunacion de un paciente
 * si este ha sido vacunado con la primera dosis, la segunda, que tipo de vacuna se le ha asignado
 * fechas de la primera y segunda dosis, enfemero/a asignado para vacunarle
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class VaccinationRegister
{
    private boolean vaccinated, firstDosesState, secondDosesNeeded;
    private final LocalDate firstAppointment;
    private LocalDate nextAppointment;
    private final Patient patientToVaccinated;
    private Nurse nurseAssigned;

    private final Random randomVaccineGenerator;
    private Vaccine vaccineAssgined;
    private VaccineType typeAssigned;

    /**
     * Constructor para objetos de la clase VaccinationRegister
     *
     * Por defecto un tipo de vacuna desconocida es asignada y a continuación el metodo setTypeOfVaccineRandomly()
     * se encarga de asignar una vacuna aleatoriamente
     *
     * Igualmente, por defecto se indica que la segunda dosis no es necesaria con secondDosesNeeded = false
     * pero en el momento de la asignacion de la vacuna si esta es distinta a Johnson se asigna a este campo el valor true
     *
     * @param patientToVaccinated Paciente al que se quiere vacunar
     * @param firstAppointment Fecha de la primera cita
     */
    public VaccinationRegister(Patient patientToVaccinated, LocalDate firstAppointment)
    {

        this.patientToVaccinated = patientToVaccinated;
        this.firstAppointment = firstAppointment;

        typeAssigned = VaccineType.UNKOWN;
        vaccinated = false;
        firstDosesState = false;
        secondDosesNeeded = false;

        randomVaccineGenerator = new Random();
        setTypeOfVaccineRandomly();
    }

    /**
     * Metodo encargado de marcar al paciente como vacunado
     *
     */
    public void setPatientAsVaccinated()
    {
        vaccinated = true;
    }

    /**
     * Metodo encargado de asignar un valor boolean a la primera dosis de vacunacion
     *
     * @param  firstDosesState Estado de la primera dosis
     */
    public void setFirstDosesState(boolean firstDosesState)
    {
        this.firstDosesState = firstDosesState;
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si la primera
     * dosis ha sido dada o no
     *
     * @return boolean true == primera dosis dada || false == primera dosis no dada
     */
    public boolean isFirstDosesDone()
    {
        return firstDosesState;
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de la segunda dosis
     * es necesaria para el paciente o no
     *
     * @return boolean true == segunda dosis necesaria || false == la segunda dosis NO es necesaria
     */
    public boolean isSecondDosesNeeded()
    {
        return secondDosesNeeded;
    }

    /**
     * Metodo encargado de asignar si la segunda dosis es necesaria o no
     *
     * @param  secondDosesState El estado de la segunda dosis : true == segunda dosis necesaria || false == segunda dosis no necesaria
     */
    public void secondDosesIsNeeded(boolean secondDosesState)
    {
        this.secondDosesNeeded = secondDosesState;
    }


    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si el paciente esta
     * vacunado o no
     *
     * @return boolean true == paciente vacunado || false == paciente no vacunado
     */
    public boolean isPatientVaccinated()
    {
        return vaccinated;
    }

    /**
     * Metodo encargado de obtener la primera fecha de vacunacion
     *
     * @return LocalDate Fecha de la primera vacunacion
     */
    public LocalDate getFirstDosesAppointment()
    {
        return firstAppointment;
    }

    /**
     * Metodo encargado de establecer la fecha de la segunda vacunacion
     *
     * @param  nextAppointment Fecha de la segunda cita de vacunacion
     */
    public void setNextDosesAppointment(LocalDate nextAppointment)
    {
        this.nextAppointment = nextAppointment;
    }

    /**
     * Metodo encargado de devolver la segunda fecha de vacunacion
     *
     * @return LocalDate Segunda fecha de vacunacion
     */
    public LocalDate getNextDosesAppointment()
    {
        if(nextAppointment != null){
            return nextAppointment;
        }
        return null;
    }

    /**
     * Metodo encargado de asignar una vacuna aleatoriamente al paciente
     *
     * Se genera un numero entero aleatorio de 0 a 2 y asigna a la variable
     * vaccineSelected, a continuación con un switch se compara el valor asignado
     * con cada caso. 0 Johnson, 1 Pfizer y 2 Moderna
     *
     */
    public void setTypeOfVaccineRandomly()
    {
        int vaccineSelected = randomVaccineGenerator.nextInt(3);

        switch(vaccineSelected){
            case 0:
            vaccineAssgined = new Johnson();
            typeAssigned = VaccineType.JOHNSON;
            break;
            case 1:
            vaccineAssgined = new Pfizer();
            typeAssigned = VaccineType.PFIZER;
            secondDosesNeeded = true;
            break;
            case 2:
            vaccineAssgined = new Moderna();
            typeAssigned = VaccineType.MODERNA;
            secondDosesNeeded = true;
            break;
        }
    }

    /**
     * Metodo encargado de devolver el tipo enumerado de vacuna asignada
     *
     * @return VaccineType Vacuna asignada
     */
    public VaccineType getTypeOfVaccineAssigned()
    {
        return typeAssigned;
    }

    /**
     * Metodo encargado de asignar un enfemero/a al registro para vacunar al paciente
     *
     * @param  nurseToAssign El/la enfermero/a a asignar
     */
    public void assignNurse(Nurse nurseToAssign)
    {
        this.nurseAssigned = nurseToAssign;
    }

    /**
     * Metodo encargado de devovler el/la enfermero/a asignado/a al registro
     *
     * @return Nurse El/la enfermero/a
     */
    public Nurse getNurseAssigned()
    {
        return nurseAssigned;
    }

    /**
     * Metodo encargado de imprimir todos los detalles del registro:
     *  Enfermero y su numero de empleado
     *  Paciente y su DNI
     *  Vacuna
     *  Fecha de la primera vacunacion
     *  Fecha de la segunda vacunacion
     *  Si el paciente ha sido vacunado o no
     *
     */
    public void getDetails()
    {
        String nextDosesAppointment = "";

        System.out.print("   +++ Enfermero/a asignado/a: "+getNurseAssigned().getName()+ " -");
        System.out.println(" Numero Empl: "+getNurseAssigned().getEmployeeNumber());
        System.out.print("   +++ Paciente: "+patientToVaccinated.getName()+ " -");
        System.out.println(" DNI: "+patientToVaccinated.getDNI());

        System.out.println("   +++ Vacuna: "+getTypeOfVaccineAssigned().toString());

        System.out.println("   +++ Fecha primera vacunacion: "+getFirstDosesAppointment().toString());

        if(getNextDosesAppointment() == null){
            nextDosesAppointment = "NO PROGRAMADA";
        }else{
            nextDosesAppointment = getNextDosesAppointment().toString();
        }

        System.out.println("   +++ Fecha segunda vacunacion: "+nextDosesAppointment);

        System.out.print("   +++ El paciente ");
        if(!isPatientVaccinated()){
            System.out.print("aun no ");
        }
        System.out.println("ha sido vacunado.");
    }
}
