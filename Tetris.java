import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Tetris extends Panel
{

	private Dimension dim = null;
	Color[] colours = new Color[]{Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.GRAY, Color.MAGENTA};
	//int rows =
	Color[][] board;
	int rows = 24;
	int cols = 12;
	int square;

	int[][] tgrid = new int[][]{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
	int[][] ogrid = new int[][]{{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
	int[][] lgrid = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};
	int[][] Lgrid = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
	int[][] Jgrid = new int[][]{{0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
	int[][] Zgrid = new int[][]{{0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
	int[][] Sgrid = new int[][]{{0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}};

	Pieces T = new Pieces(square, tgrid, Color.WHITE);
	Pieces O = new Pieces(square, ogrid, Color.MAGENTA);
	Pieces l = new Pieces(square, lgrid, Color.MAGENTA);
	Pieces L = new Pieces(square, Lgrid, Color.MAGENTA);
	Pieces J = new Pieces(square, Jgrid, Color.MAGENTA);
	Pieces Z = new Pieces(square, Zgrid, Color.MAGENTA);
	Pieces S = new Pieces(square, Sgrid, Color.MAGENTA);

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
		paintBoard(g);
		paintPieces(g);
	}

	public void paintBoard(Graphics g)
	{
		System.out.println("Painting");
		int width = getSize().width;
		int height = getSize().height;
		square = height/rows;
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

	public void paintPieces(Graphics g)
	{
		T.display(g);
	}

}