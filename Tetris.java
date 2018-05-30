import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Tetris extends Panel
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

	TimerTask Move = new TimerTask() {
		public void run() {
			if(!activePiece.move(board))
				newPiece();
			repaint();
		}
	};

	public Tetris()
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
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(Move, 100, 1000);
	}

	public void paint(Graphics g)
	{
		System.out.println("Painting");
		int width = getSize().width;
		int height = getSize().height;
		s = Math.min(height/rows, width/cols);
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				g.setColor(colors[board[i][j]]);
				g.fillRect(j*s, i*s, s, s);
				g.setColor(Color.GRAY);
				g.drawRect(j*s, i*s, s, s);
			}
		}
		activePiece.display(g, s);
	}

	public void newPiece()
	{
		print("New Piece");
		int type = (int)(7*Math.random())+1; // random int between 1 and 7
		activePiece = new Pieces(type, colors[type]);
	}
}
