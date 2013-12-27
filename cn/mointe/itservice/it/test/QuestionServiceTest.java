package cn.mointe.itservice.it.test;

import java.util.List;

import cn.mointe.itservice.it.domain.Question;
import cn.mointe.itservice.it.services.QuestionService;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

public class QuestionServiceTest extends AndroidTestCase {

	public final static String TAG = "MainActivity";

	public void testGetQuestions() {
		QuestionService service = new QuestionService(this.getContext());

//		Cursor cursor = service.getQuestions();
//		while (cursor.moveToNext()) {
//			String a1 = cursor.getString(cursor
//					.getColumnIndex(DBHelper.QSTN_COLUMN_ID));
//			String a2 = cursor.getString(cursor
//					.getColumnIndex(DBHelper.QSTN_COLUMN_STATUS));
//			String a3 = cursor.getString(cursor
//					.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME));
//			String a4 = cursor.getString(cursor
//					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG));
//			String a5 = cursor.getString(cursor
//					.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL));
//			Log.i(TAG, a1 + "--" + a2 + "--" + a3 + "--" + a4 + "--" + a5);
//		}
//		cursor.close();

		List<Question> list = service.getQuestions("1","1");
		for (int i = 0; i < list.size(); i++) {
			Log.i(TAG, list.get(i).getCategorydetail());
		}
	}
}
