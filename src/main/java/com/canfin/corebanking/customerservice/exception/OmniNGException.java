package com.canfin.corebanking.customerservice.exception;

public class OmniNGException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    private Throwable t;

    public OmniNGException() {
        super();
    }

    public OmniNGException(String error, Throwable t) {
        super(error, t);
        this.error = error;
        this.t = t;
    }

    public OmniNGException(String error) {
        super(error);
        this.error = error;
    }


}
