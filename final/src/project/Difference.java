package project;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Difference extends Thread  {

	/**
	 * @param args
	 *
	 */
	BufferedImage img = ImageUtilities.ToBufferedImage("/Users/pradeepkumar/Documents/newgenerated1.jpg");
	double[][] image1 = new double[(img.getHeight())/2][(img.getWidth())/2];
	double[][] image2 = new double[(img.getHeight())/2][(img.getWidth())/2];
	double[][] image = new double[(img.getHeight())/2][(img.getWidth())/2];
	
	
	Difference() {
	}
	Difference(double[][] image1,double[][] image2) {
		this.image1= image1;
		this.image2=image2;
	}
	
	public double[][] getImage() {
		return image;
	}
	public void setImage(double[][] image) {
		this.image = image;
	}
	public synchronized  void  run() {

		
		double[][] image3 = new double[this.img.getHeight()][this.img.getWidth()];
		for(int g=0;g<image1.length;g++)
		{
			for(int h=0;h<image1[1].length;h++)
			{
				image3[h][g]=Math.abs(image2[h][g]-image1[h][g]);
			}
		}
		this.setImage(image3);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
}
}