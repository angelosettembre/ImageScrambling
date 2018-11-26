import java.io.File;
import java.io.IOException;

public class Runner {

	public static void main(String[] args) throws IOException {

		File input = new File("cattura.jpg");
		
		EncryptionSteps.initialize(input);
		File red = EncryptionSteps.extractRed(input);
		File green = EncryptionSteps.extractGreen(input);
		File blue = EncryptionSteps.extractBlue(input);
		
		File ycbr_red = EncryptionSteps.rgb2ybcr(red);
		File ycbr_green = EncryptionSteps.rgb2ybcr(green);
		File ycbc_blue = EncryptionSteps.rgb2ybcr(blue);
		
		File final_image = EncryptionSteps.concatenateImage(ycbr_red, ycbr_green, ycbc_blue);
		File output = EncryptionSteps.convertToGray(final_image);
		
		
	}

}
