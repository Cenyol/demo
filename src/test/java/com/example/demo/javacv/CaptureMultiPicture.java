package com.example.demo.javacv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2019/1/22 16:54
 */
public class CaptureMultiPicture {

    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        startCapture("/Users/cenyol/tmp", 20);
        System.out.println(System.currentTimeMillis() - time);
    }

    /**
     * 扫描指定目录下的视频文件们
     * 截取指定数量的图片放到当前目录下与视频文件同名的文件夹中
     * @param path
     */
    private static void startCapture(String path, int pictureCount) {
        File[] children = new File(path).listFiles();
        if (children != null) {
            for (File child : children) {
                String suffix = ".mp4";
                if (child.getName().endsWith(suffix)) {
                    String directoryPath = child.getAbsolutePath().substring(0, child.getAbsolutePath().lastIndexOf(suffix));
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdir();
                        try {
                            randomGrabberFFmpegImage(child.getAbsolutePath(), directoryPath, pictureCount);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理单个视频文件
     * @param filePath
     * @param targetFilePath
     * @param pictureCount
     * @throws Exception
     */
    private static void randomGrabberFFmpegImage(String filePath, String targetFilePath, int pictureCount) throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();

        int ffLength = ff.getLengthInFrames();
        List<Integer> randomGrab = getFramePositionList(ffLength, pictureCount);
        int maxRandomGrab = randomGrab.get(randomGrab.size() - 1);
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            if (randomGrab.contains(i)) {
                doExecuteFrame(f, targetFilePath, i);
            }
            if (i >= maxRandomGrab) {
                break;
            }
            i++;
        }
        ff.stop();
    }

    public static void doExecuteFrame(Frame f, String targetFilePath, int index) {
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();

        String imageMat = "jpg";
        String FileName = targetFilePath + File.separator + index + "." + imageMat;
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(FileName);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getFramePositionList(int baseNum, int length) {
        List<Integer> list = new ArrayList<>(length);
        int step = baseNum / length;

        int i = step;
        while (i < baseNum) {
            list.add(i);
            i += step;
        }
        return list;
    }
}
