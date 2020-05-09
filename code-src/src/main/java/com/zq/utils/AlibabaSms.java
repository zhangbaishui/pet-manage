package com.zq.utils;//

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;


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
/*Quan.Zhang:  阿里短信服务*/
public class AlibabaSms {
    public static void main(String[] args) {

        //切换accessKeyId   LTAI4Fe95ZULzQFxdPi62sKn	;   accessSecret wcfDs9JvM0KpCzjNnmvQnA4ODK95gf;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fe95ZULzQFxdPi62sKn", "wcfDs9JvM0KpCzjNnmvQnA4ODK95gf");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13866393137");
        request.putQueryParameter("SignName", "亲爸爸系列");
        request.putQueryParameter("TemplateCode", "SMS_189761455");
        request.putQueryParameter("TemplateParam", "{'code':'eeqeq'}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
