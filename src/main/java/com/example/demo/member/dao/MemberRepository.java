package com.example.demo.member.dao;

import com.example.demo.member.dao.entity.MemberEntity;
import com.example.demo.member.kakao.KakaoTokenInfo;
import com.example.demo.member.kakao.KakaoUserInfo;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    public Long save(KakaoUserInfo kakaoUserInfo) {
        MemberEntity memberEntity = memberJpaRepository.save(MemberEntity.of(kakaoUserInfo.getIdToken().toString(), kakaoUserInfo.kakaoAccount.profile.getNickname()));
        return memberEntity.getId();
    }

    public Optional<MemberEntity> findByIdToken(Long idToken) {
        return memberJpaRepository.findByIdToken(idToken.toString());
    }

    public Optional<MemberEntity> findByNickname(String nickname) {
        return memberJpaRepository.findByNickname(nickname);
    }

    public void deleteMemberByMemberId(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}
