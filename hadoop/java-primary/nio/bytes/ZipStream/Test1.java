package bytes.ZipStream;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Test1 {
    void iterFile(String strPath){
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                iterFile(files[i].getAbsolutePath());

            } else {
                System.out.println((files[i].getParentFile().getName()+"/"+files[i].getName()));
            }
        }
    }
    @Test
    public void test3() throws Exception {
        String dir="/home/centos/zip-src";
        iterFile(dir);
        
        String dest="/home/centos/zip-dest";
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dest));
        zout.putNextEntry(new ZipEntry("aaa"));
    }

    @Test
    public void test2() throws Exception {
        String dir="/home/centos/zip-src";
        String dest="/home/centos/zip-dest";

        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dest));
        zout.putNextEntry(new ZipEntry("aaa"));

        //io: 拷贝
        byte[] buf=new byte[1024];
        int len=0;

        FileInputStream fin = new FileInputStream(dir + "/a.txt");
        while ((len=fin.read(buf))!=-1){
            zout.write(buf,0,len);
        }
        fin.close();
        zout.close();

    }
    public static void main(String[] args) throws Exception {


        /**
         * 文件压缩, 解压
         */
        String dir="/home/centos/zip-src";
        String dest="/home/centos/zip-dest";

//        ZipInputStream zin = new ZipInputStream(new FileInputStream(dir));
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dest));

        zip(dir,zout);

    }


    public static  void zip(String dir, ZipOutputStream zout  ) throws Exception {
        //遍历文件夹
        File folder = new File(dir);
        File[] fs = folder.listFiles();

        byte[] buf=new byte[1024];
        int len=0;


        if (fs==null)return;
        for (File f : fs) {
            if (f==null) continue;
            if(f.isFile()){
                FileInputStream fin = new FileInputStream(f);
                while ((len=fin.read(buf))!=-1){

                    zout.write(buf,0,len);
                }
                //关闭流
                fin.close();
                zout.flush();

            }else {
                String dirname=f.getName();

                zout.putNextEntry( new ZipEntry(dirname));

                zip(dirname,zout);
            }
        }
        //关闭流
        zout.flush();
    }
}
