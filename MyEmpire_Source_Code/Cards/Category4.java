package Cards;
import Model.*;

/**
 * This class extends Card class in order to identify the Cards
 * under Category4 which implements GoTo and CollectMoney
 */
public abstract class Category4 extends Card{
    protected int newpos;

    /**
     * Initializes a card of type Category4 with given name
     * @param name name of the card
     */
    public Category4(String name){
        super(name);
    }

    /**
     * returns the new position of the player
     * @return value of newpos variable
     */
    public int getNewpos() {
        return newpos;
    }

    /**
     * sets the player's new position in the Card class
     * @param newpos new position of the player
     */
    public void setNewpos(int newpos)
    {
        this.newpos = newpos;
    }
}
