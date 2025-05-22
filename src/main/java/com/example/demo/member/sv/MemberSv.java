package com.example.demo.member.sv;

import com.example.demo.member.dao.MemberRepository;
import com.example.demo.member.dao.entity.MemberEntity;
import com.example.demo.member.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSv {
    private final MemberRepository memberRepository;

    public Long registerMember(String accessToken) {
        KakaoUserInfo kakaoUserInfo = memberRepository.getKakaoUserInfo(accessToken);
        if(idVertifiedUser(kakaoUserInfo)) return 0L;
        if(memberRepository.findByNickname(kakaoUserInfo.kakaoAccount.profile.getNickname()).isPresent()) return 0L;

        memberRepository.save(kakaoUserInfo);
        return memberRepository.findByIdToken(kakaoUserInfo.getIdToken()).get().getId();
    }

    public Long login(String accessToken) {
        KakaoUserInfo kakaoUserInfo = memberRepository.getKakaoUserInfo(accessToken);
        if(idVertifiedUser(kakaoUserInfo)) return 0L;
        MemberEntity memberEntity = memberRepository.findByIdToken(kakaoUserInfo.getIdToken()).orElseThrow(IllegalAccessError::new);
        return memberEntity.getId();
    }

    public String getAccessToken(String idCode) {
        String accessToken = memberRepository.getKakaoAccessToken(idCode);
        log.warn("accessToken : {}", accessToken);
        return accessToken;
    }

    private Boolean idVertifiedUser(KakaoUserInfo kakaoUserInfo) {
        if(kakaoUserInfo.getIdToken() == 0L) return false;
        if(memberRepository.findByIdToken(kakaoUserInfo.getIdToken()).isPresent()) return false;
        return false;
    }


}
