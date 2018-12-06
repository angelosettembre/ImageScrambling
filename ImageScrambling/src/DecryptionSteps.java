import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class DecryptionSteps {
	
	public static void gray2Ycbr() throws IOException {
		int totalWidth = Keys.width;
		int totalHeight = Keys.height;
		int cols = Keys.cols;
		int rows = Keys.rows;
		int type = Keys.type;
		
		BufferedImage img = ImageIO.read(new File("img/KEY.jpg"));
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
			result = new File("img/grayscale.jpg");
			ImageIO.write(img, "jpg", result);
		}catch(IOException e){
			System.out.println(e);
		}
		
		Files.deleteIfExists(Paths.get("img/out.jpg"));

		//return result;
		
		/*
		BufferedImage combineImage = new BufferedImage(totalWidth, totalHeight, type);
		int stackWidth = 0;
		int stackHeight = 0;
		for (int i = 0; i <= cols; i++) {
			for (int j = 0; j <= rows; j++) {
				combineImage.createGraphics().drawImage(imageRejoined[i][j], stackWidth, stackHeight, null);
				stackHeight += imageRejoined[i][j].getHeight();
			}
			stackWidth += imageRejoined[i][0].getWidth();
			stackHeight = 0;
		}

		ImageIO.write(combineImage, "jpg", new File("img/KEY.jpg"));
		System.out.println("Image rejoin done.");
		*/

	}
}
