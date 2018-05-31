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

	int[][] grid = new int[4][4];
	int[][] board;
	int row;
	int col;
	int type;
	Color color;

	public Pieces(int _type, Color c, int[][] _board)
	{
		type = _type;
		if (type < 1 || type > 7)
		{
			print("ERROR: Invalid piece type");
			return;
		}
		row = 0;
		col = 4;
		color = c;
		board = _board;
		setGrid(type);
	}

	void setGrid(int type)
	{
		switch (type)
		{
			case 1: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 1, 0},
										{0, 1, 0, 0}};
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
			case 4: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 1, 0},
										{0, 0, 1, 0},
										{0, 0, 1, 0}};
										break;
			case 5: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 1, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0}};
										break;
			case 6: grid = new int[][]{ {0, 0, 0, 0},
										{0, 0, 1, 0},
										{0, 1, 1, 0},
										{0, 1, 0, 0}};
										break;
			case 7: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 1, 0},
										{0, 0, 1, 0}};
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
				if (grid[i][j] == 1 && row+i >= 4)
				{
					g.fillRect((col + j)*s, (row + i)*s, s, s);
				}
			}
		}
	}

	// returns true if the piece moved, and false if it didn't move
	public Boolean shiftDown() {
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
		if (canMove)
			row++;
		else
		{
			print("Couldn't move!");
			for (int i = 0; i<4; i++)
			{
				for (int j = 0; j<4; j++)
				{
					if (grid[i][j] == 1)
						board[row+i][col+j] = type;
				}
			}
		}
		return canMove;
	}

	public void shiftLeft()
	{
		Boolean canMove = true;
		for (int i = 0; i < 4; i++)
		{
			// find the square in the i-th row that is directly left of the piece
			int j = 0;
			for (; j < 4; j++)
			{
				if (grid[i][j] == 1)
					break;
			}
			if (j == 4) // piece is not in the i-th column
				continue;
			else if (board[row+i][col+j-1] != 0) // the space below it is occupied, so it cannot move
				canMove = false;
		}
		if (canMove)
			col--;
	}

	public void shiftRight()
	{
		Boolean canMove = true;
		for (int i = 0; i < 4; i++)
		{
			// find the square in the i-th row that is directly left of the piece
			int j = 3;
			for (; j >= 0; j--)
			{
				if (grid[i][j] == 1)
					break;
			}
			if (j == -1) // piece is not in the i-th column
				continue;
			else if (board[row+i][col+j+1] != 0) // the space below it is occupied, so it cannot move
				canMove = false;
		}
		if (canMove)
			col++;
	}

	public void fall()
	{
		while(shiftDown()); // fall down as much as possible
	}

	public void move(int dir) // Left: dir = 0, Right: dir = 1, Down: dir = 2
	{
		switch (dir)
		{
			case 0:	shiftLeft();
					break;
			case 1:	shiftRight();
					break;
			case 2: fall();
					break;
			default: print("ERROR: Invalid direction to move in");
		}
	}

	public void rotate(int dir)
	{
		int[][] newGrid = new int[4][4];
		if (dir == 0)
		{
			for (int i = 0; i<4; i++)
			{
				for (int j = 0; j<4; j++)
				{
					if (grid[i][j] == 1)
						newGrid[3-j][i] = 1;
				}
			}
		}
		else
		{
			for (int i = 0; i<4; i++)
			{
				for (int j = 0; j<4; j++)
				{
					if (grid[i][j] == 1)
						newGrid[j][3-i] = 1;
				}
			}
		}
		for (int i = 0; i<4; i++)
		{
			for (int j = 0; j<4; j++)
			{
				if (newGrid[i][j]*board[row+i][col+j] > 0)
					return; // cannot rotate, because the spot is occupied

			}
		}
		for (int i = 0; i<4; i++)
		{
			for (int j = 0; j<4; j++)
			{
				grid[i][j] = newGrid[i][j];
			}
		}
	}
}
