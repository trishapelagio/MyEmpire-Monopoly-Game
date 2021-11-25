package Cards;

import Model.*;

/**
 * This class extends Category2 which allows the player to go to
 * nearest Railroad.
 */

public class Category2C  extends Category2{

    /**
     * This constructor initializes a Card of type Category2C
     * @param name name of the card
     */
    public Category2C(String name)
    {
        super(name);
    }

    /**
     * This calls the goToSpace method
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank){
        goToSpace(player, b, loc, bank);
    }

    /**
     * This method would update the player's position to the nearest railroad, checks if player passed start
     * to give money to player.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        newpos = super.nearest("Railroad", b, loc);

        if (newpos<loc)
            bank.addTo(player, 200);

        player.addTraffics(b, loc, newpos);
        player.setLocation(newpos);
    }
}