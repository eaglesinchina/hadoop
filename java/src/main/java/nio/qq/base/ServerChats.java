package nio.qq.base;

import qq.base.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class ServerChats extends  BaseMessage{
    //属性: 头类型
    private byte[] senderAddr;
    private byte[] mesg;


    public byte[] getReceiveAddr() {
        return senderAddr;
    }

    public void setReceiveAddr(byte[] receiveAddr) {
        this.senderAddr = receiveAddr;
    }

    public byte[] getMesg() {
        return mesg;
    }

    public void setMesg(byte[] mesg) {
        this.mesg = mesg;
    }

    @Override
    public int getMsgType() {

        return SERVER_CHATS;
    }


    @Override
    public byte[] toBytes() throws IOException {
/**
 *              内容	|	内容长度	|send地址	|send地址长度   | 类型
 * 			-----------------------------------------------------------------
 * 				n	|		4       |n		    |   1           | 1  |
 */
        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(senderAddr.length);
        baos.write(senderAddr);
        baos.write(NumberUtils.int2byte(mesg.length));
        baos.write(mesg);

        return baos.toByteArray();
    }
}
