package com.thxx.ydzrzy.entity;

import com.esri.arcgisruntime.geometry.Point;

import java.util.Map;

public class PointTableFeature {

    private Map<String, Object> attributes;
    private Point point;

    public PointTableFeature(){}

    public PointTableFeature(Map<String, Object> attributes, Point point) {
        this.attributes = attributes;
        this.point = point;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
