package DownloadImage;

import Tools.ElapsedTimer;
import ScrapeAmazonCsv.ExportCSV;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Scanner;

public class DownloadImage extends Thread{

    String outputFolder = "F:\\CreateVideo_jar\\files\\productImages2";
    String[] links = readLinks("Z:\\Coding\\My Projects\\AmazonScraper\\java\\DownloadImage\\imageList\\videoImages.txt");
    static int threads;
    int thisThread;
    static int count = 0;
    static int linkCount;
    static boolean finish = false;
    static int failed = 0;

    private static FileWriter fw;
    private static BufferedWriter writer;

    private Proxy proxy;
    private HttpURLConnection connection;
    InputStream inputStream;


    public static void main(String[]args) throws IOException{
        DownloadImage t1 = new DownloadImage(1);
        DownloadImage t2 = new DownloadImage(2);
        DownloadImage t3 = new DownloadImage(3);
        DownloadImage t4 = new DownloadImage(4);
        DownloadImage t5 = new DownloadImage(5);
        DownloadImage t6 = new DownloadImage(6);
        DownloadImage t7 = new DownloadImage(7);
        DownloadImage t8 = new DownloadImage(8);
        DownloadImage t9 = new DownloadImage(9);
        DownloadImage t10 = new DownloadImage(10);
        DownloadImage t11 = new DownloadImage(11);
        DownloadImage t12= new DownloadImage(12);
        DownloadImage t13 = new DownloadImage(13);
        DownloadImage t14 = new DownloadImage(14);
        DownloadImage t15 = new DownloadImage(15);
        DownloadImage t16 = new DownloadImage(16);

        threads = 10;

        t1.createFile();

        System.out.println("Loading links...");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
//        t11.start();
//        t12.start();
//        t13.start();
//        t14.start();
//        t15.start();
//        t16.start();

        System.in.read();

        System.out.println("Terminated Process");
        try{
            saveFile();
            finish = true;

        }catch(Exception e){
            System.out.println("Already saved and exported");
        }
        System.out.println("Workbook Saved");

        //downloadImage.printLinks(links);



    }



    public void run()  {

        int attempts = 0;
        linkCount = thisThread-1;

        while (linkCount < links.length && !finish){

            int percentage = (count*100)/links.length;
            System.out.println("Completed " + percentage + "%...           " + count + "|" + links.length + "       Elapsed Time : " + ElapsedTimer.getElapsedTime() +"       Thread : " + thisThread + "   Failed : " + failed + "\n");


            try {

                getUrl(links[linkCount]);
                attempts = 0;
                count++;
                //linkCount+=threads;
                linkCount++;

            } catch (Exception e) {

                System.out.println("failed to download...	" + "  link : " + links[linkCount] + "   Failed : " + failed + " : " + e.getMessage());
                System.out.println("attempts : " + attempts);
                if(attempts >= 3){
                    failed++;
                    linkCount++;
                    attempts = 0;
                    try {
                        writer.append(links[linkCount] + "\n");
                    }catch (Exception e1){
                    }
                }
                else {
                    attempts++;
                }
            }


        }

        try {
            saveFile();
        }catch (Exception e){
            System.out.println("Couldnt save the file");
        }

    }



    public void getUrl(String getUrls) throws IOException {

        URL url = verify(getUrls);

        //proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ExportCSV.proxy, Integer.parseInt(ExportCSV.port)));
        connection = (HttpURLConnection) url.openConnection(); //proxy

        inputStream = null;
        String filename = url.getFile();
        filename = filename.substring(filename.lastIndexOf('/') + 1);
        filename = filename.replace("%","");

        FileOutputStream outputStream = new FileOutputStream(outputFolder + File.separator + filename);

        inputStream = connection.getInputStream();

        int read = -1;
        byte[] buffer = new byte [4096];

        while((read = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, read);
        }

        inputStream.close();
        outputStream.close();

        System.out.println("Downloading...	" + "  link : " + getUrls);

    }





    public DownloadImage(int thread){
        thisThread = thread;
    }

    public DownloadImage(String path){
        outputFolder = path;

    }

    private static URL verify(String url){
        if(!url.toLowerCase().startsWith("https://")){
            return null;
        }
        URL verifyUrl = null;

        try{
            verifyUrl = new URL(url);
        }catch(Exception e){

        }

        return verifyUrl;
    }

    public static void createFile(){
        File file = new File("Z:\\Coding\\My Projects\\AmazonScraper\\java\\DownloadImage\\failedImages.txt");
        try {
            fw = new FileWriter(file.getAbsolutePath(), true);
            writer = new BufferedWriter(fw);
        }catch(Exception e){
            System.out.println(e +" Failed to write failed Image");
        }
    }

    public static void saveFile() throws IOException {
        writer.flush();
        writer.close();
        System.out.println("downloadImages saved" );
    }

    public String[] readLinks(String file){
        int ctr = 0;
        try{
            Scanner s1 = new Scanner(new File(file));
            while (s1.hasNextLine()){
                ctr = ctr + 1;
                s1.next();
            }

            String[] link = new String[ctr];
            Scanner s2 = new Scanner(new File(file));

            for (int i = 0; i < ctr; i = i + 1){
                link[i] = s2.next();
            }
            return link;
        }
        catch (FileNotFoundException e){

        }
        return null;

    }

    public void printLinks(String[] array){
        for (int i = 0; i < array.length; i++){
            System.out.println(i + " : " + array[i]);
        }
    }

}



















