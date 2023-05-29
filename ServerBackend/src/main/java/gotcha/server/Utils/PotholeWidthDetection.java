package gotcha.server.Utils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;


public class PotholeWidthDetection {
    String my_path = "C:\\Users\\amitm\\Desktop\\Workspace\\Seminar\\Iteration 3\\1234.jpg";

    public void detect1(){
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            // Read the image file
            Path path = Paths.get(my_path);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Create an image object
            Image image = Image.newBuilder().setContent(imgBytes).build();

            // Create a feature for circle detection
            Feature feature = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();

            // Create the request
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder()
                            .addFeatures(feature)
                            .setImage(image)
                            .build();

            // Send the request and get the response
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));

            // Process the response
            if (response.getResponsesCount() > 0) {
                AnnotateImageResponse annotateImageResponse = response.getResponses(0);
                if (annotateImageResponse.hasError()) {
                    System.out.println("Error: " + annotateImageResponse.getError().getMessage());
                } else {
                    List<LocalizedObjectAnnotation> annotations = annotateImageResponse.getLocalizedObjectAnnotationsList();
                    if (annotations.isEmpty()) {
                        System.out.println("No circle found in the image.");
                    } else {
                        // Assuming there's only one circle detected
                        LocalizedObjectAnnotation circleAnnotation = annotations.get(0);
                        double circleSize = 2 * Math.PI * circleAnnotation.getBoundingPoly().getNormalizedVertices(0).getX();

                        // Output the circle size
                        System.out.println("Circle size: " + circleSize);
                    }
                }
            } else {
                System.out.println("No response received.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void detect() {
        try {
            // Load the image file
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/path/to/service_account_key.json"));
            BufferedImage image = ImageIO.read(new File(my_path));

            // Convert the image to grayscale
            BufferedImage grayImage = convertToGrayscale(image);

            // Detect circles using Hough Circle Transform
            int minRadius = 1; // Minimum radius of the circles to detect
            int maxRadius = 250; // Maximum radius of the circles to detect
            int threshold = 150; // Threshold for circle detection
            Circle[] circles = detectCircles(grayImage, minRadius, maxRadius, threshold);

            // Find the largest circle
            Circle largestCircle = findLargestCircle(circles);

            if (largestCircle != null) {
                // Calculate the circle size based on its radius
                double circleSize = 2 * Math.PI * largestCircle.radius;

                // Output the circle size
                System.out.println("Circle size: " + circleSize);
            } else {
                System.out.println("No blue circle found in the image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Convert an image to grayscale
    private static BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        grayImage.getGraphics().drawImage(image, 0, 0, null);
        return grayImage;
    }

    // Circle detection using Hough Circle Transform
    private static Circle[] detectCircles(BufferedImage image, int minRadius, int maxRadius, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRaster().getPixels(0, 0, width, height, (int[]) null);

        // Accumulator array to store the votes for circle centers
        int[][][] accumulator = new int[width][height][maxRadius - minRadius + 1];

        // Voting
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelValue = pixels[y * width + x];
                if (pixelValue == Color.BLUE.getRGB()) {
                    for (int r = minRadius; r <= maxRadius; r++) {
                        for (int theta = 0; theta < 360; theta++) {
                            int a = x - (int) (r * Math.cos(Math.toRadians(theta)));
                            int b = y - (int) (r * Math.sin(Math.toRadians(theta)));

                            if (a >= 0 && a < width && b >= 0 && b < height) {
                                accumulator[a][b][r - minRadius]++;
                            }
                        }
                    }
                }
            }
        }

        // Find circles with enough votes
        Circle[] circles = new Circle[width * height * (maxRadius - minRadius + 1)];
        int circleCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int r = minRadius; r <= maxRadius; r++) {
                    if (accumulator[x][y][r - minRadius] >= threshold) {
                        circles[circleCount++] = new Circle(x, y, r);
                    }
                }
            }
        }

        // Trim the circles array to the actual number of circles found
        return circleCount > 0 ? java.util.Arrays.copyOf(circles, circleCount) : new Circle[0];
    }

    // Find the largest circle in an array of circles
    private static Circle findLargestCircle(Circle[] circles) {
        Circle largestCircle = null;
        double maxRadius = 0;
        for (Circle circle : circles) {
            if (circle.radius > maxRadius) {
                largestCircle = circle;
                maxRadius = circle.radius;
            }
        }
        return largestCircle;
    }

    // Class representing a circle with center coordinates and radius
    private static class Circle {
        int centerX;
        int centerY;
        int radius;

        Circle(int centerX, int centerY, int radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }
}