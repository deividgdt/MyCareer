import java.time.LocalDate;

/**
 * Clase creada para los pacientes confinados
 * 
 * Para instanciar un objeto de esta clase solo se necesita el paciente a confinar
 * y la fecha desde la cual se le confina
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class LockedDownPatient 
{
    private LocalDate date;
    private boolean lockedDown;
    private LocalDate lockedDownSince;
    private LocalDate lockedDownTil;
    private Patient patientToLockedDown;

    /**
     * Constructor para objetos de la clase LockedDownPatient
     *  
     * @param patientToLockedDown Paciente a confinar
     * @param lockedDownSince Fecha desde la cual el paciente queda confinado
     */
    public LockedDownPatient(Patient patientToLockedDown, LocalDate lockedDownSince)
    {
        this.patientToLockedDown = patientToLockedDown;
        this.lockedDownSince = lockedDownSince;
        lockedDownTil = lockedDownSince.plusDays(10);
        lockedDown = true;
    }

    /**
     * Metodo encargado de devolver un valor boolean dependiendo de si el paciente esta confinado o no
     * 
     * @return boolean lockedDown true == confinado || false == no confinado
     */
    public boolean isLockedDown()
    {
        return lockedDown;
    }

    /**
     * Metodo encargado de devolver la fecha desde la que el paciente esta confinado
     *
     * @return LocalDate lockedDownSince Fecha desde la cual es paciente esta confinado
     */
    public LocalDate getLockedDownSince()
    {
        return lockedDownSince;
    }

    /**
     * Metodo encargado de devolver la fecha hasta la que el paciente esta confinado
     *
     * @return LocalDate lockedDownTil Fecha hasta la cual es paciente esta confinado
     */
    public LocalDate getLockedDownTil()
    {
        return lockedDownTil;
    }

}
