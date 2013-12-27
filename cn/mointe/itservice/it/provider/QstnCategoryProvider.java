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
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 问题类别 ContnetProvider
 * 
 */
public class QstnCategoryProvider extends ContentProvider {

	private DBHelper mDBHelper;
	private ContentResolver mContentResolver;

	private static final UriMatcher URI_MATCHER;
	private static final int ALL_ROWS = 1;
	private static final int SINGLE_ROW = 2;

	private static final String AUTHORITIES = "cn.mointe.itservice.providers.QstnCategoryProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES
			+ "/qstn_category");

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITIES, "qstn_category", ALL_ROWS);
		URI_MATCHER.addURI(AUTHORITIES, "qstn_category/#", SINGLE_ROW);
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

		String groupBy = null;
		String having = null;
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (URI_MATCHER.match(uri)) {
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = (!TextUtils.isEmpty(selection)) ? " and "
					+ selection : "";
			queryBuilder.appendWhere(DBHelper.CATG_COLUMN_ID + "=" + rowid
					+ where);
			break;
		default:
			break;
		}
		queryBuilder.setTables(DBHelper.CATG_TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);
		cursor.setNotificationUri(mContentResolver, uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case ALL_ROWS:
			return "vnd.android.cursor.dir/qstn_category";// 操作的数据属于集合类型
		case SINGLE_ROW:
			return "vnd.android.cursor.item/qstn_category";// 操作的数据属于非集合类型

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

		long rowid = db.insert(DBHelper.CATG_TABLE_NAME, null, values);
		if (rowid > 0) {
			Uri insertUri = ContentUris.withAppendedId(CONTENT_URI, rowid);
			mContentResolver.notifyChange(uri, null);
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
			num = db.delete(DBHelper.CATG_TABLE_NAME, selection, selectionArgs);
			break;
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = DBHelper.CATG_COLUMN_ID + "=" + rowid;
			if (selection != null && !"".equals(selection.trim())) {
				where += " and " + selection;
			}
			num = db.delete(DBHelper.CATG_TABLE_NAME, where, selectionArgs);
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
			num = db.update(DBHelper.CATG_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case SINGLE_ROW:
			long rowid = ContentUris.parseId(uri);
			String where = DBHelper.CATG_COLUMN_ID + "=" + rowid;
			if (selection != null && !"".equals(selection.trim())) {
				where += " and " + selection;
			}
			num = db.update(DBHelper.CATG_TABLE_NAME, values, where,
					selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("this is Unknown Uri:" + uri);
		}
		mContentResolver.notifyChange(uri, null);
		return num;
	}

}
