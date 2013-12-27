package cn.mointe.itservice.it.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.domain.QstnCategory;
import cn.mointe.itservice.it.provider.QstnCategoryProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class QstnCategoryDao {

	private ContentResolver mResolver;

	public QstnCategoryDao(Context context) {
		mResolver = context.getContentResolver();
	}

	/**
	 * 查询所有类别
	 * 
	 * @return List<String>
	 */
	public List<String> getQstnCategory() {
		List<String> categorys = new ArrayList<String>();

		Cursor cursor = mResolver.query(QstnCategoryProvider.CONTENT_URI,
				new String[] { DBHelper.CATG_COLUMN_QSTN_CATG },
				"1=1) group by (" + DBHelper.CATG_COLUMN_QSTN_CATG, null, null);
		while (cursor.moveToNext()) {
			String category = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG));
			categorys.add(category);
		}
		cursor.close();
		return categorys;
	}

	/**
	 * 根据类别查询类别详细和类别ID
	 * 
	 * @param category
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getQstnCategoryDetail(String category) {
		List<Map<String, Object>> details = new ArrayList<Map<String, Object>>();

		Cursor cursor = mResolver.query(QstnCategoryProvider.CONTENT_URI, null,
				DBHelper.CATG_COLUMN_QSTN_CATG + "=?",
				new String[] { category }, null);
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			int id = cursor.getInt(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_ID));
			String detail = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL));
			map.put("id", id);
			map.put("detail", detail);
			details.add(map);
		}
		cursor.close();
		return details;
	}

	/**
	 * 根据Id查询问题类别信息
	 * 
	 * @param id
	 * @return QstnCategory
	 */
	public QstnCategory getQstnCategory(String id) {
		QstnCategory category = new QstnCategory();
		Cursor cursor = mResolver.query(QstnCategoryProvider.CONTENT_URI, null,
				DBHelper.CATG_COLUMN_ID + "=?", new String[] { id }, null);
		if (cursor.moveToFirst()) {
			category.setCategory(cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG)));
			category.setCategorydetail(cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL)));
		}
		return category;

	}

}
