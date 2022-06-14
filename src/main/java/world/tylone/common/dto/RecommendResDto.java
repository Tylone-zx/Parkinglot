package world.tylone.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendResDto implements Serializable {

    private Integer id;

    private String parkName;

    private Double longitude;

    private Double latitude;

    private Double distance;

    private Integer price;

    private Integer totalNum;

    private Integer occupyNum;

    private Integer parkCredit;

}
