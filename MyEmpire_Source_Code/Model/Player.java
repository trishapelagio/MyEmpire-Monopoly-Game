package Model;
import javax.swing.text.Utilities;
import java.util.*;

/**
 * This object class represents the players that will be moving in the game.
 * Players have a name, player number which will be assigned to them during board
 * initialization, money, arraylist of properties, railroads, utilities and chanceCards.
 *
 * The location variable of the player will constantly change per turn.
 * The die will store the value of the dice in the player's current turn.
 * The fullSet variable denotes the number of fullset properties the player has, once player has 2 fullsets, player wins.
 * The game can be composed of 2-4 players.
 *
 */
public class Player {
    private String name;
    private int playerNum;
    private Money money;
    private ArrayList<Space> properties;
    private ArrayList<Space> railroads;
    private ArrayList<Space> utilities;
    private ArrayList<Card> chanceCards;
    private int location;
    private boolean inJail;
    private int die;
    private boolean broke;
    private boolean turn;

    private int fullSet = 0;


    /**
     * Creates an empty player object.
     */

    public Player()
    {
    }

    /** Creates a player object with name and player number specified.
     * Money is initialized to 1500 and location is initialized to Start (0).
     * @param name : name of the player
     * @param num : player number
     */

    public Player(String name, int num)
    {
        this.name = name;
        playerNum = num;
        money = new Money();
        properties = new ArrayList<Space>(18);
        railroads = new ArrayList<Space>(3);
        utilities = new ArrayList<Space>(2);
        chanceCards = new ArrayList<Card>(2); //because jail card is the only one which can be kept (2 max per deck)
        location = 0;
        inJail = false;
        broke = false;
    }

    /**
     * Returns current location of the player
     * @return location of the user
     */

    public int getLocation()
    {
        return location;
    }

    /**
     * Returns name of the player
     * @return name of the player
     */

    public String getName()
    {
        return name;
    }

    /**
     * Returns current money balance of the player.
     * @return balance of the player
     */

    public double getBalance()
    {
        return money.getAmount();
    }

    /**
     * Returns the ArrayList containing properties owned by the player
     * @return array list of properties of type Space
     */

    public ArrayList<Space> getProperties()
    {
        return properties;
    }

    /**
     * Returns the ArrayList containing the railroads owned by the player
     * @return array list of railroads of type Space
     */

    public ArrayList<Space> getRailroads()
    {
        return railroads;
    }

    /**
     * Returns the ArrayList containing the utilities owned by the player
     * @return array list of utilities of type Space
     */

    public ArrayList<Space> getUtilities()
    {
        return utilities;
    }

    /**
     * Returns the ArrayList containing the chance cards owned by the player
     * @return array list of chance cards of type Card
     */

    public ArrayList<Card> getChance()
    {
        return chanceCards;
    }

    /**
     * Returns the number of fullset properties the player has per color
     * @return number of full set properties the player has
     */

    public int getFullSet () {
        return fullSet;
    }

    /**
     * This method allows player to draw a card from the deck which will also execute the command written on the card chosen.
     * @param deck : deck to draw card from
     * @param b : board used in the game
     * @param bank : bank which stores money in the game
     */

    public void drawCard(Deck deck, Board b, Bank bank)
    {
        deck.drawCard(this, b, location, bank);
    }

    /**
     * This method returns true if purchase of property is successful, otherwise return false
     * @param space : Space to be purchased by the user
     * @param b : board used in the game
     * @return true if purchase is successful, otherwise, return false
     */

    public boolean purchaseSpace(Space space, Bank b)
    {
        if (space instanceof Property) { //PROPERTY
            if (money.isValidTransaction(((Property)space).getPrice())) {
                System.out.println(space.getName() + " was bought.");
                properties.add(space);
                payBank(((Property)space).getPrice(), b);
                ((Property)space).setOwner(this);
                updateRent(space); //updates rent for all properties of same color
                return true;
            } else {
                System.out.println(space.getName() + " cannot be bought.");
                return false;
            }
        }
        else if (space instanceof Utility) // UTILITY
        {
            if(money.isValidTransaction(((Utility)space).getPrice())) {
                System.out.println(space.getName() + " was bought.");
                utilities.add(space);
                payBank(((Utility)space).getPrice(), b);
                ((Utility)space).setOwner(this);
                updateRent(space); //updates rent for utilities owned
                return true;
            }
            else {
                System.out.println(space.getName() + " cannot be bought.");
                return false;
            }
        }
        else if (space instanceof Railroad) // RAILROAD
        {
            if(money.isValidTransaction(((Railroad)space).getPrice())) {
                System.out.println(space.getName() + " was bought.");
                railroads.add(space);
                payBank(((Railroad)space).getPrice(), b);
                ((Railroad)space).setOwner(this);
                updateRent(space); //updates rent for railroads owned
                return true;
            }
            else {
                System.out.println(space.getName() + " cannot be bought.");
                return false;
            }
        }
        else
            return false;
    }

    /**
     * Method returns true if playfer was able to develop the property successfully.
     * @param property : property to be developed
     * @param b : board used in the game
     * @return : True if the property was developed, false otherwise.
     */


    public boolean developProperty(Space property, Bank b)
    {
        if (money.isValidTransaction(((Property)property).getPrice()) && ((Property)property).canBeDeveloped()) {

            ((Property)property).develop();
            payBank(((Property)property).getPrice(), b);

            if(isFullSet(property)) {
              fullSet++;
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Allows the person to pay anotherrson
     * @param amount : amount of money to be paid
     * @param person : Player who will be receiving the money
     */

    public void payPerson(double amount, Player person)
    {
        if(money.isValidTransaction(amount)) {
            System.out.println("You paid " + amount + " to " + person.getName());
            person.receiveMoney(amount);
            money.subtractAmount(amount);
        }

        else {
            money.subtractAmount(amount);
            System.out.println("You cannot pay anymore. You are bankrupt.");
            playerBroke();
        }

    }

    /**
     * Allows the person to received money from bank or another player
     * @param amount : the amount of money to be added to the player's balance
     */

    public void receiveMoney (double amount)
    {
        money.addAmount(amount);
    }

    /**
     * Allows the person to pay money to bank
     * @param amount : amount of money to be paid to bank
     * @param b : bank that will received the money
     */

    public void payBank (double amount, Bank b)
    {
        if(money.isValidTransaction(amount)) {
            System.out.println("You paid bank " + amount);
            b.receiveMoney(amount);
            money.subtractAmount(amount);
        }

        else {
            money.subtractAmount(amount);
            System.out.println("You cannot pay anymore. You are bankrupt.");
            playerBroke();
        }
    }

    /**
     * This method proposes a trade with a person for a property.The player chooses a property of his own
     * to trade with the other player.
     * @param with : the person to trade with
     * @param property : property to trade for
     * @param i : chosen property to give
     */

    public void trade(Player with, Space property, int i)
    {

        ((Property)property).initTrade(this);
        ((Property)properties.get(i)).initTrade(with);

        if(with.isFullSet(property)) //decrement if property is included in fullset properties of with player
            with.fullSet--;

        with.removeProperty(property);
        with.properties.add(properties.get(i));

        if(with.isFullSet(properties.get(i))) //check for fullset
            fullSet++;

        if(isFullSet(properties.get(i))) // decrement if property is included in fullset properties of this player
            fullSet--;

        removeProperty(properties.get(i));
        properties.add(property);
        with.updateRent(property);
        updateRent(property);

        if(isFullSet(property)) { //check for fullset
            fullSet++;
        }

    }

    /**
     * This method determines if a property belongs to a full set of properties of the same color
     * @param property : property to be checked if fullset
     * @return true if property belongs to a full set (fully-developed and all properties of same color are bought), false otherwise
     */

    private boolean isFullSet(Space property) {
      int count = 0;
      for(int i = 0; i < properties.size(); i++) {
        if(((Property)properties.get(i)).getColor().equals(((Property)property).getColor()) && ((Property)properties.get(i)).getR() == 5) //if property of same color is fully developed (has hotel)
          count ++; //increment
      }

      //check if all properties of the same color as property in parameter are full developed and are complete
      if((((Property)property).getColor().equals("gray") || ((Property)property).getColor().equals("orange") || ((Property)property).getColor().equals("yellow") ) && count == 2) {
        return true;
      }
      else{
          return count == 3;
      }
    }

    /**
     * Updates rent of the spaces belonging to the same category of the space in parameter based on number of properties
     * in that category. Properties: (10 for 2 and and 20 for 3 properties), Railroads: (25 for 1, 50 for 2, 150 for 3),
     * Utility : (if player owns one, additional rent is 4 * dice, if player owns both, additional rent is 10*dice.
     * @param space : basis space to be update
     */

    private void updateRent (Space space)
    {
        if (space instanceof Property) { //if type is Property
            int i, count = 0;
            for (i = 0; i < properties.size(); i++) {
                if (((Property)space).getColor().equals(((Property)properties.get(i)).getColor()))
                    count++;
            }
            for (i = 0; i < properties.size(); i++) {
                if (((Property)space).getColor().equals(((Property)properties.get(i)).getColor()))
                    ((Property)properties.get(i)).setAdditionalRent((count - 1) * 10);
            }
        }
        else if(space instanceof Utility) //if type is Utility
        {
            int count, i;
            count = utilities.size();
            for (i=0;i<count;i++)
            {
                if(count==2)
                    ((Utility)utilities.get(i)).updatePrice(die*10);
                else
                    ((Utility)utilities.get(i)).updatePrice(die*4);
            }
        }
        else if(space instanceof Railroad) //if type is Railroad
        {
            int count, i;
            count = railroads.size();
            for(i=0;i<count;i++)
            {
                if (count==1)
                    ((Railroad)railroads.get(i)).updatePrice(25);
                else if (count==2)
                    ((Railroad)railroads.get(i)).updatePrice(50);
                else
                    ((Railroad)railroads.get(i)).updatePrice(150);
            }
        }
    }

    /**
     * Updates the foot traffic count of each of the Spaces that the user passed through
     * @param board : board used in the game
     * @param oldpos : old index position of the player
     * @param newpos : new index position of the player
     */

    public void addTraffics(Board board, int oldpos, int newpos)
    {
        int pos;

        pos = oldpos;

        while (pos!=newpos)
        {
            pos++;
            pos = pos % board.getSize(); //so index will not go out of bounds
            if (board.getCurrentSpace(pos).getType().equals("Property"))
                ((Property)board.getCurrentSpace(pos)).addTraffic(); //add traffic to all passed properties
        }
    }

    /**
     * Removes the property from the ArrayList of properties of the player
     * @param property : property to be removed from list
     */

    public void removeProperty(Space property)
    {
        properties.remove(property);
    }

    /**
     * Sets the new location of the player
     * @param loc : index of the new location of the player
     */

    public void setLocation(int loc)
    {
      location = loc;
    }

    /**
     * Adds the chance card to the set of Chance Cards of the player
     * @param chance : the chance card to be added to the list of cards of the player
     */

    public void addChanceCard (Card chance)
    {
      chanceCards.add(chance);
    }

    /**
     * Determines if the player is already broke, returns true is player is broke, false otherwise
     * @return true if player is broke and false otherwise
     */

    public boolean playerBroke()
    {
        if(money.getAmount()<0||broke) {
            money.setAmount(0);
            broke = true;
            return true;
        }
        else
            return false;
    }

    public boolean isJail()
    {
        return inJail;
    }

    public void setJail(boolean jail)
    {
        inJail = jail;
    }

    public  int getDie()  {
        return die;
    }

    public int getPlayerNum()
    {
        return playerNum;
    }


    public void setDice(int n) {
        die = n;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public void setTurn(boolean turn)
    {
        this.turn = turn;
    }


    /**
     * Print the information of the player including Player number, location, money, properties, utilities, railroads and chance cards.
     * @return a string containing player's information
     */
    @Override
    public String toString()
    {
        String info;
        info = "[Player number: " + playerNum +  "| Player name: " + name + "| Money : " + money.getAmount() + "]\nProperties : " + properties.size() + "| Utilities : " + utilities.size() + "| Railroads : " + railroads.size() + "| Chance Cards : " + chanceCards.size();

        info += "\n\nProperties: ";

        for (int i = 0 ; i < properties.size() ; i++)
            info+= "\n" + i + " : "  + properties.get(i);

        info += "\nUtilities: ";

        for (int i = 0 ; i < utilities.size() ; i++)
            info+= "\n" + i + " : "  + utilities.get(i);

        info += "\nRailroads: ";

        for (int i = 0 ; i < railroads.size() ; i++)
            info+= "\n" + i + " : "  + railroads.get(i);

        return info;
    }

}