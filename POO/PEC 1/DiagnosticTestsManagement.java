import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Clase para la gestion de pruebas diagnosticas
 *
 * Esta clase contiene todos los campos y metodos necesarios para realizar la gestion de
 * las pruebas diagnosticas
 *
 * Por defecto creamos una estructura tipo HashMap que almacena como clave el DNI del paciente
 * y como valor una Lista con registros de las diferentes pruebas diagnosticas a las que denominamos
 * DiagnosticTest
 *
 * @author @Deividgdt
 * @version 0.8
 */
public class DiagnosticTestsManagement
{
    private Map<String, List<DiagnosticTest>> DiagnosticTestsDB;
    private DiagnosticTest diagnosticTest;
    private final int LASTTEST;

    /**
     * Constructor para objetos de la clase DiagnosticTestManagement
     */
    public DiagnosticTestsManagement()
    {
        LASTTEST = 0;
        DiagnosticTestsDB = new HashMap<>();
    }

    /**
     * Metodo para crear una prueba diagnostica
     *
     * Se comprueba si el tipo de prueba diagnostica a realizar es la primera
     * o si en su defecto es una nueva prueba pero si la prueba diagnostica es del mismo tipo
     * entonces se comprueba si el tiempo entre cada prueba es correcto
     *
     *
     * @param patientAssigned El paciente a asignar la prueba
     * @param test El tipo de test a realizar
     * @param dateToScheduleTest La fecha de realizacion del test
     *
     * @return boolean true == se ha creado la cita || false == no se ha creado la cita
     */
    public boolean createNewDiagnosticTest(Patient patientAssigned, Test test, LocalDate dateToScheduleTest)
    {
        String userDNI = patientAssigned.getDNI();
        diagnosticTest = new DiagnosticTest(patientAssigned, test, dateToScheduleTest);

        if(DiagnosticTestsDB.get(userDNI) == null){
            DiagnosticTestsDB.put(userDNI, new ArrayList<>());
            DiagnosticTestsDB.get(userDNI).add(LASTTEST, diagnosticTest);
            return true;
        }

        if(sameTypeOfLastDiagnostic(userDNI, test) && !timeBetweenDosesIsOK(userDNI, test, dateToScheduleTest)){
            return false;
        }else if(sameTypeOfLastDiagnostic(userDNI, test) && timeBetweenDosesIsOK(userDNI, test, dateToScheduleTest)){
            DiagnosticTestsDB.get(userDNI).add(LASTTEST, diagnosticTest);
            return true;
        }else if(!sameTypeOfLastDiagnostic(userDNI, test)){
            DiagnosticTestsDB.get(userDNI).add(LASTTEST, diagnosticTest);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo para comprobar si el tiempo entre dos pruebas de un test es correcto o no
     *
     * @param  DNI El DNI del paciente
     * @param test El test a comprobar
     * @param dateToScheduleTest La fecha de la cita
     *
     * @return boolean true == si la cita se puede crear || false == la cita no se puede crear
     */
    private boolean timeBetweenDosesIsOK(String DNI, Test test, LocalDate dateToScheduleTest)
    {
        LocalDate LastDiagnosticDate = DiagnosticTestsDB.get(DNI).get(LASTTEST).getDiagnosticTestDate();
        int daysBetweenDoses = test.getDaysBetweenDoses();
        LocalDate DiagnosticDatePlusDaysForDoses = LastDiagnosticDate.plusDays(daysBetweenDoses);

        if(dateToScheduleTest.isAfter(DiagnosticDatePlusDaysForDoses)){
            return true;
        }

        return false;
    }

    /**
     * Metodo para comprobar si el tipo de prueba diagnostica pasada por argumento
     * es el mismo tipo de prueba realizada la ultima vez
     *
     * @param  DNI El DNI paciente
     * @param test El tipo de prueba diagnostica
     *
     * @return boolean true == La prueba es la misma || false == la prueba es distinta
     */
    private boolean sameTypeOfLastDiagnostic(String DNI, Test test)
    {
        String lastDiagnosticTest = DiagnosticTestsDB.get(DNI).get(LASTTEST).getDiagnosticTestType().toString();
        String newDiagnosticTest = test.getTestName();
        if(newDiagnosticTest.equals(lastDiagnosticTest)){
            return true;
        }
        return false;
    }

    /**
     * Metodo para comprobar si un empleado esta disponible para hacer un test
     *
     * @param  employeeAssigned El empleado a comprobar
     * @return boolean true == disponible ||false == no disponible
     */
    private boolean isAvailableToMakeATest(Employee employeeAssigned)
    {
        if(employeeAssigned.isAvailableToMakeTest()){
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de asignar a un tecnico de laboratorio a una prueba diagnostica
     *
     * @param  DNI El DNI del paciente
     * @param labTechAssigned El tecnico de laboratio a asignar
     */
    public boolean assignLabtechToDiagnostic(String DNI, LabTech labTechAssigned)
    {
        if(isAvailableToMakeATest(labTechAssigned)){
            DiagnosticTestsDB.get(DNI).get(LASTTEST).assignLabTech(labTechAssigned);
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de asignar a un/a enfermero/a a una prueba diagnostica
     *
     * @param  DNI El dni del paciente
     * @param nurseAssigned El/la enfermero/a asignado/a
     *
     * @return boolean true == si se puede asignar el/la enfermara || false == si no se puede asignar
     */
    public boolean assignNurseToDiagnostic(String DNI, Nurse nurseAssigned)
    {
        if(isAvailableToMakeATest(nurseAssigned)){
            DiagnosticTestsDB.get(DNI).get(LASTTEST).assignNurse(nurseAssigned);
            return true;
        }
        return false;
    }

    /**
     * Meotodo encargado de establecer el resultado de la prueba diagnostica
     *
     * @param DNI  El DNI del paciente
     * @param testValue El resultado del test
     */
    public void setTestResult(String DNI, boolean testValue)
    {
        DiagnosticTestsDB.get(DNI).get(LASTTEST).setResult(testValue);
    }

    /**
     * Metodo encargado de obtener la fecha de realizacion de la prueba diagnostica
     *
     * @param  DNI El DNI del paciente
     *
     * @return  LocalDate La fecha de realizacion de la prueba
     */
    public LocalDate getDiagnosticTestDate(String DNI)
    {
        return DiagnosticTestsDB.get(DNI).get(LASTTEST).getDiagnosticTestDate();
    }

    /**
     * Metodo para obtener el tipo de prueba diagnostica
     *
     * @param DNI El DNI del paciente
     * @return DiagnosticTestType El tipo de prueba diagnostico
     */
    public DiagnosticTestType getLastDiagnosticTestType(String DNI)
    {
        return DiagnosticTestsDB.get(DNI).get(LASTTEST).getDiagnosticTestType();
    }

    /**
     * Metodo encargado de devolver el resultado de la prueba
     *
     * @param  DNI El dni del paciente
     * @return boolean true == positiva || false == negativa
     */
    public boolean getLastDiagnosticResult(String DNI)
    {
        return DiagnosticTestsDB.get(DNI).get(LASTTEST).getResult();
    }

    /**
     * Metodo encargado de establecer el valor de la prueba serologica
     *
     * @param  DNI El DNI del paciente
     * @param testValue El resultadod de la prueba
     */
    public void setLastSerologicalTestResult(String DNI, Integer testValue)
    {
        DiagnosticTestsDB.get(DNI).get(LASTTEST).setSerologicalResult(testValue);
    }

    /**
     * Metodo encargado de devolver el valor de la prueba serologica
     *
     * @param  DNI El DNI del paciente
     * @return Integer El valor de 1 a 10 del resultado de la prueba serologica
     */
    public Integer getLastSerologicalTestResult(String DNI)
    {
        return DiagnosticTestsDB.get(DNI).get(LASTTEST).getSerologicalResult();
    }

    /**
     * Metodo encargado de devolver el estado de la prueba diagnostica
     * si ha sido realizada o no
     *
     * @param  DNI El DNI del paciente
     * @return String El string con el resultado de la prueba si ha sido realizada
     */
    public String getTestState(String DNI)
    {
        return DiagnosticTestsDB.get(DNI).get(LASTTEST).getTestState();
    }

    /**
     * Metodo encargado de establecer la prueba diagnostica como realizada
     *
     * @param  DNI El DNI del paciente
     */
    public void setTestAsDone(String DNI)
    {

        DiagnosticTestsDB.get(DNI).get(LASTTEST).setTestAsDone();
    }

    /**
     * Metodo encargado de devolver una lista con los DNIs de los pacientes asignados a un
     * tecnico de laboratorio
     *
     * @param labTechDNI El DNI del tecnico de laboratio
     * @return ArrayList Lista con los DNIs de los pacientes
     */
    public ArrayList<String> getPatientsByLabTechDNI(String labTechDNI)
    {
        ArrayList<String> DNIs = new ArrayList<>();

        for(Map.Entry<String, List<DiagnosticTest>> diagnostic : DiagnosticTestsDB.entrySet()){
            LabTech currentLabTech = diagnostic.getValue().get(LASTTEST).getLabTechAssigned();
            if(currentLabTech != null && currentLabTech.getDNI().equals(labTechDNI)){
                DNIs.add(diagnostic.getKey());
            }
        }
        return DNIs;
    }

    /**
     * Metodo encargado de devolver una lista con DNIs de los pacientes asignados a un/a enfermero/a
     *
     * @param nurseDNI El DNI de el/la enfermero/a
     * @return ArrayList Lista con los DNIs de los pacientes
     */
    public ArrayList<String> getPatientsByNurseDNI(String nurseDNI)
    {
        ArrayList<String> DNIs = new ArrayList<>();

        for(Map.Entry<String, List<DiagnosticTest>> diagnostic : DiagnosticTestsDB.entrySet()){
            Nurse currentNurse = diagnostic.getValue().get(LASTTEST).getNurseAssigned();
            if(currentNurse != null && currentNurse.getDNI().equals(nurseDNI)){
                DNIs.add(diagnostic.getKey());
            }
        }
        return DNIs;
    }
}
