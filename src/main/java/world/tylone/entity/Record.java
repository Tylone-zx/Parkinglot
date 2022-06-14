package world.tylone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Tylone
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String clientName;

    private String parklotName;

    private LocalDateTime createTime;

    private LocalDateTime arriveTime;

    private LocalDateTime leaveTime;

    private String placeNo;

    private Integer status;

    private String reserveToken;

}
