package es.uned.lsi.eped.pract2020_2021;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class BSTPriorityQueueTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BSTPriorityQueueTest
{
    private es.uned.lsi.eped.pract2020_2021.BSTPriorityQueue<String> bSTPrior;

    /**
     * Default constructor for test class BSTPriorityQueueTest
     */
    public BSTPriorityQueueTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        bSTPrior = new es.uned.lsi.eped.pract2020_2021.BSTPriorityQueue<String>();
        bSTPrior.enqueue("7a", 7);
        bSTPrior.enqueue("3a", 3);
        bSTPrior.enqueue("10a", 10);
        bSTPrior.enqueue("9a", 9);
        bSTPrior.enqueue("8a", 8);
        bSTPrior.enqueue("4a", 4);
        bSTPrior.enqueue("5a", 5);
        bSTPrior.enqueue("2a", 2);
        bSTPrior.enqueue("1a", 1);
        bSTPrior.enqueue("9b", 9);
        bSTPrior.enqueue("6a", 6);
        bSTPrior.enqueue("7b", 7);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
