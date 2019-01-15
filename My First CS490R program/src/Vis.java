import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;


public class Vis extends JPanel {
	//X, Y value for animation of Oval;
	private static int x = 0;
	private static int y = 0;
	
	
	public Vis() {
		super();
	}
	
	public static void points(int a, int b) {
		x = a;
		y = b;
	}
		
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		
		//draw blank background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//render visualization
		g.setColor(Color.BLACK);
		g.drawString("I Love you TA", 10, 20);
		
		g.setColor(Color.orange);
		//points(100, 150);//test only..working
		g.fillRect(x, y, 50, 50);
	}

}
