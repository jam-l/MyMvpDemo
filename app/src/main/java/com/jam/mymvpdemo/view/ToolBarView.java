package com.jam.mymvpdemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jam.mymvpdemo.R;

public class ToolBarView extends RelativeLayout implements OnClickListener {

	private Button btnTitleLeft;
	private Button btnTitleCenter;
	private Button btnTitleRight;

	private ImageView   ivTitleBack;
	private TextView  tvTitleRightTip;
	private ImageView ivTitleRight;
	private ImageView ivTitleRight2;

	public ToolBarView(Context context) {
		super(context);
		init(context, null);
	}

	public ToolBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ToolBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.view_toolbar, this);
		initViews();

		if (attrs != null && attrs.getAttributeCount() > 0) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
			if (typedArray != null) {
				int    count     = typedArray.getIndexCount();
				String titleText = null;
				for (int i = 0; i < count; i++) {
					int attr = typedArray.getIndex(i);
					switch (attr) {
					case R.styleable.ToolBar_center_text:
						titleText = typedArray.getString(attr);
						break;
					case R.styleable.ToolBar_back_image_visibility:
						boolean visible = typedArray.getBoolean(attr, false);
						ivTitleBack.setVisibility(visible ? View.VISIBLE : View.GONE);
						break;
					default:
						break;
					}
				}
				typedArray.recycle();
				if (!TextUtils.isEmpty(titleText)) {
					btnTitleCenter.setText(titleText);
				}
			}
		}
	}

	private void initViews() {
		btnTitleLeft = (Button) findViewById(R.id.btnTitleLeft);
		btnTitleCenter = (Button) findViewById(R.id.btnTitleCenter);
		btnTitleRight = (Button) findViewById(R.id.btnTitleRight);
		ivTitleBack = (ImageView) findViewById(R.id.ivTitleBack);
		ivTitleRight = (ImageView) findViewById(R.id.ivTitleRight);
		ivTitleRight2 = (ImageView) findViewById(R.id.ivTitleRight2);
		tvTitleRightTip = (TextView) findViewById(R.id.tvTitleRightTip);
		ivTitleBack.setOnClickListener(this);
	}

	/** 设置按钮文本 **/
	private void setBtnText(Button btn, CharSequence text) {
		if (!TextUtils.isEmpty(text)) {
			btn.setText(text);
			btn.setVisibility(View.VISIBLE);
			if (btn.equals(btnTitleLeft)) {
				if (text.equals("返回")) {
					ivTitleBack.setVisibility(View.VISIBLE);
					btn.setVisibility(View.GONE);

				} else {
					ivTitleBack.setVisibility(View.GONE);
					btn.setVisibility(View.VISIBLE);
				}
			}
		} else {
			if (btn.equals(btnTitleLeft)) {
				ivTitleBack.setVisibility(View.GONE);
			}
			btn.setVisibility(View.GONE);
		}
	}

	/** 设置默认标题内容 [左中右]*/
	public void setDefaultToolbar(CharSequence leftText, CharSequence titleText, CharSequence rightText) {
		setBtnText(btnTitleLeft, leftText);
		setBtnText(btnTitleCenter, titleText);
		setBtnText(btnTitleRight, rightText);
	}

	/** 设置[右]标题 */
	public void setRightText(String leftText) {
		setBtnText(btnTitleRight, leftText);
	}

	/** 设置[左]标题 */
	public void setLeftText(String rightText) {
		setBtnText(btnTitleCenter, rightText);
	}

	/** 设置[中]标题 */
	public void setCenterText(String titleText) {
		setBtnText(btnTitleCenter, titleText);
	}

	/** 设置默认标题 [右]点击事件 */
	public void setRightOnClick(OnClickListener rightOnclickListener) {
		if (rightOnclickListener != null) {
			btnTitleRight.setOnClickListener(rightOnclickListener);
		}
	}

	/** 设置默认标题 [左]点击事件 */
	public void setLeftOnClick(OnClickListener leftOnclickListener) {
		if (leftOnclickListener != null) {
			btnTitleLeft.setOnClickListener(leftOnclickListener);
		}
	}

	/** 设置默认标题 [中]点击事件 */
	public void setCenterOnClick(OnClickListener centerOnclickListener) {
		if (centerOnclickListener != null) {
			btnTitleCenter.setOnClickListener(centerOnclickListener);
		}
	}

	/** 标题点击回调 **/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivTitleBack:
		case R.id.btnTitleLeft:
			if (getContext() instanceof Activity) {
				((Activity) getContext()).onBackPressed();
			}
			break;
		default:
			break;
		}
	}
}
