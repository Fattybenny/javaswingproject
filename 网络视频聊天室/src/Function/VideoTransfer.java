package Function;

import com.github.sarxos.webcam.Webcam;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author guochao
 * @date 2021/11/9
 */
public class VideoTransfer {
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    static Webcam webcam;

    static JFrame VideoFrame = new JFrame();
    static  Graphics g;

    public VideoTransfer(DataOutputStream dataoutput, DataInputStream dataInputStream, Webcam webcam) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataoutput;
        this.webcam = webcam;

    }

    /**
     * 发送视频
     */
    public static void SendVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webcam.open();
                VideoFrame.setVisible(true);
                g=VideoFrame.getGraphics();
                while (true) {
                    Image image = webcam.getImage();////摄像头获取的图片默认宽320高180
                    BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
                    Graphics bg = bufferedImage.getGraphics();
                    bg.drawImage(image, 0, 0, 300, 300, null);
                    g.drawImage(image, 0, 0, 300, 300, null);
                    SendImage(bufferedImage);
                }
            }
        }).start();
    }

    public static void SendImage(BufferedImage bufferedImage) {
        if (bufferedImage == null) return;
        //先获得二维整型rgb数组
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int[][] imagePixel = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                imagePixel[i][j] = bufferedImage.getRGB(i, j);
            }
        }
        byte[] rgbbytes = intarrToBytearr(imagePixel);
        try {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeInt(w);
            dataOutputStream.writeInt(h);
            dataOutputStream.write(rgbbytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] intarrToBytearr(int[][] intarr) {
        int w = intarr.length;
        int h = intarr[0].length;
        int k = 0;
        //将二维数组转一byte
        byte[] bytearray = new byte[w * h * 4];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bytearray[k++] = (byte) ((intarr[i][j] >> 0) & 0xFF);
                bytearray[k++] = (byte) ((intarr[i][j] >> 8) & 0xFF);
                bytearray[k++] = (byte) ((intarr[i][j] >> 16) & 0xFF);
                bytearray[k++] = (byte) ((intarr[i][j] >> 24) & 0xFF);
            }
        }
        return bytearray;
    }

    /**
     * 接收视频
     */
    public static void ReceiveVideo() {
        int w = 0;
        int h = 0;
        try {
            w = dataInputStream.readInt();
            h = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] getbytes = new byte[w * h * 4];

        try {
            dataInputStream.readFully(getbytes);//一次性将byte[]
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] array = bytearrToIntarr(getbytes);
        //存放rgb的二维数组
        int[][] imagePixel = new int[w][h];
        int k = 0;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb = array[k++];
                imagePixel[i][j] = rgb;
            }
        }
        BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bufferedImage.setRGB(i, j, imagePixel[i][j]);
            }
        }

        // g.drawImage(bufferedImage, 850, 300, 300, 300, null);
        VideoFrame(bufferedImage);
    }


    public static void VideoFrame(BufferedImage bufferedImage) {
        VideoFrame.setTitle("视频窗口");
        VideoFrame.setSize(600, 300);
        VideoFrame.setLayout(null);
        VideoFrame.setDefaultCloseOperation(3);
        VideoFrame.setVisible(true);
        g=VideoFrame.getGraphics();
        g.drawImage(bufferedImage, 300, 0, 300, 300, null);

    }

    // 图片的一维字节数组转二维整数数组，先将一维字节数组转一维整数数组
    public static int[] bytearrToIntarr(byte[] bytes) {
        int length = bytes.length;
        System.out.println("length" + length);
        int arraylength = length / 4;
        System.out.println("arraylength" + arraylength);
        int[] arr = new int[arraylength];

        int k = 0;
        for (int i = 0; i < bytes.length - 3; i = i + 4) {
            byte[] curbyte = new byte[4];
            curbyte[0] = bytes[i];
            curbyte[1] = bytes[i + 1];
            curbyte[2] = bytes[i + 2];
            curbyte[3] = bytes[i + 3];
            arr[k++] = ByteToInt(curbyte);
        }
        return arr;
    }

    //4字节数组转整数
    public static int ByteToInt(byte[] byteInt) {
        //从高位开始读
        int x = ((byteInt[0] & 0xFF) << 0) |
                ((byteInt[1] & 0xFF) << 8) |
                ((byteInt[2] & 0xFF) << 16) |
                ((byteInt[3] & 0xFF) << 24);
        return x;
    }
}

