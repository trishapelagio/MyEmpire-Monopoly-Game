import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.awt.event.ActionListener;


public class EmpireGUI extends JFrame {
    private JLayeredPane panel1;
    private JLayeredPane panel2;
    private JLayeredPane mainFrame;

    private JPanel panel1North;
    private JPanel panel1South;
    private JPanel panel1East;
    private JPanel panel1West;
    private JPanel panel1Center;
    private JLayeredPane panel1BG;

    private JLabel setup;

    private JLayeredPane buttons;

    private JScrollPane sList;

    private JLabel spaceNotification;
    private JLabel payNotification;
    private JLabel registerBackground;
    private JLabel menuBackground;

    private JLabel gameOver;
    private JButton dice;
    private JLabel chanceDice ;
    private JLabel bank;
    private JLabel bankBalance;

    private ArrayList<JLabel> panel1Spaces;
    private ArrayList<JLabel> players;
    private ArrayList<JLabel> winners;
    private ArrayList<JLabel> playerNames;

    private JButton start;
    private JButton next;
    private JButton spaceConfirm;

    private JButton payRent;
    private JButton trade;
    private JButton purchase;
    private JButton doNothing;
    private JButton develop;

    private JButton proceed;
    private JButton charInput;

    private JLabel cards;

    private JButton drawCard;

    private JButton yes;
    private JButton no;
    private JButton tradeConfirm;
    private JButton buffConfirm;

    private JButton useCard;
    private JButton nextTurn;
    private ArrayList<JButton> playerInfo;
    private ArrayList<JLabel> playerInfoNames;

    private JComboBox nplayers;

    private ArrayList<JTextField> fieldNames;
    private JTextField spaceInput;

    private JTextArea spaceList;

    private ArrayList<String> names;
    private ArrayList<ImageIcon> playerImages;
    private ArrayList<ImageIcon> playerIcons;
    private ArrayList<ImageIcon> diceImages;

    private int n;

    public EmpireGUI() {
        
        setTitle("myEmpire");
        setSize(1440, 750);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        initializeStart();
        refresh(panel1);
    }

    public void initializeStart() {
        panel1 = new JLayeredPane();
        panel1.setPreferredSize(new Dimension(1440,720));

        panel1.setBounds(0,0,1440,720);

        next = new JButton(new ImageIcon("GUI Photos/next.png"));
        next.setBounds(730,150, 250, 70);
        next.setBorderPainted(false);
        next.setBackground(new Color(203,255,219));

        start = new JButton(new ImageIcon("GUI Photos/start.png"));
        start.setBounds(630,510, 250, 70);
        start.setBorderPainted(false);
        start.setBackground(new Color(203,255,219));

        payRent = new JButton(new ImageIcon("GUI Photos/buttons/pay rent.png"));
        payRent.setBounds(230,400,250,70);

        purchase = new JButton(new ImageIcon("GUI Photos/buttons/purchase.png"));
        purchase.setBounds(230,480,250,70);


        playerInfo = new ArrayList<>();
        playerInfoNames = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            playerInfo.add(new JButton(new ImageIcon()));
            playerInfo.get(i).setBounds(590, 370 + (i * 85), 60, 60);
            playerInfoNames.add(new JLabel());
            playerInfoNames.get(i).setBounds(600, 370 + (i * 85 + 40), 100,50 );
        }

        trade = new JButton(new ImageIcon("GUI Photos/buttons/trade.png"));
        trade.setBounds(230,480,250,70);

        develop = new JButton(new ImageIcon("GUI Photos/buttons/develop.png"));
        develop.setBounds(230,400,250,70);

        doNothing = new JButton(new ImageIcon("GUI Photos/buttons/donothing.png"));
        doNothing.setBounds(230,570,250,70);

        cards =  new JLabel();
        cards.setBounds(300, 450,136,239);

        proceed = new JButton(new ImageIcon("GUI Photos/buttons/proceed.png"));
        proceed.setBounds(320,400,100,30);

        buffConfirm = new JButton(new ImageIcon("GUI Photos/buttons/confirm.png"));
        buffConfirm.setBounds(230,400,250,70);

        charInput= new JButton(new ImageIcon("GUI Photos/buttons/confirm.png"));
        charInput.setBounds(230,400,250,70);

        yes = new JButton(new ImageIcon("GUI Photos/buttons/yes.png"));
        yes.setBounds(230,530,250,70);
        no = new JButton (new ImageIcon("GUI Photos/buttons/no.png"));
        no.setBounds(230,610,250,70);

        drawCard = new JButton(new ImageIcon("GUI Photos/buttons/draw card.png"));
        drawCard.setBounds(230,400,250,70);

        useCard = new JButton(new ImageIcon("GUI Photos/buttons/use card.png"));
        useCard.setBounds(230,500,250,70);

        nextTurn = new JButton(new ImageIcon("GUI Photos/buttons/end turn.png"));
        nextTurn.setBounds(230,560,250,70);

        buttons = new JLayeredPane();
        buttons.setBounds(0,0,720,720);

        spaceConfirm = new JButton(new ImageIcon("GUI Photos/buttons/confirm.png"));
        spaceConfirm.setBounds(245,400,250,70);

        tradeConfirm = new JButton(new ImageIcon("GUI Photos/buttons/confirm.png"));
        tradeConfirm.setBounds(230,400,250,70);

        dice = new JButton();
        dice.setBounds(20,200,82,82);
        dice.setBackground(new Color(203,255,219));
        dice.setOpaque(false);
        dice.setBorderPainted(false);

        chanceDice = new JLabel();
        chanceDice.setBounds(20,200,82,82);


        menuBackground = new JLabel();
        menuBackground.setIcon(new ImageIcon("GUI Photos/menu.png"));
        menuBackground.setBounds(0,0,1440,720);
        menuBackground.add(start);

        bankBalance = new JLabel("BALANCE");
        bank = new JLabel(new ImageIcon("GUI Photos/bank.png"));


        diceImages = new ArrayList<> ();
        diceImages.add(new ImageIcon("GUI Photos/Dice/1.png"));
        diceImages.add(new ImageIcon("GUI Photos/Dice/2.png"));
        diceImages.add(new ImageIcon("GUI Photos/Dice/3.png"));
        diceImages.add(new ImageIcon("GUI Photos/Dice/4.png"));
        diceImages.add(new ImageIcon("GUI Photos/Dice/5.png"));
        diceImages.add(new ImageIcon("GUI Photos/Dice/6.png"));

        String[] num = {"2","3","4"};
        nplayers = new JComboBox(num);
        nplayers.setBounds(680,400,100,20);
        menuBackground.add(nplayers);
        menuBackground.add(start);
        panel1.add(menuBackground, CENTER_ALIGNMENT);

        add(panel1);

    }

    public void registerPlayers(int n) {
        this.n = n;
        panel1.remove(menuBackground);
        playerImages = new ArrayList<> ();
        playerIcons = new ArrayList<> ();
        players = new ArrayList<>();

        registerBackground = new JLabel();
        fieldNames = new ArrayList<>();

        if(n == 2) {
            registerBackground.setIcon(new ImageIcon("GUI Photos/Register/2.png"));
            registerBackground.setBounds(0, 0, 1440, 720);
            panel1.add(registerBackground, CENTER_ALIGNMENT);

            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());

            fieldNames.get(0).setBounds(630,270,180,40);
            fieldNames.get(1).setBounds(630,370,180,40);

            playerImages.add(new ImageIcon("GUI Photos/Players/icons/finn.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/jake.png"));

            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/finn.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/jake.png"));

        }

        else if (n == 3) {
            registerBackground.setIcon(new ImageIcon("GUI Photos/Register/3.png"));
            registerBackground.setBounds(0,0,1440,720);
            panel1.add(registerBackground);
            
            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());

            fieldNames.get(0).setBounds(630,270,180,40);
            fieldNames.get(1).setBounds(630,370,180,40);
            fieldNames.get(2).setBounds(630,470,180,40);

            playerImages.add(new ImageIcon("GUI Photos/Players/icons/finn.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/jake.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/bubblegum.png"));

            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/finn.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/jake.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/bubblegum.png"));

        }
        else {
            registerBackground.setIcon(new ImageIcon("GUI Photos/Register/4.png"));
            registerBackground.setBounds(0,0,1440,720);
            panel1.add(registerBackground);

            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());
            fieldNames.add(new JTextField());

            fieldNames.get(0).setBounds(630,270,180,40);
            fieldNames.get(1).setBounds(630,370,180,40);
            fieldNames.get(2).setBounds(630,470,180,40);
            fieldNames.get(3).setBounds(630,570,180,40);

            playerImages.add(new ImageIcon("GUI Photos/Players/icons/finn.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/jake.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/bubblegum.png"));
            playerImages.add(new ImageIcon("GUI Photos/Players/icons/marceline.png"));

            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/finn.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/jake.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/bubblegum.png"));
            playerIcons.add(new ImageIcon("GUI Photos/Players/dp/marceline.png"));
        }

        for(int i = 0; i < n; i++) { // board icons
            players.add(new JLabel(playerIcons.get(i)));
            players.get(i).setSize(82,82);
            playerInfo.get(i).setIcon(playerImages.get(i));
            playerInfo.get(i).setBackground(new Color(203,255,219));
            playerInfo.get(i).setBorderPainted(false);
            playerInfo.get(i).setOpaque(false);
        }

        for (int i=0; i<n ; i++) {
            registerBackground.add(fieldNames.get(i));
        }

        registerBackground.add(next);

        refresh(panel1);

    }

    public void endScreen(ArrayList<String> names) {
        gameOver = new JLabel(new ImageIcon("GUI Photos/gameover.png"));
        winners = new ArrayList<>();
        for(int i = 0; i < names.size(); i++ ) {
            winners.add(new JLabel(i+1 +". "+ names.get(i)));
            winners.get(i).setBounds(590, 320+ (50*i), 400,50);
            gameOver.add(winners.get(i));
        }

        mainFrame.removeAll();

        mainFrame.add(gameOver);
        refresh(mainFrame);
    }

    public ArrayList<String> getNames(){
        names = new ArrayList<>();
        for(int i=0;i<fieldNames.size();i++) {
            names.add(fieldNames.get(i).getText());
            System.out.println(names.get(i));
        }
        return names;
    }

    public void addListeners(EventListener listener) {
        start.addActionListener((ActionListener) listener);
        next.addActionListener((ActionListener) listener);
        spaceConfirm.addActionListener((ActionListener) listener);
        payRent.addActionListener((ActionListener) listener);
        trade.addActionListener((ActionListener) listener);
        doNothing.addActionListener((ActionListener) listener);
        purchase.addActionListener((ActionListener) listener);
        develop.addActionListener((ActionListener) listener);
        nextTurn.addActionListener((ActionListener) listener);
        drawCard.addActionListener((ActionListener) listener);
        dice.addActionListener((ActionListener) listener);
        tradeConfirm.addActionListener((ActionListener) listener);
        proceed.addActionListener((ActionListener) listener);
        yes.addActionListener((ActionListener) listener);
        no.addActionListener((ActionListener) listener);
        useCard.addActionListener((ActionListener) listener);

        for (int i = 0; i < playerInfo.size(); i++)
            playerInfo.get(i).addActionListener((ActionListener) listener);

        buffConfirm.addActionListener((ActionListener) listener);
        charInput.addActionListener((ActionListener) listener);
    }

    public void setupBoard() {
        setup = new JLabel();
        setup.setBounds(0,0,720,720);

        spaceNotification = new JLabel("Input Index of Chosen Space");
        spaceNotification.setBounds(280,340,300,20);

        payNotification = new JLabel();
        payNotification.setBounds(280,380,300,20);

        spaceList = new JTextArea();
        spaceList.setEditable(false);

        spaceList.setBackground(new Color(203,255,219));

        String[] spaces = {"0 : Almond Drive", "1 : Kasoy Street", "2 : Rodeo Drive", "3 : Orange Street",
                "4 : Ventura Street", "5 : Juan Luna", "6 : Ylaya", "7 : J.Abad Santos", "8 : Madison", "9 : Annapolis", "10 : Connecticut",
                "11 : Bougainvilla", "12 : Dama de Noche", "13 : Acacia", "14 : Solar Street", "15 : Galaxy Street", "16 : 9th Street",
                "17 : 5th Avenue", "18 : Railroad 1", "19 : Railroad 2", "20 : Railroad 3", "21 : Utility 1", "22 : Utility 2", "23 : Chance 1",
                "24 : Chance 2", "25 : Chance 3", "26 : Luxury Tax", "27 : Income Tax"};

        spaceList.append("List of Available Spaces: \n\n");

        for (int i=0;i<28;i++)
            spaceList.append(spaces[i] + "\n");

        sList = new JScrollPane(spaceList);
        sList.setBounds(160, 120, 400, 150);

        spaceInput = new JTextField();
        spaceInput.setBounds(320,360,100,30);

        setup.add(sList);
        setup.add(spaceConfirm);
        setup.add(spaceNotification);
        setup.add(payNotification);
        setup.add(spaceInput);
        panel2.add(setup);
    }

    public void setCardImage(String name)
    {
        cards.setIcon(new ImageIcon("GUI Photos/Cards/"+name+".png"));
    }

    public void startGame() {
        panel1.remove(registerBackground);

        mainFrame = new JLayeredPane();
        mainFrame.setBounds(0, 0, 1440, 720);
        mainFrame.setLayout(new GridLayout(1, 2));

        panel1 = new JLayeredPane();
        panel1.setLayout(new BorderLayout());
        panel1.setBounds(0, 0, 720, 720);
        panel1.setPreferredSize(new Dimension(720, 70));

        panel2 = new JLayeredPane();
        panel2.setBounds(0, 0, 720, 720);
        panel2.setPreferredSize(new Dimension(720, 720));

        JLabel bg = new JLabel();
        bg.setIcon(new ImageIcon("GUI Photos/Main Screen/board.png"));
        bg.setBounds(0, 0, 720, 720);

        panel1BG = new JLayeredPane();
        panel1BG.setPreferredSize(new Dimension(720, 720));
        panel1BG.setBounds(0, 0, 720, 720);
        panel1BG.add(bg);

        JLabel sidePane = new JLabel();
        sidePane.setIcon(new ImageIcon("GUI Photos/Main Screen/info.png"));
        sidePane.setBounds(0, 0, 720, 720);

        panel1North = new JPanel(new GridLayout(1, 9));
        panel1North.setBounds(0, 0, 738, 102);
        panel1South = new JPanel(new GridLayout(1, 9));
        panel1South.setBounds(0, 0, 738, 82);
        panel1East = new JPanel(new GridLayout(7, 1));
        panel1East.setBounds(0, 0, 82, 574);
        panel1West = new JPanel(new GridLayout(7, 1));
        panel1West.setBounds(0, 0, 82, 574);
        panel1North.setOpaque(false);
        panel1South.setOpaque(false);
        panel1East.setOpaque(false);
        panel1West.setOpaque(false);

        panel1Center = new JPanel();
        panel1Center.setBounds(0, 0, 556, 556);

        panel1Spaces = new ArrayList<>();

        ArrayList<JLabel> southTemp = new ArrayList<> (9);
        for(int i = 0; i < 9; i++) {
            southTemp.add(new JLabel());
        }

        ArrayList<JLabel> westTemp = new ArrayList<> (7);
        for(int i = 0; i < 7; i++) {
            westTemp.add(new JLabel());
        }

        int j=0,k=0;
        for (int i = 0; i < 32; i++) {
            if (0 <= i && i <= 8) {
                panel1Spaces.add(new JLabel());
                panel1Spaces.get(i).setBounds(0, 0, 82, 82);
                panel1North.add(panel1Spaces.get(i));
            } else if (9 <= i && i <= 15) {
                panel1Spaces.add(new JLabel());
                panel1Spaces.get(i).setBounds(0, 0, 82, 82);
                panel1East.add(panel1Spaces.get(i));
            } else if (16 <= i && i <= 24) {
                panel1Spaces.add(new JLabel());
                panel1Spaces.get(i).setBounds(0, 0, 82, 82);
                southTemp.set(8-j, panel1Spaces.get(i));
                j++;
            } else {
                panel1Spaces.add(new JLabel());
                panel1Spaces.get(i).setBounds(0, 0, 82, 82);
                westTemp.set(6-k, panel1Spaces.get(i));
                k++;
            }

            panel1Spaces.get(i).setOpaque(false);

            if(i==0)
                panel1Spaces.get(i).setIcon(new ImageIcon(("GUI Photos/Spaces/start.png")));
            else if(i==8)
                panel1Spaces.get(i).setIcon(new ImageIcon(("GUI Photos/Spaces/Free Parking.png")));
            else if(i==16)
                panel1Spaces.get(i).setIcon(new ImageIcon(("GUI Photos/Spaces/Community Service.png")));
            else if(i==24)
                panel1Spaces.get(i).setIcon(new ImageIcon(("GUI Photos/Spaces/Jail.png")));
            else
                panel1Spaces.get(i).setIcon(new ImageIcon(("GUI Photos/Spaces/unassigned.png")));
        }

        for(int i = 0; i < 9; i++) {
            panel1South.add(southTemp.get(i));
        }
        for(int i = 0; i < 7; i++) {
            panel1West.add(westTemp.get(i));
        }




            panel1.add(panel1North, BorderLayout.NORTH);
            panel1.add(panel1South, BorderLayout.SOUTH);
            panel1.add(panel1East, BorderLayout.EAST);
            panel1.add(panel1West, BorderLayout.WEST);
            panel1.moveToFront(panel1East);
            panel1.moveToFront(panel1South);
            panel1.moveToFront(panel1West);
            panel1.moveToFront(panel1North);
            panel1.moveToBack(bg);
            panel2.add(sidePane);
            panel2.moveToFront(sidePane);
            setupBoard();

            panel1BG.add(panel1);
            panel1BG.moveToFront(panel1);

            mainFrame.add(panel1BG);
            panel1.moveToFront(panel1Spaces.get(0));
            mainFrame.add(panel2);
            panel2.moveToFront(setup);
            this.getRootPane().setDefaultButton(spaceConfirm);

            add(mainFrame);
            refresh(mainFrame);



    }

    public void game() {
        bank.setBounds(250,350,200,195);
        bankBalance.setBounds(320,480,200,100);
        panel1.add(bankBalance);
        panel1.add(bank);
        panel1.moveToFront(bank);
        panel1.moveToFront(bankBalance);


        dice.setIcon(diceImages.get(0));
        dice.setEnabled(true);

        chanceDice.setIcon(diceImages.get(0));
        panel1.add(chanceDice, BorderLayout.CENTER);
        chanceDice.setVisible(false);

        for(int i = 0; i < n; i++) {
            panel2.add(playerInfo.get(i));
            panel2.moveToFront(playerInfo.get(i));
            panel2.add(playerInfoNames.get(i));
            panel2.moveToFront(playerInfoNames.get(i));
        }
        setup.remove(spaceConfirm);
        setup.remove(spaceInput);

        panel2.add(dice);
        panel2.moveToFront(dice);
        panel2.add(buttons);
        panel2.moveToFront(buttons);
        panel2.moveToFront(buttons);

        refresh(panel2);
        refresh(mainFrame);
    }


    public void setDice(int n) {
        dice.setIcon(diceImages.get(n-1));
        refresh(mainFrame);
    }

    public void setChanceDice(int n) {
        chanceDice.setIcon(diceImages.get(n-1));
        refresh(mainFrame);
    }

    public JButton getDice() {
        return dice;
    }
    public JLabel getChanceDice() {
        return chanceDice;
    }

    public void setPlayerInfoNames(ArrayList<String> names) {
        for (int i = 0; i < names.size(); i++) {
            playerInfoNames.get(i).setText(names.get(i));
        }
    }

    public void removeButtons() {
        buttons.removeAll();
        refresh(panel2);
    }

    public void unownedOptions() {
        removeButtons();
        buttons.add(purchase);
        buttons.add(doNothing);
        refresh(buttons);
    }

    public void selfownedOptions() {
        removeButtons();
        buttons.add(develop);
        buttons.add(doNothing);
        refresh(buttons);
    }

    public void otherownedOptions() {
        removeButtons();
        buttons.add(trade);
        buttons.add(payRent);
        refresh(buttons);
    }

    public void jailOptions() {
        removeButtons();
        buttons.add(useCard);
        refresh(buttons);
    }

    public void chanceOptions() {
        removeButtons();
        buttons.add(drawCard);
        refresh(buttons);
    }
    public void buffProperty() {
        removeButtons();
        buttons.add(spaceInput);
        buttons.add(buffConfirm);
        refresh(mainFrame);
    }

    public void inputChar() {
        removeButtons();
        buttons.add(spaceInput);
        buttons.add(charInput);
        refresh(mainFrame);
    }

    public void startTrade() {
        removeButtons();
        buttons.add(spaceInput);
        buttons.add(tradeConfirm);

        refresh(buttons);
    }

    public void acceptTrade () {
        removeButtons();
        buttons.add(yes);
        buttons.add(no);
        refresh(buttons);
    }

    public void nextTurn() {
        removeButtons();
        buttons.add(nextTurn);
        refresh(buttons);
    }

    public void showCard() {
        removeButtons();
        buttons.add(cards);
        buttons.add(proceed);
        refresh(buttons);
    }


    public void addPlayerIcon(int playerIndex, int n) {
        panel1Spaces.get(n).add(players.get(playerIndex));
        refresh(mainFrame);
    }



    public void removePlayerIcon(int playerIndex, int n) {
        panel1Spaces.get(n).remove(players.get(playerIndex));
        refresh(mainFrame);
    }


    public void refresh(JLayeredPane x) {
        x.revalidate();
        x.repaint();
    }

    public JButton getProceed()
    {
        return proceed;
    }

    public JButton getNext(){
        return next;
    }

    public JButton getStart() {
        return start;
    }

    public JButton getSpaceConfirm()
    {
        return spaceConfirm;
    }

    public JTextField getSpaceInput()
    {
        return spaceInput;
    }

    public JComboBox getNplayers() {
        return nplayers;
    }

    public ArrayList<JLabel> getPanel1Spaces()
    {
        return panel1Spaces;
    }

    public JTextArea getSpaceList()
    {
        return spaceList;
    }
    
    public void setPayNotification(String text)
    {
        payNotification.setText(text);
    }


    public void setSpaceList(ArrayList<Integer> indices)
    {
        String[] spaces = {"0 : Almond Drive", "1 : Kasoy Street", "2 : Rodeo Drive", "3 : Orange Street",
                "4 : Ventura Street", "5 : Juan Luna", "6 : Ylaya", "7 : J.Abad Santos", "8 : Madison", "9 : Annapolis", "10 : Connecticut",
                "11 : Bougainvilla", "12 : Dama de Noche", "13 : Acacia", "14 : Solar Street", "15 : Galaxy Street", "16 : 9th Street",
                "17 : 5th Avenue", "18 : Railroad 1", "19 : Railroad 2", "20 : Railroad 3", "21 : Utility 1", "22 : Utility 2", "23 : Chance 1",
                "24 : Chance 2", "25 : Chance 3", "26 : Luxury Tax", "27 : Income Tax"};
        spaceList.setText("");
        spaceList.append("List of Available Spaces: \n\n");
        for (int i=0;i<28;i++) {
            if (!indices.contains(i))
                spaceList.append(spaces[i] + "\n");
        }
    }

    public JLabel getSpaceNotification()
    {
        return spaceNotification;
    }

    public void setSpaceNotification(String text)
    {
        spaceNotification.setText(text);
    }

    public void setSpaceList(String text)
    {
        spaceList.setText(text);
    }

    public JButton getBuffConfirm() {
        return buffConfirm;
    }
    public JLayeredPane getButtons() {
        return buttons;
    }

    public JButton getPayRent() {
        return payRent;
    }

    public JButton getTrade() {
        return trade;
    }

    public JButton getPurchase() {
        return purchase;
    }

    public JButton getDoNothing() {
        return doNothing;
    }

    public JButton getDevelop() {
        return develop;
    }
    public JButton getTradeConfirm() {
        return tradeConfirm;
    }

    public JLabel getCards() {
        return cards;
    }

    public JButton getDrawCard() {
        return drawCard;
    }

    public JButton getYes() {
        return yes;
    }

    public JButton getNo() {
        return no;
    }

    public JButton getUseCard() {
        return useCard;
    }

    public JButton getCharInput()
    {
        return charInput;
    }

    public JButton getNextTurn() {
        return nextTurn;
    }
    public ArrayList<JButton> getPlayerInfo() {
        return playerInfo;
    }

    public JLabel getBankBalance() {
        return bankBalance;
    }
}
