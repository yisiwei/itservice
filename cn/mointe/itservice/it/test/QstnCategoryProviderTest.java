package cn.mointe.itservice.it.test;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.provider.QstnCategoryProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class QstnCategoryProviderTest extends AndroidTestCase {

	public static final String TAG = "MainActivity";

	public void testInsert() {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG, "网络问题");
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL, "只能上QQ不能上网");
		Uri uri = resolver.insert(QstnCategoryProvider.CONTENT_URI, values);
		Log.i(TAG, uri + "");
	}

	public void testQuery() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(QstnCategoryProvider.CONTENT_URI, null,
				null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_ID));
			String qstn_catg = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG));
			String detail = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL));
			Log.i(TAG, id + "--" + qstn_catg + "--" + detail);
		}
		cursor.close();
	}

	public void testDelete() throws Exception {
		ContentResolver resolver = this.getContext().getContentResolver();
		int count = resolver.delete(QstnCategoryProvider.CONTENT_URI,
				DBHelper.CATG_COLUMN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

	public void testUpdate() throws Exception {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL, "网络连不上");
		int count = resolver.update(QstnCategoryProvider.CONTENT_URI, values,
				DBHelper.CATG_COLUMN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

}
