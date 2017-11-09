package com.administrator.administrator.eataway.comm;

import java.io.Serializable;

/**
 * Project: EatAway1
 * Author: ZhangHao
 * Date:    2017/6/22
 */

public class Login implements Serializable{
    private String shopName = "";
    private String shopId = "";
    private String token = "";
    private String head = "";
    private String pic = "";
    private String phone = "";
    private String address = "";
    private String introduce = "";
    private String longitude = "";
    private String latitude = "";
    private String location_text = "";
    private String time = "";
    private double distance;
    private int certification = 0;//认证状态：1:营业中 2：未营业 3:认证中 4、已退出

    //配送规则
    private double maxPrice = -1;
    private double maxLong = -1;
    private double lkMoney = 0;
    private double maxMoney = 0;
    private double lmoney = 0;

    public double getLmoney() {
        return lmoney;
    }

    public void setLmoney(double lmoney) {
        this.lmoney = lmoney;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public double getDistance() {
        return distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMaxLong() {
        return maxLong;
    }

    public void setMaxLong(double maxLong) {
        this.maxLong = maxLong;
    }

    public double getLkMoney() {
        return lkMoney;
    }

    public void setLkMoney(double lkMoney) {
        this.lkMoney = lkMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
