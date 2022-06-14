package world.tylone.common.dto.RegisterDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ParklotRegisterDto implements Serializable {

    @NotBlank(message = "令牌不能为空")
    private String token;

    @NotNull(message = "停车场经度不能为空")
    private Double longitude;

    @NotNull(message = "停车场纬度不能为空")
    private Double latitude;

    @NotNull(message = "停车场价格不能为空")
    private Integer price;

    @NotBlank(message = "停车场名不能为空")
    private String parkName;

    @NotNull(message = "停车场容量不能为空")
    private Integer totalNum;
}