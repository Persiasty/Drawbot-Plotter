import java.awt.Color;
import java.io.File;

public class EdgeDetector
{
	public Picture pic;
	public boolean[][] pixelMap;
	public int[][][] filters = new int[][][]{
		{
			{-1, 0, 1},
			{-2, 0, 2},
			{-1, 0, 1}
		},
		{
			{1, 2, 1},
			{0, 0, 0},
			{-1, -2, -1}
		},
		{
			{0, 1, 2},
			{-1, 0, 1},
			{-2, -1, 0}
		},
		{
			{2, 1, 0},
			{1, 0, -1},
			{0, -1, -2}
		}
	};

	private int truncate(int a)
	{
		if(a < 0) return 0;
		else if(a > 255) return 255;
		else return a;
	}
	private double luminance(Color color)
	{
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		return .299 * r + .587 * g + .114 * b;
	}

	public EdgeDetector(File file, int w, int h)
	{
		Picture pic0 = new Picture(file);
		pic0.scalePic(w, h);

		int width = pic0.width();
		int height = pic0.height();

		Picture pic1 = new Picture(width, height);
		pixelMap = new boolean[width][height];

		for(int y = 1; y < height - 1; y++)
		{
			for(int x = 1; x < width - 1; x++)
			{

				int[][] gray = new int[3][3];
				for(int i = 0; i < 3; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						gray[i][j] = (int) luminance(pic0.get(x, y - 1 + j));
					}
				}

				int[] grayFilter = new int[filters.length];
				for(int i = 0; i < 3; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						for(int k = 0; k < grayFilter.length; k++)
						{
							grayFilter[k] += gray[i][j] * filters[k][i][j];
						}
					}
				}

				int trunc = 0;
				for(int k = 0; k < grayFilter.length; k++)
				{
					trunc += Math.pow(grayFilter[k], 2);
				}

				int magnitude = truncate((int) Math.sqrt(trunc));

				boolean pixel = magnitude > 50;

				pixelMap[x - 1][y - 1] = pixel;
				pic1.set(x, y, pixel ? Color.BLACK : Color.WHITE);
			}
		}
		pic = pic1;
	}
}