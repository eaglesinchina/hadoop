package nio.qq.base2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class FileReceiveYesCli extends BaseMessage {
    //属性:  文件、存储路径、接收的用户ip
    FileMessage file;
    String destLocation;


    @Override
    public int getMsgType() {
        return FILE_ANSWER_YES_CLI;
    }

    public String getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**--->
         *      类型 |文件名长度|  文件名 |file长度|  file           | 存储路径长度 |存储路径
         * 	------------------------------
         * 	    1   |1         |    n   |4      |    n            |1           |n
         */

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        if (file != null) {
            baos.write(file.getFileName().getBytes().length);
            baos.write(file.getFileName().getBytes());

            baos.write(qq.base.util.NumberUtils.int2byte(file.getFileContent().length));
            baos.write(file.getFileContent());
        }

        System.out.println("fileReceiveYesCli--->javabean类：destLocation= "+destLocation);
        baos.write(destLocation.getBytes().length);
        baos.write(destLocation.getBytes());

        return baos.toByteArray();
    }




    public FileMessage getFile() {
        return file;
    }
    public void setFile(FileMessage file) {
        this.file = file;
    }
}
