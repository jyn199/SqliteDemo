package com.jyn.sqlitedemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jyn.sqlite.DemoSqliteHelper;

public class MainActivity extends Activity {

	int versionCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		try {
			getVersionInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//create tables
		//upgrade
		SQLiteOpenHelper dbHelper = new DemoSqliteHelper(this, "demo", null, versionCode);
		//downgrade
//		SQLiteOpenHelper dbHelper = new DemoSqliteHelper(this, "demo", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		db.execSQL("insert into demo values(1, 'test')");
		Cursor cursor = db.rawQuery("select * from demo where id = ?", new String[]{"1"});
		while(cursor.moveToNext()){
			System.out.println(cursor.getColumnIndex("id"));
			System.out.println(cursor.getColumnIndex("name"));
		}
	}
	
	private void getVersionInfo() throws Exception{
		//versionCode控制数据库版本
		PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		versionCode = packageInfo.versionCode;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
