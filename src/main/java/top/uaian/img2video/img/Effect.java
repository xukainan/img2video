package top.uaian.img2video.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * description:  <br>
 * date: 2019/12/2 10:39 <br>
 *
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class Effect {
    static BufferedImage img1;
    static BufferedImage img2;
    static int width1;
    static int width2;
    static int height1;
    static int height2;
    static BufferedImage bufferedImage;

    static {
        try {
            img1 = ImageIO.read(new FileInputStream("F:\\project-file\\img2video\\img\\001.jpg"));
            img2 = ImageIO.read(new FileInputStream("F:\\project-file\\img2video\\img\\002.jpg"));
            width1 = img1.getWidth();
            width2 = img2.getWidth();
            height1 = img1.getHeight();
            height2 = img2.getHeight();
            bufferedImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        //移动效果
//        move();
        //放大缩小
//        zoom();
        //渐变
        trans();
        //气泡
        //曲线
    }


    static void move() throws IOException {
        Graphics graphics = bufferedImage.getGraphics();
        for (int i = 0; i < width1; i += 4) {
            graphics.drawImage(img1, 0, 0, width1, height1, null);
            graphics.drawImage(img2, i, 0, width2, height2, null);
            ImageIO.write(bufferedImage, "jpg", new File("F:\\project-file\\img2video\\img\\effect\\move\\" + i + ".jpg"));
        }
    }


    private static void zoom() throws IOException {
        Graphics graphics = bufferedImage.getGraphics();
        double zoomRatio = 1.25d;
        int i = 0;
        while (true) {
            int newWidth = (int) (width1 * zoomRatio);
            int newHeight = (int) (height1 * zoomRatio);
            graphics.drawImage(img1, 0, 0, newWidth, newHeight, null);
            ImageIO.write(bufferedImage, "jpg", new File("F:\\project-file\\img2video\\img\\effect\\zoom\\" + i++ +
                    ".jpg"));
            zoomRatio -= 0.02;
            if (zoomRatio < 1.00) {
                break;
            }
        }
    }

    private static void trans() throws IOException {
        for (int alphaA = 0; alphaA < 255; alphaA += 5) {
            int alphaB = 255 - alphaA;
            Graphics graphics = bufferedImage.getGraphics();
            BufferedImage imgA = img_alpha(img1, alphaA);
            BufferedImage imgB = img_alpha(img2, alphaB);
            graphics.drawImage(imgA, 0, 0, null);
            graphics.drawImage(imgB, 0, 0, null);
            ImageIO.write(bufferedImage, "jpg", new File("F:\\project-file\\img2video\\img\\effect\\trans\\" + alphaA +
                    ".jpg"));
        }
    }

    private static BufferedImage img_alpha(BufferedImage imgsrc, int alpha) {
        try {
            //创建一个包含透明度的图片,半透明效果必须要存储为png合适才行，存储为jpg，底色为黑色
            BufferedImage back = new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int width = imgsrc.getWidth();
            int height = imgsrc.getHeight();
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int rgb = imgsrc.getRGB(i, j);
                    Color color = new Color(rgb);
                    Color newcolor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                    back.setRGB(i, j, newcolor.getRGB());
                }
            }
            return back;
        } catch (Exception e) {
            return null;
        }


    }
}
