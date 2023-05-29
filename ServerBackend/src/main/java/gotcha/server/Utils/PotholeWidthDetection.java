package gotcha.server.Utils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;


public class PotholeWidthDetection {


    /**
     * this method get an image who should contain a pothole,
     * @param my_path - path to an image
     * @return double[] where double[0] is width and double[1] is height.
     * @return 0 in cases of no pothole detection or broken image.
     */
    public double[] detect(String my_path) {
        double[] answer = new double[2];

        try {
            byte[] imageArray = Files.readAllBytes(Paths.get(my_path));
            String encoded = Base64.getEncoder().encodeToString(imageArray);
            byte[] data = encoded.getBytes("ASCII");
            String api_key = "1EQ1Efjqv4OtzzFRD7SB"; // Your API Key
            String model_endpoint = "gotcha/3"; // Set model endpoint

            // Construct the URL
            String uploadURL =
                    "https://detect.roboflow.com/" + model_endpoint + "?api_key=" + api_key
                            + "&name=YOUR_IMAGE.jpg";

            // Service Request Config
            System.setProperty("https.protocols", "TLSv1.2");

            // Configure Request
            URL url = new URL(uploadURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(data.length);

            // Write Data
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(data);
            }

            // Get Response
            StringBuilder responseContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }

            JSONObject jsonObject = new JSONObject(responseContent);
            JSONArray predictions = jsonObject.getJSONArray("predictions");
            for (int i = 0; i < predictions.length(); i++) {
                JSONObject prediction = predictions.getJSONObject(i);
                double confidence = prediction.getDouble("confidence");
                if (confidence > 0.75) {
                    answer[0] = prediction.getDouble("width");
                    answer[1] = prediction.getDouble("height");
                    return answer;
                }
            }
        } catch (Exception e) {

        }
        answer[0] = 0;
        answer[1] = 0;
        return answer;
    }
}