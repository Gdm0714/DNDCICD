package com.example.demo.member.exception;

import com.example.demo.common.BusinessException;
import com.example.demo.common.ErrorCode;

public class KakaoException extends BusinessException {
  public KakaoException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static class INCORRECT_ID_CODE extends KakaoException {
    public INCORRECT_ID_CODE() {
      super(KakaoErrorCode.INCORRECT_ID_CODE);
    }
  }

  public static class KAKAO_SERVER_ERROR extends KakaoException {
    public KAKAO_SERVER_ERROR() {
      super(KakaoErrorCode.KAKAO_SERVER_ERROR);
    }
  }

  public static class INCORRECT_ACCESSTOKEN extends KakaoException {
    public INCORRECT_ACCESSTOKEN() {
      super(KakaoErrorCode.INCORRECT_ACCESSTOKEN);
    }
  }

  public static class KAKAO_MEMBER_NOT_FOUND extends KakaoException {
    public KAKAO_MEMBER_NOT_FOUND() {
      super(KakaoErrorCode.KAKAO_MEMBER_NOT_FOUND);
    }
  }
}
