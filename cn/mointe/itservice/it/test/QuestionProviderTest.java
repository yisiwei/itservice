package cn.mointe.itservice.it.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;
import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.provider.QuestionProvider;

public class QuestionProviderTest extends AndroidTestCase {

	public static final String TAG = "MainActivity";

	public void testInsert() {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.QSTN_COLUMN_CATG_ID, 4);
		values.put(DBHelper.QSTN_COLUMN_START_TIME, new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		values.put(DBHelper.QSTN_COLUMN_STATUS, "已分配");
		values.put(DBHelper.QSTN_COLUMN_COMMENTS, "11");
		values.put(DBHelper.QSTN_COLUMN_EQUIP_ID, 1);
		values.put(DBHelper.QSTN_COLUMN_HELPER_ID, 1);
		Uri uri = resolver.insert(QuestionProvider.CONTENT_URI, values);
		Log.i(TAG, uri + "");
	}

	public void testQuery() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(QuestionProvider.CONTENT_URI, null,
				null, null, null);
		while (cursor.moveToNext()) {
			String createtime = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME));
			String state = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_STATUS));
			String eqpid = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_EQUIP_ID));
			String catg_id = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_CATG_ID));
			Log.i(TAG, createtime + "--" + state + "--" + eqpid + "--"
					+ catg_id);
		}
		cursor.close();
	}

	public void testDelete() throws Exception {
		ContentResolver resolver = this.getContext().getContentResolver();
		int count = resolver.delete(QuestionProvider.CONTENT_URI,
				DBHelper.QSTN_COLUMN_ID + "=?", new String[] { "2" });
		Log.i(TAG, count + "");
	}

	public void testUpdate() throws Exception {
		// 访问内容提供者
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DBHelper.QSTN_COLUMN_STATUS, "未分配");
		int count = resolver.update(QuestionProvider.CONTENT_URI, values, null,
				null);
		// DBHelper.QUESTION_COLUMN_ID + "=?", new String[] { "1" }
		Log.i(TAG, count + "");
	}

	public void testQstnCatgQuery() {
		String[] projection = { "question." + DBHelper.QSTN_COLUMN_ID,
				"question." + DBHelper.QSTN_COLUMN_CATG_ID,
				"qstn_category." + DBHelper.CATG_COLUMN_QSTN_CATG,
				"qstn_category." + DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL,
				"question." + DBHelper.QSTN_COLUMN_START_TIME,
				"question." + DBHelper.QSTN_COLUMN_STATUS };
		Uri uri = Uri.withAppendedPath(QuestionProvider.CONTENT_URI,
				DBHelper.CATG_TABLE_NAME);
		Cursor cursor = getContext().getContentResolver().query(uri,
				projection, null, null, null);
		if (cursor == null) {
			Log.i(TAG, "cursor is null");
			return;
		}
		while (cursor.moveToNext()) {
			Log.i(TAG, "*****************");
			String cateid = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_CATG_ID));
			String qstn_catg = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG));
			String catg_detail = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL));
			String time = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME));
			String status = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_STATUS));
			Log.i(TAG, cateid + "--" + qstn_catg + "--" + catg_detail + "--"
					+ time + "--" + status);
		}
	}

	public void deleteDB() {
		DBHelper db = new DBHelper(this.getContext());
		boolean b = db.deleteDB(this.getContext());
		Log.i(TAG, b + "");
	}

}
