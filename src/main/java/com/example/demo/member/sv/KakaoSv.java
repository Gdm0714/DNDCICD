package com.example.demo.member.sv;

import com.example.demo.member.exception.KakaoException;
import com.example.demo.member.kakao.KakaoTokenInfo;
import com.example.demo.member.kakao.KakaoUserInfo;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
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
                                clientResponse -> Mono.error(new KakaoException.INCORRECT_ACCESSTOKEN()))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> Mono.error(new KakaoException.KAKAO_SERVER_ERROR()))
                        .bodyToMono(KakaoUserInfo.class)
                        .block();

        return userInfo;
    }

    public String getKakaoAccessToken(String authorizationCode) {
        log.warn("KAKAO_CLIENT_ID : {}", KAKAO_CLIENT_ID);
        log.warn("KAKAO_REDIRECT_URL : {}", KAKAO_REDIRECT_URL);
        log.warn("authorizationCode : {}", authorizationCode);
        KakaoTokenInfo kakaoTokenInfo =
                WebClient.create("https://kauth.kakao.com")
                        .post()
                        .uri("/oauth/token")
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .body(
                                BodyInserters.fromFormData("grant_type", "authorization_code")
                                        .with("client_id", KAKAO_CLIENT_ID)
                                        .with("redirect_uri", KAKAO_REDIRECT_URL)
                                        .with("code", authorizationCode)
                        )
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse ->
                                        Mono.error(new KakaoException.INCORRECT_ID_CODE()))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse ->
                                        Mono.error(new KakaoException.KAKAO_SERVER_ERROR()))
                        .bodyToMono(KakaoTokenInfo.class)
                        .block();
        log.warn("kakaoTokenInfo : {}", kakaoTokenInfo);
        return kakaoTokenInfo.getAccessToken();
    }
}
