package cn.mointe.itservice.it.provider;

import cn.mointe.itservice.it.db.DBHelper;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class QuestionProvider extends ContentProvider {

	private static final String TAG = "MainActivity";

	private DBHelper mDBHelper;
	private ContentResolver mContentResolver;

	private static final UriMatcher URI_MATCHER;
	private static final int ALL_ROWS = 1;
	private static final int SINGLE_ROW = 2;
	// question 和 category 两个表查询
	private static final int QSTN_CATG = 3;

	private static final String AUTHORITIES = "cn.mointe.itservice.providers.QuestionProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES
			+ "/question");

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITIES, "question", ALL_ROWS);
		URI_MATCHER.addURI(AUTHORITIES, "question/#", SINGLE_ROW);// #代表数字
		URI_MATCHER.addURI(AUTHORITIES, "question/*", QSTN_CATG);
	}

	// 内容提供者实例创建后被调用
	@Override
	public boolean onCreate() {
		mDBHelper = new DBHelper(this.getContext());
		mContentResolver = this.getContext().getContentResolver();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db;
		try {
			db = mDBHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = mDBHelper.getReadableDatabase();
		}
		Cursor cursor = null;

		switch (URI_MATCHER.match(uri)) {
		case ALL_ROWS:
			cursor = db.query(DBHelper.QSTN_TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = DBHelper.QSTN_COLUMN_ID + "=" + rowid;
			if (selection != null && !"".equals(selection.trim())) {
				where += " and " + selection;
			}
			cursor = db.query(DBHelper.QSTN_TABLE_NAME, projection, where,
					selectionArgs, null, null, sortOrder);
			break;
		case QSTN_CATG:
			// format:authority/table1/table2
			String questionTable = uri.getPathSegments().get(0);
			String categoryTable = uri.getPathSegments().get(1);
			Log.i(TAG, questionTable + "--" + categoryTable);

			if (questionTable.equalsIgnoreCase(DBHelper.QSTN_TABLE_NAME)) {
				if (categoryTable.equalsIgnoreCase(DBHelper.CATG_TABLE_NAME)) {
					if (selection != null) {
						if (selection.length() > 0) {
							selection += " and " + DBHelper.QSTN_TABLE_NAME
									+ "." + DBHelper.QSTN_COLUMN_CATG_ID
									+ " = " + DBHelper.CATG_TABLE_NAME + "."
									+ DBHelper.CATG_COLUMN_ID;
						} else {
							selection = DBHelper.QSTN_TABLE_NAME + "."
									+ DBHelper.QSTN_COLUMN_CATG_ID + " = "
									+ DBHelper.CATG_TABLE_NAME + "."
									+ DBHelper.CATG_COLUMN_ID;
						}
					} else {
						selection = DBHelper.QSTN_TABLE_NAME + "."
								+ DBHelper.QSTN_COLUMN_CATG_ID + "="
								+ DBHelper.CATG_TABLE_NAME + "."
								+ DBHelper.CATG_COLUMN_ID;
					}
					cursor = db.query(questionTable + " inner join "
							+ categoryTable, projection, selection,
							selectionArgs, null, null, sortOrder);
				}
			}

			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}
		cursor.setNotificationUri(mContentResolver, uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case ALL_ROWS:
			return "vnd.android.cursor.dir/question";// 操作的数据属于集合类型
		case SINGLE_ROW:
			return "vnd.android.cursor.item/question";// 操作的数据属于非集合类型

		default:
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db;
		try {
			db = mDBHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = mDBHelper.getReadableDatabase();
		}

		long rowid = db.insert(DBHelper.QSTN_TABLE_NAME, null, values);
		if (rowid > 0) {
			Uri insertUri = ContentUris.withAppendedId(CONTENT_URI, rowid);
			mContentResolver.notifyChange(uri, null);// 发出数据变化通知
			return insertUri;
		} else {
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		int num = 0;
		switch (URI_MATCHER.match(uri)) {
		case ALL_ROWS:
			num = db.delete(DBHelper.QSTN_TABLE_NAME, selection, selectionArgs);
			break;
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = DBHelper.QSTN_COLUMN_ID + "=" + rowid;
			if (selection != null && !"".equals(selection.trim())) {
				where += " and " + selection;
			}
			num = db.delete(DBHelper.QSTN_TABLE_NAME, where, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}
		mContentResolver.notifyChange(uri, null);
		return num;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		int num = 0;
		switch (URI_MATCHER.match(uri)) {
		case ALL_ROWS:
			num = db.update(DBHelper.QSTN_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = DBHelper.QSTN_COLUMN_ID + "=" + rowid;
			if (selection != null && !"".equals(selection.trim())) {
				where += " and " + selection;
			}
			num = db.update(DBHelper.QSTN_TABLE_NAME, values, where,
					selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}
		mContentResolver.notifyChange(uri, null);
		return num;
	}

}
