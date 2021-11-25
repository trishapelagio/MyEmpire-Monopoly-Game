package Cards;

/**
 * This class extends Category5 to denote cards that update
 * the chance percent of a space (Property, Utility or Railroqd)
 */
public abstract class Category5C extends Category5 {
    private double amount;

    /**
     * This initializes a Card of Category5C with a given name
     * and amount (chance percent)
     * @param name name of the card
     * @param amount chance percent of the card
     */
    public Category5C(String name, double amount) {
        super(name);
        this.amount = amount;
    }

    /**
     * This will return the value to which the player's space property
     * will be increased.
     * @return chance percent of the card
     */
    public double getAmount() {
        return amount;
    }

}
