import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;


public class Sizer extends JDialog implements ActionListener
{
	private static final long serialVersionUID = - 4823476409667554872L;
	public static int x0 = 0, y0 = 0, w = 0, h = 0;
	private JFrame prev;
	
	public Sizer(JFrame prev)
	{
		this.prev = prev;
		setTitle("Set Region");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		prev.setVisible(false);
		//setUndecorated(true);
		setResizable(false);
		setBounds(50, 100, 500, 500);
		JButton b1 = new JButton("OK");
		b1.addActionListener(this);
		b1.setVisible(true);
		add(b1);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Rectangle box = getBounds();
		x0 = box.x;
		y0 = box.y;
		w = (int)box.getWidth();
		h = (int)box.getHeight();
		setVisible(false);
		prev.setVisible(true);
	}
}
