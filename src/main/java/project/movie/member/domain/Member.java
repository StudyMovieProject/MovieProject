package project.movie.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.movie.common.domain.Base;
import project.movie.member.dto.MemberUpdateReqDto;
import project.movie.member.dto.PasswordChangeReqDto;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
@Entity
@Getter
@Slf4j
@ToString
public class Member extends Base {
    @Schema(description = "아이디", required = true, example = "jin8374")
    @Id
    @Column(name = "member_id")
    private String memberId;

    @Schema(description = "비밀번호", required = true, example = "wlsrud1234")
    @Column(unique = true, nullable = false, length = 60) // 패스워드 인코딩(BCrypt)
    private String password;

    @Schema(description = "이름", required = true, example = "진경이")
    @Column(nullable = false, length = 20)
    private String username;

    @Schema(description = "이메일", required = true, example = "park32122@naver.com")
    @Column(nullable = false, length = 30)
    private String email;

    @Schema(description = "전화번호", required = true, example = "01066554444")
    @Column(length = 20)
    private String tel;

    @Schema(description = "우편코드", example = "417-888")
    @Column(length = 20)
    private String zipcode;

    @Schema(description = "주소", example = "서울시 강남구 대치동")
    private String address;

    @Schema(description = "상세 주소", example = "66-1")
    @Column(name="detail_address")
    private String detailAddress;

    @Schema(description = "전체 이름", example = "박진경")
    // @Column(nullable = false, length = 20)
    private String fullname;

    @Schema(description = "권한", example = "GUEST | CUSTOMER")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role; // GUEST, CUSTOMER

    @Schema(description = "활동 여부", example = "true | false")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus status; // 활동 여부

    @Builder
    public Member(String memberId, String password, String username, String email, String tel, String zipcode, String address, String detailAddress, String fullname, MemberRole role, MemberStatus status) {
        this.memberId = memberId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.fullname = fullname;
        this.role = role;
        this.status = status;
    }

    public boolean verifyOwnMemberId(String memberId) {
        return memberId.equals(this.memberId);
    }

    // 전체 업데이트 메서드
    public void update(MemberUpdateReqDto dto) {
        // this.password = bCryptPasswordEncoder.encode(dto.getPassword()); // 비밀번호 변경 시 암호화 필요
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.tel = dto.getTel();
        this.zipcode = dto.getZipcode();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.fullname = dto.getFullname();
        this.role = MemberRole.valueOf(dto.getRole());
        this.status = dto.getStatus(); // 상황에 따라 필요한 경우 ENUM 변환
    }

    public void update(PasswordChangeReqDto dto, BCryptPasswordEncoder bCryptPasswordEncoder) {
         this.password = bCryptPasswordEncoder.encode(dto.getNewPassword()); // 비밀번호 변경 시 암호화 필요
    }

    public boolean validatePassword(String inputPassword, BCryptPasswordEncoder bCryptPasswordEncoder) {
        // 입력된 평문 패스워드와 이미 암호화된 패스워드를 비교
        return bCryptPasswordEncoder.matches(inputPassword, this.password);
    }
}
