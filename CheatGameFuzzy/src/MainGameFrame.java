import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainGameFrame extends JFrame{

	GameLogic gamelogic;
	GameController controller;

	Container contentPane;
	JPanel topPane;
	JPanel bottomPane;
	JPanel panel2;
	JPanel panel3;

	JLabel instructionsLabel;

	JButton startbutton;
	JLabel cheatlabel;
	JLabel playerinfolabel;
	JComboBox ranklist;
	JButton okbutton;
	JLabel pileLabel;

	JPanel cardPanel;
	JPanel checkboxPanel;
	
	JButton cheatingbutton;
	JButton notcheatingbutton;

	Color backgroundColor = new Color(0, 110, 0); //dark green
	private JLabel otherinfo;


	MainGameFrame()
	{
		super("Cheat Card Game");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,500);

		contentPane = this.getContentPane();

		topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.PAGE_AXIS)); //vertical layout

		topPane.setBackground(backgroundColor);

		bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.PAGE_AXIS)); //vertical layout

		checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.LINE_AXIS));//horizontal layout

		cardPanel = new JPanel();
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.LINE_AXIS));//horizontal layout

		playerinfolabel = new JLabel("");
		cheatlabel = new JLabel("");
		otherinfo = new JLabel("");
	}

	public void addModel(GameLogic model) 
	{
		this.gamelogic = model;
	}

	public void addController(GameController controller) 
	{
		this.controller = controller;
	}

	public void setStartDisabled() {
		startbutton.setEnabled(false);
	}
	void createAndShowGUI() 
	{

		//JButton 
		startbutton = controller.getStartButton("Start Game");
		startbutton.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(startbutton);

		//spacing
		topPane.add(Box.createRigidArea(new Dimension(0,30)));

		//gives the user the options for selecting the rank they will play
		ranklist = new JComboBox(gamelogic.getPossibleRanksToPlay());
		ranklist.setMaximumSize(new Dimension(100, 20));
		ranklist.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(ranklist);
		ranklist.setEnabled(false);

		//spacing
		topPane.add(Box.createRigidArea(new Dimension(0,10)));

		okbutton = controller.getOKButton();
		okbutton.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(okbutton);
		okbutton.setEnabled(false);
		
		//spacing
		topPane.add(Box.createRigidArea(new Dimension(0,20)));

		//contains the picture depicting the pile ( picture should not be there if pile is empty)
		pileLabel = new JLabel();
		pileLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add( pileLabel );
		
		//other game info
		
		otherinfo.setForeground(Color.white);
		otherinfo.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(otherinfo);

		//label that tells user about the cheat calls 
		cheatlabel.setForeground(Color.white);
		cheatlabel.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(cheatlabel);
		
		topPane.add(Box.createRigidArea(new Dimension(0,20)));
		
		//label that says what the last player played and other relevant info
		playerinfolabel.setForeground(Color.white);
		playerinfolabel.setAlignmentX(CENTER_ALIGNMENT);
		topPane.add(playerinfolabel);

		//gives the user instructions
		instructionsLabel = new JLabel();
		instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
		instructionsLabel.setForeground(Color.white);
		topPane.add(instructionsLabel);

		bottomPane.add(Box.createRigidArea(new Dimension(0,30)));

		//Put everything together, using the content pane's BorderLayout.
		contentPane.add(topPane, BorderLayout.CENTER); //center of screen
		contentPane.add(bottomPane, BorderLayout.PAGE_END); //bottom of screen

		this.setVisible(true); // display the window
	}
	
	public void setItsYourTurn(boolean isUsersTurn)
	{
		if(isUsersTurn)
		{
			okbutton.setEnabled(true);
			instructionsLabel.setText("Its your turn! Select 1 - 4 cards to put down, select which rank you are playing, and click OK");
	
			ranklist.setModel(new JComboBox(gamelogic.getPossibleRanksToPlay()).getModel());
			ranklist.setEnabled(true);
			
			if(cheatingbutton !=null)
			{
				cheatingbutton.setVisible(false);
				notcheatingbutton.setVisible(false);
				//cheatlabel.setText("");
			}
			
		}
		else{
			okbutton.setEnabled(false);
			ranklist.setEnabled(false);
			
			instructionsLabel.setText("Do you think they're cheating?");
			cheatingbutton.setEnabled(true);
			notcheatingbutton.setEnabled(true);
		}
	}
	
	public void displayCheatButtons(boolean b) {
		
		if(cheatingbutton == null && notcheatingbutton == null)
		{
			JPanel temp = new JPanel();
			
			cheatingbutton = controller.getCallCheatButton();
			cheatingbutton.setAlignmentX(CENTER_ALIGNMENT);
			temp.add(cheatingbutton);
			cheatingbutton.setEnabled(true);
			
			notcheatingbutton = controller.getNotCheatingButton();
			notcheatingbutton.setAlignmentX(CENTER_ALIGNMENT);
			temp.add(notcheatingbutton);
			notcheatingbutton.setEnabled(true);
			
			temp.setBackground(backgroundColor);
			topPane.add(temp);
		}
		else{
			if(b){
				cheatingbutton.setVisible(true);
				notcheatingbutton.setVisible(true);
				
			}
			else{
				cheatingbutton.setVisible(false);
				notcheatingbutton.setVisible(false);
				cheatlabel.setText("");
				instructionsLabel.setText("");
			}
		}
	}

	public void update() throws IOException { //gets called whenever any player finishes their turn

		
		if(!gamelogic.getPile().isEmpty())
		{
			//get the image for the flipped over card
			try {	
				BufferedImage myPicture = ImageIO.read(new File("cards/card-images/cardback.gif"));
				Image img = myPicture.getScaledInstance(50, 70,  java.awt.Image.SCALE_SMOOTH);

				pileLabel.setIcon(new ImageIcon(img));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			pileLabel.setIcon(null);
		}

		//output the cards that player 1 is holding.
		//to the bottom pane, add the cards that player[0] is holding
		Player human = gamelogic.getPlayers()[0];

		cardPanel.removeAll();
		checkboxPanel.removeAll();

		for(Card card: human.getHand())
		{
			BufferedImage myPicture = ImageIO.read(new File(card.getImageFilename()));
			Image img = myPicture.getScaledInstance(50, 70,  java.awt.Image.SCALE_SMOOTH);
			JLabel imagelabel = new JLabel(new ImageIcon(img));

			String cardname = String.valueOf(card.getRank()) + String.valueOf(card.getSuit());
			JCheckBox checkbox = new JCheckBox(cardname, false);
			checkbox.setBackground(backgroundColor);

			checkboxPanel.add(Box.createRigidArea(new Dimension(10,0)));
			checkboxPanel.add(checkbox);

			cardPanel.add(imagelabel);
		}

		bottomPane.add(checkboxPanel);
		bottomPane.add(cardPanel);


		checkboxPanel.setBackground(backgroundColor);
		bottomPane.setBackground(this.backgroundColor);
		this.repaint();
		this.setVisible(true);

	}

	public Move getPlayersLastMove()
	{
		//get actual cards played
		List<String> cardsplayed = getActualCardsPlayed();

		//get the rank claimed
		String selecteditem = (String) ranklist.getSelectedItem();
		char rankclaimed = selecteditem.charAt(0);

		//check if is a valid move 
		boolean isValid = isValidMove(cardsplayed, rankclaimed);

		int n = cardsplayed.size();

		return new Move(n, rankclaimed, isValid);
	}

	/*return whether or not the cards played match the claimed card number and rank*/
	private boolean isValidMove(List<String> cardsplayed, char rankclaimed)
	{
		boolean isValid = true; //assume true, 

		System.out.print("Cards played: ");
		for(String cardname: cardsplayed)
		{
			System.out.print(cardname +" ");

			char r = cardname.charAt(0);
			if ( r != rankclaimed)
			{
				isValid = false;
				break;
			}
		}
		System.out.println();
		
		//if the ranks all match the rank claimed, then isValid will still be true.
		return isValid;
	}

	/*get the actual cards the user played*/
	public List<String> getActualCardsPlayed() {

		List<String> cardsplayed = new ArrayList<String>();
		int numcards = 0;

		//for check boxes in checkboxPanel
		for(Component c : checkboxPanel.getComponents())
		{
			if(c instanceof JCheckBox)
			{
				JCheckBox cbox = (JCheckBox)c;
				if(cbox.isSelected())
				{
					if(numcards<4)
						cardsplayed.add(cbox.getText());
					numcards++;
				}
			}
		}
		return cardsplayed;
	}

	public void updateInfoLabel(String string) {

		//update the information label
		playerinfolabel.setText(string);
	}

	public void updateCheatLabel(String string){
		
		cheatlabel.setText(string);
	}
	
	public void updateOtherInfor(String string){
		this.otherinfo.setText(string);
	}



}
