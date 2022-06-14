package world.tylone.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.tylone.common.dto.ClientDto;
import world.tylone.common.dto.RegisterDto.ClientRegisterDto;
import world.tylone.common.dto.RegisterDto.ManagerRegisterDto;
import world.tylone.common.lang.Result;
import world.tylone.entity.Parklot;
import world.tylone.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/info")
    public Result userInfo(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        return Result.succ(user);
    }

    @GetMapping("/client/list")
    public Result clientDetail() {
        List<User> userList = userService.list(new QueryWrapper<User>().eq("role", "client"));
        List<ClientDto> clientDtoList = new ArrayList<>();
        for (User user : userList) {
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(user, clientDto);
            clientDtoList.add(clientDto);
        }
        return Result.succ(clientDtoList);
    }

    @GetMapping("/client/{username}")
    public Result clientDetail(@PathVariable(name = "username") String username) {
        User user = userService.getByUsername(username);
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(user.getRole().equals("client"), "请求错误");
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(user, clientDto);
        return Result.succ(clientDto);
    }

    @GetMapping("/client/blackList")
    public Result clientBlackList() {
        List<User> userList = userService.list(new QueryWrapper<User>().eq("is_black", 1));
        List<ClientDto> clientDtoList = new ArrayList<>();
        for (User user : userList) {
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(user, clientDto);
            clientDtoList.add(clientDto);
        }
        return Result.succ(clientDtoList);
    }

    @PreAuthorize("hasRole('root')")
    @GetMapping("/list")
    public Result getUserList() {
        List<User> userList = userService.list(new QueryWrapper<User>().ne("role", "root"));
        return Result.succ(userList);
    }

    @PreAuthorize("hasRole('root')")
    @GetMapping("/delete/{id}")
    public Result userDelete(Principal principal, @PathVariable(name = "id") Integer id) throws Exception {

        User user = userService.getById(id);
        Assert.notNull(user, "用户不存在");
        if (principal.getName().equals(user.getUsername()))
            throw new Exception("操作错误");

        if (user.getRole().equals("manager")) {
            Parklot parklot = parklotService.getById(user.getParkNo());
            parklot.setManagerId(0);
            parklotService.saveOrUpdate(parklot);
        }

        userService.removeById(id);
        return Result.succ("删除成功");
    }

    @PreAuthorize("hasRole('root')")
    @PostMapping("/addClient")
    public Result addClient(@Validated @RequestBody ClientRegisterDto clientRegisterDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", clientRegisterDto.getUsername()));
        Assert.isNull(user, "用户已存在");
        user = new User();
        BeanUtils.copyProperties(clientRegisterDto, user);
        user.setCreated(LocalDateTime.now());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(clientRegisterDto.getPassword()));
        boolean status = userService.save(user);
        if (!status) return Result.fail("添加失败");
        return Result.succ(MapUtil.builder()
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("telNo", user.getTelNo())
                .put("role", "client")
                .map());
    }

    @PreAuthorize("hasRole('root')")
    @PostMapping("/addManager")
    public Result addManager(@Validated @RequestBody ManagerRegisterDto managerRegisterDto) {
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
        if (!status) return Result.fail("添加失败");
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
}