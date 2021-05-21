package com.ldz.biz.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.util.bean.ApiResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公共接口 ， 不需要权限验证
 */
@RequestMapping("/pub")
@RestController
public class PubController {


    @Autowired
    private TraineeInformationService informationService;


    @PostMapping("/update_exception")
    public Map<String,Object> gxEx(@RequestParam("file") MultipartFile file ,String km) throws IOException {
        Map<String,Object> map = Maps.newHashMap();
        File tempFile = new File("D:/temp.txt");
        FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
        List<String> lines = FileUtils.readLines(tempFile, StandardCharsets.UTF_8.name());


        List<String> sucList = Lists.newArrayList();
        List<String> errorList = Lists.newArrayList();

        for (String line : lines) {
            String[] split = line.split(",");

            String result = split[3];
            if (StringUtils.isBlank(result)) {
                // 结果为空的也不处理
                continue;
            }
            String ex = split[8];
            String time = ex.substring(ex.indexOf("考试时间:")+5, ex.indexOf("考试地点") ).trim();

            String idCard = split[2];
            TraineeInformation information = informationService.findByIdCardNo(idCard);
            if(information == null) {
                errorList.add("未找到学员信息：" + idCard );
                // 需要记录下
                continue;
            }

            ApiResponse<String> response = informationService.updateTestResultForEx(information.getId(), km, result.equals("1") ? "00" : "10", time);
            if(response.getCode() == 200) {
                sucList.add("成功：" + idCard);
            }else {
                errorList.add("失败：" + idCard);
            }

        }
        tempFile.delete();
        // 最后 清空所有已经结业学员的异常

        map.put("suc",sucList);
        map.put("fail",errorList);
        return map;

    }



}
