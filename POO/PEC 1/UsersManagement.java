  

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Clase encargada de la gestion de los usuarios: pacientes y empleados.
 * Instancia una estructura de datos de tipo HashMap donde almacena objetos
 * de tipo User como valor, como clave se usa el DNI de cada usuario
 * 
 * @author @Deividgdt
 * @version 1.0
 */
public class UsersManagement
{
    private Map<String, User> usersDatabase;

    /**
     * Constructor para objetos de la clase UsersManagement
     * Instancia un HashMap con clave: DNI - Valor: User
     */
    public UsersManagement()
    {
       usersDatabase = new HashMap<>();
    }

    /**
     * Metodo encargado de crear un paciente nuevo. 
     * Lo añade al HashMap de usuarios UsersDatabase
     * 
     * @param  name   El nombre del paciente
     * @param  age La edad del paciente
     * @param  DNI El DNI del paciente
     */
    public void createNewPatient(String name, int age, String DNI)
    {
        Patient newPatient = new Patient(name, age, DNI); 
        usersDatabase.put(DNI, newPatient);
    }

    /**
     * Metodo encargado de crear un empleado nuevo
     * Lo añade al HashMap de usuarios UsersDatabase
     * Dependiendo del tipo de usuario pasado por el argumento type
     * se instancia un tipo de usuario: admin, nurse o labtech
     * 
     * @param type Tipo de empleado. 1 Admin, 2 Enfermero, 3 tecnico
     * @param name Nombre del empleado
     * @param age Edad del empleado
     * @param DNI El DNI del empleado
     * @param employeeNumber El numero identificador del empleado
     */
    public void createNewEmployee(int type, String name, int age, String DNI, int employeeNumber)
    {
        switch(type){
            case 1:
            Administrator newAdmin = new Administrator(name, age, DNI, employeeNumber);
            usersDatabase.put(DNI, newAdmin);
            break;
            case 2:
            Nurse newNurse = new Nurse(name, age, DNI, employeeNumber);
            usersDatabase.put(DNI, newNurse);
            break;
            case 3:
            LabTech newLabTech = new LabTech(name, age, DNI, employeeNumber);
            usersDatabase.put(DNI, newLabTech);
            break;
            default:
            break;
        }
    }

    /**
     * Metodo encargado de eliminar un usuario de la base de datos
     * Simplemente se usa el metodo remove del HashMap pasandole por argumento el DNI del usuario
     *
     * @param DNI El DNI del usuario a eliminar
     */
    public void deleteUserFromDatabase(String DNI)
    {
        usersDatabase.remove(DNI);
    }

    /**
     * Metodo encargado de modificar el nombre de un usuario. 
     * Se obtiene el objeto de tipo Usuario usando como clave su DNI y se
     * llama al metodo setName del mismo para establecer el nuevo nombre
     *
     * @param DNI El DNI del usuario a modificarle el nombre
     * @param newName El nuevo nombre del usuario
     */
    public void modifyUserName(String DNI, String newName)
    {
        usersDatabase.get(DNI).setName(newName);
    }

    /**
     * Metodo encargado de modificar el DNI de un usuario
     * Para poder realizar esta tarea, sabiendo que el DNI es la clave en el HashMap
     * primero se obtiene el objeto tipo usuario usando el currentDNI y se llama al metodo
     * setDNI, a continuación se inserta un nuevo elemento en el HashMap, en este caso como clave
     * usamos el newDNI y como valor usamos usersDatabase.remove(currentDNI) de esta forma a la vez
     * que eliminamos el objeto con el DNI antiguo (clave antigua) lo inserta con el DNI nuevo.
     *
     * @param currentDNI El DNI del usuario actual
     * @param newDNI El DNI nuevo que tendrá el usuario
     */
    public void modifyUserDNI(String currentDNI, String newDNI)
    {
        usersDatabase.get(currentDNI).setDNI(newDNI);
        usersDatabase.put(newDNI, usersDatabase.remove(currentDNI));
    }

    /**
     * Metodo encargado de modificar la edad de un usuario.
     *
     * @param DNI El DNI del usuario a modificar la edad
     * @param newAge La edad nueva del paciente
     */
    public void modifyUserAge(String DNI, int newAge)
    {
        usersDatabase.get(DNI).setAge(newAge);
    }

    /**
     * Metodo encargado de modificar el numero de empleado de un usuario
     * 
     * Al ser el campo employeeNumber unico de la clase Employee hay que realizar un casting del objeto
     * usuario obtenido del HashMap para que se pueda llamar al metodo setNewEmployeeNumber que es el encargado
     * de modificar el numero del empleado
     *
     * @param DNI El DNI del usuario a modificar el numero de empleado
     * @param newEmployeeNumber El numero de empleado nuevo a asignar al usuario
     */
    public void modifyEmployeeNumber(String DNI, int newEmployeeNumber)
    {
        Employee userToModify = ( Employee )usersDatabase.get(DNI);
        userToModify.setEmployeeNumber(newEmployeeNumber);
    }

    /**
     * Metodo encargado de comprar si existe un usuario.
     * Se usa el metodo get del HashMap pasando como argumento la clave, es decir el DNI
     * Si la variable que almacena el usuario userToCheck no es nula decimos que el usuario existe
     * Si es nula, el usuario no existe
     *
     * @param DNI El DNI del usuario a buscar
     * @return boolean true == existe el usuario || false == no existe el usuario
     */
    public boolean existUser(String DNI)
    {
        User userToCheck = usersDatabase.get(DNI);
        if(userToCheck != null){
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de obtener el nombre de un usuario
     *
     * @param  DNI El DNI del usuario 
     * @return String name El nombre del usuario
     */
    public String getUserFullname(String DNI)
    {
        return usersDatabase.get(DNI).getName();
    }

    /**
     * Metodo encargado de obtener la edad de un usuario
     *
     * @param DNI El DNI del usuario a buscar  
     * @return int age La edad del usuario
     */
    public int getUserAge(String DNI)
    {
        return usersDatabase.get(DNI).getAge();
    }

    /**
     * Metodo encargado de obtener el numero de empleado
     * 
     * El campo employeeNumber es unico de la clase Employee por lo que se tiene
     * que hacer un casting del tipo User a tipo Employee para poder llamar al 
     * metodo getEmployeeNumber()
     *
     * @param DNI El DNI del usuario 
     * @return int EmployeeNumber El numero del empleado
     */
    public int getEmployeeNumber(String DNI)
    {
        Employee employeeToGet = ( Employee )usersDatabase.get(DNI);
        return employeeToGet.getEmployeeNumber();
    }

    /**
     * Metodo encargado de devolver el objeto usuario
     *
     * @param DNI El DNI del usuario
     * @return User El objeto User
     */
    public User getUser(String DNI)
    {
        return usersDatabase.get(DNI);
    }

    /**
     * Metodo encargado de devolver un objeto tipo Nurse
     * Se obtiene el objeto tipo User y se hace un casting a tipo Nurse
     * y se devuelve un objeto tipo Nurse
     *
     * @param DNI El DNI del usuario
     * @return Nurse El objeto Nurse
     */
    public Nurse getNurse(String DNI)
    {
        Nurse NurseToGet = ( Nurse )usersDatabase.get(DNI);
        return NurseToGet;
    }

    /**
     * Metodo encargado de devolver un objeto tipo LabTech
     * Se obtiene el objeto tipo User y se hace un casting a tipo LabTech
     *
     * @param DNI El DNI del usuario
     * @return LabTech El objeto LabTech
     */
    public LabTech getLabTech(String DNI)
    {
        LabTech labTechToGet = ( LabTech )usersDatabase.get(DNI);
        return labTechToGet;
    }

    /**
     * Metodo encargado de devolver un objeto tipo Patient
     * Se obtiene el objetipo tipo User y se hace un casting a tipo Patient
     *
     * @param DNI El DNI del usuario
     * @return Patient El objeto Patient
     */
    public Patient getPatient(String DNI)
    {
        Patient patientToGet = ( Patient )usersDatabase.get(DNI);
        return patientToGet;
    }

    /**
     * Metodo encargado de realizar una busqueda por employeeNumber en el HashMap
     * usersDatabase y devolver un objeto tipo Employee.
     * 
     * Para hacer la busqueda se recorre el HashMap y por cada objeto tipo User localizado
     * se comprueba si es de tipo Employee, si lo es se hace un casting y se llama al metodo
     * getEmployeeNumber(), si este valor es igual al buscado devolvemos este objeto Employee
     *
     * @param  searchedEmployeeNumber El numero de empleado buscado
     * @return Employeee El objeto tipo Employee buscado || null = Empleado no existe
     */
    public Employee getEmployeeByEmployeeNumber(Integer searchedEmployeeNumber)
    {
        for(Map.Entry<String, User> user : usersDatabase.entrySet()){
            User currentUser = user.getValue();
            if( currentUser instanceof Employee){
                Employee currentEmployee = ( Employee )currentUser;
                if(currentEmployee.getEmployeeNumber() == searchedEmployeeNumber){
                    return currentEmployee;
                }
            }
        }
        return null;
    }

    /**
     * Metodo encargado de realizar una busqueda por name en el HashMap
     * usersDatabase y devolver un ArrayList con objeto/s tipo Patient
     * 
     * Para hacer la busqueda se recorre el HashMap y por cada objeto tipo User localizado
     * se comprueba si el nombre devuelvo por el metodo getName() contiene parte de la cadena
     * pasada por argumento searchedNamed, si coincide se añade al ArrayList userListMatchName
     *
     * @param  searchedNamed El nombre del usuario buscado
     * @return ArrayList La lista de usuarios que coinciden con el nombre
     */
    public ArrayList<User> getUserByName(String searchedNamed)
    {
        ArrayList<User> userListMatchName = new ArrayList<>();
        
        for(Map.Entry<String, User> user : usersDatabase.entrySet()){
            if(user.getValue().getName().contains(searchedNamed)){
                userListMatchName.add(user.getValue());
            }
        }
        
        return userListMatchName;
    }


    /**
     * Metodo encargado de imprimir una lista de todos los pacientes de la base de datos
     */
    public void listPatients()
    {
        for(Map.Entry<String, User> user : usersDatabase.entrySet()){
            if(user.getValue() instanceof Patient){
                System.out.println(String.format("%7s %10s %1s %10s %1s %4s %1s", "|",
                        user.getValue().getName(), "|", 
                        user.getValue().getDNI(), "|", 
                        user.getValue().getAge(), "|" 
                    ));
            }
        }
    }

    /**
     * Metodo encargado de imprimir una lista de todos los empleados de la base de datos
     */
    public void listEmployees()
    {
        for(Map.Entry<String, User> user : usersDatabase.entrySet()){
            if(user.getValue() instanceof Employee){
                Employee employeeToGet = ( Employee )user.getValue();
                System.out.println(String.format("%7s %10s %1s %10s %1s %4s %1s %13s %1s", "|", 
                                    employeeToGet.getName(), "|", 
                                    employeeToGet.getDNI(), "|", 
                                    employeeToGet.getAge(), "|", 
                                    employeeToGet.getEmployeeNumber(), "|"
                                    ));
            }
        }
    }

}
