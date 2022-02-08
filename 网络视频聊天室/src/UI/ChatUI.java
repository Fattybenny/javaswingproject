package UI;

import Function.FileTransfer;
import Function.MessageTransfer;
import Function.VideoTransfer;
import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.DatagramPacket;

/**
 * @author guochao
 * @date 2021/10/3
 */
public class ChatUI {

    JFrame jFrame = new JFrame();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public static String textrecord = "";//消息记录
    public Graphics g;
    public Webcam webcam = Webcam.getDefault();
    Color background;
    public JTextPane ReceivePane;
    public JTextPane SendPane;
    public JTextPane secret;
    private String strme[] = {"发送文件", "帮助"};
    private String strmi[][] = {{"打开"}, {"关于"}};

    public ChatUI(DataInputStream datainput, DataOutputStream dataoutput) {
        super();
        this.dataInputStream = datainput;
        this.dataOutputStream = dataoutput;
    }

    public JTextPane initUI(String title) {
        jFrame.setTitle(title);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        background = jFrame.getBackground();

        JMenuBar jMenuBar = new JMenuBar();
        jFrame.setJMenuBar(jMenuBar);
        JMenuItem[][] jMenuItems = new JMenuItem[strme.length][];
        JMenu[] jMenu = new JMenu[strme.length];
        for (int i = 0; i < strme.length; i++) {

            jMenu[i] = new JMenu(strme[i]);
            jMenuBar.add(jMenu[i]);

            jMenuItems[i] = new JMenuItem[strmi[i].length];
            for (int j = 0; j < strmi[i].length; j++) {
                jMenuItems[i][j] = new JMenuItem(strmi[i][j]);
                jMenu[i].add(jMenuItems[i][j]);
            }
        }

        //接收显示框
        ReceivePane = new JTextPane();
        ReceivePane.setBackground(Color.WHITE);
        ReceivePane.setFont(new Font("楷体", Font.BOLD, 20));
        JScrollPane jsp = new JScrollPane(ReceivePane);
        jsp.setBounds(0, 0, 800, 300);




        //发送框
        SendPane = new JTextPane();
        SendPane.setBackground(Color.WHITE);
        SendPane.setFont(new Font("楷体", Font.BOLD, 20));
        JScrollPane jsp1 = new JScrollPane(SendPane);
        jsp1.setBounds(0, 350, 800, 100);

        JButton SendText = new JButton("原文发送");
        SendText.setFont(new Font("圆体", Font.BOLD, 20));
        SendText.setBounds(0, 450, 120, 30);

        JButton SendEntext = new JButton("加密发送");
        SendEntext.setFont(new Font("黑体", Font.ITALIC, 20));
        SendEntext.setBackground(Color.MAGENTA);
        SendEntext.setBounds(130, 450, 120, 30);


        JButton Detext = new JButton("解密消息");
        Detext.setFont(new Font("黑体", Font.ITALIC, 20));
        Detext.setBackground(Color.green);
        Detext.setBounds(260, 450, 120, 30);

        JButton ClearRecord = new JButton("清除记录");
        ClearRecord.setFont(new Font("黑体", Font.ITALIC, 20));
        ClearRecord.setBackground(Color.CYAN);
        ClearRecord.setBounds(390, 450, 120, 30);

        JButton Password = new JButton("文本加解密码");
        Password.setBounds(0, 500, 120, 30);
        ClearRecord.setFont(new Font("黑体", Font.ITALIC, 20));

        JButton SendVideo = new JButton("视频聊天");
        SendVideo.setFont(new Font("黑体", Font.ITALIC, 20));
        SendVideo.setBounds(520, 450, 120, 30);
        SendVideo.setBackground(Color.pink);


        JButton CloseVideo = new JButton("关闭视频");
        CloseVideo.setFont(new Font("黑体", Font.ITALIC, 20));
        CloseVideo.setBounds(630, 450, 120, 30);
        CloseVideo.setBackground(Color.ORANGE);

        secret = new JTextPane();//密码输入框
        secret.setBounds(70, 500, 700, 30);
        secret.setFont(new Font("黑体", Font.BOLD, 20));

        jFrame.add(jsp);
        jFrame.add(jsp1);
        jFrame.add(SendText);
        jFrame.add(SendEntext);
        jFrame.add(Detext);
        jFrame.add(ClearRecord);
       jFrame.add(Password);
       jFrame.add(SendVideo);
        //add(CloseVideo);
        jFrame.add(secret);
        jFrame.setVisible(true);
        g = jFrame.getGraphics();

        for (int i = 0; i < jMenuItems.length; i++) {
            for (int j = 0; j < jMenuItems[0].length; j++) {
                if (i == 0 && j == 0)
                    jMenuItems[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {//发送文件
                            String cmd = e.getActionCommand();
                            if (cmd.equals("打开")) {
                                FileDialog diaOpen = new FileDialog(jFrame, "请选择一个纯文本文件（.txt）", FileDialog.LOAD);
                                diaOpen.setVisible(true);
                                String fileDir = diaOpen.getDirectory();
                                String fileName = diaOpen.getFile();
                                if (fileName!=null) {
                                    FileTransfer.SendFile(fileDir, fileName);
                                    MessageTransfer.SendInit("已发送一个文件...");
                                }
                            }
                        }
                    });
            }
        }
        CloseVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (webcam.isOpen()) {
                    webcam.close();
                    g.setColor(background);
                    g.fillRect(850, 10, 300, 300);
                    SendVideo.setEnabled(true);
                }
            }
        });

        SendVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VideoTransfer.SendVideo();
                SendVideo.setEnabled(false);
            }
        });

        //加密算法
        SendEntext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageTransfer.SendEncrpt();
            }
        });

        //解密算法
        Detext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageTransfer.DisCrypt();
            }
        });

        SendText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageTransfer.SendInit();
            }
        });

        ClearRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageTransfer.ClearRecord();
            }
        });
        return ReceivePane;
    }
}
