package world.tylone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import world.tylone.entity.Response;
import world.tylone.mapper.ResponseMapper;
import world.tylone.service.ResponseService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tylone
 * @since 2021-05-27
 */
@Service
public class ResponseServiceImpl extends ServiceImpl<ResponseMapper, Response> implements ResponseService {

}
