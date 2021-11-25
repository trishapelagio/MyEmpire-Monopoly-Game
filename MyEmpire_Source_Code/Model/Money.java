package Model;
/**
 * The <code>Money</code> class is responsible for the behaviors of the game involving the amount of cash and the transactions that occur.
 */
public class Money {
    private double amount;

    /**
     * This will instantiate a money object with an amount of 1500
     */
    public Money()
    {
        amount = 1500;
    }

    /**
     * This will instantiate a money object with a given amount
     * @param amount : the amount of money of a bank or player
     */

    public Money(double amount)
    {
        this.amount = amount;
    }


    /**
     * The function returns the amount property of <code>Money</code>
     * @return amount value
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * Adds a value to the amount property of <code>Money</code> specified in the parameter
     * @param amount : amount to be added
     */
    public void addAmount(double amount)
    {
        this.amount+=amount;
    }

    /**
     * Subtracts a given amount from the total balance of the <code>Money</code> object
     * @param amount : amount to be subtracted
     */

    public void subtractAmount(double amount)
    {
        this.amount-=amount;
    }

    /**
     * Determines if money is enough to pay for the certain amount in the parameter
     * @param amount : amount of money to be compared to money balance
     * @return boolean value denoting if it is a valid transaction or not
     */

    public boolean isValidTransaction(double amount)
    {
        return this.amount >= amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

}
