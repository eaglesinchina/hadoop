package qq.base;

import qq.base.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class ClientChats extends  BaseMessage{
    //属性: 头类型
    public String senderAddr;
    public String mesg;


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

        return CLIENT_CHATS;
    }
    @Override
    public byte[] toBytes() throws IOException {
/**
 *          	        内容	|长度   | 类型
 * 			------------------------------
 * 			|		n		|   4   | 1  |
 */
        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(NumberUtils.int2byte(mesg.getBytes().length));
        baos.write(mesg.getBytes());

        baos.close();
        return baos.toByteArray();
    }


}
