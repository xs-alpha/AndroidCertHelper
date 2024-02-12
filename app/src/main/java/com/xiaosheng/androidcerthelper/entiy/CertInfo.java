package com.xiaosheng.androidcerthelper.entiy;

public class CertInfo {
    private String title;
    private String expireTm;

    private String issuer;

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    private boolean isExpired;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpireTm() {
        return expireTm;
    }

    public void setExpireTm(String expireTm) {
        this.expireTm = expireTm;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
