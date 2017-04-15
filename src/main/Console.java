package main;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import visuals.Tab;

public class Console extends WindowAdapter implements WindowListener, ActionListener, Runnable {
	private ConsolePanel frame;
	private JTextArea textArea;
	private Thread reader;
	private Thread reader2;
	private boolean quit;

	private final PipedInputStream pin = new PipedInputStream();
	private final PipedInputStream pin2 = new PipedInputStream();

	Thread errorThrower; // just for testing (Throws an Exception at this
							// Console

	public ConsolePanel getPanel()
	{
		return frame;
	}
	
	public Console() {
		// create all components and add them
		frame = new ConsolePanel();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension((int) (screenSize.width / 2), (int) (screenSize.height / 2));
		int x = (int) (frameSize.width / 2);
		int y = (int) (frameSize.height / 2);
		frame.setBounds(x, y, frameSize.width, frameSize.height);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JButton button = new JButton("clear");

		frame.setLayout(new BorderLayout());
		frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
		frame.add(button, BorderLayout.SOUTH);
		frame.setVisible(true);

		//frame.addWindowListener(this);
		button.addActionListener(this);

		try {
			PipedOutputStream pout = new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout, true));
		} catch (java.io.IOException io) {
			textArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
		} catch (SecurityException se) {
			textArea.append("Couldn't redirect STDOUT to this console\n" + se.getMessage());
		}

		try {
			PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2, true));
		} catch (java.io.IOException io) {
			textArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
		} catch (SecurityException se) {
			textArea.append("Couldn't redirect STDERR to this console\n" + se.getMessage());
		}

		quit = false; // signals the Threads that they should exit

		// Starting two seperate threads to read from the PipedInputStreams
		//
		reader = new Thread(this);
		reader.setDaemon(true);
		reader.start();
		//
		reader2 = new Thread(this);
		reader2.setDaemon(true);
		reader2.start();

	}

	public synchronized void windowClosed(WindowEvent evt) {
		quit = true;
		this.notifyAll(); // stop all threads
		try {
			reader.join(1000);
			pin.close();
		} catch (Exception e) {
		}
		try {
			reader2.join(1000);
			pin2.close();
		} catch (Exception e) {
		}
		System.exit(0);
	}

	public synchronized void windowClosing(WindowEvent evt) {
		frame.setVisible(false); // default behaviour of JFrame
		
	}

	public synchronized void actionPerformed(ActionEvent evt) {
		textArea.setText("");
	}

	public synchronized void run() {
		try {
			while (Thread.currentThread() == reader) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (pin.available() != 0) {
					String input = this.readLine(pin);
					textArea.append(input);
				}
				if (quit)
					return;
			}

			while (Thread.currentThread() == reader2) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (pin2.available() != 0) {
					String input = this.readLine(pin2);
					textArea.append(input);
				}
				if (quit)
					return;
			}
		} catch (Exception e) {
			textArea.append("\nConsole reports an Internal error.");
			textArea.append("The error is: " + e);
		}

	}

	public synchronized String readLine(PipedInputStream in) throws IOException {
		String input = "";
		do {
			int available = in.available();
			if (available == 0)
				break;
			byte b[] = new byte[available];
			in.read(b);
			input = input + new String(b, 0, b.length);
		} while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
		return input;
	}
}