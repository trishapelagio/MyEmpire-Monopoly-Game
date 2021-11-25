package Model;
/**
 * This extends the <code>Space</code> class to implement several methods and properties that indicate the behaviors of a <code>Property</code>.
 */

public class Property extends Space{
    private Player owner;
    private double[] rent;
    private int r;
    private double[] price;
    private int p;
    private double additionalRent;
    private double chancePercent;
    private double multiplier;
    private String color;
    private double collectedRent;
    private double trafficCount;
    private double trafficLimit;
    private boolean hasDoubleRent;

    /**
     * Creates a <code>Property</code> object through the specified parameters indicating a list of rent rates, price rates, multiplier, color, and name.
     * @param rent array of values containing rates that differ depending on the level of development
     * @param price array of values containing the rates for development
     * @param multiplier unique multiplier of the <code>Property</code>
     * @param color <code>String</code> parameter indicating the color
     * @param players number of participating players in the game
     * @param name indicates the name of the <code>Property</code>
     */
    public Property(double[] rent, double[] price, double multiplier, String color, int players, String name)
    {
      super(name, "Property");
      this.price = price;
      this.rent = rent;
      this.color = color;
      r = 0;
      p = 0;
      collectedRent = 0;
      chancePercent=1;
      this.multiplier= multiplier;
      trafficLimit = players*multiplier;
    }

    /**
     * Returns the price property of the object specified at an index <code>p</code>
     * @return price at an index <code>p</code>
     */
    public double getPrice()
    {
      return price[p];
    }

    /**
     * Returns the rent rate of the <code>Property</code> at an index <code>r</code>
     * @return the rate at index <code>r</code>
     */
    public double getRent()
    {
        if(isDoubleRent())
            return ((rent[r]*chancePercent) + additionalRent) * 2;
        else
            return (rent[r]*chancePercent) + additionalRent;
    }

    /**
     * Returns the <code>Player</code> object that bought the <code>Property</code> object
     * @return owner of <code>Property</code>
     */
    public Player getOwner()
    {
      return owner;
    }

    /**
     * Returns the <code>String</code> color of the <code>Property</code>
     * @return value of the color member value in the <code>Property</code>
     */
    public String getColor()
    {
      return color;
    }

    /**
     * Returns the value of index <code>r</code>
     * @return index <code>r</code>
     */
    public int getR() {
      return r;
    }

    /**
     * Increments <code>r</code> index by 1 to indicate level of development, and sets <code>p</code> to 1 to specify status of development
     */
    public void develop()
    {
      r++;
      p=1;
    }

    /**
     * Changes the <code>owner</code> to the <code>Player</code> argument.
     * @param x <code>Player</code> object that is assigned
     */
    public void initTrade(Player x)
    {
      owner = x ;
    }

    /**
     * Increments the traffic count value by 1 whose value is used for development conditions
     */
    public void addTraffic()
    {
      trafficCount++;
    }

    /**
     * Returns the total count in foot traffic of the <code>Property</code>
     * @return trafic count property
     */
    public double getTraffic() {
        return trafficCount;
    }

    /**
     * Adds the value of the parameter to the collected rent of <code>Property</code>
     * @param amount value to be added
     */
    public void addTotalRent(double amount)
    {
      collectedRent += amount;
    }

    /**
     * Assigns an added value to the rent corresponding to <code>Property</code> objects of the same <code>property</code>
     * @param amount value to be added
     */
    public void setAdditionalRent(double amount)
    {
      additionalRent = amount;
    }

    /**
     * Assigns the added multiplier obtained and initiated by chance cards
     * @param percentage value multiplier to be added
     */
    public void setChancePercent(double percentage)
    {
      chancePercent+=percentage;
    }

    /**
     * Identifies if the <code>Property</code> object is owned by a <code>Player</code>. A true value is returned if the <code>Property</code> has a corresponding owner, and false if otherwise.
     * @return true if owner property has an owner of class <code>Player</code>
     */
    public boolean isOwned()
    {
        return owner != null;
    }

    /**
     * Validates the conditions if the <code>Property</code> is eligible for development by returning a true value. A false values is returned all of the conditions are not satisfied.
     * @return true if at least one of the conditions of development are satisfied and false all are not
     */

    public boolean canBeDeveloped()
    {
        if (r==4) {
            int total = 0;
            int count = 0;


            for(int i = 0; i < owner.getProperties().size(); i++) {
                if(((Property)owner.getProperties().get(i)).getColor().equals(((Property)this).getColor())) {
                    total++;
                    if(((Property)owner.getProperties().get(i)).getR() >= 4)
                        count++;
                }
            }
            return count == total;
        }

        else if(r != 5 && (collectedRent >= price[1] || trafficCount >= trafficLimit))
            return true;
        else
            return false;
    }

    /**
     * Toggles the multiplier for double rent through the <code>x</code> parameter
     * @param x value indicating its state
     */
    public void setDoubleRent(boolean x)
    {
      hasDoubleRent = x; //set status of double rent
    }

    /**
     * Verifies if the <code>Property</code> has true as its value for double rent
     * @return true if double rent is active, while false if otherwise
     */
    public boolean isDoubleRent()
    {
      return hasDoubleRent;
    }

    /**
     * Assigns the owner field with a <code>Player</code> indicated in the parameter
     * @param player Player that owns property
     */
    public void setOwner(Player player)
    {
      owner = player;
    }

    /**
     * Returns the total collected rent the property
     * @return total collected rent
     */

    public double getCollectedRent()
    {
        return collectedRent;
    }

    /**
     * Returns total worth of the property
     * @return total worth of the property
     */

    public double getTotalPrice()
    {
        return price[0] + (price[1]*r);
    }

    @Override
    public String toString()
    {
        if(r!=5)
            return "<" + super.getName() + " : " + r + " Houses | Rent : " + getRent() + "\n\t      Foot Traffic : " + getTraffic() + "/" + trafficLimit + " | Collected Rent : "  + collectedRent + "/" + price[1] + " >\n";
        else
            return "<" + super.getName() + " : Hotel>\n";
    }
}