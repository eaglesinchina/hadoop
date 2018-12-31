package nio.qq.base2;

import java.io.IOException;
import java.util.List;

/**
 * to---mesg
 */

public class ClientFreshFriends extends BaseMessage {
    //属性: 头类型
    private List<String> friends;


    @Override
    public int getMsgType() {

        return CLIENT_REFRESHFRIENDS;
    }

    @Override
    public byte[] toBytes() throws IOException {
        /**
         *    | 类型
         * 	------------------------------
         * 	1  |
         */

        return new byte[]{(byte) getMsgType()};
    }

    public List<String> getFriends() {
        return friends;
    }
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
