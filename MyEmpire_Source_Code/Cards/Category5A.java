package Cards;

import Model.*;

/**
 * Category 5A extends Category 5 which contains the implementations of double rent to a property
 */

public class Category5A extends Category5{

    /**
     * Creates an instance of this object through a String parameter assigned as its name
     * @param name name of card
     */
    public Category5A(String name) {
        super(name);
    }

    /**
     * The method assigns a true value to the hasDoubleRent variable of the Property object specified in the loc parameter
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {
        upgradeSpace(player.getProperties().get(index));
    }

    /**
     * Sets the hasDoubleRent property of a Property space type
     * @param space space to be upgraded
     */
    public void upgradeSpace(Space space) {
        ((Property)space).setDoubleRent(true); //set double rent for the Property to true
    }
}
