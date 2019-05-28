package com.api.entity.jwt;

import java.io.Serializable;

public class JwtObj implements Serializable {
    private static final long serialVersionUID = 1691489461127248816L;
    private String taxNo;
    private String cusName;
    private String machineNo;
    private long expires;

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
