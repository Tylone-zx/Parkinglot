package world.tylone.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class parkCarDto implements Serializable {

    @NotBlank(message = "停车场名不能为空")
    private String parklotName;

    @NotBlank(message = "车牌号不能为空")
    private String placeNo;

    @NotBlank(message = "Token不能为空")
    private String reserveToken;
}
