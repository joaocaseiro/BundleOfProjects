package my.pretty.project.utils.database.contentprovider;

import my.pretty.project.utils.InfoApp;
import android.content.UriMatcher;
import android.net.Uri;

public class ContentProviderDescriptor {
	public static final Uri BASE_URI = Uri.parse("content://" + InfoApp.AUTHORITY);
	public static final UriMatcher URI_MATCHER = buildUriMatcher();
	
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = InfoApp.AUTHORITY;

//        //100 e 200
//        matcher.addURI(authority, WidgetDescriptor.PATH, WidgetDescriptor.PATH_TOKEN);
//        matcher.addURI(authority, WidgetDescriptor.PATH_FOR_ID, WidgetDescriptor.PATH_FOR_ID_TOKEN);
//        
//        //101 e 201
//        matcher.addURI(authority, AdministrativeWidgetDescriptor.PATH, AdministrativeWidgetDescriptor.PATH_TOKEN);
//        matcher.addURI(authority, AdministrativeWidgetDescriptor.PATH_FOR_ID, AdministrativeWidgetDescriptor.PATH_FOR_ID_TOKEN);
//        
//        //102 e 202
//        matcher.addURI(authority, SettingsDescriptor.PATH_FOR_ID, SettingsDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, SettingsDescriptor.PATH, SettingsDescriptor.PATH_TOKEN);
//        
//        //103 e 203
//        matcher.addURI(authority, PersonalizedTabDescriptor.PATH_FOR_ID, PersonalizedTabDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, PersonalizedTabDescriptor.PATH, PersonalizedTabDescriptor.PATH_TOKEN);
//        
//        //104 e 204
//        matcher.addURI(authority, AdministrativeTabDescriptor.PATH_FOR_ID, AdministrativeTabDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, AdministrativeTabDescriptor.PATH, AdministrativeTabDescriptor.PATH_TOKEN);
//
//        //105 e 205
//        matcher.addURI(authority, AndroidDatabaseTagDescriptor.PATH_FOR_ID, AndroidDatabaseTagDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, AndroidDatabaseTagDescriptor.PATH, AndroidDatabaseTagDescriptor.PATH_TOKEN);
//        
//        //106 e 206
//        matcher.addURI(authority, AndroidDatabaseLogicalObjectDescriptor.PATH_FOR_ID, AndroidDatabaseLogicalObjectDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, AndroidDatabaseLogicalObjectDescriptor.PATH, AndroidDatabaseLogicalObjectDescriptor.PATH_TOKEN);
//        
        //107 e 207
        matcher.addURI(authority, AndroidDatabaseLocationDescriptor.PATH_FOR_ID, AndroidDatabaseLocationDescriptor.PATH_FOR_ID_TOKEN);
        matcher.addURI(authority, AndroidDatabaseLocationDescriptor.PATH, AndroidDatabaseLocationDescriptor.PATH_TOKEN);
//        
//        //108 e 208
//        matcher.addURI(authority, AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH_FOR_ID, AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH, AndroidDatabaseRelationObjectOrLocationAndTagDescriptor.PATH_TOKEN);
//        
//        //109 e 209
//        matcher.addURI(authority, AndroidDatabaseScenarioDescriptor.PATH_FOR_ID, AndroidDatabaseScenarioDescriptor.PATH_FOR_ID_TOKEN);
//        matcher.addURI(authority, AndroidDatabaseScenarioDescriptor.PATH, AndroidDatabaseScenarioDescriptor.PATH_TOKEN);
        return matcher;
	}
}
