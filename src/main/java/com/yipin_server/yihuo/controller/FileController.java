package com.yipin_server.yihuo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Files;
import com.yipin_server.yihuo.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    private static String fileUploadPath = "/www/wwwroot/yipin_server/";
//
//    @Value("${qiniu.accessKey}")
//    private static String accessKey;
//
//    @Value("${qiniu.SecretKey}")
//    private static String accessSecretKey;
//
    private static String serverIp = "http://47.109.16.200:8083/";
//
//    private static String bucket = "yipin-image";

    @Resource
    private FileMapper fileMapper;

//    @Resource
//    private PictureService pictureService;
//
//
//    @AuthAccess
//    @GetMapping("/hhh")
//    public String test(){
//        return "uuhjhjhjkj";
//    }
//
//    //上传头像的方法
//    @AuthAccess
//    @PostMapping(value = "/uploadtest")
//    public Result uploadtest(@RequestParam("file") MultipartFile file) {
//        String path = pictureService.upload(file);
//        System.out.println(path);
//        return Result.ok().data("url",path);
//    }

//    @AuthAccess
    @PostMapping("/upload")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result upload(MultipartFile file) throws IOException {
        System.out.println("上传文件---------");
        System.out.println(file);
        String originalFileName = file.getOriginalFilename();
        String type = FileUtil.extName(originalFileName);
        //定义文件唯一标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;
        long size = file.getSize();
        //文件存储地址
        File uploadFile = new File(fileUploadPath + fileUUID);
        //判断配置文件的文件目录是否存在，不存在则创建新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        String url;
        String md5 = SecureUtil.md5(file.getInputStream());
        //数据库查询是否有相同记录
        Files dbFiles = fileMapper.selectOne(new QueryWrapper<Files>().eq("md5",md5));
        if(dbFiles!=null){
            url = dbFiles.getUrl();
        }else{
            //上传文件到磁盘
            file.transferTo(uploadFile);
            //数据库若不存在重复文件，则不删除刚才上传的文件
            url =  serverIp + fileUUID;
            System.out.println("文件地址："+url);
            Files saveFile = new Files();
            saveFile.setName(originalFileName);
            saveFile.setType(type);
            saveFile.setSize(size/1024);//kb
            saveFile.setUrl(url);
            saveFile.setMd5(md5);
            fileMapper.insert(saveFile);
        }
        Map<String ,Object> map = new HashMap<>();
        map.put("url" ,url);
        return Result.ok().data(map);
    }

//    @AuthAccess
//    @Async
//    public String upload(String fileUUID,MultipartFile file,String type,String originalFileName ) throws IOException {
//
//    }

//    @AuthAccess
    @GetMapping("/{fileUUID}")
    public Result download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        System.out.println("下载文件---------");
        //根据文件的唯一标识获取文件
        File uploadFile = new File(fileUploadPath  +fileUUID);
        ServletOutputStream outputStream = response.getOutputStream();
        //设置输出流格式
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
        return Result.ok();
    }

//    @AuthAccess
    @DeleteMapping("/delete")
    public Result delete(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        System.out.println("删除文件---------");
        fileMapper.delete(new QueryWrapper<Files>().eq("url","http://47.109.16.200:80/"+fileUUID));
        //根据文件的唯一标识获取文件
        return Result.ok();
    }

}
