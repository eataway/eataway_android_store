package com.administrator.administrator.eataway.utils;

/**
 * Created by Administrator on 2017/7/13.
 */

public interface Contants {
    String URL = "http://wm.sawfree.com/index.php/server/";
//    String URL = "http://192.168.2.126/tp/index.php/server/";
//    String URL = "http://108.61.96.39/index.php/server/";
//    String URL = "http://www21mxwx.cn/tp/index.php/server/";

    //修改菜单顺序
    String URL_CHANGE_MENU_LIST = URL + "shop/movecategory";

    //用户账号密码注册
    String URL_RELEASE = URL + "user/register";
    //手机号验证
    String URL_VERL_PHONE = URL + "user/veri_mobile";
    //用户登陆
    String URL_LOGIN = URL + "user/login";
    //商铺信息
    String URL_SHOPMSG = URL + "user/shopmesa";
    //添加认证商铺
    String URL_ADDSHOP = URL + "user/addshop";
    //退出加盟
    String URL_OUT = URL + "user/out";
    //修改营业状态
    String URL_EDITSTATE = URL + "user/editstate";
    //修改商铺头像、简介、名称
    String URL_EDITJIANJIE = URL + "user/addjianjie";
    //修改商铺主图
    String URL_EDITZHUTU = URL + "user/editzhutu";
    //修改商铺营业时间
    String URL_EDITTIME = URL + "user/edittime";
    //修改免配送最小金额
    String URL_EDITMINPRICE = URL + "user/editprice";
    //删除最小金额
    String URL_BACKMINPRICE = URL + "shop/backprice";
    //修改免配送最大距离 //修改，变为多少千米多少钱的“多少千米”
    String URL_EDITMAXDISTANCE = URL + "user/editmaxlong";
    //新增:多少千米多少钱的“多少钱”
    String URL_EDITMAXMONEY = URL + "user/editmaxmoney";
    //删除最大距离
    String URL_BACKMAXDISTANCE = URL + "shop/backlong";
    //修改距离单价
    String URL_EDITLKPRICE = URL + "user/editlkmoney";
    //新增：修改商家最低起送
    String URL_EDITMINMONEY = URL + "shop/editlmoney";
    //修改商家配送距离
    String URL_EDITLONG = URL + "user/editlong";
    //修改商家手机
    String URL_EDITPHONE = URL + "shop/editmobile";
    //Google地图搜索
    String URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    //修改商家地址
    String URL_EDITADDRESS = URL + "shop/editaddress";
    //问题反馈
    String URL_SHOP_BACK = URL + "evaluate/shopback";
    //退出登录
    String URL_LOGOUT = URL + "user/logout";
    //忘记密码
    String URL_RESETPWD = URL + "user/replay_password";
    //收入明细
    String URL_MINGXI = URL + "order/mingxi";
    //修改菜品
    String URL_EDITDISH = URL + "shop/editgoods";
    String URL_NORDERLIST=URL+"order/norderlist";
    String URL_YOUDER_LIST=URL+"order/yorderlist";
    String URL_QUEREN=URL+"order/queren";
    String URL_SONGDA=URL+"order/songda";
    String URL_BACKORDER=URL+"order/backorder";
    String URL_MENUGOODS=URL+"shop/menugoods";
    String URL_CATEGORY=URL+"shop/categorylist";
    String URL_ADDMUNU=URL+"shop/addcategory";
    String URL_EDITCATEGORY=URL+"shop/editcategory";
    String URL_DELETECATEGORY=URL+"shop/deletecategory";
    String URL_ADD_GOODS=URL+"shop/addgoods";
    String URL_GOODSDETAILS=URL+"shop/menudetail";
    String URL_DELETEGOODS=URL+"shop/deletegoods";
    String URL_EDITFLAGA=URL+"shop/editflaga";
    String URL_EDITFLAGB=URL+"shop/editflagb";
    String URL_COMMENT=URL+"evaluate/pingjialist";
    String URL_BACKPINGJIA=URL+"evaluate/backpingjia";

    String URL_CATEGORYS=URL+"shop/categorylists";

    //获取商家营业时间
    String URL_GET_BUSSINESS_TIME = URL + "shop/lookyingye";
}
