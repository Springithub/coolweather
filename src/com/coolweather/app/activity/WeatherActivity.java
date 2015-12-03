package com.coolweather.app.activity;

import java.net.URLDecoder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.until.HttpCallbackListener;
import com.coolweather.app.until.HttpUtil;
import com.coolweather.app.until.Utility;

public class WeatherActivity extends Activity implements OnClickListener {
	private LinearLayout weatherInfoLayout;
	// 用于显示城市名
	private TextView cityNametext;
	// 用于显示发布时间
	private TextView publishText;
	// 天气描述信息
	private TextView weatherDespText;
	// 气温1
	private TextView temp1Text;
	// 气温2
	private TextView temp2Text;
	// 当前日期
	private TextView currentDateText;
	private Button swithCity;
	private Button refreshWeather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		// 初始化各种控件
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNametext = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		swithCity = (Button) findViewById(R.id.switch_city);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);
		swithCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

		String county = getIntent().getStringExtra("county_name");
		// if (!TextUtils.isEmpty(county)) {
		// 有县级代号时就去查天气
		publishText.setText("同步中……");
		weatherInfoLayout.setVisibility(View.INVISIBLE);
		cityNametext.setVisibility(View.INVISIBLE);
		queryWeatherInfo(county);
		// Toast.makeText(this, "正在查询……", Toast.LENGTH_SHORT).show();
		// } else {
		// 没有县级代号时就直接显示本地天气
		// showWeather();
		// Toast.makeText(this, "本地天气", Toast.LENGTH_SHORT).show();
	}

	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("同步中……");
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			String county = prefs.getString("city_name", "");
			if (!TextUtils.isEmpty(county)) {
				queryWeatherInfo(county);
			}
			break;
		default:
			break;
		}
	}

	// 查询县级代号所对应的天气代号
	// private void queryWeatherCode(String countyCode) {
	// String address = "http://www.weather.com.cn/data/list3/city"
	// + countyCode + ".xml";
	// queryFromServer(address, "countyCode");
	//
	// }

	// 查询天气代号所对应的天气
	private void queryWeatherInfo(String county) {

		String address = "http://wthrcdn.etouch.cn/weather_mini?city=" + county;
		// Toast.makeText(this, address, Toast.LENGTH_LONG).show();
		queryFromServer(address);
	}

	// 根据传入的地址和类型去向服务器查询天气代号或者天气信息
	private void queryFromServer(final String address) {
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {

				Utility.handleWeatherResponse(WeatherActivity.this, response);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showWeather();
						Log.d("coolw", "showWeater");
					}
				});
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						publishText.setText("同步失败");
					}
				});
			}
		});
	}

	// 从sharepreferences文件中读取存储的天气信息，并显示到界面上
	private void showWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNametext.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("更新于：" + prefs.getString("publish_time", ""));
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNametext.setVisibility(View.VISIBLE);
		Log.d("cool", "getPreferences");
		Log.d("cool", prefs.getString("city_name", ""));
		Log.d("cool", prefs.getString("temp1", ""));
		Log.d("cool", prefs.getString("temp2", ""));
	}

}
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
*/
