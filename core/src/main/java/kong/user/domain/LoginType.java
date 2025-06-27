package kong.user.domain;

import lombok.Getter;

@Getter
public enum LoginType {
    NORMAL("일반 유저"),
    GITHUB("깃허브 유저");

    private final String description;

    LoginType(String description) {
        this.description = description;
    }
}
