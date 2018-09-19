package qq.base.util;

public class Constants {

    //定义常量

    //poolsize
    private static String poolSizeName="qq.server.threadpool.cores";
    public  static int poolSizeValue=PropUtils.getIntVal(poolSizeName);

    //socket通信
    private static String serverBlookMode="qq.server.block.mode";
    public static String serverBlookModeValue=PropUtils.getStringVal(serverBlookMode);

    private static String serverIP="qq.server.ip";
    public static String serverIPValue=PropUtils.getStringVal(serverIP);
    private static String serverPort="qq.server.port";
    public static int serverPortValue=PropUtils.getIntVal(serverPort);


    //clie: qq.cli.ip, port
    private static String cliIP="qq.cli.ip";
    public static String cliIPValue=PropUtils.getStringVal(cliIP);
    private static String cliPort="qq.cli.port";
    public static int cliPortValue=PropUtils.getIntVal(cliPort);

    public static void main(String[] args) {
        System.out.println(poolSizeValue);
    }

}
