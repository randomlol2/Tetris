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
	double moveTime = 0.8; // time between each block movement, in seconds

	TimerTask Fall = new TimerTask() {
		public void run() {
			if(!activePiece.shiftDown(board))
				newPiece();
			repaint();
		}
	};

	public Tetris()
	{
		addKeyListener(this);
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
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(Fall, 0, (long)(moveTime*1000));
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
		System.out.println("Painting");
		int width = dim.width;
		int height = dim.height;
		s = Math.min(height/rows, width/cols);
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				osg.setColor(colors[board[i][j]]);
				osg.fillRect(j*s, i*s, s, s);
				osg.setColor(Color.GRAY);
				osg.drawRect(j*s, i*s, s, s);
			}
		}
		activePiece.display(osg, s);
		g.drawImage(osi, 0, 0, this);
	}

	public void newPiece()
	{
		print("New Piece");
		int type = (int)(7*Math.random())+1; // random int between 1 and 7
		activePiece = new Pieces(type, colors[type]);
	}

	// KeyListener interface methods
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT: 	activePiece.move(0, board);
									break;
			case KeyEvent.VK_RIGHT: activePiece.move(1, board);
									break;
			case KeyEvent.VK_DOWN: 	activePiece.move(2, board);
									newPiece();
									break;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
