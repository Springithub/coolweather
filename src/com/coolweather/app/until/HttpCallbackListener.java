package com.coolweather.app.until;

public interface HttpCallbackListener {
	void onFinish(String response);

	void onError(Exception e);
}
