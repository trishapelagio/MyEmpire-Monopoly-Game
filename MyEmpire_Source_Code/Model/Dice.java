package Model;
import java.util.Random;

/**
 * The <code>Dice</code> class provides methods for the game to roll a dice by generating random values
 */

public class Dice {
    /**
     * Creates <code>Dice</code> object
     */
    public Dice () {

    }

    /**
     * Randomizes a number between 1 to  6 inclusively to act as a dice
     * @return randomly generated number between 1 to 6 inclusively
     */
    public int roll() {
        Random number = new Random();
        return number.nextInt(6)+1;
    }
}