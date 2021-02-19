package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.xuecheng.filesystem.base.FileBaseService;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class FileSystemService extends FileBaseService {

    /**
     * 上传文件
     *
     * @param file        文件
     * @param fileTag     文件标签
     * @param businessKey 业务key
     * @param metadata    元信息,json格式
     */
    public UploadFileResult upload(MultipartFile file, String fileTag, String businessKey, String metadata) {
        if (file == null) ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        if (StringUtils.isBlank(fileTag)) ExceptionCast.cast(CommonCode.INVALID_PARAM);
        //上传文件
        InputStream stream;
        try {
            stream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            if (StringUtils.isBlank(fileName)) ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEIDISNULL);
            String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
            StorePath upload = storageClient.uploadFile(stream, file.getSize(), fileExtName, null);
            String fileId = upload.getFullPath();
            //初始化文件信息
            FileSystem fileSystem = new FileSystem();
            fileSystem.setFileId(fileId);
            fileSystem.setFileType(file.getContentType());
            fileSystem.setFileTag(fileTag);
            fileSystem.setFilePath(fileId);
            fileSystem.setBusinessKey(businessKey);
            fileSystem.setFileName(fileName);
            fileSystem.setFileSize(file.getSize());
            if (StringUtils.isNotBlank(metadata)) {
                try {
                    fileSystem.setMetadata(JSON.parseObject(metadata, Map.class));
                } catch (Exception e) {
                    ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_METAERROR);
                }
            }
            fileSystemRepository.save(fileSystem);
            return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
        } catch (IOException e) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_GETSTREAMFAIL);
        }
        return null;
    }

    /**
     * 根据图片ID删除图片
     */
    public ResponseResult deletePic(String filePath) {
        storageClient.deleteFile(filePath);
        long res = fileSystemRepository.deleteByFilePath(filePath);
        if (res > 0) return ResponseResult.SUCCESS();
        return ResponseResult.FAIL();
    }
}
