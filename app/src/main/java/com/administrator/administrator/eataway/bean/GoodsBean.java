package com.administrator.administrator.eataway.bean;

/**
 * Created by Administrator on 2017/7/21.
 */

public class GoodsBean {

    /**
     * status : 1
     * msg : {"goodsname":"一我是","goodsphoto":"http://wm.sawfree.com/uploads/4da14d5f1a96ec476784dbf3cce91631.jpg","cid":"61","goodsprice":"45537.00","flag":"1","goodscontent":"想去就去","num":0,"fenlei":"XP我搜"}
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
         * goodsname : 一我是
         * goodsphoto : http://wm.sawfree.com/uploads/4da14d5f1a96ec476784dbf3cce91631.jpg
         * cid : 61
         * goodsprice : 45537.00
         * flag : 1
         * goodscontent : 想去就去
         * num : 0
         * fenlei : XP我搜
         */

        private String goodsname;
        private String goodsphoto;
        private String cid;
        private String goodsprice;
        private String flag;
        private String goodscontent;
        private int num;
        private String fenlei;

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getGoodsphoto() {
            return goodsphoto;
        }

        public void setGoodsphoto(String goodsphoto) {
            this.goodsphoto = goodsphoto;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getGoodsprice() {
            return goodsprice;
        }

        public void setGoodsprice(String goodsprice) {
            this.goodsprice = goodsprice;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getGoodscontent() {
            return goodscontent;
        }

        public void setGoodscontent(String goodscontent) {
            this.goodscontent = goodscontent;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getFenlei() {
            return fenlei;
        }

        public void setFenlei(String fenlei) {
            this.fenlei = fenlei;
        }
    }
}
