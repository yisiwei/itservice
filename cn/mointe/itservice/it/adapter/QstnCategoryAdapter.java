package cn.mointe.itservice.it.adapter;

import cn.mointe.itservice.R;
import cn.mointe.itservice.it.db.DBHelper;
import cn.mointe.itservice.it.services.QuestionService;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class QstnCategoryAdapter extends CursorAdapter {

	private LayoutInflater mInflater;
	private Context mContext;

	private QuestionService mService;

	public QstnCategoryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.question_list_item, null);
		ViewCache cache = new ViewCache();
		cache.categoryView = (TextView) view
				.findViewById(R.id.tv_question_type);
		cache.timeView = (TextView) view.findViewById(R.id.tv_createtime);
		cache.checkBox = (CheckBox) view.findViewById(R.id.cb_finish);
		view.setTag(cache);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewCache cache = (ViewCache) view.getTag();
		cache.checkBox.setChecked(false);
		cache.checkBox.setEnabled(true);
		cache.categoryView
				.setText(cursor.getString(cursor
						.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG))
						+ "-"
						+ cursor.getString(cursor
								.getColumnIndex(DBHelper.CATG_COLUMN_QSTN_CATG_DETAIL)));
		cache.timeView.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.QSTN_COLUMN_START_TIME)));
		String status = cursor.getString(cursor
				.getColumnIndex(DBHelper.QSTN_COLUMN_STATUS));
		if (status.equals("处理完毕")) {
			cache.checkBox.setChecked(true);
			cache.checkBox.setEnabled(false);
		}
		final int questionid = cursor.getInt(cursor
				.getColumnIndex(DBHelper.QSTN_COLUMN_ID));
		mService = new QuestionService(mContext);
		cache.checkBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {

							boolean b = mService.updateStatus(questionid);
							if (b) {
								buttonView.setEnabled(false);
								Toast.makeText(mContext, "处理完毕",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mContext, "出错了",
										Toast.LENGTH_SHORT).show();
							}
						}

					}
				});
	}

	private final class ViewCache {
		public TextView categoryView;
		public TextView timeView;
		public CheckBox checkBox;
	}

}
