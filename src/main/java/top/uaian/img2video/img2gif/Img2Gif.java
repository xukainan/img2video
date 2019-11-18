package top.uaian.img2video.img2gif;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

public class Img2Gif {

    public static void main(String[] args){
        String imgPath = "F:\\project-file\\img2video\\img\\change2\\";
        String gifPath = "F:\\project-file\\img2video\\img\\change2\\test.gif";
        Img2Gif.toGif(imgPath,gifPath);
    }

    public static void toGif(String imgPath,String gifPath){
        int fileSum = new File(imgPath).list().length;
        File[] files = new File(imgPath).listFiles();
        Arrays.sort(files,
                (o1, o2) -> Integer.valueOf(o1.getName().replace(".jpg","")) - Integer.valueOf(o2.getName().replace(".jpg","")));
        System.out.println("共有 " + fileSum + " 个文件转换成gif");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage[] bufferedImage = new BufferedImage[fileSum];
        //渲染

        try {
            AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
            animatedGifEncoder.setRepeat(0);
            animatedGifEncoder.start(byteArrayOutputStream);
            for (int i = 0; i < fileSum; i++) {
                //延迟
                animatedGifEncoder.setDelay(100);
                bufferedImage[i] = ImageIO.read(files[i]);
                animatedGifEncoder.addFrame(bufferedImage[i]);
            }
            animatedGifEncoder.finish();
            //输出
            File gifFile = new File(gifPath);
            FileOutputStream fileOutputStream = new FileOutputStream(gifFile);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
