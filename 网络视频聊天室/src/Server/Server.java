package Server;

import Function.FileTransfer;
import Function.MessageTransfer;
import Function.ReceiveThread;

import Function.VideoTransfer;
import UI.ChatUI;


import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author guochao
 * @date 2021/9/30
 */
public class Server {

    public static void main(String[] args) {
        ServerSocket serverforclient;
        DataInputStream datainput;
        DataOutputStream dataoutput;
        Graphics g;

        //信任证书和自己的证书都放在一个KeyStore中，实际应用中，应该分开
        //设置需要信任的证书库和密码

        System.setProperty("javax.net.ssl.trustStore", "OlChattingPlatform/src/serverStore.ks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        //设置自己的证书库和密码
        System.setProperty("javax.net.ssl.keyStore", "OlChattingPlatform/src/serverStore.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        try {
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
            serverforclient = serverSocketFactory.createServerSocket(9999);

            //serverforclient = new ServerSocket(9999);//server服务器的IP为本机IP
            System.out.println("等待连接");

            Socket Servsocket = serverforclient.accept();//阻塞 监听连接
            datainput = new DataInputStream(Servsocket.getInputStream());
            dataoutput = new DataOutputStream(Servsocket.getOutputStream());

            System.out.println("连接成功");
            ChatUI msgui = new ChatUI(datainput, dataoutput);
            JTextPane showmsgpane = msgui.initUI("服务器");
            g = msgui.g;

            FileTransfer fileTransfer = new FileTransfer(dataoutput, datainput);//文件通信对象
            MessageTransfer messageTransfer = new MessageTransfer(datainput, dataoutput, msgui.ReceivePane, msgui.SendPane, msgui.secret);//文本通信对象
            VideoTransfer videoTransfer = new VideoTransfer(dataoutput, datainput, msgui.webcam);

            new Thread(new ReceiveThread(datainput, showmsgpane, g, fileTransfer, messageTransfer, videoTransfer,msgui)).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


