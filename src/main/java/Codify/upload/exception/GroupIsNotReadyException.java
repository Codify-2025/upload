package Codify.upload.exception;

import Codify.upload.exception.baseException.BaseException;

public class GroupIsNotReadyException extends BaseException {
    public GroupIsNotReadyException() {
        super(ErrorCode.GROUP_IS_NOT_READY);
    }
}
