package com.administrator.administrator.eataway.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CommentListBean {

    /**
     * status : 1
     * msg : [{"uid":"22","username":"EA_15733116318","head_photo":"","eid":"19","pingjia":"3","content":"味道不错","photo1":"http://wm.sawfree.com/uploads/4ce0b25b26045a28d5dcb57b4988b1cb.jpg","photo2":"http://wm.sawfree.com/uploads/ef4ccab4d6825b89d5f20ff25f783690.jpg","state":"2","addtime":"2017-07-21 11:52:54","backpingjia":"vvvvvv"}]
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
         * uid : 22
         * username : EA_15733116318
         * head_photo :
         * eid : 19
         * pingjia : 3
         * content : 味道不错
         * photo1 : http://wm.sawfree.com/uploads/4ce0b25b26045a28d5dcb57b4988b1cb.jpg
         * photo2 : http://wm.sawfree.com/uploads/ef4ccab4d6825b89d5f20ff25f783690.jpg
         * state : 2
         * addtime : 2017-07-21 11:52:54
         * backpingjia : vvvvvv
         */

        private String uid;
        private String username;
        private String head_photo;
        private String eid;
        private String pingjia;
        private String content;
        private String photo1;
        private String photo2;
        private String state;
        private String addtime;
        private String backpingjia;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getPingjia() {
            return pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhoto1() {
            return photo1;
        }

        public void setPhoto1(String photo1) {
            this.photo1 = photo1;
        }

        public String getPhoto2() {
            return photo2;
        }

        public void setPhoto2(String photo2) {
            this.photo2 = photo2;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getBackpingjia() {
            return backpingjia;
        }

        public void setBackpingjia(String backpingjia) {
            this.backpingjia = backpingjia;
        }
    }
}
