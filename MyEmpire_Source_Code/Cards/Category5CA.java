package Cards;

import java.util.*;
import Model.*;

/**
 * This object extends Category extends Category5C which focuses on updating the rent percentage of a Property object
 */
public class Category5CA extends Category5C{
    /**
     * Creates an instance of this object with the name obtained from parameter. The percentage value, in decimal values, from the parameter is assigned
     * to the amount property of the parent class.
     * @param name name of card
     * @param amount amount to be added
     */
    public Category5CA(String name, double amount) {
        super(name,amount);
    }

    /**
     * The function defines the capability of the card which changes the rent percentage of a Property
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {

            upgradeSpace(player.getProperties().get(index));
    }

    public void upgradeSpace(Space space) {
        ((Property)space).setChancePercent(getAmount());
    }
}
