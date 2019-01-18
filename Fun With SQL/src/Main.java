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
	
	private String textToDisplay, gender, age, hours, gender_t, hours_t, age_t;
	
	public Main() {
		textToDisplay = "Waiting for the command...";
		gender = "M";
		gender_t = "MALE";
		age = ">40";
		age_t = " > 40 years";
		hours = ">5";
		hours_t = " > 5 hours";
	}
	
	public JMenuBar createMenu() {
		var file = new JMenu("File");
		var option1 = new JMenuItem("Open");
		var option2 = new JMenuItem("Save");
		var option3 = new JMenuItem("Exit");
		//Query Menu
		var query = new JMenu("Query");
		var option4 = new JMenuItem("Count");
		var option_gen = new JMenu("Gender");
		var option_age = new JMenu("Age");
		var option_hrs = new JMenu("Hours");
		//Sub menu Items
		var option_male = new JMenuItem("Male");
		var option_fmle = new JMenuItem("Female");
		
		var option_l4 = new JMenuItem("<4");
		var option_b45 = new JMenuItem("4<<5");
		var option_g5 = new JMenuItem(">5");
		
		var option_25 = new JMenuItem("<25");
		var option_39 = new JMenuItem("25<<39");
		var option_40 = new JMenuItem(">40");
		
		//Setting the commands
		option3.setActionCommand("EXIT");
		option3.addActionListener(this);
		option4.setActionCommand("CNT");
		option4.addActionListener(this);
		
		option_male.setActionCommand("MALE");
		option_male.addActionListener(this);
		option_fmle.setActionCommand("FEMALE");
		option_fmle.addActionListener(this);
		
		option_l4.setActionCommand("LESS4");
		option_l4.addActionListener(this);
		option_b45.setActionCommand("BTW45");
		option_b45.addActionListener(this);
		option_g5.setActionCommand("GRT5");
		option_g5.addActionListener(this);
		
		option_25.setActionCommand("LESS25");
		option_25.addActionListener(this);
		option_39.setActionCommand("BTW2539");
		option_39.addActionListener(this);
		option_40.setActionCommand("GRT40");
		option_40.addActionListener(this);
		
		file.add(option1);
		file.add(option2);
		file.add(option3);
		
		option_gen.add(option_male);
		option_gen.add(option_fmle);
		
		option_age.add(option_25);
		option_age.add(option_39);
		option_age.add(option_40);
		
		option_hrs.add(option_l4);
		option_hrs.add(option_b45);
		option_hrs.add(option_g5);
		
		query.add(option4);
		query.add(option_gen);
		query.add(option_age);
		query.add(option_hrs);
		
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
		g.drawString(textToDisplay, 10, 30);
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
				ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM marathon WHERE age" + age + " AND gender='" + gender + "'AND hours" + hours + "");
				rs.next();
				int rows = rs.getInt(1);
				textToDisplay = rows + " Athletes have the attributes: GENDER: " + gender_t +", AGE: " + age_t +", TIME: " + hours_t + "";
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Oops. Something broke.");
				e1.printStackTrace();
			}
			repaint();
		} else if (cmd.equals("MALE")) {
			gender = "M";
			gender_t = "MALE";
		} else if (cmd.equals("FEMALE")) {
			gender = "F";
			gender_t = "FEMALE";
		} else if (cmd.equals("LESS4")) {
			hours = "<4";
			hours_t = "< 4 hours";
		} else if (cmd.equals("BTW45")) {
			hours = " BETWEEN 4 AND 5";
			hours_t = "between 4 and 5 hours";
		} else if (cmd.equals("GRT5")) {
			hours = ">5";
			hours_t = "> 5 hours";
		} else if (cmd.equals("LESS25")) {
			age = "<25";
			age_t = "< 25 years";
		} else if (cmd.equals("BTW2539")) {
			age = " BETWEEN 25 AND 39";
			age_t = "between 25 and 39 years";
		} else if (cmd.equals("GRT40")) {
			age = ">40";
			age_t = "> 40 years";
		}
		
	}

}