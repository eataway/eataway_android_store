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
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.GlideUtils;
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
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.Call;

public class ShopPicConfigActivity extends BaseActivity {
    @Bind(R.id.tp_shop_pic_config)
    TopBar tpShopPicConfig;
    @Bind(R.id.iv_activity_shop_pic_config_pic)
    ImageView ivActivityShopPicConfigPic;
    @Bind(R.id.et_activity_shop_pic_config_name)
    EditText etActivityShopPicConfigName;
    @Bind(R.id.tv_activity_shop_pic_config_change)
    TextView tvActivityShopPicConfigChange;
    @Bind(R.id.btn_activity_dish_change_confirm)
    Button btnActivityDishChangeConfirm;
    @Bind(R.id.et_activity_shop_pic_config_description)
    EditText etActivityShopPicConfigDescription;

    private static final int IMAGE_PICKER = 1000;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    private File picture;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private Button alertDialogButton;

    private String shopName, shopHead, shopContent;
    private Intent i;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_pic_config;
    }

    @Override
    protected void initDate() {
        i = getIntent();
        Bundle data = i.getBundleExtra("data");
        shopName = data.getString("shopname");
        shopHead = data.getString("shoppic");
        shopContent = data.getString("shopcontent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initDialog();
        initImagePicker();
        tvActivityShopPicConfigChange.setText(getString(R.string.dian_pu_jie_shao)+"("+getString(R.string.xuan_tian)+")");
        etActivityShopPicConfigName.setText(shopName);
        if (!"".equals(shopContent)) {
            etActivityShopPicConfigDescription.setText(shopContent);
        }
        GlideUtils.load(this, shopHead, ivActivityShopPicConfigPic, GlideUtils.Shape.ShopIcon);
    }

    private void initTopBar() {
        tpShopPicConfig.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpShopPicConfig.setTbCenterTv(R.string.xiu_gai_shang_jia_xin_xi, R.color.color_white);
        tpShopPicConfig.setTbRightIv(R.drawable.img_icon_close_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        imagePicker.setFocusWidth(MyApplication.width / 2);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
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
        if (ims != null && pictures.size()>0) {
            for (int i = 0; i < ims.length; i++) {
                if (ims[i].isFile() && (ims[i].getName().toLowerCase().endsWith(".jpg")
                        || ims[i].getName().toLowerCase().endsWith(".jpeg")
                        || ims[i].getName().toLowerCase().endsWith(".png"))) {
                    pictures.add(ims[i]);
                }
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
                    upMessage(fileList.get(0));
                }

                @Override
                public void onError(Throwable e) {
                    alertDialog.setMessage("上传失败");
                    progressBar.setVisibility(View.GONE);
                    alertDialogButton.setEnabled(true);
                }
            });
        }
        if (pictures.size() == 0) {
            alertDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            alertDialog.setMessage("正在上传，请稍后");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            alertDialogButton.setEnabled(false);
            upMessage(null);
        }
    }

    private void upMessage(final File pic) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITJIANJIE) {
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialogButton.setEnabled(true);
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("shopname", etActivityShopPicConfigName.getText().toString().trim());
        httpUtils.addParam("content", etActivityShopPicConfigDescription.getText().toString().trim());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        if (pic != null)
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
                ivActivityShopPicConfigPic.setImageBitmap(BitmapFactory.decodeFile(images.get(images.size()-1).path));
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.iv_activity_shop_pic_config_pic, R.id.btn_activity_dish_change_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_activity_shop_pic_config_pic:
                i.setClass(this, ImageGridActivity.class);
                startActivityForResult(i, IMAGE_PICKER);
                break;
            case R.id.btn_activity_dish_change_confirm:
                selectHeadImage();
                break;
        }
    }
}
