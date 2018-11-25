import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test {

    public static void main(String[] args) throws Exception {

        //default filenames if run without arguments
        String infilename = "cattura.png";
        String outfilename = "output.png";
        if(args.length == 2) {
            infilename = args[0];
            outfilename = args[1];
        }

        
        //set up files
        File imageFile = new File(infilename);
        File outFile = new File(outfilename);
        if(outFile.exists()) outFile.delete();
        outFile.createNewFile();

        BufferedImage image = ImageIO.read(imageFile);

        //copy the image into a two-dimensional array that's easier to work with
        int[][] imagePixels = new int[image.getHeight()][image.getWidth()];

        for(int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                imagePixels[i][j] = image.getRGB(j, i);
            }
        }

        //align the horizontal rows
        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(imagePixels[i][j]);

                //red/green are only different from blue on key pixels
                if(color.getRed() != color.getBlue() || color.getGreen() != color.getBlue()) {
                    int[] newRow = rotate(imagePixels[i],j+2);
                    //add two to over-rotate the color line to the right side as in the solution

                    imagePixels[i] = newRow;
                }
            }
        }

        //🍆 (look, this part solves the bonus and the solution says 'EGGPLANT')
        boolean done = false;
        boolean shaker = false;
        while(!done) {
            done = true;

            //This is the first time I've ever used ternaries in a way that makes sense
            for(int i = shaker ? 0 : image.getHeight()-2; shaker ? i < image.getHeight()-1 : i >= 0;) {
                Color currow = new Color(imagePixels[i][image.getWidth() - 1]);
                Color nextrow = new Color(imagePixels[i + 1][image.getWidth() - 1]);
                if (currow.getRed() > nextrow.getRed()) {
                    int[] swap = imagePixels[i];
                    imagePixels[i] = imagePixels[i+1];
                    imagePixels[i+1] = swap;
                    done = false;
                }
                if(shaker) i++;
                else i--;
            }
            shaker = !shaker;
        }
        //I think this is a decent cocktail shaker sort now, at least
        //quicksort is still beyond me

        //write the array back to the image buffer
        for(int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, imagePixels[i][j]);
            }
        }

        //save the file
        ImageIO.write(image, "png", outFile);

    }

    //can now intelligently 'wrap around'
    public static int[] rotate(int[] inArray, int distance) {

        distance = (distance % inArray.length);

        int[] output = new int[inArray.length];

        int j = 0;

        //NOW it makes sense to use arraycopy
        System.arraycopy(inArray, distance, output, 0, inArray.length-distance);

        System.arraycopy(inArray, 0, output, inArray.length-distance, distance);

        return output;
    }
}