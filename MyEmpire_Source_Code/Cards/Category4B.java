package Cards;

import Model.*;

/**
 * This class extends Card4 class to denote cards which are under category 4 which implement the GoTo and CollectMoney interfaces
 * This would apply the effect of going to the nearest property
 */
public class Category4B extends Category4 implements GoTo, CollectMoney{

    /**
     * This constructor initializes a Card of type Category4B with a given name
     * @param name name of the card
     */
    public Category4B(String name)
    {
        super(name);
    }

    /**
     * This would call the method goToSpace
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank)
    {
        goToSpace(player, b, loc, bank);
    }

    /**
     * This method would update the player's position to the nearest property, checks if player passed start
     * to give money to player.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */

    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        super.setNewpos(nearest("Property", b, loc));

        if (super.getNewpos() < loc) //if it passed through Start
            collectMoney(player, bank);

        player.addTraffics(b, loc, super.getNewpos());
        player.setLocation(super.getNewpos());
    }

    /**
     * This would return the index of the nearest space type specified in the parameter
     * @param type : the space type that the player will go to, whichever is nearest
     * @param b : board to be used in the game
     * @param loc :current location of the player
     * @return index of nearest space
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
     * This gives the player an amount of 200
     * @param player : player who drew the card from deck
     * @param bank : bank in the game which stores and collects money from players
     */
    public void collectMoney(Player player, Bank bank)
    {
        bank.addTo(player, 200);
    }

}
