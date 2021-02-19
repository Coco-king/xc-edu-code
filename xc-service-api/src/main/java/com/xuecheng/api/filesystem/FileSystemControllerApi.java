package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件管理接口，提供文件的上传、删除、下载")
public interface FileSystemControllerApi {
    /**
     * 上传文件
     *
     * @param file        文件
     * @param fileTag     文件标签
     * @param businessKey 业务key
     * @param metadata    元信息,json格式
     */
    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileTag", value = "业务标签", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", value = "业务key", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "metadata", value = "文件元信息，json格式", paramType = "query", dataType = "String")
    })
    UploadFileResult upload(
            @ApiParam(name = "file", value = "文件", required = true) MultipartFile file,
            String fileTag, String businessKey, String metadata);

    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "filePath", value = "图片路径", required = true, paramType = "query", dataType = "String")
    ResponseResult deletePic(String filePath);
}
