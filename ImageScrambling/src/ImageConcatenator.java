import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConcatenator
{
	public static void main(String[] args) throws IOException, Exception
	{
		File file1 = new File("redYCBR.jpg");
		File file2 = new File("greenYCBR.jpg");
		File file3 = new File("blueYCBR.jpg");

		BufferedImage img1 = ImageIO.read(file1);
		BufferedImage img2 = ImageIO.read(file2);
		BufferedImage img3 = ImageIO.read(file3);

		int widthImg1 = img1.getWidth();
		int heightImg1 = img1.getHeight();

		BufferedImage imgH = new BufferedImage(
				widthImg1+widthImg1+widthImg1, // Final image will have width and height as
				heightImg1, // addition of widths and heights of the images we already have
				BufferedImage.TYPE_INT_RGB);

		imgH.createGraphics().drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions

		imgH.createGraphics().drawImage(img2, widthImg1, 0, null); // here width is mentioned as width of

		imgH.createGraphics().drawImage(img3, widthImg1+widthImg1, 0, null); // here width is mentioned as width of

		// horizontally
		File final_image = new File("Finalhor.jpg"); //png can also be used here
		ImageIO.write(imgH, "jpeg", final_image); //if png is used, write "png" instead "jpeg"

		BufferedImage imgV = new BufferedImage(
				widthImg1, // Final image will have width and height as
				heightImg1+heightImg1+heightImg1, // addition of widths and heights of the images we already have
				BufferedImage.TYPE_INT_RGB);

		imgV.createGraphics().drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions

		imgV.createGraphics().drawImage(img2, 0, heightImg1, null); // here width is mentioned as width of

		imgV.createGraphics().drawImage(img3, 0, heightImg1+heightImg1, null); // here width is mentioned as width of

		// Vertical
		File final_imageV = new File("FinalV.jpg"); //png can also be used here
		ImageIO.write(imgV, "jpeg", final_imageV); //if png is used, write "png" instead "jpeg"
	}
}