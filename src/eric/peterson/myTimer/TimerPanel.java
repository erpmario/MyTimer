package eric.peterson.myTimer;

import eric.peterson.myCommonMethods.FileIO;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

/**
 * @author Eric Peterson
 * Date Created: 10/02/18
 * Date Updated: 10/09/18
 */
public class TimerPanel extends JPanel implements Runnable
{
	/**
	 * Verification for send and receive.
	 */
	private static final long serialVersionUID = 1L;
	
	/* ********* *
	 * Constants *
	 * ********* */
	/**
	 * The file containing the alarm sound.
	 */
	protected static final String ALARM_FILE = "alarm.wav";
	
	/* **************** *
	 * Member Variables *
	 * **************** */
	/**
	 * The width of the panel in pixels.
	 */
	private int m_panelWidth = 150;
	/**
	 * The height of the panel in pixels.
	 */
	private int m_panelHeight = 24;
	/**
	 * The <code>String</code> representing the current value of the timer in "hh:mm:ss" format.
	 */
	private String m_timeString = "00:00:00";
	/**
	 * Variable to keep track of the current time. It is set to 10 by default if the <code>setTimer</code> method is never called.
	 */
	private long m_time = 10L;
	/**
	 * The <code>Thread</code> this <code>Runnable</code> will run on.
	 */
	private Thread m_timerThread;
	/**
	 * Reference to <code>MyTimer</code>'s button panel so this class can manipulate the buttons.
	 */
	private JPanel m_buttonPanel;
	
	/**
	 * Constructor
	 * <p>
	 * Sets the timer to <code>time</code> and the font to <code>font</code>. Sets <code>m_panelHeight</code> to the size of the font. Sets
	 * <code>m_panelWidth</code> to the width of <code>m_timeString</code>.
	 *
	 * @param time
	 * 		The initial time to which to set the timer.
	 * @param font
	 * 		The font the timer should use when displaying.
	 */
	public TimerPanel(long time, Font font, JPanel buttonPanel)
	{
		m_buttonPanel = buttonPanel;
		setTimer(time);
		setFont(font);
		m_panelHeight = font.getSize();
		FontMetrics fontMetrics = getFontMetrics(font);
		m_panelWidth = fontMetrics.stringWidth(m_timeString);
	}
	
	/**
	 * Sets the timer value to the passed <code>long</code>.
	 *
	 * @param time
	 * 		The initial time to which to set the timer.
	 */
	public void setTimer(long time)
	{
		m_time = time;
		long h = time / 3600;
		long m = (time / 60) % 60;
		long s = time % 60;
		m_timeString = String.format("%02d:%02d:%02d", h, m, s);
		repaint();
	}
	
	/**
	 * Called when the start button is pressed. Creates a new <code>Thread</code> with this class as the <code>Runnable</code>, stores it in
	 * <code>m_timerThread</code>, and starts it.
	 */
	public void start()
	{
		stop();
		m_timerThread = new Thread(this);
		m_timerThread.start(); // Calls the run method in this class.
	}
	
	/**
	 * Called when the timer reaches 0. Displays a message indicating the time is up and plays the sound specified by <code>ALARM_FILE</code>.
	 */
	protected void timesUp()
	{
		Clip clip = FileIO.playClip(this, ALARM_FILE);
		clip.start();
		//String msg = "Times up!";
		//JOptionPane.showMessageDialog(this, msg);
		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException e)
		{
			return;
		}
		clip.stop();
		
		//m_buttonPanel.getComponent(1).setVisible(false); // Set "Stop" button invisible.
		//m_buttonPanel.getComponent(0).setVisible(true); // Set "Start" button visible.
	}
	
	/**
	 *
	 */
	public void run()
	{
		while(m_time > 0)
		{
			m_time -= 1;
			setTimer(m_time);
			//System.out.println(m_time);
			
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				return;
			}
		}
		timesUp();
	}
	
	/**
	 * Called when the stop button is pressed. Interrupts the timer thread and sets <code>m_timerThread</code> to <code>null</code>.
	 */
	public void stop()
	{
		if(m_timerThread != null)
		{
			m_timerThread.interrupt();
			m_timerThread = null;
		}
	}
	
	/**
	 * @return <code>m_time</code>, the current value of the timer as a <code>long</code>.
	 */
	public long getTime()
	{
		return m_time;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString(m_timeString, 0, m_panelHeight);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(m_panelWidth, m_panelHeight);
	}
}
