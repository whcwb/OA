package com.ldz.util.commonUtil;

import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * @since 2017/11/1
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
    	fileExistsDir(filePath);
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
    
    /**
     * 判断目录是否存在，不在就创建
     * @param filePath
     */
    public static void fileExistsDir(String filePath){
    	File targetFile = new File(filePath);
    	try {
    		if(!targetFile.exists()){
                targetFile.mkdirs();
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    }
    
    public static void uploadCopyFile(InputStream  f,String filePath, String fileName) throws Exception {
    	fileExistsDir(filePath);
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        //File out = new File(filePath+fileName);
        FileCopyUtils.copy(f,out);
        try {
        	
        	out.flush();
        	out.close();
		} catch (Exception e) {
			// TODO: handle exception
		} 
        //out.write(file);
        //out.flush();
        //out.close();
    }


    /**
     * 判断指定的文件是否存在。
     * @param fileName 要判断的文件的文件名
     * @return 存在时返回true，否则返回false。
     * @since  1.0
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 从文件路径得到文件名。
     * @param filePath 文件的路径，可以是相对路径也可以是绝对路径
     * @return 对应的文件名
     * @since  1.0
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * /Users/yangx/Desktop/ccc.jpg
     * 结果：jpg
     *
     *
     * @param fileName
     * @return 文件名中的类型部分
     * @since  1.0
     */
    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        }
        else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     * @param file 文件
     * @return 文件名中的类型部分
     * @since  1.0
     */
    public static String getFileType(File file) {
        return getTypePart(file.getName());
    }


    //拷贝文件
    public static final boolean CopyFile(File in, File out) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(in);
            FileOutputStream fos = new FileOutputStream(out);
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
            fis.close();
            fos.close();
            return true;
        } catch (IOException ie) {
            ie.printStackTrace();
            return false;
        }
    }
    //拷贝文件
    public static final boolean CopyFile(String infile, String outfile) {
        try {
            File in = new File(infile);
            File out = new File(outfile);
            return CopyFile(in, out);
        } catch (Exception ie) {
            ie.printStackTrace();
            return false;
        }

    }
    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置。
     * 对于DOS或者UNIX风格的分隔符都可以。
     * @param fileName 文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
     * @since  1.0
     */
    public static int getPathLsatIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置。
     * 对于DOS或者UNIX风格的分隔符都可以。
     * @param fileName 文件路径
     * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
     * @since  1.0
     */
    public static int getPathLsatIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }
    /**
     * 得到文件的名字部分。
     * 实际上就是路径中的最后一个路径分隔符后的部分。
     *
     *
     * /Users/yangx/Desktop/ccc.jpg
     * 结果：ccc.jpg
     *
     * @param fileName 文件名
     * @return 文件名中的名字部分
     * @since  1.0
     */
    public static String getNamePart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        }
        else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                }
                else {
                    return fileName.substring(0, point);
                }
            }
            else {
                return fileName.substring(secondPoint + 1, point);
            }
        }
        else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * 将文件名中的类型部分去掉。
     *
     * /Users/yangx/Desktop/ccc.jpg
     * 结果：/Users/yangx/Desktop/ccc
     *
     * @param filename 文件名
     * @return 去掉类型部分的结果
     * @since  1.0
     */
    public static String trimType(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        }
        else {
            return filename;
        }
    }
    public static void main(String[] args) {
        String path = "/Users/yangx/Desktop/ccc.jpg";
        System.out.println(path+"\n结果："+getNamePart(path));
        System.out.println(path+"\n结果："+trimType(path));
        System.out.println(path+"\n结果："+getTypePart(path));





    }

}
