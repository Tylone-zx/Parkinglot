package world.tylone.service.impl;

import world.tylone.entity.Notice;
import world.tylone.mapper.NoticeMapper;
import world.tylone.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tylone
 * @since 2021-05-31
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
