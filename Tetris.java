import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Tetris extends Panel
{
	Dimension dim = null;
	int[][] board;
	int rows = 24; // number of rows in board
	int cols = 12; // number of columns in board
	int s; // side length of square in board
	Color[] colors = new Color[]{Color.GRAY, Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.BLACK}; // colors for the board

	public Tetris()
	{
		board = new Color[rows][cols];
		for(int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				if (j == 0 || i == rows - 1 || j == cols - 1)
					board[i][j] = 0; // empty (grey)
				else
					board[i][j] = 8; // border (black)
			}
		}
	}

	public void paint(Graphics g)
	{
		paintBoard(g);
		paintPieces(g);
	}

	public void paintBoard(Graphics g)
	{
		System.out.println("Painting");
		int width = getSize().width;
		int height = getSize().height;
		s = Math.min(height/rows, width/cols);
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				g.setColor(board[i][j]);
				g.fillRect(j*s, i*s, s, s);
				g.setColor(Color.GRAY);
				g.drawRect(j*s, i*s, s, s);
			}
		}
	}

	public void paintPieces(Graphics g)
	{
		testPiece.display(g, s);
	}

}
