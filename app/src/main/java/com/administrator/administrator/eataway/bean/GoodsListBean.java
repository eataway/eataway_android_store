package com.administrator.administrator.eataway.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class GoodsListBean {

    /**
     * status : 1
     * msg : [{"goodsid":"24","goodsname":"他咯","goodsphoto":"http://wm.sawfree.com/uploads/520a4f06ab66f990d438ecdfefa252d3.jpg","goodsprice":"128.00","goodscontent":"轮到我们的人们的人们班","cid":"47","shopid":"14","flag":"1","addtime":"2017-07-19 11:58:21"}]
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
         * goodsid : 24
         * goodsname : 他咯
         * goodsphoto : http://wm.sawfree.com/uploads/520a4f06ab66f990d438ecdfefa252d3.jpg
         * goodsprice : 128.00
         * goodscontent : 轮到我们的人们的人们班
         * cid : 47
         * shopid : 14
         * flag : 1
         * addtime : 2017-07-19 11:58:21
         */

        private String goodsid;
        private String goodsname;
        private String goodsphoto;
        private String goodsprice;
        private String goodscontent;
        private String cid;
        private String shopid;
        private String flag;
        private String addtime;

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

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

        public String getGoodsprice() {
            return goodsprice;
        }

        public void setGoodsprice(String goodsprice) {
            this.goodsprice = goodsprice;
        }

        public String getGoodscontent() {
            return goodscontent;
        }

        public void setGoodscontent(String goodscontent) {
            this.goodscontent = goodscontent;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
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
