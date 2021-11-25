package Cards;
import Model.*;

/**
 * Category2A represents as the subclass of Category2 that focuses on the Property Space type in moving the player to the nearest Property
 */

public class Category2A extends Category2{

    /**
     * Creates a Category2A object with a String parameter assigned as the name of the Card which the parent of the object extends from
     * @param name name of card
     */
    public Category2A(String name)
    {
        super(name);
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
    }

    /**
     * Defines the capabilities of the object which changes the location of the player from the parameter to the nearest Property
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        newpos = super.nearest("Property", b, loc);

        player.addTraffics(b, loc, newpos);
        player.setLocation(newpos);

    }

}
