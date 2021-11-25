package Cards;
import Model.*;

/**
 * Interface that denotes if the card can be owned or not
 */
public interface Ownable {
    public void isOwnable(Player player, Board b);
}
