package Codify.upload.exception.assignment;

import Codify.upload.exception.ErrorCode;
import Codify.upload.exception.baseException.BaseException;

public class AssignmentAccessDeniedException extends BaseException {
    public AssignmentAccessDeniedException() {
        super(ErrorCode.ASSIGNMENT_ACCESS_DENIED);
    }
}
