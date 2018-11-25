import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class YCbCR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedImage img = null; 
		File f = null; 

		//read image 
		try
		{ 
			f = new File("green.jpg"); 
			img = ImageIO.read(f); 
		} 
		catch(IOException e) 
		{
			System.out.println(e); 
		}

		// get width and height 
		int width = img.getWidth(); 
		int height = img.getHeight(); 

		BufferedImage ycb =  new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		// convert to blue image 
		for (int y = 0; y < height; y++) 
		{ 
			for (int x = 0; x < width; x++) 
			{ 
				int p = img.getRGB(x,y); 

				int b = p&0xff; 
				int g = (p>>8)&0xff; 
				int r = (p>>16)&0xff; 
				
				int Y = (int)(0.299*r+0.587*g+0.114*b);
	            int Cb=(int)(128-0.169*r-0.331*g+0.500*b);
	            int Cr =(int)(128+0.500*r-0.419*g-0.081*b);

				int val = (Y<<16) | (Cb<<8) | Cr;
				ycb.setRGB(x,y,val);
			} 
		} 

		try {
			ImageIO.write(ycb,"jpg", new File("greenYCBR.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
