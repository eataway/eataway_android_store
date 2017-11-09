package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.administrator.administrator.eataway.utils.GlideUtils;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ImageLoaderUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DishChangeActivity extends BaseActivity {
    @Bind(R.id.tp_dish_change)
    TopBar tpDishChange;
    @Bind(R.id.et_activity_dish_change_dish_name)
    EditText etActivityDishChangeDishName;
    @Bind(R.id.et_activity_dish_change_dish_price)
    EditText etActivityDishChangeDishPrice;
    @Bind(R.id.tv_activity_dish_change_need_change)
    TextView tvActivityDishChangeNeedChange;
    @Bind(R.id.btn_activity_dish_change_confirm)
    Button btnActivityDishChangeConfirm;
    @Bind(R.id.et_activity_dish_change_dish_description)
    EditText etActivityDishChangeDishDescription;
    @Bind(R.id.iv_activity_dish_change_dish_pic)
    ImageView ivActivityDishChangeDishPic;
    @Bind(R.id.pb_newsdetails)
    ProgressBar pbNewsdetails;
    @Bind(R.id.rl_newsdetails)
    RelativeLayout rlNewsdetails;
    private Login login = MyApplication.getLogin();
    private List<File> pictures = new ArrayList<>();
    private ImagePicker imagePicker;
    List<ImageItem> list = new ArrayList<>();
    private ArrayList<ImageItem> images;
    private int MAX_PCITURE = 1;
    private String goodsid;
    private String cid;
    private Bitmap bitmap;
    private static final int IMAGE_PICKER = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dish_change;
    }

    @Override
    protected void initDate() {
        Bundle b = getIntent().getBundleExtra("data");
        goodsid = b.getString("goodsid");
        cid = b.getString("cid");
        GlideUtils.load(this, b.getString("dishpic",""), ivActivityDishChangeDishPic, GlideUtils.Shape.ShopPic);
        etActivityDishChangeDishName.setText(b.getString("dishname"));
        etActivityDishChangeDishPrice.setText(b.getString("dishprice"));
        etActivityDishChangeDishDescription.setText(b.getString("dishdescription"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initListener();
        tvActivityDishChangeNeedChange.setText("("+getString(R.string.xuan_tian)+")");
    }

    private void initTopBar() {
        tpDishChange.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpDishChange.setTbCenterTv(R.string.xiu_gai_cai_pin, R.color.color_white);
        tpDishChange.setTbRightIv(R.drawable.img_icon_close_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        //初始化照片选择器
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true);
        imagePicker.setImageLoader(new ImageLoaderUtils());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        //判断缓存路径中有无图片，有则删除
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

    private void changeDish() {
        if (etActivityDishChangeDishName.getText() == null || etActivityDishChangeDishName.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, R.string.qing_shu_ru_cai_pin_ming_cheng, Toast.LENGTH_SHORT).show();
            return;
        }
        if (etActivityDishChangeDishPrice.getText() == null || etActivityDishChangeDishPrice.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, R.string.qing_shu_ru_cai_dan_jia_ge, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITDISH) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(DishChangeActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                rlNewsdetails.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        Toast.makeText(DishChangeActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (status == 9) {
                        Toast.makeText(DishChangeActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(DishChangeActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(DishChangeActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    rlNewsdetails.setVisibility(View.GONE);
                    btnActivityDishChangeConfirm.setClickable(true);
                }
            }
        };
        httpUtils.addParam("cid", cid).addParams("token", login.getToken()).addParams("shopid", login.getShopId())
                .addParams("goodsid", goodsid)
                .addParams("goodsname", etActivityDishChangeDishName.getText().toString().trim())
                .addParams("goodsprice", etActivityDishChangeDishPrice.getText().toString().trim());
        if (pictures.size()>0) {
            httpUtils.addFile("myFile", pictures.get(0).getName(), pictures.get(0));
        }
        if (etActivityDishChangeDishDescription.getText() != null && !etActivityDishChangeDishDescription.getText().toString().trim().equals("")) {
            httpUtils.addParam("goodscontent", etActivityDishChangeDishDescription.getText().toString().trim());
        }
        httpUtils.clicent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = null;
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    if ((images.get(i).path.toLowerCase().endsWith(".jpg")
                            || images.get(i).path.toLowerCase().endsWith(".jpeg")
                            || images.get(i).path.toLowerCase().endsWith(".png"))) {
                        list.add(i, images.get(i));
                        File file = new File(images.get(i).path);
                        pictures.add(i, file);
                    } else {
                        Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                    }
                }
//                imagePicker.setSelectLimit(MAX_PCITURE - list.size());
                bitmap = BitmapFactory.decodeFile(pictures.get(0).getPath());
                if (bitmap != null) {
                    ivActivityDishChangeDishPic.setImageBitmap(bitmap);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.iv_activity_dish_change_dish_pic, R.id.btn_activity_dish_change_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_activity_dish_change_dish_pic:
                Intent intent = new Intent(DishChangeActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.btn_activity_dish_change_confirm:
                changeDish();
                break;
        }
    }
}
