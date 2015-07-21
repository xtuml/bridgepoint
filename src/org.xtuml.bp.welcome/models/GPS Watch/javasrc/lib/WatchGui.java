package lib;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class WatchGui extends JFrame {

	public static final long serialVersionUID = 0;
	
	public static final int LARGE_Y = 355;
	public static final int SMALL_Y = 295;
	
	public static final String[] UNIT_LABELS = new String[] {
		  "km", 
		  "meters",
		  "min/km",
		  "km/h",
		  "miles",
		  "yds",
		  "ft",
		  "min/mile",
		  "mph",
		  "bpm",
		  "laps"
	};
	public static final int UNIT_KM           = 0;
	public static final int UNIT_METERS       = 1;
	public static final int UNIT_MIN_PER_KM   = 2;
	public static final int UNIT_KM_PER_HOUR  = 3;
	public static final int UNIT_MILES        = 4;
	public static final int UNIT_YDS          = 5;
	public static final int UNIT_FT           = 6;
	public static final int UNIT_MIN_PER_MILE = 7;
	public static final int UNIT_MPH          = 8;
	public static final int UNIT_BPM          = 9;
	public static final int UNIT_LAPS         = 10;

	private WatchGui.ApplicationConnection server;
	private JPanel holdAll = new JPanel();

	protected ImageIcon watch;
	protected ImageIcon watchIcon;
	protected ImageIcon lightHover;
	protected ImageIcon powerPressed;
	protected ImageIcon displayHover;
	protected ImageIcon displayPressed;
	protected ImageIcon modeHover;
	protected ImageIcon modePressed;
	protected ImageIcon lapResetHover;
	protected ImageIcon lapResetPressed;
	protected ImageIcon startStopHover;
	protected ImageIcon startStopPressed;
	protected ImageIcon smallSeparator;
	protected ImageIcon largeDots;
	protected ImageIcon smallDigit[] = new ImageIcon[10];
	protected ImageIcon largeDigit[] = new ImageIcon[10];
	
	private JLabel watchLabel     = new JLabel();
	private JLabel lightLabel     = new JLabel();
	private JLabel displayLabel   = new JLabel();
	private JLabel modeLabel      = new JLabel();
	private JLabel lapResetLabel  = new JLabel();
	private JLabel startStopLabel = new JLabel();
	private JLabel smallSeparatorLabel = new JLabel();
	private JLabel largeDotsLabel = new JLabel();
	private JLabel unitsLabel     = new JLabel();
	
	private JLabel[] smallDigitLabel = new JLabel[4];
	private JLabel[] largeDigitLabel = new JLabel[4];

	protected ImageIcon createStandaloneImageIcon(String path) {
		URL imgURL = ClassLoader.getSystemResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	protected void createImageIcons() {
		watch            = createStandaloneImageIcon("lib/img/watch.png");
		watchIcon        = createStandaloneImageIcon("lib/img/app_icon.gif");
		lightHover       = createStandaloneImageIcon("lib/img/light_hover.png");
		powerPressed     = createStandaloneImageIcon("lib/img/light_pressed.png");
		displayHover     = createStandaloneImageIcon("lib/img/display_hover.png");
		displayPressed   = createStandaloneImageIcon("lib/img/display_pressed.png");
		modeHover        = createStandaloneImageIcon("lib/img/mode_hover.png");
		modePressed      = createStandaloneImageIcon("lib/img/mode_pressed.png");
		lapResetHover    = createStandaloneImageIcon("lib/img/lap_reset_hover.png");
		lapResetPressed  = createStandaloneImageIcon("lib/img/lap_reset_pressed.png");
		startStopHover   = createStandaloneImageIcon("lib/img/start_stop_hover.png");
		startStopPressed = createStandaloneImageIcon("lib/img/start_stop_pressed.png");
		smallSeparator        = createStandaloneImageIcon("lib/img/dots_small.png");
		largeDots        = createStandaloneImageIcon("lib/img/dots_large.png");
		for (int i = 0; i < largeDigit.length; i++) {
			largeDigit[i] = createStandaloneImageIcon("lib/img/" + i + "_large.png");
			smallDigit[i] = createStandaloneImageIcon("lib/img/" + i + "_small.png");
		}
	}

	public WatchGui() {
		// the connection handle lives for the entire duration of this program
		new ConnectionHandler().start();
		
		// load images that make up the GUI
		createImageIcons();
		
		// container holding all button/display images
		JLayeredPane pane = new JLayeredPane();
		
		// background image
		watchLabel.setIcon(watch);
		watchLabel.setBounds(0, 0, 487, 756);
		
		lightLabel.setBounds(0, 190, 75, 122);
		lightLabel.addMouseListener(new ButtonListener(lightHover, powerPressed) {
			public void buttonPressed() {
				WatchGui.this.buttonPressed(new SignalData(SignalData.SIGNAL_NO_TARGET_PRESSED));
			}
		});

		startStopLabel.setBounds(165, 499, 164, 70);
		startStopLabel.addMouseListener(new ButtonListener(startStopHover, startStopPressed) {
			public void buttonPressed() {
				WatchGui.this.buttonPressed(new SignalData(SignalData.SIGNAL_NO_START_STOP_PRESSED));
			}
		});

		lapResetLabel.setBounds(420, 434, 83, 121);
		lapResetLabel.addMouseListener(new ButtonListener(lapResetHover, lapResetPressed) {
			public void buttonPressed() {
				WatchGui.this.buttonPressed(new SignalData(SignalData.SIGNAL_NO_LAP_RESET_PRESSED));
			}
		});

		displayLabel.setBounds(412, 190, 75, 122);
		displayLabel.addMouseListener(new ButtonListener(displayHover, displayPressed) {
			public void buttonPressed() {
				WatchGui.this.buttonPressed(new SignalData(SignalData.SIGNAL_NO_LIGHT_PRESSED));
			}
		});
		
		modeLabel.setBounds(0, 434, 81, 121);
		modeLabel.addMouseListener(new ButtonListener(modeHover, modePressed) {
			public void buttonPressed() {
				WatchGui.this.buttonPressed(new SignalData(SignalData.SIGNAL_NO_MODE_PRESSED));
			}
		});

		// configure and position display images
		smallSeparatorLabel.setBounds(210, SMALL_Y + 15, 8, 21);
		smallSeparatorLabel.setIcon(smallSeparator);
		
		largeDotsLabel.setBounds(242, LARGE_Y + 28, 13, 35);
		largeDotsLabel.setIcon(largeDots);
		
		unitsLabel.setText("");
		unitsLabel.setBounds(275, SMALL_Y + 28, 100, 25);
		unitsLabel.setForeground(Color.DARK_GRAY);
		unitsLabel.setFont(new Font("verdana", 0, 25));
		
		// add button/display images to a layer where they are visible
		pane.add(watchLabel,     JLayeredPane.PALETTE_LAYER);
		pane.add(lightLabel,     JLayeredPane.POPUP_LAYER);
		pane.add(displayLabel,   JLayeredPane.POPUP_LAYER);
		pane.add(modeLabel,      JLayeredPane.POPUP_LAYER);
		pane.add(lapResetLabel,  JLayeredPane.POPUP_LAYER);
		pane.add(startStopLabel, JLayeredPane.POPUP_LAYER);
		pane.add(smallSeparatorLabel, JLayeredPane.POPUP_LAYER);
		pane.add(largeDotsLabel, JLayeredPane.POPUP_LAYER);
		pane.add(unitsLabel,     JLayeredPane.POPUP_LAYER);
		
		for (int i = 0; i < largeDigitLabel.length; i++) {
			smallDigitLabel[i] = new JLabel();
			setSmallDigit(i, 0);
			smallDigitLabel[i].setBounds(160 + i * 26 + (i > 1 ? 5 : 0), SMALL_Y, 26, 51);
			pane.add(smallDigitLabel[i], JLayeredPane.POPUP_LAYER);
			largeDigitLabel[i] = new JLabel();
			setLargeDigit(i, 0);
			largeDigitLabel[i].setBounds(150 + i * 49 + (i > 1 ? 8 : 0), LARGE_Y, 43, 94);
			pane.add(largeDigitLabel[i], JLayeredPane.POPUP_LAYER);
		}

		holdAll.setLayout(new BorderLayout());
		holdAll.add(pane, BorderLayout.CENTER);

		getContentPane().add(pane, BorderLayout.CENTER);
		
		setLocation(10, 10);
		setSize(496, 790);
		setAlwaysOnTop(true);
		setIconImage(watchIcon.getImage());
		setTitle("Gps Watch");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void setSmallDigit(int index, int value) {
		smallDigitLabel[index].setIcon(smallDigit[value]);
	}
	private void setLargeDigit(int index, int value) {
		largeDigitLabel[index].setIcon(largeDigit[value]);
	}
	private void setUnit(String unit) {
		unitsLabel.setText(unit);
	}
	private void showSeparator(boolean show) {
		if (show) {
			smallSeparatorLabel.setIcon(smallSeparator);
		} else {
			smallSeparatorLabel.setIcon(null);
		}
	}
	private void buttonPressed(SignalData data) {
		if (server != null && !server.connection.isClosed()) {
			server.sendSignal(data);
		}
	}

	public void setTime(int time) {
		int min = (time % 3600) / 60;
		int sec = time % 60;
		setLargeDigit(0, min / 10);
		setLargeDigit(1, min % 10);
		setLargeDigit(2, sec / 10);
		setLargeDigit(3, sec % 10);
	}
	private void setFloatingPointValue(float value) {
		setSmallDigit(0, (int)(value / 10f) % 10);
		setSmallDigit(1, (int)(value) % 10);
		setSmallDigit(2, (int)(value * 10f) % 10);
		setSmallDigit(3, (int)(value * 100f) % 10);
	}
	private void setDiscreteValue(float value) {
		setSmallDigit(0, (int)(value / 1000f) % 10);
		setSmallDigit(1, (int)(value / 100f) % 10);
		setSmallDigit(2, (int)(value / 10f) % 10);
		setSmallDigit(3, (int)(value) % 10);
	}
	private void setTimex(float value) {
		int min = (int)value % 60; // this will truncate the hour value
		int sec = (int)(60 * value) % 60;
		setSmallDigit(0, min / 10);
		setSmallDigit(1, min % 10);
		setSmallDigit(2, sec / 10);
		setSmallDigit(3, sec % 10);
	}
	public void setData(float value, int unit) {
		switch (unit) {
		
		case UNIT_KM:
		case UNIT_MILES:		
		case UNIT_KM_PER_HOUR:
		case UNIT_MPH:
			setFloatingPointValue(value);
			showSeparator(true);
			break;
	
		case UNIT_METERS:
		case UNIT_YDS:
		case UNIT_FT:
		case UNIT_BPM:
		case UNIT_LAPS:
			setDiscreteValue(value);
			showSeparator(false);
			break;
	
		case UNIT_MIN_PER_KM:
		case UNIT_MIN_PER_MILE:
			setTimex(value);
			showSeparator(true);
			break;
		default:
			break;
		}
		setUnit(UNIT_LABELS[unit]);
	}
	
	/**
	 * A generic button listener that will switch images when
	 * buttons are hovered/pressed/released
	 */
	public abstract class ButtonListener implements MouseListener {
		private ImageIcon hover;
		private ImageIcon pressed;
		private ImageIcon cached;
		private boolean inside = false;
		public ButtonListener(ImageIcon hover, ImageIcon pressed) {
			this.hover = hover;
			this.pressed = pressed;
			cached = hover;
		}
		public void mouseExited(MouseEvent me) {
			((JLabel)me.getSource()).setIcon(null);
			inside = false;
		}
		public void mousePressed(MouseEvent me) {
			cached = pressed;
			((JLabel)me.getSource()).setIcon(pressed);
		}	
		public void mouseEntered(MouseEvent me) {
			((JLabel)me.getSource()).setIcon(cached);
			inside = true;
		}
		public void mouseReleased(MouseEvent me) {
			cached = hover;
			if (inside) {
				((JLabel)me.getSource()).setIcon(cached);
				buttonPressed();
			} else {
				((JLabel)me.getSource()).setIcon(null);
			}
		}
		public void mouseClicked(MouseEvent me) {}
		public abstract void buttonPressed();
	}
	
	/**
	 * The <code>ConnectionHandler</code> sits and waits for clients
	 * to connect. As soon as a new connection is detected, any existing
	 * connections are released, allowing the most recently connected client
	 * to control and be controlled by the GUI.
	 */
	public class ConnectionHandler extends Thread {

		ServerSocket providerSocket;
		@Override
		public void run() {
			try {
				providerSocket = new ServerSocket(2003);
				System.out.println("Waiting for connection");
				Socket connection = providerSocket.accept();
				while (true) {
					server = new ApplicationConnection(connection);
					server.start();
					Socket newConnection = providerSocket.accept();
					connection.close();
					connection = newConnection;
				}
			} catch (IOException ioException) {
				ioException.printStackTrace();
			} finally {
				try {
					providerSocket.close();
				} catch(IOException ioException){
					ioException.printStackTrace();
				}
				new ConnectionHandler().start();
			}
		}
	}
	
	/**
	 * The <code>ApplicationConnection</code> is the connection to the underlying 
	 * application that takes input from the GUI and displays data via the GUI.
	 * Messages are from the application are received on this thread. Message sending 
	 * occurs from the UI thread. 
	 */
	public class ApplicationConnection extends Thread {
		Socket connection = null;
		DataOutputStream out;
		BufferedReader in;
		String message = "";
		
	 	public ApplicationConnection(Socket connection) {
	 		this.connection = connection;
	 	}
		public void sendSignal(SignalData data) {
			try{
				data.serialize(out);
				out.write('\n');
				out.flush();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
		public void run() {
			try {
				System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				out = new DataOutputStream(connection.getOutputStream());
				out.flush();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				// main loop
				while (true) {
					try {
						String msg = in.readLine();
						
						// data arrives in a comma separated list
						StringTokenizer tokenizer = new StringTokenizer(msg, ",");
						SignalData data = null;
						
						// work out the data type of the incoming message
						// and set the action (run()) to be carried out
						switch (Integer.parseInt(tokenizer.nextToken())) {
						case SignalData.SIGNAL_NO_SET_DATA:
							data = new SetData() {
								public static final long serialVersionUID = 0;
								@Override
								public void run() {
									setData(value, unit);
								}
							};
							break;
						case SignalData.SIGNAL_NO_SET_TIME:
							data = new SetTime() {
								public static final long serialVersionUID = 0;
								@Override
								public void run() {
									setTime(time);
								}
							};
							break;
						default:
							break;
						}
						
						// initialize data from the incoming message
						data.unserialize(tokenizer);
						
						// execute the associated action
						data.run();
					} catch (IOException ioe) {
						System.out.println("Connection closed by client.");
						break;
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					connection.close();
					setTime(0);
					setData(0f, 0);
				} catch (IOException ioException){
					ioException.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {		
		WatchGui gui = new WatchGui();
		gui.setVisible(true);
	}	
}