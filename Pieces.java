import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Pieces
{
	// for convenience
	public void print(String s){
		System.out.println(s);
	}
	public void print(int s){
		System.out.println(s);
	}

	public int[][] grid = new int[4][4];
	int row;
	int col;
	Color color;

	public Pieces(int type)
	{
		if (type < 1 || type > 7)
			print("ERROR: Invalid piece type");
		row = 4;
		column = 0;
		color = colors[type - 1];
		setGrid(type);
	}

	void setGrid(int type)
	{
		switch (type)
		{
			case 1: grid = new int[][]{ {0, 1, 0, 0},
										{1, 1, 1, 0},
										{0, 0, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 2: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 1, 0},
										{0, 1, 1, 0},
										{0, 0, 0, 0}};
										break;
			case 3: grid = new int[][]{ {0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0}};
										break;
			case 4: grid = new int[][]{ {0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 1, 0},
										{0, 0, 0, 0}};
										break;
			case 5: grid = new int[][]{ {0, 1, 1, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 6: grid = new int[][]{ {0, 0, 1, 0},
										{0, 1, 1, 0},
										{0, 1, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 7: grid = new int[][]{ {0, 1, 0, 0},
										{0, 1, 1, 0},
										{0, 0, 1, 0},
										{0, 0, 0, 0}};
										break;
			default: print("ERROR: Invalid piece type");
		}
	}

	public void display(Graphics g, int s) // s = side length of square in grid
	{
		g.setColor(color);
		for (int i = 0; i<4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				if (grid[i][j] == 1)
				{
					g.fillRect((row + i)*s, (column + j)*s, s, s);
				}
			}
		}
	}

	// returns true if the piece moved, and false if it didn't move
	public Boolean move(int[][] board) {
		Boolean canMove = true;
		for (int i = 0; i < 4; i++)
		{
			// find the square in the i-th column that is directly under the piece
			int j = 3;
			for (; j >= 0; j--)
			{
				if (grid[j][i] == 1)
					break;
			}
			if (j == -1) // piece is not in the i-th column
				continue;
			else if (board[row+j+1][col+i] != 0) // the space below it is occupied, so it cannot move
				canMove = false;
		}
		if (canmove)
			column--;
		else
		{
			for (int i = 0 i<4; i++)
			{
				for (int j = 0; i<4; j++)
				{
					if (grid[i][j] == 1)
						board[row+i][col+j] = type;
				}
			}
		}
		return canMove;
	}
}
