package Cards;

import Model.*;

/**
 * This class extends Category4 to and implements GoTo which changes the location of the player
 */

public class Category4A extends Category4 implements GoTo {

    /**
     * Creates an instance of this object given the String parameter as its name and assigns the Jail location to the newpos property
     * @param name name of card
     */
    public Category4A(String name)
    {
        super(name);
        super.setNewpos(24);
    }

    /**
     *  Calls the goToSpace function, which contains the characteristics of the card
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank){
        goToSpace(player, b, loc, bank);
        player.setJail(true);
    }

    /**
     * Defines the capabilities of the object which changes the location of the player from the parameter to the location of jail
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        player.setLocation(newpos);
        player.addTraffics(b, loc, newpos);
    }
}
