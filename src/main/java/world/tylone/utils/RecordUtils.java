package world.tylone.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import world.tylone.entity.Parklot;
import world.tylone.entity.Record;
import world.tylone.service.ParklotService;
import world.tylone.service.RecordService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RecordUtils {

    @Autowired
    RecordService recordService;

    @Autowired
    ParklotService parklotService;

    public void reFresh() {
        List<Record> recordList = recordService.list(new QueryWrapper<Record>().eq("status", 0));
        for (Record record : recordList) {
            if (record.getArriveTime().isBefore(LocalDateTime.now())) {
                Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", record.getParklotName()));
                parklot.setOccupyNum(parklot.getOccupyNum() - 1);
                parklotService.saveOrUpdate(parklot);
                record.setStatus(3);
                recordService.saveOrUpdate(record);
            }
        }

        recordList = recordService.list(new QueryWrapper<Record>().eq("status", 1));
        for (Record record : recordList) {
            if (record.getLeaveTime().isBefore(LocalDateTime.now())) {
                Parklot parklot = parklotService.getOne(new QueryWrapper<Parklot>().eq("park_name", record.getParklotName()));
                parklot.setOccupyNum(parklot.getOccupyNum() - 1);
                parklotService.saveOrUpdate(parklot);
                record.setStatus(2);
                recordService.saveOrUpdate(record);
            }
        }
    }
}