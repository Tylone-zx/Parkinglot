package world.tylone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import world.tylone.entity.User;

public interface UserService extends IService<User> {

    User getByUsername(String username);

    String getUserAuthorityInfo(Integer userId);
}
