import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Main extends JPanel implements ActionListener {
	
	private String textToDisplay;
	
	public Main() {
		textToDisplay = "Waiting for the command...";
	}
	
	public JMenuBar createMenu() {
		var file = new JMenu("File");
		var option1 = new JMenuItem("Open");
		var option2 = new JMenuItem("Save");
		var option3 = new JMenuItem("Exit");
		
		var query = new JMenu("Query");
		var option4 = new JMenuItem("Count");
		//option3.addActionListener(e -> System.exit(0));
		/*option3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		
			}
			
		});*/
		option3.setActionCommand("EXIT");
		option3.addActionListener(this);
		option4.setActionCommand("CNT");
		option4.addActionListener(this);
		
		file.add(option1);
		file.add(option2);
		file.add(option3);
		
		query.add(option4);
		
		var menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(query);
		return menubar;
	}

	public static void main(String[] args) {
		var window = new JFrame("Fun with SQL");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(800,200);
		var contents = new Main();
		window.setContentPane(contents);
		window.setJMenuBar(contents.createMenu());
		window.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawString(textToDisplay, 10, 10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("EXIT")) {
			System.exit(0);
		} else if (cmd.equals("CNT")) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:derby:cs490R");
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM marathon WHERE age>40 AND gender='M' AND hours>5");
				rs.next();
				int rows = rs.getInt(1);
				textToDisplay = rows + " Athletes have the attributes: GENDER: MALE, AGE: > 40 years, TIME: > 5 hours";
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Oops. Something broke.");
				e1.printStackTrace();
			}
			repaint();
		}
		
	}

}