package Cards;

import Model.*;

/**
 * This class extends Category5 class to denote that the
 * card will renovate the property space of the player.
 */
public class Category5B extends Category5 {

    /**
     * This would initializes a card if Category5B with a given name
     * @param name name of the card
     */
    public Category5B(String name)
    {
        super(name);
    }

    /**
     * If player does not own any properties, card will be discarded,
     * otherwise it will be renovated based on number of houses or hotel.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank) {
        if (player.getProperties().size()>0)
        {
            if(((Property)player.getProperties().get(index)).getR() == 5) //if the property has hotel
                player.payBank(50, bank);

            else
                player.payBank(25 * ((Property)player.getProperties().get(index)).getR(), bank); //if property has houses, multiply price to number of houses

            upgradeSpace(player.getProperties().get(index));
        }
    }

    /**
     * Updates the rent percentage increase of a property space type
     * @param space to be upgraded
     */
    public void upgradeSpace(Space space) {
        ((Property)space).setChancePercent(.5);
    }
}