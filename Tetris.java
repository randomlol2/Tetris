import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Tetris extends Panel
{

	private Dimension dim = null;
	Color[] colours = new Color[]{Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.GRAY};
	//int rows =
	Color[][] board;
	int rows = 24;
	int cols = 12;

	public Tetris()
	{
		initialize();

	}
	private void initialize()
	{
		System.out.println("Initializing");
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
		System.out.println("Painting");
		int width = getSize().width;
		int height = getSize().height;
		int square = height/rows;
		if (width/cols < square)
			square = width/cols;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				g.setColor(board[i][j]);
				g.fillRect(j*square, i*square, square, square);
				g.setColor(Color.GRAY);
				g.drawRect(j*square, i*square, square, square);
			}
		}
	}
}