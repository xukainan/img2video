package top.uaian.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.uaian.model.ImagePx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

/**
 * description:  图片处理工具类<br>
 * date: 2019/11/14 15:42 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class ImageUtils {

    static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    private static Font font = new Font("微软雅黑", Font.BOLD, 48);


    public static void main(String[] args) throws IOException {
        String imgUrl = "F:\\project-file\\img2video\\img\\001.jpg";
        String imgDirectoryName = UUID.randomUUID().toString();
        String imgDirectoryPath = Files.createTempDirectory(imgDirectoryName).toString();
        //按比例放大缩小图片
//        float scale = 1.20f;
//        changeSize(imgUrl, scale, imgDirectoryPath);
        //读取一个图片的RGB的值
//        getRGB(imgUrl, 3, 5);
        //改变图片的alpha值
//        changeAlpha(imgUrl, imgDirectoryPath, 0);
        //图片上写字
//        String content = "降价出售！";
//        drawContentOnImg(imgUrl, content, 320, 180, imgDirectoryPath);
        //移动效果
//        String backImgUrl =  "F:\\project-file\\img2video\\img\\002.jpg";
//        int step = 4;
//        move(backImgUrl, imgUrl, step, imgDirectoryPath);
        //渐变效果，像素点实现
        String backImgUrl =  "F:\\project-file\\img2video\\img\\002.jpg";
        tranByPx(imgUrl, backImgUrl, imgDirectoryPath);
        //渐变效果，alpha值实现

    }

    private static BufferedImage getBufferedImage(String imgUrl) throws IOException {
        if(!Optional.ofNullable(imgUrl).isPresent() || imgUrl.length() <= 0) {
            return null;
        }
        if (imgUrl.toLowerCase().contains("http://") || imgUrl.toLowerCase().contains("https://")) {
            return ImageIO.read(new URL(imgUrl));
        } else {
            return ImageIO.read(new File(imgUrl));
        }
    }

    public static void changeSize(String imgUrl, float scale, String imgDirectoryPath) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        if(bufferedImage != null) {
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            logger.info("<ImageUtils.changeSize> 原图片width为:" + width + ", height为：" + height);
            int newWidth = (int)(width * scale);
            int newHeight = (int)(height * scale);
            logger.info("<ImageUtils.changeSize> 新图片width为:" + newWidth + ", height为：" + newHeight);
            //定义一个BufferedImage对象，用于保存尺寸更改后的位图
            BufferedImage newBufferedImage=new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
            Graphics graphics=newBufferedImage.getGraphics();
            //将原始位图缩小(放大)后绘制到bufferedImage对象中
            graphics.drawImage(bufferedImage,0,0,newWidth,newHeight,null);
            //文件名
            String imgName = imgDirectoryPath + "\\" + UUID.randomUUID().toString() + ".jpg";
            //将bufferedImage对象输出到磁盘上
            ImageIO.write(newBufferedImage,"jpg",new File(imgName));
            logger.info("<ImageUtils.changeSize> 生成图片为：" + imgName);

        }

    }

    private static Integer[] getRGB(String imgUrl, int x, int y) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        Integer[] rgb = new Integer[3];
        int pixel = bufferedImage.getRGB(x, y);
        // 下面三行代码将一个数字转换为RGB数字
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        logger.info("<ImageUtils.getRGB> 图片的RGB为： x=" + x + ",y=" + y + ":(" + rgb[0] + ","
                + rgb[1] + "," + rgb[2] + ")");
        return rgb;
    }

    private static void changeAlpha(String imgUrl, String imgDirectoryPath,int alpha) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        //TYPE_INT_ARGB表示Color为int(4字节),alpha通道位为24-31,红色通道为16-23,绿色为8-15,蓝色为0-7.
        //TYPE_INT_RGB以与TYPE_INT_ARGB相同的方式表示为int(4字节)的颜色,但Alpha通道被忽略(或第24-31位为0).
        BufferedImage newBufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        BufferedImage backImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = backImg.getGraphics();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
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
        String imgName = imgDirectoryPath + "\\" + UUID.randomUUID().toString() + ".jpg";
        ImageIO.write(backImg,"jpg",new File(imgName));
        logger.info("<ImageUtils.changeAlpha> 生成图片为：" + imgName + ", alpha值为：" + alpha);
    }

    private static void tranByPx(String imgUrl, String backImgUrl, String imgDirectoryPath) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        BufferedImage newBufferedImage = getBufferedImage(backImgUrl);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
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
                String imgName = imgDirectoryPath + "\\" + UUID.randomUUID().toString() + ".jpg";
                //将bufferedImage对象输出到磁盘上
                ImageIO.write(newBufferedImage,"jpg",new File(imgName));
                logger.info("<ImageUtils.changeSize> 生成图片为：" + imgName);
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

    private static void drawContentOnImg(String imgUrl, String content, int x , int y, String imgDirectoryPath) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(content, x, y);
        graphics.dispose();
        String imgName = imgDirectoryPath + "\\" + UUID.randomUUID().toString() + ".jpg";
        ImageIO.write(bufferedImage,"jpg",new File(imgName));
        logger.info("<ImageUtils.changeSize> 生成图片为：" + imgName);
    }


    private static void move(String backImgUrl, String imgUrl, int step, String imgDirectoryPath) throws IOException {
        BufferedImage bufferedImage = getBufferedImage(imgUrl);
        BufferedImage backBufferedImage= getBufferedImage(backImgUrl);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage newBufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=newBufferedImage.getGraphics();
        for (int i = 0;i <width; i+=step) {
            graphics.drawImage(backBufferedImage,0,0,width,height,null);
            graphics.drawImage(bufferedImage,i,0,width,height,null);
            //将bufferedImage对象输出到磁盘上
            String imgName = imgDirectoryPath + "\\" + UUID.randomUUID().toString() + ".jpg";
            ImageIO.write(newBufferedImage,"jpg",new File(imgName));
            logger.info("<ImageUtils.changeAlpha> 生成图片为：" + imgName);
        }

    }














}
