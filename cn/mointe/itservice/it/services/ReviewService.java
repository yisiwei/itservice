package cn.mointe.itservice.it.services;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.domain.Review;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ReviewService {

	private DBHelper mDBHelper;

	public ReviewService(Context context) {
		mDBHelper = new DBHelper(context);
	}

	/**
	 * Ìí¼ÓÆÀÂÛ
	 * 
	 * @param review
	 * @return boolean
	 */
	public boolean addReview(Review review) {
		boolean flag = false;
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "insert into " + DBHelper.RVW_TABLE_NAME + "("
				+ DBHelper.RVW_COLUMN_QSTN_ID + ","
				+ DBHelper.RVW_COLUMN_CONTENT + "," + DBHelper.RVW_COLUMN_TIME
				+ ") values(?,?,?)";
		try {
			db.execSQL(sql,
					new Object[] { review.getQstnid(), review.getContent(),
							review.getTime() });
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();
		return flag;
	}

}
