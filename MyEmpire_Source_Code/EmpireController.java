import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import Model.*;
import Cards.*;


public class EmpireController implements ActionListener {
    private EmpireGUI gui;
    private Board board;
    private ArrayList<Integer> indices;
    private ArrayList<String> names;
    private Dice dice;
    private Bank bank;
    private int numPlayers;
    private Card card;
    private Player[] winners;

    public EmpireController(EmpireGUI gui) {
        this.gui = gui;
        gui.addListeners(this);
        names = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public void actionPerformed(ActionEvent ae) {
        JButton b = (JButton) ae.getSource();

        if (b.equals(gui.getStart())) {
            String value = gui.getNplayers().getSelectedItem().toString();
            gui.registerPlayers(Integer.parseInt(value));
            System.out.println(value);
        }
        else if (b.equals(gui.getNext())) {
            names = gui.getNames();
            gui.setPlayerInfoNames(names);
            numPlayers = gui.getNames().size();
            gui.startGame();
            indices.add(28);
            gui.getPanel1Spaces().get(indices.size()).setIcon(new ImageIcon("GUI Photos/Spaces/empty.png"));
        }
        else if (b.equals(gui.getSpaceConfirm())) {


            if (indices.size() == 8) { //Free Parking
                indices.add(29);
                System.out.println(indices.get(indices.size() - 1));
            }
            else if (indices.size() == 16) { //Community Service
                indices.add(30);
                gui.getPanel1Spaces().get(indices.size()).setIcon(new ImageIcon("GUI Photos/Spaces/empty.png"));
            }
            else if (indices.size() == 24) { //Jail
                indices.add(31);
                gui.getPanel1Spaces().get(indices.size()).setIcon(new ImageIcon("GUI Photos/Spaces/empty.png"));
            }

            if ((0 <= getIndex() && getIndex() < 28) && !indices.contains(getIndex())) {
                indices.add(getIndex());
                gui.setSpaceList(indices);
                System.out.println(indices.get(indices.size() - 1));
                gui.getPanel1Spaces().get(indices.size() - 1).setIcon(new ImageIcon("GUI Photos/Spaces/" + getIndex() + ".png"));
                gui.getSpaceInput().setText("");
                if (indices.size() == 8 || indices.size() == 16 || indices.size() == 24)
                    gui.getPanel1Spaces().get(indices.size() + 1).setIcon(new ImageIcon("GUI Photos/Spaces/empty.png"));
                else if (indices.size() != 32) {
                    gui.getPanel1Spaces().get(indices.size()).setIcon(new ImageIcon("GUI Photos/Spaces/empty.png"));
                }
            }

            if (indices.size() == 32) { // After choosing spaces is finished
                board = new Board(names, indices);
                gui.game();
                dice = new Dice();
                bank = new Bank(numPlayers);
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));

                for (int i = 0; i < board.getTotalPlayer(); i++)
                    gui.addPlayerIcon(i, 0);

                updatePlayerInfo();

                gui.setSpaceNotification("");

            }

        }
        else if (b.equals(gui.getDice())) { // Rolling dice starts the turn
                gui.getBankBalance().setText(Double.toString(bank.getAmount())); // Update bank balance


                Player player = board.getCurrentPlayer();
                gui.setPayNotification("");

                gui.setSpaceNotification(player.getName() + " is rolling dice.");

                player.setDice(dice.roll());
                gui.setDice(player.getDie());

                gui.setSpaceNotification("Dice : " + player.getDie());

                int newpos = (player.getLocation() + player.getDie()) % board.getSize(); //so that index will not go over bounds, used for method addTraffics

                int i = board.getCurrentPlayer().getLocation();
                Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, newpos); // Moving animation for players
                Thread move = new Thread(x);
                move.start();

                player.addTraffics(board, player.getLocation(), newpos); //adds traffic to all Property spaces passed

                player.setLocation(player.getLocation() + player.getDie()); //do not modulo yet, to check if it passed Start

                if (player.getLocation() > 32) { // check if Player passed Start
                    bank.addTo(player, 200);
                    gui.setSpaceNotification(player.getName() + " collected money from bank. ");
                    gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                }

                player.setLocation(player.getLocation() % board.getSize()); //so that location index will not go over bounds

                updatePlayerInfo();

                gui.getDice().setEnabled(false);

                gui.setSpaceNotification("New loc : " + board.getCurrentSpace(player.getLocation()).getName());


            if (board.getCurrentSpace(player.getLocation()) instanceof Property) { // PLAYER LANDS ON PROPERTY
                if (player.getProperties().contains(board.getCurrentSpace(player.getLocation()))) { // if user owns property
                    gui.selfownedOptions();
                    gui.setSpaceNotification("You own this property.");
                }
                else if (((Property) board.getCurrentSpace(player.getLocation())).isOwned()) { //if owned property
                    gui.otherownedOptions();
                    gui.setSpaceNotification("Someone else owns this property.");
                }
                else { // if no one owns property
                    gui.unownedOptions();
                    gui.setSpaceNotification("Property is unowned.");
                }
            }
            else if (board.getCurrentSpace(player.getLocation()) instanceof Railroad) {
                if (player.getRailroads().contains(board.getCurrentSpace(player.getLocation()))) { // if user owns
                    gui.setSpaceNotification("You Own This Railroad. End of Turn.");
                    gui.nextTurn();
                }
                else if (((Railroad) board.getCurrentSpace(player.getLocation())).isOwned()) { //if owned, pay rent
                    gui.setSpaceNotification(((Railroad) board.getCurrentSpace(player.getLocation())).getOwner().getName() + " owns this railroad. Pay Rent.");
                    double amount = ((Railroad) board.getCurrentSpace(player.getLocation())).getPrice();
                    if(amount >= player.getBalance()) {
                        player.payPerson(amount, ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner());
                        gui.setSpaceNotification("You are broke, you cannot pay anymore.");
                    }
                    else {
                        player.payPerson(amount, ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner());
                        gui.setPayNotification("You paid " + ((Railroad) board.getCurrentSpace(player.getLocation())).getPrice()+ " to \n" + ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner().getName());
                    }
                    gui.nextTurn();
                }
                else { //if not owned
                    gui.unownedOptions();
                }
            }
            else if (board.getCurrentSpace(player.getLocation()) instanceof Utility) {
                if (player.getUtilities().contains(board.getCurrentSpace(player.getLocation()))) { // if user owns
                    gui.setSpaceNotification("You Own This Utility. End of Turn.");
                    gui.nextTurn();
                }
                else if (((Utility) board.getCurrentSpace(player.getLocation())).isOwned()) { //if owned, pay rent
                    gui.setSpaceNotification(((Utility) board.getCurrentSpace(player.getLocation())).getOwner().getName() + " owns this utility.\nPay Rent.");
                    if (((Utility) board.getCurrentSpace(player.getLocation())).getPrice() > player.getBalance())
                    {
                        player.payPerson(((Utility) board.getCurrentSpace(player.getLocation())).getPrice(), ((Utility) board.getCurrentSpace(player.getLocation())).getOwner());
                        gui.setSpaceNotification("You are broke, you cannot pay anymore.");
                    }
                    else {
                        player.payPerson(((Utility) board.getCurrentSpace(player.getLocation())).getPrice(), ((Utility) board.getCurrentSpace(player.getLocation())).getOwner());
                        gui.setPayNotification("You paid " + ((Utility) board.getCurrentSpace(player.getLocation())).getPrice() + " to \n" + ((Utility) board.getCurrentSpace(player.getLocation())).getOwner().getName());
                    }
                    gui.nextTurn();
                }
                else { //if not owned
                    gui.unownedOptions();
                }
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Chance")) { //if landed on chance, draw card
                gui.chanceOptions();
                gui.setPayNotification("Size of Deck : " + board.getDeck().getCards().size());
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Luxury Tax")) { // if landed on luxury tax, pay
                gui.setSpaceNotification("Luxury Tax, pay 75");
                player.payBank(75, bank);
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                gui.nextTurn();
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Income Tax")) { // if landed on income tax, pay

                if (200 > (player.getBalance() * .10)) {
                    gui.setSpaceNotification("Income Tax, pay 200");
                    player.payBank(200, bank);
                    gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                    gui.nextTurn();
                } else {
                    gui.setSpaceNotification("Income Tax, pay " + player.getBalance() * .10);
                    player.payBank(player.getBalance() * .10, bank);
                    gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                    gui.nextTurn();
                }
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Start")) { //collect 200 from bank
                gui.setSpaceNotification("Start! Collect 200 from bank.");
                bank.addTo(player, 200);
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                gui.nextTurn();
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Free Parking")) { //wait for next turn
                gui.setSpaceNotification("Free Parking, wait for next turn.");
                gui.nextTurn();
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Community Service")) { // pay 50 to bank
                gui.setSpaceNotification("Community Service, pay 50 to bank.");
                player.payBank(50, bank);
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                gui.nextTurn();
            }
            else if (board.getCurrentSpace(player.getLocation()).getType().equals("Jail")) { //get jailed
                player.setJail(true);
                if (player.getChance().size() != 0) { //if chance card is owned, get out of jail
                    gui.setSpaceNotification("You are in Jail, use your Chance Card.");
                    gui.jailOptions();
                }
                else {
                    gui.setSpaceNotification("You are in Jail! You bad man!");
                    gui.nextTurn();
                }
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
            }
        }
        else if (b.equals(gui.getPurchase())) {
            if (board.getCurrentSpace(board.getCurrentPlayer().getLocation()) instanceof Property) { // Purchasing property
                if (board.getCurrentPlayer().purchaseSpace(board.getCurrentSpace(board.getCurrentPlayer().getLocation()), bank)) {
                    gui.setPayNotification("You paid bank " + ((Property) board.getCurrentSpace(board.getCurrentPlayer().getLocation())).getPrice() + ".");
                    gui.nextTurn();
                }
                else
                    gui.setPayNotification("The property cannot be bought.");
            }
            else if (board.getCurrentSpace(board.getCurrentPlayer().getLocation()) instanceof Railroad) { // Purchasing railroad
                if (board.getCurrentPlayer().purchaseSpace(board.getCurrentSpace(board.getCurrentPlayer().getLocation()), bank)) {
                    gui.setPayNotification("You paid bank 200.");
                    gui.nextTurn();
                }
                else
                    gui.setPayNotification("The railroad cannot be bought");
            }
            else { // Purchasing utility
                if (board.getCurrentPlayer().purchaseSpace(board.getCurrentSpace(board.getCurrentPlayer().getLocation()), bank)) {
                    gui.setPayNotification("You paid bank 150.");
                    gui.nextTurn();
                }
                else
                    gui.setPayNotification("The utility cannot be bought");
            }
            gui.getBankBalance().setText(Double.toString(bank.getAmount()));
            updatePlayerInfo();
        }
        else if (b.equals(gui.getPayRent())) {
            double amount;
            Player currentPlayer = board.getCurrentPlayer();
            if (board.getCurrentSpace(board.getCurrentPlayer().getLocation()) instanceof Property) {
                if (!((Property) board.getCurrentSpace(currentPlayer.getLocation())).isDoubleRent()) { // If property has double rent
                    amount = ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent();
                    if(amount > currentPlayer.getBalance()) { // Invalid
                        currentPlayer.payPerson(amount , ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner()); // payPerson includes playerBroke() and turns player into broke
                        gui.setSpaceNotification("You are broke. You cannot pay anymore");
                    }
                    else { // Valid
                        currentPlayer.payPerson(amount , ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner());
                        ((Property) board.getCurrentSpace(currentPlayer.getLocation())).addTotalRent(((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent());
                        gui.setPayNotification(amount + " paid to " + ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner().getName());
                    }
                    
                } else {
                    amount = ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent() * 2;
                    if(amount > currentPlayer.getBalance()) { // Invalid
                        currentPlayer.payPerson(((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent() * 2, ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner()); // payPerson includes playerBroke() and turns player into broke
                        gui.setSpaceNotification("You are broke. You cannot pay anymore");
                    }
                    else { // Valid
                        currentPlayer.payPerson(((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent() * 2, ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner()); //set double rent back to false
                        ((Property) board.getCurrentSpace(currentPlayer.getLocation())).setDoubleRent(false);
                        ((Property) board.getCurrentSpace(currentPlayer.getLocation())).addTotalRent(((Property) board.getCurrentSpace(currentPlayer.getLocation())).getRent() * 2);
                        gui.setPayNotification(amount + " paid to " + ((Property) board.getCurrentSpace(currentPlayer.getLocation())).getOwner().getName());
                    }
                }
            }
            gui.nextTurn();
            updatePlayerInfo();
            gui.getBankBalance().setText(Double.toString(bank.getAmount()));
        }

        else if (b.equals(gui.getDoNothing())) {
            gui.nextTurn();
            updatePlayerInfo();
        }

        else if(b.equals(gui.getTradeConfirm())){ // START OF TRADE
            Player player = board.getCurrentPlayer();

            if (player.getProperties().size() > 0) {
                if (getIndex() >= 0 && getIndex() <player.getProperties().size()) {
                    gui.acceptTrade();
                    gui.setSpaceNotification(((Property)board.getCurrentSpace(player.getLocation())).getOwner().getName()+", do you accept?");
                }
            }
            else {
                gui.setSpaceNotification("You do not have any\nproperties to trade!");
                gui.otherownedOptions();
            }

        }

        else if (b.equals(gui.getTrade())) {
            gui.startTrade();
            gui.setSpaceNotification("Choose index of chosen property.");
            updatePlayerInfo();
        }

        else if (b.equals(gui.getYes())) { //for trading
            int i = getIndex();
            Player player = board.getCurrentPlayer();
            gui.setSpaceNotification("Trade successful");
            player.trade(((Property) board.getCurrentSpace(player.getLocation())).getOwner(), board.getCurrentSpace(player.getLocation()), i);
            gui.nextTurn();
        }

        else if (b.equals(gui.getNo())) { //for trading
            gui.setSpaceNotification("Player declined your offer.");
            gui.otherownedOptions();
        }

        else if (b.equals(gui.getDevelop())) {
            Player player = board.getCurrentPlayer();

            if (player.developProperty(board.getCurrentSpace(player.getLocation()), bank)) { //if development is possible
                gui.setSpaceNotification(board.getCurrentSpace(player.getLocation()).getName() + " was developed.");
                gui.nextTurn();

            }
            else { //if development was not possible
                gui.setSpaceNotification(board.getCurrentSpace(player.getLocation()).getName() + " cannot be developed.");
                gui.nextTurn();
            }
            gui.getBankBalance().setText(Double.toString(bank.getAmount()));
            updatePlayerInfo();
        }

        else if (b.equals(gui.getUseCard())) { // to get out of jail
            Player player = board.getCurrentPlayer();
            player.getChance().get(player.getChance().size() - 1).applyEffect(player, board, player.getLocation(), bank);
            gui.nextTurn();
            updatePlayerInfo();
        }

        else if (b.equals(gui.getDrawCard())) {
            gui.setPayNotification("");

            if (board.getDeck().getCards().size()==0) //if deck is empty, notify that it was shuffled
                gui.setPayNotification("Deck was shuffled.");

            Player player = board.getCurrentPlayer();
            card = board.getDeck().drawCard(player, board, player.getLocation() , bank);
            gui.setCardImage(card.getName());
            gui.showCard();
            updatePlayerInfo();
        }

        else if (b.equals(gui.getProceed())) { // insta-use or apply effect of card
            Player player = board.getCurrentPlayer();

            if(card instanceof Category1) {
                ((Category1) card).isOwnable(player, board);
                gui.setSpaceNotification("Get out of jail free has been added to your cards." );
                gui.nextTurn();
            }
            
            else if(card instanceof Category2) {
                if(card instanceof Category2A) {
                    int i = board.getCurrentPlayer().getLocation();
                    card.applyEffect(player, board, player.getLocation(), bank);
                    if (player.getProperties().contains(board.getCurrentSpace(player.getLocation()))) { // if current player owns property
                        gui.selfownedOptions();
                        gui.setSpaceNotification("You own this property.");
                    }
                    else if (((Property) board.getCurrentSpace(player.getLocation())).isOwned()) { //if owned property
                        gui.otherownedOptions();
                        gui.setSpaceNotification("Someone else owns this property.");
                    }
                    else { // if no one owns property
                        gui.unownedOptions();
                        gui.setSpaceNotification("Property is unowned.");
                    }

                    int newpos = ((Category2)card).getNewpos();
                    Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, newpos);
                    Thread move = new Thread(x);
                    move.start();

                }

                else if(card instanceof Category2B) {
                    int i =  player.getLocation();
                    card.applyEffect(player, board, player.getLocation(), bank);

                    if (!((Utility)board.getCurrentSpace(player.getLocation())).isOwned()) {
                        gui.setSpaceNotification("The Utility is not owned.");
                        gui.unownedOptions();
                    }

                    else if (player.getUtilities().contains(board.getCurrentSpace(player.getLocation()))) { // if user owns
                        gui.setSpaceNotification("You Own This Utility. End of Turn.");
                        gui.nextTurn();
                    }

                    else {
                        int roll = ((Category2B) card).getDiceRoll();
                        gui.setSpaceNotification("The Utility is owned. Dice Roll is " + roll + ".\n Paying " + (roll * 10));
                        gui.getChanceDice().setVisible(true);
                        gui.setChanceDice(roll);
                        gui.nextTurn();
                    }

                    int newpos = ((Category2)card).getNewpos();
                    Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, newpos);
                    Thread move = new Thread(x);
                    move.start();

                    if(newpos<i)
                        gui.setPayNotification("Passed START, receive 200 from Bank.");
                }

                else {
                    int i =  player.getLocation();
                    card.applyEffect(player, board, player.getLocation(), bank);

                    if (!((Railroad)board.getCurrentSpace(player.getLocation())).isOwned()) { // if unowned
                        gui.setSpaceNotification("The Railroad is not owned.");
                        gui.unownedOptions();
                    }
                    else if (player.getRailroads().contains(board.getCurrentSpace(player.getLocation()))) { // if user owns
                        gui.setSpaceNotification("You Own This Railroad. End of Turn.");
                        gui.nextTurn();
                    }
                    else {
                        gui.setSpaceNotification(((Railroad) board.getCurrentSpace(player.getLocation())).getOwner().getName() + " owns this railroad. Pay Rent.");
                        double amount = ((Railroad) board.getCurrentSpace(player.getLocation())).getPrice();
                        if(amount >= player.getBalance()) {
                            player.payPerson(amount, ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner());
                            gui.setSpaceNotification("You are broke, you cannot pay anymore.");
                        }
                        else {
                            player.payPerson(amount, ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner());
                            gui.setPayNotification("You paid " + ((Railroad) board.getCurrentSpace(player.getLocation())).getPrice()+ " to \n" + ((Railroad) board.getCurrentSpace(player.getLocation())).getOwner().getName());
                        }
                        gui.nextTurn();
                    }


                    int newpos = ((Category2)card).getNewpos();
                    Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, newpos);
                    Thread move = new Thread(x);
                    move.start();

                    if(newpos<i)
                        gui.setPayNotification("Passed START, receive 200 from Bank.");

                }
            }
            else if(card instanceof Category3) {
                int i = board.getCurrentPlayer().getLocation();
                if(card instanceof Category3A) {
                  card.applyEffect(player, board, player.getLocation(), bank);
                }
                else {
                    card.applyEffect(player, board, player.getLocation(), bank);
                    Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, 0);
                    Thread move = new Thread(x);
                    move.start();
                }
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                gui.nextTurn();
            }

            else if(card instanceof Category4) {
                int i = board.getCurrentPlayer().getLocation();

                card.applyEffect(player, board, player.getLocation(), bank);

                int newpos = ((Category4) card).getNewpos();

                if(card instanceof Category4A) {
                    gui.setSpaceNotification("Proceeding to jail.");
                }
                else {
                    gui.setSpaceNotification("Going to nearest property");
                    if(newpos<i)
                        gui.setPayNotification("Passed START, receive 200 from Bank.");
                }
                
                Runnable x = new PlayerMove(gui, board.getCurrentPlayer(), board, i, newpos);
                Thread move = new Thread(x);
                move.start();
                gui.nextTurn();
            }
            else if(card instanceof Category5) {

                if(card instanceof Category5A || card instanceof Category5B || card instanceof Category5CA) {
                    if(player.getProperties().size() > 0) {
                        gui.setSpaceNotification("Enter Index : ");
                        gui.buffProperty();
                    }
                    else {
                        gui.setSpaceNotification("You do not own any property.");
                        gui.nextTurn();
                    }
                }
                else {
                    if (player.getRailroads().size()==0 && player.getUtilities().size()==0)
                        gui.nextTurn();
                    else {
                        gui.buffProperty();
                        gui.setSpaceNotification("Enter U or R : ");
                    }
                }
            }

            else {
                card.applyEffect(player, board, player.getLocation(), bank);
                gui.setPayNotification("You paid " + ((Category6)card).getRandomAmt());
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
                gui.nextTurn();
            }

            updatePlayerInfo();
        }

        else if(b.equals(gui.getBuffConfirm())) {
            Player player = board.getCurrentPlayer();

            if(((card instanceof Category5A) || (card instanceof Category5B) || (card instanceof Category5CA)) && 0 <= getIndex() && getIndex() < player.getProperties().size()) {
                ((Category5)card).setIndex(getIndex());
                card.applyEffect(player, board, player.getLocation(), bank);
                gui.nextTurn();
                updatePlayerInfo();
            }
            else if(card instanceof Category5CB) {
                char c = getChar();
                if (c =='u'||getChar()=='U' || c=='r'||c=='R') {
                    ((Category5CB)card).setUr(c);

                    if((c == 'u' || c == 'U') && player.getUtilities().size() > 0) {
                        gui.setSpaceNotification("Enter index : ");
                        gui.inputChar();
                    }

                    else if((c == 'r' || c == 'R') && player.getRailroads().size() > 0) {
                        gui.setSpaceNotification("Enter index : ");
                        gui.inputChar();
                    }
                    else {
                        gui.setSpaceNotification("Your chosen space type is empty.");
                    }
                }
            }

        }



        else if(b.equals(gui.getCharInput()))
        {
            Player player = board.getCurrentPlayer();
            char c =  ((Category5CB)card).getUr();
            if((c == 'u' || c=='U') && 0<=getIndex()&&getIndex()<=player.getUtilities().size()) {
                ((Category5) card).setIndex(getIndex());
                gui.nextTurn();
                card.applyEffect(player, board, player.getLocation(), bank);
                updatePlayerInfo();
            }
            else if((c == 'r' || c == 'R') && getIndex() >= 0 && getIndex() < player.getRailroads().size()) {
                ((Category5)card).setIndex(getIndex());
                gui.nextTurn();
                card.applyEffect(player, board, player.getLocation(), bank);
                updatePlayerInfo();
            }
            else {
                gui.setSpaceNotification("Input invalid.");
            }
        }

        else if (b.equals(gui.getNextTurn())) { // Starting the next turn (End of Turn)
            gui.getChanceDice().setVisible(false);
            System.out.println("Start Turn");
            gui.getDice().setEnabled(true);
            gui.removeButtons();
            gui.setPayNotification("");
            gui.setSpaceNotification("");
            board.nextTurn();
            updatePlayerInfo();
            Player player = board.getCurrentPlayer();
            if(player.isJail())
            {
                player.payBank(50, bank);
                gui.setPayNotification(board.getCurrentPlayer().getName() + ", you bailed out! Paid bank 50");
                player.setJail(false);
                if (player.playerBroke())
                    gui.setSpaceNotification("You cannot pay anymore. Insufficient Funds.");
                gui.getBankBalance().setText(Double.toString(bank.getAmount()));
            }
            if (board.isEndGame(bank))
                gui.endScreen(getWinnerNames());
        }
        // BUTTONS FOR CHECKING PLAYER INFORMATION
        else if (b.equals(gui.getPlayerInfo().get(0)))
        {
            gui.getSpaceList().setText(board.getPlayers()[0].toString());
        }

        else if (b.equals(gui.getPlayerInfo().get(1)))
        {
            gui.getSpaceList().setText(board.getPlayers()[1].toString());
        }

        else if (b.equals(gui.getPlayerInfo().get(2)))
        {
            gui.getSpaceList().setText(board.getPlayers()[2].toString());
        }

        else if (b.equals(gui.getPlayerInfo().get(3)))
        {
            gui.getSpaceList().setText(board.getPlayers()[3].toString());
        }

    }

    public ArrayList<String> getWinnerNames() {
        ArrayList<String> names = new ArrayList<>();
        Player[] rank ;
        rank = board.getWinners();
        for (int i = 0; i < board.getTotalPlayer(); i++) {
            if(!(rank[i].playerBroke())) //append winners' info
                names.add(rank[i].getName() + " | Total Worth : " + board.playerTotalWorth(rank[i]));
            else //append broke player's info
                names.add("[Broke] " + rank[i].getName() + " | Total Worth : " + board.playerTotalWorth(rank[i]));
        }
        return names;
    }

    private char getChar() { //Input for chance cards involving utilities or railroads for applying its effects

        try {
            char c;
            c = gui.getSpaceInput().getText().charAt(0);
            return c;
        }
        catch (Exception e){

        }

        return '0';

    }

    private int getIndex() { //Input to get index from Space Input Text Field
        try {
            int i;
            i = Integer.parseInt(gui.getSpaceInput().getText());
            return i;
        }

        catch (Exception e){
            System.out.println("Wrong input");
        }

        return 0;
    }

    public void updatePlayerInfo() {
        gui.getSpaceList().setText(board.getCurrentPlayer().toString());
    }
}

class PlayerMove implements Runnable { //ANIMATION
    private EmpireGUI gui;
    private Player player;
    private Board board;
    private int currentLocation;
    private int newpos;

    public PlayerMove(EmpireGUI gui, Player player, Board board, int currentLocation, int newpos) {
        this.gui = gui;
        this.player = player;
        this.board = board;
        this.currentLocation = currentLocation;
        this.newpos = newpos;
    }

    public void run() {
        int j;
        try {
            while (currentLocation!=newpos) {
                j = (currentLocation + 1) % board.getSize();
                gui.addPlayerIcon(board.getCurrentPlayer().getPlayerNum() - 1, j);
                gui.removePlayerIcon(player.getPlayerNum() - 1, currentLocation);
                currentLocation = (currentLocation + 1) % board.getSize();
                Thread.sleep(300);
            }
        }
        catch (InterruptedException e){

        }
    }
}

