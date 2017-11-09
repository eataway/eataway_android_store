package com.administrator.administrator.eataway.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by local123 on 2017/11/9.
 */

public class ShopTimeBean {

    /**
     * status : 1
     * msg : {"shopid":"45","shopname":"四川辣子鸡","mobile":"15230868184","content":"","shophead":"uploads/2d37b2e92b5ca8f9d11df90688c98e77.jpg","shopphoto":"uploads/05eab6d8954a842c2c611e4cf2e0dd7a.jpg","state":"2","states":"1","detailed_address":"阿德莱德大学","coordinate":"138.6062277,-34.92060300000001","gotime":" ","monday":"10:58-10:58","tuesday":"","wednesday":"","thursday":"","friday":"","saturday":"","sunday":"","lmoney":"12","long":"20000","maxprice":"-1","maxlong":"-1","maxmoney":"0","lkmoney":"4.5","bkm":"","flag":"1","addtime":"2017-10-12 10:43:33"}
     */

    private int status;
    private MsgBean msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * shopid : 45
         * shopname : 四川辣子鸡
         * mobile : 15230868184
         * content :
         * shophead : uploads/2d37b2e92b5ca8f9d11df90688c98e77.jpg
         * shopphoto : uploads/05eab6d8954a842c2c611e4cf2e0dd7a.jpg
         * state : 2
         * states : 1
         * detailed_address : 阿德莱德大学
         * coordinate : 138.6062277,-34.92060300000001
         * gotime :
         * monday : 10:58-10:58
         * tuesday :
         * wednesday :
         * thursday :
         * friday :
         * saturday :
         * sunday :
         * lmoney : 12
         * long : 20000
         * maxprice : -1
         * maxlong : -1
         * maxmoney : 0
         * lkmoney : 4.5
         * bkm :
         * flag : 1
         * addtime : 2017-10-12 10:43:33
         */

        private String shopid;
        private String shopname;
        private String mobile;
        private String content;
        private String shophead;
        private String shopphoto;
        private String state;
        private String states;
        private String detailed_address;
        private String coordinate;
        private String gotime;
        private String monday;
        private String tuesday;
        private String wednesday;
        private String thursday;
        private String friday;
        private String saturday;
        private String sunday;
        private String lmoney;
        @SerializedName("long")
        private String longX;
        private String maxprice;
        private String maxlong;
        private String maxmoney;
        private String lkmoney;
        private String bkm;
        private String flag;
        private String addtime;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getShophead() {
            return shophead;
        }

        public void setShophead(String shophead) {
            this.shophead = shophead;
        }

        public String getShopphoto() {
            return shopphoto;
        }

        public void setShopphoto(String shopphoto) {
            this.shopphoto = shopphoto;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getGotime() {
            return gotime;
        }

        public void setGotime(String gotime) {
            this.gotime = gotime;
        }

        public String getMonday() {
            return monday;
        }

        public void setMonday(String monday) {
            this.monday = monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        public String getFriday() {
            return friday;
        }

        public void setFriday(String friday) {
            this.friday = friday;
        }

        public String getSaturday() {
            return saturday;
        }

        public void setSaturday(String saturday) {
            this.saturday = saturday;
        }

        public String getSunday() {
            return sunday;
        }

        public void setSunday(String sunday) {
            this.sunday = sunday;
        }

        public String getLmoney() {
            return lmoney;
        }

        public void setLmoney(String lmoney) {
            this.lmoney = lmoney;
        }

        public String getLongX() {
            return longX;
        }

        public void setLongX(String longX) {
            this.longX = longX;
        }

        public String getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(String maxprice) {
            this.maxprice = maxprice;
        }

        public String getMaxlong() {
            return maxlong;
        }

        public void setMaxlong(String maxlong) {
            this.maxlong = maxlong;
        }

        public String getMaxmoney() {
            return maxmoney;
        }

        public void setMaxmoney(String maxmoney) {
            this.maxmoney = maxmoney;
        }

        public String getLkmoney() {
            return lkmoney;
        }

        public void setLkmoney(String lkmoney) {
            this.lkmoney = lkmoney;
        }

        public String getBkm() {
            return bkm;
        }

        public void setBkm(String bkm) {
            this.bkm = bkm;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
