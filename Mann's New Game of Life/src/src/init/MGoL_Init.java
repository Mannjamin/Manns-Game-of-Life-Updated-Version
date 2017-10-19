package src.init;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

//import java.awt.Dimension;
//import java.awt.Toolkit;
import javax.swing.JFrame;

import src.backend.MGoL_Backend;
import src.gui.MGoL_GUI;

public class MGoL_Init
{
	
	/* This is the Initialiser Class.
	 * Call this to run the program.
	 * It calls the Backend to Genenrate the Game of Life Board.
	 * and also calls the GUI Class to display the user interface.
	*/
	
	//Declare Variables:
	public static JFrame game;
	public static Color newColor = MGoL_GUI.newColor;
	public static MGoL_Backend platform;

	public static void main(String[] args) 
	{
		//Declare Frame.
		game = new MGoL_GUI();
		//Border-less Frame
		game.setUndecorated(true);
		//Stop Operation on Close.
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set Frame Title.
		game.setTitle("Mann's Game Of Life");
		//Set Frame Size.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		game.setSize(screenSize.width, screenSize.height);
		//Set Frame Position.
		game.setLocationRelativeTo(null);
		
		//Starting Variables.
		//Opens the Configuration panel on startup. 
		MGoL_GUI.configButton.doClick();
		//Autofils the Array to give the user a starting board of cells. 
		MGoL_GUI.autofillButton.doClick();
		//Specifies the Default Cell Color. Without this. The Default Color is a dark grey.
		MGoL_Backend.cellColor = new Color(204,204,255);
		
		//Set Frame Visibility.
		game.setVisible(true);
		

	}//End of main. DO NOT TOUCH

	protected void setVisible(boolean b) {}
}