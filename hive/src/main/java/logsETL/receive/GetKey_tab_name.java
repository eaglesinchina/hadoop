package logsETL.receive;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 从数据库获取表名： ---》表的所有字段
 *
 */
public class GetKey_tab_name {

    //初始化： 属性
    static  String url, username , pwd ;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         url="jdbc:mysql://master:3306/mysql2HDFS";
         username="root";
         pwd="daitoue";
    }


    /**
     * 获取： 一个表-----》所有字段
     * @param tbname
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<String> getTbNameProp(String tbname) throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection(url, username, pwd);
        //创建stam对象
        String sql="select * from table_shadow where tablename=?";
        PreparedStatement prestam = conn.prepareStatement(sql);

        prestam.setString(1, tbname);

        ResultSet resultSet = prestam.executeQuery();

        ArrayList<String> props = new ArrayList<>();
        //遍历结果
        while (resultSet.next()){
            int len = resultSet.getMetaData().getColumnCount();
            //取出所有值----->表的字段
            for(int i=2;i<=len;i++){
                String colum = resultSet.getString(i);
                if (null !=colum  ){

                    props.add(colum);
//                    System.out.println(colum);
                }

            }
        }

        return props;
    }

    /**
     * 获取： 所有表名
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getTbname() throws Exception{
        Connection conn = DriverManager.getConnection(url, username, pwd);
        //创建stam对象
        String sql="select tablename from table_shadow ";
        PreparedStatement prestam = conn.prepareStatement(sql);

        ResultSet resultSet = prestam.executeQuery();
        //取出结果
       // HashMap<String, List<String>> map = new HashMap<>();
        ArrayList<String> tbs = new ArrayList<>();

        while (resultSet.next()) {
            String tbname = resultSet.getString(1);
            tbs.add(tbname);
//            System.out.println("表名--->"+tbname);
        }

        return tbs;
    }

    /**
     * 获取所【表记录】：  表名， 所有字段
     * @throws Exception
     */

    public static HashMap<String, List<String>> getMap_tbname_prop( ) throws Exception{
        HashMap<String, List<String>> map = new HashMap<>();

        //表名
        ArrayList<String> tbnames = getTbname();

        //表： 所有字段
        for (String tbname : tbnames) {

            ArrayList<String> props = getTbNameProp(tbname);

            //组合map
            map.put(tbname,props);
            System.out.println(tbname+""+props);
        }

        return map;
    }
    //===========
    public static void main(String[] args) throws Exception {
//        getTbNameProp("appErrorLogs");
//        getTbname();
        getMap_tbname_prop2("appBaseLog");//"appStartupLogs");
    }

    public static List<String> getMap_tbname_prop2(String tableName ) throws Exception{

        //表名
        ArrayList<String> tbnames = getTbname();

        //表： 所有字段
        for (String tbname : tbnames) {

            if(tbname.equalsIgnoreCase(tableName)) {
                ArrayList<String> props = getTbNameProp(tbname);
                System.out.println(props);
                return props;
            }
        }

        return null;
    }


}
