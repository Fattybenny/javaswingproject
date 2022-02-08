package Client;

import Function.FileTransfer;
import Function.MessageTransfer;
import Function.ReceiveThread;
import Function.VideoTransfer;
import UI.ChatUI;


import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * @author guochao
 * @date 2021/9/30
 */
public class Client {

    public static void main(String[] args) {
        Socket socket;
        DataInputStream datainput;
        DataOutputStream dataoutput;
        Graphics g;

        //信任证书和自己的证书都放在一个KeyStore中，实际应用中，应该分开
        //设置需要信任的证书库和密码
        System.setProperty("javax.net.ssl.trustStore", "OlChattingPlatform/src/clientStore.ks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        //设置自己的证书库和密码
        System.setProperty("javax.net.ssl.keyStore", "OlChattingPlatform/src/clientStore.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        try {
            SocketFactory socketFactory = SSLSocketFactory.getDefault();
            //socket = socketFactory.createSocket("127.0.0.1", 9999);

            socket=socketFactory.createSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);//目的服务器地址和端口

            System.out.println("连接中");
            socket.connect(inetSocketAddress);
            System.out.println("连接成功");
            //socket = new Socket("127.0.0.1", 9999);//形参为目的IP
            datainput = new DataInputStream(socket.getInputStream());
            dataoutput = new DataOutputStream(socket.getOutputStream());

            ChatUI msgui = new ChatUI(datainput, dataoutput);
            JTextPane showmsgpane = msgui.initUI("客户端");
            g = msgui.g;

            //三个通信功能
            FileTransfer fileTransfer = new FileTransfer(dataoutput, datainput);//文件通信对象
            MessageTransfer messageTransfer = new MessageTransfer(datainput, dataoutput, msgui.ReceivePane, msgui.SendPane, msgui.secret);//文本通信对象
            VideoTransfer videoTransfer = new VideoTransfer(dataoutput, datainput, msgui.webcam);

            new Thread(new ReceiveThread(datainput, showmsgpane, g, fileTransfer, messageTransfer, videoTransfer,msgui)).start();
            } catch (Exception e) {
            System.err.println("会话建立失败,一秒后再次建立");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException E) {
            }
        }
    }
}