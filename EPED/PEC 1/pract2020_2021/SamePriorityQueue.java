package es.uned.lsi.eped.pract2020_2021;

import es.uned.lsi.eped.DataStructures.QueueIF;
import es.uned.lsi.eped.DataStructures.Queue;
import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Sequence;

/*Representa una cola con un nivel de prioridad asignado determinado*/
public class SamePriorityQueue<E> implements QueueIF<E>,Comparable<SamePriorityQueue<E>>{
     
    //LA DEFINICIÓN DE LOS ATRIBUTOS DE LA CLASE ES TAREA DE CADA ESTUDIANTE
    private Integer priority;
    private QueueIF<E> priorityQueue; 
    /* OPERACIONES PROPIAS DE ESTA CLASE */
    
    /*constructor por defecto: crea cola vacía con la prioridad dada por parámetro.
    *@param priority: nivel de prioridad asociado a la cola
    */
    SamePriorityQueue(int p){ 
        this.priority = p;
        priorityQueue = new Queue<E>();
    }
    
    /* Devuelve la prioridad de la cola
    * @return this.priority prioridad de la cola
    */
    public int getPriority(){
        return this.priority;  
    }
    
    /* OPERACIONES PROPIAS DE QUEUEIF */
    /*Devuelve el primer elemento de la cola
    * @Pre !isEmpty()
    */
    public E getFirst() {
        if(!priorityQueue.isEmpty()){
            return priorityQueue.getFirst();
        }
        return null;
    }
    
    /*Añade un elemento a la cola de acuerdo al orden de llegada*/
    public void enqueue(E elem) {
        priorityQueue.enqueue(elem);
    }
    
    /*Elimina un elemento a la cola de acuerdo al orden de llegada
     * @Pre !isEmpty()
    */
    public void dequeue() {
        priorityQueue.dequeue();
    }
    
    /* OPERACIONES PROPIAS DEL INTERFAZ SEQUENCEIF */
    
    /*Devuelve un iterador para la cola*/
    public IteratorIF<E> iterator() {
        return priorityQueue.iterator();
    }
    
    /* OPERACIONES PROPIAS DEL INTERFAZ COLLECTIONIF */
    /*Devuelve el número de elementos de la cola*/
    public int size() { 
        return priorityQueue.size();
    }
    
    /*Decide si la cola está vacía*/
    public boolean isEmpty() { 
        return priorityQueue.isEmpty();
    }
    
    /*Decide si la cola contiene el elemento dado por parámetro*/
    public boolean contains(E e) { 
        return priorityQueue.contains(e);
    }
    
    /*Elimina todos los elementos de la cola*/
    public void clear() {
        priorityQueue.clear();
    }
    
    /* OPERACIONES PROPIAS DEL INTERFAZ COMPARABLE */
    /*Comparación entre colas según su prioridad
     * Salida:
     *  - Valor >0 si la cola tiene mayor prioridad que la cola dada por parámetro
     *  - Valor 0 si ambas colas tienen la misma prioridad
     *  - Valor <0 si la cola tiene menor prioridad que la cola dada por parámetro
     */
    public int compareTo(SamePriorityQueue<E> o) { 
        return this.priority.compareTo(o.priority);
    }

}

