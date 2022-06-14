package world.tylone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import world.tylone.service.*;
import world.tylone.utils.RecordUtils;
import world.tylone.utils.RedisUtil;

public class BaseController {

    @Autowired
    ResponseService responseService;

    @Autowired
    UserService userService;

    @Autowired
    ParklotService parklotService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    RecordService recordService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RecordUtils recordUtils;

}