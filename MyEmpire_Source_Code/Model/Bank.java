package Model;
/**
 * This class denotes the bank object of the game which gives and collects money from players.
 * This class contains money as an attribute
 */
public class Bank {
    private Money money;

    /**
     * This will instantiate a Bank given number of players, because the bank initial money
     * will depend on the number of players in the game.
     * @param players : number of players in the game
     */

    public Bank(int players)
    {
        money = new Money(players*2500);
    }

    /**
     * This method will return the money attribute of the bank
     * @return money of the bank of type Money
     */

    public Money getMoney()
    {
        return money;
    }

    /**
     * Return the current balance of the bank's money
     * @return double amount which corresponds to the balance of bank's money
     */

    public double getAmount()
    {
        return money.getAmount();
    }

    /**
     * Allows the bank to receive money
     * @param amount : amount of money to be received by the bank
     */

    public void receiveMoney(double amount)
    {
        money.addAmount(amount);
    }

    /**
     * Subtracts an amount of money from the bank
     * @param amount : amount of money to be subtracted
     */

    public void subtractMoney(double amount)
    {
        money.subtractAmount(amount);
    }

    /**
     * This will transfer money from bank to player, given an amount
     * @param p : player that will receive money from bank
     * @param amount : amount of money that will be received by player and subtracted from bank
     */

    public void addTo(Player p, double amount)
    {
        p.receiveMoney(amount);
        subtractMoney(amount);
    }


}
