package world.tylone.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RecommendDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arriveTime;

    @NotNull(message = "当前经度不能为空")
    private Double longitude;

    @NotNull(message = "当前纬度不能为空")
    private Double latitude;

    @NotNull(message = "半径不能为空")
    private Double radius;

}
