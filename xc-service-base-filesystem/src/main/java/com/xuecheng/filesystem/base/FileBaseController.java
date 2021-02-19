package com.xuecheng.filesystem.base;

import com.xuecheng.filesystem.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;

public class FileBaseController {
    @Autowired
    protected FileSystemService fileSystemService;
}
