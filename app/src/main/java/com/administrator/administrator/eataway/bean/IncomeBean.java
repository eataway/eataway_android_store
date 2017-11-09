package com.administrator.administrator.eataway.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class IncomeBean {

    /**
     * status : 1
     * msg : [{"id":"482","shopid":"39","orderid":"1501420740","money":"76","addtime":"2017-07-30 21:19:00"},{"id":"481","shopid":"39","orderid":"1501420683","money":"76","addtime":"2017-07-30 21:18:03"},{"id":"480","shopid":"39","orderid":"1501420678","money":"76","addtime":"2017-07-30 21:17:58"},{"id":"479","shopid":"39","orderid":"1501420653","money":"76","addtime":"2017-07-30 21:17:33"},{"id":"478","shopid":"39","orderid":"1501420633","money":"48","addtime":"2017-07-30 21:17:13"},{"id":"477","shopid":"39","orderid":"1501420520","money":"48","addtime":"2017-07-30 21:15:20"},{"id":"476","shopid":"39","orderid":"1501420515","money":"48","addtime":"2017-07-30 21:15:15"},{"id":"475","shopid":"39","orderid":"1501415672","money":"196","addtime":"2017-07-30 19:54:32"}]
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
         * id : 482
         * shopid : 39
         * orderid : 1501420740
         * money : 76
         * addtime : 2017-07-30 21:19:00
         */

        private String id;
        private String shopid;
        private String orderid;
        private String money;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
