package qq.base2;

import java.io.IOException;

public abstract class BaseMessage {
    //属性: 头类型
    public static final  byte CLIENT_CHATS=0;
    public static final byte CLIENT_CHAT=1;
    public static final byte CLIENT_REFRESHFRIENDS=2;


    public static final byte SERVER_CHATS=3;
    public static final byte SERVER_CHAT=4;
    public static final byte SERVER_REFRESHFRIENDS=5;

    public static final byte FILE=6;
    public static final byte FILE_ANSWER_YES_CLI=7;
    public static final byte FILE_ANSWER_YES_SERVer=8;
    //方法
    public abstract int getMsgType();

    public abstract byte[] toBytes() throws IOException;
}
