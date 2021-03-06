package top.uaian.img;

import top.uaian.model.ImagePx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
public class BufferImageUtils {

    private static Font font = new Font("微软雅黑", Font.BOLD, 48);


    public static void main(String[] args) throws IOException {
        String source_img_path = "F:\\project-file\\img2video\\img\\001.jpg";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(source_img_path));
        //生成指定宽高的图片
//        createSizeImg(bufferedImage);
        //读取一个图片的RGB的值
//        getRGB(bufferedImage);
        //改变图片的alpha值
//        changePicAlpha(bufferedImage);
        //图片上写字
//        drawContentToPic(bufferedImage);
        //批量生成新图片
//        createNewPics(bufferedImage);
        //合成两张图片
//        mergeTwoPics(bufferedImage);
        //渲染像素点（从左到右）
//        createChangedPic(bufferedImage);
        //渲染像素点（随机）
//        createChangedPic2(bufferedImage);

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

    private static void changePicAlpha(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        //TYPE_INT_ARGB表示Color为int(4字节),alpha通道位为24-31,红色通道为16-23,绿色为8-15,蓝色为0-7.
        //TYPE_INT_RGB以与TYPE_INT_ARGB相同的方式表示为int(4字节)的颜色,但Alpha通道被忽略(或第24-31位为0).
        BufferedImage newBufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        BufferedImage back = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = back.getGraphics();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        for (int alpha = 0; alpha < 256; alpha++) {
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    int pixel = bufferedImage.getRGB(i,j);
                    Color color = new Color(pixel);
                    Color colorNew = new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha);
                    newBufferedImage.setRGB(i,j,colorNew.getRGB());
                }
            }
            graphics.drawImage(newBufferedImage,0,0,null);
            //将bufferedImage对象输出到磁盘上
            ImageIO.write(back,"jpg",new File("F:\\project-file\\img2video\\img\\alpha\\ "+ alpha +".jpg"));
        }
    }

    private static void createChangedPic2(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        String source_img_path2 = "F:\\project-file\\img2video\\img\\002.jpg";
        BufferedImage newBufferedImage = ImageIO.read(new FileInputStream(source_img_path2));
        List<ImagePx> imagePxes = new ArrayList<>();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bufferedImage.getRGB(i, j);
                ImagePx imagePx = new ImagePx();
                imagePx.setX(i);
                imagePx.setY(j);
                imagePx.setRgb(pixel);
                imagePxes.add(imagePx);
            }
        }
        int size = imagePxes.size();
        Random random = new Random();
        int count = 0;
        while (true) {
            int next = random.nextInt(size);
            if(( count % 10000 == 0) || count == 0  ) {
                ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\change2\\"+count+
                        ".jpg"));
            }
            if (imagePxes.get(next) != null) {
                newBufferedImage.setRGB(imagePxes.get(next).getX(), imagePxes.get(next).getY(), imagePxes.get(next).getRgb());
                imagePxes.set(next,null);
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
        List<ImagePx> imagePxes = new ArrayList<>();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bufferedImage.getRGB(i, j);
                ImagePx imagePx = new ImagePx();
                imagePx.setR((pixel & 0xff0000) >> 16);
                imagePx.setG((pixel & 0xff00) >> 8);
                imagePx.setB((pixel & 0xff));
                imagePx.setX(i);
                imagePx.setY(j);
                imagePx.setRgb(pixel);
                imagePxes.add(imagePx);
            }
        }
        for (int m = 0; m< imagePxes.size(); m++) {
            newBufferedImage.setRGB(imagePxes.get(m).getX(), imagePxes.get(m).getY(), imagePxes.get(m).getRgb());
            if((m % 2000 == 0) || m == 0 ){
                ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\change\\"+m+".jpg"));
            }
        }

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
        for (int i = 0;i <width; i+=4) {
            graphics.drawImage(bufferedImage_other,0,0,width,height,null);
            graphics.drawImage(bufferedImage,i,0,width,height,null);
            //将bufferedImage对象输出到磁盘上
            ImageIO.write(newBufferedImage,"jpg",new File("F:\\project-file\\img2video\\img\\pics\\" + i +
                    ".jpg"));
        }

    }














}
