import java.util.*;

/**
 * Model a 1D elementary cellular automaton.
 * 
 */
public class Automaton
{
    // The number of cells.
    private final int numberOfCells;
    // The state of the cells.
    private int[] state;
    // The state table encoding the next-state changes.
    private int[] stateTable;
    // The bitsValues table
    private final int[] bitsValues;
    

    /**
     * Create a 1D automaton consisting of the given number of cells.
     * @param numberOfCells The number of cells in the automaton.
     */
    public Automaton(int numberOfCells, int wolframCode)
    {
        this.numberOfCells = numberOfCells;
        // The bits value table
        bitsValues = new int[] {128,64,32,16,8,4,2,1,};
        // Allow an extra element to avoid 'fencepost' errors.
        state = new int[numberOfCells + 1];
        stateTable = convertWolframCode(wolframCode);
        /*stateTable = new int[] {
            //0, 1, 0, 0, 1, 0, 0, 1, // Wolfram code 146
            1, 0, 1, 1, 0, 1, 1, 1, // Wolfram code 62
        };*/
        // Seed the automaton with a single 'on' cell.
        state[numberOfCells / 2] = 1;
    }

    /**
     * Print the current state of the automaton.
     */
    public void print()
    {
        for(int cellValue : state) {
            System.out.print(cellValue == 1 ? "*" : " ");
        }
        System.out.println();
    }   

    /**
     * Update the automaton to its next state.
     */
    public void update()
    {
        // Build the new state in a separate array.
        int[] nextState = new int[state.length];
        // Use 0 for the non-existent value to the left of
        // the first cell.
        int left = 0;
        int center = state[0];
        for(int i = 0; i < numberOfCells; i++) {
            int right = state[i + 1];
            nextState[i] = calculateNextState(left, center, right);
            left = center;
            center = right;
        }
        state = nextState;
    }

    /**
     * Reset the automaton.
     */
    public void reset()
    {
        Arrays.fill(state, 0);
        // Seed the automaton with a single 'on' cell in the middle.
        state[numberOfCells / 2] = 1;
    }

    /**
     * Calculate the next state of the center cell
     * given current left, center and right cell
     * values.
     * This implements Wolfram code 110.
     * @see https://en.wikipedia.org/wiki/Wolfram_code
     * @param left The state of the cell to the left of center.
     * @param center The state of the center cell.
     * @param right The state of the cell to the right of center.
     * @return The new value of center (0 or 1).
     */
    private int calculateNextState(int left, int center, int right)
    {
        return stateTable[encodeTriplet(left, center, right)];
    }

    /**
     * Encode the 1/0 triplet (left, center, right) as an
     * integer value in the range 0-7.
     * @param left The state of the cell to the left of center (0 or 1).
     * @param center The state of the center cell (0 or 1).
     * @param right The state of the cell to the right of center (0 or 1).
     * @return (left,center,right) interpreted as a 3-bit value.
     */
    private int encodeTriplet(int left, int center, int right)
    {
        return left * 4 + center * 2 + right;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public int[] convertWolframCode(int wolframCode)
    {
        int[] stateTable = new int[8];
        int aux = wolframCode;
        
        for(int i=0;i<bitsValues.length;i++){
            if(aux >= bitsValues[i]){
                aux = aux - bitsValues[i];
                stateTable[7-i] = 1;
            }else{
                stateTable[7-i] = 0;
            } 
        }
                        
        return stateTable;
    }

}
