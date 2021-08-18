import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase principal que lanza la interfaz grafica de la aplicacion
 *
 * Ademas en esta clase se tratan los diferentes datos de entrada de los usuarios
 * y se hace uso de las principales clases gestoras de cada grupo
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class MainMenu
{
    private Scanner readInput;
    private String userLoginDNI;
    private UsersManagement usersManagement;
    private DiagnosticTestsManagement diagnosticTestsManagement;
    private VaccinationManagement vaccinationManagement;
    private VaccineStockManagement vaccineStockManagement;
    private final int ADMIN, NURSE, LABTECH;
    private Integer userType;

    /**
     * Constructor para objetos de la clase MainMenu
     */
    public MainMenu()
    {
        ADMIN = 1;
        NURSE = 2;
        LABTECH = 3;
        userType = null;
        userLoginDNI = null;

        readInput = new Scanner(System.in);
        diagnosticTestsManagement = new DiagnosticTestsManagement();
        vaccinationManagement = new VaccinationManagement();
        vaccineStockManagement = new VaccineStockManagement();

        // Inicializa la BBDD
        initializeDB();

    }

    /**
     * Metodo encargado de inicializar la BBDD con los usuarios, vacunaciones y pruebas por defecto
     */
    private void initializeDB()
    {
         // Creacion de Usuario admin por defecto
        usersManagement = new UsersManagement();
        usersManagement.createNewEmployee(1, "admin", 100, "admin", 0); //ADMIN
        
        // Añadir 50 vacunas de cada
        vaccineStockManagement.addJohnsonUnits(50);
        vaccineStockManagement.addModernaUnits(50);
        vaccineStockManagement.addPfizerUnits(50);


        //usuarios por defecto para tests
        usersManagement.createNewEmployee(2, "Nerea", 25, "100A", 1);    // NURSE, 2 pruebas
        usersManagement.createNewEmployee(2, "Julian", 25, "101A", 2); //NURSE, 1 vacunacion 
        usersManagement.createNewEmployee(2, "Pedro", 58, "102A", 3);    // NURSE, 2 vacunaciones, 1 prueba
        usersManagement.createNewEmployee(3, "Adrian", 28, "103A", 4); // LABTECH
        usersManagement.createNewEmployee(3, "Roberto", 28, "104A", 5); //LABTEC
        usersManagement.createNewEmployee(3, "Isabel", 28, "105A", 6); // LABTECH
        usersManagement.createNewPatient("David-21-1A", 21, "1A"); //PATIENT
        usersManagement.createNewPatient("Jesus-89-2A", 89, "2A"); //PATIENT
        usersManagement.createNewPatient("Chloe-78-3A", 78, "3A"); //PATIENT
        usersManagement.createNewPatient("Naomi-35-4A", 35, "4A"); //PATIENT
        usersManagement.createNewPatient("Mikel-87-5A", 87, "5A"); //PATIENT

        //Vacunaciones por defecto
        vaccinationManagement.scheduleVaccination(usersManagement.getPatient("2A"), LocalDate.of(2021,07,02));
        vaccinationManagement.scheduleVaccination(usersManagement.getPatient("3A"), LocalDate.of(2021,07,03));
        vaccinationManagement.scheduleVaccination(usersManagement.getPatient("5A"), LocalDate.of(2021,07,01));
        if(vaccinationManagement.getVaccineType(usersManagement.getPatient("5A")) != VaccineType.JOHNSON){
            vaccinationManagement.scheduleVaccination(usersManagement.getPatient("5A"), LocalDate.of(2021,07,24));
        }

        //Asignar nurse a vacunacion
        vaccinationManagement.assignNurseToRegister(usersManagement.getPatient("2A") , usersManagement.getNurse("102A"));
        usersManagement.getNurse("102A").makeATest();
        vaccinationManagement.assignNurseToRegister(usersManagement.getPatient("3A") , usersManagement.getNurse("102A"));
        usersManagement.getNurse("102A").makeATest();
        vaccinationManagement.assignNurseToRegister(usersManagement.getPatient("3A") , usersManagement.getNurse("101A"));
        usersManagement.getNurse("101A").makeATest();
        vaccinationManagement.assignNurseToRegister(usersManagement.getPatient("5A") , usersManagement.getNurse("102A"));
        usersManagement.getNurse("102A").makeATest();

        //Pruebas diagnosticas por defecto
        diagnosticTestsManagement.createNewDiagnosticTest(usersManagement.getPatient("1A"), new Serological(), LocalDate.of(2021,07,15));
        diagnosticTestsManagement.createNewDiagnosticTest(usersManagement.getPatient("2A"), new Serological(), LocalDate.of(2021,07,16));
        diagnosticTestsManagement.createNewDiagnosticTest(usersManagement.getPatient("3A"), new PCR(), LocalDate.of(2021,07,16));
        diagnosticTestsManagement.createNewDiagnosticTest(usersManagement.getPatient("5A"), new PCRAntigen(), LocalDate.of(2021,07,17));

        //Asignar nurse a pruebas diagnosticas
        diagnosticTestsManagement.assignNurseToDiagnostic("1A", usersManagement.getNurse("100A"));
        usersManagement.getNurse("100A").makeATest();
        diagnosticTestsManagement.assignNurseToDiagnostic("2A", usersManagement.getNurse("100A"));
        usersManagement.getNurse("100A").makeATest();
        diagnosticTestsManagement.assignNurseToDiagnostic("3A", usersManagement.getNurse("100A"));
        usersManagement.getNurse("100A").makeATest();
        diagnosticTestsManagement.assignNurseToDiagnostic("5A", usersManagement.getNurse("102A"));
        usersManagement.getNurse("102A").makeATest();

        //Asignar labtech a pruebas diagnosticas
        diagnosticTestsManagement.assignLabtechToDiagnostic("2A", usersManagement.getLabTech("103A"));
        usersManagement.getLabTech("103A").makeATest();
        diagnosticTestsManagement.assignLabtechToDiagnostic("3A", usersManagement.getLabTech("103A"));
        usersManagement.getLabTech("103A").makeATest();
        diagnosticTestsManagement.assignLabtechToDiagnostic("5A", usersManagement.getLabTech("103A"));
        usersManagement.getLabTech("103A").makeATest();
        
    }

    /**
     * Metodo encargado de mostrar el banner de la aplicacion
     */
    private void showBanner()
    {
        // put your code here
        System.out.println(""); 
        System.out.println("     |________|___________________|_");
        System.out.println("     |        | | | | | | | | | | | |_______");
        System.out.println("     |________|___________________|_|      ,");
        System.out.println("     |        |                   |        ,");
        System.out.println("");
        System.out.println("   _______   ___  ____    _________ _   _____ ");
        System.out.println("  / __/ _ | / _ \\/ __/___/ ___/ __ \\ | / /_  |");
        System.out.println(" _\\ \\/ __ |/ , _/\\ \\/___/ /__/ /_/ / |/ / __/ ");
        System.out.println("/___/_/ |_/_/|_/___/    \\___/\\____/|___/____/ ");
        System.out.println("         Gestión de campaña de vacunacion     ");
        System.out.println("                                              ");
        System.out.println("                 -------------                ");
    }

    /**
     * Metodo encargado de loggear al usuario en la aplicacion
     *
     * Este es el metodo principal el cual llamado desde la clase Clinica
     *
     * Con este metodo se inicia la ejecucion de toda la aplicacion
     */
    public void loginUser()
    {
        // Mostramos el banner y el texto de inicio de sesion
        showBanner();
        System.out.print("              Usuario: ");
        userLoginDNI = readInput.nextLine();

        // Mientras el usuario no pueda iniciar sesion se le
        // continua enseñando la pantalla de inicio de sesion
        while(!userCanLogin(userLoginDNI)){
            userLoginDNI = readInput.nextLine();
        }

        // Dependiendo del tipo de usuario se asigna a la variable userType un
        // int que indica el tipo de usuario. Esto se usa para una correcta
        // gestion de las opciones mostradas a cada usuario en los diferentes
        // menus
        if(usersManagement.getUser(userLoginDNI) instanceof Administrator){
            userType = ADMIN;
        }else if(usersManagement.getUser(userLoginDNI) instanceof Nurse){
            userType = NURSE;
        }
        else if(usersManagement.getUser(userLoginDNI) instanceof LabTech){
            userType = LABTECH;
        }
        showMenu();
    }

    /**
     * Meotodo en cargado de comprobar si un usuario se puede loguear en el sistema
     *
     * La forma en controlar esta parte es simplemente comprobando si el DNI pasado
     * por el usuario de la aplicacion existe en la base de datos
     *
     * Por defecto se ha creado un usuario administrador. Para el administrador su DNI
     * es simplemente; admin, para el resto de usuarios del sistema se podra iniciar
     * sesion indicando el DNI de cada uno
     */
    private boolean userCanLogin(String DNI)
    {
        // put your code here
        if(DNI.equals("admin")){
            System.out.println("              OK. Bienvenido Admin.");
            return true;
        }else if(usersManagement.existUser(DNI)){
            System.out.println("              OK. Bienvenido/a "+usersManagement.getUserFullname(DNI)) ;
            return true;
        }else{
            System.out.println("        Usuario incorrecto. Intentelo de nuevo") ;
            System.out.print("                Usuario: ");
            return false;
        }

    }

    /**
     * Metodo encargado de mostrar el menu prinicipal
     *
     * Como se puede apreciar el menu muestra unas opciones u otras dependiendo
     * de la variable userType y que valor tiene asignado esta
     */
    private void showMenu()
    {
        String answer = "";

        while(!answer.equals("Q")){
            System.out.println("\n >   MENU PRINCPAL");
            System.out.println("");
            if(userType == ADMIN){
                System.out.println("   Gestion de usuarios                    (G)");
                System.out.println("   Planificacion tentativa                (T)?");
                System.out.println("   Gestion de pruebas serologicas         (L)?");
                System.out.println("   Listado de pacientes confinados        (C)?");
                System.out.println("   Visualizacion de datos de usuarios     (U)");
                System.out.println("   Buscar usuario                         (B)");
                System.out.println("   Gestion de pruebas diagnosticas        (D)");
                System.out.println("   Gestion de vacunacion                  (V)");
                System.out.println("   Gestion de stock de vacunas            (S)");
            }
            if(userType == NURSE){
                System.out.println("   Gestion de pruebas diagnosticas        (D)");
                System.out.println("   Gestion de vacunacion                  (V)");
            }
            if(userType == LABTECH){
                System.out.println("   Gestion de pruebas diagnosticas        (D)");
            }
            System.out.println("   Visualizacion de pacientes             (P)");
            System.out.println("   Desconectarse                          (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "G":
                usersManagementMenu();
                break;
                case "D":
                diagnosticTestsManagementMenu();
                break;
                case "V":
                vaccinationManagementMenu();
                break;
                case "U":
                showUsersMenu();
                break;
                case "P":
                showPatientsMenu(userLoginDNI);
                break;
                case "B":
                searchUserMenu();
                break;
                case "S":
                stockManagementMenu();
                // Estas opciones son del nivel 3 y no han sido realizadas en esta practica
                case "T":
                case "L":
                case "C":
                System.out.println("\n\n   Nivel 3 - En desarrollo...");
                break;
            }
        }
        // Con la opcion Q el usuario puede deslogearse del sistema y dar paso al logueo de otro usuario
        if(answer.equals("Q")){
            System.out.println("\n\n   [OK]--> Se ha desconectado satisfactoriamente.");
            loginUser();
        }
    }

    /**
     * Metodo encargado de mostrar todas las opciones de la gestion de usuarios
     */
    private void usersManagementMenu()
    {
        String answer = "";
        String name, age, DNI, type, employeeNumber;

        while(!answer.equals("Q")){
            System.out.println("\n >   GESTION DE USUARIOS");
            System.out.println("");
            System.out.println("   Alta nuevo paciente                    (P)");
            System.out.println("   Alta nuevo empleado                    (E)");
            System.out.println("   Baja paciente/empleado                 (B)");
            System.out.println("   Modificacion paciente/empleado         (M)");
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "P":
                    System.out.print("   Nombre: ");
                    name = readInput.nextLine();
                    System.out.print("   DNI :");
                    DNI = readInput.nextLine();
                    System.out.print("   Edad: ");
                    age = readInput.nextLine();

                    usersManagement.createNewPatient(name, Integer.parseInt(age), DNI);
                break;

                case "E":                    
                    System.out.print("   Nombre: ");
                    name = readInput.nextLine();
                    System.out.print("   DNI :");
                    DNI = readInput.nextLine();
                    System.out.print("   Edad: ");
                    age = readInput.nextLine();
                    System.out.print("   Numero de empleado: ");
                    employeeNumber = readInput.nextLine();
                    System.out.print("   El usuario sera Administrador (1), Enfermero/a (2) o Tecnico/a de laboratorio (3)? : ");
                    type = readInput.nextLine();

                    usersManagement.createNewEmployee(Integer.parseInt(type), name, Integer.parseInt(age), DNI, Integer.parseInt(employeeNumber));
                break;

                case "B":                    
                    System.out.print("   DNI del usuario a dar de baja: ");
                    DNI = readInput.nextLine();
                    usersManagement.deleteUserFromDatabase(DNI);
                break;

                case "M":
                    userManagementModificationMenu();
                break;
            }

        }
    }

    /**
     * Metodo encargado de mostrar las opciones relacionadas con la modificacion de usuarios
     */
    private void userManagementModificationMenu(){
        String answer = "";
        String name, age, DNI, newDNI, employeeNumber;

        while(!answer.equals("Q")){
            System.out.println("\n >   MODIFICACION DE USUARIOS");
            System.out.println("");
            System.out.println("   Modificar nombre                       (N)");
            System.out.println("   Modificar edad                         (E)");
            System.out.println("   Modificar DNI                          (D)");
            System.out.println("   Modificar numero de empleado           (R)");
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "N":
                    System.out.print("   DNI: ");
                    DNI = readInput.nextLine();
                    System.out.println("   Nombre actual: "+usersManagement.getUserFullname(DNI));
                    System.out.print("   Nombre nuevo: ");
                    name = readInput.nextLine();
                    usersManagement.modifyUserName(DNI, name);
                break;

                case "E":
                    System.out.print("   DNI: ");
                    DNI = readInput.nextLine();
                    System.out.println("   Edad actual: "+usersManagement.getUserAge(DNI));
                    System.out.print("   Edad nueva: ");
                    age = readInput.nextLine();
                    usersManagement.modifyUserAge(DNI, Integer.parseInt(age));
                break;

                case "D":
                    System.out.print("   DNI: ");
                    DNI = readInput.nextLine();
                    System.out.print("   DNI nuevo: ");
                    newDNI = readInput.nextLine();
                    usersManagement.modifyUserDNI(DNI, newDNI);
                break;

                case "R":
                    System.out.print("   DNI: ");
                    DNI = readInput.nextLine();
                    System.out.println("   Numero de empleado nuevo actual: "+usersManagement.getEmployeeNumber(DNI));
                    System.out.print("   Numero de empleado nuevo: ");
                    employeeNumber = readInput.nextLine();
                    usersManagement.modifyEmployeeNumber(DNI, Integer.parseInt(employeeNumber));
                break;
            }
        }
    }

    /**
     * Metodo encargado de mostrar todas las opciones de gestion de las pruebas diagnosticas
     */
    private void diagnosticTestsManagementMenu()
    {
        String answer = "";
        String testSerologicalResult, testResult, DNI, nurseDNI, labtechDNI;

        while(!answer.equals("Q")){
            System.out.println("\n >   GESTION DE PRUEBAS DIAGNOSTICAS");
            System.out.println("");
            if(userType == ADMIN){
                System.out.println("   Nueva prueba diagnostica               (N)");
                System.out.println("   Asignar enfermero/a a prueba           (E)");
                System.out.println("   Asignar tecnico/a a prueba             (T)");
            }

            if(userType == LABTECH){
                System.out.println("   Registrar resultados de prueba         (R)");
            }

            if(userType == NURSE){
                System.out.println("   Marcar prueba diagnostica realizada    (M)");
            }
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "N":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    Test diagnosticTestSelected = diagnosticTypeSelectionMenu();
                    LocalDate dateSelected = dateSelectionMenu();
                    diagnosticTestsManagement.createNewDiagnosticTest(usersManagement.getPatient(DNI), diagnosticTestSelected, dateSelected);
                break;
                case "E":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    System.out.print("   DNI Enfermero/a: ");
                    nurseDNI = readInput.nextLine();
                    diagnosticTestsManagement.assignNurseToDiagnostic(DNI, usersManagement.getNurse(nurseDNI));
                    usersManagement.getNurse(nurseDNI).makeATest();
                break;
                case "T":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    System.out.print("   DNI Tecnico/a: ");
                    labtechDNI = readInput.nextLine();
                    if(usersManagement.getLabTech(labtechDNI).isAvailableToMakeTest()){
                        diagnosticTestsManagement.assignLabtechToDiagnostic(DNI, usersManagement.getLabTech(labtechDNI));
                        usersManagement.getLabTech(labtechDNI).makeATest();
                        break;
                    }else{
                        System.out.println("  \n++El/la tecnico "+usersManagement.getLabTech(labtechDNI).getName()+" no puede realizar mas tests esta semana.");
                        break;
                    }
                case "M":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    diagnosticTestsManagement.setTestAsDone(DNI);
                    System.out.print("   +++ Prueba diagnostica del paciente "+usersManagement.getPatient(DNI).getName()+" marcada como realizado");
                break;
                case "R":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    if(diagnosticTestsManagement.getLastDiagnosticTestType(DNI) == DiagnosticTestType.SEROLOGICAL ){
                        System.out.print("   Introduzca el resultado del test serologico (1-10): ");
                        testSerologicalResult = readInput.nextLine();
                        diagnosticTestsManagement.setLastSerologicalTestResult(DNI, Integer.parseInt(testSerologicalResult));
                    }else{
                        testResult = "";
                        while(!(testResult.equals("P") || testResult.equals("N"))){
                            System.out.println("   \nElija el resultado de la prueba diagnostica ");
                            System.out.println("     - Positiva (P)");
                            System.out.println("     - Negativa (N)");
                            System.out.print("   Resultado: ");
                            testResult = readInput.nextLine();
                        }

                        if(testResult.equals("P")){
                            diagnosticTestsManagement.setTestResult(DNI, true);
                        }else {
                            diagnosticTestsManagement.setTestResult(DNI, false);
                        }
                    }
                break;
            }
        }
    }

    /**
     * Metodo encargado de mostrar el menu de seleccion de tipo de prueba diagnostica
     *
     * @return Test EL tipo de test seleccionado por el usuario
     */
    private Test diagnosticTypeSelectionMenu()
    {
        String answer = "";
        while(!answer.equals("Q")){
            System.out.println("\n >   SELECCION DE TIPO DE PRUEBA");
            System.out.println("   Serologica                             (S)");
            System.out.println("   PCR                                    (P)");
            System.out.println("   Antigenos Clasica                      (C)");
            System.out.println("   Antigenos Rapida                       (R)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "S":
                    return new Serological();
                case "P":
                    return new PCR();
                case "C":
                    return new PCRAntigen();
                case "R":
                    return new QuickAntigen();
            }
        }
        return null;
    }

    /**
     * Metodo encargado de mostrar un menu para inserccion de una fecha al usuario
     * ademas este metodo devuelve la fecha en tipo LocalDate
     *
     * @return LocalDate La fecha introducida por el usuario
     */
    private LocalDate dateSelectionMenu()
    {
        String day, month, year;

        System.out.println("\n >   SELECCION DE FECHAS");
        System.out.print("   Dia : ");
        day = readInput.nextLine();
        System.out.print("   Mes: ");
        month = readInput.nextLine();
        System.out.print("   Año : ");
        year = readInput.nextLine();

        return LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
    }

    /**
     * Metodo encargado de mostrar el menu para la gestion de la vacunacion
     */
    private void vaccinationManagementMenu()
    {
        String answer = "";
        String DNI, nurseDNI;

        while(!answer.equals("Q")){
            System.out.println("\n >   GESTION DE VACUNACION");
            System.out.println("");
            if(userType == ADMIN){
                System.out.println("   Nueva cita de vacunacion               (N)");
                System.out.println("   Asignar enfermero/a a cita             (E)");
            }else if(userType == NURSE){
                System.out.println("   Programar segunda dosis                (S)");
                System.out.println("   Registrar paciente vacunado            (R)");
                System.out.println("   Ver detalles vacunacion                (D)");
            }
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "S":
                case "N":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    LocalDate dateSelected = dateSelectionMenu();
                    
                    VaccineType currentVaccineAssigned = vaccinationManagement.getVaccineType(usersManagement.getPatient(DNI));

                    if(currentVaccineAssigned != null && currentVaccineAssigned == VaccineType.JOHNSON
                    && vaccinationManagement.patientIsScheduledToBeVaccinated(usersManagement.getPatient(DNI))
                    )
                    {
                        System.out.println("\n   +++ATENCION: Vacuna asignada JOHNSON. Solo se permite una dosis.");
                        break;
                    }

                    if(vaccinationManagement.isPatientVaccinated(usersManagement.getPatient(DNI))){
                        System.out.println("   +++El paciente ya esta vacunado.");
                        break;
                    }else if(usersManagement.getPatient(DNI).isAPriorityPatient()){
                        vaccinationManagement.scheduleVaccination(usersManagement.getPatient(DNI), dateSelected);
                        break;
                    }else if(vaccinationManagement.allPriorityPatientsAreVaccinated()){
                        vaccinationManagement.scheduleVaccination(usersManagement.getPatient(DNI), dateSelected);
                        break;
                    }
                    else if(!vaccinationManagement.allPriorityPatientsAreVaccinated() && vaccinationManagement.dateIsBetweenDoses(dateSelected)){
                        vaccinationManagement.scheduleVaccination(usersManagement.getPatient(DNI), dateSelected);
                        break;
                    }else if(vaccinationManagement.getClosestPriorityVaccinationSecondDate().isEqual(vaccinationManagement.getFarthestPriorityVaccinationFirstDosesDate())){
                        System.out.println("   \n+++ATENCION: Paciente no prioritario.");
                        System.out.println("   +++El paciente no puede ser vacunado");
                        break;
                    }else{
                        System.out.println("   \n+++ATENCION: Paciente no prioritario.");
                        System.out.print("   +++Solo puede ser vacunado entre las siguientes fechas: "+vaccinationManagement.getClosestPriorityVaccinationSecondDate().toString());
                        System.out.println(" -- "+vaccinationManagement.getFarthestPriorityVaccinationFirstDosesDate().toString());
                        break;
                    }
                case "E":
                    System.out.print("   DNI Enfermero/a: ");
                    nurseDNI = readInput.nextLine();
                    if(usersManagement.getNurse(nurseDNI).isAvailableToMakeTest()){
                        System.out.print("   DNI Paciente: ");
                        DNI = readInput.nextLine();
                        if(vaccinationManagement.patientIsScheduledToBeVaccinated(usersManagement.getPatient(DNI))){
                            vaccinationManagement.assignNurseToRegister(usersManagement.getPatient(DNI) , usersManagement.getNurse(nurseDNI));
                            vaccinationManagement.searchForVaccinationRegister(usersManagement.getPatient(DNI)).getDetails();
                            usersManagement.getNurse(nurseDNI).makeATest();
                        }else {
                            System.out.println("   \n+++ATENCION: No hay una fecha de vacunacion programada para el paciente.");
                            break;
                        }
                        break;
                    }else{
                        System.out.println("   \n+++ATENCION: El/la enfermero/a "+usersManagement.getNurse(nurseDNI).getName()+" no puede realizar más tests esta semana.");
                        break;
                    }
                case "R":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    Patient currentPatient = usersManagement.getPatient(DNI);
                    if(vaccinationManagement.isSecondDosesNeeded(currentPatient) && !vaccinationManagement.isFirstDosesDone(currentPatient) && !vaccinationManagement.isPatientVaccinated(currentPatient)){
                        vaccinationManagement.setFirstDosesDone(currentPatient);
                        vaccineStockManagement.useOneDoses(vaccinationManagement.getVaccineType(currentPatient));

                        System.out.println("     La primera dosis del paciente ha sido registrada.");
                    }else if(vaccinationManagement.isSecondDosesNeeded(currentPatient) && vaccinationManagement.isFirstDosesDone(currentPatient) && !vaccinationManagement.isPatientVaccinated(currentPatient)){
                        vaccinationManagement.secondDosesIsNoNeeded(currentPatient);
                        vaccinationManagement.vaccinatePatient(currentPatient);
                        vaccineStockManagement.useOneDoses(vaccinationManagement.getVaccineType(currentPatient));

                        System.out.println("     La segunda dosis del paciente ha sido registrada.");
                        System.out.println("     Paciente registrado como vacunado.");
                    }else if(!vaccinationManagement.isSecondDosesNeeded(currentPatient) && !vaccinationManagement.isFirstDosesDone(currentPatient) && !vaccinationManagement.isPatientVaccinated(currentPatient)){
                        vaccinationManagement.vaccinatePatient(currentPatient);
                        vaccineStockManagement.useOneDoses(vaccinationManagement.getVaccineType(currentPatient));

                        System.out.println("     Paciente registrado como vacunado.");
                    }
                    break;
                    case "D":
                    System.out.print("   DNI Paciente: ");
                    DNI = readInput.nextLine();
                    vaccinationManagement.searchForVaccinationRegister(usersManagement.getPatient(DNI)).getDetails();
                break;
            }
        }
    }

    /**
     * Metodo encargado de mostrar un menu al usuario para la visualizacion de todos los usuarios
     * del
     */
    private void showUsersMenu()
    {
        String answer = "";

        while(!answer.equals("Q")){
            System.out.println("\n >   VISUALIZACION DE USUARIOS");
            System.out.println("");
            System.out.println("   Visualizar usuarios                    (V)");
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                case "V":
                    System.out.println("\n\n");
                    System.out.println("      ------------ PACIENTES ------------");
                    System.out.println("      |    NOMBRE   |     DNI    | EDAD |");
                    System.out.println("      -----------------------------------");
                    usersManagement.listPatients();
                    System.out.println("\n\n");
                    System.out.println("      ---------------------- EMPLEADOS -----------------");
                    System.out.println("      |   NOMBRE   |     DNI    | EDAD |  Nº EMPLEADO  |");
                    System.out.println("      --------------------------------------------------");

                    usersManagement.listEmployees();
                    System.out.println("\n\n");
                break;
            }
        }

    }
    
     /**
      * Metodo encargado de mostrar un menu con las opciones para la gestion de pacientes
      *
      * Esta es la clase mas compleja de toda la aplicacion
      *
      * Por parametro se le pasa el DNI del usuario logueado y en la variable switchedUserType se almacena
      * de manera auxiliar el tipo de usuario logueado
      *
      * Cuando el usuario es un administrador, por defecto se le muestran todos los empleados y se le pregunta
      * por el numero de empleado por elcual quiere ver los pacientes que tiene asignados para
      * vacunacion o prueba diagnostica, de esta forma el DNI del susodicho paciente se almacena temporalmente
      * en userLoggedIn y con esta variable se llaman los metodos getPatientsByNurseDNI y getPatientsByLabTechDNI
      *
      * @param userLoggedIn DNI del usuario que esta logueado
      *
      */
    private void showPatientsMenu(String userLoggedIn)
    {
        String answer = "";
        Integer switchedUserType = userType;

        while(!answer.equals("Q")){
            System.out.println("\n >   VISUALIZACION DE PACIENTES");           
            ArrayList<String> diagnosticsDNIs = null;
            ArrayList<String> vaccinationDNIs = null;

            // Sie el tipo de usuario es un administrador mostramos todos los empleados
            if(switchedUserType == ADMIN){
                System.out.println("\n\n");
                System.out.println("      ---------------------- EMPLEADOS -----------------");
                System.out.println("      |   NOMBRE   |     DNI    | EDAD |  Nº EMPLEADO  |");
                System.out.println("      --------------------------------------------------");
                usersManagement.listEmployees();

                // Pedimos al administrador que indique el numero de empleado del que quiere
                // visualizar los pacientes
                System.out.print("\n   Para ver los pacientes asignados a un empleado. Escriba su numero de empleado: ");
                answer = readInput.nextLine();
                Employee currentEmployee = usersManagement.getEmployeeByEmployeeNumber(Integer.parseInt(answer));

                // Si el usuario seleccionado es un Nurse o LabTech asignamos el valor a la variable switchedUserType
                if(currentEmployee instanceof Nurse){
                    switchedUserType = NURSE;
                }else if(currentEmployee instanceof LabTech){
                    switchedUserType = LABTECH;                    
                }
                // obtenemos el DNI del empleado seleccionado y lo ponemos en la variable userLoggedIn
                userLoggedIn = currentEmployee.getDNI();
            }

            // Menu a mostrar si el usuario no es administrador
            System.out.println("\n\n");
            System.out.println("      ----------------- PACIENTES ---------------");
            System.out.println("      |    NOMBRE   |     DNI    | EDAD |  TIPO  |");
            System.out.println("      -------------------------------------------");

            // Si es tipo Nurse, usando el DNI del mismo obtenemos los pacientes asignados en un ArrayList
            if(switchedUserType == NURSE ){
                vaccinationDNIs = vaccinationManagement.getPatientsByNurseDNI(userLoggedIn);
                diagnosticsDNIs = diagnosticTestsManagement.getPatientsByNurseDNI(userLoggedIn);
            // si es tipo LabTech, usando el DNI del mismo obtenemos los pacientes asignados en un ArrayList
            }else if(switchedUserType == LABTECH){
                diagnosticsDNIs = diagnosticTestsManagement.getPatientsByLabTechDNI(userLoggedIn);
            }

            // Si el tipo de usuario es un tecnico de laboratio recorremos el ArrayList con DNIs y obtenemos
            // los datos de esos pacientes
            if(switchedUserType == LABTECH){
                Iterator<String> diagnosticsDNIsList = diagnosticsDNIs.iterator();
                while(diagnosticsDNIsList.hasNext()){
                    String currentDNI = diagnosticsDNIsList.next();
                    System.out.println(String.format("%7s %10s %1s %10s %1s %4s %1s %4s %1s", "|", 
                            usersManagement.getPatient(currentDNI).getName(), "|", 
                            currentDNI, "|", 
                            usersManagement.getPatient(currentDNI).getAge(), "|", 
                            "PRUEBA", "|"
                        ));
                }
            // Si el tipo de usuario es un/a enfermero/a recorremos los ArrayList con DNIs y obtenemos
            // los datos de esos pacientes
            }else if( switchedUserType == NURSE ){
                Iterator<String> vaccinationDNIsList = vaccinationDNIs.iterator();
                Iterator<String> diagnosticsDNIsList = diagnosticsDNIs.iterator();

                while(diagnosticsDNIsList.hasNext()){
                    String currentDNI = diagnosticsDNIsList.next();
                    System.out.println(String.format("%7s %10s %1s %10s %1s %4s %1s %4s %1s", "|", 
                            usersManagement.getPatient(currentDNI).getName(), "|", 
                            currentDNI, "|", 
                            usersManagement.getPatient(currentDNI).getAge(), "|", 
                            "PRUEBA", "|"
                        ));
                }

                while(vaccinationDNIsList.hasNext()){
                    String currentDNI = vaccinationDNIsList.next();
                    System.out.println(String.format("%7s %10s %1s %10s %1s %4s %1s %4s %1s", "|", 
                            usersManagement.getPatient(currentDNI).getName(), "|", 
                            currentDNI, "|", 
                            usersManagement.getPatient(currentDNI).getAge(), "|", 
                            "VACUNA", "|"
                        ));
                }
            }              

            System.out.println("\n\n");
            // Si el tipo de usuario es un tecnico de laboratorio mostramos solo el menu de pruebas
            if( switchedUserType == LABTECH ){
                System.out.println("   Visualizar fechas pruebas              (P)");
            // Si el tipo de usuario es un enfermero mostramos menu de pruebas y vacunacion
            }else{
                System.out.println("   Visualizar fechas vacunacion           (V)");
                System.out.println("   Visualizar fechas pruebas              (P)");
            }
            System.out.println("   Salir                                  (Q)");
            System.out.print("\n   Elija una opcion: ");
            answer = readInput.nextLine();

            switch(answer){
                // iteramos sobre la lista de DNIs que han sido seleccionados como pacientes asignados a el/la enfermero/a
                case "V":
                Iterator<String> vaccinationDNIsList = vaccinationDNIs.iterator();

                System.out.println("\n");
                System.out.println("      ------------------------------------------------------------------------------------------------");
                System.out.println("      |    NOMBRE   |     DNI    |  FECHA DOSIS 1  |  FECHA DOSIS 2  |     VACUNADO    |    VACUNA   |");
                System.out.println("      ------------------------------------------------------------------------------------------------");
                while(vaccinationDNIsList.hasNext()){
                    String currentDNI = vaccinationDNIsList.next();
                    Patient currentPatient = usersManagement.getPatient(currentDNI);
                    String vaccinationState = "NO";
                    
                    if(vaccinationManagement.isPatientVaccinated(currentPatient)){
                        vaccinationState = "SI";
                    }else if(!vaccinationManagement.isFirstDosesDone(currentPatient) && vaccinationManagement.isSecondDosesNeeded(currentPatient)){
                        vaccinationState = "NO";
                    }else if(vaccinationManagement.isFirstDosesDone(currentPatient) && vaccinationManagement.isSecondDosesNeeded(currentPatient)){
                        vaccinationState = "2DA DOSIS PDT.";
                    }else if(vaccinationManagement.isFirstDosesDone(currentPatient) && !vaccinationManagement.isSecondDosesNeeded(currentPatient)){
                        vaccinationState = "SI";
                    }
                    
                    if(vaccinationManagement.getVaccineType(currentPatient) == VaccineType.JOHNSON){
                        
                    }

                    System.out.println(String.format("%7s %10s %1s %10s %1s %15s %1s %15s %1s %16s %1s %10s %1s",  "|", 
                            currentPatient.getName(), "|",
                            currentDNI, "|",
                            vaccinationManagement.getFirstDosesDateAppointment(currentPatient), "|",
                            vaccinationManagement.getSecondDosesDateAppointment(currentPatient), "|",
                            vaccinationState, "|",
                            vaccinationManagement.getVaccineType(currentPatient).toString(), "|"
                        ));
                }

                break;
                // iteramos sobre la lista de DNIs que han sido seleccionados como pacientes asignados a el/la enfermero/a y/o tecnico
                case "P":
                Iterator<String> diagnosticsDNIsList = diagnosticsDNIs.iterator();
                String resultOutput = "";

                System.out.println("\n");
                System.out.println("      -----------------------------------------------------------------------------------------------------------");
                System.out.println("      |    NOMBRE   |     DNI    |      FECHA      |       TIPO        |       RESULTADO      |      ESTADO     |");
                System.out.println("      -----------------------------------------------------------------------------------------------------------");

                while(diagnosticsDNIsList.hasNext()){
                    String currentDNI = diagnosticsDNIsList.next();
                    Patient currentPatient = usersManagement.getPatient(currentDNI);

                    if(diagnosticTestsManagement.getLastDiagnosticTestType(currentDNI) == DiagnosticTestType.SEROLOGICAL ){
                        if(diagnosticTestsManagement.getLastDiagnosticResult(currentDNI)){
                            resultOutput = "POSITIVO = ";
                        }else {
                            resultOutput = "NEGATIVO = ";
                        }
                        resultOutput = resultOutput+diagnosticTestsManagement.getLastSerologicalTestResult(currentDNI);
                    }else{
                        resultOutput = diagnosticTestsManagement.getLastDiagnosticResult(currentDNI) ? "POSITIVO " : "NEGATIVO ";
                    }

                    System.out.println(String.format("%7s %10s %1s %10s %1s %15s %1s %17s %1s %20s %1s %15s %1s", "|",
                            currentPatient.getName(), "|",
                            currentDNI, "|",
                            diagnosticTestsManagement.getDiagnosticTestDate(currentDNI).toString(), "|",
                            diagnosticTestsManagement.getLastDiagnosticTestType(currentDNI).toString(), "|",
                            resultOutput, "|",
                            diagnosticTestsManagement.getTestState(currentDNI), "|"
                        ));
                }
                break;
            }
        }
    }

    /**
     * Metodo encargado de mostrar el menu para buscar usuarios en la base de datos
     */
    private void searchUserMenu()
    {
        String DNI, name, employeeNumber;
        String answer = "";
        while(!answer.equals("Q")){
            System.out.println("\n >   BUSQUEDA DE USUARIOS");
            System.out.println("   Por DNI                                (D)");
            System.out.println("   Por Numero empleado                    (E)");
            System.out.println("   Por Nombre                             (N)");
            System.out.print("   Elija una opcion: ");
            answer = readInput.nextLine();
            System.out.print("\n");
            switch(answer){
                case "D":
                    System.out.print("   DNI Usuario: ");
                    DNI = readInput.nextLine();
                    User currentUser = usersManagement.getUser(DNI);
                    if(currentUser != null ){
                        System.out.println("   Usuario encontrado.");
                        System.out.println("   - Nombre: "+currentUser.getName());
                        System.out.println("   - DNI: "+currentUser.getDNI());
                        System.out.println("   - Edad: "+currentUser.getAge());
                        if(currentUser instanceof Patient){
                            System.out.println("   - Es un paciente");
                        }else {
                            Employee currentEmploye = ( Employee )currentUser;
                            System.out.println("   - Es un empleado y su codigo es: "+currentEmploye.getEmployeeNumber());
                        }
                    }else{
                        System.out.println("   Usuario NO encontrado.");
                    }
                break;
                case "E":
                    System.out.print("  Numero empleado: ");
                    employeeNumber = readInput.nextLine();
                    Employee employeSearched = usersManagement.getEmployeeByEmployeeNumber(Integer.parseInt(employeeNumber));
                    if( employeSearched != null){
                        System.out.println("   Empleado encontrado.");
                        System.out.println("   - Nombre: "+employeSearched.getName());
                        System.out.println("   - DNI: "+employeSearched.getDNI());
                        System.out.println("   - Edad: "+employeSearched.getAge());
                        System.out.println("   - Numero empleado: "+employeSearched.getEmployeeNumber());
                        if(employeSearched instanceof Administrator){
                            System.out.println("   - Es un/a administrador/a");
                        }else if(employeSearched instanceof Nurse){
                            System.out.println("   - Es un/a enfermero/a");
                        }else if(employeSearched instanceof LabTech){
                            System.out.println("   - Es un/a tecnico/a de laboratorio");
                        }
                    }else{
                        System.out.println("   Empleado NO encontrado.");
                    }
                break;
                case "N":
                    System.out.print("  Nombre del usuario: ");
                    name = readInput.nextLine();
                    ArrayList<User> userList = usersManagement.getUserByName(name);
                    if( userList != null ){
                        System.out.println("   Usuario/s encontrado/s con el nombre "+name+":");
                        for(User currentMatchedUser : userList){
                            System.out.println("   - Nombre: "+currentMatchedUser.getName());
                            System.out.println("   - Edad: "+currentMatchedUser.getAge());
                            System.out.println("   - DNI: "+currentMatchedUser.getDNI());
                            System.out.println("\n");
                        }
                    }else{
                        System.out.println("   No se han encontrado usuarios con el nombre "+name);
                    }
                break;
            }
        }
    }

    /**
     * Metodo encargado de mostrar el menu para la gestion del stock de las vacunas
     */
    private void stockManagementMenu()
    {
        // put your code here
        String quantity;
        String answer = "";
        
        while(!answer.equals("Q")){
            System.out.println("\n >   GESTION DE STOCK DE VACUNAS\n");
            System.out.println("   Recepcionar pedido Pfizer              (P)");
            System.out.println("   Recepcionar pedido Moderna             (M)");
            System.out.println("   Recepcionar pedido Johnson             (J)");
            System.out.println("   Ver stock Pfizer                       (F)");
            System.out.println("   Ver stock Moderna                      (O)");
            System.out.println("   Ver stock Johnson                      (H)");
            
            System.out.print("   Elija una opcion: ");
            answer = readInput.nextLine();
            System.out.print("\n");
            
            
            switch(answer){
                case "P":
                    System.out.print("   Cuantas vacunas Pfizer desea recepcionar: ");
                    quantity = readInput.nextLine();
                    vaccineStockManagement.addPfizerUnits(Integer.parseInt(quantity));
                    System.out.println("   "+quantity+" vacunas han sido agregadas al stock");
                    System.out.println("   Dosis actuales disponibles: "+vaccineStockManagement.getPfizerTotalDosis());
                break;
                case "M":
                    System.out.print("   Cuantas vacunas Moderna desea recepcionar: ");
                    quantity = readInput.nextLine();
                    vaccineStockManagement.addModernaUnits(Integer.parseInt(quantity));
                    System.out.println("   "+quantity+" vacunas han sido agregadas al stock");
                    System.out.println("   Dosis actuales disponibles: "+vaccineStockManagement.getModernaTotalDosis());
                break;
                case "J":
                    System.out.print("   Cuantas vacunas Johnson desea recepcionar: ");
                    quantity = readInput.nextLine();
                    vaccineStockManagement.addJohnsonUnits(Integer.parseInt(quantity));
                    System.out.println("   "+quantity+" vacunas han sido agregadas al stock");
                    System.out.println("   Dosis actuales disponibles: "+vaccineStockManagement.getJohnsonTotalDosis());
                break;
                case "F":
                    System.out.println("   Cantidad actual de dosis: "+vaccineStockManagement.getPfizerTotalDosis());
                break;
                case "O":
                    System.out.println("   Cantidad actual de dosis: "+vaccineStockManagement.getModernaTotalDosis());
                break;
                case "H":
                    System.out.println("   Cantidad actual de dosis: "+vaccineStockManagement.getJohnsonTotalDosis());
                break;
            }
        }
    }

}
