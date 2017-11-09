package com.administrator.administrator.eataway.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.ShopBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.ui.CustomDialog;
import com.administrator.administrator.eataway.ui.CustomFormatDialog;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.GlideUtils;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ImageLoaderUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.Call;

public class ShopDetailActivity extends BaseActivity {


    private static final int IMAGE_PICKER = 1000;
    @Bind(R.id.iv_activity_shop_detail_shop_pic)
    ImageView ivActivityShopDetailShopPic;
    @Bind(R.id.iv_activity_shop_detail_back)
    ImageView ivActivityShopDetailBack;
    @Bind(R.id.tv_activity_shop_detail_shop_change)
    TextView tvActivityShopDetailShopChange;
    @Bind(R.id.ll_activity_dish_detail_shop_change)
    LinearLayout llActivityDishDetailShopChange;
    @Bind(R.id.cimg_activity_shop_detail_shop_icon)
    CircleImageView cimgActivityShopDetailShopIcon;
    @Bind(R.id.tv_activity_shop_name)
    TextView tvActivityShopName;
    @Bind(R.id.tv_activity_shop_detail_introduce)
    TextView tvActivityShopDetailIntroduce;
    @Bind(R.id.rl_activity_shop_detail_info)
    RelativeLayout rlActivityShopDetailInfo;
    @Bind(R.id.iv_shop_detail_location)
    ImageView ivShopDetailLocation;
    @Bind(R.id.tv_shop_detail_location)
    TextView tvShopDetailLocation;
    @Bind(R.id.rl_activity_shop_detail_location)
    RelativeLayout rlActivityShopDetailLocation;
    @Bind(R.id.iv_shop_detail_phone)
    ImageView ivShopDetailPhone;
    @Bind(R.id.tv_shop_detail_phone)
    TextView tvShopDetailPhone;
    @Bind(R.id.rl_activity_shop_detail_phone)
    RelativeLayout rlActivityShopDetailPhone;
    @Bind(R.id.iv_shop_detail_time)
    ImageView ivShopDetailTime;
    @Bind(R.id.tv_shop_detail_time)
    TextView tvShopDetailTime;
    @Bind(R.id.rl_activity_shop_detail_time)
    RelativeLayout rlActivityShopDetailTime;
    @Bind(R.id.iv_shop_detail_moto)
    ImageView ivShopDetailMoto;
    @Bind(R.id.iv_shop_detail_more_moto)
    ImageView ivShopDetailMoreMoto;
    @Bind(R.id.tv_activity_shop_detail_delivery_distance)
    TextView tvActivityShopDetailDeliveryDistance;
    @Bind(R.id.rl_activity_shop_detail_distance)
    RelativeLayout rlActivityShopDetailDistance;
    @Bind(R.id.tv_shop_detail_fee)
    ImageView tvShopDetailFee;
    @Bind(R.id.iv_shop_detail_more_fee)
    ImageView ivShopDetailMoreFee;
    @Bind(R.id.rl_activity_shop_detail_fee)
    RelativeLayout rlActivityShopDetailFee;
    @Bind(R.id.tv_shop_detail_min_fee)
    ImageView tvShopDetailMinFee;
    @Bind(R.id.iv_shop_detail_more_min_fee)
    ImageView ivShopDetailMoreMinFee;
    @Bind(R.id.tv_activity_shop_detail_min_fee)
    TextView tvActivityShopDetailMinFee;
    @Bind(R.id.rl_activity_shop_detail_min_fee)
    RelativeLayout rlActivityShopDetailMinFee;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    private File picture;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private Button alertDialogButton;

    private Login login;
    private ShopBean bean;
    private CustomDialog dialog;
    private CustomFormatDialog formatDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDialog();
        initImagePicker();
        initDialog();
        StatusBarUtil.setTranslucentForImageView(this, 0, ivActivityShopDetailBack);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getLogin()!= null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_SHOPMSG) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(ShopDetailActivity.this, R.string.please_check_your_network_connection,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        JSONObject o = new JSONObject(response);
                        int status = o.getInt("status");
                        if (status == 1) {
                            bean = new Gson().fromJson(response, ShopBean.class);
                            GlideUtils.load(ShopDetailActivity.this, bean.getMsg().getShopphoto(), ivActivityShopDetailShopPic, GlideUtils.Shape.ShopPic);
                            GlideUtils.load(ShopDetailActivity.this, bean.getMsg().getShophead(), cimgActivityShopDetailShopIcon, GlideUtils.Shape.ShopIcon);
                            tvActivityShopName.setText(bean.getMsg().getShopname());
                            tvActivityShopDetailIntroduce.setText(bean.getMsg().getContent());
                            if (!" ".equals(bean.getMsg().getGotime())) {
                                tvShopDetailTime.setText(bean.getMsg().getGotime());
                            } else {
                                tvShopDetailTime.setText(getString(R.string.qing_she_zhi_ying_ye_shi_jian));
                            }
                            tvShopDetailPhone.setText(bean.getMsg().getMobile());
                            tvShopDetailLocation.setText(bean.getMsg().getDetailed_address());
                            tvActivityShopDetailDeliveryDistance.setText(bean.getMsg().getLongX() + "km");
                            tvActivityShopDetailMinFee.setText("$" + bean.getMsg().getLmoney());
                        } else if (status == 0) {
                            Toast.makeText(ShopDetailActivity.this, R.string.please_check_your_network_connection,
                                    Toast.LENGTH_SHORT).show();
                        } else if (status == 9) {
                            Toast.makeText(ShopDetailActivity.this, R.string.token_yan_zheng_shi_bai,
                                    Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    //关于ImagePicker
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setImageLoader(new ImageLoaderUtils());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(MyApplication.height / 2);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(MyApplication.width / 2);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        File cropCacheFolder = imagePicker.getCropCacheFolder(this);
        File[] files = cropCacheFolder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }
    }

    private void selectHeadImage() {
        List<File> pictures = new ArrayList<>();
        File cropCacheFolder = imagePicker.getCropCacheFolder(this);
        File[] ims = cropCacheFolder.listFiles();
        for (int i = 0; i < ims.length; i++) {
            if (ims[i].isFile() && (ims[i].getName().toLowerCase().endsWith(".jpg")
                    || ims[i].getName().toLowerCase().endsWith(".jpeg")
                    || ims[i].getName().toLowerCase().endsWith(".png"))) {
                pictures.add(ims[i]);
            }
        }
        if (pictures.size() == 0) {
            Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
            return;
        }
        Luban.compress(this, pictures).launch(new OnMultiCompressListener() {
            @Override
            public void onStart() {
                alertDialog.show();
                progressBar.setVisibility(View.VISIBLE);
                alertDialog.setMessage("正在上传，请稍后");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                alertDialogButton.setEnabled(false);
            }

            @Override
            public void onSuccess(List<File> fileList) {
                upMessage(fileList.get(fileList.size() - 1));
            }

            @Override
            public void onError(Throwable e) {
                alertDialog.setMessage("上传失败");
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }
        });

    }

    private void upMessage(final File pic) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITZHUTU) {
            @Override
            public void onError(Call call, Exception e, int id) {
                alertDialog.setMessage(getString(R.string.please_check_your_network_connection));
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        alertDialog.setMessage(getString(R.string.xiu_gai_shi_bai));
                        progressBar.setVisibility(View.GONE);
                    } else if (status == 9) {
                        alertDialog.setMessage(getString(R.string.token_yan_zheng_shi_bai));
                        progressBar.setVisibility(View.GONE);
                        MyApplication.saveLogin(null);
                    } else if (status == 1) {
                        alertDialog.setMessage(getString(R.string.xiu_gai_cheng_gong));
                        progressBar.setVisibility(View.GONE);
                        ivActivityShopDetailShopPic.setScaleType(ImageView.ScaleType.FIT_XY);
                        ivActivityShopDetailShopPic.setImageBitmap(BitmapFactory.decodeFile(images.get(images.size() - 1).path));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialogButton.setEnabled(true);
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.addFile("myFile", pic.getName(), pic);
        httpUtils.clicent();
    }

    private void initDialog() {
        builder = new AlertDialog.Builder(this);
        progressBar = new ProgressBar(this);
        builder.setTitle("上传到服务器");
        builder.setMessage("正在上传，请稍后");
        builder.setView(progressBar);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selectHeadImage();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.iv_activity_shop_detail_back, R.id.rl_activity_shop_detail_info, R.id.rl_activity_shop_detail_location, R.id.rl_activity_shop_detail_phone, R.id.rl_activity_shop_detail_time, R.id.rl_activity_shop_detail_distance, R.id.rl_activity_shop_detail_fee
            , R.id.ll_activity_dish_detail_shop_change, R.id.rl_activity_shop_detail_min_fee})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_shop_detail_min_fee:
                formatDialog = new CustomFormatDialog(this, R.style.MyDialog, CustomDialog.CANCLE);
                formatDialog.setEditText(0);
                formatDialog.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, final String et) {
                        if (type == CustomDialog.OK) {
                            login = MyApplication.getLogin();
                            if (et.length() != 0&& login != null && Double.parseDouble(et) != login.getLmoney()) {
                                HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITMINMONEY) {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                                        Toast.makeText(ShopDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        try {
                                            JSONObject o = new JSONObject(response);
                                            int status = o.getInt("status");
                                            if (status == 0) {
                                                formatDialog.dismiss();
                                                Toast.makeText(ShopDetailActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                                            } else if (status == 1) {
                                                formatDialog.dismiss();
                                                Toast.makeText(ShopDetailActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                                Login login = MyApplication.getLogin();
                                                login.setDistance(Double.parseDouble(et));
                                                MyApplication.saveLogin(login);
                                                tvActivityShopDetailMinFee.setText("$" + et);
                                            } else if (status == 9) {
                                                Toast.makeText(ShopDetailActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                                MyApplication.saveLogin(null);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
                                httpUtils.addParam("lmoney", et);
                                httpUtils.addParam("token", MyApplication.getLogin().getToken());
                                httpUtils.clicent();
                            } else {
                                formatDialog.dismiss();
                            }
                        } else if (type == CustomDialog.CANCLE) {
                            formatDialog.dismiss();
                        }
                    }
                });
                formatDialog.show();
                break;
            case R.id.iv_activity_shop_detail_back:
                finish();
                break;
            case R.id.rl_activity_shop_detail_info:
                Bundle b1 = new Bundle();
                if (bean != null) {
                    b1.putString("shoppic", bean.getMsg().getShophead());
                    b1.putString("shopname", bean.getMsg().getShopname());
                    b1.putString("shopcontent", bean.getMsg().getContent());
                    goToActivity(ShopPicConfigActivity.class, b1);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rl_activity_shop_detail_location:
                if (bean != null) {
                    goToActivity(ShopAddressConfigActivity.class);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rl_activity_shop_detail_phone:
                if (bean != null) {
                    goToActivity(ShopPhoneConfigActivity.class);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_activity_shop_detail_time:
                if (bean != null) {
                    goToActivity(TimeListShowActivity.class);
//                    Bundle b = new Bundle();
//                    if (!getString(R.string.qing_she_zhi_ying_ye_shi_jian).equals(tvShopDetailTime.getText())) {
//                        String[] split = tvShopDetailTime.getText().toString().split("-");
//                        b.putString("open", split[0]);
//                        b.putString("close", split[1]);
//                    } else {
//                        b.putString("open", "00:00");
//                        b.putString("close", "00:00");
//                    }
//                    goToActivity(ShopTimeConfigActivity.class, b);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

//                goToActivity(ShopTimeConfigActivity.class);
                break;
            case R.id.rl_activity_shop_detail_distance:
                formatDialog = new CustomFormatDialog(this, R.style.MyDialog, CustomFormatDialog.CANCLE);
                formatDialog.setEditText(1);
                formatDialog.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, final String et) {
                        if (type == CustomDialog.OK) {
                            if (et.length() != 0) {
                                HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITLONG) {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                                        Toast.makeText(ShopDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        try {
                                            JSONObject o = new JSONObject(response);
                                            int status = o.getInt("status");
                                            if (status == 0) {
                                                formatDialog.dismiss();
                                                Toast.makeText(ShopDetailActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                                            } else if (status == 1) {
                                                formatDialog.dismiss();
                                                Toast.makeText(ShopDetailActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                                Login login = MyApplication.getLogin();
                                                login.setDistance(Double.parseDouble(et));
                                                MyApplication.saveLogin(login);
                                                tvActivityShopDetailDeliveryDistance.setText(et + "km");
                                            } else if (status == 9) {
                                                Toast.makeText(ShopDetailActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                                MyApplication.saveLogin(null);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
                                httpUtils.addParam("long", et);
                                httpUtils.addParam("token", MyApplication.getLogin().getToken());
                                httpUtils.clicent();
                            } else {
                                formatDialog.dismiss();
                            }
                        } else if (type == CustomDialog.CANCLE) {
                            formatDialog.dismiss();
                        }
                    }
                });
                formatDialog.show();
                break;
            case R.id.rl_activity_shop_detail_fee:
                if (bean != null) {
                    goToActivity(FeeConfigActivity.class);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_activity_dish_detail_shop_change:
                if (bean != null) {
                    Intent i = new Intent(this, ImageGridActivity.class);
                    startActivityForResult(i, IMAGE_PICKER);
                } else {
                    ToastUtils.showToast(R.string.please_check_your_network_connection, mContext);
//                    Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        ToastUtils.cancelToast();
        super.onBackPressedSupport();
    }
}
