package Cards;
import Model.*;

/**
 * This object class extends the Card object to provide the implementations needed for cards which can be stored that allow a player to get out of jail. In order to
 * apply this process, Category1 implements the JailOut and Ownable
 *
 */


public class Category1 extends Card implements JailOut, Ownable {

    /** Creates a Category1 object, extended from Card, given a String value assigned in the name property from its parent.
     *
     *   @param name String value assigned to the object from its super class.
     *
     */
    public Category1 (String name) {
        super (name);
    }

    /**
     * The function implements the characteristics of the Card in category 1, which grant the capabilities to store the card and bail out of jail.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank){
        outJail(player, b);
    }

    /**
     * The function implements the method in JailOut interface by assigning the inJail property of a player from the parameter to false. This indicates that the player is not in jail.
     * @param player : player that will be removed from jail
     * @param b : board object containing the functions necessary to store the card to the discards list of the game
     */
    public void outJail(Player player, Board b) {
            b.getDeck().addCard(this);
            player.getChance().remove(this);
            player.setJail(false);
    }

    /**
     *  Implements the Ownable interface by adding this object to the Cards list of the player
     * @param player : Player object the receives this object
     * @param b : used for removing the drawn card from the board since it is added to the player
     */

    public void isOwnable(Player player, Board b)
    {
        player.addChanceCard(this);
        b.getDeck().getCards().remove(this);
    }
}
