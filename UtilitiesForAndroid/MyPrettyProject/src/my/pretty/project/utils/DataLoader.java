package my.pretty.project.utils;

import java.io.Serializable;

public interface DataLoader extends Serializable {
	void loadData();

	void updateUI();
	
	void onCancel();
}
