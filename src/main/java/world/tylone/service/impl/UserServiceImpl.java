package world.tylone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.tylone.entity.User;
import world.tylone.mapper.UserMapper;
import world.tylone.service.UserService;
import world.tylone.utils.RedisUtil;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public User getByUsername(String username) {
        return userService.getOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Integer userId) {

        User user = userService.getById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + user.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + user.getUsername());
        } else {
            authority = "ROLE_" + user.getRole();
            redisUtil.set("GrantedAuthority:" + user.getUsername(), authority, 60 * 60);
        }
        return authority;
    }
}
