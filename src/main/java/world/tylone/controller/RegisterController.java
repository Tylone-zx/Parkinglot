package world.tylone.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.tylone.common.dto.RegisterDto.ClientRegisterDto;
import world.tylone.common.dto.RegisterDto.ManagerRegisterDto;
import world.tylone.common.dto.RegisterDto.ParklotRegisterDto;
import world.tylone.common.lang.Result;
import world.tylone.entity.Parklot;
import world.tylone.entity.User;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @PostMapping("/client")
    public Result clientRegister(@Validated @RequestBody ClientRegisterDto clientRegisterDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", clientRegisterDto.getUsername()));
        Assert.isNull(user, "用户已存在");
        user = new User();
        BeanUtils.copyProperties(clientRegisterDto, user);
        user.setCreated(LocalDateTime.now());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(clientRegisterDto.getPassword()));
        boolean status = userService.save(user);
        if (!status) return Result.fail("注册失败");
        return Result.succ(MapUtil.builder()
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("telNo", user.getTelNo())
                .put("role", "client")
                .map());
    }

    @PostMapping("/manager")
    public Result managerRegister(@Validated @RequestBody ManagerRegisterDto managerRegisterDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", managerRegisterDto.getUsername()));
        Assert.isNull(user, "用户已存在");
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", managerRegisterDto.getParkName()));
        Assert.notNull(parklot, "停车场不存在");
        Assert.isTrue(parklot.getManagerId() == 0, "该停车场已有管理员");
        Assert.isTrue(parklot.getToken().equals(managerRegisterDto.getToken()), "口令错误");
        user = new User();
        BeanUtils.copyProperties(managerRegisterDto, user);
        user.setCreated(LocalDateTime.now());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(managerRegisterDto.getPassword()));
        user.setRole("manager");
        user.setParkNo(parklot.getId());
        boolean status = userService.save(user);
        if (!status) return Result.fail("注册失败");
        user = userService.getOne(new QueryWrapper<User>().eq("username", managerRegisterDto.getUsername()));
        parklot.setManagerId(user.getId());
        parklotService.saveOrUpdate(parklot);
        return Result.succ(MapUtil.builder()
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("telNo", user.getTelNo())
                .put("role", user.getRole())
                .put("parkNo", user.getParkNo())
                .map());
    }

    @PostMapping("/parklot")
    public Result parklotRegister(@Validated @RequestBody ParklotRegisterDto parklotRegisterDto) {
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", parklotRegisterDto.getParkName()));
        Assert.isNull(parklot, "停车场已存在");
        parklot = new Parklot();
        BeanUtils.copyProperties(parklotRegisterDto, parklot);
        boolean status = parklotService.save(parklot);
        if (!status) return Result.fail("注册失败");
        redisUtil.saveParklotPosition(parklot);
        return Result.succ(MapUtil.builder()
                .put("parkName", parklot.getParkName())
                .put("price", parklot.getPrice())
                .put("token", parklot.getToken())
                .put("totalNum", parklot.getTotalNum())
                .map());
    }
}
