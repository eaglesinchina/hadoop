package qq.base2;

/**
 * cli:
 * 内容	|内容长度 |文件名|文件名长度   | 类型
 * ------------------------------
 * n  |   4  	 |	n  |        1   | 1  |
 */

import qq.base.util.NumberUtils;

import java.io.*;

/**
 * ser:
 * 内容	|  内容长度	|文件名|文件名长度    |send地址	|send地址长度   | 类型
 * -----------------------------------------------------------------
 * n	|	4       |	n  |        1   |n		    |   1           | 1  |
 */
public class FileMessage extends BaseMessage {
    //属性
    private String fileName;
    private String senderAddr;
    private byte[] fileContent;


    @Override
    public int getMsgType() {
        return BaseMessage.FILE;
    }

    @Override
    public byte[] toBytes() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        if (senderAddr!=null) {
            baos.write(senderAddr.getBytes().length);
            baos.write(senderAddr.getBytes());
        }

        baos.write(fileName.getBytes().length);
        baos.write(fileName.getBytes());

        baos.write(NumberUtils.int2byte(fileContent.length));
        baos.write(fileContent);
        baos.close();

        return baos.toByteArray();
    }


    //set,get
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public String getSenderAddr() {
        return senderAddr;
    }
    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public void setFileContent(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        BufferedInputStream bufin = new BufferedInputStream(in);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = bufin.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }

        //关闭资源
        baos.close();
        bufin.close();
        in.close();

        this.fileContent = baos.toByteArray();
    }
}
