package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;


/**
 * MovieView class is the GUI page for the showing the theatres, movies, showtimes, seats
 * it has dropdown menus,for use to select the theatre, movie, ...
 */
public class MoviePage {

	/**
	 * the main frame
	 */
	private JFrame frame;
	
	
	/**
	 * drop down menu for theatres
	 */
	private JComboBox<String> theatreChoice;
	
	/**
	 * drop down menu for movies
	 */
	private JComboBox<String> movieChoice;
	
	/**
	 * drop down menu for showtimes
	 */
	private JComboBox<String> showtimeChoice;
	
	
	/**
	 * button for homepage
	 */
	private JButton homePageButton;
	
	/**
	 * button for checkout
	 */
	private JButton checkoutButton;
	
	/**
	 * toggle buttons for seats
	 */
	private JToggleButton[][] seats;
	
	/**
	 * array list that holds the seats row/column indexes
	 */
	private ArrayList<ArrayList<Integer>> selectedSeats;
	
	
	/**
	 * two helper vairbales that needed to be class level access
	 */
	int idx1;
	int idx2;
	
	
	/**
	 * movie page constructor
	 */
	public MoviePage() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setVisible(true);
		frame.setBounds(100, 100, 394, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Label label_1 = new Label("Select Theatre");
		label_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1.setBounds(42, 21, 86, 22);
		frame.getContentPane().add(label_1);

		Label label = new Label("Select Movie");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setBounds(42, 55, 86, 22);
		frame.getContentPane().add(label);


		Label label_1_1 = new Label("Select Showtime");
		label_1_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1_1.setBounds(42, 87, 99, 22);
		frame.getContentPane().add(label_1_1);

		Label label_1_1_1 = new Label("Select Seat");
		label_1_1_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1_1_1.setBounds(137, 142, 99, 22);
		frame.getContentPane().add(label_1_1_1);

		selectedSeats = new ArrayList<>();
		seats = new JToggleButton[3][3];

		seats[0][0] = new JToggleButton("row1, col1");
		seats[0][0].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[0][0].setBounds(42, 184, 86, 23);

		seats[0][1] = new JToggleButton("row1, col2");
		seats[0][1].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[0][1].setBounds(150, 184, 86, 23);

		seats[0][2] = new JToggleButton("row1, col3");
		seats[0][2].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[0][2].setBounds(259, 184, 86, 23);

		seats[1][0] = new JToggleButton("row2, col1");
		seats[1][0].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[1][0].setBounds(42, 232, 86, 23);

		seats[1][1] = new JToggleButton("row2, col2");
		seats[1][1].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[1][1].setBounds(150, 232, 86, 23);

		seats[1][2] = new JToggleButton("row2, col3");
		seats[1][2].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[1][2].setBounds(259, 232, 86, 23);

		seats[2][0] = new JToggleButton("row3, col1");
		seats[2][0].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[2][0].setBounds(42, 279, 86, 23);

		seats[2][1] = new JToggleButton("row3, col2");
		seats[2][1].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[2][1].setBounds(150, 279, 86, 23);

		seats[2][2] = new JToggleButton("row3, col3");
		seats[2][2].setFont(new Font("Tahoma", Font.PLAIN, 10));
		seats[2][2].setBounds(259, 279, 86, 23);

		for (JToggleButton[] row : seats) {
			for (JToggleButton col : row)
			frame.getContentPane().add(col);
		}

		homePageButton = new JButton("Back");
		homePageButton.setBounds(85, 333, 89, 23);
		frame.getContentPane().add(homePageButton);
	
		checkoutButton = new JButton("Checkout");
		checkoutButton.setBounds(217, 333, 89, 23);
		frame.getContentPane().add(checkoutButton);		

	}

	/**
	 * @param theatres
	 * @param movieTitles
	 * @param showtimes
	 * @param seatStatus
	 * sets the items in the drop down menu of theatres, movies, showtimes, seats
	 */
	public void setMoviePageComponents(String[] theatres, String[] movieTitles,
			String[][] showtimes, Boolean[][][][] seatStatus) {	
		
		ArrayList<String> temp = new ArrayList<>();
		temp.add("");
		for(int i=0; i<theatres.length; i++) {
			temp.add(theatres[i]);
		}
		
		String[] arr = new String[temp.size()];
        arr = temp.toArray(arr);
		
		theatreChoice = new JComboBox<>(arr);
		theatreChoice.setBounds(178, 21, 165, 22);
		frame.getContentPane().add(theatreChoice);
	
		movieChoice = new JComboBox<>();
		movieChoice.setBounds(178, 55, 165, 22);
		frame.getContentPane().add(movieChoice);
	
		showtimeChoice = new JComboBox<>();
		showtimeChoice.setBounds(178, 87, 165, 22);
		frame.getContentPane().add(showtimeChoice);
	
	
		theatreChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					
					movieChoice.removeAllItems();
					for (String s : movieTitles)
						movieChoice.addItem(s);
					
					System.out.println(movieTitles.toString());
				}
				
			}
		});
	
	
		movieChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					idx1 = movieChoice.getSelectedIndex();
					showtimeChoice.removeAllItems();
					for (String s : showtimes[idx1])
						showtimeChoice.addItem(s);
				}
			}
		});
	
	
		showtimeChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					idx2 = showtimeChoice.getSelectedIndex();
					Boolean[][] sea = seatStatus[idx1][idx2];
					for (int i=0; i<3; i++)
						for (int j=0; j<3; j++) {
							seats[i][j].setEnabled(!sea[i][j]);
							seats[i][j].setSelected(false);
							if( !sea[i][j]) {
								seats[i][j].setBackground(Color.green);
							}
							else {
								seats[i][j].setBackground(Color.red);
							}
						}
				}
			}
		});
	
		for (JToggleButton[] row : seats) {
			for (JToggleButton col : row)
			col.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						Scanner sc = new Scanner(col.getText());
						MatchResult temp = sc.findAll("row(\\d+), col(\\d+)").collect(Collectors.toList()).get(0);
						ArrayList<Integer> takeInSeat = new ArrayList<>();
						takeInSeat.add(Integer.valueOf(temp.group(1)));
						takeInSeat.add(Integer.valueOf(temp.group(2)));
						selectedSeats.add(takeInSeat);
					}
					if (e.getStateChange() == ItemEvent.DESELECTED) {
						Scanner sc = new Scanner(col.getText());
						MatchResult temp = sc.findAll("row(\\d+), col(\\d+)").collect(Collectors.toList()).get(0);
						ArrayList<Integer> takeOutSeat = new ArrayList<>();
						takeOutSeat.add(Integer.valueOf(temp.group(1)));
						takeOutSeat.add(Integer.valueOf(temp.group(2)));
						selectedSeats.remove(takeOutSeat);
					}
				}
			});
		}
	}
	
	
	/**
	 * @param actionListener
	 * sets the actions listener for homepage button
	 */
	public void setHomePageListener ( ActionListener a ) {
		homePageButton.addActionListener(a);
	}

	/**
	 * @param actionListener
	 * sets the action listener for checkout button
	 */
	public void setCheckoutListener( ActionListener a) {
		checkoutButton.addActionListener(a);
	}

	/**
	 * hides the page
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * @return selected seats
	 * row/column of selected seats by the user
	 */
	public ArrayList<ArrayList<Integer>> getSelectedSeats() {
		return selectedSeats;
	}

	/**
	 * sets the page visibility to true
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	// pop-up dialogues
	/**
	 * @param message
	 * shows the message as a pop up
	 */
	public void popUpDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	/**
	 * @return selected theatre as string
	 */
	public String getTheatre() {
		return (String) theatreChoice.getSelectedItem();	
	}
	
	/**
	 * @return selected movie as string
	 */
	public String getMovie() {
		return (String) movieChoice.getSelectedItem();	
	}
	
	/**
	 * @return selected showtime as string
	 */
	public String getShowtime() {
		return (String) showtimeChoice.getSelectedItem();	
	}
	
	/**
	 * @return returns the row, col of selected seat
	 */
	public int[] getSeat() {
		int row = selectedSeats.get(0).get(0);
		int col = selectedSeats.get(0).get(1);
		int[] seat = {row, col};
		
//		System.out.println("seat row, col: " + seat[0] + ", " + seat[1]);
		
		return seat;	
	}
	
}
