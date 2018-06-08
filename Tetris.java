import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;

public class Tetris extends Panel implements KeyListener
{
	// for convenience
	public void print(String s){
		System.out.println(s);
	}
	public void print(int s){
		System.out.println(s);
	}

	Dimension dim = null;
	int[][] board;
	int rows = 24; // number of rows in board
	int cols = 12; // number of columns in board
	int s; // side length of square in board
	Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.GRAY}; // colors for the board
	Pieces activePiece; // the piece that the player controls
	BufferedImage osi; // off screen image
	Graphics osg; // off screen graphics
	Boolean died = false;
	Boolean paused = false;
	int score = 0;
	Timer timer;

	public Tetris()
	{
		addKeyListener(this);
		startGame();
	}

	public void startGame()
	{
		board = new int[rows][cols];
		for(int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				if (j == 0 || i == rows - 1 || j == cols - 1)
					board[i][j] = 8; // border (grey)
				else
					board[i][j] = 0; // empty (black)
			}
		}
		newPiece();

		scheduleTask();
		died = false;
		score = 0;
	}

	public void scheduleTask()
	{
		// calculates time per each move, based on current score
		// time decreases exponentially as score grows
		long time = (long)(800*Math.pow(0.97, score/100));
		print("New Task Scheduled with time "+time);
		TimerTask Fall = new TimerTask() {
			public void run() {
				if (died)
					return;
				if(!activePiece.shiftDown())
					newPiece();
				repaint();
				// cancel the current task, and schedule a new one with less time
				// this mathe game speed up as you play
				timer.cancel();
				timer = new Timer();
				scheduleTask();
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(Fall, time, 100000);
	}

	public void paint(Graphics g)
	{
		// uses buffered painting
		dim = getSize();
		osi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		osg = osi.getGraphics();
		update(g);
	}

	public void update(Graphics g)
	{
		// if there are any filled rows, delete them
		deleteRows();
		s = Math.min(dim.height/rows, dim.width/cols); // figure out the maximum side length of a square that will fit
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				osg.setColor(colors[board[i][j]]); // color is determined by board[][]
				if (i < 4)
					osg.setColor(Color.GRAY); // don't show the first 4 rowss
				osg.fillRect(j*s, i*s, s, s);
				osg.setColor(Color.GRAY);
				osg.drawRect(j*s, i*s, s, s);
			}
		}
		activePiece.display(osg, s); // display the active piece

		// write text
		osg.setColor(Color.WHITE);
		if (died)
		{
			osg.drawString("Game Over! Youre score is " + score, 50, 50);
			osg.drawString("Press 'r' to restart.", 50, 70);
		}
		else
			osg.drawString("Score: " + score, 50, 50);

		g.drawImage(osi, 0, 0, this);
	}

	// checks if the player has lost the game (if there's a piece in teh top four rows)
	public void checkDeath()
	{
		for (int i = 0; i<4; i++)
		{
			for (int j = 1; j < cols-1; j++)
			{
				if (board[i][j] != 0)
				{
					print("You Lost");
					died = true;
					timer.cancel();
					repaint();
					return;
				}
			}
		}
	}

	// creates a new piece and sets it to the acive piece
	// piece type is chosen randomly
	public void newPiece()
	{
		checkDeath();
		if (died)
			return; // if the game has ended, don't create new pieces
		int type = (int)(7*Math.random())+1; // random int between 1 and 7
		activePiece = new Pieces(type, colors[type], board);
	}

	// deletes all full rows on the board
	// calculates points based on number of rows cleared
	// 100 points for clearing 1 row, 200 for 2 rows, 400 for 3 rows, 800 for 4 rows
	public void deleteRows()
	{
		int count = 0; // number of lines deleted
		for (int i = 0; i < rows - 1; i++)
		{
			// attempts to delete row i as many times as possible
			// might be called multiple times for the same i, because rows shift down
			while (deleteRow(i))
				count++;
		}
		if (count > 0)
		{
			int increment = 100;
			for (int i = 1; i<count; i++)
				increment *= 2;
			score += increment;
		}
	}

	// if the row is full, delete the row
	// all rows above shift down one step
	// returns whether the row was full or not
	public Boolean deleteRow(int row)
	{
		Boolean full = true;
		for (int j = 1; j < cols-1; j++)
		{
			if (board[row][j] == 0)
				full = false;
		}
		if (full)
		{	// shift everything abodfve row down by one
			for (int i = row; i > 0; i--)
			{
				for (int j = 1; j < cols-1; j++)
					board[i][j] = board[i-1][j]; // move down one step
			}
			for (int j = 1; j < cols-1; j++)
				board[0][j] = 0; // top row becomes empty
		}
		return full;
	}

	// pauses or unpauses the game
	void pause()
	{
		// when the game is paused, it unpauses the game and continues the TimerTask
		if (paused)
		{
			paused = false;
			scheduleTask();
		}
		else // when the game is unpaused, it pauses the game and stops the timer
		{
			paused = true;
			timer.cancel();
		}
	}

	// KeyListener interface methods
	public void keyPressed(KeyEvent e)
	{
		if (died && e.getKeyCode() != KeyEvent.VK_R)
			return;
		if (paused && e.getKeyCode() != KeyEvent.VK_P)
			return;
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT: 	activePiece.move(0); // move left
									break;
			case KeyEvent.VK_RIGHT: activePiece.move(1); // move right
									break;
			case KeyEvent.VK_DOWN: 	if(!activePiece.shiftDown()) // move down
										newPiece(); // if the new piece reaches the bottom, creates a new piece
									break;
			case KeyEvent.VK_SPACE: activePiece.move(2); // move all the way down
									newPiece(); // creates a new piece because the current piece fell to the bottom
									break;
			case KeyEvent.VK_Z: 	activePiece.rotate(0); // rotate left
									break;
			case KeyEvent.VK_X: 	activePiece.rotate(1); // rotate right
									break;
			case KeyEvent.VK_R:		timer.cancel(); // restarts the game
									startGame();
									break;
			case KeyEvent.VK_P:		pause(); // pause or unpause the game
									break;
		}
		repaint(); // repaint to update any changes
	}

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
