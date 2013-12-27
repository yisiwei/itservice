package cn.mointe.itservice.it.activity;

import cn.mointe.itservice.R;
import cn.mointe.itservice.it.services.QuestionService;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private QuestionService mQuestionService;
	private String mUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mQuestionService = new QuestionService(this);
		
		Intent intent = getIntent();
		mUsername = intent.getStringExtra("username");

	}

	public void open(View v) {
		String eqpid = "1";
		// 扫码将问题状态 已分配 改为 处理中
		mQuestionService.updateQuestionStatus(eqpid, mUsername);
		Intent intent = new Intent(this, QuestionListActivity.class);
		intent.putExtra("eqpid", eqpid);
		intent.putExtra("helperid", mUsername);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
