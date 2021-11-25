package Cards;

import Model.*;

/**
 * Category2B represents as the subclass of Category2 that focuses on the Property Space type in moving the player to the nearest Utility
 * This contains a property, diceRoll, used to store values from 1 - 6 obtained in implementing goToSpace
 */
public class Category2B extends Category2 {
    private int diceRoll;

    /**
     * Creates a Category2B object with a String parameter assigned as the name of the Card which the parent of the object extends from
     * @param name name of card
     */
    public Category2B(String name)
    {
        super(name);
    }

    /**
     *  Calls the goToSpace function, which contains the characteristics of the card
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void applyEffect(Player player, Board b, int loc, Bank bank){
        goToSpace(player, b, loc, bank);
        //if owned, turn of player will still be true, execute usual command when player lands in property
    }

    /**
     * Defines the capabilities of the object which changes the location of the player from the parameter to the nearest Utility.
     * If the location is owned, a dice is rolled which is used to determine the amount to paid to the owner of the Utility.
     * @param player : player who drew the card from deck
     * @param b : board used in the game
     * @param loc : current location of the player
     * @param bank : bank in the game which stores and collects money from players
     */
    public void goToSpace(Player player, Board b, int loc, Bank bank) {
        newpos = super.nearest("Utility", b, loc);
        player.addTraffics(b, loc, newpos);
        player.setLocation(newpos);

        if (newpos<loc)
            bank.addTo(player, 200);

        if (((Utility)b.getCurrentSpace(newpos)).isOwned()){ //if owned, turn of player will still be true, execute usual command when player lands in property
            Dice x = new Dice();
            diceRoll = x.roll();
            player.payPerson(diceRoll*10, ((Utility)b.getCurrentSpace(newpos)).getOwner());
        }
    }

    /**
     * Returns the roll of the dice, from goToSpace
     * @return the value of the dice roll
     */
    public int getDiceRoll() {
        return diceRoll;
    }
}