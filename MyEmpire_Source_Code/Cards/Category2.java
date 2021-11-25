package Cards;
import Model.*;

/**
 * This object, Category2, represents the cards that contains the capabilities that proceed to a nearest space such as, properties, utilities, or railroads. This is operated by
 * extending the Card object and implementing the GoTo interface.
 *
 * Category2 contains a property, newpos, which is an integer that stores the nearest location of a Space object.
 */
public abstract class Category2 extends Card implements GoTo{

    protected int newpos;

    /**
     * Creates a Category2 object given a String parameter, to be assigned to its super class, Card, as its name
     * @param name String data type to be assigned as the name of the card
     */
    Category2(String name)
    {
        super(name);
    }

    /**
     * Gets the location of the nearest Space on the board from the current location of the player.
     * @param type : the space type that the player will go to, whichever is nearest
     * @param b : board to be used in the game
     * @param loc :current location of the player
     * @return // oof
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

    /**
     * Returns the newpos property of the object to reassign the location of the player
     * @return location of the nearest Space type
     */
    public int getNewpos()
    {
        return newpos;
    }

}