
/**
 * Clase principal Clinica que llama instancia una clase MainMenu y llama al metodo LoginUser
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class Clinica
{   
    public static void main (String[] args){
        MainMenu mainMenu= new MainMenu();
        mainMenu.loginUser();
    }
}
