package Cards;
import Model.*;

/**
 * This interface includes the need for implementing the method for changing the location of a player
 */
public interface GoTo {

    public void goToSpace(Player player, Board b, int loc, Bank bank);

}
