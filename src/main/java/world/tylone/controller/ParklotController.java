package world.tylone.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.tylone.common.dto.RegisterDto.ParklotRegisterDto;
import world.tylone.common.lang.Result;
import world.tylone.entity.Parklot;
import world.tylone.entity.Record;
import world.tylone.entity.Response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tylone
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/parklot")
public class ParklotController extends BaseController {

    @GetMapping("/list")
    public Result getParklotList() {
        List<Parklot> parklotList = parklotService.list();
        return Result.succ(parklotList);
    }

    @GetMapping("/listNoManager")
    public Result listNoManager() {
        List<Parklot> parklotList = parklotService.list(new QueryWrapper<Parklot>().eq("manager_id", 0));
        return Result.succ(parklotList);
    }

    @GetMapping("/getRecord/{parklotName}")
    public Result getRecord(@PathVariable(name = "parklotName") String parklotName) {
        List<Record> recordList = recordService.list(new QueryWrapper<Record>().eq("parklot_name", parklotName));
        return Result.succ(recordList);
    }

    @GetMapping("/detail/{id}")
    public Result getParklotDetail(@PathVariable(name = "id") Integer id) {
        recordUtils.reFresh();
        Parklot parklot = parklotService.getById(id);
        Assert.notNull(parklot, "该停车场不存在");

        List<Record> inRecords = recordService.list(new QueryWrapper<Record>()
                .eq("parklot_name", parklot.getParkName())
                .between("arrive_time", LocalDateTime.now().minusDays(1), LocalDateTime.now())
                .in("status", Arrays.asList(1, 2))
        );
        List<Record> outRecords = recordService.list(new QueryWrapper<Record>()
                .eq("parklot_name", parklot.getParkName())
                .between("leave_time", LocalDateTime.now().minusDays(1), LocalDateTime.now())
                .in("status", Arrays.asList(1, 2))
        );

        int[] inRecs = new int[24];
        int[] outRecs = new int[24];
        for (Record inRecord : inRecords) {
            Duration duration = Duration.between(inRecord.getArriveTime(), LocalDateTime.now());
            inRecs[Math.toIntExact(duration.toHours())]++;
        }
        for (Record outRecord : outRecords) {
            Duration duration = Duration.between(outRecord.getLeaveTime(), LocalDateTime.now());
            outRecs[Math.toIntExact(duration.toHours())]++;
        }

        String stars = "暂无评分";
        List<Response> responseList = responseService.list(new QueryWrapper<Response>().eq("parklot_name", parklot.getParkName()));
        if (responseList.size() > 0) {
            Double sum = (double) 0;
            for (Response response : responseList) {
                sum += response.getStars();
            }
            sum /= (double) responseList.size();
            stars = sum.toString();
        }

        return Result.succ(MapUtil.builder()
                .put("id", parklot.getId())
                .put("stars", stars)
                .put("parkName", parklot.getParkName())
                .put("longitude", parklot.getLongitude())
                .put("latitude", parklot.getLatitude())
                .put("parkCredit", parklot.getParkCredit())
                .put("price", parklot.getPrice())
                .put("occupyNum", parklot.getOccupyNum())
                .put("totalNum", parklot.getTotalNum())
                .put("inVolume", inRecs)
                .put("outVolume", outRecs)
                .map());
    }

    @GetMapping("/blackList")
    public Result getBlackList() {
        List<Parklot> parklotList = parklotService.list(new QueryWrapper<Parklot>().eq("is_black", 1));
        return Result.succ(parklotList);
    }

    @PreAuthorize("hasRole('root')")
    @PostMapping("/addParklot")
    public Result addParklot(@Validated @RequestBody ParklotRegisterDto parklotRegisterDto) {
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", parklotRegisterDto.getParkName()));
        Assert.isNull(parklot, "停车场已存在");
        parklot = new Parklot();
        BeanUtils.copyProperties(parklotRegisterDto, parklot);
        boolean status = parklotService.save(parklot);
        if (!status) return Result.fail("添加失败");
        redisUtil.saveParklotPosition(parklot);
        return Result.succ(MapUtil.builder()
                .put("parkName", parklot.getParkName())
                .put("price", parklot.getPrice())
                .put("token", parklot.getToken())
                .put("totalNum", parklot.getTotalNum())
                .map());
    }

    @PreAuthorize("hasRole('root')")
    @GetMapping("/delete/{id}")
    public Result deleteParklot(@PathVariable(name = "id") Integer id) {
        Parklot parklot = parklotService.getById(id);
        if (parklot.getManagerId() != 0)
            userService.removeById(parklot.getManagerId());
        parklotService.removeById(id);
        redisUtil.removeParklotPosition(parklot.getParkName());
        return Result.succ("删除成功");
    }
}
