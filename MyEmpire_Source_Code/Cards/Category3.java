package Cards;
import Model.*;

/**
 * This category extends Cards class which is a category which has the implementation of
 * giving money to the player.
 */

public abstract class Category3 extends Card{
    private double amount;

    /**
     * This constructor initializes a card of type Category3
     * @param amount amount to be given to the player
     * @param name name of the card
     */
    public Category3(double amount, String name) {
        super(name);
        this.amount = amount;
    }

    /**
     * Returns the amount to be given to player
     * @return amount to be given to the player
     */
    public double getAmount() {
        return amount;
    }
}
