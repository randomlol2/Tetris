import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Pieces
{
	Color[] colours = new Color[]{Color.BLUE, Color.WHITE, Color.RED, Color.GREEN, Color.YELLOW, Color.GRAY};
	int[][] grid = new int[4][4];
	int row;
	int column;
	Color color;
	int square;

	public Pieces(int r, int c, int s){
		row = r;
		column = c;
		square = s;
	}
	public void display(Graphics g){
		g.setColor(color);
		for (int i = 0; i<4; i++){
			for(int j = 0; j<4; j++){
				if (grid[i][j] == 1){
					g.fillRect(row*square, column*square, square, square);
				}
			}
		}
	}

	public void move(){

		column--;
	}
}