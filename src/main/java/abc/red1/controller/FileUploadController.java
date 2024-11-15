package abc.red1.controller;

import abc.red1.entity.Manager;
import abc.red1.service.ManagerService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FileUploadController
 * @Author YiXia
 * @Date 2024/1/25 11:32
 * @Version 1.0
 * @Description TODO
 **/

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {


    private String managerUrl = "D:/red1/managerImage/";
    @Autowired
    private ManagerService managerService;

    @PostMapping("/managerImageFirstUpload")
    public String managerImageFirstUpload(@RequestParam("file") MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);
        //获取文件大小
        long size = file.getSize();

        //获取文件
        File uploadParentFile = new File(managerUrl);
        //判断文件目录是否存在
        if (!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();

        String newName = uuid + StrUtil.DOT + type;
        File uploadFile = new File(managerUrl + newName);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        //判断管理员列表是否有一条新数据
        Manager m = managerService.getLastOne();
        log.info("打印managerImage管理员信息 = {}",m);
        if (m.getManagerImage() == null || "".equals(m.getManagerImage())) {
            m.setManagerImage(newName);
            managerService.updateById(m);
        }
        return "Success";
    }


}
