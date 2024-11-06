package project.movie.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
    ADMIN("관리자"), CUSTOMER("고객");
    private String value;
}