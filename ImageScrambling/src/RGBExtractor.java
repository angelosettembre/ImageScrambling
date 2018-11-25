import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RGBExtractor {
	private static BufferedImage img = null;
	private static int width;
	private static int height;

	public static void main(String[] args) throws IOException {

		File f = null;
		File red = null;
		File blue = null;
		File green = null;
		
		//read image
		try{
			f = new File("cattura.jpg");
			img = ImageIO.read(f);

			//get width and height
			width = img.getWidth();
			height = img.getHeight();

		}catch(IOException e){
			System.out.println(e);
		}

		extractBlue(f);
		extractGreen(f);
		extractRed(f);
		
		red = new File("red.jpg");
		blue = new File("green.jpg");
		green = new File("blue.jpg");
		

	}

	public static void extractBlue(File f) throws IOException{
		img = ImageIO.read(f);
		
		//convert to blue image
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int p = img.getRGB(x,y);

				int a = (p>>24)&0xff;
				int b = p&0xff;

				//set new RGB
				p = (a<<24) | (0<<16) | (0<<8) | b;

				img.setRGB(x, y, p);
			}
		}

		//write image
		try{
			f = new File("blue.jpg");
			ImageIO.write(img, "jpg", f);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public static void extractGreen(File f) throws IOException{
		img = ImageIO.read(f);
		
		//convert to green image
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int p = img.getRGB(x,y);

				int a = (p>>24)&0xff;
				int g = (p>>8)&0xff;

				//set new RGB
				p = (a<<24) | (0<<16) | (g<<8) | 0;

				img.setRGB(x, y, p);
			}
		}

		//write image
		try{
			f = new File("green.jpg");
			ImageIO.write(img, "jpg", f);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public static void extractRed(File f) throws IOException{
		img = ImageIO.read(f);
		
		//convert to red image
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int p = img.getRGB(x,y);

				int a = (p>>24)&0xff;
				int r = (p>>16)&0xff;

				//set new RGB
				p = (a<<24) | (r<<16) | (0<<8) | 0;

				img.setRGB(x, y, p);
			}
		}

		//write image
		try{
			f = new File("red.jpg");
			ImageIO.write(img, "jpg", f);
		}catch(IOException e){
			System.out.println(e);
		}

	}
}