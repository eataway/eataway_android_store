package com.administrator.administrator.eataway.comm;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.administrator.administrator.eataway.R;

/**
 * @Description:自定义对话框
 * @author shidengzhong
 */
public class WaitDialog extends ProgressDialog {

	private ImageView dialog_iv;
	private AnimationDrawable animationDrawable;
	private Context context;

	public WaitDialog(Context context) {
		super(context, R.style.waitDialog);
		setCanceledOnTouchOutside(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
	}

	public void setContent(String str) {
	}

	private void initView() {
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.3); // 高度设置为屏幕的0.6
		lp.height = (int) (d.widthPixels * 0.3);
		lp.alpha = 0.5f;
		dialogWindow.setAttributes(lp);

		setContentView(R.layout.dialog_layout);
	}

	/**
	 * 监听对话框返回键不可用
	 */
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	// return true;
	// } else {
	// return false;
	// }
	// }
}
