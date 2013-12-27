package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "itservice.db";// 数据库名称
	public static final int DB_VERSION = 2;// 数据库版本

	/* 问题表 start */
	public static final String QSTN_TABLE_NAME = "question";// 表名称

	public static final String QSTN_COLUMN_ID = "_id";// 主键，自动增长
	public static final String QSTN_COLUMN_CATG_ID = "qstn_catg_id";// 问题类别id
	public static final String QSTN_COLUMN_GDE_ID = "qstn_gde_id";// 等级id

	public static final String QSTN_COLUMN_HELPER_ID = "qstn_helper_id";// 处理人id
	public static final String QSTN_COLUMN_EQUIP_ID = "qstn_epuip_id";// 设备id
	public static final String QSTN_COLUMN_START_TIME = "qstn_start_time";// 开始时间

	public static final String QSTN_COLUMN_END_TIME = "qstn_end_time";// 处理完毕时间
	public static final String QSTN_COLUMN_STATUS = "qstn_status";// 状态
	public static final String QSTN_COLUMN_COMMENTS = "comments";// 注解
	/* 问题表 end */

	/* 评论表 start */
	public static final String RVW_TABLE_NAME = "review";// 表名称

	public static final String RVW_COLUMN_QSTN_ID = "rvw_qstn_id";// 问题id
	public static final String RVW_COLUMN_CONTENT = "rvw_content";// 评论内容
	public static final String RVW_COLUMN_TIME = "rvw_time";// 评论时间
	/* 评论表 end */

	/* 等级表 start */
	public static final String GDE_TABLE_NAME = "grade";// 表名称

	public static final String GDE_COLUMN_ID = "_id";// 主键，自动增长
	public static final String GDE_COLUMN_LEVEL = "gde_level";// 等级
	public static final String GDE_COLUMN_DESC = "gde_desc";// 描述
	/* 等级表 end */

	/* 问题类别表 start */
	public static final String CATG_TABLE_NAME = "qstn_category";// 表名称

	public static final String CATG_COLUMN_ID = "_id";// 主键，自动增长
	public static final String CATG_COLUMN_QSTN_CATG = "qstn_catg";// 问题类别
	public static final String CATG_COLUMN_QSTN_CATG_DETAIL = "qstn_catg_detail";// 问题类别详细
	/* 问题类别表 end */

	// 创建问题表SQL语句
	public static final String CREATE_QUESTION_TABLE_SQL = "create table "
			+ QSTN_TABLE_NAME + "(" + QSTN_COLUMN_ID
			+ " integer primary key autoincrement, " + QSTN_COLUMN_CATG_ID
			+ " varchar(20), " + QSTN_COLUMN_GDE_ID + " varchar(20), "
			+ QSTN_COLUMN_HELPER_ID + " varchar(20), " + QSTN_COLUMN_EQUIP_ID
			+ " varchar(10), " + QSTN_COLUMN_START_TIME + " varchar(10), "
			+ QSTN_COLUMN_END_TIME + " varchar(10), " + QSTN_COLUMN_STATUS
			+ " varchar(10), " + QSTN_COLUMN_COMMENTS + " varchar(45)" + ")";

	// 创建问题类别表SQL语句
	public static final String CREATE_QSTN_CATEGORY_TABLE_SQL = "create table "
			+ CATG_TABLE_NAME + "(" + CATG_COLUMN_ID
			+ " integer primary key autoincrement, " + CATG_COLUMN_QSTN_CATG
			+ " varchar(20), " + CATG_COLUMN_QSTN_CATG_DETAIL + " varchar(20)"
			+ ")";

	// 创建等级表SQL语句
	public static final String CREATE_GRADE_TABLE_SQL = "create table "
			+ GDE_TABLE_NAME + "(" + GDE_COLUMN_ID
			+ " integer primary key autoincrement, " + GDE_COLUMN_LEVEL
			+ " varchar(20), " + GDE_COLUMN_DESC + " varchar(20)" + ")";

	// 创建评论表SQL语句
	public static final String CREATE_REVIEW_TABLE_SQL = "create table "
			+ RVW_TABLE_NAME + "(" + RVW_COLUMN_QSTN_ID + " integer, "
			+ RVW_COLUMN_CONTENT + " varchar(20), " + RVW_COLUMN_TIME
			+ " varchar(20)" + ")";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	// 删除数据库
	public boolean deleteDB(Context context) {
		return context.deleteDatabase(DB_NAME);
	}

	/**
	 * 数据库第一次被创建时调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_QUESTION_TABLE_SQL);
		db.execSQL(CREATE_QSTN_CATEGORY_TABLE_SQL);
		db.execSQL(CREATE_GRADE_TABLE_SQL);
		db.execSQL(CREATE_REVIEW_TABLE_SQL);
	}

	/**
	 * 数据库版本发生变化时调用
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
