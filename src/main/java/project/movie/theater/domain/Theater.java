package project.movie.theater.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "theaters")
@Entity
@Getter
@ToString
public class Theater {

    @Schema(description = "영화관 고유 번호", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Integer id;

    @Schema(description = "위치 코드", required = true, example = "GURO")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private Locations locations;

    // 영화관 사진
    private String img;

    // 영화관명
    private String name;

    // 주소
    private String address;

    // X좌표 (경도)
    private String x;

    // Y좌표 (위도)
    private String y;

}
