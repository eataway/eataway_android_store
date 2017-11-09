package com.administrator.administrator.eataway.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ImageLoaderUtils;
import com.administrator.administrator.eataway.view.TopBar;
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

public class CertificationUpActivity extends BaseActivity {
    @Bind(R.id.tp_certification_up)
    TopBar tpCertificationUp;
    @Bind(R.id.tv_activity_certification_shop_name)
    EditText tvActivityCertificationShopName;
    @Bind(R.id.rl_activity_certification_up_shop_name)
    RelativeLayout rlActivityCertificationUpShopName;
    @Bind(R.id.tv_activity_certification_shop_phone)
    EditText tvActivityCertificationShopPhone;
    @Bind(R.id.rl_activity_certification_up_shop_phone)
    RelativeLayout rlActivityCertificationUpShopPhone;
    @Bind(R.id.tv_certification_up_shop_location)
    TextView tvCertificationUpShopLocation;
    @Bind(R.id.tv_activity_certification_shop_location)
    TextView tvActivityCertificationShopLocation;
    @Bind(R.id.rl_activity_certification_up_shop_location)
    RelativeLayout rlActivityCertificationUpShopLocation;
    @Bind(R.id.rl_activity_certification_up_shop_icon)
    RelativeLayout rlActivityCertificationUpShopIcon;
    @Bind(R.id.rl_activity_certification_up_shop_pic)
    RelativeLayout rlActivityCertificationUpShopPic;
    @Bind(R.id.cimg_activity_certification_up_shop_icon)
    CircleImageView cimgActivityCertificationUpShopIcon;
    @Bind(R.id.img_activity_certification_up_shop_pic)
    ImageView imgActivityCertificationUpShopPic;

    private HttpUtils httpUtils;
    private Login login;

    private static final int SHOP_ICON = 1000;
    private static final int SHOP_PIC = 1001;

    private ImagePicker imagePicker;
    private Intent i;

    private ArrayList<ImageItem> images;
    private File pic, icon;
    private List<File> list;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private Button alertDialogButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification_up;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initDialog();
        initImagePicker();
        i = getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        login = MyApplication.getLogin();
        if (login != null && !"".equals(login.getLocation_text())) {
            tvActivityCertificationShopLocation.setText(login.getLocation_text());
        }
    }

    private void initTopBar() {
        tpCertificationUp.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpCertificationUp.setTbCenterTv(R.string.ren_zheng_zhuang_tai, R.color.color_white);
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
//        imagePicker.setFocusWidth(MyApplication.width / 2);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(MyApplication.width / 2);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
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
        list = new ArrayList<>();
        list.add(0, icon);
        list.add(1, pic);
        Luban.compress(this, list).launch(new OnMultiCompressListener() {
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
                upMessage(fileList.get(0), fileList.get(1));
            }

            @Override
            public void onError(Throwable e) {
                alertDialog.setMessage("上传失败");
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }
        });

    }

    private void upMessage(final File icon, final File pic) {
        httpUtils = new HttpUtils(Contants.URL_ADDSHOP) {
            @Override
            public void onError(Call call, Exception e, int id) {
                alertDialog.setMessage("网络错误,请检查网络设置");
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
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialogButton.setEnabled(true);
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("shopname", tvActivityCertificationShopName.getText().toString().trim());
        httpUtils.addParam("detailed_address", tvActivityCertificationShopLocation.getText().toString().trim());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.addFile("myFile1", icon.getName(), icon);
        httpUtils.addFile("myFile2", pic.getName(), pic);
        httpUtils.addParam("coordinate", MyApplication.getLogin().getLongitude()+","+MyApplication.getLogin().getLatitude());
        httpUtils.addParam("mobile", tvActivityCertificationShopPhone.getText().toString().trim());
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
            if (data != null) {
                if (requestCode == SHOP_ICON){
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    cimgActivityCertificationUpShopIcon.setImageBitmap(BitmapFactory.decodeFile(images.get(0).path));
                    if ((images.get(0).path.toLowerCase().endsWith(".jpg")
                            || images.get(0).path.toLowerCase().endsWith(".jpeg")
                            || images.get(0).path.toLowerCase().endsWith(".png"))) {
                       icon = new File(images.get(0).path);
                    } else {
                        Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    imgActivityCertificationUpShopPic.setImageBitmap(BitmapFactory.decodeFile(images.get(images.size()-1).path));
                    if ((images.get(0).path.toLowerCase().endsWith(".jpg")
                            || images.get(0).path.toLowerCase().endsWith(".jpeg")
                            || images.get(0).path.toLowerCase().endsWith(".png"))) {
                        pic = new File(images.get(0).path);
                    } else {
                        Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (data != null && requestCode == SHOP_PIC){
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.rl_activity_certification_up_shop_name, R.id.rl_activity_certification_up_shop_phone, R.id.rl_activity_certification_up_shop_location, R.id.rl_activity_certification_up_shop_icon, R.id.rl_activity_certification_up_shop_pic, R.id.btn_activity_certification_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_certification_up_shop_name:

                break;
            case R.id.rl_activity_certification_up_shop_phone:
                break;
            case R.id.rl_activity_certification_up_shop_location:
                goToActivity(MapActivity.class);
                break;
            case R.id.rl_activity_certification_up_shop_icon:
                imagePicker.setFocusWidth(MyApplication.width / 2);
                imagePicker.setFocusHeight(MyApplication.width / 2);
                i.setClass(this, ImageGridActivity.class);
                startActivityForResult(i, SHOP_ICON);
                break;
            case R.id.rl_activity_certification_up_shop_pic:
                imagePicker.setFocusWidth(MyApplication.height / 2);
                imagePicker.setFocusHeight(MyApplication.width / 2);
                i.setClass(this, ImageGridActivity.class);
                startActivityForResult(i, SHOP_PIC);
                break;
            case R.id.btn_activity_certification_ok:
                selectHeadImage();
                break;
        }
    }
}
