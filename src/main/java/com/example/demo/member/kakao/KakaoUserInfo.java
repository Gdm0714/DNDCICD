package com.example.demo.member.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {

    @JsonProperty("id")
    private Long idToken;

    // 카카오 계정 정보
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {

        // 사용자 프로필 정보
        @JsonProperty("profile")
        public Profile profile;

        // 이메일 제공 동의 여부
        @JsonProperty("email_needs_agreement")
        private Boolean isEmailAgree;

        // 카카오계정 대표 이메일
        @JsonProperty("email")
        private String email;

        @Getter
        @NoArgsConstructor
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            // 닉네임
            @JsonProperty("nickname")
            private String nickname;

            // 프로필 미리보기 이미지 URL
            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;

            // 프로필 사진 URL
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}