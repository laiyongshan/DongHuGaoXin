package suncere.androidapp.lib.mvp.ui;

import android.os.Bundle;
import android.view.View;

import suncere.androidapp.lib.R;
import suncere.androidapp.lib.mvp.ui.baseui.BaseActivity;


public class AQIDesActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aqi_des_act);
	}

	public void On_btnBack_Click(View sender)
	{
		this.finish();
	}
}
