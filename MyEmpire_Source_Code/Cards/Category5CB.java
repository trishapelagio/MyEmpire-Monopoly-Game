package Cards;

import Model.*;
import java.util.*;

/**
 * This object extends Category extends Category5C which focuses on updating the rent percentage of a Utility or Railroad object
 */
public class Category5CB extends Category5C{
    private char ur;

    public Category5CB(String name, double amount) {
        super(name, amount);
    }

    /**
     * The function defines the capability of the card which changes the rent percentage of a Utility or Railroad
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {
            if (ur=='U' || ur =='u') {
                upgradeSpace(player.getUtilities().get(index));
            }
            else if (ur=='R' || ur=='r') {
                upgradeSpace(player.getRailroads().get(index));
            }
    }

    /**
     * Updates the chance percent of chosen utility or railroad
     * @param space space to be upgraded
     */
    public void upgradeSpace(Space space) {
        if(space instanceof Railroad) {
            ((Railroad)space).setChancePercent(getAmount());
        }
        else {
            ((Utility)space).setChancePercent(getAmount());
        }
    }

    /**
     * Assigns the ur property of the parent class
     * @param c : value to be assigned to ur, representing the choice of the user, where 'u' or 'U' is equal to Utility, and 'r' or 'R' represents Railroad
     */
    public void setUr(char c)
    {
        ur = c;
    }

    /**
     * Returns the character value of ur, denoting user choice of which rent Space to update
     * @return whether player chose u or r
     */
    public char getUr ()
    {
        return ur;
    }


}