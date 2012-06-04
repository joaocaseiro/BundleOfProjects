package com.service.simple;

import java.io.Serializable;

public interface DataLoader extends Serializable {
	void loadData();

	void updateUI();
	
	void onCancel();
}
