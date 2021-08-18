/**
 * Superclase generica para usuarios
 * 
 * @author @Deividgdt
 * @version 1.0
 */
public class User
{
    private String name;
    private int age;
    private String DNI;

    /**
     * Constructor para usuarios
     */
    public User()
    {
        this.name = name;
        this.age = age;
        this.DNI = DNI;
    }

    /**
     * Constructor para usuarios
     */
    public User(String name, int age, String DNI)
    {
        this.name = name;
        this.age = age;
        this.DNI = DNI;
    }

    /**
     * Devuelve el nombre del usuario
     *
     * @return String name - Nombre del usuario
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Modifica el nombre del usuario
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Devuelve la edad del usuario
     *
     * @return int age Edad del usuario
     */
    public int getAge()
    {
        return this.age;
    }

    /**
     * Metodo encargado de asignar la edad al usuario
     *
     * @param int age La edad del paciente
     */
    public void setAge(int age)
    {
        this.age = age;
    }

    /**
     * Metodo encargado de devolver DNI del usuario
     * 
     * @return String DNI
     */
    public String getDNI()
    {
        return this.DNI;
    }

    /**
     * Metodo encargado de un DNI al usuario
     *
     * @return String DNI
     */
    public void setDNI(String DNI)
    {
        this.DNI = DNI;
    }
}
