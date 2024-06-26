package com.macro.mall.tiny.common.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.macro.mall.tiny.common.domain.AliyunProperties;
import com.macro.mall.tiny.common.service.OSSUnity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;


/**
 * 阿里云服务实现
 *
 * @author 轻描淡写 linkxs@qq.com
 */
public class AliyunServiceImpl implements OSSUnity {
    @Resource
    private AliyunProperties aliyunProperties;

    private static OSS oss;

    @PostConstruct
    private void init() {
        try {
            oss = new OSSClientBuilder().build(
                    aliyunProperties.getEndpoint(),
                    aliyunProperties.getAccessKeyId(),
                    aliyunProperties.getAccessKeySecret()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String upload(String path, File file) {
        oss.putObject(aliyunProperties.getBucketName(), path, file);
        return aliyunProperties.getDomain() + "/" + path;
    }

    @Override
    public String upload(String path, InputStream inputStream) {
        oss.putObject(aliyunProperties.getBucketName(), path, inputStream);
        return aliyunProperties.getDomain() + "/" + path;
    }
}
