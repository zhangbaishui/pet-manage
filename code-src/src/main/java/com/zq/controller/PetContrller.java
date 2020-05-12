package com.zq.controller;//

import com.zq.pojo.Pet;
import com.zq.service.PetService;
import com.zq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RestController
@RequestMapping("/pet")
public class PetContrller {
    @Autowired
    PetService petService;

    /*通过id获取信息*/
    @PostMapping("/getPetById")
    @ResponseBody
    public Map<String, Object> getPet(@RequestParam(value = "id") String id) {
        HashMap<String, Object> map = petService.getPet(id);
        return map;
    }

    /*获取所有宠物类型信息*/
    @PostMapping("/queryAllType")
    @ResponseBody
    public Map<String, Object> queryAllType() {
        HashMap<String, Object> map = petService.queryAllType();
        return map;
    }
    /*删除宠物*/
    @PostMapping("/deleteById")
    @ResponseBody
    public void deleteById(@RequestParam(value = "id") String id) {
        petService.delete(id);
    }


    /*修改宠物信息*/
    @PostMapping("/update")
    @ResponseBody
    public void update(@RequestParam(value = "id") String id,
                       @RequestParam(value = "petName") String petName,
                       @RequestParam(value = "petAge") String petAge,
                       @RequestParam(value = "type") String type,
                       @RequestParam(value = "status") String status,
                       @RequestParam(value = "hobby") String hobby,
                       @RequestParam(value = "image") List<String> image ,
                       @RequestParam(value = "desc") String desc) {
        Pet pet = new Pet();
        pet.setId(Long.parseLong(id));
        pet.setPet_name(petName);
        pet.setPet_age(Long.parseLong(petAge));
        pet.setType(Long.parseLong(type));
        pet.setPet_status(status);
        pet.setImage(image.toString());
        pet.setHobby(hobby);
        pet.setPet_desc(desc);
        pet.setUpdate_time(new Date());
        petService.update(pet);

    }

    /*修改宠物信息*/
    @PostMapping("/add")
    @ResponseBody
    public void add(

            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "petName") String petName,
                       @RequestParam(value = "petAge") String petAge,
                       @RequestParam(value = "type") String type,
                       @RequestParam(value = "status") String status,
                       @RequestParam(value = "hobby") String hobby,
                       @RequestParam(value = "image") List<String> image ,
                       @RequestParam(value = "desc") String desc) {
        Pet pet = new Pet();
        pet.setUer_id(Long.parseLong(userId));
        pet.setPet_name(petName);
        pet.setPet_age(Long.parseLong(petAge));
        pet.setType(Long.parseLong(type));
        pet.setPet_status(status);
        pet.setHobby(hobby);
        pet.setPet_desc(desc);
        pet.setImage(image.toString());
        pet.setUpdate_time(new Date());
        petService.add(pet);

    }
}
