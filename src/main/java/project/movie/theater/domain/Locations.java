package project.movie.theater.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "locations")
@Entity
@Getter
@ToString
public class Locations {
    @Schema(description = "위치코드", required = true, example = "GURO")
    @Id
    @Column(length = 30)
    private String code;

    @Schema(description = "위치명", required = true, example = "충주연수점")
    @Column(length = 50)
    private String name;

    @Schema(description = "위치그룹", required = true, example = "1")
    @Min(1) // 최소값 1
    @Max(3) // 최대값 3
    @Column(name = "group_num")
    private Integer groupNum;
}
