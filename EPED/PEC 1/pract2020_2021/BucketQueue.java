package es.uned.lsi.eped.pract2020_2021;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Collection;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;
import es.uned.lsi.eped.DataStructures.Queue;

/*Representa una cola con prioridad implementada mediante una secuencia de SamePriorityQueue*/
public class BucketQueue<E> extends Collection<E> implements PriorityQueueIF<E> {
    //LA DEFINICIÓN DE LOS ATRIBUTOS DE LA CLASE ES TAREA DE CADA ESTUDIANTE
    protected ListIF<SamePriorityQueue<E>> aBucketQueue;
    protected final int FIRSTINDEX;
    private int size;

    /**
     * Clase privada que implementa un iterador para la *
     * cola con prioridad basada en secuencia.          
     */
    public class PriorityQueueIterator implements IteratorIF<E> {

        //LA DEFINICIÓN DE LOS ATRIBUTOS DE LA CLASE ES TAREA DE CADA ESTUDIANTE
        private IteratorIF<E> itSPQ; 
        private IteratorIF<SamePriorityQueue<E>> itBQ;
        private E currentE;
        
        protected PriorityQueueIterator() {
            itBQ = aBucketQueue.iterator();
            reset();
        }

        /**
         * Devuelve el siguiente elemento de la iteración
         */
        public E getNext() {
            currentE = itSPQ.getNext();
            if (!itSPQ.hasNext() && itBQ.hasNext()) {
                itSPQ = itBQ.getNext().iterator();
            }
            
            return currentE;
        }

        /**
         * Comprueba si queda algún elemento por iterar
         */
        public boolean hasNext() {
            if (itSPQ.hasNext()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Reinicia el iterador a la posición inicial
         */
        public void reset() {
            itBQ.reset();
            if (itBQ.hasNext()) {
                itSPQ = itBQ.getNext().iterator();
            } else {
                itSPQ = new Queue<E>().iterator();
            }
        }
    }

    /* OPERACIONES PROPIAS DE ESTA CLASE */
    /**
     * constructor por defecto: crea cola con prioridad vacía
     */
    BucketQueue(){
        aBucketQueue = new List<>();
        this.size = 0;
        FIRSTINDEX = 1;
    }
  
    /* OPERACIONES PROPIAS DE LA INTERFAZ PRIORITYQUEUEIF */
    /**
     * Devuelve el elemento más prioritario de la cola y que
     * llegó en primer lugar
     * @Pre !isEmpty()
     */
    public E getFirst() {
        // extraer el primer elemento
        return aBucketQueue.get(FIRSTINDEX).getFirst();
    }

    /**
     * Añade un elemento a la cola de acuerdo a su prioridad
     *y su orden de llegada
     */
    public void enqueue(E elem, int priority){
        boolean elementEnqueued = false;
        Integer BucketQueueIndexToEnqueue = null;
        IteratorIF<SamePriorityQueue<E>> it = this.aBucketQueue.iterator();
        int itIndex = 0;
        
        while(it.hasNext()){
            SamePriorityQueue currentSamePriorityQueue = it.getNext();
            int currentSPQPriority= currentSamePriorityQueue.getPriority();
            if(currentSPQPriority == priority){
                currentSamePriorityQueue.enqueue(elem);
                elementEnqueued = true;
                break;
            }
                
            if(priority > currentSPQPriority){
                BucketQueueIndexToEnqueue = itIndex;           
                break;
            }      
            itIndex++;
        }
                        
        if(!elementEnqueued){
            SamePriorityQueue<E> newSamePriorityQueue = new SamePriorityQueue<>(priority);
            newSamePriorityQueue.enqueue(elem);
                    
            aBucketQueue.insert(itIndex+1, newSamePriorityQueue);
            
            elementEnqueued = true;
        }
        
        this.size++;
    }

    /**
     * Elimina el elemento más prioritario y que llegó a la cola
     * en primer lugar
     * @Pre !isEmpty()
     */
    public void dequeue() {
        IteratorIF<SamePriorityQueue<E>> it = this.aBucketQueue.iterator();
        SamePriorityQueue firstSPQ = it.getNext();
        
        firstSPQ.dequeue();
        if(firstSPQ.isEmpty()){
            aBucketQueue.remove(FIRSTINDEX);
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
        for(int bucketQueueIndex=1;bucketQueueIndex<=aBucketQueue.size();bucketQueueIndex++){
            if(aBucketQueue.get(bucketQueueIndex).contains(e)){
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina todos los elementos de la cola
     */
    public void clear() {
        int BucketQueueSize = aBucketQueue.size();
        for(int bucketQueueIndex=1;bucketQueueIndex<=BucketQueueSize;bucketQueueIndex++){
            aBucketQueue.remove(FIRSTINDEX);
        }
        this.size=0;
    }
}
