/**
 * Interface for a list with a Point Of Interest (POI) Model: ListIPIF.
 * Operations: get, modify, insert element, remove element, 
 * 
 * @author David L. 
 * @version 1.0 - 10/03/2021
 */

public interface ListIPIF<E> extends SequenceIF<E>
{   
    /**
     * Get the Point Of Interest of the list
     */
    public int getPOI();
      
    /**
     * Modify the Point Of Interest in the list
     * @param The position of the POI
     * @pre: 0 < pos <= size()+1
     */
    public void modifyPOI(int pos);
    
    /**
     * Insert an element e in the POI
     * @pre: 0 < getPOI() <= size()
     * @post: size()+1
     */
    public void insertElement(E e);
    
    /**
     * Remove the element in the POI
     * @pre: 0 < getPOI() <= size()
     */
    public void removeElement();
}