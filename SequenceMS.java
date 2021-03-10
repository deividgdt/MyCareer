
/**
 * Interface to set a maximum size on Sequences. Extends SequenceIF.
 * Operations: setMS, unsetMS, getMS
 * 
 * @author David L. 
 * @version 1.0 - 10/03/2021
 */

public interface SequenceMS<E> extends SequenceIF<E>
{
    /**
     * Set a maximum number (MSValue) of elements that the list can contain.
     * The MSValue must be (greater than || equal to) the number of elements in the list : size()
     * @pre: MSValue >= 0
     * @pre: MSValue >= size()
     */
    public void setMS(int MSValue);
    
    /**
     * Unset the maximum number of elements that the list can contain. 
     * Is mandatory to set a Maximum Size first before call this method.
     * @pre: setMS(int MSValue)
     */
    public void unsetMS();
    
    /**
     * Get the actual maximum size set. 
     * Is mandatory to set a Maximum Size first before call this method.
     * @pre: setMS(int MSValue)
     * @return: MSValue
     */
    public int getMS();
    
    /**
     * Return True if the number of elements is equal to the Maximum Size set. 
     * Return False if not.
     * Is mandatory to set a Maximum Size first before call this method.
     * @pre: setMS(int MSValue)
     * @return: True if getMS() == size()
     */
    public boolean isFull();
}
