package com.example.demo.member.sv;

import com.example.demo.member.ctrl.req.AdminKey;
import com.example.demo.member.dao.MemberRepository;
import com.example.demo.member.dao.entity.MemberEntity;
import com.example.demo.member.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSv {
    @Value("${admin.key}")
    private String adminKey;

    private final MemberRepository memberRepository;

    public Long registerMember(String accessToken) {
        KakaoUserInfo kakaoUserInfo = memberRepository.getKakaoUserInfo(accessToken);
        if(idVertifiedUser(kakaoUserInfo)) return 0L;
        if(memberRepository.findByNickname(kakaoUserInfo.kakaoAccount.profile.getNickname()).isPresent()) return 0L;

        return memberRepository.save(kakaoUserInfo);
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

    public void deleteMemberByMemberId(Long memberRowId, AdminKey adminKey) {
        if (this.adminKey.equals(adminKey.getAdminKey())) memberRepository.deleteMemberByMemberId(memberRowId);
        else log.error("어드민 키가 올바르지 않습니다. 입력된 adminKey : {}", adminKey.getAdminKey());
    }

    private Boolean idVertifiedUser(KakaoUserInfo kakaoUserInfo) {
        if(kakaoUserInfo.getIdToken() == 0L) return false;
        if(memberRepository.findByIdToken(kakaoUserInfo.getIdToken()).isPresent()) return false;
        return false;
    }


}
