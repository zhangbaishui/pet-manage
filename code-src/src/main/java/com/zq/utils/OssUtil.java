package com.zq.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class OssUtil {


    //文件上传方法
    public String upload(File file) {
        if (file == null) {
            return null;
        }

        String endPoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessId = "LTAI4Fe95ZULzQFxdPi62sKn";
        String accessSecret = "wcfDs9JvM0KpCzjNnmvQnA4ODK95gf";
        String bucketName =  "baishui-1";
        String hostName = "image";

        //阿里云客户端对象
        OSSClient client = new OSSClient(endPoint, accessId, accessSecret);

        try {
            //当桶不存在时创建桶
            if (!client.doesBucketExist(bucketName)) {
                //client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                //访问控制器设置为公共读
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }

            //"images"+"当天的日期"+uuid+文件名;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = format.format(new Date());
            // images/2019-10-21/6c964702b67d4eeb920e7f1f4358189b-dishu.jpg
            String key = hostName + "/" + currentDate + "/" + (UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //最核心的文件上传方法
            PutObjectResult result = client.putObject(putObjectRequest);
            //公共读的权限
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

            if (result != null) {
                //说明上传成功
                return key;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                //关闭客户端与服务器之间的连接
                client.shutdown();
            }
        }

        return null;
    }


}
