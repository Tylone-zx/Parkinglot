package world.tylone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import world.tylone.common.dto.*;
import world.tylone.common.lang.Result;
import world.tylone.entity.Parklot;
import world.tylone.entity.Record;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tylone
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/record")
public class RecordController extends BaseController {

    @GetMapping("/getByClientName/{clientName}")
    public Result getByClientName(@PathVariable(name = "clientName") String clientName) {
        recordUtils.reFresh();
        List<Record> recordList = recordService.list(new QueryWrapper<Record>().eq("client_name", clientName));
        return Result.succ(recordList);
    }

    @GetMapping("/getByParklotName/{parklotName}")
    public Result getByParklotName(@PathVariable(name = "parklotName") String parklotName) {
        recordUtils.reFresh();
        List<Record> recordList = recordService.list(new QueryWrapper<Record>().eq("parklot_name", parklotName));
        return Result.succ(recordList);
    }

    @GetMapping("/cancelReserve/{reserveId}")
    public Result cancelReserveById(Principal principal, @PathVariable(name = "reserveId") Integer reserveId) {
        recordUtils.reFresh();
        Record reserve = recordService.getById(reserveId);
        Assert.notNull(reserve, "预约不存在");
        Assert.isTrue(reserve.getStatus().equals(0) || reserve.getStatus().equals(3), "操作错误");
        Assert.isTrue(principal.getName().equals(reserve.getClientName()), "没有操作权限");
        boolean status = recordService.removeById(reserveId);
        if (status) return Result.succ("取消预约成功");
        return Result.fail("取消预约失败");
    }

    @PostMapping("/recommend")
    public Result recommend(@Validated @RequestBody RecommendDto recommendDto) {
        recordUtils.reFresh();
        Circle circle = new Circle(new Point(recommendDto.getLongitude(), recommendDto.getLatitude()), new Distance(recommendDto.getRadius(), Metrics.KILOMETERS));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(20);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius("Parklot", circle, args);
        Map<String, Double> distance = new HashMap<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            distance.put(result.getContent().getName(), result.getDistance().getValue());
        }

        Set<String> parklotNames = new HashSet<String>();
        List<Parklot> parklotList = parklotService.list();
        for (Parklot parklot : parklotList) {
            if (parklot.getOccupyNum() < parklot.getTotalNum()) parklotNames.add(parklot.getParkName());
        }
        List<Record> recordList = recordService.list(new QueryWrapper<Record>().eq("status", 0).or().eq("status", 1));
        for (Record record : recordList) {
            if (record.getLeaveTime().isBefore(recommendDto.getArriveTime())) parklotNames.add(record.getParklotName());
        }

        parklotNames.retainAll(distance.keySet());
        if (parklotNames.size() == 0) return Result.fail("暂无推荐停车场，请扩大搜索范围");
        parklotList = parklotService.list(new QueryWrapper<Parklot>().in("park_name", parklotNames));
        List<RecommendResDto> recommendResDtoList = new ArrayList<>();
        for (Parklot parklot : parklotList) {
            RecommendResDto recommendResDto = new RecommendResDto();
            BeanUtils.copyProperties(parklot, recommendResDto);
            recommendResDto.setDistance(distance.get(parklot.getParkName()));
            recommendResDtoList.add(recommendResDto);
        }
        recommendResDtoList.sort(Comparator.comparing(RecommendResDto::getDistance));
        return Result.succ(recommendResDtoList);
    }

    @PostMapping("/uploadReserve")
    public Result upload(Principal principal, @Validated @RequestBody RecordDto recordDto) {
        recordUtils.reFresh();
        Assert.isTrue(recordDto.getArriveTime().isBefore(recordDto.getLeaveTime()), "预约错误");
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", recordDto.getParklotName()));
        Assert.notNull(parklot, "停车场不存在");
        Assert.isTrue(parklot.getOccupyNum() < parklot.getTotalNum(), "停车位已满");
        Record reserve = new Record();
        BeanUtils.copyProperties(recordDto, reserve);
        reserve.setStatus(0);
        reserve.setClientName(principal.getName());
        reserve.setCreateTime(LocalDateTime.now());
        reserve.setReserveToken(UUID.randomUUID().toString());
        parklot.setOccupyNum(parklot.getOccupyNum() + 1);

        parklotService.saveOrUpdate(parklot);
        boolean status = recordService.save(reserve);
        if (!status) return Result.fail("预约失败");
        return Result.succ(reserve);
    }

    @PostMapping("/parkCar")
    public Result parkCar(Principal principal, @Validated @RequestBody parkCarDto parkCarDto) {
        recordUtils.reFresh();
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", parkCarDto.getParklotName()));
        if (parkCarDto.getReserveToken().equals("noReserve")) {
            Assert.isTrue(parklot.getOccupyNum() < parklot.getTotalNum(), "车位已满");
            Record record = new Record();
            BeanUtils.copyProperties(parkCarDto, record);
            record.setClientName(principal.getName());
            record.setCreateTime(LocalDateTime.now());
            record.setArriveTime(LocalDateTime.now());
            record.setReserveToken(UUID.randomUUID().toString());
            record.setStatus(1);

            parklot.setOccupyNum(parklot.getOccupyNum() + 1);
            parklotService.saveOrUpdate(parklot);
            boolean status = recordService.save(record);
            if (!status) return Result.fail("停车失败");
            return Result.succ(record);
        }
        Record record = recordService.getOne(new QueryWrapper<Record>().eq("status", 0).eq("reserve_token", parkCarDto.getReserveToken()));
        Assert.isTrue(record.getClientName().equals(principal.getName()), "没有权限");
        Assert.notNull(record, "预约不存在或已过期");
        record.setStatus(1);
        record.setArriveTime(LocalDateTime.now());
        boolean status = recordService.saveOrUpdate(record);
        if (!status) return Result.fail("停车失败");
        return Result.succ(record);
    }

    @PostMapping("/leave")
    public Result leave(Principal principal, @Validated @RequestBody LeaveDto leaveDto) {
        Record record = recordService.getOne(new QueryWrapper<Record>().eq("reserve_token", leaveDto.getReserveToken()));
        Assert.isTrue(record.getClientName().equals(principal.getName()), "没有权限");
        Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", record.getParklotName()));
        record.setLeaveTime(LocalDateTime.now());
        record.setStatus(2);
        parklot.setOccupyNum(parklot.getOccupyNum() - 1);
        parklotService.saveOrUpdate(parklot);
        recordService.saveOrUpdate(record);
        return Result.succ(record);
    }
}
