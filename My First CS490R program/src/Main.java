import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.Timer;


public class Main extends JFrame {
	
	private Vis mainPanel;
	private final int delay = 10;
	private Timer timer;
	private int x, y, velX, velY;

	public Main() {
		
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		
		mainPanel = new Vis();
		setContentPane(mainPanel);

		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MOGUMOGU");
		setVisible(true);
		
		velX = 2;
		velY = 2;

		timer = new Timer(delay, new timerAction());
	}
	
	private class timerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			x += velX;
			y += velY;
			if (x <= 0 || x >= 750)
				velX = velX * -1;
			if (y <= 0 || y >= 500)
				velY = velY * -1;
			Vis.points(x, y);
			repaint();
		}
	}
	
	private JMenuBar setupMenu() {
		//instantiate menubar, menus, and menu options
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem item1 = new JMenuItem("Throw");
		JMenuItem item_e = new JMenuItem("Exit");
		JMenu subMenu = new JMenu("Submenu");
		JMenuItem item2 = new JMenuItem("Catch");
		
		//setup action listeners
		//Used Lambda Syntax
		item1.addActionListener(throwing -> timer.start());		
		item2.addActionListener(catching -> timer.stop());
		item_e.addActionListener(exiting -> System.exit(0));
		
		//now hook them all together
		subMenu.add(item2);
		fileMenu.add(item1);
		fileMenu.add(subMenu);
		fileMenu.add(item_e);
		menuBar.add(fileMenu);
		
		return menuBar;
	}

	public static void main(String[] args) {

		//this makes the GUI adopt the look-n-feel of the windowing system (Windows/X11/Mac)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}