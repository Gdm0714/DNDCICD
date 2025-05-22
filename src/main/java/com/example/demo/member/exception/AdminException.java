package com.example.demo.member.exception;

import com.example.demo.common.BusinessException;
import com.example.demo.common.ErrorCode;

public class AdminException extends BusinessException {
  public AdminException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static class ADMINKEY_UNAUTHORIZED extends AdminException {
    public ADMINKEY_UNAUTHORIZED() {
      super(AdminErrorCode.ADMINKEY_UNAUTHORIZED);
    }
  }
}
