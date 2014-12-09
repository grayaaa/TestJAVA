package asiainfo;

import common.Logger;

import java.io.*;

/**
 * Created by Geng on 2014/12/9.
 */
public class Test {

    private static Logger LOG = Logger.getLogger(Test.class);

    public void dealFiles(String dir) {
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getName();
            System.out.println(fileName.split("\\.")[fileName.split("\\.").length-1]);
        }

    }

    public static void main(String[] args) {

    }
}
