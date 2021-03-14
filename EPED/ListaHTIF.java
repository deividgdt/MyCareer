
/**
 * Interface definition for a Head-Tail Model List: LISTHTIF
 * Operations: getHead, getTail, set, insert, delete
 * @author David L.
 * @version 1.1 11/03/2021
 */

public interface ListaHTIF<E> extends SequenceIF<E>
{
    /**
     * Get the List Head
     * @pre: !isEmpty()
     */
    public E getHead();
    
    /**
     * Get the List Tail (Other ListaHTIF)
     * @pre: !isEmpty()
     */
    public ListaHTIF<E> getTail();
    
    /**
     * Set or modify the List Head
     * @param: e New value for the List Head
     * @return: The List with the modification
     */
    public ListaHTIF<E> set(E e);
    
    /**
     * Insert a new element in the List Head
     * @param: e The value to be inserted
     * @return: The List with the new value as Head
     */
    public ListaHTIF<E> insert(E e);
    
    /**
     * Delete the List Head, the List Tail becomes the new List Head
     * @pre: !isEmpty()
     * @post: size()-1 
     * @post: head=tail
     * @return: A new list where the Tail becomes the Head
     */
    
    public ListaHTIF<E> deleteHead();
}
