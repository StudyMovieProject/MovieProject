package project.movie.member.domain;

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

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
@Entity
@Getter
@Slf4j
@ToString
public class Member extends Base {
    @Id
    private String memberId;
    @Column(unique = true, nullable = false, length = 60) // 패스워드 인코딩(BCrypt)
    private String password;
    @Column(nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 30)
    private String email;
    @Column(length = 20)
    private String tel;
    @Column(length = 20)
    private String zipcode;
    private String address;
    @Column(name="detail_address")
    private String detailAddress;
    // @Column(nullable = false, length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role; // ADMIN, CUSTOMER

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
    public void update(MemberUpdateReqDto dto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(dto.getPassword()); // 비밀번호 변경 시 암호화 필요
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.tel = dto.getTel();
        this.zipcode = dto.getZipcode();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.fullname = dto.getFullname();
        this.role = dto.getRole();
        this.status = dto.getStatus(); // 상황에 따라 필요한 경우 ENUM 변환
    }
}