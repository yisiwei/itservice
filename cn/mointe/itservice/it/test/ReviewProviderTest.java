package cn.mointe.itservice.it.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.provider.ReviewProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class ReviewProviderTest extends AndroidTestCase {

	public static final String TAG = "MainActivity";

	public void testCreateReview() {
		DBHelper dbHelper = new DBHelper(this.getContext());
		dbHelper.getReadableDatabase();
	}

	public void testInsert() {
		// ���������ṩ��
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.RVW_COLUMN_QSTN_ID, 1);
		values.put(DBHelper.RVW_COLUMN_CONTENT, "��ʱ���������");
		values.put(DBHelper.RVW_COLUMN_TIME, new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Uri uri = resolver.insert(ReviewProvider.CONTENT_URI, values);
		Log.i(TAG, uri + "");
	}

	public void testQuery() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(ReviewProvider.CONTENT_URI, null, null,
				null, null);
		while (cursor.moveToNext()) {
			String qstn_id = cursor.getString(cursor
					.getColumnIndex(DBHelper.RVW_COLUMN_QSTN_ID));
			String content = cursor.getString(cursor
					.getColumnIndex(DBHelper.RVW_COLUMN_CONTENT));
			Log.i(TAG, qstn_id + "--" + content);
		}
		cursor.close();
	}

	public void testDelete() throws Exception {
		ContentResolver resolver = this.getContext().getContentResolver();
		int count = resolver.delete(ReviewProvider.CONTENT_URI,
				DBHelper.RVW_COLUMN_QSTN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

	public void testUpdate() throws Exception {
		// ���������ṩ��
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.RVW_COLUMN_CONTENT, "����");
		int count = resolver.update(ReviewProvider.CONTENT_URI, values,
				DBHelper.RVW_COLUMN_QSTN_ID + "=?", new String[] { "1" });
		Log.i(TAG, count + "");
	}

}
