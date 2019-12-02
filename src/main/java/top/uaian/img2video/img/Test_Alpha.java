package top.uaian.img2video.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * description:  <br>
 * date: 2019/11/18 14:48 <br>
 *
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class Test_Alpha {
    public static void main(String[] args) throws IOException {
        String source_img_path = "F:\\project-file\\img2video\\img\\001.jpg";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(source_img_path));
        //转换图片灰度值为黑色
//        convertPicBlack(bufferedImage);
        //转换图片透明度
        convertPicAlpha(bufferedImage);

    }

    private static void convertPicAlpha(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
//        BufferedImage back = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),
//                BufferedImage.TYPE_4BYTE_ABGR);
        String source_img_path = "F:\\project-file\\img2video\\img\\002.jpg";
        BufferedImage back = ImageIO.read(new FileInputStream(source_img_path));
        for (int k = 0; k < 255; k++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = bufferedImage.getRGB(i, j);
                    Color color = new Color(rgb);
                    Color newColor = new Color(color.getRed(),color.getGreen(),color.getBlue(),k);
                    back.setRGB(i,j,newColor.getRGB());
                }
            }
            ImageIO.write(back,"jpg", new File("F:\\project-file\\img2video\\img\\alpha\\alpha" + k + ".jpg"));
        }

    }

    public static void convertPicBlack(BufferedImage bufferedImage) throws IOException {
        //使用原bufferedimage改变alpha无效，故创建一个
        BufferedImage back = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        for (int i= minx;i< width;i ++) {
            for (int j= miny;j< height;j ++) {
                int rgb = bufferedImage.getRGB(i, j);
                Color color = new Color(rgb);
                Color newColor = new Color(color.getRed(),color.getGreen(),color.getBlue(),0);
                back.setRGB(i,j,newColor.getRGB());
            }
        }
        ImageIO.write(back,"jpg",new File("F:\\project-file\\img2video\\img\\alpha_black.jpg"));
    }
}
