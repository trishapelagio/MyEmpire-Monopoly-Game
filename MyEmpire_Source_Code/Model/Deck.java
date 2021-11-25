package Model;
import Cards.*;
import java.security.SecureRandom;
import java.util.*;


/**
 *This class denotes the deck object of the game which initializes, shuffles and contains
 * all of the chance cards in the game. These cards will only be reused and shuffled, once
 * all cards have already been used. The properties of this object include:
 *
 * categoryof type Card[][]: which will store all possible cards to be used in the deck
 * cardsof type Card[] : store all the cards randomly initialized by the Deck
 * sizeOfDeck of type int : denote the current size of the deck.
 */

public class Deck
{
	private ArrayList<Card> cards;
	private ArrayList<Card> discards;

	/**
	 * This constructor will initialize a deck of random cards of size 28 with specific sizes per category:
	 * There are 6 total categories and 18 possible cards. The cards will then be shuffled after the initialization.
	 */


	public Deck() //initializes deck of random cards with specific sizes per category
	{
		Random rand = new Random();
		cards = new ArrayList<>();
		discards = new ArrayList<>();
		Card[][] category;
		category = initCategory();
		for (int i=0;i<28;i++)
		{
			if (i==0)
				discards.add(category[0][0]);
			else if (i==1)
				discards.add(new Category1("Jail"));
			else if (2<=i&&i<=7)
				discards.add(category[1][rand.nextInt(3)]);
			else if (8<=i&&i<=13)
				discards.add(category[2][rand.nextInt(5)]);
			else if (14<=i&&i<=17)
				discards.add(category[3][rand.nextInt(2)]);
			else if (18<=i&&i<=25)
				discards.add(category[4][rand.nextInt(5)]);
			else
				discards.add(category[5][rand.nextInt(2)]);
		}
		shuffle();

	}

	/**
	 * This will initialize all the possible cards of type Card that will be used for putting random cards into the deck.
	 */

	private Card[][] initCategory() //initialize all possible cards with corresponding row and column
	{
		Card[][] category = new Card[6][5];
		category[0][0] = new Category1("Jail");
		category[0][1] = new Category1("Jail");
		category[1][0] = new Category2A("goProperty");
		category[1][1] = new Category2B("nearestUtility");
		category[1][2] = new Category2C("nearestRailroad");
		category[2][0] = new Category3A(50,"receive50");
		category[2][1] = new Category3A(100,"receive100");
		category[2][2] = new Category3B(200,"goStart");
		category[2][3] = new Category3A(300,"receive300");
		category[2][4] = new Category3A(150,"receive150");
		category[3][0] = new Category4A("goJail");
		category[3][1] = new Category4B("tripProperty");
		category[4][0] = new Category5A("doubleRent");
		category[4][1] = new Category5B("renovation");
		category[4][2] = new Category5CA("increaseRent10", .10);
		category[4][3] = new Category5CB("increaseUR10", .10);
		category[4][4] = new Category5CB("decreaseUR10", -.10);
		category[5][0] = new Category6("donateMoney");
		category[5][1] = new Category6("payTaxes");

		return category;
	}

	/**
	 * This method will return a boolean value denoting if it is the end of the player's turn already (false) or not (true)
	 * after the execution of the command in the chosen card.
	 * @param player : the player who drew the card
	 * @param b : board used in the game
	 * @param loc : current location of the player
	 * @param bank : bank which collects and gives money to players
	 * @return current card
	 */

	public Card drawCard(Player player, Board b, int loc, Bank bank) {

		Card current = getCurrent(player, b, loc, bank); //get next card to be drawn

		System.out.println("\n" + current.getName());

		if(!(current instanceof Ownable)) //if  ownable, card will not be added to the discarded pile, will only be added when it is used
			discards.add(current);

		cards.remove(current);

		return current;
	}


	/**
	 * This will randomize the content of the array of cards
	 * @param array : array containing the cards to be randomized
	 * @return the randomized Card array
	 */


	private ArrayList<Card> randomizeArray(ArrayList<Card> array){
		Random rgen = new Random();

		for (int i=0; i<array.size(); i++) {
		    int randomPosition = rgen.nextInt(array.size()); //randomize index
		    Card temp = array.get(i); //set t
			array.set(i, array.get(randomPosition)); //swap to random
			array.set(randomPosition, temp); //swap to random
		}
		return array;
	}

	/**
	 * This will reset the size of the deck to 28 and
	 * shuffle the contents of the deck
	 */

	private void shuffle()
	{
		for(int i = 0 ; i < discards.size() ; i++)
		{
			cards.add(discards.get(i)); //add all cards from discard pile to cards
		}

		discards.clear(); //clear discard pile

		randomizeArray(cards); //randomize content
	}

	/**
	 * The function adds a card to the discarded pile
	 * @param card the current card to be added to discards
	 * */

	public void addCard(Card card) {
		discards.add(card);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}


	public Card getCurrent (Player player, Board b, int loc, Bank bank) {
		if(cards.size()>0) //if deck not yet empty
			return cards.get(cards.size()-1);
		else
		{
			shuffle(); //reset with cards from discard set
			drawCard(player, b, loc, bank); //call draw again
		}
		return cards.get(cards.size()-1);
	}
}
