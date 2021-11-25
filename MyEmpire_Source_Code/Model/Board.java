package Model;
import java.util.*;

/**
 * <p>The Board class contains the methods and properties for initiailzing and progressing the game.</p>
 *

 * <p>Specifically, it includes the following private fields in implementing myEmpire :</p>
 * <ul>
 *     <li>Space[]</li>
 *     <li>Player[]</li>
 *     <li>totalPlayer</li>
 *     <li>int turnIndex </li>
 *     <li>int turnCount</li>
 *     <li>Deck deck</li>
 * </ul>
 * */

public class Board {
    private Space[] spaces;
    private Player[] players;
    private int totalPlayer;
    private int turnIndex = 0;
    private Deck deck;

    /** Creates a Board object with the number of players in the game specified.
     * The index of the turn is initialized to 0, while its count is initialized to 1.
     *
     *
     *   @param playerNames the total number of players
     *   @param indices space indices to be instantiated to board
     *
     */

    public Board (ArrayList<String> playerNames, ArrayList<Integer> indices)
    {
        Space[] temp;
        deck = new Deck();
        this.totalPlayer = playerNames.size();
        players =  new Player[totalPlayer];
        spaces = new Space[32];
        temp = new Space[32];
        instantiateSpaces(temp); //instantiate all Spaces to be put to board

        for(int i = 0; i < totalPlayer; i++) {
            players[i] = new Player(playerNames.get(i), i+1);
        }

        for(int i=0;i<32;i++)
            spaces[i] = temp[indices.get(i)];

        System.out.println("YES");

    }
    
    public int getSize() {
        return spaces.length;
    }

    /**
     *  Returns the <b>Space</b> object from the private fields at the specified position.
     * @param i Index of the Space to return
     * @return Space object of the argument
     */
    public Space getCurrentSpace(int i) {

        return spaces[i];
    }

    /**
     * Returns the player object at the specified position of the turnIndex property.
     * @return the player object at the turnIndex property
     */
    public Player getCurrentPlayer() {
        return players[turnIndex];
    }

    /**
     * Returns an <b>array of Player</b> containing the <b>Player</b> objects instantiated
     * @return the array of <b>Player</b> initialized in <b>Board</b>
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the turnIndex property of board which refers to the <b>Player</b> object identifed
     * @return the index of the current turn
     */
    public int getTurnIndex() {
        return turnIndex;
    }


    /**
     * Returns the number of <b>Player</b> objects in the <b>Board</b> instance
     * @return the number of <p>Player</p> objects.
     */
    public int getTotalPlayer() {
        return totalPlayer;
    }

    /**
     * Returns the <b>Deck</b> object implemented during <b>Board</b> implementation
     * @return the <b>Deck</b> instantiated
     */
    public Deck getDeck() {
        return deck;
    }

    public boolean isEndGame(Bank bank) {
        for(int i = 0; i < totalPlayer; i++) {
            if(players[i].playerBroke()) //if one of the players are broke
                return true;

            else if(players[i].getFullSet() == 2) //if one of the players has 2 full sets of properties
                return true;

            else if(bank.getMoney().getAmount() <= 0) //if bank runs out of money
                return true;
        }
        return false;
        // Bankrupt bank should be checked in the middle of the turn
    }

    /**
     * This method validates if myEmpire has satisfied any of the conditions of ending. It returns a value of <b>TRUE</b> if at least one of these conditons are met. Otherwise, the return value is <b>FALSE</b>.
     *
     *
     * <p>Conditions of game ending are</p>
     * <ul>
     *     <li>a player does not have enough money to pay for rent, tax or fine; or</li>
     *     <li>a player owns 2 full sets of properties with the same color; or</li>
     *     <li>the bank is out of cash.</li>
     * </ul>
     *
     * @return <b>TRUE</b> if at least one of the conditions are satisfied.
     */

    public Player[] getWinners() { //return array of arranged players based on rank
        Player[] rank = new Player[totalPlayer];
        Player broke = null;
        Player temp = new Player();

        int i,j=0,k=0;
        for(i = 0; i < totalPlayer; i++) { // Assigning players to the array to be returned
            if(players[i].playerBroke()) {
                broke = players[i];
            }
            else {
                rank[k] = players[i];
                k++;
            }
        }

        if(k > 1) {
            for(i = 0; i < k - 1 ; i++) { // Sorting the players via worth
                for(j = 0; j < k-1-i; j++) {
                    if(playerTotalWorth(rank[j]) < playerTotalWorth(rank[j+1])){
                        temp = rank[j];
                        rank[j] = rank[j+1];
                        rank[j+1] = temp;
                    }
                }
            }
        }

        if (broke!=null) // if win condition 2 or 3
            rank[k] = broke;

        return rank;
    }

    /**
     * <p>This function returns the total worth of the player specified in the parameter.</p>
     * Total worth is computed by (Balance + Property Prices + Railroad Prices + Utility Prices)
     *
     * @param player : Player object to be computed for total worth
     * @return the accumulated worth of the <p>Player</p> instance in the function argument
     */

    public double playerTotalWorth(Player player) { // Computes the total worth of the player parameter based on balance and the prices of properties, railroads, and utilities
        double worth = 0;
        worth += player.getBalance();

        for(int i = 0; i < player.getProperties().size(); i++)
            worth += ((Property)player.getProperties().get(i)).getTotalPrice();

        worth += player.getRailroads().size() * 200;
        worth += player.getUtilities().size() * 150;

        System.out.println(player.getName() + " worth  " + worth);
        return worth;
    }

    /**
     * Increments the turn index by 1 to identify which <b>Player</b> object is active.
     */

    public void nextTurn() { // Increments the turn by 1 to indicate player turn
        turnIndex++; 
        turnIndex = turnIndex%totalPlayer; //so index will not go over bounds
    }

    /**
     *  Instantiate the blocks as the spaces to be used in the <b>Board</b>. Each <b>Space</b> contains pre-defined values that correspond to their name.
     * @param space array of <b>Space</b> objects instantiated within the the constructor of <b>Board</b> object.
     */
    private void instantiateSpaces (Space [] space) { // Instantiates the predefined spaces(properties, railroads, utilities, and corner) to the parameter Space array
        double[][] rent = { //stores all rent values of properties
                {2,10,30,90,160,250},
                {4,20,60,180,320,450},
                {6,30,90,270,400,550},
                {6,30,90,270,400,550},
                {6,40,100,300,450,600},
                {10,50,150,450,625,750},
                {10,50,150,450,625,750},
                {12,60,180,500,700,900},
                {14,70,200,550,750,950},
                {14,70,200,550,750,950},
                {16,80,220,600,800,1000},
                {18,90,250,700,875,1050},
                {18,90,250,700,875,1050},
                {20,100,300,750,925,1100},
                {22,110,330,800,975,1150},
                {22,110,330,800,975,1150},
                {26,130,390,900,1100,1275},
                {28,150,450,1000,1200,1400}
        };
        double[][] price = { //stores all price values of properties
                {60,50},
                {60,50},
                {100,50},
                {100,50},
                {120,50},
                {140,100},
                {140,100},
                {160,100},
                {180,100},
                {180,100},
                {200,100},
                {220,150},
                {220,150},
                {240,150},
                {260,150},
                {260,150},
                {300,200},
                {320,200}
        };

        //initializes Spaces based on their specific characteristics

        space[0] = new Property(rent[0], price[0], 2.5, "gray", totalPlayer,  "Almond Drive");
        space[1] = new Property(rent[1], price[1], 3.0, "gray", totalPlayer,  "Kasoy Street");
        space[2] = new Property(rent[2], price[2], 3.5, "violet", totalPlayer,  "Rodeo Drive");
        space[3] = new Property(rent[3], price[3], 4.0, "violet", totalPlayer,  "Orange Street");
        space[4] = new Property(rent[4], price[4], 4.0, "violet", totalPlayer,  "Ventura Street");
        space[5] = new Property(rent[5], price[5], 4.5, "pink", totalPlayer,  "Juan Luna");
        space[6] = new Property(rent[6], price[6], 4.5, "pink", totalPlayer,  "Ylaya");
        space[7] = new Property(rent[7], price[7], 5.0, "pink", totalPlayer,  "J. Abad Santos");
        space[8] = new Property(rent[8], price[8], 5.0, "green", totalPlayer,  "Madison");
        space[9] = new Property(rent[9], price[9], 5.5, "green", totalPlayer,  "Annapolis");
        space[10] = new Property(rent[10], price[10],5.5, "green", totalPlayer,  "Connecticut");
        space[11] = new Property(rent[11], price[11], 6.0, "blue", totalPlayer,  "Bougainvilla");
        space[12] = new Property(rent[12], price[12], 6.0, "blue", totalPlayer,  "Dama de Noche");
        space[13] = new Property(rent[13], price[13], 6.5, "blue", totalPlayer,  "Acacia");
        space[14] = new Property(rent[14], price[14], 6.5, "orange", totalPlayer,  "Solar Street");
        space[15] = new Property(rent[15], price[15], 7.0, "orange", totalPlayer,  "Galaxy Street");
        space[16] = new Property(rent[16], price[16], 7.0, "yellow", totalPlayer,  "9th Street");
        space[17] = new Property(rent[17], price[17], 8.0, "yellow", totalPlayer,  "5th Avenue");

        space[18] = new Railroad("North Railroad");
        space[19] = new Railroad("South Railroad");
        space[20] = new Railroad("Metro Railroad");

        space[21] = new Utility("Water Utility");

        space[22] = new Utility("Electricity Utility");

        space[23] = new Spacee("Chance 1", "Chance");
        space[24] = new Spacee("Chance 2", "Chance");
        space[25] = new Spacee("Chance 3", "Chance");

        space[26] = new Spacee("Luxury Tax", "Luxury Tax");
        space[27] = new Spacee("Income Tax", "Income Tax");

        space[28] = new Spacee(0, "Start", "Start");
        space[29] = new Spacee(8,"Free Parking", "Free Parking");
        space[30] = new Spacee(16, "Community Service", "Community Service");
        space[31] = new Spacee(24, "Jail", "Jail");
    }

}
