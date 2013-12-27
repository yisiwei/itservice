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
		// ���������ṩ��
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG, "��������");
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL, "ֻ����QQ��������");
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
		// ���������ṩ��
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL, "����������");
		int count = resolver.update(QstnCategoryProvider.CONTENT_URI, values,
				DBHelper.CATG_COLUMN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

}
