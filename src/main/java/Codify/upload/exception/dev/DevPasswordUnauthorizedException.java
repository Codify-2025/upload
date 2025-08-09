package Codify.upload.exception.dev;

import Codify.upload.exception.ErrorCode;
import Codify.upload.exception.baseException.BaseException;

public class DevPasswordUnauthorizedException extends BaseException {

    public DevPasswordUnauthorizedException() {
        super(ErrorCode.INVALID_DEV_PASSWORD);
    }
}
