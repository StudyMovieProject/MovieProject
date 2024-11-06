package project.movie.member.dto;


import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberSaveReqDto {
    @NotNull
    private String memberId;
    @NotNull
    @Size(min = 4, max = 20)
    private String password;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해주세요")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[0-9]{11}", message = "연락처 형식으로 작성해주세요")
    private String tel;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String fullname;
    @NotNull
    @Pattern(regexp = "^(ADMIN|CUSTOMER)$")
    private String role; // ADMIN, CUSTOMER
    private MemberStatus status; // 활동 여부
    private String certificationCode; // 인증 코드

    @Builder
    public MemberSaveReqDto(Member member) {
        this.memberId = member.getMemberId();
        this.password = member.getPassword();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.tel = member.getTel();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.detailAddress = member.getDetailAddress();
        this.fullname = member.getFullname();
        this.role = member.getRole().toString();
        this.status = member.getStatus();
    }

    public Member to(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .memberId(memberId)
                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .email(email)
                .tel(tel)
                .zipcode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .fullname(fullname)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.PENDING)
                .build();
    }
}
