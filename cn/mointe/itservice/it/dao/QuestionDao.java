package cn.mointe.itservice.it.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.domain.Question;
import cn.mointe.itservice.it.provider.QuestionProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class QuestionDao {

	private ContentResolver mResolver;

	public QuestionDao(Context context) {
		mResolver = context.getContentResolver();
	}

	/**
	 * 根据设备查询问题
	 * 
	 * @param eqpid
	 * @return List<Question>
	 */
	public List<Question> getQuestions(String eqpid) {
		List<Question> questions = new ArrayList<Question>();

		Cursor cursor = mResolver.query(QuestionProvider.CONTENT_URI, null,
				DBHelper.QSTN_COLUMN_EQUIP_ID + "=?", new String[] { eqpid },
				DBHelper.QSTN_COLUMN_START_TIME + " desc");
		while (cursor.moveToNext()) {
			String comments = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_COMMENTS));
			String starttime = cursor.getString(cursor
					.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME));
			Question question = new Question();
			question.setComments(comments);
			question.setStarttime(starttime);
			questions.add(question);
		}
		cursor.close();
		return questions;
	}

	/**
	 * 新建问题
	 * 
	 * @param question
	 * @return boolean
	 */
	public boolean addQuestion(Question question) {
		boolean flag = false;
		ContentValues values = new ContentValues();

		values.put(DBHelper.QSTN_COLUMN_CATG_ID, question.getCatgid());
		values.put(DBHelper.QSTN_COLUMN_COMMENTS, question.getComments());
		values.put(DBHelper.QSTN_COLUMN_START_TIME, new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));

		values.put(DBHelper.QSTN_COLUMN_EQUIP_ID, question.getEpuipid());
		values.put(DBHelper.QSTN_COLUMN_HELPER_ID, question.getHelperid());
		values.put(DBHelper.QSTN_COLUMN_STATUS, question.getStatus());

		try {
			mResolver.insert(QuestionProvider.CONTENT_URI, values);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
