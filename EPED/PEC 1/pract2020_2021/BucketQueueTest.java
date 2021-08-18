package es.uned.lsi.eped.pract2020_2021;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class BucketQueueTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BucketQueueTest
{
    private es.uned.lsi.eped.pract2020_2021.BucketQueue<String> bucketQu1;
    /**
     * Default constructor for test class BucketQueueTest
     */
    public BucketQueueTest()
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
        bucketQu1 = new es.uned.lsi.eped.pract2020_2021.BucketQueue<String>();
        bucketQu1.enqueue("8E", 8);
        bucketQu1.enqueue("1D", 1);
        bucketQu1.enqueue("3D", 3);
        bucketQu1.enqueue("10G", 10);
        bucketQu1.enqueue("4D", 4);
        bucketQu1.enqueue("7A", 7);
        bucketQu1.enqueue("2A", 2);
        bucketQu1.enqueue("7B", 7);
        bucketQu1.enqueue("2B", 2);
        bucketQu1.enqueue("7C", 7);
        bucketQu1.enqueue("2C", 2);
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

    @Test
    public void DequeueAll()
    {
        assertEquals(30, bucketQu1.size());
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        bucketQu1.dequeue();
        assertEquals(0, bucketQu1.size());
    }
}

