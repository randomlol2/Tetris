import java.awt.*;

public class ProgramWindow extends Frame
{
	Tetris panel = new Tetris() ; // create a panel

	public ProgramWindow()
	{
		setTitle("Tetris");
		setSize(800, 600); // set dimensions of the window
		setLocation (100, 100); //set the position of the top-left corner
		setResizable(true); // the user will be able to change the size
		add(panel); // attach the panel to the frame
		setVisible(true); // show the window on the monitor
	}
}
