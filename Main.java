import java.awt.event.*;

public class Main
{
	public static void main ( String[] args )
	{
		ProgramWindow frame = new ProgramWindow ();

		// Allows the window to be closed
		frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
	}
}
