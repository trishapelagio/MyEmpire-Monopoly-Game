package Cards;
import Model.*;

/**
 * This class extends the Category3 class to denote that
 * the card can only implement collectMoney
 */
public class Category3A extends Category3 implements CollectMoney{
    /**
     * This constructor initializes a card of type Category3A
     * @param amount amount to be given to the player
     * @param name name of the card
     */
    public Category3A(double amount, String name) {

        super(amount,name);
    }

    /**
     * This calls the method collectMoney
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {
        collectMoney(player, bank);
    }

    /**
     * This gives player the amount of money in the card
     * when it was initialized
     * @param player player to receive the money
     * @param bank bank that will give the money
     */
    public void collectMoney(Player player, Bank bank) {
        bank.addTo(player, getAmount());
    }
}
