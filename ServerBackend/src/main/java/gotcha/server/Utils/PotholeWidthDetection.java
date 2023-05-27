package gotcha.server.Utils;



import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PotholeWidthDetection {
    public void detect() {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the image using ImageIO
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\amitm\\Desktop\\Workspace\\Seminar\\Iteration 3\\1164270.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the image to OpenCV Mat format
        Mat image = bufferedImageToMat(bufferedImage);

        // Convert the image to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Apply thresholding to convert to binary image
        Mat binaryImage = new Mat();
        Imgproc.threshold(grayImage, binaryImage, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        // Find contours
        Mat contours = new Mat();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binaryImage, (List<MatOfPoint>) contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Filter contours based on size or area
        Mat filteredContours = new Mat();
        double minContourArea = 100; // Minimum area to filter out small contours
        for (int i = 0; i < contours.size().height; i++) {
            double[] contourArea = new double[]{Imgproc.contourArea(contours.row(i))};
            if (contourArea[0] > minContourArea) {
                filteredContours.push_back(contours.row(i));
            }
        }

        // Calculate width of the largest contour
        double maxWidth = 0;
        for (int i = 0; i < filteredContours.size().height; i++) {
            Rect boundingRect = Imgproc.boundingRect(new MatOfPoint(filteredContours.row(i)));
            double width = boundingRect.width;
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        System.out.println("Pothole width: " + maxWidth);
    }
    // Utility method to convert BufferedImage to Mat
    private static Mat bufferedImageToMat(BufferedImage image) {
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
}
