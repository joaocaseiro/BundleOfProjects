package com.service.simple;

import android.app.Application;
import android.util.Log;

public class SimpleLongRunningServiceApplication extends Application {
	private ServiceCommunicator serviceComm;

	public SimpleLongRunningServiceApplication() {
		LogHelper.log(getClass(), Log.INFO, " - Application starting");
		serviceComm = new ServiceCommunicator(this);
	}

	public ServiceCommunicator getServiceCommunicator() {
		return serviceComm;
	}
}
