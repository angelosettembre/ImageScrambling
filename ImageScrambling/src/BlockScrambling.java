import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlockScrambling {

	private static int totalCols,totalRows;

	public static void splitImage(File input)throws IOException{
		BufferedImage originalImg = ImageIO.read(input);
		int squareLength = 128;

		int originalWidth = originalImg.getWidth();
		int originalHeight = originalImg.getHeight();

		int rowsOfSquare = originalHeight / squareLength;
		int colsOfSquare = originalWidth / squareLength;
		int squaresCount = colsOfSquare * rowsOfSquare;
		System.out.println("total numblock "+squaresCount);

		
        int widthOutOfSquaresRemainder = originalWidth % squareLength;
        boolean hasWidthRemainder = widthOutOfSquaresRemainder != 0;
        System.out.println("RESTO withd" + hasWidthRemainder);
        totalCols = hasWidthRemainder ? colsOfSquare + 1 : colsOfSquare;
        
        int heightOutOfSquaresRemainder = originalHeight % squareLength;
        boolean hasHeightRemainder = heightOutOfSquaresRemainder != 0;
        System.out.println("RESTO height" + hasHeightRemainder);
        totalRows = hasHeightRemainder ? rowsOfSquare + 1 : rowsOfSquare;
		 

		/**
		 *  Regular square image chunks crop and output.
		 */
		BufferedImage imgs[] = new BufferedImage[squaresCount];
		int count = 0;
		for (int x = 0; x < colsOfSquare; x++) {
			for (int y = 0; y < rowsOfSquare; y++) {
				//Initialize the image array with image chunks
				imgs[count] = new BufferedImage(squareLength, squareLength, originalImg.getType());

				// draws the image chunk
				Graphics2D gr = imgs[count].createGraphics();
				gr.drawImage(originalImg, 0, 0, squareLength, squareLength, squareLength * x, squareLength * y, squareLength * x + squareLength, squareLength * y + squareLength, null);
				gr.dispose();

				// output chunk
				writeToFile(imgs[count], x, y);
				count++;
			}
		}


		/**
		 *  Remainder chunks crop and output.
		 */
		// Right end column width remainder image chunks crop and output.
		/*
        if (hasWidthRemainder) {
            BufferedImage rightRemainderChunks[] = new BufferedImage[rowsOfSquare];
            for (int i = 0; i < rowsOfSquare; i++) {
                rightRemainderChunks[i] = new BufferedImage(widthOutOfSquaresRemainder, squareLength, originalImg.getType());

                Graphics2D gr = rightRemainderChunks[i].createGraphics();
                gr.drawImage(originalImg, 0, 0, widthOutOfSquaresRemainder, squareLength, squareLength * colsOfSquare, squareLength * i, squareLength * colsOfSquare + widthOutOfSquaresRemainder, squareLength * i + squareLength, null);
                gr.dispose();

                writeToFile(rightRemainderChunks[i], totalCols - 1, i);
            }
        }
		 */
		/*
        //  Bottom row height remainder image chunks crop and output.
        if (hasHeightRemainder) {
            BufferedImage bottomRemainderChunks[] = new BufferedImage[colsOfSquare];
            for (int i = 0; i < colsOfSquare; i++) {
                bottomRemainderChunks[i] = new BufferedImage(squareLength, heightOutOfSquaresRemainder, originalImg.getType());

                Graphics2D gr = bottomRemainderChunks[i].createGraphics();
                gr.drawImage(originalImg, 0, 0, squareLength, heightOutOfSquaresRemainder, squareLength * i, squareLength * rowsOfSquare, squareLength * i + squareLength, squareLength * rowsOfSquare + heightOutOfSquaresRemainder, null);
                gr.dispose();

                writeToFile(bottomRemainderChunks[i], i, totalRows - 1);
            }
        }

        //  Bottom right corner remainder image chunk crops and outputs.
        if (hasWidthRemainder && hasHeightRemainder) {
            BufferedImage cornerRemainderImage = new BufferedImage(widthOutOfSquaresRemainder, heightOutOfSquaresRemainder, originalImg.getType());

            Graphics2D gr = cornerRemainderImage.createGraphics();
            gr.drawImage(originalImg, 0, 0, widthOutOfSquaresRemainder, heightOutOfSquaresRemainder, squareLength * colsOfSquare, squareLength * rowsOfSquare, originalWidth, originalHeight, null);
            gr.dispose();

            writeToFile(cornerRemainderImage, totalCols - 1, totalRows - 1);
        }
		 */
	}

	private static void writeToFile(BufferedImage image, int colNum, int rowNum) throws IOException {

        File file = new File("split/img_" + colNum + "_" + (totalCols - 1) + "_" + rowNum + "_" + (totalRows - 1) + ".jpg");
        ImageIO.write(image, "jpg", file);
	}

	public static void join() throws IOException{
		int totalWidth = 0;
		int totalHeight = 0;
		int cols;   // 0 base
		int rows;   // 0 base
		int type;

		/**
		 *  Load images from output folder.
		 */
		File folder = new File("split");
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles.length == 0) {
			throw new IOException("No files are found in the output folder.");
		}

		/**
		 *  Get data from first file.
		 */
		String[] firstFileName = listOfFiles[0].getName().split("_");
		cols = Integer.parseInt(firstFileName[2]);
		rows = Integer.parseInt(firstFileName[4].split("\\.")[0]); // remove .bmp
		BufferedImage firstImage = ImageIO.read(listOfFiles[0]);
		type = firstImage.getType();

		/**
		 *  Set up 2d array holding image chunks.
		 */
		BufferedImage[][] imageChunks = new BufferedImage[cols + 1][rows + 1];
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String[] name = file.getName().split("_");
				int colNum = Integer.parseInt(name[1]);
				int rowNum = Integer.parseInt(name[3]);

				BufferedImage image = ImageIO.read(file);

				if (colNum == 0) {
					totalHeight += image.getHeight();
				}
				if (rowNum == 0) {
					totalWidth += image.getWidth();
				}


				imageChunks[colNum][rowNum] = image;
			}
		}

		/**
		 *  Assign image chunks from 2d array to original one image.
		 */
		BufferedImage combineImage = new BufferedImage(totalWidth, totalHeight, type);
		int stackWidth = 0;
		int stackHeight = 0;
		for (int i = 0; i <= cols; i++) {
			for (int j = 0; j <= rows; j++) {
				combineImage.createGraphics().drawImage(imageChunks[i][j], stackWidth, stackHeight, null);
				stackHeight += imageChunks[i][j].getHeight();
			}
			stackWidth += imageChunks[i][0].getWidth();
			stackHeight = 0;
		}

		ImageIO.write(combineImage, "jpg", new File("img/join.jpg"));
		System.out.println("Image rejoin done.");
		
		

	}
}
