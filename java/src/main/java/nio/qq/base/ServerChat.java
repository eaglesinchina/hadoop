package nio.qq.base;

import qq.base.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class ServerChat extends  BaseMessage{

    private byte[] reveiveAddr;
    private byte[] senderAddr;
    private byte[] mesg;

    public byte[] getMesg() {
        return mesg;
    }
    public void setMesg(byte[] mesg) {
        this.mesg = mesg;
    }
    public byte[] getReveiveAddr() {
        return reveiveAddr;
    }
    public void setReveiveAddr(byte[] reveiveAddr) {
        this.reveiveAddr = reveiveAddr;
    }

    public byte[] getSenderAddr() {
        return senderAddr;
    }

    public void setSenderAddr(byte[] senderAddr) {
        this.senderAddr = senderAddr;
    }

    @Override
    public int getMsgType() {

        return SERVER_CHAT;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**
         *              内容	|   	内容长度	|receive地址	|receive地址长度 |send地址	|send地址长度   | 类型
         * 			-----------------------------------------------------------------
         * 				n	|		4       |n		    |   1          |n		    |   1           | 1  |
         */
        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(senderAddr.length);
        baos.write(senderAddr);

        baos.write(reveiveAddr.length);
        baos.write(reveiveAddr);

        baos.write(NumberUtils.int2byte(mesg.length));
        baos.write(mesg);

        return baos.toByteArray();
    }

}
