package com.administrator.administrator.eataway.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class YorderListBean {

    /**
     * status : 1
     * msg : [{"orderid":"1499499440","uid":"15","shopid":"14","money":"5.00","allprice":"35.00","cometime":"立即送达","endtime":null,"name":"李","sex":"1","phone":"18633045435","address":"Denhill Lodge 202","beizhu":"","statu":"2","states":"1","state":"4","juli":"31","pay":"1","todaynums":"0","addtime":"2017-07-08 15:37:20","goods":[{"num":"1","goodsname":"鸭腿","goodsprice":"30.00"}]},{"orderid":"1499409165","uid":"15","shopid":"14","money":"5.00","allprice":"405.00","cometime":"立即送达","endtime":"0000-00-00 00:00:00","name":"李","sex":"1","phone":"18633045435","address":"Denhill Lodge 202","beizhu":"啊啊啊","statu":"1","states":"2","state":"5","juli":"1","pay":"1","todaynums":"0","addtime":"2017-07-07 14:32:41","goods":[]},{"orderid":"14994091610","uid":"16","shopid":"14","money":"5.00","allprice":"405.00","cometime":"立即送达","endtime":"0000-00-00 00:00:00","name":"李","sex":"1","phone":"18633045435","address":"Denhill Lodge 202","beizhu":"啊啊啊","statu":"1","states":"2","state":"5","juli":"2","pay":"2","todaynums":"0","addtime":"2017-07-07 14:32:41","goods":[]},{"orderid":"1499419750","uid":"14","shopid":"14","money":"5.00","allprice":"281.00","cometime":"立即送达","endtime":null,"name":"李","sex":"1","phone":"18633045435","address":"Denhill Lodge 202","beizhu":"辣","statu":"2","states":"1","state":"4","juli":"12","pay":"1","todaynums":"0","addtime":"0000-00-00 00:00:00","goods":[{"num":"2","goodsname":"烤全鱼","goodsprice":"88.00"},{"num":"1","goodsname":"鲤鱼","goodsprice":"100.00"}]}]
     */

    private int status;
    private List<MsgBean> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * orderid : 1499499440
         * uid : 15
         * shopid : 14
         * money : 5.00
         * allprice : 35.00
         * cometime : 立即送达
         * endtime : null
         * name : 李
         * sex : 1
         * phone : 18633045435
         * address : Denhill Lodge 202
         * beizhu :
         * statu : 2
         * states : 1
         * state : 4
         * juli : 31
         * pay : 1
         * todaynums : 0
         * addtime : 2017-07-08 15:37:20
         * goods : [{"num":"1","goodsname":"鸭腿","goodsprice":"30.00"}]
         */

        private String orderid;
        private String uid;
        private String shopid;
        private String money;
        private String allprice;
        private String cometime;
        private Object endtime;
        private String name;
        private String sex;
        private String phone;
        private String address;
        private String beizhu;
        private String statu;
        private String states;
        private String state;
        private String juli;
        private String pay;
        private String todaynums;
        private String addtime;
        private List<GoodsBean> goods=new ArrayList<>();

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAllprice() {
            return allprice;
        }

        public void setAllprice(String allprice) {
            this.allprice = allprice;
        }

        public String getCometime() {
            return cometime;
        }

        public void setCometime(String cometime) {
            this.cometime = cometime;
        }

        public Object getEndtime() {
            return endtime;
        }

        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getStatu() {
            return statu;
        }

        public void setStatu(String statu) {
            this.statu = statu;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getTodaynums() {
            return todaynums;
        }

        public void setTodaynums(String todaynums) {
            this.todaynums = todaynums;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * num : 1
             * goodsname : 鸭腿
             * goodsprice : 30.00
             */

            private String num;
            private String goodsname;
            private String goodsprice;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getGoodsname() {
                return goodsname;
            }

            public void setGoodsname(String goodsname) {
                this.goodsname = goodsname;
            }

            public String getGoodsprice() {
                return goodsprice;
            }

            public void setGoodsprice(String goodsprice) {
                this.goodsprice = goodsprice;
            }
        }
    }
}
