package project.movie.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;

@Getter
@Setter
@ToString
@Builder
public class MemberRespDto {
    private String memberId;
    private String username;
    private String email;
    private String tel;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String fullname;
    private MemberRole role; // ADMIN, CUSTOMER
    private MemberStatus status; // 활동 여부

    public MemberRespDto(String memberId, String username, String email, String tel, String zipcode, String address, String detailAddress, String fullname, MemberRole role, MemberStatus status) {
        this.memberId = memberId;
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

    public MemberRespDto(Member member) {
        this.memberId = member.getMemberId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.tel = member.getTel();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.detailAddress = member.getDetailAddress();
        this.fullname = member.getFullname();
        this.role = member.getRole();
        this.status = member.getStatus();
    }

    public static MemberRespDto from(Member member) {
        return MemberRespDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .username(member.getUsername())
                .email(member.getEmail())
                .tel(member.getTel())
                .zipcode(member.getZipcode())
                .address(member.getAddress())
                .fullname(member.getFullname())
                .role(member.getRole())
                .status(member.getStatus())
                .build();
    }

}
