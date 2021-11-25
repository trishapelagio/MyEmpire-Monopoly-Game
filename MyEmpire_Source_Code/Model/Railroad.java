package Model;
/**
 * This class implements several methods and properties that display the behaviors of a <code>Railroad</code> by extending the <code>Space</code> class
 */

public class Railroad extends Space{
  private Player owner;
  private double price;
  private double chancePercent = 1;

  /**
   * Produces a <code>Railroad</code> object through a <code>String</code> parameter as the name, and intializes the price property to 200.
   * @param name : Name of Railroad
   */
  public Railroad (String name) {
    super(name, "Railroad");
    price = 200;
  }

    /**
     * Returns the total price for rent of the <code>Railroad</code>. This is obtained by multiplying actual price with the additional multipliers.
     * @return rent price of the railroad
     */
  public double getPrice() {
    return price * chancePercent;
  }

    /**
     * Returns the <code>Player</code> object that bought the <code>Railroad</code> object
     * @return owner of the <code>Railroad</code> specified in the object
     */

  public Player getOwner() {
    return owner;
  }

    /**
     * Sets the price property of the <code>Railroad</code> object with the specified parameter
     * @param amount value to be assigned
     */
  public void updatePrice(double amount) {
    price = amount;
  }

    /**
     * Designates a <code>Player</code> object as the value of the owner parameter
     * @param player <code>Player</code> object to be assigned as the owner
     */
  public void setOwner(Player player) {
    owner = player;
  }


    /**
     * Identifies if the <code>Utility</code> object is owned by a <code>Player</code>. If the value is not null, the function returns true. Otherwise, it returns the value of false.
     * @return true if owner property has an owner of class <code>Player</code>
     */
  public boolean isOwned() {
      return owner != null;
  }


    /**
     * Assigns the added multiplier obtained and implemented by chance cards
     * @param percentage value multiplier to be added
     */
  public void setChancePercent(double percentage)
  {
    chancePercent += percentage;

  }

  @Override
  public String toString()
  {
    return "" + super.getName() + " : Rent = " +getPrice();
  }

}


