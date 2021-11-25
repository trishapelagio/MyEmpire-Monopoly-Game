package Model;
/**
 * This class extends the <code>Space</code> class for implementing methods and properties to indicate the behaviors of a <code>Utility</code>.
 */
public class Utility extends Space{
  private Player owner;
  private double price;
  private double chancePercent=1;

  /**
   * Initializes a Utility object with given name
   * @param name : name of the utility object
   */
  public Utility(String name)
  {
    super(name, "Utility");
    price = 150;
  }

    /**
     * Returns the price property of the <code>Utility</code> object
     * @return the price
     */
  public double getPrice()
  {
    return price*chancePercent;
  }


    /**
     * Identifies if the <code>Utility</code> object is owned by a <code>Player</code>. If the value is not null, the function returns true. Otherwise, it returns the value of false.
     * @return true if owner property has an owner of class <code>Player</code>
     */
  public boolean isOwned()
  {
      return owner != null;
  }


  /**
   * Returns the String color of the <code>Utility</code>
   * @return value of the color member value in the <code>Utility</code>
   */
  public Player getOwner()
  {
    return owner;
  }

    /**
     * Assigns the price field of the <code>Utility</code> object with the specified parameter
     * @param amount value to be assigned
     */
  public void updatePrice (double amount)
  {
    price = amount;
  }

    /**
     * Designates a <code>Player</code> object as the value of the owner parameter
     * @param player <code>Player</code> object to be assigned as the owner
     */
  public void setOwner(Player player)
  {
    owner = player;
  }


    /**
     * Assigns the added multiplier obtained and initiated by chance cards
     * @param percentage value multiplier to be added
     */
  public void setChancePercent(double percentage)
  {
    chancePercent+=percentage;
  }

  @Override
  public String toString()
  {
    return "" + super.getName() + " : Rent = " + getPrice();
  }

}
