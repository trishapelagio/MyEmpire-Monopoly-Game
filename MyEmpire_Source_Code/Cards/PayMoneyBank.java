package Cards;
import Model.*;

/**
 * Interface which allows player to pay money to bank.
 */
public interface PayMoneyBank {
    public void pay(double amount, Bank bank, Player player);
}
