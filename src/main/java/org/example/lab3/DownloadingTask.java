package org.example.lab3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.util.concurrent.Semaphore;

public class DownloadingTask implements Runnable {
    public DownloadingTask(String url, Semaphore semaphore, DataContainer dataContainer, long startTime, int iterationNumber) {
        this.url = url;
        this.semaphore = semaphore;
        this.dataContainer = dataContainer;
        this.startTime = startTime;
        this.iterationNumber = iterationNumber;
    }
    String directoryForFiles = "E:/Downloads";
    private String url;
    private Semaphore semaphore;
    private DataContainer dataContainer;
    private long startTime;
    private int iterationNumber;
    private String getFileFormat(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1]; // get the part of the URL after the last '/'
        String[] queryParts = lastPart.split("\\?");
        lastPart = queryParts[0]; // get the part of the file name before the '?'
        int dotIndex = lastPart.lastIndexOf('.');
        if (dotIndex == -1) {
            /*Usually, photo viewers ignore the file extension in the file name
            and recognize it by its content, so an incorrect extension
            in the file name shouldn't cause any problems.
            Either way, lets consider it still better than the "unknown" extension ( ⓛ ﻌ ⓛ *)*/
            return "jpg";
        }
        return lastPart.substring(dotIndex + 1);
    }

    private String generateFileName(String url) {
        String filename = startTime + "_" + iterationNumber + "." + getFileFormat(url);
        return filename;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            URL imageUrl = new URL(url);
            String fileName = generateFileName(url);
            Path dirPath = Path.of(directoryForFiles);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path outputPath = dirPath.resolve(fileName);

            try (InputStream in = imageUrl.openStream()) {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Downloading " + fileName + " | Thread: " + Thread.currentThread().getName());
                dataContainer.updateMinFileSize(Files.size(outputPath));
                dataContainer.updateMaxFileSize(Files.size(outputPath));
                dataContainer.updateTotalFileSize(Files.size(outputPath));
                dataContainer.updateSuccessfulIterationNumber();
                System.out.println(fileName + " is downloaded | Size: " + Files.size(outputPath) + " bytes");
            } catch (IOException e) {
                System.out.println("Error downloading " + iterationNumber + " file");
                dataContainer.addFailedUrl(url);
                dataContainer.updateFailedIterationNumber();
                //e.printStackTrace();
            }
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}