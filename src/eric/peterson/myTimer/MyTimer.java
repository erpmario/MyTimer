package eric.peterson.myTimer;

import eric.peterson.myComponents.Colors;
import eric.peterson.myComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Eric Peterson
 * Date Created: 10/02/18
 * Date Updated: 10/09/18
 */
public class MyTimer extends JFrame
{
	/**
	 * Verification for send and receive.
	 */
	private static final long serialVersionUID = 1L;
	
	/* **************** *
	 * Member Variables *
	 * **************** */
	/**
	 *
	 */
	private Font m_font = new Font(Font.DIALOG, Font.BOLD, 36);
	/**
	 *
	 */
	private TimerPanel m_timerPanel;
	/**
	 *
	 */
	private JPanel m_buttonPanel;
	
	/**
	 * Default Constructor
	 * <p>
	 * Calls the <code>initGui</code> method and sets up basic parameters for the <code>JFrame</code>.
	 */
	public MyTimer()
	{
		initGUI();
		
		// JFrame Settings
		setTitle("My Timer");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the GUI.
	 * <p>
	 * Creates the <code>TitleLabel</code> and <code>JPanel</code> for the window. Creates a <code>JPanel</code> to hold buttons and populates it with the
	 * buttons.
	 */
	public void initGUI()
	{
		// Create TitleLabel.
		TitleLabel titleLabel = new TitleLabel("My Timer");
		add(titleLabel, BorderLayout.PAGE_START);
		
		// Create JPanel.
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Colors.STEELERS_GOLD);
		add(centerPanel, BorderLayout.CENTER);
		
		// Create button panel.
		m_buttonPanel = new JPanel();
		m_buttonPanel.setBackground(Color.BLACK);
		add(m_buttonPanel, BorderLayout.PAGE_END);
		
		m_timerPanel = new TimerPanel(0, m_font, m_buttonPanel);
		
		// Add TimerPanel.
		centerPanel.add(m_timerPanel);
		m_timerPanel.setBackground(Colors.STEELERS_GOLD);
		
		// Create start button.
		ActionListener startListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				start();
			}
		};
		JButton startButton = new JButton("Start");
		startButton.addActionListener(startListener);
		m_buttonPanel.add(startButton);
		
		// Create stop button.
		ActionListener stopListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				stop();
			}
		};
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(stopListener);
		stopButton.setVisible(false);
		m_buttonPanel.add(stopButton);
		
		// Create hour button.
		JButton hourButton = new JButton("Hours");
		ActionListener hourListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addHour();
			}
		};
		hourButton.addActionListener(hourListener);
		m_buttonPanel.add(hourButton);
		
		// Create minute button.
		JButton minuteButton = new JButton("Minutes");
		ActionListener minuteListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addMinute();
			}
		};
		minuteButton.addActionListener(minuteListener);
		m_buttonPanel.add(minuteButton);
		
		// Create clear button.
		JButton clearButton = new JButton("Clear");
		ActionListener clearListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clear();
			}
		};
		clearButton.addActionListener(clearListener);
		m_buttonPanel.add(clearButton);
	}
	
	/**
	 * Starts the timer. Calls <code>m_timerPanel</code>'s <code>start</code> method.
	 */
	public void start()
	{
		m_buttonPanel.getComponent(0).setVisible(false);
		m_buttonPanel.getComponent(1).setVisible(true);
		m_timerPanel.start();
	}
	
	/**
	 * Stops the timer. Calls <code>m_timerPanel</code>'s <code>stop</code> method.
	 */
	public void stop()
	{
		m_buttonPanel.getComponent(1).setVisible(false);
		m_buttonPanel.getComponent(0).setVisible(true);
		m_timerPanel.stop();
	}
	
	/**
	 * Adds 3600 seconds (or 1 hour) to the timer.
	 */
	private void addHour()
	{
		long time = m_timerPanel.getTime() + 3600; // Current time plus 3600 seconds (1 hour).
		m_timerPanel.setTimer(time);
	}
	
	/**
	 * Adds 60 seconds (or 1 minute) to the timer.
	 */
	private void addMinute()
	{
		long time = m_timerPanel.getTime() + 60; // Current time plus 60 seconds (1 minute).
		m_timerPanel.setTimer(time);
	}
	
	/**
	 * Sets the timer to 0 and stops it.
	 */
	private void clear()
	{
		m_buttonPanel.getComponent(1).setVisible(false);
		m_buttonPanel.getComponent(0).setVisible(true);
		m_timerPanel.stop();
		m_timerPanel.setTimer(0);
	}
	
	public static void main(String[] args)
	{
		// Set up cross-platform look and feel.
		try
		{
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Create new instance of myTimer.
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				new MyTimer();
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
