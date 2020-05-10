package com.zq.service;//

import com.zq.mapper.PetMapper;
import com.zq.mapper.UserMapper;
import com.zq.pojo.Pet;
import com.zq.pojo.User;
import com.zq.utils.AlibabaSms;
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
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PetMapper petMapper;

    public List<User> selectAllUser() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    public void addUser(User user) {
        if (user.getName() != null && user.getPass() != null) {
            int insert = userMapper.insert(user);
        }

    }

    public Boolean deleteUser(User user) {
        int delete = userMapper.delete(user);
        if (delete > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User selectUserByName(String name) {
        User user = new User();
        user.setName(name);
        User select = userMapper.selectOne(user);
        if (select.getId() == null) {
            return null;
        } else {
            return select;
        }
    }

    public Boolean upadateUser(User user) {
        int i = userMapper.updateByPrimaryKey(user);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean nameIsEixt(String name) {
        System.out.println(name);
        User user = new User();
        user.setName(name);
        List<User> select = userMapper.select(user);
        if (select.size()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean namePassIsTrue(String name, String pass) {
        User user = new User();
        user.setName(name);
        user.setPass(pass);
        List<User> select = userMapper.select(user);
        System.out.println(select);
        if (select.size()>0){
            return true;
        }else {
            return false;
        }
    }

    public HashMap<String, String> registerUser(User user) {
        user.setCreate_time(new Date());
        int insert = userMapper.insert(user);
        HashMap<String, String> map = new HashMap<>();
        if (insert> 0){
            map.put("message","添加成功");
        }else {
            map.put("message","添加失败");
        }
        return  map;
    }

    public User selectOneUser(User user) {
        return  userMapper.selectOne(user);
    }

    public HashMap<String, String> callbackPassService(String iphone) {

        /*先校验该手机号所对应的密码是否存在*/
        User user = new User();
        user.setIphone(iphone);
        List<User> select = userMapper.select(user);
        HashMap<String, String> map = new HashMap<>();
        if (select.size() > 0) {
            new  AlibabaSms().sms(select.get(0).getIphone(),select.get(0).getPass());
            map.put("msg","已经发送到您的手机");
        }else {
            map.put("msg",null);
        }
        return map;
    }

    public HashMap<String, String> uupdateName(String id, String name) {
        HashMap<String, String> map = new HashMap<>();
        User user = new User();
        user.setId(Long.parseLong(id));
        User user1 = userMapper.selectOne(user);
        user1.setName(name);
        user1.setUpdate_time(new Date());
        userMapper.updateByPrimaryKey(user1);
       map.put("message","修改成功");
       return map;
    }

    public HashMap<String, String> updateMali(String id, String mail) {
        HashMap<String, String> map = new HashMap<>();
        User user = new User();
        user.setId(Long.parseLong(id));
        User user1 = userMapper.selectOne(user);
        user1.setMail(mail);
        user1.setUpdate_time(new Date());
        userMapper.updateByPrimaryKey(user1);
        map.put("message","修改成功");
        return map;
    }

    public HashMap<String,Object> getUser(String name, String mail) {
        HashMap<String, Object> map = new HashMap<>();
        User user = new User();
        user.setName(name);
        user.setMail(mail);
        User user1 = userMapper.selectOne(user);
        Pet pet = new Pet();
        pet.setUer_id(user1.getId());
        List<Pet>  pets  = petMapper.select(pet);
        map.put("id",user1.getId().toString());
        map.put("name",user1.getName());
        map.put("mail",user1.getMail());
        map.put("gender",user1.getGender());
        map.put("createTime",user1.getCreate_time().toString());
        map.put("pets", pets);
        return map;

    }

    public HashMap<String, Object> getId(String name, String mail) {
        HashMap<String, Object> map = new HashMap<>();
        User user = new User();
        user.setName(name);
        user.setMail(mail);
        User user1 = userMapper.selectOne(user);
        Pet pet = new Pet();
        pet.setUer_id(user1.getId());
        List<Pet>  pets  = petMapper.select(pet);
        map.put("id",user1.getId().toString());
        map.put("pets", pets);
        return map;
    }


    ;
}
