package com.xuecheng.filesystem.base;

import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FileBaseService {
    @Autowired
    protected FastFileStorageClient storageClient;
    @Autowired
    protected ThumbImageConfig thumbImageConfig;
    @Autowired
    protected FileSystemRepository fileSystemRepository;
}
