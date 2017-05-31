import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Picture
{
	private BufferedImage image;
	private int width, height;

	public void scalePic(int w, int h)
	{
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics g = newImage.createGraphics();
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();

		width = w;
		height = h;
		image = newImage;
	}

	public Picture(int w, int h)
	{
		width = w;
		height = h;
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}

	public Picture(File file)
	{
		try
		{
			image = ImageIO.read(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Could not open file: " + file);
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
	}


	public int height()
	{
		return height;
	}

	public int width()
	{
		return width;
	}

	public Color get(int x, int y)
	{
		return new Color(image.getRGB(x, y));
	}

	public void set(int x, int y, Color color)
	{
		image.setRGB(x, y, color.getRGB());
	}


}