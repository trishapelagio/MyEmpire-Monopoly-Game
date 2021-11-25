package Cards;
import Model.*;
/**
 * This class extends Card category to denote cards which belong under Category5
 * This primarily focuses on buffing a property, utility or railroad space owned by
 * the player.
 */
public abstract class Category5 extends Card implements Upgrade{
    protected int index;

    /**
     * This would initialize a card under Category5
     * @param name name of the card
     */
    public Category5(String name) {
        super(name);
    }

    /**
     * This sets the index variable to a certain value
     * @param i index of the property
     */
    public void setIndex(int i) {
        index = i;
    }

}
