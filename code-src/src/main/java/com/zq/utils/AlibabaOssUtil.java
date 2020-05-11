package com.zq.utils;//

//                       .::::.
//                     .::::::::.
//                    :::::::::::
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..
//

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class AlibabaOssUtil {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    //endpoint 的 地址需要与你的 Bucket地址对应。 如果Bucket为shanghai，就设置为shanghai
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    //云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private static String accessKeyId = "LTAI4Fe95ZULzQFxdPi62sKn";
    private static String accessKeySecret = "wcfDs9JvM0KpCzjNnmvQnA4ODK95gf";
    private static String bucketName = "baishui-1";

    //滚动条值   前端显示滚动条，利用request 的 0，1,2,3,4,5 状态码作为传输进度
    private static Integer percent = 0;


    /**
     * 用于外界赋值，目录名称。
     * 利用时间作为文件名，查找log方便
     */
    public static void upload(HttpServletRequest request , MultipartFile file , String tableName) throws IOException {
        System.out.println(file.getOriginalFilename());

        //TODO 进行赋值 文件的上传名称 包含目录 , 在赋值 tableName 中最后加上 / 隔开
        String objectName = "";
        if (tableName != null && !tableName.isEmpty()){
            objectName = tableName+"/"+file.getOriginalFilename();
        }else{
            objectName = file.getOriginalFilename();
        }

        //每次进入方法，就滚动条设置0
        setPercent(request);

        //将 MultipartFile 转为 File类型，进行上传到 oss
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 带进度条的上传。
            PutObjectResult putObjectResult = ossClient.putObject(new PutObjectRequest(bucketName, objectName, toFile).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        //最后删除掉，刚创建的 本地临时文件
        deleteTempFile(toFile);
    }


    /**
     * @param objectName  文件的名称
     * @param tableName  目录的名称
     * @return 获取上传文件的的 地址值
     */
    public static String getURL(String objectName , String tableName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //通过 ossClient 可以获取 刚上传文件的 访问URL
        Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);

        //判断目录是否 为空 或者 null
        if (tableName != null && !tableName.isEmpty()){
            objectName = tableName+"/"+objectName;
        }

        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);

        System.out.println(url.toString());

        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString().split("\\?")[0];
    }


    /**
     * @param objectName 删除阿里云OSS上 存储的对应 fileName的文件
     * @param tableName 删除阿里云OSS上 存储的对应 fileName的目录
     */
    public static void delete(String objectName , String tableName){
        // <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。

        //判断目录是否 为空 或者 null
        if (tableName != null && !tableName.isEmpty()){
            objectName = tableName+"/"+objectName;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * @param request 获取当前滚动条的数值
     * @return
     */
    public static Integer getPercent(HttpServletRequest request){
        return AlibabaOssUtil.percent;
    }


    /**
     * @param request 将滚动条 重置0
     */
    private static void setPercent(HttpServletRequest request){
        //每次进入，upload上传图片方法，就赋值 upload_percent 为0 ； 重置长度
        //防止 GET请求获取 进度条值 upload_percent时，出现异步，先获取的问题
        //request.getSession().setAttribute("upload_percent" , 0);
        AlibabaOssUtil.percent = 0;
    }


    /**
     * 获取流文件,将流文件 赋值给 file
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除本地临时文件
     * @param file
     */
    private static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     *  上传图片的 滚动条进行 存储到 session中。
     *  当前存储在成员变量中。可能会产生某些问题
     */
    private static class PutObjectProgressListener implements ProgressListener {
        private long bytesWritten = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
        private HttpServletRequest request;

        public PutObjectProgressListener(HttpServletRequest request) {
            this.request = request;
        }

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to upload......");
                    break;
                case REQUEST_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                    break;
                case REQUEST_BYTE_TRANSFER_EVENT:
                    this.bytesWritten += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                        System.out.println("我在赋值中"+percent);
                        //                        request.getSession().setAttribute("upload_percent",percent);
                        AlibabaOssUtil.percent = percent;
                        System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
                    }
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                    break;
                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                    break;
                default:
                    break;
            }
        }

        public boolean isSucceed() {
            return succeed;
        }
    }
}
