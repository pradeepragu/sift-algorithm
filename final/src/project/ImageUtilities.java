package project;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageUtilities {

	public static BufferedImage ToBufferedImage(String path) 
	{
		File imgFile = new File(path);
		BufferedImage img = null;
		try
		{
			 img = ImageIO.read(imgFile);

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return img;
	}
	public static double[][] fromImage(String path) throws IOException
	{
		BufferedImage img=ToBufferedImage(path);
		double[][] image = new double[img.getHeight()][img.getWidth()];

		for(int j=1; j<img.getHeight(); j++)
		{
			for(int i=1; i<img.getWidth(); i++)
			{
				image[i][j]= new Color(img.getRGB(i, j)).getRed();
			}
		}
		return image;
	}

	public static BufferedImage toImage(double[][] imageArray) throws IOException
	{
		BufferedImage img=new BufferedImage(imageArray[0].length,imageArray.length,BufferedImage.TYPE_INT_RGB);

		for(int j=1; j<imageArray[0].length; j++)
		{
			for(int i=1; i<imageArray.length; i++)
			{

				int x= (int)imageArray[j][i];
				x=(int)((x << 8)+imageArray[j][i]);
				x=(int)((x << 8)+imageArray[j][i]);
				img.setRGB(i, j, x);

			}
		}

		return img;
	}
	public static double[][] subArray(double[][] source,int size,int number)
	{
		double[][] destination = new double[(source.length)/2][(source[1].length)/2];
		if(number == 1)
		{
			//.out.println("(source.length+1)/2:"+(source.length+1)/2);
			//System.out.println("source[1].length/2:"+source[1].length/2);
		for(int j=0;j<(source.length/2);j++)
		{
			for(int k=0;k<(source[1].length/2);k++)
			{
				destination[j][k]=source[j][k];
			}
		}
		}
		if(number == 2)
		{
			//System.out.println("(source.length+1)/2:"+(source.length+1)/2);
			//System.out.println("source[1].length/2:"+source[1].length/2);
		for(int j=0;j<(source.length/2);j++)
		{
			for(int k=0;k<(source[1].length/2);k++)
			{
				destination[j][k]=source[j+(source.length/2)][k];
			}
		}
		}
		if(number == 3)
		{
		for(int j=0;j<(source.length/2);j++)
		{
			for(int k=0;k<(source[1].length/2);k++)
			{
				destination[j][k]=source[j][k+(source[1].length/2)];
			}
		}
		}
		if(number == 4)
		{
		for(int j=0;j<(source.length/2);j++)
		{
			for(int k=0;k<(source[1].length/2);k++)
			{
				destination[j][k]=source[j+(source.length/2)][k+(source[1].length/2)];
			}
		}
		}
		
		
		
		return destination;
	}
	public static double[][] mergeArray(double[][] source,double[][] source1,double[][] source2,double[][] source3)
	{
		double[][] destination = new double[source.length*2][source[1].length*2];
		for(int j=0;j<(source.length);j++)
		{
			for(int k=0;k<(source[1].length);k++)
			{
				destination[j][k]=source[j][k];
			}
		}
		for(int j=0;j<(source.length);j++)
		{
			for(int k=0;k<(source[1].length);k++)
			{
				destination[j+source.length][k]=source1[j][k];
			}
		}
		for(int j=0;j<(source.length);j++)
		{
			for(int k=0;k<(source[1].length);k++)
			{
				destination[j][k+source[1].length]=source2[j][k];
			}
		}
		for(int j=0;j<(source.length);j++)
		{
			for(int k=0;k<(source[1].length);k++)
			{
				destination[j+source.length][k+source[1].length]=source3[j][k];
			}
		}
		return destination;

	}

}
