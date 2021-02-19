package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.base.FileBaseController;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController extends FileBaseController implements FileSystemControllerApi {

    /**
     * 上传文件
     *
     * @param file        文件
     * @param fileTag     文件标签
     * @param businessKey 业务key
     * @param metadata    元信息,json格式
     */
    @Override
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public UploadFileResult upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileTag") String fileTag,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "metadata", required = false) String metadata) {
        return fileSystemService.upload(file, fileTag, businessKey, metadata);
    }
}
