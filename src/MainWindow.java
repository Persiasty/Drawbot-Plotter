import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.net.ServerSocket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainWindow extends JFrame implements ActionListener, Runnable
{

	private static final long serialVersionUID = - 2940158651205432420L;
	private boolean[][] pixelMap;
	private JButton b1, b2, b3;
	
	public static void main(String[] args)
	{
		new MainWindow();
	}
	public MainWindow()
	{
		
		setTitle("Drawer Bot");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(800, 100, 200, 200);
		setResizable(false);
		
		
		JPanel cp = new JPanel();
		
		b1 = new JButton("Set Region");
		b1.setActionCommand("sr");
		b1.addActionListener(this);
		
		b2 = new JButton("Set Image");
		b2.setActionCommand("si");
		b2.addActionListener(this);
		b2.setEnabled(false);
		
		b3 = new JButton("Paint");
		b3.setActionCommand("paint");
		b3.addActionListener(this);
		b3.setEnabled(false);
		
		cp.add(b1);
		cp.add(b2);
		cp.add(b3);
		add(cp);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("sr"))
		{
			new Sizer(this);
			b1.setEnabled(false);
			b2.setEnabled(true);
		}
		else if(e.getActionCommand().equals("si"))
		{
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(fc);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				pixelMap = new EdgeDetector(fc.getSelectedFile(), Sizer.w, Sizer.h).pixelMap;
				b2.setEnabled(false);
				b3.setEnabled(true);
			}
		}
		else if(e.getActionCommand().equals("paint"))
		{
			new Thread(this).start();
			b3.setEnabled(false);
			b1.setEnabled(true);
		}
		
	}
	public void run()
	{
		long time = System.currentTimeMillis();
		Robot r = null;
		try
		{
			r = new Robot();
		}
		catch(AWTException e)
		{
			e.printStackTrace();
		}
		r.mouseMove(Sizer.x0, Sizer.y0);
		boolean isPressed = false;
		for(int y = 0; y < Sizer.h; y++)
			for(int x = 0; x < Sizer.w; x++)
			{
				if(pixelMap[x][y])
				{
					r.mouseMove(Sizer.x0 + x, Sizer.y0 + y);
					if(!isPressed)
					{
						r.mousePress(InputEvent.BUTTON1_MASK);
						isPressed = true;
					}
					
					if(x + 2 < Sizer.w)
					{	
						if(!pixelMap[x + 1][y] && !pixelMap[x + 2][y] && isPressed)
						{
							try
							{
								Thread.sleep(5);
							}
							catch(InterruptedException e)
							{
								e.printStackTrace();
							}
							r.mouseRelease(InputEvent.BUTTON1_MASK);
							isPressed = false;
						}
					}
					else if(x + 1 < Sizer.w)
					{	
						if(!pixelMap[x + 1][y] && isPressed)
						{
							try
							{
								Thread.sleep(5);
							}
							catch(InterruptedException e)
							{
								e.printStackTrace();
							}
							r.mouseRelease(InputEvent.BUTTON1_MASK);
							isPressed = false;
						}
					}
					else
						if(isPressed)
						{
							r.mouseRelease(InputEvent.BUTTON1_MASK);
							isPressed = false;
						}
				}
			}
			System.out.println("Current. " + (System.currentTimeMillis() - time) + "ms");
	}
	
}
