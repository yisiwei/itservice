package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "itservice.db";// ���ݿ�����
	public static final int DB_VERSION = 2;// ���ݿ�汾

	/* ����� start */
	public static final String QSTN_TABLE_NAME = "question";// ������

	public static final String QSTN_COLUMN_ID = "_id";// �������Զ�����
	public static final String QSTN_COLUMN_CATG_ID = "qstn_catg_id";// �������id
	public static final String QSTN_COLUMN_GDE_ID = "qstn_gde_id";// �ȼ�id

	public static final String QSTN_COLUMN_HELPER_ID = "qstn_helper_id";// ������id
	public static final String QSTN_COLUMN_EQUIP_ID = "qstn_epuip_id";// �豸id
	public static final String QSTN_COLUMN_START_TIME = "qstn_start_time";// ��ʼʱ��

	public static final String QSTN_COLUMN_END_TIME = "qstn_end_time";// �������ʱ��
	public static final String QSTN_COLUMN_STATUS = "qstn_status";// ״̬
	public static final String QSTN_COLUMN_COMMENTS = "comments";// ע��
	/* ����� end */

	/* ���۱� start */
	public static final String RVW_TABLE_NAME = "review";// ������

	public static final String RVW_COLUMN_QSTN_ID = "rvw_qstn_id";// ����id
	public static final String RVW_COLUMN_CONTENT = "rvw_content";// ��������
	public static final String RVW_COLUMN_TIME = "rvw_time";// ����ʱ��
	/* ���۱� end */

	/* �ȼ��� start */
	public static final String GDE_TABLE_NAME = "grade";// ������

	public static final String GDE_COLUMN_ID = "_id";// �������Զ�����
	public static final String GDE_COLUMN_LEVEL = "gde_level";// �ȼ�
	public static final String GDE_COLUMN_DESC = "gde_desc";// ����
	/* �ȼ��� end */

	/* �������� start */
	public static final String CATG_TABLE_NAME = "qstn_category";// ������

	public static final String CATG_COLUMN_ID = "_id";// �������Զ�����
	public static final String CATG_COLUMN_QSTN_CATG = "qstn_catg";// �������
	public static final String CATG_COLUMN_QSTN_CATG_DETAIL = "qstn_catg_detail";// ���������ϸ
	/* �������� end */

	// ���������SQL���
	public static final String CREATE_QUESTION_TABLE_SQL = "create table "
			+ QSTN_TABLE_NAME + "(" + QSTN_COLUMN_ID
			+ " integer primary key autoincrement, " + QSTN_COLUMN_CATG_ID
			+ " varchar(20), " + QSTN_COLUMN_GDE_ID + " varchar(20), "
			+ QSTN_COLUMN_HELPER_ID + " varchar(20), " + QSTN_COLUMN_EQUIP_ID
			+ " varchar(10), " + QSTN_COLUMN_START_TIME + " varchar(10), "
			+ QSTN_COLUMN_END_TIME + " varchar(10), " + QSTN_COLUMN_STATUS
			+ " varchar(10), " + QSTN_COLUMN_COMMENTS + " varchar(45)" + ")";

	// ������������SQL���
	public static final String CREATE_QSTN_CATEGORY_TABLE_SQL = "create table "
			+ CATG_TABLE_NAME + "(" + CATG_COLUMN_ID
			+ " integer primary key autoincrement, " + CATG_COLUMN_QSTN_CATG
			+ " varchar(20), " + CATG_COLUMN_QSTN_CATG_DETAIL + " varchar(20)"
			+ ")";

	// �����ȼ���SQL���
	public static final String CREATE_GRADE_TABLE_SQL = "create table "
			+ GDE_TABLE_NAME + "(" + GDE_COLUMN_ID
			+ " integer primary key autoincrement, " + GDE_COLUMN_LEVEL
			+ " varchar(20), " + GDE_COLUMN_DESC + " varchar(20)" + ")";

	// �������۱�SQL���
	public static final String CREATE_REVIEW_TABLE_SQL = "create table "
			+ RVW_TABLE_NAME + "(" + RVW_COLUMN_QSTN_ID + " integer, "
			+ RVW_COLUMN_CONTENT + " varchar(20), " + RVW_COLUMN_TIME
			+ " varchar(20)" + ")";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	// ɾ�����ݿ�
	public boolean deleteDB(Context context) {
		return context.deleteDatabase(DB_NAME);
	}

	/**
	 * ���ݿ��һ�α�����ʱ����
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_QUESTION_TABLE_SQL);
		db.execSQL(CREATE_QSTN_CATEGORY_TABLE_SQL);
		db.execSQL(CREATE_GRADE_TABLE_SQL);
		db.execSQL(CREATE_REVIEW_TABLE_SQL);
	}

	/**
	 * ���ݿ�汾�����仯ʱ����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists" + QSTN_TABLE_NAME);
		db.execSQL("drop table if exists" + CATG_TABLE_NAME);
		db.execSQL("drop table if exists" + GDE_TABLE_NAME);
		db.execSQL("drop table if exists" + RVW_TABLE_NAME);
		onCreate(db);
	}

}
