package cn.mointe.itservice.it.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.provider.GradeProvider;
import cn.mointe.itservice.it.provider.QuestionProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class GradeProviderTest extends AndroidTestCase {

	public static final String TAG = "MainActivity";

	public void testInsert() {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.GDE_COLUMN_LEVEL, 5);
		values.put(DBHelper.GDE_COLUMN_DESC, "非常满意");
		Uri uri = resolver.insert(GradeProvider.CONTENT_URI, values);
		Log.i(TAG, uri + "");
	}

	public void testQuery() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(GradeProvider.CONTENT_URI, null, null,
				null, null);
		while (cursor.moveToNext()) {
			String level = cursor.getString(cursor
					.getColumnIndex(DBHelper.GDE_COLUMN_LEVEL));
			String desc = cursor.getString(cursor
					.getColumnIndex(DBHelper.GDE_COLUMN_DESC));
			Log.i(TAG, level + "--" + desc);
		}
		cursor.close();
	}

	public void testDelete() throws Exception {
		ContentResolver resolver = this.getContext().getContentResolver();
		int count = resolver.delete(QuestionProvider.CONTENT_URI,
				DBHelper.GDE_COLUMN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

	public void testUpdate() throws Exception {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.GDE_COLUMN_DESC, "满意");
		int count = resolver.update(QuestionProvider.CONTENT_URI, values,
				DBHelper.GDE_COLUMN_LEVEL + "=?", new String[] { "4" });
		Log.i(TAG, count + "");
	}

}
