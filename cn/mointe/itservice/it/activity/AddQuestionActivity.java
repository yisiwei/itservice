package cn.mointe.itservice.it.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.mointe.itservice.R;
import cn.mointe.itservice.it.dao.QstnCategoryDao;
import cn.mointe.itservice.it.dao.QuestionDao;
import cn.mointe.itservice.it.domain.Question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQuestionActivity extends Activity {

	private Spinner mCategoryView;
	private ArrayAdapter<String> mCategoryAdapter;
	private List<String> mCategoryList;

	private Spinner mCategoryDetailView;
	private ArrayAdapter<String> mCategoryDetailAdapter;
	private List<Map<String, Object>> mCategoryDetailList;

	private EditText mCommentsView;

	private QstnCategoryDao mCategoryDao;
	private QuestionDao mQuestionDao;

	private String mCategory;
	private String mCategoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_question);

		mCategoryDao = new QstnCategoryDao(this);
		mQuestionDao = new QuestionDao(this);

		// ��ʼ���ؼ�
		mCategoryView = (Spinner) this.findViewById(R.id.spn_category);
		mCategoryDetailView = (Spinner) this
				.findViewById(R.id.spn_category_datail);
		mCommentsView = (EditText) this.findViewById(R.id.et_comments);

		// ��ѯ�������
		mCategoryList = mCategoryDao.getQstnCategory();
		for (int i = 0; i < mCategoryList.size(); i++) {
			System.out.println("---->" + mCategoryList.get(i));
		}
		// ������
		mCategoryAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mCategoryList);
		mCategoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategoryView.setAdapter(mCategoryAdapter);

		mCategoryView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mCategory = mCategoryList.get(position);
				// ��������ѯ�������ϸ��ID
				mCategoryDetailList = mCategoryDao
						.getQstnCategoryDetail(mCategory);
				List<String> details = new ArrayList<String>();
				// ѭ�������������ϸ��ʾ��spinner
				for (Map<String, Object> map : mCategoryDetailList) {
					details.add(map.get("detail").toString());
				}
				mCategoryDetailAdapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.simple_spinner_item,
						details);
				mCategoryDetailAdapter
						.setDropDownViewResource(R.layout.simple_spinner_item);
				mCategoryDetailView.setAdapter(mCategoryDetailAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mCategoryDetailView
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// �õ����ID
						mCategoryId = mCategoryDetailList.get(position)
								.get("id").toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
	}

	public void btnClick(View v) {

		switch (v.getId()) {
		case R.id.btn_add_question:// �½�����
			Intent intent = getIntent();
			String eqpid = intent.getStringExtra("eqpid");
			String helperid = intent.getStringExtra("heplerid");

			String comments = mCommentsView.getText().toString();

			Question question = new Question();
			question.setEpuipid(eqpid);
			question.setHelperid(helperid);
			question.setStatus("������");
			question.setComments(comments);
			question.setCatgid(mCategoryId);

			boolean b = mQuestionDao.addQuestion(question);
			if (b) {
				Toast.makeText(this, "�½��ɹ�", Toast.LENGTH_SHORT).show();
				this.finish();
			} else {
				Toast.makeText(this, "�½�ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.btn_cancel_question:
			this.finish();
			break;
		default:
			break;
		}

	}

}
