package Function;

import UI.ChatUI;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author guochao
 * @date 2021/12/26
 */
public class ReceiveThread implements Runnable {

    DataInputStream datainputstream;
    JTextPane showmsgpane;
    Graphics g;
    MessageTransfer messageTransfer;
    FileTransfer fileTransfer;
    VideoTransfer videoTransfer;
    ChatUI msgui;
    public ReceiveThread(DataInputStream datainputstream, JTextPane jTextPane, Graphics g, FileTransfer fileTransfer, MessageTransfer messageTransfer, VideoTransfer videoTransfer, ChatUI msgui) {
        this.datainputstream = datainputstream;
        this.showmsgpane = jTextPane;
        this.g = g;
        this.fileTransfer = fileTransfer;
        this.messageTransfer = messageTransfer;
        this.videoTransfer = videoTransfer;
        this.msgui=msgui;
    }

    @Override
    public void run() {
        while (true) {
            byte type = -1;
            try {
                type = datainputstream.readByte();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (type == 0) {
                MessageTransfer.ReceiveMsg();
            } else if (type == 1) {
                VideoTransfer.ReceiveVideo();
            } else if (type == 2) {
                FileTransfer.ReceiveFile();
            }

        }
    }
}
