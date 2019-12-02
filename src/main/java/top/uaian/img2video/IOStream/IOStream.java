package top.uaian.img2video.IOStream;

import java.io.*;

/**
 * description:  IO流常用API<br>
 * date: 2019/12/2 14:40 <br>
 *
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class IOStream {
    public static void main(String[] args) throws IOException {
        //文件
//        createFile();
        //字节流读取，输出图片
//        byteStream();
        //字符流读取，输出文本
//        characterStream();
        //高效流
//        bufferStream();
        //转换流
//        transStream();
    }


    private static void createFile() throws IOException {
        //创建目录
        File catalog =  new File("F:\\project-file\\img2video\\img\\ioStream");
        if (!catalog.exists()) {
            catalog.mkdir();
        }
        //创建文件
        File file = new File("F:\\project-file\\img2video\\img\\ioStream\\created.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    private static void byteStream() throws IOException {
        //字节输入流
        FileInputStream fileInputStream = new FileInputStream(new File("F:\\project-file\\img2video\\img\\001.jpg"));
        //字节输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\project-file\\img2video\\img\\ioStream" +
                "\\001.jpg"));
        int start = 0;
        //会输出255 216 1 0... 因为底层存的是数值
        while ((start = fileInputStream.read()) != -1) {
//            System.out.println(start);
            fileOutputStream.write(start);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }


    private static void characterStream() throws IOException {
        //字符输入流
        FileReader fileReader = new FileReader(new File("F:\\project-file\\img2video\\img\\ioStream\\read.txt"));
        //字符输出流
        FileWriter fileWriter = new FileWriter(new File("F:\\project-file\\img2video\\img\\ioStream\\write.txt"));
        int start = 0;
        while ((start = fileReader.read()) != -1) {
            fileWriter.write(start);
            System.out.print((char) start);
        }
        //必须刷新流，否则写出无效
        fileWriter.flush();
        fileReader.close();
        fileWriter.close();

    }


    private static void bufferStream() throws IOException {
        long bufferStartTime = System.currentTimeMillis();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("F:\\project-file\\img2video" +
                "\\img\\001.jpg")));
        int bufferStart = 0;
        while ((bufferStart = bufferedInputStream.read()) != -1) {
//            System.out.print(bufferStart + " ");
        }
        System.out.println("BufferedInputStream耗时 : " + (System.currentTimeMillis() - bufferStartTime));
        long startTime = System.currentTimeMillis();
        FileInputStream inputStream = new FileInputStream(new File("F:\\project-file\\img2video" +
                "\\img\\001.jpg"));
        int start = 0;
        while ((start = inputStream.read()) != -1) {
//            System.out.print(start + " ");
        }
        System.out.println("FileInputStream耗时 : " + (System.currentTimeMillis() - startTime));

    }

    private static void transStream() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("F:\\project-file" +
                "\\img2video\\img\\ioStream\\read.txt")));
        int start = 0;
        while ((start = inputStreamReader.read()) != -1) {
            System.out.print(start + " ");
        }
    }
}
