import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Tetris extends Panel
{

	private Dimension dim = null;
	Color[] colours = new Color[]{Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.GRAY, Color.MAGENTA};
	//int rows =
	Color[][] board;
	int rows = 24; // number of rows in board
	int cols = 12; // number of columns in board
	int s; // side length of square in board


	public Tetris()
	{
		board = new Color[rows][cols];
		for(int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				if (j == 0 || i == rows - 1 || j == cols - 1)
					board[i][j] = Color.GRAY;
				else
					board[i][j] = Color.BLACK;
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

	Pieces testPiece = new Pieces(0);

	public void paintPieces(Graphics g)
	{
		testPiece.display(g, s);
	}

}
