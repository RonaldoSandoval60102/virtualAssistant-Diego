package org.fundacionjala.virtualassistant.repository;

import java.nio.file.Path;

public interface ASRClient {
    
    String convertToText(Path audioFile);
}
