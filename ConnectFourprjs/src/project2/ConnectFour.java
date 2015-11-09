/***********************************************************************
This implements a simplistic version of a connect four game through the
use of a JFrame and Panel. The key concept was to also eliminate the GUI
from doing any of the "work" and to remain isolated to just the display
while the game class actual does the computations

@author Evan Stedman
@version Winter 2015
***********************************************************************/
package project2;
import javax.swing.*;

public class ConnectFour {
	
	public static void main (String[] args){
		
		JFrame frame = new JFrame ("Connect Four Game!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		ConnectFourPanel panel = new ConnectFourPanel();
		frame.getContentPane().add(panel);
		frame.setSize(1050, 400);
		frame.setVisible(true);
	}
}
