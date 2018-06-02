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
	Pieces activePiece;
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
		dim = getSize();
		osi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		osg = osi.getGraphics();
		update(g);
	}

	public void update(Graphics g)
	{
		deleteRows();
		int width = dim.width;
		int height = dim.height;
		s = Math.min(height/rows, width/cols);
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				osg.setColor(colors[board[i][j]]);
				if (i < 4)
					osg.setColor(Color.GRAY); // don't show the first 4 rowss
				osg.fillRect(j*s, i*s, s, s);
				osg.setColor(Color.GRAY);
				osg.drawRect(j*s, i*s, s, s);
			}
		}
		activePiece.display(osg, s);
		osg.setColor(Color.WHITE);
		// osg.setFont(new Font("Arial", Font.PLAIN, 8));
		if (died)
		{
			osg.drawString("Game Over! Youre score is " + score, 50, 50);
			osg.drawString("Press 'r' to restart.", 50, 70);
		}
		else
			osg.drawString("Score: " + score, 50, 50);
		g.drawImage(osi, 0, 0, this);
	}

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

	public void newPiece()
	{
		checkDeath();
		if (died)
			return;
		int type = (int)(7*Math.random())+1; // random int between 1 and 7
		activePiece = new Pieces(type, colors[type], board);
	}

	public void deleteRows()
	{
		int count = 0; // number of lines deleted
		for (int i = 0; i < rows - 1; i++)
		{
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
		// 100 points for clearing a row, 200 for 2 rows, 400 for 3 rows, 800 for 4 rows
	}

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
					board[i][j] = board[i-1][j];
			}
			for (int j = 1; j < cols-1; j++)
				board[0][j] = 0;
		}
		return full;
	}

	void pause()
	{
		if (paused)
		{
			paused = false;
			scheduleTask();
		}
		else
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
			case KeyEvent.VK_LEFT: 	activePiece.move(0);
									break;
			case KeyEvent.VK_RIGHT: activePiece.move(1);
									break;
			case KeyEvent.VK_DOWN: 	if(!activePiece.shiftDown())
										newPiece();
									break;
			case KeyEvent.VK_SPACE: activePiece.move(2);
									newPiece();
									break;
			case KeyEvent.VK_Z: 	activePiece.rotate(0); // rotate clockwise
									break;
			case KeyEvent.VK_X: 	activePiece.rotate(1);
									break;
			case KeyEvent.VK_R:		timer.cancel();
									startGame();
									break;
			case KeyEvent.VK_P:		pause();
									break;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
