package com.ldz.util.commonUtil;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * 图片合成文字和其它图片
 *
 */
public class ImgUtil {
    public static void main(String[] args) {
        String yearlateryyyy="";
        String jsonMapString="";

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        Date y = c.getTime();
        System.out.println("过去一年："+DateUtils.getDateStr(y,"yyyy"));
        System.out.println("过去一年："+DateUtils.getDateStr(y,"MM"));
        System.out.println("过去一年："+DateUtils.getDateStr(y,"dd"));


        jsonMapString="[{\"backdropImg\":\"/Users/yangx/Desktop/img/1.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[{\"fileType\":\"text\",\"x\":\"400\",\"y\":\"450\",\"message\":\"#userName#\",\"textzt\":\"宋体\",\"textsize\":\"28\"}]},{\"backdropImg\":\"/Users/yangx/Desktop/img/2.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[]},{\"backdropImg\":\"/Users/yangx/Desktop/img/3.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[]},{\"backdropImg\":\"/Users/yangx/Desktop/img/4.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[]},{\"backdropImg\":\"/Users/yangx/Desktop/img/5.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[{\"fileType\":\"text\",\"x\":\"1000\",\"y\":\"775\",\"message\":\"#addyyyy#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"225\",\"y\":\"840\",\"message\":\"#addmm#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"315\",\"y\":\"840\",\"message\":\"#adddd#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"450\",\"y\":\"840\",\"message\":\"#yearlateryyyy#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"535\",\"y\":\"840\",\"message\":\"#yearlatermm#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"625\",\"y\":\"840\",\"message\":\"#yearlaterdd#\",\"textzt\":\"宋体\",\"textsize\":\"18\"}]},{\"backdropImg\":\"/Users/yangx/Desktop/img/6.jpg\",\"oracleId\":\"#oracleId#\",\"parameterlist\":[{\"fileType\":\"text\",\"x\":\"380\",\"y\":\"645\",\"message\":\"#addyyyy#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"450\",\"y\":\"645\",\"message\":\"#addmm#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"530\",\"y\":\"645\",\"message\":\"#adddd#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"810\",\"y\":\"645\",\"message\":\"#addyyyy#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"895\",\"y\":\"645\",\"message\":\"#addmm#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"text\",\"x\":\"970\",\"y\":\"645\",\"message\":\"#adddd#\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"img\",\"x\":\"770\",\"y\":\"510\",\"message\":\"/Users/yangx/Desktop/img/logo/thtc.png\",\"textzt\":\"宋体\",\"textsize\":\"18\"},{\"fileType\":\"img\",\"x\":\"330\",\"y\":\"510\",\"message\":\"#userQm#\",\"textzt\":\"宋体\",\"textsize\":\"18\"}]}]";

        jsonMapString=jsonMapString.replaceAll("#oracleId#","order2003");
        jsonMapString=jsonMapString.replaceAll("#userName#","杨强");
        jsonMapString=jsonMapString.replaceAll("#userDn#","133111111111");
        jsonMapString=jsonMapString.replaceAll("#userZJH#","3200000000000000000");
        jsonMapString=jsonMapString.replaceAll("#addyyyy#","2018");
        jsonMapString=jsonMapString.replaceAll("#addmm#","08");
        jsonMapString=jsonMapString.replaceAll("#adddd#","08");
        jsonMapString=jsonMapString.replaceAll("#userQm#","/Users/yangx/Desktop/img/logo/thtc.png");

        jsonMapString=jsonMapString.replaceAll("#yearlateryyyy#","2019");
        jsonMapString=jsonMapString.replaceAll("#yearlatermm#","08");
        jsonMapString=jsonMapString.replaceAll("#yearlaterdd#","08");

//



//#oracleId#  订单id
//#userName#	用户姓名
//#userDn#	用户手机
//#userZJH#	用户证件号
//#addyyyy#	订单创建时间 年
//#addmm#		订单创建时间 月
//#adddd#		订单创建时间 日
//#userQm#	用户签名的地址。

//        List<Map<String,Object>> list=JsonUtil.toList(jsonMapString, Map.class);
//        for(Map<String,Object> m:list){
//            List<Map<String, String>> parameterlist=new ArrayList<>();
//            parameterlist= (List<Map<String, String>>) m.get("parameterlist");
//            //"backdropImg" -> "/Users/yangx/Desktop/img/1.jpeg"
//            generateCode("/Users/yangx/Desktop/img/out1",parameterlist,(String) m.get("backdropImg"),(String) m.get("oracleId"));//"oracleId" -> "order2003"
//        }
//        List<Map<String, String>> parameterlist=new ArrayList<>();
//        Map<String,String> map =new HashMap<String,String>();
//        map.put("fileType","img");
//        map.put("x","1");
//        map.put("y","1");
//        map.put("message","/Users/yangx/Desktop/img/logo/thtc.png");
//        parameterlist.add(map);
//        map =new HashMap<String,String>();
//        map.put("fileType","text");
//        map.put("x","100");
//        map.put("y","100");
//        map.put("message","杨强");
//        parameterlist.add(map);
//        map =new HashMap<String,String>();
//        map.put("fileType","text");
//        map.put("x","200");
//        map.put("y","200");
//        map.put("message","13311111111");
//        parameterlist.add(map);
//        ///Users/yangx/Desktop/img/logo/thtc.png
//
//        generateCode("/Users/yangx/Desktop/img/out",parameterlist,"/Users/yangx/Desktop/img/1.jpeg","order13456");
    }

    /**
     * 图片处理
     * @param outFilePaths  生成的文档存放的基础目录
     * @param parameterlist 附加在图片上的参数
     *                      fileType  text 文本 img 图片 (必填)
     *                      x       图片或者文本的X坐标 (必填)
     *                      y       图片或者文本的X坐标 (必填)
     *                      message  图片就是图片的根地址  文本就是它的文字 (必填)
     *                      textzt  文本的字体 默认是宋体
     *                      textsize    文本的大小 默认为10
     * @param backdropImg   背景图片的地址  D://2003/111.JPG
     * @param oracleId      订单的 唯一ID
     * @return
     */
    public static Map<String,String> generateCode(String outFilePaths, List<Map<String, String>> parameterlist, String backdropImg, String oracleId) {
        Map<String,String> ret=new HashMap<>();
        String fileUrl="";//返回的文件地址

        if(StringUtils.isEmpty(backdropImg)){
            ret.put("code","500");
            ret.put("message","背景地址不能为空");
            return ret;
        }
        if(StringUtils.isEmpty(oracleId)){
            ret.put("code","500");
            ret.put("message","订单ID不能为空");
            return ret;
        }
        //判断文件是否存在
        File backdropFile = new File(backdropImg);
        if(!backdropFile.isFile()){
            ret.put("code","500");
            ret.put("message","背景地址不存在，请核实");
            return ret;
        }

        try {
            String outFilePath=outFilePaths;
            if (!outFilePath.endsWith("/")) {
                outFilePath += "/";
            }

            outFilePath+=DateUtils.getToday("yyyyMMdd")+"/"+oracleId+"/";
            FileUtil.fileExistsDir(outFilePath);//创建目录
            String fileName=backdropFile.getName();//获取模板的名称
            if(parameterlist==null||parameterlist.size()==0){
                if (!outFilePath.endsWith("/")) {
                    outFilePath += "/";
                }
                FileUtil.CopyFile(backdropFile,new File(outFilePath+fileName));//复制文件
                fileUrl=(outFilePath+fileName).replaceAll(outFilePaths,"");//移除前缀
            }else{
                BufferedImage imageLocal = ImageIO.read(backdropFile);

                for(Map<String,String> map:parameterlist){
                    String fileType=StringUtils.trim(map.get("fileType"));//text 文本 img 图片 (必填)
                    String x=StringUtils.trim(map.get("x"));//图片或者文本的X坐标
                    String y=StringUtils.trim(map.get("y"));//图片或者文本的Y坐标
                    String message=StringUtils.trim(map.get("message"));//图片就是图片的根地址（d://2003/aaa.jgp）  文本就是它的文字 (必填)
                    if(StringUtils.isEmpty(fileType)){
                        ret.put("code","500");
                        ret.put("message","参数类型[fileType]不能为空，请核实");
                        return ret;
                    }else {
                        fileType.toLowerCase();//大写转小写
                    }
                    int i=0;
                    if(StringUtils.isEmpty(x)){
                        ret.put("code","500");
                        ret.put("message","参数X轴[x]不能为空，请核实");
                        return ret;
                    }else {
                        try {
                            i=Integer.parseInt(map.get("x"));
                        }catch (Exception e){
                            ret.put("code","500");
                            ret.put("message","参数X轴[x]必须是正整数，请核实");
                            return ret;
                        }
                    }
                    int j=0;
                    if(StringUtils.isEmpty(y)){
                        ret.put("code","500");
                        ret.put("message","参数Y轴[y]不能为空，请核实");
                        return ret;
                    }else{
                        try {
                            j=Integer.parseInt(map.get("y"));
                        }catch (Exception e){
                            ret.put("code","500");
                            ret.put("message","参数X轴[y]必须是正整数，请核实");
                            return ret;
                        }
                    }
                    if(StringUtils.isEmpty(message)){
                        ret.put("code","500");
                        ret.put("message","参数内容[message]不能为空，请核实");
                        return ret;
                    }

                    String textzt="宋体";//文本的字体 默认是宋体
                    if(StringUtils.isNotEmpty(map.get("textzt"))){
                        textzt=map.get("textzt");
                    }

                    int textsize=10;//文本的大小 默认为10
                    if(StringUtils.isNotEmpty(map.get("textsize"))){
                        try {
                            textsize=Integer.parseInt(map.get("textsize"));
                        }catch (Exception e){}
                    }
                    if(StringUtils.equals(fileType,"text")){
                        Graphics2D g = imageLocal.createGraphics();
                        Font font = new Font(textzt, Font.PLAIN, textsize);// 添加字体的属性设置
                        g.setFont(font);
                        g.setColor(Color.BLACK);//黑色
                        // 添加用户名称
                        g.drawString(message, i, j);

                        g.dispose();
                    }else if(StringUtils.equals(fileType,"img")){
                       // 加载图片
                        File imgFile=new File(message);
                        if(!imgFile.isFile()){
                            ret.put("code","500");
                            ret.put("message","素材地址："+message+"不存在，请核实");
                            return ret;
                        }
                        Graphics2D g = imageLocal.createGraphics();
                        BufferedImage imageCode = ImageIO.read(imgFile);
                        int imgWidth=imageCode.getWidth();
                        int imgHeight=imageCode.getHeight();
                        if(imgWidth>101){
                            Double bl= 1D;
                            try {
                                bl= Double.valueOf(imageCode.getWidth()/100);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            imgWidth= (int) Math.round(imgWidth/bl);
                            imgHeight= (int) Math.round(imgHeight/bl);
                        }
                        // 在模板上添加用户图片(地址,左边距,上边距,图片宽度,图片高度,未知)
                        g.drawImage(imageCode, i, j, imgWidth,imgHeight,null);
                        g.dispose();
                    }else{
                        ret.put("code","500");
                        ret.put("message","参数类型[fileType]:"+fileType+" 因该是(text、img)不正确，请核实");
                        return ret;
                    }

                    // 获取新文件的地址
                    File outputfile = new File(outFilePath+fileName);
                    // 生成新的合成过的用户二维码并写入新图片
                    ImageIO.write(imageLocal, "jpg", outputfile);
                    fileUrl=(outFilePath+fileName).replaceAll(outFilePaths,"");//移除前缀
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回给页面的图片地址(因为绝对路径无法访问)
//        imgName = Constants.PROJECT_URL + "codeImg/" + userId + ".png";
        ret.put("code","200");
        ret.put("message","操作成功");
        ret.put("fileUrl",fileUrl);
        return ret;
    }


}