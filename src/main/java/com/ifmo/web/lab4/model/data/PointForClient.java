package com.ifmo.web.lab4.model.data;

import lombok.Data;

@Data
public class PointForClient {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;

    public PointForClient(Double x, Double y, Double r, boolean b) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = b;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
