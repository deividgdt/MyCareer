import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de la gestion del stock de las vacunas
 * Almacena las unidades de cada tipo de vacuna por separado en una Lista de objetos de cada tipo
 * Posee diferentes metodos getter y setter para gestionar el stock
 * 
 * @author @Deividgdt 
 * @version 0.8
 */
public class VaccineStockManagement
{
    private List<Pfizer> pfizerStockDB;
    private List<Moderna> modernaStockDB;
    private List<Johnson> johnsonStockDB;
    private int FIRSTVACCINE;

    /**
     * Constructor para objetos de la clase. Crea tres bases de datos, una para cada tipo de vacuna.
     */
    public VaccineStockManagement()
    {
        FIRSTVACCINE = 0;
        pfizerStockDB = new ArrayList<>();
        modernaStockDB = new ArrayList<>();
        johnsonStockDB = new ArrayList<>();
    }

    /**
     * Metodo encargado de agregar unidades al stock de Pfizer
     * 
     * @param  pfizerUnitsToInsert Numero de unidades Pfizer a insertar
     */
    public void addPfizerUnits(int pfizerUnitsToInsert)
    {
        for(int unit = 0; unit<pfizerUnitsToInsert; unit++){
            Pfizer newPfizerVaccine = new Pfizer();
            pfizerStockDB.add(newPfizerVaccine);
        }
    }

    /**
     * Metodo encargado de agregar unidades al stock de Moderna
     * 
     * @param  modernaUnitsToInsert Numero de unidades Moderna a insertar
     */
    public void addModernaUnits(int modernaUnitsToInsert)
    {
        for(int unit = 0; unit<modernaUnitsToInsert; unit++){
            Moderna newModernaVaccine = new Moderna();
            modernaStockDB.add(newModernaVaccine);
        }
    }

    /**
     * Metodo encargado de agregar unidades al stock de Johnson
     * 
     * @param  johnsonUnitsToInsert Numero de unidades Johnson a insertar
     */
    public void addJohnsonUnits(int johnsonUnitsToInsert)
    {
        for(int unit = 0; unit<johnsonUnitsToInsert; unit++){
            Johnson newJohnsonVaccine = new Johnson();
            johnsonStockDB.add(newJohnsonVaccine);
        }
    }

    /**
     * Metodo encargado de usar una dosis de cada tipo en base al tipo de vacuna pasado por parametro
     *
     * @param vaccineType Tipo enumerado vaccineType
     */
    public void useOneDoses(VaccineType vaccineType)
    {
        if(VaccineType.JOHNSON == vaccineType){
            useJohnsonDosis();
        }
        else if(VaccineType.MODERNA == vaccineType){
            useModernaDosis();
        }
        else if(VaccineType.PFIZER == vaccineType){
            usePfizerDosis();
        }
    }

    /**
     * Metodo encargado de usar una dosis de Johnson del stock
     *
     */
    public void useJohnsonDosis()
    {
        boolean firstVaccineIsEmpty;
        johnsonStockDB.get(FIRSTVACCINE).useOneDosis();

        firstVaccineIsEmpty = johnsonStockDB.get(FIRSTVACCINE).isEmpty();
        if(firstVaccineIsEmpty){
            johnsonStockDB.remove(FIRSTVACCINE);
        }
    }

    /**
     * Metodo encargado de usar una dosis de Pfizer del stock
     */
    public void usePfizerDosis()
    {
        boolean firstVaccineIsEmpty;
        pfizerStockDB.get(FIRSTVACCINE).useOneDosis();

        firstVaccineIsEmpty = pfizerStockDB.get(FIRSTVACCINE).isEmpty();
        if(firstVaccineIsEmpty){
            pfizerStockDB.remove(FIRSTVACCINE);
        }
    }

    /**
     * Metodo encargado de usar una dosis de Moderna del stock
     */
    public void useModernaDosis()
    {
        boolean firstVaccineIsEmpty;
        modernaStockDB.get(FIRSTVACCINE).useOneDosis();

        firstVaccineIsEmpty = modernaStockDB.get(FIRSTVACCINE).isEmpty();
        if(firstVaccineIsEmpty){
            modernaStockDB.remove(FIRSTVACCINE);
        }
    }

    /**
     * Metodo que devuelve la cantidad de dosis Pfizer disponibles en el stock
     *
     * @return  int Numero de dosis disponibles
     */
    public int getPfizerTotalDosis()
    {
        if(pfizerStockDB.isEmpty()){
            return 0;
        }

        if (pfizerStockDB.get(FIRSTVACCINE).getNumberOfDosisLeft() == 1){
            return (pfizerStockDB.size()*2)-1;
        }else{
            return pfizerStockDB.size()*2;
        }
    }

    /**
     * Metodo que devuelve la cantidad de dosis Moderna disponibles en el stock
     *
     * @return int Numero de dosis disponibles
     */
    public int getModernaTotalDosis()
    {
        if(modernaStockDB.isEmpty()){
            return 0;
        }

        if (modernaStockDB.get(FIRSTVACCINE).getNumberOfDosisLeft() == 1){
            return (modernaStockDB.size()*2)-1;
        }else{
            return modernaStockDB.size()*2;
        }
    }

    /**
     * * Metodo que devuelve la cantidad de dosis Johnson disponibles en el stock
     *
     * @return   int Numero de dosis disponibles
     */
    public int getJohnsonTotalDosis()
    {
        return johnsonStockDB.size();
    }
}
