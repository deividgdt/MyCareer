import java.time.LocalDate;
/**
 * Clase donde se define una prueba serologica
 *
 * Esta clase contiene todos los metodos y campos necesarios para gestionar una
 * prueba diagnostica: obtener el estado de la prueba, obtener la fecha de realizacion,
 * obtener el/la enfermero/a asingado/a, obtener el resultado de la prueba,
 * tanto como si es serologico o no, obtener el tecnico de laboratio asignado, obtener el
 * tipo de prueba diagostica realizada, etc...
 * 
 * @author @Deividgdt 
 * @version 1.0
 */
public class DiagnosticTest
{
    private Nurse nurseAssigned;
    private LabTech labTechAssigned;
    private Patient patientAssigned;
    private LocalDate diagnosticTestDate;
    private Test test;
    private String testState;

    /**
     * Constructor para objetos DiagnosticTest con fecha programada
     *
     * @param patientAssigned El paciente asignado
     * @param test El tipo de prueba diagnostica
     * @param dateToScheduleTest Fecha de la prueba diagnostica
     */
    public DiagnosticTest(Patient patientAssigned, Test test, LocalDate dateToScheduleTest)
    {
        this.test = test;
        testState = "SIN REALIZAR";

        diagnosticTestDate = dateToScheduleTest;

        nurseAssigned = null;
        labTechAssigned = null;
        this.patientAssigned = patientAssigned;
    }

    /**
     * Metodo que establece la prueba como realizada
     */
    public void setTestAsDone()
    {
        testState = "REALIZADA";
    }

    /**
     * Metodo que devuelve un String con el estado de la prueba
     *
     * @return String Estado de la prueba en un String
     */
    public String getTestState()
    {
        return testState;
    }


    /**
     * Metodo para obtener la fecha de la prueba diagnostica
     *
     * @return LocalDate La fecha de la prueba diagnostica
     */
    public LocalDate getDiagnosticTestDate()
    {
        return diagnosticTestDate;
    }

    /**
     * Metodo para asignar un tecnico de laboratio a la prueba diagnostica
     *
     * @param labTechAssigned El tecnico de laboratio a asignar
     */
    public void assignLabTech(LabTech labTechAssigned)
    {
        this.labTechAssigned = labTechAssigned;
    }

    /**
     * Metodo para asignar un/a enfermero/a a la prueba diagnostica
     *
     * @param nurseAssigned Enfermero/a asignado/a a la prueba
     */
    public void assignNurse(Nurse nurseAssigned)
    {
        this.nurseAssigned = nurseAssigned;
    }

    /**
     * Metodo para obtener el tecnico de laboratorio asignado a la prueba
     *
     * @return LabTech El tecnico asignado
     */
    public LabTech getLabTechAssigned()
    {
        return labTechAssigned;
    }

    /**
     * Metodo para obtener el/la enfermero/a asignado a la prueba diagnostica
     *
     * @return Nurse El/la enfermero/a asignado/a
     */
    public Nurse getNurseAssigned()
    {
        return nurseAssigned;
    }

    /**
     * Metodo para obtener el resultado de la prueba diagnostica
     *
     * @return boolean
     */
    public boolean getResult()
    {
        return test.isPositive();
    }

    /**
     * Metodo para establecer el resultado de la prueba diagnostica
     */
    public void setResult(boolean testValue)
    {
        test.setResult(testValue);
    }

    /**
     * Metodo para obtaener el resultado de la prueba serologica en caso de que esta sea
     * el tipo de prueba asignada al test
     *
     * Si la prueba es mayor que 2 , se entiende que el resultado es positivo
     *
     * @return Integer El valor del 1 al 10 de la prueba
     */
    public Integer getSerologicalResult()
    {
        Serological serologicalTest = ( Serological )test;
        return serologicalTest.getSerologicalResult();
    }

    /**
     * Metodo para establecer el resultado de la prueba serologica
     *
     * @param  serologicalTestResult El resultado de la prueba serologica
     */
    public void setSerologicalResult(Integer serologicalTestResult)
    {
        Serological serologicalTest = ( Serological )test;
        serologicalTest.setSerologicalResult(serologicalTestResult);
    }

    /**
     * Metodo para obtener el tipo enumerado de prueba diagnostica asignada al test
     *
     * Dependiendo de si la variable test que almacena la prueba diagnostica es una instancia
     * de un tipo de prueba concreto (SEROLOGICAL, PCR, QUICKANTIGEN, PCRANTIGEN) se devuelve este
     * en el return del metodo
     *
     * @return DiagnosticTestType El tipo enumero de prueba diagnostica
     */
    public DiagnosticTestType getDiagnosticTestType()
    {
        if(test instanceof Serological){
            return DiagnosticTestType.SEROLOGICAL;
        }else if(test instanceof PCR){
            return DiagnosticTestType.PCR;
        }else if(test instanceof QuickAntigen){
            return DiagnosticTestType.QUICKANTIGEN;
        }else if(test instanceof PCRAntigen){
            return DiagnosticTestType.PCRANTIGEN;
        }else{
            return DiagnosticTestType.UNKNOWN;
        }
    }
}
