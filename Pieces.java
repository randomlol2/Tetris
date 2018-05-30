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

	Color[] colors = new Color[]{Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA};
	public int[][] grid = new int[4][4];
	int row;
	int column;
	Color color;

	public Pieces(int type){
		if (type < 0 || type > 6)
			print("ERROR: Invalid piece type");
		row = 4;
		column = 0;
		color = colors[type];
		setGrid(type);
	}

	void setGrid(int type){
		switch (type) {
			case 0: grid = new int[][]{ {0, 1, 0, 0},
										{1, 1, 1, 0},
										{0, 0, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 1: grid = new int[][]{ {0, 0, 0, 0},
										{0, 1, 1, 0},
										{0, 1, 1, 0},
										{0, 0, 0, 0}};
										break;
			case 2: grid = new int[][]{ {0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0}};
										break;
			case 3: grid = new int[][]{ {0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 1, 1, 0},
										{0, 0, 0, 0}};
										break;
			case 4: grid = new int[][]{ {0, 1, 1, 0},
										{0, 1, 0, 0},
										{0, 1, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 5: grid = new int[][]{ {0, 0, 1, 0},
										{0, 1, 1, 0},
										{0, 1, 0, 0},
										{0, 0, 0, 0}};
										break;
			case 6: grid = new int[][]{ {0, 1, 0, 0},
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
					print("" + (row + i)*s + " " + (column + j)*s);
					g.fillRect((row + i)*s, (column + j)*s, s, s);
				}
			}
		}
	}

	public void move(){
		column--;
	}
}
