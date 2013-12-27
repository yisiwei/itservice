package cn.mointe.itservice.it.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import cn.mointe.itservice.R;
import cn.mointe.itservice.it.adapter.QstnCategoryAdapter;
import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.provider.QuestionProvider;

@SuppressLint("NewApi")
public class QuestionListActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = "MainActivity";

	private ActionBar mBar;// actionBar
	private View mView;// 自定义actionBar 按钮

	private String mEQPid;// 设备ID
	private String mHelperid;// 处理人ID

	private ListView mQuestionView;// 问题列表
	private QstnCategoryAdapter mQstnCategoryAdapter;// 自定义CursorAdapter
	private LoaderManager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_list);

		Intent intent = getIntent();
		mEQPid = intent.getStringExtra("eqpid");// 扫码得到的设备ID
		mHelperid = intent.getStringExtra("helperid");// 处理人ID

		mQuestionView = (ListView) this.findViewById(R.id.lv_questionList);

		mManager = getLoaderManager();
		mManager.initLoader(1000, null, this);

		mQstnCategoryAdapter = new QstnCategoryAdapter(this, null, true);
		mQuestionView.setAdapter(mQstnCategoryAdapter);

		// ******************************* actionBar ***********************
		mView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
		mBar = getActionBar();
		mBar.setDisplayHomeAsUpEnabled(true);
		// 显示自定义actionBar
		mBar.setDisplayShowCustomEnabled(true);
		// 自定义actionBar
		mBar.setCustomView(mView, new ActionBar.LayoutParams(
				LayoutParams.WRAP_CONTENT, 80, Gravity.RIGHT
						| Gravity.CENTER_VERTICAL));
	}

	// 新建问题
	public void addQuestion(View v) {
		Intent intent = new Intent(this, AddQuestionActivity.class);
		intent.putExtra("eqpid", mEQPid);
		intent.putExtra("helperid", mHelperid);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.i(TAG, "onCreateLoader...");
		CursorLoader loader = new CursorLoader(this);
		String[] projection = { "question." + DBHelper.QSTN_COLUMN_ID,
				"question." + DBHelper.QSTN_COLUMN_CATG_ID,
				"qstn_category." + DBHelper.CATG_COLUMN_QSTN_CATG,
				"qstn_category." + DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL,
				"question." + DBHelper.QSTN_COLUMN_START_TIME,
				"question." + DBHelper.QSTN_COLUMN_STATUS };
		Uri uri = Uri.withAppendedPath(QuestionProvider.CONTENT_URI,
				DBHelper.CATG_TABLE_NAME);
		loader.setUri(uri);
		loader.setProjection(projection);
		loader.setSelection("question." + DBHelper.QSTN_COLUMN_EQUIP_ID + "=?");
		loader.setSelectionArgs(new String[] { mEQPid });
		loader.setSortOrder("question." + DBHelper.QSTN_COLUMN_START_TIME
				+ " desc");
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.i(TAG, "onLoadFinished...");
		mQstnCategoryAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.i(TAG, "onLoaderReset...");
		mQstnCategoryAdapter.swapCursor(null);
	}

}
