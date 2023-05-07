package com.example.chatapplication.custom.exception;


import com.example.chatapplication.common.Category;

import java.util.HashMap;
import java.util.Map;

public class GeneralException extends RuntimeException {
    protected Integer status;
    protected String code;
    protected Map<String, String> messageParams;
    protected Throwable source;

    public GeneralException(String code) {
        this.code = Category.ErrorCodeEnum.INTERNAL_SERVER_ERROR.name();
        this.code = code;
    }

    public GeneralException(String code, Map<String, String> messageParams) {
        this.code = Category.ErrorCodeEnum.INTERNAL_SERVER_ERROR.name();
        this.code = code;
        this.messageParams = messageParams;
    }

    public GeneralException(String code, String errorContent) {
        this.code = Category.ErrorCodeEnum.INTERNAL_SERVER_ERROR.name();
        this.code = code;
        Map<String, String> messageParams = new HashMap();
        messageParams.put("content", errorContent);
        this.messageParams = messageParams;
    }


    public String getMessage() {
        return this.code;
    }

    public GeneralException source(Throwable source) {
        this.source = source;
        return this;
    }

    public synchronized Throwable getCause() {
        return this.source;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GeneralException)) {
            return false;
        } else {
            GeneralException other = (GeneralException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label49: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label49;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label49;
                    }

                    return false;
                }

                Object this$messageParams = this.getMessageParams();
                Object other$messageParams = other.getMessageParams();
                if (this$messageParams == null) {
                    if (other$messageParams != null) {
                        return false;
                    }
                } else if (!this$messageParams.equals(other$messageParams)) {
                    return false;
                }

                Object this$source = this.getSource();
                Object other$source = other.getSource();
                if (this$source == null) {
                    if (other$source != null) {
                        return false;
                    }
                } else if (!this$source.equals(other$source)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GeneralException;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $messageParams = this.getMessageParams();
        result = result * 59 + ($messageParams == null ? 43 : $messageParams.hashCode());
        Object $source = this.getSource();
        result = result * 59 + ($source == null ? 43 : $source.hashCode());
        return result;
    }

    public String getCode() {
        return this.code;
    }

    public Map<String, String> getMessageParams() {
        return this.messageParams;
    }

    public Throwable getSource() {
        return this.source;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessageParams(Map<String, String> messageParams) {
        this.messageParams = messageParams;
    }

    public void setSource(Throwable source) {
        this.source = source;
    }

    public String toString() {
        String var10000 = this.getCode();
        return "GeneralException(code=" + var10000 + ", messageParams=" + this.getMessageParams() + ", source=" + this.getSource() + ")";
    }


}

