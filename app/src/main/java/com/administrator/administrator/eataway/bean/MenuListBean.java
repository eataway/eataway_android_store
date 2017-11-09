package com.administrator.administrator.eataway.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MenuListBean implements Serializable{


    /**
     * status : 1
     * msg : [{"cid":"364","cname":"呃呃呃","num":0,"shopid":"45","up":"-1","end":"367","addtime":"2017-11-07 14:24:11"},{"cid":"367","cname":"新菜单","num":1,"shopid":"45","up":"364","end":"368","addtime":"2017-11-07 17:05:12"},{"cid":"368","cname":"新的","num":0,"shopid":"45","up":"367","end":"366","addtime":"2017-11-07 17:33:52"},{"cid":"366","cname":"阿卡丽","num":0,"shopid":"45","up":"368","end":"369","addtime":"2017-11-07 14:24:35"},{"cid":"369","cname":"嗯嗯","num":0,"shopid":"45","up":"366","end":"370","addtime":"2017-11-07 17:36:28"},{"cid":"370","cname":"佛佛","num":0,"shopid":"45","up":"369","end":"0","addtime":"2017-11-07 17:37:18"}]
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
         * cid : 364
         * cname : 呃呃呃
         * num : 0
         * shopid : 45
         * up : -1
         * end : 367
         * addtime : 2017-11-07 14:24:11
         */

        private String cid;
        private String cname;
        private int num;
        private String shopid;
        private String up;
        private String end;
        private String addtime;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
