package com.ldz.wechat.util;

import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 从服务器端下载文件
 */
public class DloadImgUtil {
    Logger log = LoggerFactory.getLogger("access_info");

    @Autowired
    private WechatUtils wechatUtils;

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileexpandedName(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/gif".equals(contentType))
            fileEndWitsh = ".gif";
        else if ("video/png".equals(contentType))
            fileEndWitsh = ".png";
        return fileEndWitsh;
    }
    /**
     * 获取媒体文件
     * @param openid 接口访问凭证
     * @param mediaId 媒体文件id
     * @param savePath 文件在本地服务器上的存储路径
     * */
    public static String downloadMedia(String mediaId, String savePath,String accessToken) {
          String filePath = null;
        //返回的文件地址
          String repPath = null;
        // 拼接请求地址
        String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (!savePath.endsWith("/")) {
                savePath += "/";
            }
            // 根据内容类型获取扩展名
            String fileExt = DloadImgUtil .getFileexpandedName(conn.getHeaderField("Content-Type"));
            if(StringUtils.isEmpty(fileExt)){
                return null;
            }
            String datePath = DateUtils.getToday("YYYYMMdd");
            savePath += datePath+"/";
            FileUtil.fileExistsDir(savePath);

            // 将mediaId作为文件名
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString().replaceAll("-","");
            filePath = savePath + fileName + fileExt;
            repPath = "/"+datePath+"/" + fileName + fileExt;
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();
            String info = String.format("下载媒体文件成功，filePath=" + filePath);
            System.out.println(info);
        } catch (Exception e) {
            filePath = null;
            String error = String.format("下载媒体文件失败：%s", e);
            System.out.println(error);
        }
        return repPath;
    }
}