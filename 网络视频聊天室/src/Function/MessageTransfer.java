package Function;

import UI.ChatUI;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalTime;

/**
 * @author guochao
 * @date 2021/11/9
 */
public class MessageTransfer {

    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static  JTextPane jtp;//接收框
    static JTextPane jtp1;//发送框
    static  JTextPane secret;
    public static String textrecord = "";//消息记录
    public static String lastword = "";//上条信息

    public MessageTransfer( DataInputStream dataInputStream,DataOutputStream dataOutputStream, JTextPane jtp, JTextPane jtp1, JTextPane secret) {

        this.dataInputStream=dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.jtp = jtp;
        this.jtp1 = jtp1;
        this.secret = secret;
    }

    public static  void SendInit(String context){//发送原文
        textrecord = jtp.getText();//原先文本框内容
        String msg = context;
        byte[] msgbyte = msg.getBytes();
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(msgbyte.length);
            dataOutputStream.write(msgbyte);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jtp.setFont(new Font("楷体", Font.BOLD, 20));
        jtp.setText(textrecord + "\t\t\t\t\t\t\t\t我：" + msg + " " + LocalTime.now() + "\n"+"\n");


    }


    public static  void SendInit(){//发送原文
            textrecord = jtp.getText();//原先文本框内容

            String msg = jtp1.getText();//输入框内容
            byte[] msgbyte = msg.getBytes();
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(msgbyte.length);
            dataOutputStream.write(msgbyte);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
           jtp.setFont(new Font("楷体", Font.BOLD, 20));
            jtp.setText(textrecord + "\t\t\t\t\t\t\t\t我：" + msg + " " + LocalTime.now() + "\n"+"\n");

    }

    public static void SendEncrpt(){//发送密文
        textrecord = jtp.getText();//原先文本框内容
            String msg = jtp1.getText();
            String sec = secret.getText();
            char[] setchar = msg.toCharArray();
            char[] secretchar = sec.toCharArray();
            int j = 0;
            for (int i = 0; i < setchar.length; i++) {
                if (i >= secretchar.length) {
                    j = 0;
                } else j = i;
                setchar[i] = (char) ((int) setchar[i] + (int) secretchar[j]);
            }
            String newstring = new String(setchar);
            byte[] msgbyte = newstring.getBytes();
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(msgbyte.length);
            dataOutputStream.write(msgbyte);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jtp.setFont(new Font("楷体", Font.BOLD, 20));
        jtp.setText(textrecord + "\t\t\t\t\t\t\t\t我：" + msg + " " + LocalTime.now() + "\n"+"\n");

    }

    public static void DisCrypt(){//解密
        textrecord = jtp.getText();//原先文本框内容
        if (lastword != "") {
            char[] rechar = lastword.toCharArray();
            String sec = secret.getText();
            char[] secretchar = sec.toCharArray();
            int j = 0;
            for (int i = 0; i < rechar.length; i++) {
                if (i >= secretchar.length) {
                    j = 0;
                } else j = i;
                rechar[i] = (char) ((int) rechar[i] - (int) secretchar[j]);
            }
            String getmsg = new String(rechar);
            jtp.setFont(new Font("楷体", Font.BOLD, 20));
            jtp.setText(textrecord + "解密——：" + getmsg + "\n"+"\n");

        }

    }


    public static void ClearRecord(){//清除消息记录
        textrecord = "";
        jtp.setFont(new Font("楷体", Font.BOLD, 20));
        jtp.setText(textrecord);

    }

    public static void ReceiveMsg(){
        try {
            int msglength = 0;
            msglength = dataInputStream.readInt();
            System.out.println("消息长度是" + msglength);
            byte[] msgbytes = new byte[msglength];
            dataInputStream.read(msgbytes);
            String getmsg = new String(msgbytes);
            System.out.println("对方说" + getmsg);
            ChatUI.textrecord = jtp.getText();
            jtp.setFont(new Font("楷体", Font.BOLD, 20));
            jtp.setText(ChatUI.textrecord + "对方：" + getmsg + " " + LocalTime.now() + "\n"+"\n");

            lastword = getmsg;
        } catch(IOException e){
            e.printStackTrace();
        }
    }


}
