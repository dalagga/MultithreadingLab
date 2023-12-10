package org.example.lab3;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {
    private long minFileSize = Long.MAX_VALUE;
    private long maxFileSize = Long.MIN_VALUE;
    private int successfulIterationNumber = 0;
    private int failedIterationNumber = 0;
    private long totalFileSize = 0;
    private List<String> failedUrls = new ArrayList<>();

    public List<String> getFailedUrls() {
        return failedUrls;
    }

    public synchronized void addFailedUrl(String url) {
        failedUrls.add(url);
    }

    public synchronized void updateMinFileSize(long fileSize) {
        minFileSize = Math.min(minFileSize, fileSize);
    }

    public synchronized void updateMaxFileSize(long fileSize) {
        maxFileSize = Math.max(maxFileSize, fileSize);
    }

    public synchronized void updateTotalFileSize(long fileSize) { totalFileSize += fileSize;}

    public synchronized void updateSuccessfulIterationNumber() {
        successfulIterationNumber++;
    }

    public synchronized void updateFailedIterationNumber() {
        failedIterationNumber++;
    }

    public long getMinFileSize() {
        return minFileSize;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public int getSuccessfulIterationNumber() {
        return successfulIterationNumber;
    }

    public int getFailedIterationNumber() {
        return failedIterationNumber;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

}