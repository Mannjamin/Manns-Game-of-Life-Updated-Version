package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.backend.MGoL_Backend;
import src.init.MGoL_Init;

/* 
 * This is the Graphical User Interface Class.
 * This class is responcible for generating the main user interacgtion elements. 
 * Certain Elements of this class are also responcible for the maniplation of Backend Processing Elements within the Backend Class.
 */

public class MGoL_GUI extends JFrame implements ActionListener, ChangeListener
{
	//Declaration of Variables.
	private static final long serialVersionUID = 204971138673684451L;
	public static int speed = 3;
	public static JLabel speedLabel, generationLabel, survivorLabel, deadLabel, existLabel;
	public static Timer timer;
	public static int generationCount, survivorCount, deadCount, existenceCount;
	public static JMenuBar Menu;
	public static JToolBar StatMenu;
	public static JButton gridButton, speedButton, autofillButton, playButton,
					stopButton, resetButton, configButton, pointButton, gunButton, shipButton, unstableOneButton,
					artButton, aboutButton, helpButton, closeButton, exitButton;
	public static boolean gridVisible;
	public static boolean[][] glider, spaceship, unstOne;
	public static boolean isPoint = true;
	public static boolean isGlider = false;
	public static boolean isSpaceship = false;
	public static boolean isUnstOne = false;
	public static MGoL_Backend platform;
	public static JMenu patternMenu;
	public static JCheckBox gridCheck;
	public static JSlider secondsSelect;
	public static JDialog configFrame;
	public static JRadioButton circularCellCheck, squareCellCheck;
	public static boolean squareCell = true;
	public static boolean circleCell = false;
	public static JColorChooser tcc;
	public static Color newColor;
	public static Object size;
	
	//Main Frame Components.
	//Class Constructor.
	public MGoL_GUI()
	{	
		
		//Menu
		Menu = new JMenuBar();
		setJMenuBar(Menu);
		//Menu Styling
		Menu.setBackground(new Color(234,234,234));
		Menu.setBorder(BorderFactory.createEmptyBorder());
		//#####################################################################################################
		//Play Button
		playButton = new JButton("Play");
		Menu.add(playButton);
		playButton.addActionListener(this);
		//Play Button Styling.
		playButton.setPreferredSize(new Dimension(80, 50));
		playButton.setBackground(new Color(234,234,234));  
		playButton.setForeground(Color.BLACK);  
		playButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		playButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################    
		//Stop Button
		stopButton = new JButton("Stop");
		Menu.add(stopButton);
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);
		//Stop Button Styling
		stopButton.setPreferredSize(new Dimension(80, 50));
		stopButton.setBackground(new Color(234,234,234));  
		stopButton.setForeground(Color.BLACK);  
		stopButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		stopButton.setBorder(new LineBorder(new Color(234,234,234)));     
		
		//#####################################################################################################    
		//Reset Button
		resetButton = new JButton("Reset");
		Menu.add(resetButton); 
		resetButton.addActionListener(this);
		//Reset Button Styling
		resetButton.setPreferredSize(new Dimension(85, 30));
		resetButton.setBackground(new Color(234,234,234));  
		resetButton.setForeground(Color.BLACK);  
		resetButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		resetButton.setBorder(new LineBorder(new Color(234,234,234)));

		//##################################################################################################### 
		//Auto fill Button (Automatically Fills Random Cells)
		autofillButton = new JButton("Autofill");
		Menu.add(autofillButton,BorderLayout.CENTER);
		autofillButton.addActionListener(this);
		//Auto fill Button Styling.
		autofillButton.setPreferredSize(new Dimension(110, 30));
		autofillButton.setBackground(new Color(234,234,234));  
		autofillButton.setForeground(Color.BLACK);  
		autofillButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		autofillButton.setBorder(new LineBorder(new Color(234,234,234)));    
		
		//#####################################################################################################
		//Configuration Button
		configButton = new JButton("Configuration..."); 
		Menu.add(configButton); 
		configButton.addActionListener(this);
		//Configuration Styling
		configButton.setPreferredSize(new Dimension(200, 30));
		configButton.setBackground(new Color(234,234,234));  
		configButton.setForeground(Color.BLACK);  
		configButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		configButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//##################################################################################################### 
		//Pattern Button
		patternMenu = new JMenu("Pattern...");
		//Pattern Styling
		patternMenu.setPreferredSize(new Dimension(130, 30));
		patternMenu.setBackground(new Color(234,234,234));  
		patternMenu.setForeground(Color.BLACK);  
		patternMenu.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		patternMenu.setBorder(new LineBorder(new Color(234,234,234)));
		//#####################################################################################################
		
		//Pattern Menu Items
		JMenuItem pointButton = new JMenuItem("Default");
		JMenuItem shipButton = new JMenuItem("Ship");
		JMenuItem gunButton = new JMenuItem("Gun");
		JMenuItem unstableOneButton = new JMenuItem("Unstable 1");
		
		//#####################################################################################################
		
		//Pattern Menu Item Styles
		pointButton.setPreferredSize(new Dimension(130, 30));
		pointButton.setBackground(new Color(234,234,234));  
		pointButton.setForeground(Color.BLACK);  
		pointButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		pointButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################
		
		shipButton.setPreferredSize(new Dimension(130, 30));
		shipButton.setBackground(new Color(234,234,234));  
		shipButton.setForeground(Color.BLACK);  
		shipButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		shipButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################
		
		gunButton.setPreferredSize(new Dimension(130, 30));
		gunButton.setBackground(new Color(234,234,234));  
		gunButton.setForeground(Color.BLACK);  
		gunButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		gunButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################
		
		unstableOneButton.setPreferredSize(new Dimension(130, 30));
		unstableOneButton.setBackground(new Color(234,234,234));  
		unstableOneButton.setForeground(Color.BLACK);  
		unstableOneButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		unstableOneButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################
		
		patternMenu.add(pointButton);
		patternMenu.add(shipButton);
		patternMenu.add(gunButton);
		patternMenu.add(unstableOneButton);
			
		//#####################################################################################################
		
		Menu.add(patternMenu); 
		
		//Pattern Menu Item Action Listeners
		shipButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Create a new two dimensional array. This pattern contains a Game of Life Ship which will move vertically down the screen. 
				// This will be placed when the button is pressed and the user has pressed a point on the screen.
				spaceship = new boolean[][]{
					{true ,false,false,true ,false},
					{false,false,false,false,true },
					{true ,false,false,false,true },
					{false,true ,true ,true ,true }
				};
				//Ship Button
				shipButton.setForeground(Color.RED); // Ship Button Turns red.
				shipButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 17 
				//Gun Button
				gunButton.setForeground(Color.BLACK); // Gun Button Turns black.
				gunButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15
				//Disable Point
				pointButton.setForeground(Color.BLACK); // Point Button turns black.
				pointButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15
				
				unstableOneButton.setForeground(Color.BLACK);
				unstableOneButton.setFont(new Font("Courier", Font.PLAIN, 17));
				
				//Booleans
				isSpaceship = true;	//isSpaceship Boolean set to true.
				isGlider = false;		//isGlider Boolean set to false. 
				isPoint = false;		//isPoint Boolean set to false.
				isUnstOne = false;
			
			}
		});
		
		gunButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Create a two dimensional boolean array. This is the pattern which allows us to spawn in a gun. 
				//Essentially, once the button has been pressed, the point becomes this array below allowing us to draw the gun.
				//Due to the complexity of this boolean array, we named each section such as B1, and every column. this is a 9 x 36 drid of true and false values.
				glider = new boolean[][]{
					/*one*/		{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,false, /*b3*/ false,false,false,false,false,false, /*b4*/ false,false,false,false,false,false, /*b5*/ true ,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*two*/ 	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,false, /*b3*/ false,false,false,false,false,false, /*b4*/ false,false,false,false,true ,false, /*b5*/ true ,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*three*/	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,false, /*b3*/ true ,true ,false,false,false,false, /*b4*/ false,false,true ,true ,false,false, /*b5*/ false,false,false,false,false,false, /*b6*/ false,false,false,false,true ,true ,},
					/*four*/	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,true , /*b3*/ false,false,false,true ,false,false, /*b4*/ false,false,true ,true ,false,false, /*b5*/ false,false,false,false,false,false, /*b6*/ false,false,false,false,true ,true ,},
					/*five*/	{/*b1*/true ,true ,false,false,false,false, /*b2*/ false,false,false,false,true ,false, /*b3*/ false,false,false,false,true ,false, /*b4*/ false,false,true ,true ,false,false, /*b5*/ false,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*six*/		{/*b1*/true ,true ,false,false,false,false, /*b2*/ false,false,false,false,true ,false, /*b3*/ false,false,true ,false,true ,true , /*b4*/ false,false,false,false,true ,false, /*b5*/ true ,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*seven*/	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,true ,false, /*b3*/ false,false,false,false,true ,false, /*b4*/ false,false,false,false,false,false, /*b5*/ true ,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*eight*/	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,true , /*b3*/ false,false,false,true ,false,false, /*b4*/ false,false,false,false,false,false, /*b5*/ false,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,},
					/*nine*/ 	{/*b1*/false,false,false,false,false,false, /*b2*/ false,false,false,false,false,false, /*b3*/ true ,true ,false,false,false,false, /*b4*/ false,false,false,false,false,false, /*b5*/ false,false,false,false,false,false, /*b6*/ false,false,false,false,false,false,}
				};
				//Gun Button
				gunButton.setForeground(Color.RED); //Gun Button Turns red.
				gunButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 17
				//Spaceship
				shipButton.setForeground(Color.BLACK); //Ship Button Turns black.
				shipButton.setFont(new Font("Courier", Font.PLAIN, 17));	//Font Size of Text set to 15
				//Point
				pointButton.setForeground(Color.BLACK); //Point Button Turns Black.
				pointButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15
				
				unstableOneButton.setForeground(Color.BLACK);
				unstableOneButton.setFont(new Font("Courier", Font.PLAIN, 17));
				
				//Booleans
				isGlider = true;		//isGlider Boolean set to true. 
				isSpaceship = false;	//isSpaceship Boolean set to false.
				isPoint = false;		//isPoint Boolean set to false.
				isUnstOne = false;
			}
		});
		
		pointButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Point Button
				pointButton.setForeground(Color.RED); //Point Button Text turns red. 
				pointButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text increases by 2 (from 15)
				//Gun Button
				gunButton.setForeground(Color.BLACK); //Gun Button turns black.
				gunButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15.
				//Ship Button
				shipButton.setForeground(Color.BLACK); //Ship Button turns black.
				shipButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15.
				
				unstableOneButton.setForeground(Color.BLACK);
				unstableOneButton.setFont(new Font("Courier", Font.PLAIN, 17));
				
				isPoint = true;			//isPoint Boolean set to true.
				isGlider = false; 		//isGlider Boolean set to false.
				isSpaceship = false;	//isSpaceship Boolean set to false.
				isUnstOne = false;
			}
		});		
		
		unstableOneButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				unstOne = new boolean[][]{
					/*one*/		{/*b1*/false,false,false,true,true,true,true, true, true, false, false, false,},
					/*Two*/		{/*b2*/false,false,false,true,true,true,true, true, true, false, false, false,},
					/*Three*/	{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Four*/	{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Five*/	{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Six*/		{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Seven*/	{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Eight*/	{/*b2*/true,true,false,false,false,false,false, false, false, false, true, true,},
					/*Nine*/	{/*b1*/false,false,false,true,true,true,true, true, true, false, false, false,},
					/*Ten*/		{/*b2*/false,false,false,true,true,true,true, true, true, false, false, false,}
				};
					
					//Point Button
					pointButton.setForeground(Color.BLACK); //Point Button Text turns red. 
					pointButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text increases by 2 (from 15)
					//Gun Button
					gunButton.setForeground(Color.BLACK); //Gun Button turns black.
					gunButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15.
					//Ship Button
					shipButton.setForeground(Color.BLACK); //Ship Button turns black.
					shipButton.setFont(new Font("Courier", Font.PLAIN, 17)); //Font Size of Text set to 15.
					
					unstableOneButton.setForeground(Color.RED);
					unstableOneButton.setFont(new Font("Courier", Font.PLAIN, 17));
					
					isPoint = false;			//isPoint Boolean set to true.
					isGlider = false; 		//isGlider Boolean set to false.
					isSpaceship = false;	//isSpaceship Boolean set to false.
					isUnstOne = true;
			}
		});

		//##################################################################################################### 
		//Button Separator.
		//This separator keeps all the buttons evenly spaced.
		Menu.add(Box.createVerticalStrut(20));
		//Ship Button
		aboutButton = new JButton("Game of Life");
		Menu.add(aboutButton); 
		aboutButton.addActionListener(this);
		//Gun Button Styling
		aboutButton.setPreferredSize(new Dimension(210, 30));
		aboutButton.setBackground(new Color(234,234,234));  
		aboutButton.setForeground(Color.BLACK);  
		aboutButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		aboutButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//#####################################################################################################
		//Exit Button
		exitButton = new JButton("Exit");
		Menu.add(exitButton); 
		exitButton.addActionListener(this);
		//Exit Button Styling
		exitButton.setPreferredSize(new Dimension(80, 50));
		exitButton.setBackground(new Color(234,234,234));
		exitButton.setForeground(Color.RED);  
		exitButton.setFont(new Font("Century Gothic", Font.BOLD, 24));  
		exitButton.setBorder(new LineBorder(new Color(234,234,234)));
		
		//##################################################################################################### 
		//speedLabel Counter
		//This is a placeholder value for when the application starts for the first time.
		speedLabel = new JLabel("");
		//speedLabel Counter Styling
		speedLabel.setBackground(new Color(234,234,234));  
		speedLabel.setForeground(Color.BLACK);  
		speedLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		speedLabel.setBorder(null);
		
		//#####################################################################################################
		//Generation Counter
		//This is a placeholder value for when the application starts for the first time.
		generationLabel = new JLabel("");
		//Generation Counter Styling
		generationLabel.setBackground(new Color(234,234,234));  
		generationLabel.setForeground(Color.BLACK);  
		generationLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		generationLabel.setBorder(null); 
		
		//#####################################################################################################    
		//Survivor Counter
		//This is a placeholder value for when the application starts for the first time.
		survivorLabel = new JLabel("");
		//Survivor Counter Styling
		survivorLabel.setBackground(new Color(234,234,234));  
		survivorLabel.setForeground(Color.BLACK);  
		survivorLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		survivorLabel.setBorder(null);
		
		//#####################################################################################################  
		//Survivor Counter
		//This is a placeholder value for when the application starts for the first time.        
		deadLabel = new JLabel("");
		//Survivor Counter Styling
		deadLabel.setBackground(new Color(234,234,234));  
		deadLabel.setForeground(Color.BLACK);  
		deadLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		deadLabel.setBorder(null);
		
		//#####################################################################################################  
		//Survivor Counter
		//This is a placeholder value for when the application starts for the first time.
		existLabel = new JLabel("| Cells Total: 000000 |");
		//Survivor Counter Styling
		existLabel.setBackground(new Color(234,234,234));  
		existLabel.setForeground(Color.BLACK);  
		existLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));  
		existLabel.setBorder(null);
		
		//#####################################################################################################  
		//Statistics Menu
		//These are the Statistics at the bottom of the GUI.
		StatMenu = new JToolBar("Label Toolbar");
		//The Vertical Struts keep the statistics in the centre of the tool bar.
		StatMenu.add(Box.createVerticalStrut(20));
		StatMenu.add(speedLabel);
		StatMenu.add(generationLabel);
		StatMenu.add(survivorLabel);
		StatMenu.add(deadLabel);
		StatMenu.add(existLabel);
		StatMenu.add(Box.createVerticalStrut(20));
		//Statistics Menu Styling
		StatMenu.setBorder(BorderFactory.createEmptyBorder());
		StatMenu.setBackground(new Color(234,234,234));  
		StatMenu.setFloatable(true); 		//As a JToolBar, the Statistics Menu can be made drag-able if you user desires.This can be fixed to other positions on the GUI.
		add(StatMenu, BorderLayout.SOUTH);	//Positions the JToolBar at the bottom of the GUI
		
		//#####################################################################################################    
		//Setup game board
		//This code generates the game of life board!
		platform = new MGoL_Backend();	
		add(platform);
		
		//Define Timer.
		timer = new Timer(1000/speed, this);
		//Identifies the Timer as an Action Command.
		timer.setActionCommand("Timer");
		//Starts Timer Immediately.
		timer.setInitialDelay(0);
		//Adds Action Listener Function to Timer.
		timer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Begins Game of Life
				if (e.getActionCommand().equals("Timer")) { 
					platform.run(); //Runs the Game of Life.
				} 
			}
		}
		);
	}//End of GameOfLife Class. DO NOT TOUCH
	
	public void ActiveGameOfLife(boolean CurrentlyActive) 
	{
		//If the play button is pressed, This method becomes active. 
		if (CurrentlyActive) 
		{
			playButton.setEnabled(false);
			stopButton.setEnabled(true);
			timer.start();
		}
		//If the Stop Button is pressed, the Boolean CurrentlyActive becomes false and the following code activates. This code is also active when the game starts to prevent the game starting instantly.
		else
		{
			playButton.setEnabled(true);
			stopButton.setEnabled(false);
			timer.stop();
		}
	}//End of ActiveGameOfLife. DO NOT TOUCH
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(configButton))
		{	
			
			
			//Create a JFrame for Speed Options
			JDialog configFrame = new JDialog(MGoL_Init.game, "Mann's Game of Life");
		
			configFrame.setSize(1000,800);
			configFrame.setLocationRelativeTo(null);
			configFrame.setResizable(true);
			
			JPanel configContainer = new JPanel();
			configContainer.setLayout(new BoxLayout(configContainer, BoxLayout.Y_AXIS));
			
			configFrame.add(configContainer);
			
			JPanel titlePanel = new JPanel();
			titlePanel.setOpaque(true);
			titlePanel.setBackground(new Color(234,234,234));
			
				JLabel titleLabel = new JLabel("Mann's Game of Life.");
				titleLabel.setFont(new Font("Century Gothic", Font.ITALIC, 50));
				titleLabel.setBackground(new Color(234,234,234));  
				titleLabel.setForeground(Color.BLACK); 
			
				titlePanel.add(titleLabel);
				
		    configContainer.add(titlePanel);
			
			//#####################################################################################################   
			JPanel cellPanel = new JPanel();
			//JPanel Styling
			cellPanel.setOpaque(true);
			cellPanel.setBackground(new Color(234,234,234));
			cellPanel.setBorder(BorderFactory.createTitledBorder("Toggle Cell Shape:"));
					
			squareCellCheck = new JRadioButton("Square Cell Array");
			
			squareCellCheck.setFont(new Font("Century Gothic", Font.PLAIN, 20));
			squareCellCheck.setBackground(new Color(234,234,234));  
			squareCellCheck.setForeground(Color.BLACK); 
			squareCellCheck.setBorder(new LineBorder(new Color(234,234,234)));
			// Changes start here
			squareCellCheck.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent ae)
				{
					if (circularCellCheck.isSelected())
					{
						circleCell = true;
						squareCell = false;
						platform.repaint();
					} else {
						circleCell = false;
						squareCell = true;
		                platform.repaint();
					}
				}
			}
			);
			// Changes end here
			
			circularCellCheck = new JRadioButton("Circular Cell Array");
			circularCellCheck.setFont(new Font("Century Gothic", Font.PLAIN, 20));
			circularCellCheck.setBackground(new Color(234,234,234));  
			circularCellCheck.setForeground(Color.BLACK); 
			circularCellCheck.setBorder(new LineBorder(new Color(234,234,234)));
			// Changes start here
			circularCellCheck.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent ae)
				{
					if (squareCellCheck.isSelected())
		            {
						circleCell = false;
						squareCell = true;
		                platform.repaint();
		            } else {
		            	circleCell = true;
						squareCell = false;
						platform.repaint();
		            }
				}
			}
			);
			
			// Changes end here

			ButtonGroup toggleGroup = new ButtonGroup();
			toggleGroup.add(squareCellCheck);
			toggleGroup.add(circularCellCheck);
			
			cellPanel.add(squareCellCheck);
			cellPanel.add(circularCellCheck);
			configContainer.add(cellPanel);
			
			//#####################################################################################################
			
			JPanel gridPanel = new JPanel();
			//JPanel Styling
			gridPanel.setOpaque(true);
			gridPanel.setBackground(new Color(234,234,234));
			gridPanel.setBorder(BorderFactory.createTitledBorder("Toggel Grid Display  - May affect performance and visibility."));
			
			JCheckBox gridCheck = new JCheckBox("Display Grid Array");
			
			gridCheck.setFont(new Font("Courier", Font.PLAIN, 17));
			gridCheck.setBackground(new Color(234,234,234));  
			gridCheck.setForeground(Color.BLACK); 
			gridCheck.setBorder(new LineBorder(new Color(234,234,234)));
			
			gridCheck.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {  
			        if(e.getStateChange() == ItemEvent.SELECTED) {
			        	gridVisible = true;
			        } else {
						gridVisible = false;
			        };
				}
			});
			
			gridPanel.add(gridCheck);
			configContainer.add(gridPanel);
			//#####################################################################################################    
			JPanel sizePanel = new JPanel();
			sizePanel.setOpaque(true);
			sizePanel.setBackground(new Color(234,234,234));
			sizePanel.setBorder(BorderFactory.createTitledBorder("Adjust Size - This will reset your board."));
			
			JSlider sizeSelect = new JSlider(JSlider.HORIZONTAL,3,100,10);
			sizeSelect.addChangeListener(new ChangeListener() {
	
			//YOOOOOO THIS IS THE RESIZE LISTENER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				public void stateChanged(ChangeEvent size) {
					MGoL_Backend.cellSize = ((JSlider)size.getSource()).getValue();
					platform.componentResized(null);
					platform.resetBoard();
					platform.randomFill(10);
				}
			});
			
			sizeSelect.setPreferredSize(new Dimension(800, 50));
			
			sizeSelect.setValue(MGoL_Backend.cellSize);
			
			Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
			sizeTable.put( new Integer( 20  ), new JLabel("20")   );
			sizeTable.put( new Integer( 40  ), new JLabel("40")   );
			sizeTable.put( new Integer( 60  ), new JLabel("60")   );
			sizeTable.put( new Integer( 80  ), new JLabel("80")   );
			sizeTable.put( new Integer( 100 ), new JLabel("100")  );
			sizeSelect.setLabelTable( sizeTable );

			sizeSelect.setPaintLabels(true);;
			
			sizeSelect.setFont(new Font("Courier", Font.PLAIN, 17));
			sizeSelect.setBackground(new Color(234,234,234));  
			sizeSelect.setForeground(Color.BLACK); 
			sizeSelect.setBorder(new LineBorder(new Color(234,234,234)));
			
			sizePanel.add(sizeSelect);
			configContainer.add(sizePanel);
			
			//#####################################################################################################    
			//Create a JPanel for the Speed Options:
			JPanel speedPanel = new JPanel();
			//JPanel Styling
			speedPanel.setOpaque(true);
			speedPanel.setBackground(new Color(234,234,234));
			speedPanel.setBorder(BorderFactory.createTitledBorder("Adjust Speed - This will affect the performance of the game."));

			
			JSlider secondsSelect = new JSlider(JSlider.HORIZONTAL,1,100,10);
			secondsSelect.addChangeListener(new ChangeListener() {
			   public void stateChanged(ChangeEvent spd) {
				   speed = ((JSlider)spd.getSource()).getValue();
				   timer.setDelay(1000/speed);
			   }
			});
			
			secondsSelect.setPreferredSize(new Dimension(800, 50));
			
			secondsSelect.setValue(speed);
			
			Hashtable<Integer, JLabel> speedTable = new Hashtable<Integer, JLabel>();
			speedTable.put( new Integer( 1 ), new JLabel("Slow") );
			speedTable.put( new Integer( 100 ), new JLabel("Fast") );
			secondsSelect.setLabelTable( speedTable );

			secondsSelect.setPaintLabels(true);;
			
			secondsSelect.setFont(new Font("Courier", Font.PLAIN, 17));
			secondsSelect.setBackground(new Color(234,234,234));  
			secondsSelect.setForeground(Color.BLACK); 
			secondsSelect.setBorder(new LineBorder(new Color(234,234,234)));
			
			speedPanel.add(secondsSelect);
		
			//Add JPanel to the JFrame
			configContainer.add(speedPanel);
			//#####################################################################################################    
			JPanel colorPanel = new JPanel(new BorderLayout());
			
			colorPanel.setOpaque(true);
			colorPanel.setBackground(new Color(234,234,234));
	 
	        //Set up color chooser for setting text color
	        tcc = new JColorChooser();
	        tcc.getSelectionModel().addChangeListener(new ChangeListener(){
	        	@Override
	            public void stateChanged(ChangeEvent color) {
	                Color cellColor = tcc.getColor();
	                MGoL_Backend.cellColor = cellColor;
	            }
	        });
	        
	        
	        tcc.setBorder(BorderFactory.createTitledBorder("Choose a Cell Color"));
	        tcc.setOpaque(true);
	        tcc.setBackground(new Color(234,234,234));
	         

	        colorPanel.add(tcc);
	        configContainer.add(colorPanel);
			//#####################################################################################################   
			//In case the user does not wish to change the speed, a close button has been added.
			JPanel closePanel = new JPanel();
			//JPanel Styling
			closePanel.setOpaque(true);
			closePanel.setBackground(new Color(234,234,234));

			//Add JPanel to the JFrame
			configContainer.add(closePanel);
			//#####################################################################################################    
			//Display the JFrame if Speed button is pressed.
			configFrame.setVisible(true);
		}
		//If Auto fill is pressed
		else if (ae.getSource().equals(autofillButton)) { platform.randomFill(10); } //Auto fill Refills Board Alive cells. 
		//End of Auto Fill Button Action Event. DO NOT TOUCH.
		else if (ae.getSource().equals(resetButton)) //If Rest Button is pressed.
		{
			//Statistics Values Reset to 0.
			
			generationCount = 0; //Statistics Values Reset to 0.
			survivorCount = 0;
			deadCount = 0;
			existenceCount = 0;
			//Board is reset thus turning all cells to false.
			platform.resetBoard();
			//Board is repainted to display the new board of dead cells.
			platform.repaint();
			//Game of Life is stopped.
			ActiveGameOfLife(false);
		}//End of Reset Button Action Event. DO NOT TOUCH.
		//If Play Button is pressed.
		else if (ae.getSource().equals(playButton)) { ActiveGameOfLife(true); } //Start Game of Life. 
		//End of Play Button Action Event. DO NOT TOUCH.
		//If Stop Button is pressed.
		else if (ae.getSource().equals(stopButton)) { ActiveGameOfLife(false); } //Stop Game of Life. 
		//End of Stop Button Action Event. DO NOT TOUCH.
		else if(ae.getSource().equals(pointButton)) //If Point is pressed.
		{
		} //End of Point Button Action Event. DO NOT TOUCH.
		else if(ae.getSource().equals(gunButton)) //If Gun Button is pressed.
		{
		} //End of GunButton Action Event. DO NOT TOUCH.
		else if(ae.getSource().equals(shipButton)) //If Ship Button is pressed.
		{
		}//End of ShipButton Action Event. DO NOT TOUCH.
		else if(ae.getSource().equals(aboutButton)) //If About Button is pressed.
		{
			//Create a JFrame for Speed Options
			final JDialog aboutFrame = new JDialog(MGoL_Init.game,"Game Of Life", Dialog.ModalityType.APPLICATION_MODAL);
			//JFrame Styling
			aboutFrame.setSize(1200,690);
			aboutFrame.setLocationRelativeTo(null);
			aboutFrame.setResizable(false);
			//Create a JPanel for the Speed Options:
			JPanel aboutPanel = new JPanel();
			//JPanel Styling
			aboutPanel.setOpaque(true);
			aboutPanel.setBackground(new Color(234,234,234));
			//Add JPanel to the JFrame
			aboutFrame.add(aboutPanel);
			//Make a JLabel in the JPanel
			JTextArea aboutInfo = new JTextArea();
			//Border-less Frame
			aboutInfo.setText(	"Game Of Life: \n"
								+"--------------------------------------------------------------------------------------------------------\n"
								+"The Game of Life, also known simply as Life,"
								+"is a cellular automaton devised by the British mathematician John Horton Conway in 1970.\n \n"
								+"The 'game' is a zero-player game, meaning that its evolution is determined by its initial state,"
								+"requiring no further input.\n \n"
								+"One interacts with the Game of Life by creating an initial configuration and observing how it evolves,"
								+"or, for advanced 'players', by creating patterns with particular properties.\n"
								+"--------------------------------------------------------------------------------------------------------\n"
								+"Rules of Game Of Life\n"
								+"The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. \n"
								+"     1).   Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.\n"
								+"     2).   Any live cell with two or three live neighbours lives on to the next generation.\n"
								+"     3).   Any live cell with more than three live neighbours dies, as if by overpopulation.\n"
								+"     4).   Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n"
								+"--------------------------------------------------------------------------------------------------------\n"
								+"Reference: \n"
								+"CONWAY'S GAME OF LIFE \n"
								+"In-text: (En.wikipedia.org, 2017)\n"
								+"En.wikipedia.org. (2017). Conway's Game of Life. [online] Available at: https://en.wikipedia.org/wiki/Conway's_Game_of_Life [Accessed 20 Mar. 2017]. \n"
								+"--------------------------------------------------------------------------------------------------------"
			);
			aboutInfo.setLineWrap(true);
			aboutInfo.setWrapStyleWord(true);
			aboutInfo.setEditable(false);
			aboutInfo.setHighlighter(null);
			aboutInfo.setSize(1200 - 100, 650 - 100);
			aboutInfo.setFont(new Font("Century Gothic", Font.PLAIN, 20));  
			aboutPanel.add(aboutInfo);
			//JTextArea Styling
			aboutInfo.setOpaque(true);
			aboutInfo.setBackground(new Color(234,234,234));
			
			//Display the JFrame if Speed button is pressed.
			aboutFrame.setVisible(true);
		}
		else if(ae.getSource().equals(exitButton))
		{
			MGoL_Init.game.dispose();
			System.exit(0);
		}
	}//End of actionPerformed. DO NOT TOUCH

	@Override
	public void stateChanged(ChangeEvent e) {}



}
