package world.tylone.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import world.tylone.common.dto.NoticeDto;
import world.tylone.common.lang.Result;
import world.tylone.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tylone
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @PostMapping("/upload")
    public Result noticeUpload(@Validated @RequestBody NoticeDto noticeDto) {
        Assert.isTrue(noticeDto.getTarget().equals("parklot") || noticeDto.getTarget().equals("client") || noticeDto.getTarget().equals("all"), "目标错误");
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        notice.setCreateTime(LocalDateTime.now());
        boolean status = noticeService.save(notice);
        if (!status) return Result.fail("操作失败");
        return Result.succ(MapUtil.builder()
                .put("title", notice.getTitle())
                .put("content", notice.getContent())
                .put("target", notice.getTarget())
                .put("createTime", notice.getCreateTime())
                .map());
    }

    @GetMapping("getNotice/{target}")
    public Result getNotice(@PathVariable(name = "target") String target) {
        Assert.isTrue(target.equals("parklot") || target.equals("client") || target.equals("all"), "目标错误");
        List<Notice> noticeList = noticeService.list(new QueryWrapper<Notice>().eq("target", target));
        return Result.succ(noticeList);
    }

}
