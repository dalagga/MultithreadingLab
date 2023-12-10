package org.example.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MediaDownloader {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Semaphore semaphore = new Semaphore(numThreads);
        DataContainer dataContainer = new DataContainer();
        long startTime = System.currentTimeMillis(); // get the timestamp for generating file names
        List<String> fileUrls = getStrings();

        for (int i = 0; i < fileUrls.size(); i++) {
            String fileUrl = fileUrls.get(i);
            DownloadingTask task = new DownloadingTask(fileUrl, semaphore, dataContainer, startTime, i);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println();
        System.out.println("Min file size: " + dataContainer.getMinFileSize());
        System.out.println("Max file size: " + dataContainer.getMaxFileSize());
        System.out.println("Average file size: " + dataContainer.getTotalFileSize() / dataContainer.getSuccessfulIterationNumber());
        int failedIteration = dataContainer.getFailedIterationNumber();
        if (failedIteration == 0) {
            System.out.println("All files were downloaded successfully");
        } else {
            System.out.println("Number of failed iterations: " + failedIteration);
            System.out.println("Failed URLs:");
            for (String url : dataContainer.getFailedUrls()) {
                System.out.println(url+"\n");
            }
        }
    }

    private static List<String> getStrings() {
        List<String> fileUrls = new ArrayList<>();
        fileUrls.add("https://i.redd.it/i6ve83lq1yv91.jpg");
        fileUrls.add("https://fastly.picsum.photos/id/769/200/300.jpg?hmac=cl3KEs924CuE_nF1wC98S7NBc8JPXkf0hlwtPXGIIhM");
        fileUrls.add("https://dnm.nflximg.net/api/v6/BvVbc2Wxr2w6QuoANoSpJKEIWjQ/AAAAQX-930vEtRP9rsvHBTX6l-1jfabMcycY5cVTuFqI0GXcWlfc4q5ZQlCNnrxej50k0_UL0lbW25eENTy2VCX15lyXl4ZMduNNr6Ioj2AelJ0b2PhXwlsH40BT7WqMKMrdjs0-TJUr0EYPkjtWH5RW.jpg?r=f84");
        fileUrls.add("https://i.pinimg.com/564x/39/12/0c/39120c806b86c61a14ebdf41878b39e1.jpg");
        fileUrls.add("https://scontent.cdn1instagram.com/v/t51.2885-15/408012149_665691815738857_5018750286023870848_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE0NDAuc2RyIn0&_nc_ht=scontent.cdninstagram.com&_nc_cat=101&_nc_ohc=9bmryVG0rIYAX8_RhGw&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzI1MjU3ODEwOTQ0NDg4NTI5MQ%3D%3D.2-ccb7-5&oh=00_AfAg80-4cgnm3COddvwpCQYwcd3UQ4fvTq-Nop5wlHtinA&oe=657946A0&_nc_sid=10d13b");
        fileUrls.add("https://example.com/1.jpg");
        fileUrls.add("https://i.scdn.co/image/ab67616d0000b2733346c90e405bb64b6300df88");
        fileUrls.add("https://images.saymedia-content.com/.image/t_share/MTc2Mjk4MjU5Mjk4NTkxOTM0/the-end-of-evangelion-has-an-even-darker-unused-ending.jpg");
        fileUrls.add("https://scontent.cdninstagram.com/v/t51.2885-15/408012149_665691815738857_5018750286023870848_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE0NDAuc2RyIn0&_nc_ht=scontent.cdninstagram.com&_nc_cat=101&_nc_ohc=9bmryVG0rIYAX8_RhGw&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzI1MjU3ODEwOTQ0NDg4NTI5MQ%3D%3D.2-ccb7-5&oh=00_AfAg80-4cgnm3COddvwpCQYwcd3UQ4fvTq-Nop5wlHtinA&oe=657946A0&_nc_sid=10d13b");
        return fileUrls;
    }
}