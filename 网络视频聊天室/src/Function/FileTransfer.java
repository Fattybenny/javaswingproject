package Function;
import java.io.*;
import java.util.StringTokenizer;

/**
 * @author guochao
 * @date 2021/12/26
 */
public class FileTransfer {

    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    public FileTransfer(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }

    public static void SendFile(String fileDir,String filename) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDir+filename);
            int len = fileInputStream.available();
            byte[] data = new byte[len];
            fileInputStream.read(data);
            fileInputStream.close();
            dataOutputStream.writeByte(2);

            String fName = filename;
            byte[] fNameBytes = fName.getBytes();
            int flen = fNameBytes.length;
            dataOutputStream.writeInt(flen);//文件名字节长度
            dataOutputStream.write(fNameBytes);//文件名字节内容

            int datalen = data.length;
            dataOutputStream.writeInt(datalen);
            dataOutputStream.write(data);
            System.out.println("发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void ReceiveFile() {
        try {
            int fileNameLen = dataInputStream.readInt();//文件名字节长度
            byte[] FN = new byte[fileNameLen];
            dataInputStream.read(FN);//把文件名的字节读进来
            String Fname = new String(FN);

            int FLen = dataInputStream.readInt();//文件的内容字节长度
            byte[] data = new byte[FLen];
            dataInputStream.read(data);//读进所有文件内容



            //将文件保存磁盘
            FileOutputStream fileOutputStream = new FileOutputStream(Fname);
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
