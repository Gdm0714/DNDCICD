package com.example.demo.member.sv;

import com.example.demo.member.kakao.KakaoTokenInfo;
import com.example.demo.member.kakao.KakaoUserInfo;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoSv {
    private final String BEARER = "Bearer";
    private final String AUTHORIZATION_CODE = "authorization_code";

    @Value("${kakao.kakao-client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.kakao-redirect-url}")
    private String KAKAO_REDIRECT_URL;

    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        KakaoUserInfo userInfo =
                WebClient.create("https://kapi.kakao.com")
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder.scheme("https").path("/v2/user/me").build(true))
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                BEARER + " " + accessToken) // access token 인가
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse -> Mono.error(new IllegalAccessException("카카오 엑세스 토큰이 올바르지 않습니다.")))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> Mono.error(new IllegalAccessException("카카오 서버 오류입니다.")))
                        .bodyToMono(KakaoUserInfo.class)
                        .block();

        return userInfo;
    }

    public String getKakaoAccessToken(String authorizationCode) {
        log.warn("KAKAO_CLIENT_ID : {}", KAKAO_CLIENT_ID);
        log.warn("KAKAO_REDIRECT_URL : {}", KAKAO_REDIRECT_URL);
        KakaoTokenInfo kakaoTokenInfo =
                WebClient.create("https://kauth.kakao.com")
                        .post()
                        .uri(
                                uriBuilder ->
                                        uriBuilder
                                                .scheme("https")
                                                .path("/oauth/token")
                                                .queryParam("grant_type", AUTHORIZATION_CODE)
                                                .queryParam("client_id", KAKAO_CLIENT_ID)
                                                .queryParam("redirect_uri", KAKAO_REDIRECT_URL)
                                                .queryParam("code", authorizationCode)
                                                .build(true))
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse ->
                                        Mono.error(new RuntimeException("카카오 idCode 값이 유효하지 않습니다.")))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse ->
                                        Mono.error(new RuntimeException("카카오 서버 오류입니다.")))
                        .bodyToMono(KakaoTokenInfo.class)
                        .block();
        return kakaoTokenInfo.getAccessToken();
    }
}
