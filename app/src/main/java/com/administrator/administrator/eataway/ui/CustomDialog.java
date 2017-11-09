package com.administrator.administrator.eataway.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.utils.HttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/7/3.
 */

public class CustomDialog extends Dialog {
    @Bind(R.id.tv_km_config)
    TextView tvKmConfig;
    private Context context;

    @Bind(R.id.et_fee_config)
    EditText etFeeConfig;
    @Bind(R.id.tv_fee_config)
    TextView tvFeeConfig;
    @Bind(R.id.btn_dialog_delete)
    Button btnDialogDelete;
    @Bind(R.id.btn_dialog_ok)
    Button btnDialogOk;

    public static final int DELETE = 0;
    public static final int CANCLE = 1;
    public static final int OK = 2;

    private int currentType = 0;

    private DialogOnClickListener listener;

    private HttpUtils httpUtils;
    private String text;
    private int type = 0;

    public CustomDialog(@NonNull Context context, int currentType) {
        super(context);
        this.context = context;
        this.currentType = currentType;
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId, int currentType) {
        super(context, themeResId);
        this.context = context;
        this.currentType = currentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fee_info_config);
        ButterKnife.bind(this);
        setDiaLogType(currentType);
        if (type == 0) {
            tvFeeConfig.setVisibility(View.VISIBLE);
            tvKmConfig.setVisibility(View.GONE);
        }else {
            tvFeeConfig.setVisibility(View.GONE);
            tvKmConfig.setVisibility(View.VISIBLE);
        }
    }

    public void setEditText(int type) {
       this.type = type;
    }

    @OnClick({R.id.btn_dialog_delete, R.id.btn_dialog_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_delete:
                if (currentType == DELETE) {
                    listener.onClick(DELETE, etFeeConfig.getText().toString().trim());
                } else if (currentType == CANCLE) {
                    listener.onClick(CANCLE, etFeeConfig.getText().toString().trim());
                }
                break;
            case R.id.btn_dialog_ok:
                listener.onClick(OK, etFeeConfig.getText().toString().trim());
                break;
        }
    }

    public void setDiaLogType(int type) {
        switch (type) {
            case DELETE:
                btnDialogDelete.setText(context.getString(R.string.text_delete));
                currentType = DELETE;
                break;
            case CANCLE:
                btnDialogDelete.setText(context.getString(R.string.text_cancel));
                currentType = CANCLE;
                break;
        }
    }

    public void setDialogOnClickListener(DialogOnClickListener listener) {
        this.listener = listener;
    }

    public interface DialogOnClickListener {
        void onClick(int type, String et);
    }
}
