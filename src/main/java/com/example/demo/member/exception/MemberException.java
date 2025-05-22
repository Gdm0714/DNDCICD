package com.example.demo.member.exception;

import com.example.demo.common.BusinessException;
import com.example.demo.common.ErrorCode;

public class MemberException extends BusinessException {
  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static class MEMBER_NOT_FOUND extends MemberException {
    public MEMBER_NOT_FOUND() {
      super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
  }

  public static class MEMBER_NICKNAME_CONFLICT extends MemberException {
    public MEMBER_NICKNAME_CONFLICT() {
      super(MemberErrorCode.MEMBER_NICKNAME_CONFLICT);
    }
  }
}
