package qq.base2;

import qq.base2.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * to---mesg
 */

public class FileReceiveYesServer extends BaseMessage {
    //属性:  文件、存储路径、接收的用户ip
    FileMessage file;
    String destLocation;
    String receiverIp;


    @Override
    public int getMsgType() {
        return BaseMessage.FILE_ANSWER_YES_SERVer;
    }

    public String getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }

    public String getReceiverIp() {
        return receiverIp;
    }

    public void setReceiverIp(String receiverIp) {
        this.receiverIp = receiverIp;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**--->
         *      类型 |文件名长度|  文件名 |file长度|  file           | 存储路径长度 |存储路径 | 接收者Ip长度 |接收者Ip
         * 	------------------------------
         * 	    1   |1         |    n   |4      |    n            |1           |n        |1           |n
         */

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(file.getFileName().getBytes().length);
        baos.write(file.getFileName().getBytes());

        baos.write(qq.base.util.NumberUtils.int2byte(file.getFileContent().length));
        baos.write(file.getFileContent());

        System.out.println("fileReceiveYes--->javabean类：destLocation= " + destLocation);
        baos.write(destLocation.getBytes().length);
        baos.write(destLocation.getBytes());

        baos.write(receiverIp.getBytes().length);
        baos.write(receiverIp.getBytes());
        baos.close();

        return baos.toByteArray();
    }


    public FileMessage getFile() {
        return file;
    }

    public void setFile(FileMessage file) {
        this.file = file;
    }
}
