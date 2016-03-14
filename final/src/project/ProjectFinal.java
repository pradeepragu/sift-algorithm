package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

public class ProjectFinal 	 {
	/**
	 * i/p sig
	 */
	public void run() {
		//Display info about this particular thread
		//System.out.println(Thread.currentThread().getName());
	}
	public double[][] GaussianMsmask(int n )
	{

		//generating mask
		double sig = n;

		int size = (((int) sig) * 8 )+ 1;
		//size=5;
		double[][] mask = new double[size][size];
		double total = 0;

		for(int x = -((size-1)/2) ; x <= (size-1)/2 ; x++){

			for(int y = -((size-1)/2) ; y <= (size-1)/2 ; y++){

				/*double c = (x*x + y*y - 2*sig*sig)/(sig*sig*sig*sig);

				double e = -(x*x + y*y)/(2*sig*sig);

				double exp = Math.exp(e);

				mask[x+((size-1)/2)][y+((size-1)/2)] = c*exp ;*/
				double first=2*3.14*sig*sig;
				double e = -(x*x + y*y)/(2*sig*sig);

				double exp = Math.exp(e);

				double result= exp/first;
				mask[x+((size-1)/2)][y+((size-1)/2)] = result;

				total+=mask[x+((size-1)/2)][y+((size-1)/2)] ;
			}


		}
		//.out.println("total"+total);
		return mask;
	}
	public BufferedImage ToBufferedImage(String path) throws IOException
	{
		File imgFile = new File(path);
		BufferedImage img = ImageIO.read(imgFile);
		return img;
	}
	public double[][] fromImage(String path) throws IOException
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

	public BufferedImage toImage(double[][] imageArray) throws IOException
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
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		long time1= System.currentTimeMillis();

		ProjectFinal pf = new ProjectFinal();
		BufferedImage img=pf.ToBufferedImage("test1.jpg");
		double[][] image = new double[img.getHeight()][img.getWidth()];

		image=pf.fromImage("test1.jpg");

		for(int m=1;m<=5;m++)
		{
			double[][] gausssianFilter = new double[img.getHeight()][img.getWidth()];
			//System.out.println(pf.GaussianMsmask(m));
			double [][] mask=pf.GaussianMsmask(m);
			for(double[] arrayOne : mask){
				for(double array : arrayOne){
					//System.out.println(array);
				}
			}

			for(int i=0;i<img.getHeight()-mask.length;i++)
			{
				for(int j=0;j<img.getWidth()-mask.length;j++)
				{
					double total=0;
					for(int k=0;k<mask.length;k++)
					{
						for(int l=0;l<mask.length;l++)
						{
							total+=(image[j+l][i+k])*(mask[l][k]);

						}
					}

					gausssianFilter[i][j]=Math.abs(total);
					//if(total>25)
					//System.out.println(total);
				}	
			}
			BufferedImage image1=pf.toImage(gausssianFilter);
			File imgFile = new File("newgenerated"+m+".jpg");
			ImageIO.write(image1, "jpg", imgFile);
		}
		/*for(int n=1; n<=4 ;n++)
		{
			String path = "newgenerated"+n+".jpg";
			String path1 = "newgenerated"+(n+1)+".jpg";
			String path3 = "newgenerated"+(n+2)+".jpg";
			pf.findDifference(path,path1,n);

		} */
		
		Difference[][] threadArraytotal= new Difference[4][4];
		for(int n=1; n<=4 ;n++)
	{
		Difference[] threadArray= new Difference[4];

		for(int block=1;block<=4;block++)
	{
		String path = "newgenerated"+n+".jpg";
		String path1 = "newgenerated"+(n+1)+".jpg";
		Difference thread1= new Difference(ImageUtilities.subArray(ImageUtilities.fromImage(path),4,block),ImageUtilities.subArray(ImageUtilities.fromImage(path1),4,block));
		thread1.start();
		threadArray[block-1]=thread1;
		
	} 
		threadArraytotal[n-1]=	threadArray;
	}
	for(int n=1; n<=4 ;n++)
	{
		Difference[] threadlocal=threadArraytotal[n-1];
		double[][] image1=ImageUtilities.mergeArray(ImageUtilities.subArray(threadlocal[0].getImage(),4,1),ImageUtilities.subArray(threadlocal[1].getImage(),4,1),ImageUtilities.subArray(threadlocal[2].getImage(),4,1),ImageUtilities.subArray(threadlocal[3].getImage(),4,1));
		//double[][] image=ImageUtilities.mergeArray(threadlocal[0].getImage(),threadlocal[1].getImage(),threadlocal[2].getImage(),threadlocal[3].getImage());

		BufferedImage finalimage=ImageUtilities.toImage(image1);
		File imgFilex = new File("difference"+n+".jpg");
		ImageIO.write(finalimage, "jpg", imgFilex);
	}
		
		int n=1;
		String path1 = "difference1.jpg";
		String path2 = "difference2.jpg";
		String path3 = "difference3.jpg";
		String path4 = "difference4.jpg";
		
		double[][] finalimg1=pf.findMaxima_Minima(path1, path2, path3);
		double[][] finalimg2=pf.findMaxima_Minima(path2, path3, path4);
		//pf.findDifference("newgenerated"+2+".jpg","test1.jpg",2);
	//	System.out.println("image:"+finalimg1);
		BufferedImage image1=pf.toImage(finalimg1);
		File imgFile1 = new File("keypoints1.jpg");
		ImageIO.write(image1, "jpg", imgFile1);
		
		BufferedImage image2=pf.toImage(finalimg2);
		File imgFile2 = new File("keypoints2.jpg");
		ImageIO.write(image2, "jpg", imgFile2);
		pf.Magnitudeandorientation("keypoints1.jpg");
		long time2= System.currentTimeMillis();
		//System.out.println("time taken in ms:"+(time2-time1));
		System.out.println("Successfully Generated the keypoints using SIFT!!!!!! in :"+(time2-time1)+"ms");

	}

	public double[][] findMaxima_Minima(String path,String path1,String path2) throws IOException
	{
		
		
		double[][] image4=fromImage(path);
		double[][] image5=fromImage(path1);
		double[][] image6=fromImage(path2);
		BufferedImage img=ToBufferedImage(path);
		double[][] image7 = new double[img.getHeight()][img.getWidth()];
		double[][] finalimg1 = new double[img.getHeight()][img.getWidth()];
		double[][] finalimg2 = new double[img.getHeight()][img.getWidth()];
		for(int a=2; a<image4.length-5;a++)
		{
			for(int b=2; b<image4[1].length-5;b++)
			{
				List<Double> list = new ArrayList<Double>();
				//image4
			
				{	
				list.add(image4[a][b]);
				list.add(image4[a][b+1]);
				list.add(image4[a][b-1]);

				list.add(image4[a+1][b]);
				list.add(image4[a+1][b+1]);
				list.add(image4[a+1][b-1]);

				list.add(image4[a-1][b]);
				list.add(image4[a-1][b+1]);
				list.add(image4[a-1][b-1]);
				
				
				}
				// image5

				list.add(image5[a][b+1]);
				list.add(image5[a][b-1]);

				list.add(image5[a+1][b]);
				list.add(image5[a+1][b+1]);
				list.add(image5[a+1][b-1]);

				list.add(image5[a-1][b]);
				list.add(image5[a-1][b+1]);
				list.add(image5[a-1][b-1]);

				//image6
				list.add(image6[a][b]);
				list.add(image6[a][b+1]);
				list.add(image6[a][b-1]);

				list.add(image6[a+1][b]);
				list.add(image6[a+1][b+1]);
				list.add(image6[a+1][b-1]);

				list.add(image6[a-1][b]);
				list.add(image6[a-1][b+1]);
				list.add(image6[a-1][b-1]);

				
				
				double max = Collections.max(list);
				double min = Collections.min(list);
				if(image5[a][b]==max||image5[a][b]==min)
				{
					
					finalimg1[a][b] =255;
				}
				
				/*Collections.sort(list);
				double small=(Double) list.get(0);
				double big=(Double) list.get(25);
				double actual=image5[a][b];
				boolean	set;
				if(actual == small || actual == big)
				{
					//for contrast and edge	
					actual=255;
					image7[a][b]=actual;
				set = true;
				}*/
				else
				{
					finalimg1[a][b] =0;
				}
				
				
				/*if(set)
				{
				dxx =(image5[a-1][b]+image5[a+1][b]-2*image5[a][b]);
				dyy =(image5[a][b-1]+image5[a][b+1]-2*image5[a][b]);
				dxy =(image5[a-1][b-1]+image5[a+1][b-1]-image5[a+1][b-1]-image5[a-1][b+1])/4.0;

				trH = dxx + dyy;
				detH = dxx*dyy - dxy*dxy;

				curvatureRatio = trH*trH/detH;
				if(detH<0 || curvatureRatio>curvatureThreshold)
				{
					image7[a][b] = ;
					//cvSetReal2D(m_extrema[i][j-1], yi, xi, 0);
					num--;
					numRemoved++;

					set=false;
				}
				}*/
				
			}
			
		}
		
		return finalimg1;
		
		
	}
	
	public void Magnitudeandorientation(String path) throws IOException
	{
		//String path = "newgenerated1.jpg";
		
			double[][] edgeForImage=fromImage(path);
			double[][] magnitude = new double[edgeForImage.length][edgeForImage[1].length];
			double[][] direction = new double[edgeForImage.length][edgeForImage[1].length];
			double[][] mag = new double[edgeForImage.length][edgeForImage[1].length];
			double[] histogram = new double[36];
			for(int i=1;i<edgeForImage.length-3;i++)
			{
				for(int j=1;j<edgeForImage[1].length-3;j++)
				{
					double xval = edgeForImage[i+1][j-1]-edgeForImage[i-1][j-1]+edgeForImage[i+1][j]-edgeForImage[i-1][j]+edgeForImage[i+1][j+1]-edgeForImage[i-1][j+1];
					double yval = edgeForImage[i-1][j-1]-edgeForImage[i-1][j+1]+edgeForImage[i][j-1]-edgeForImage[i][j+1]+edgeForImage[i+1][j+1]-edgeForImage[i+1][j-1];
					//double xval = edgeForImage[i][j+1] - edgeForImage[i][j-1];
					//double yval = edgeForImage[i+1][j] - edgeForImage[i-1][j];
					double X = Math.sqrt(Math.pow(xval, 2) + Math.pow(yval, 2));
					magnitude[i][j] = X;
					//xval=Math.sqrt(Math.pow(xval, 2));
					//yval=Math.sqrt(Math.pow(yval, 2));
					
					double Theta = Math.atan(yval/xval);
					
					double theta = Theta/8;
			//		System.out.println(X);
					direction[i][j] = theta;
					if((int) theta>0)
					histogram[(int) theta]+=1;
					if(X>100)
					{
						if(edgeForImage[i][j] !=0)
							mag[i][j] =255;
						else
							mag[i][j] =0;
							
					}
				}
			}
			int max=(int)findMaximum(histogram);
			Set list=findList(histogram,max);
			//max = max/100;
			//System.out.println("max"+max);

			Line[][] line= new Line [edgeForImage.length][edgeForImage[1].length];
			Line l= new Line();
			Random r= new Random(100);
			for(int i=1;i<edgeForImage.length-3;i++)
			{
				for(int j=1;j<edgeForImage[1].length-3;j++)
				{
					//line[i][j]=l.findPoint(i, j, (int)direction[i][j], max/10000);
					line[i][j]=l.findPoint(i, j, (int)direction[i][j], 12);

				}
				}
			BufferedImage bi=toImage(mag);
			Graphics2D kpImage = bi.createGraphics();
		
			/*for(int i=1;i<30;i++)
			{
				for(int j=1;j<30;j++)
				{
					if(mag[i][j]==255 && direction[i][j]>((80*max)/100))
					{
						//System.out.println(line[i][j].getX2()+"y2:"+line[i][j].getY2());
					Line templine = line[i][j];
						kpImage.setColor(Color.blue);
					kpImage.drawLine(templine.getX1(), templine.getY1(), templine.getX2(), templine.getY2());

					}
				}
				} */
	//		System.out.println("list:"+list);

        for(int i=1;i<edgeForImage.length-35;i++)
			{
				for(int j=1;j<edgeForImage[1].length-10;j++)
				{
					//System.out.println("list:"+list);
					//if(mag[i][j]==255 &&  list.contains(direction[i][j]))
						if(mag[i][j]==255 )
					{
						//System.out.println(line[i][j].getX2()+"y2:"+line[i][j].getY2());
						//System.out.println(direction[i][j]);
						Line templine = line[i][j];
						kpImage.setColor(Color.blue);
						int n=r.nextInt(11);

						if(n==10)
					kpImage.drawLine(templine.getX1(), templine.getY1(), templine.getX2(), templine.getY2());

					}
				}
				}
			kpImage.dispose();
			ImageIO.write(bi, "jpeg", new File("finalimage.jpg"));
			BufferedImage finalkey=toImage(mag);
			File imgFilex = new File("magnitude.jpg");
			ImageIO.write(finalkey, "jpg", imgFilex);
	}
	private Set findList(double[] histogram, int max) {
		Set<Double> list= new HashSet<Double>();
		for(int i=0;i<histogram.length;i++)
		{
			if(histogram[i]>=((80*max)/100))
			{
		//		System.out.println("histogram[i]"+histogram[i]);
				list.add(histogram[i]);
			}
		}
		return list;
	}
	public double findMaximum(double[] histogram)
	{
		double max=0;
		for(int i=0;i<=35; i++)
		{
		if(histogram[i]>max)
			max = histogram[i];
		}
		
		return max;
		
	}
	
	

	
	

}
