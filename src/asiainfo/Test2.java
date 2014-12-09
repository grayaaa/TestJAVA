package asiainfo;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 1.选择被复制的源目录：sourcePath
 * 2.选择文件被复制后存放的目标目录：targetPath
 * 3.文件后缀名分类文件，并且将文件复制到不同的文件夹
 */
public class Test2 {
    /**
     * 启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 选择被复制的源目录：sourcePath
         */
        File sourcePath = getSourcePath();

        /**
         * 选择文件被复制后存放的目标目录：targetPath
         */
        File outputPath = getOutputPath();

        /**
         * 执行复制操作
         */
        handlePath(sourcePath, outputPath);

        handleTxt(outputPath, "txt");
    }

    /**
     * 处理txt文件
     *
     * @param sourcePath
     * @param suffix
     */
    private static void handleTxt(File sourcePath, String suffix) {

        File num = new File(sourcePath, "num");
        File abc = new File(sourcePath, "abc");
        File chars = new File(sourcePath, "char");

        try {
            for (File file : new File(sourcePath, suffix).listFiles()) {
                List<String> readLines = Files.readLines(file, Charsets.UTF_8);
                for (String str : readLines) {
                    for (int i = 0; i < str.length(); i++) {
                        char c = str.charAt(i);
                        if (c >= '0' && c <= '9') {
                            Files.append(String.valueOf(c), num, Charsets.UTF_8);
                        } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                            Files.append(String.valueOf(c), abc, Charsets.UTF_8);
                        } else {
                            Files.append(String.valueOf(c), chars, Charsets.UTF_8);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理文件分类复制
     *
     * @param sourcePath
     * @param outputPath
     */
    private static void handlePath(File sourcePath, File outputPath) {
        if (sourcePath == null || outputPath == null) //1.如果没有选择源目录或目标目录，则返回
            return;
        for (File file : sourcePath.listFiles()) {  //2.取得源目录下所有的文件或文件夹，并进行遍历
            if (file.isDirectory()) {
                //3.如果当前遍历的是文件夹，则使用递归调用，继续处理该文件夹下面的所有文件或文件夹
                handlePath(file, outputPath);
            } else {
                //4.如果当前遍历的是不是文件夹而是文件，那么直接开始copy的操作

                //4.1.取得文件的文件名，包含文件后缀名
                String fileName = file.getName();

                if (fileName.contains(".")) {
                    //4.2.1.如果该文件有后缀名，即包含“.”点符号，则取得文件的后缀名为：最后一个点符号后面的字符串
                    String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
                    //4.2.2.根据文件最后的输出的目标目录和文件的后缀名，执行copy的操作（因为最后输出格式为按文件的后缀名分类，即最后输出如：pdf目录下面有pdf文件，txt目录下面有txt文件）
                    copy(file, new File(outputPath, suffix));
                } else {
                    //4.3.如果该文件没有后缀名，即不包含“.”点符号，则直接在文件最后的输出的目标目录下建立“nosuffix”目录，执行copy的操作，将没有后缀名的文件复制到nosuffix目录下
                    copy(file, new File(outputPath, "nosuffix"));
                }
            }
        }
    }

    /**
     * 单个文件复制操作
     *
     * @param sourceFile
     * @param targetDir
     */
    private static void copy(File sourceFile, File targetDir) {
        System.out.println("copying " + sourceFile);
        //1.如果目标目录不存在，则新建，此处的目标目录为按文件后缀名命名的目录，如pdf目录，txt目录以及nosuffix目录
        if (!targetDir.exists()) {
            targetDir.mkdir();//新建目录
        }
        try {
            Files.copy(sourceFile, new File(targetDir, sourceFile.getName()));
//            //2.将源文件读取到输入流中
//            FileInputStream fis = new FileInputStream(sourceFile);
//
//            //3.按照目标目录和文件名构建文件输出流
//            FileOutputStream fos = new FileOutputStream(new File(targetDir, sourceFile.getName()));
//
//            //4.定义缓冲区，大小为102400byte
//            byte[] buf = new byte[102400];
//
//            //5.定义输入流读取到的文件大小，下面会用到
//            int available = 0;
//
//            //6.fis.available()：使用输入流fis读取当前文件的大小，将读取到的大小赋值给available
//            while ((available = fis.available()) > buf.length) {
//                //7.如果当前读取到的大小available大于定义的缓冲区的大小102400，则用fis.read(buf)方法读取文件。
//                //此时，缓冲区buf会被完全读满，即源文件的前102400 byte的数据都会被读取到buf中，
//                //fis下一次读取就会从102401 byte开始读，即文件读取的开始位置移动到了available+1的位置上
//                fis.read(buf);
//
//                //8.使用文件输入流fos将缓冲区buf中的内容写入到目标文件中
//                fos.write(buf);
//            }
//
//            //9.上面的循环始终会结束，因为文件的大小不可能无限的大，当上面循环的判断使用fis.available()读取到当前剩余的文件大小小于102400 byte时，
//            //使用fis.read(buf, 0, available)将剩余的文件数据读取的buf对应的位置，
//            //例如，最后剩下的文件数据大小为100 byte，即available的值为100，那么使用执行方法fis.read(buf, 0, available)，后，
//            //buf数组中的buf[0]到buf[99]会有数据，buf[100]之后的就都是空了。
//            fis.read(buf, 0, available);
//
//            //10.调用输出流fos.write(buf, 0, available)表示将buf中的前available大小的数据输出，
//            //例如上面举得例子，就只会将buf中的前100位数据输出。
//            fos.write(buf, 0, available);
//            fis.close();
//            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 弹出文件选择对话框，选择要被复制的源目录
     *
     * @return
     */
    private static File getSourcePath() {
        //1.弹出文件选择对话框
        JFileChooser chooser = new JFileChooser();

        //2.设置只能选择目录
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //3.返回选择的目录，如果没有选择则返回null
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    /**
     * 弹出对话框，选择文件复制后存放的目标目录
     *
     * @return
     */
    private static File getOutputPath() {
        //1.弹出文件选择对话框
        JFileChooser chooser = new JFileChooser();

        //2.设置只能选择目录
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //3.返回选择的目录，如果没有选择则返回null
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
