package top.uaian.img2video.img;

import top.uaian.img2video.img2gif.Img2Gif;
import top.uaian.img2video.model.PicPx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * description:  <br>
 * date: 2019/11/14 15:42 <br>
 *
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class Test_BufferImage {

    private static Font font = new Font("微软雅黑", Font.BOLD, 48);

    public static void main(String[] args) throws IOException {
        String source_img_path = "F:\\project-file\\img2video\\img\\001.jpg";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(source_img_path));
        //生成指定宽高的图片
//        createSizeImg(bufferedImage);
        //读取一个图片的RGB的值
//        getRGB(bufferedImage);
        //将图片改成全黑输出
//        setBlackPic(bufferedImage);
        //图片上写字
//        drawContentToPic(bufferedImage);
        //生成新图片
//        createNewPic(bufferedImage);
        //批量生成新图片
//        createNewPics(bufferedImage);
        //转化为GIF
//        Img2Gif.toGif("F:\\project-file\\img2video\\img\\pics\\","F:\\project-file\\img2video\\img\\pics\\mygif.gif");
        //合成两张图片
//        mergeTwoPics(bufferedImage);
        //渲染像素点（从左到右）
//        createChangedPic(bufferedImage);
        //渲染像素点（随机）
        createChangedPic2(bufferedImage);
    }

    private static void createChangedPic2(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        String source_img_path2 = "F:\\project-file\\img2video\\img\\002.jpg";
        BufferedImage newBufferedImage = ImageIO.read(new FileInputStream(source_img_path2));
        List<PicPx> picPxes = new ArrayList<>();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bufferedImage.getRGB(i, j);
                PicPx picPx = new PicPx();
                picPx.setX(i);
                picPx.setY(j);
                picPx.setRgb(pixel);
                picPxes.add(picPx);
            }
        }
        int size = picPxes.size();
        Random random = new Random();
        int count = 0;
        while (true) {
            int next = random.nextInt(size);
            if(( count % 10000 == 0) || count == 0  ) {
                ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\change2\\"+count+
                        ".jpg"));
            }
            if (picPxes.get(next) != null) {
                newBufferedImage.setRGB(picPxes.get(next).getX(),picPxes.get(next).getY(),picPxes.get(next).getRgb());
                picPxes.set(next,null);
                count ++;
            }

            if (count >= size) {
                break;
            }
        }
    }

    private static void createChangedPic(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        BufferedImage newBufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        List<PicPx> picPxes = new ArrayList<>();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bufferedImage.getRGB(i, j);
                PicPx picPx = new PicPx();
                picPx.setR((pixel & 0xff0000) >> 16);
                picPx.setG((pixel & 0xff00) >> 8);
                picPx.setB((pixel & 0xff));
                picPx.setX(i);
                picPx.setY(j);
                picPx.setRgb(pixel);
                picPxes.add(picPx);
            }
        }
        for (int m=0;m<picPxes.size();m++) {
            newBufferedImage.setRGB(picPxes.get(m).getX(),picPxes.get(m).getY(),picPxes.get(m).getRgb());
            if((m % 2000 == 0) || m == 0 ){
                ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\change\\"+m+".jpg"));
            }
        }

    }

    private static void mergeTwoPics(BufferedImage bufferedImage) throws IOException {
        String two_path = "F:\\project-file\\img2video\\img\\GaussianBlur.jpg";
        BufferedImage two_bufferedImage = ImageIO.read(new FileInputStream(two_path));
        Graphics graphics = two_bufferedImage.getGraphics();
        int height = bufferedImage.getHeight();
        int draw_height = (height - 144) / 2;
        graphics.drawImage(bufferedImage,64,draw_height,364,200,null);
        ImageIO.write(two_bufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\merge.jpg"));

    }

    private static void createNewPics(BufferedImage bufferedImage) throws IOException {
        String source_img_path_other = "F:\\project-file\\img2video\\img\\002.jpg";
        BufferedImage bufferedImage_other = ImageIO.read(new File(source_img_path_other));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage newBufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=newBufferedImage.getGraphics();
        //将原始位图缩小后绘制到bufferedImage对象中
        for (int i = 0;i <width; i+=4) {
            graphics.drawImage(bufferedImage_other,0,0,width,height,null);
            graphics.drawImage(bufferedImage,i,0,width,height,null);
            //将bufferedImage对象输出到磁盘上
            ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\pics\\" + i +
                    ".jpg"));
        }

    }

    private static void createNewPic(BufferedImage bufferedImage) throws IOException {
        String source_img_path_other = "F:\\project-file\\img2video\\img\\002.jpg";
        BufferedImage bufferedImage_other = ImageIO.read(new File(source_img_path_other));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage newBufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=newBufferedImage.getGraphics();
        //将原始位图缩小后绘制到bufferedImage对象中
        graphics.drawImage(bufferedImage_other,0,0,width,height,null);
        graphics.drawImage(bufferedImage,width / 2,0,width,height,null);
        //将bufferedImage对象输出到磁盘上
        ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\createPic.jpg"));

    }

    private static void drawContentToPic(BufferedImage bufferedImage) throws IOException {
        String content = "降价出售！";
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(content, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
        graphics.dispose();
        ImageIO.write(bufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\addContent.jpg"));
    }

    private static void setBlackPic(BufferedImage bufferedImage) throws IOException {
        Integer[] rgb = new Integer[3];
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage newBufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                newBufferedImage.setRGB(i,j,0);
            }
        }
        //将bufferedImage对象输出到磁盘上
        ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\black.jpg"));
    }

    private static void getRGB(BufferedImage bufferedImage) {
        Integer[] rgb = new Integer[3];
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
                        + rgb[1] + "," + rgb[2] + ")");
            }
        }


    }

    private static void createSizeImg(BufferedImage bufferedImage) throws IOException {
        System.out.println("原图片width为:" + bufferedImage.getWidth() + ", height为：" + bufferedImage.getHeight());
        int newWidth = 1500;
        int newHeight = 844;
        //定义一个BufferedImage对象，用于保存尺寸更改后的位图
        BufferedImage newBufferedImage=new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=newBufferedImage.getGraphics();
        //将原始位图缩小后绘制到bufferedImage对象中
        graphics.drawImage(bufferedImage,0,0,newWidth,newHeight,null);
        //将bufferedImage对象输出到磁盘上
        ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\newSize.jpg"));
    }





}
