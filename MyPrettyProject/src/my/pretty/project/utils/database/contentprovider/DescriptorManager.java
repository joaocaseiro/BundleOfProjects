package my.pretty.project.utils.database.contentprovider;

import android.net.Uri;

public class DescriptorManager {
	public static BaseDescriptor getCorrectObject(Uri uri, int match) {
		boolean many = false;
		switch (match) {
//			case WidgetDescriptor.PATH_TOKEN:								//100
//				many = true;
//			case WidgetDescriptor.PATH_FOR_ID_TOKEN:						//200
//				return new WidgetDescriptor(many);
//				
//			case AdministrativeWidgetDescriptor.PATH_TOKEN:					//101
//				many = true;
//			case AdministrativeWidgetDescriptor.PATH_FOR_ID_TOKEN:			//201
//				return new AdministrativeWidgetDescriptor(many);
//				
//			case SettingsDescriptor.PATH_TOKEN:								//102
//				many = true;
//			case SettingsDescriptor.PATH_FOR_ID_TOKEN:						//202
//				return new SettingsDescriptor(many);
//				
//			case PersonalizedTabDescriptor.PATH_TOKEN:						//103
//				many = true;
//			case PersonalizedTabDescriptor.PATH_FOR_ID_TOKEN:				//203
//				return new PersonalizedTabDescriptor(many);
//	
//			case AdministrativeTabDescriptor.PATH_TOKEN:					//104
//				many = true;
//			case AdministrativeTabDescriptor.PATH_FOR_ID_TOKEN:				//204
//				return new AdministrativeTabDescriptor(many);
//	
//			case AndroidDatabaseTagDescriptor.PATH_TOKEN:					//105
//				many = true;
//			case AndroidDatabaseTagDescriptor.PATH_FOR_ID_TOKEN:			//205
//				return new AndroidDatabaseTagDescriptor(many);
//				
//			case AndroidDatabaseLogicalObjectDescriptor.PATH_TOKEN:			//106
//				many = true;
//			case AndroidDatabaseLogicalObjectDescriptor.PATH_FOR_ID_TOKEN:	//206
//				return new AndroidDatabaseLogicalObjectDescriptor(many);
//				
			case AndroidDatabaseLocationDescriptor.PATH_TOKEN:				//107
				many = true;
			case AndroidDatabaseLocationDescriptor.PATH_FOR_ID_TOKEN:		//207
				return new AndroidDatabaseLocationDescriptor(many);
				
//			case AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH_TOKEN:				//108
//				many = true;
//			case AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH_FOR_ID_TOKEN:		//208
//				return new AndroidDatabaseRelationObjectOrLocationAndTagDescriptor(many);
//				
//			case AndroidDatabaseScenarioDescriptor.PATH_TOKEN:				//109
//				many = true;
//			case AndroidDatabaseScenarioDescriptor.PATH_FOR_ID_TOKEN:		//209
//				return new AndroidDatabaseScenarioDescriptor(many);
//	
			default:
				throw new UnsupportedOperationException("URI " + uri + " is not supported.");
		}
	}
	
	public static Uri getContentUri(String tableName) {
//		if(PersonalizedTabDescriptor.TBL_NAME.equals(tableName)) {
//			return PersonalizedTabDescriptor.CONTENT_URI;
//			
//		} else if(AdministrativeTabDescriptor.TBL_NAME.equals(tableName)) {
//			return AdministrativeTabDescriptor.CONTENT_URI;
//			
//		} else if(WidgetDescriptor.TBL_NAME.equals(tableName)) {
//			return WidgetDescriptor.CONTENT_URI;
//			
//		} else if(AdministrativeWidgetDescriptor.TBL_NAME.equals(tableName)) {
//			return AdministrativeWidgetDescriptor.CONTENT_URI;
//			
//		} else if(SettingsDescriptor.TBL_NAME.equals(tableName)) {
//			return SettingsDescriptor.CONTENT_URI;
//			
//		} else if(AndroidDatabaseTagDescriptor.TBL_NAME.equals(tableName)) {
//			return AndroidDatabaseTagDescriptor.CONTENT_URI;
//			
//		} else if(AndroidDatabaseLogicalObjectDescriptor.TBL_NAME.equals(tableName)) {
//			return AndroidDatabaseLogicalObjectDescriptor.CONTENT_URI;
//			
		/*} else */if(AndroidDatabaseLocationDescriptor.TBL_NAME.equals(tableName)) {
			return AndroidDatabaseLocationDescriptor.CONTENT_URI;
			
//		} else if(AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.TBL_NAME.equals(tableName)) {
//			return AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.CONTENT_URI;
//			
//		} else if(AndroidDatabaseScenarioDescriptor.TBL_NAME.equals(tableName)) {
//			return AndroidDatabaseScenarioDescriptor.CONTENT_URI;
//			
		} else {
			throw new UnsupportedOperationException("Table " + tableName + " was not found.");			
		}
	}
}
