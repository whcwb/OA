package com.ldz.util.commonUtil;

//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;


/**
 * 将多张图片合并转为PDF；需要用到iTextpdf包，
 *
 * 	  <!-- 生成PDF的JAR包 -->
 * 	  <dependency>
 * 		  <groupId>com.itextpdf</groupId>
 * 		  <artifactId>itextpdf</artifactId>
 * 		  <version>5.5.13</version>
 * 	  </dependency>
 * 	  <!-- zip 工具包https://mvnrepository.com/artifact/org.apache.ant/ant -->
 * 	  <dependency>
 * 		  <groupId>org.apache.ant</groupId>
 * 		  <artifactId>ant</artifactId>
 * 		  <version>1.8.2</version>
 * 	  </dependency>
 *
 * @author 浴缸
 *
 */
public class PrintToPdfUtil {
    /**
     *
     * @param   files
     *            图片文件夹地址
     * @param pdfPath
     *            PDF文件保存地址
     *
     */
    public static void toPdf(File[] files, String pdfPath) {
//        try {
//
//            // 图片地址
//            String imagePath = null;
//            // PDF文件保存地址
//            // String pdfPath = "D:/Demo/ceshi/hebing.pdf";
//            // 输入流
//            FileOutputStream fos = new FileOutputStream(pdfPath);
//            // 创建文档
//            Document doc = new Document(null, 0, 0, 0, 0);
////            doc.open();
//            // 写入PDF文档
//            PdfWriter.getInstance(doc, fos);
//            // 读取图片流
//            BufferedImage img = null;
//            // 实例化图片
//            Image image = null;
//            // 获取图片文件夹对象
////            File file = new File(imageFolderPath);
////            File[] files = file.listFiles();
//            // 循环获取图片文件夹内的图片
//            for (File file1 : files) {
//                if (file1.getName().endsWith(".png")
//                        || file1.getName().endsWith(".jpg")
//                        || file1.getName().endsWith(".gif")
//                        || file1.getName().endsWith(".jpeg")
//                        || file1.getName().endsWith(".tif")) {
//                    // System.out.println(file1.getName());
//                    imagePath =  file1.getPath();
//                    System.out.println(file1.getName());
//                    // 读取图片流
//                    img = ImageIO.read(new File(imagePath));
//                    // 根据图片大小设置文档大小
//                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
//                    // 实例化图片
//                    image = Image.getInstance(imagePath);
//                    // 添加图片到文档
//                    doc.open();
//                    doc.add(image);
//                    doc.newPage();//生成新的一页
//
//                }
//            }
//            // 关闭文档
//            doc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (BadElementException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        File[] files =new File[1] ;
        files[0]=new File("D:\\2003/86619-107.png");
        toPdf(files, "D:\\2003//hebing.pdf");
        long time2 = System.currentTimeMillis();
        int time = (int) ((time2 - time1)/1000);
        System.out.println("执行了："+time+"秒！");
    }

}
