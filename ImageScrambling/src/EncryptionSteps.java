import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class EncryptionSteps {
	private static BufferedImage img = null;
	private static int width;
	private static int height;


	public static void initialize(File input) throws IOException{
		img = ImageIO.read(input);

		//get width and height
		width = img.getWidth();
		height = img.getHeight();
	}

	public static File extractBlue(File f) throws IOException{
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
		return f;
	}

	public static File extractGreen(File f) throws IOException{
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
		return f;
	}

	public static File extractRed(File f) throws IOException{
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
		return f;

	}

	public static File rgb2ybcr(File input) throws IOException{
		BufferedImage img = null; 
		img = ImageIO.read(input);
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
				int Cb=(int)(-0.169*r - 0.331*g + 0.500*b)+128;
				int Cr =(int)(0.500*r-0.419*g-0.081*b)+128;

				int val = (Y<<16) | (Cb<<8) | Cr;
				ycb.setRGB(x,y,val);
			} 
		} 

		try {
			String color = input.getName();
			if(color.contains("blue")){
				input = new File("blueYCBR.jpg");
				ImageIO.write(ycb,"jpg", input);
			}else if(color.contains("red")){
				input = new File("redYCBR.jpg");
				ImageIO.write(ycb,"jpg", input);
			}else if(color.contains("green")){
				input = new File("greenYCBR.jpg");
				ImageIO.write(ycb,"jpg", input);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return input; 

	}

	public static File concatenateImage(File file1, File file2,File file3) throws IOException{
		BufferedImage img1 = ImageIO.read(file1);
		BufferedImage img2 = ImageIO.read(file2);
		BufferedImage img3 = ImageIO.read(file3);

		File final_image = null;

		int widthImg1 = img1.getWidth();
		int heightImg1 = img1.getHeight();

		Random rand=new Random();
		int x = rand.nextInt(2);
		System.out.println(x);

		if(x == 0){
			// horizontally
			BufferedImage imgH = new BufferedImage(
					widthImg1+widthImg1+widthImg1, // Final image will have width and height as
					heightImg1, // addition of widths and heights of the images we already have
					BufferedImage.TYPE_INT_RGB);

			imgH.createGraphics().drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions

			imgH.createGraphics().drawImage(img2, widthImg1, 0, null); // here width is mentioned as width of

			imgH.createGraphics().drawImage(img3, widthImg1+widthImg1, 0, null); // here width is mentioned as width of

			final_image = new File("Final.jpg"); //png can also be used here
			ImageIO.write(imgH, "jpeg", final_image); //if png is used, write "png" instead "jpeg"
		}else{
			//Vertically
			BufferedImage imgV = new BufferedImage(
					widthImg1, // Final image will have width and height as
					heightImg1+heightImg1+heightImg1, // addition of widths and heights of the images we already have
					BufferedImage.TYPE_INT_RGB);

			imgV.createGraphics().drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions

			imgV.createGraphics().drawImage(img2, 0, heightImg1, null); // here width is mentioned as width of

			imgV.createGraphics().drawImage(img3, 0, heightImg1+heightImg1, null); // here width is mentioned as width of

			final_image = new File("Final.jpg"); //png can also be used here
			ImageIO.write(imgV, "jpeg", final_image); //if png is used, write "png" instead "jpeg"
		}

		return final_image;
	}

	public static File convertToGray(File input)throws IOException{
		BufferedImage img = ImageIO.read(input);
		File result = null;

		//get image width and height
		int width = img.getWidth();
		int height = img.getHeight();

		//convert to grayscale
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int p = img.getRGB(x,y);

				int a = (p>>24)&0xff;
				int r = (p>>16)&0xff;
				int g = (p>>8)&0xff;
				int b = p&0xff;

				//calculate average
				int avg = (r+g+b)/3;

				//replace RGB value with avg
				p = (a<<24) | (avg<<16) | (avg<<8) | avg;

				img.setRGB(x, y, p);
			}
		}

		//write image
		try{
			result = new File("grayscale.jpg");
			ImageIO.write(img, "jpg", result);
		}catch(IOException e){
			System.out.println(e);
		}
		return result;
	}

}