package top.uaian.img2video.img;

import org.omg.PortableInterceptor.INACTIVE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * description:  <br>
 * date: 2019/11/14 15:42 <br>
 *
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class Test_BufferImage {
    public static void main(String[] args) throws IOException {
        String source_img_path = "F:\\project-file\\img2video\\img\\001.jpg";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(source_img_path));
        //生成指定宽高的图片
//        createSizeImg(bufferedImage);
        //读取一个图片的RGB的值
//        getRGB(bufferedImage);
        //将图片改成全黑输出
        setBlackPic(bufferedImage);
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
