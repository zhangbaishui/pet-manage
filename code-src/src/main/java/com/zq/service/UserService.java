package com.zq.service;//

import com.zq.mapper.UserMapper;
import com.zq.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    ;
}
