package src.backend;

import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Point;
//import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

//import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.gui.MGoL_GUI;

public class MGoL_Backend extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable, ChangeListener
{
	//This class contains most Game of Life Components including the main board, the human interaction features and more. 
	//Generic Serial Version ID.
	private static final long serialVersionUID = 882831296469793120L;
	
	//Declare Variables
	public static Color cellColor; //Cell Color.
	public static Graphics g; 
	public static int cellSize = 20; //Cell Size.
	public static Object outsideOfUniverse;
	public static Object alive;
	public Dimension GameOfLifeDimensions = new Dimension(getWidth()/cellSize-2, getHeight()/cellSize-2);
	public static ArrayList<Point> point = new ArrayList<Point>(0);
	
	public MGoL_Backend() 
	{
		//Game Of Life Constructor.
		//This Constructor inherits the Game Of Life Parameters. 
		//This is the first method which is initiated when the game of life is run.
		//Style Game of Life Board
		setBackground(Color.black);		//Make background of the Game Of Life black.
		addComponentListener(this);		//Calls the addComponentListenerMethod. Without this, the Game Of Life will not compile.
		addMouseListener(this);			//Calls the addMouseListener. Without this, the user cannot interact with the gameboard.
		addMouseMotionListener(this);	//Calls the addMouseMotionListener. Without this, the user cannot create a path with the mouse.
	}
	//End of GameOfLifeBoard. DO NOT TOUCH.
	
	//##############################################################################################################################################

	public void addPoint(int x, int y, MouseEvent me, boolean[][] glider, boolean[][] spaceship, boolean[][] unstOne) 
	{
		//This Method is responsible for defining the Boolean Value of a point within the Game of Life Board Array.
		
		SwingUtilities.isLeftMouseButton(me);
		SwingUtilities.isRightMouseButton(me);
		if(MGoL_GUI.isGlider)
		{	
			//if isGlider Boolean is true. The following array will generate at the Point of mouse click. 
			//This Array will print out the Boolean Gun based on the pre-written 9 x 36 array manually written within the Action Events method.
			for(int i=0; i < 9; ++i)     
			{  										
				for(int j=0; j < 36; ++j)
				{
					//Prints out the array at the mouse press point.
					if(glider[i][j]) { point.add(new Point(x+i, y+j)); }
				}		
			}
		}
		else if(MGoL_GUI.isSpaceship)
		{	
			//if isSpaceship Boolean is true. The following array will generate at the Point of mouse click. 
			//This Array will print out the Boolean ship based on the pre-written 5 x 4  array manually written within the Action Events method.
			for(int k=0; k < 4; ++k)
			{
				for(int l=0; l < 5; ++l)
				{
					//Prints out the array at the mouse press point.
					if(spaceship[k][l]) { point.add(new Point(x+k, y+l)); }
				}		
			}
		}
		else if(MGoL_GUI.isUnstOne)
		{
			//if inUnstOne Boolean is true. The following array will generate at the Point of mouse click. 
			//This Array will print out the Boolean Unstable Pattern based on the pre-written array manually written within the Action Events method.
			for(int m=0; m < 10; ++m)
			{
				for(int n=0; n < 12; ++n)
				{
					//Prints out the array at the mouse press point.
					if(unstOne[m][n]) { point.add(new Point(x+n, y+m)); }
				}		
			}
		}
		
		/*
		 * Allow me to explain what happens here, 
		 * the nested for loops perform a recursive action-
		 * to generate a pattern based on the Boolean Array-
		 * Defined for specific pattern selections within the-
		 * GUI Class.
		 */
		
		//If Left Mouse Button is pressed on the mouse.
		//This is the default Point selection.
		else if (SwingUtilities.isLeftMouseButton(me)) { point.add(new Point(x,y)); } //Add Point at Mouse Press 
		
		//This IF STATEMENT is responsible for removing points.
		else if (SwingUtilities.isRightMouseButton(me)) //If Right Mouse Button is pressed on the mouse.
		{
			point.remove(new Point(x,y));//Remove Point at Mouse Press
			++MGoL_GUI.deadCount; //Increase Death Count Statistics.
			--MGoL_GUI.survivorCount; //Increase Survivor Count Statistics
		}	  	
		repaint();
	} //End of AddPoint

	//##############################################################################################################################################
	
	public void addPoint(MouseEvent me, boolean[][] glider, boolean[][] spaceship, boolean[][] unstOne) 
	{
		
		/* This Method is Directly Connected to the Similarly Names addPoint method. 
		 * Strange as it seems. this code defines the the Coordinates of the mouse press and sends them to other addPoint method through-
		 * Inheritance. But only on a very very small scale. 
		 */
		
		int x = me.getPoint().x/cellSize-1; //Coordinate of X-axis on Mouse Press
		int y = me.getPoint().y/cellSize-1; //Coordinate of Y-axis on Mouse press
		if ((x >= 0) && (x < GameOfLifeDimensions.width) && (y >= 0) && (y < GameOfLifeDimensions.height))
		{
			addPoint(x,y,me,glider,spaceship, unstOne);
		}
	}//End of AddPoint
	
	//##############################################################################################################################################
	/* 
	 * This method is responsible for ensuring that cells can only be considered alive, within the dimensions of the board.
	 * Further Down the class, there is a Wrap-around Algorithm to ensure the Game of Life will be a finite but infinite board for cells.
	 * This Method helps with ensuring cells are remove if they're forcefully put outside the array.
	 * If a "Point" is considered alive outside the board. An "Out of Bounds Nullpointer Exception" will occur.
	 */
	public void updateUniverseSize()
	{
		ArrayList<Point> outsideOfUniverse = new ArrayList<Point>(0);
		for (Point alive : point)
		{
			if ( (alive.x > GameOfLifeDimensions.width - 1) || (alive.y > GameOfLifeDimensions.height - 1) )
			{
				outsideOfUniverse.add(alive);
			}
		}
		point.removeAll(outsideOfUniverse);
		repaint();
	}
	//##############################################################################################################################################
	/*
	 * The resetBoard Method has the very simple task of completely wiping the array board of points.
	 * This method essentially makes all points within the array grid false.
	 */
	public void resetBoard()
	{
		point.clear();			//Remove all points (Alive Cells) from the board.
		repaint();				//Refresh the board.
	}
	
	//##############################################################################################################################################
	/*
	 * The randomFill Method is responsible for the random generation of Cells.
	 * This method can be called by either pressing the "Autofill" button or through changing the cellsize.
	 */
	public void randomFill(int percent)  //If Method is called, randomly fill the board with points.
	{		
		for (int i=0; i<GameOfLifeDimensions.width; i++) 
		{
			for (int j=0; j<GameOfLifeDimensions.height; j++) 
			{
				if (Math.random()*100 < percent) { point.add(new Point(i,j)); }
			}
		}
	}

	//##############################################################################################################################################
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * This is an abstract Graphics Class. 
	 * It is responsible for the generation of cell graphic.
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		try 
		{	

			//Defined Cell Color.
			g.setColor(cellColor);		
			
			repaint();
			
			//This For Loop Helps Define whether the Cell graphic will be a circle or a Square.
			for (Point newPoint : point) 
			{
				if(MGoL_GUI.squareCell){
					MGoL_GUI.squareCellCheck.setSelected(true);
					MGoL_GUI.circularCellCheck.setSelected(false);
					
					g.fillRect(cellSize + (cellSize*newPoint.x), cellSize + (cellSize*newPoint.y), cellSize, cellSize);
				} else {
					MGoL_GUI.squareCellCheck.setSelected(false);
					MGoL_GUI.circularCellCheck.setSelected(true);
					
					g.fillOval(cellSize + (cellSize*newPoint.x), cellSize + (cellSize*newPoint.y), cellSize, cellSize);

				}
			}
		} 
		
		catch (ConcurrentModificationException cme) {} //
		
		// Setup grid
		if (MGoL_GUI.gridVisible) { g.setColor(Color.gray); repaint(); }
		else { g.setColor(Color.black); repaint();}
		repaint();
		
		//Rectangle Grid
		for (int i=0; i<=GameOfLifeDimensions.width; i++) 
		{
			g.drawLine(((i*cellSize)+cellSize), cellSize, (i*cellSize)+cellSize, cellSize + (cellSize*GameOfLifeDimensions.height) );
		}
		for (int i=0; i<=GameOfLifeDimensions.height; i++) 
		{
			g.drawLine(cellSize, ((i*cellSize)+cellSize), cellSize*(GameOfLifeDimensions.width+1), ((i*cellSize)+cellSize) );
		}
		
		updateStatisticsMenu();
	}
	//End of paintComponent
	
	//##############################################################################################################################################
	
	private void updateStatisticsMenu()
	{	
		/*
		 * This is the Update Statistics Menu.
		 * This method is responsible for resetting the Statistics Menu Component Values.
		 * This method is called when the game board is reset.
		 */
		//Asynchronously Update Statistics Menu
		MGoL_GUI.survivorCount = point.size();
		MGoL_GUI.existenceCount = point.size() + MGoL_GUI.deadCount;
		//Speed Value
		String speedOutput = String.format("%03d%n", MGoL_GUI.speed);
		MGoL_GUI.speedLabel.setText("| Speed: " + speedOutput + " |");
		//Generation Values
		String generationOutput = String.format("%08d%n", MGoL_GUI.generationCount);
		//Sets Starting Value of Cells Alive Count
		MGoL_GUI.generationLabel.setText("| Generation: " + generationOutput + " |");
		//Survivor Values
		String cellOutput = String.format("%08d%n", MGoL_GUI.survivorCount);
		//Sets Starting Value of Cells Alive Count
		MGoL_GUI.survivorLabel.setText("| Cells Alive: " + cellOutput + " |");
		//Dead Values
		String deadOutput = String.format("%08d%n", MGoL_GUI.deadCount);
		//Sets Starting Value of existence Count
		MGoL_GUI.deadLabel.setText("| Cells Dead: " + deadOutput + " |");
		//Existing Values
		String existOutput = String.format("%08d%n", MGoL_GUI.existenceCount);
		//Sets Starting Value of existence Count
		MGoL_GUI.existLabel.setText("| Cells Total: " + existOutput + " |");
	}

	//##############################################################################################################################################
	
	@Override
	public void componentResized(ComponentEvent e) 
	{
		/*
		 * This method is responsible for the Array Board always being the dimensions of the User Interface.
		 * This is necessary when the user changes the size of the Cells within the Array. 
		 * This inadvertently makes the array smaller and thus, the board ensure the array will stay the same size while adding more cells to accomodate.
		 * This calls the Update Universe Method.
		 */
		// Setup the game board size with proper boundaries
		GameOfLifeDimensions = new Dimension(getWidth()/cellSize-2, getHeight()/cellSize-2);
		updateUniverseSize();
	}
	
	//##############################################################################################################################################
	
	//DO NOT TOUCH
	@Override
	public void run() 
	{	
		/*
		 * This is the run method. This is responsible for the operation of the Game of life.
		 * This is executed when the Play Button is pressed.				
		 */
		
		//Defines the size of the game of life board.
		boolean[][] GameOfLifeBoard = new boolean[GameOfLifeDimensions.width][GameOfLifeDimensions.height];
		
		//Defines an "Alive Cell" at a particular point within the array.
		try
		{
			for (Point current : point) 
			{
				GameOfLifeBoard[current.x][current.y] = true;
			}
		} 
		catch(ArrayIndexOutOfBoundsException ae) {}
		
		//This is an array list which contains all alive cells.
		ArrayList<Point> survivingCells = new ArrayList<Point>(0);
		
		// Iterate through the array, follow game of life rules
		for (int i=0; i<GameOfLifeBoard.length; i++) 
		{
			for (int j=0; j<GameOfLifeBoard[0].length; j++) 
			{
				int n = wraparoundloop(GameOfLifeBoard, i, j);

				if(gameOfLifeRules(n, GameOfLifeBoard[i][j]))
				{
					survivingCells.add(new Point(i, j));
				}
			}
		}
		/*
		 * The call for the reset board is necessary after each generation to define the current state of the board.
		 * Do not delete it.
		 * Deleting this will cause the rules of the game of life to infinitely spread.
		 * It also dramatically affects performance.
		 */
		resetBoard();
		MGoL_GUI.generationCount++;
		MGoL_GUI.survivorCount = survivingCells.size();
		//The Reset Board call above empties the board, and then adds the next generation of Surviving Cells.
		point.addAll(survivingCells);
		//Repaints the board.
		repaint();          
	}
	//End of Run. DO NOT TOUCH.
	
	//##############################################################################################################################################
	
	public int wraparoundloop(boolean[][] GameOfLifeBoard, int X, int Y)
	{
		/*
		 * This is the wrap around algorithm needed to create a two dimensional "Infinite" board-
		 * as per the specifications of Conway's original Game of Life.
		 * 
		 * "value" = number of alive neighbours around the current cell
		 *
		 * "(rows + X + a) % rows" -> Gets the x-coordinate of the neighbours around the cell being checked using the GoL rules.
		 * 
		 * "(columns + Y + b) % columns" Is very similar but for the y-coordinate.
		 * 
		 *  Then if GameOfLifeBoard[x][y] is true, increment value by 1 else don't
		 */
		
		//ternary statement
		int value = GameOfLifeBoard[X][Y] ? -1 : 0;
		int rows = GameOfLifeBoard.length;
		int columns = GameOfLifeBoard[0].length;

		for (int a = -1; a <= 1; ++a)
		{
			for(int b = -1; b <= 1; ++b)
			{
				if( GameOfLifeBoard[(rows + X + a) % rows][(columns + Y + b) % columns]){value++;}
			}
		}
		return value;
	}
	//End of Wrap around Loop. DO NOT TOUCH.
	
	//##############################################################################################################################################
	/*
	 * The Game of Life Rules method defines the actual rules which the game of life will operate on.
	 * These rules have been minimised into Two If Statements.
	 * The Rules are:
	 * 
	 * Any live cell with fewer than two live neighbours dies, as if caused by under-population.
	 * Any live cell with two or three live neighbours lives on to the next generation.
	 * Any live cell with more than three live neighbours dies, as if by overpopulation.
	 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
	 */
	public boolean gameOfLifeRules(int n, boolean alive)
	{
		if(alive && (n == 2 || n == 3)) { return true; }
		else if(!alive && (n == 3)) { ++MGoL_GUI.deadCount; return true; }
		else return false;
	}
	
	//##############################################################################################################################################
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * 
	 * These are the main Getters and Setters associated with this Class File.
	 */
	// Mouse was released (user clicked)
	@Override
	public void mousePressed(MouseEvent e) { addPoint(e,MGoL_GUI.glider,MGoL_GUI.spaceship,MGoL_GUI.unstOne); }
	@Override
	public void mouseDragged(MouseEvent e)
	{
		try { addPoint(e,MGoL_GUI.glider,MGoL_GUI.spaceship, MGoL_GUI.unstOne); }
		catch(ArrayIndexOutOfBoundsException ae) { JOptionPane.showMessageDialog(null, "An array cannot be printed outside the frame."); }
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void stateChanged(ChangeEvent e) {}
	
	public void outsideOfUniverse() {}



}