package nio.qq.base;

import qq.base.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class ClientChat extends  BaseMessage{
    //属性: 头类型
    public String receiveAddr;
    public String senderAddr;
    public String mesg;


    public String getReceiveAddr() {
        return receiveAddr;
    }
    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }
    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr;
    }
    public String getMesg() {
        return mesg;
    }
    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public String getSenderAddr() {
        return senderAddr;
    }

    @Override
    public int getMsgType() {

        return CLIENT_CHAT;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**
         *          	        内容	|长度   |接收方地址|接收方地址长度| 类型
         * 			------------------------------
         * 			|		n		|   4   |n 字节   | 1字节      | 1  |
         */
        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(this.getReceiveAddr().getBytes().length);
        baos.write(this.getReceiveAddr().getBytes());

        baos.write(NumberUtils.int2byte(this.getMesg().getBytes().length));
        baos.write(this.getMesg().getBytes());

        baos.close();
        return baos.toByteArray();
    }
}
