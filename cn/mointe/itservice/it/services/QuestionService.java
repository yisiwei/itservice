package cn.mointe.itservice.it.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.domain.Question;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuestionService {

	private DBHelper mDBHelper;

	public QuestionService(Context context) {
		mDBHelper = new DBHelper(context);
	}

	/**
	 * 
	 * 根据设备ID查询问题及问题类别
	 * 
	 * @param eqpid
	 * @return List<Question>
	 */
	public List<Question> getQuestions(String eqpid, String helperid) {
		List<Question> questions = new ArrayList<Question>();

		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String sql = "select t1." + DBHelper.QSTN_COLUMN_ID + ",t1."
				+ DBHelper.QSTN_COLUMN_START_TIME + ",t1."
				+ DBHelper.QSTN_COLUMN_STATUS + ",t2."
				+ DBHelper.CATG_COLUMN_QSTN_CATG + ",t2."
				+ DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL + " from "
				+ DBHelper.QSTN_TABLE_NAME + " t1," + DBHelper.CATG_TABLE_NAME
				+ " t2 " + " where t1." + DBHelper.QSTN_COLUMN_CATG_ID
				+ " = t2." + DBHelper.CATG_COLUMN_ID + " and t1."
				+ DBHelper.QSTN_COLUMN_EQUIP_ID + "=? and t1."
				+ DBHelper.QSTN_COLUMN_HELPER_ID + "=? order by t1."
				+ DBHelper.QSTN_COLUMN_START_TIME + " desc";
		Cursor cursor = db.rawQuery(sql, new String[] { eqpid, helperid });

		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_ID));
			String starttime = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME));
			String status = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_STATUS));
			String category = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG));
			String categorydetail = cursor.getString(cursor
					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL));

			Question question = new Question();
			question.setId(id);
			question.setStarttime(starttime);
			question.setStatus(status);
			question.setCategory(category);
			question.setCategorydetail(categorydetail);

			questions.add(question);
		}

		cursor.close();
		return questions;
	}

	/**
	 * 
	 * 根据设备ID和处理人ID将已分配问题状态改为处理中
	 * 
	 * @param eqpid
	 * @param helperid
	 * @return List<Question>
	 */
	public void updateQuestionStatus(String eqpid, String helperid) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "update " + DBHelper.QSTN_TABLE_NAME + " set "
				+ DBHelper.QSTN_COLUMN_STATUS + "=? where "
				+ DBHelper.QSTN_COLUMN_EQUIP_ID + "=? and "
				+ DBHelper.QSTN_COLUMN_HELPER_ID + "=? and "
				+ DBHelper.QSTN_COLUMN_STATUS + "=?";
		db.execSQL(sql, new Object[] { "处理中", eqpid, helperid, "已分配" });
		db.close();
	}

	/**
	 * 问题处理完毕
	 * 
	 * @param question
	 *            void
	 */
	public boolean updateStatus(int questionid) {
		boolean flag = false;
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "update " + DBHelper.QSTN_TABLE_NAME + " set "
				+ DBHelper.QSTN_COLUMN_STATUS + "=?,"
				+ DBHelper.QSTN_COLUMN_END_TIME + "=?,"
				+ DBHelper.QSTN_COLUMN_GDE_ID + "=? where "
				+ DBHelper.QSTN_COLUMN_ID + "=?";
		try {
			db.execSQL(
					sql,
					new Object[] {
							"处理完毕",
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date()), 5, questionid });
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();
		return flag;
	}

	/**
	 * 分配处理人
	 * 
	 * @param helperid
	 * @param qstnid
	 *            void
	 */
	public void updateHelper(String helperid, String qstnid) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "update " + DBHelper.QSTN_TABLE_NAME + " set "
				+ DBHelper.QSTN_COLUMN_HELPER_ID + "=? where "
				+ DBHelper.QSTN_COLUMN_ID + "=?";
		db.execSQL(sql, new Object[] { helperid, qstnid });
	}

	/**
	 * 修改评论等级
	 * 
	 * @param gradeid
	 * @param qstnid
	 *            void
	 */
	public void updateGrade(String gradeid, String qstnid) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "update " + DBHelper.QSTN_TABLE_NAME + " set "
				+ DBHelper.QSTN_COLUMN_GDE_ID + "=? where "
				+ DBHelper.QSTN_COLUMN_ID + "=?";
		db.execSQL(sql, new Object[] { gradeid, qstnid });
	}

}
