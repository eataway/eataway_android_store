package com.administrator.administrator.eataway.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/20.
 */

public class UpShopBean {

    /**
     * status : 1
     * msg : {"content":"","gotime":" ","long":"0","maxprice":"-1","maxlong":"-1","lkmoney":"3","shophead":"","shopphoto":"","shopname":"wolegequ","state":"2","coordinate":"116.475558,39.936625","detailed_address":"111111111111111111","mobile":"15230868185","allmoney":0,"nums":0}
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
         * content :
         * gotime :
         * long : 0
         * maxprice : -1
         * maxlong : -1
         * lkmoney : 3
         * shophead :
         * shopphoto :
         * shopname : wolegequ
         * state : 2
         * coordinate : 116.475558,39.936625
         * detailed_address : 111111111111111111
         * mobile : 15230868185
         * allmoney : 0
         * nums : 0
         */

        private String content;
        private String gotime;
        @SerializedName("long")
        private String longX;
        private String maxprice;
        private String maxlong;
        private String lkmoney;
        private String shophead;
        private String shopphoto;
        private String shopname;
        private String state;
        private String coordinate;
        private String detailed_address;
        private String mobile;
        private double allmoney;
        private int nums;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGotime() {
            return gotime;
        }

        public void setGotime(String gotime) {
            this.gotime = gotime;
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

        public String getLkmoney() {
            return lkmoney;
        }

        public void setLkmoney(String lkmoney) {
            this.lkmoney = lkmoney;
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

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(int allmoney) {
            this.allmoney = allmoney;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }
    }
}
