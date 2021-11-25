package Cards;
import Model.*;

/**
 * This object represents a sub-class of Category 2 which adds the capabilities of changing the location of a player
 */
public class Category3B extends Category3 implements GoTo, CollectMoney{

    /**
     * Creates a Category3B class
     * @param amount value of money given to player when the card is implemented
     * @param name name of the object assigned to the ancestor classes
     */
    public Category3B(double amount, String name) {
        super(amount,name);
    }

    /**
     *  Calls the goToSpace function, which contains the characteristics of the card
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {
        collectMoney(player, bank);
        goToSpace(player, b, loc, bank);
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

    /**
     * Defines the capabilities of the object which changes the location of the player from the parameter to the location of start
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        player.setLocation(0);
        player.addTraffics(b, loc, 0);
        bank.addTo(player, 200);
    }
}
