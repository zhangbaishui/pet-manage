package com.zq.service;//

import com.zq.mapper.PetMapper;
import com.zq.mapper.PetTypeMapper;
import com.zq.mapper.UserMapper;
import com.zq.pojo.Pet;
import com.zq.pojo.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
@Service
public class PetService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetTypeMapper  petTypeMapper;


    public HashMap<String, Object> getPet(String id) {
        Pet pet1 = new Pet();
        pet1.setId(Long.parseLong(id));
        Pet pet = petMapper.selectOne(pet1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pet",pet);
        return map;
    }

    public HashMap<String, Object> queryAllType() {
        List<PetType> petTypes = petTypeMapper.selectAll();
        HashMap<String, Object> map = new HashMap<>();
        map.put("types",petTypes);
        return  map;
    }

    public void update(Pet pet) {
        Pet pet1 = new Pet();
        pet1.setId(pet.getId());
        Pet pet2 = petMapper.selectOne(pet1);
        pet.setUer_id(pet2.getUer_id());
        pet.setCreate_time(pet2.getCreate_time());
        petMapper.updateByPrimaryKey(pet);
    }

    public void delete(String id) {
        Pet pet = new Pet();
        pet.setId(Long.parseLong(id));
        petMapper.deleteByPrimaryKey(pet);
    }

    public void add(Pet pet) {
        pet.setCreate_time(new Date());
        petMapper.insertSelective(pet);
    }
}
