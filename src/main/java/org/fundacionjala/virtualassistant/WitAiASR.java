package org.fundacionjala.virtualassistant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WitAiASR {
    private static final String WIT_AI_TOKEN = "KY7NZTDMVC2AW3MQ67DCWWRNSSYK6SCV";
    private static final String WIT_AI_API_URL = "https://api.wit.ai/speech";

    public static void main(String[] args) {
        String language = "es";
        String audioFilePath = "Audio En Español";
        try {
            File audioFile = new File(audioFilePath);
            URL url = new URL(WIT_AI_API_URL + "?lang=" + language);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + WIT_AI_TOKEN);
            connection.setRequestProperty("Content-Type", "audio/wav");
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            FileInputStream fis = new FileInputStream(audioFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            fis.close();
            dos.flush();
            dos.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                var af = response.toString().split("\"text\":");
                var asw = af[af.length - 1].split("\",");
                System.out.println(asw[0] + "\"");

            } else {
                System.err.println("Error in request " + responseCode + " " + connection.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String obtenerUltimoTexr(String input) {
        Pattern pattern = Pattern.compile("text: \\S+");
        Matcher matcher = pattern.matcher(input);
        String ultimoTexr = null;
        while (matcher.find()) {
            ultimoTexr = matcher.group();
        }
        return ultimoTexr;
    }
}