package nio.qq.base;

import qq.base.util.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * to---mesg
 */

public class ServerFreshFriends extends  BaseMessage{
    private  byte[] friends;

    public byte[] getFriends() {
        return friends;
    }
    public void setFriends(byte[] friends) {
        this.friends = friends;
    }

    @Override
    public int getMsgType() {

        return SERVER_REFRESHFRIENDS;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**
         *   		            内容	|长度   | 类型
         * 			------------------------------
         * 			|		n		|   4   | 1  |
         */
        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getMsgType());
        baos.write(NumberUtils.int2byte(friends.length));
        baos.write(friends);
        baos.close();

        System.out.println("server: tobytes==>len:"+friends.length);
        return baos.toByteArray();
    }

}
