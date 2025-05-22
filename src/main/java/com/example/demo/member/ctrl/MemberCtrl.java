package com.example.demo.member.ctrl;

import com.example.demo.member.ctrl.req.AdminKey;
import com.example.demo.member.ctrl.req.RegisterReq;
import com.example.demo.member.ctrl.res.MemberRowIdRes;
import com.example.demo.member.sv.MemberSv;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/members")
public class MemberCtrl {

    private final MemberSv memberSv;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/join")
    public MemberRowIdRes register(@RequestBody @Valid RegisterReq registerReq) {
        Long result = memberSv.registerMember(registerReq.accessToken());
        MemberRowIdRes memberRowIdRes = new MemberRowIdRes(result);
        return memberRowIdRes;
    }

    @GetMapping("/v1/{accessToken}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Member 로그인 API")
    public MemberRowIdRes login(
            @PathVariable("accessToken")
            @Parameter(
                    name = "accessToken",
                    description = "카카오 accessToken",
                    required = true)
            String accessToken) {

        return new MemberRowIdRes(memberSv.login(accessToken));
    }

    @GetMapping("/v1/accessToken/{idCode}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "(백엔드용)accessToken을 받아오기 위한 API")
    public String getAccessToken(
            @PathVariable("idCode")
            @Parameter(name = "idCode", description = "카카오에서 발급 받은 idCode", required = true)
            String idCode) {
        return memberSv.getAccessToken(idCode);
    }

    @DeleteMapping("/v1/{memberRowId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "(Admin)회원 탈퇴를 위한 API")
    public void unjoin(
            @PathVariable("memberRowId")
            @Parameter(name = "memberRowId", description = "사용자 식별 번호", required = true)
            Long memberRowId,
            @RequestBody
            AdminKey adminKey
    ) {
        memberSv.deleteMemberByMemberId(memberRowId, adminKey);
    }


}
