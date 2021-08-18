package es.uned.lsi.eped.pract2020_2021;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Collection;
import es.uned.lsi.eped.DataStructures.Queue;
import es.uned.lsi.eped.DataStructures.BSTree;
import es.uned.lsi.eped.DataStructures.BSTreeIF;
import es.uned.lsi.eped.DataStructures.BSTreeIF.IteratorModes;

/**
 * Representa una cola con prioridad implementada mediante un árbol binario de búsqueda de SamePriorityQueue
 */
public class BSTPriorityQueue<E> extends Collection<E> implements PriorityQueueIF<E> {
    private int size;
    private BSTreeIF<SamePriorityQueue<E>> aBSTPriorityQueue;
    
    //LA DEFINICIÓN DE LOS ATRIBUTOS DE LA CLASE ES TAREA DE CADA ESTUDIANTE
    /**
     * Clase privada que implementa un iterador para la 
     * cola con prioridad basada en secuencia.          
     */
    public class PriorityQueueIterator implements IteratorIF<E> {
        private IteratorIF<SamePriorityQueue<E>> itBST;
        private IteratorIF<E> itSPQ; 
        private E currentE;
        
        //LA DEFINICIÓN DE LOS ATRIBUTOS DE LA CLASE ES TAREA DE CADA ESTUDIANTE
        /**
         * Constructor por defecto
         */
        protected PriorityQueueIterator(){
            itBST = aBSTPriorityQueue.iterator(IteratorModes.REVERSEORDER);
            reset();
        }
        
        /**
         * Devuelve el siguiente elemento de la iteración
         */
        public E getNext() {
            currentE = itSPQ.getNext();
            if(!itSPQ.hasNext() && itBST.hasNext()){
                itSPQ = itBST.getNext().iterator();
            }
            
            return currentE;
        }
        
        /**
         * Comprueba si queda algún elemento por iterar
         */
        public boolean hasNext() {
            if(itSPQ.hasNext()){
                return true;
            }else{
                return false;
            }
        }
        
        /**
         * Reinicia el iterador a la posición inicial
         */
        public void reset() {
            itBST.reset();
            if(itBST.hasNext()){
                itSPQ = itBST.getNext().iterator();
            }else{
                itSPQ = new Queue().iterator();
            }
        }
    }
    
    /* OPERACIONES PROPIAS DE ESTA CLASE */
    /**
     * constructor por defecto: crea cola con prioridad vacía
     */
    BSTPriorityQueue(){
        aBSTPriorityQueue = new BSTree<>();
    }
    
    /* OPERACIONES PROPIAS DE LA INTERFAZ PRIORITYQUEUEIF */
    /**
     * Devuelve el elemento más prioritario de la cola y que
     *llegó en primer lugar
     * @Pre !isEmpty()
     */
    public E getFirst() {
        IteratorIF<SamePriorityQueue<E>> it = aBSTPriorityQueue.iterator(IteratorModes.REVERSEORDER);
        return it.getNext().getFirst();
    }
    
    /**
     * Añade un elemento a la cola de acuerdo a su prioridad
     *y su orden de llegada
     */
    public void enqueue(E elem, int priority) {
        boolean elementEnqueued = false;
        IteratorIF<SamePriorityQueue<E>> it = aBSTPriorityQueue.iterator(IteratorModes.DIRECTORDER);
        
        while(it.hasNext()){
            SamePriorityQueue currentSPQ = it.getNext();
            if(currentSPQ.getPriority() == priority){
                currentSPQ.enqueue(elem);
                elementEnqueued = true;
            }
        }
        
        if(!elementEnqueued){
            SamePriorityQueue newSPQ = new SamePriorityQueue(priority);
            newSPQ.enqueue(elem);
            aBSTPriorityQueue.add(newSPQ);
        }
        
        this.size++;
    }
    
    /**
     * Elimina el elemento más prioritario y que llegó a la cola
     *en primer lugar
     * @Pre !isEmpty()
     */
    public void dequeue() {
        IteratorIF<SamePriorityQueue<E>> it = aBSTPriorityQueue.iterator(IteratorModes.REVERSEORDER);
        SamePriorityQueue currentSPQ = it.getNext();
        currentSPQ.dequeue();
        if(currentSPQ.isEmpty()){
            aBSTPriorityQueue.remove(currentSPQ);
        }
        this.size--;
    }
    
    /* OPERACIONES PROPIAS DE LA INTERFAZ SEQUENCEIF */
    /**
     * Devuelve un iterador para la cola
     */
    public IteratorIF<E> iterator() {
        return new PriorityQueueIterator();
    }
    
    /* OPERACIONES PROPIAS DE LA INTERFAZ COLLECTIONIF */
    /**
     * Devuelve el número de elementos de la cola
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Decide si la cola está vacía
     */
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    /**
     * Decide si la cola contiene el elemento dado por parámetro
     */
    public boolean contains(E e) {
        IteratorIF<SamePriorityQueue<E>> it = aBSTPriorityQueue.iterator(IteratorModes.DIRECTORDER);
        while(it.hasNext()){
            SamePriorityQueue currentSPQ = it.getNext();
            if(currentSPQ.contains(e)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Elimina todos los elementos de la cola
     */
    public void clear() {
        IteratorIF<SamePriorityQueue<E>> it = aBSTPriorityQueue.iterator(IteratorModes.DIRECTORDER);
        while(it.hasNext()){
            SamePriorityQueue currentSPQ = it.getNext();
            aBSTPriorityQueue.remove(currentSPQ);
        }
        this.size = 0;
    }
}