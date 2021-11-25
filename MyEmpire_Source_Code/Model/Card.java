package Model;
import java.util.*;

/**
 * This class denotes the Card object of the game, this also contains all the commands of the card chosen.
 * The Card object contains a name, rowIndex (to denote category) and columnIndex (to denote which card
 * it is in a certain category).
 */
public abstract class Card{
  private String name;

    /**
     * This will initialize a Card with a given name, rowIndex and columnIndex
     * @param name : name of the Card
     */

  public Card (String name)
  {
    this.name = name;
  }

    /**
     * Applies effect of the chosen card to the player. This contains 18 possible commands.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */

  public abstract void applyEffect(Player player, Board b, int loc, Bank bank);


    /**
     * This method returns the name of the card
     * @return the String name of the card
     */

  public String getName()
  {
    return name;
  }

    /**
     * Returns the indexof the nearest space type specified with given current location in the board
     * @param type : the space type that the player will go to, whichever is nearest
     * @param b : board to be used in the game
     * @param loc :current location of the player
     * @return index to nearest Space of given type
     */

    public int nearest(String type, Board b, int loc)
    {
        boolean flag = false;
        loc++;
        while(!flag)
        {
            if(b.getCurrentSpace(loc).getType().equals(type))
            {
                flag = true;
                return loc;
            }
            loc++;
            loc = loc%b.getSize();
        }
        return 0;
    }


  public String toString () {
      return getName();
  }
}
