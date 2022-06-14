package world.tylone.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.tylone.common.dto.ResponseDto;
import world.tylone.common.lang.Result;
import world.tylone.entity.Parklot;
import world.tylone.entity.Response;
import world.tylone.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tylone
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/response")
public class ResponseController extends BaseController {

    @PostMapping("/upload")
    public Result feedbackUpload(Principal principal, @Validated @RequestBody ResponseDto responseDto) {
        Assert.isTrue(responseDto.getRole().equals("feedback") || responseDto.getRole().equals("complaint"), "属性错误");
        if (responseDto.getRole().equals("complaint"))
            Assert.isTrue(responseDto.getStars().equals(0), "评分异常");
        else
            Assert.isTrue(responseDto.getStars() >= 1 && responseDto.getStars() <= 5, "评分异常");

        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", responseDto.getParklotName()));
        Assert.notNull(parklot, "停车场不存在");
        Response response = new Response();
        BeanUtils.copyProperties(responseDto, response);
        response.setUserName(principal.getName());
        response.setCreateTime(LocalDateTime.now());
        boolean status = responseService.save(response);
        if (!status) return Result.fail("反馈失败");
        return Result.succ(response);
    }

    @GetMapping("/getResponse/{role}/{parklotName}")
    public Result getResponse(@PathVariable(name = "role") String role, @PathVariable(name = "parklotName") String parklotName) {
        Assert.isTrue(role.equals("feedback") || role.equals("complaint"), "属性错误");
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", parklotName));
        Assert.notNull(parklot, "停车场不存在");
        List<Response> responseList = responseService.list(new QueryWrapper<Response>().eq("role", role)
                .eq("parklot_name", parklotName));
        return Result.succ(responseList);
    }

    @PreAuthorize("hasRole('manager')")
    @GetMapping("/getResponse/{role}")
    public Result getResponse(Principal principal, @PathVariable(name = "role") String role) {
        Assert.isTrue(role.equals("feedback") || role.equals("complaint"), "属性错误");
        User user = userService.getByUsername(principal.getName());
        Parklot parklot = parklotService.getById(user.getParkNo());
        List<Response> responseList = responseService.list(new QueryWrapper<Response>().eq("role", role)
                .eq("parklot_name", parklot.getParkName()));
        return Result.succ(responseList);
    }
}
