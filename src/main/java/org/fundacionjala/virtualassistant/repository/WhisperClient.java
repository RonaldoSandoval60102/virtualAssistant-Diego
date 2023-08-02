package org.fundacionjala.virtualassistant.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;

public class WhisperClient implements ASRClient{

    private String transcription;
    
    @Override
    public String convertToText(Path audioFile) {
        try {
            try (Socket socket = new Socket("127.0.0.0", 12345);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out.println(audioFile);
                String transcribedText = in.readLine();
                transcription = transcribedText;
                return transcription;
            }
        } catch (IOException e) {
            System.out.println("Error in Convert To Text: " + e.getMessage());
        }
        return null;
    }
    
}
