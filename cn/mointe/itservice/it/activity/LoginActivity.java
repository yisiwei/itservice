package cn.mointe.itservice.it.activity;

import cn.mointe.itservice.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText mUsernameView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mUsernameView = (EditText) this.findViewById(R.id.et_username);
		mPasswordView = (EditText) this.findViewById(R.id.et_password);
	}

	public void login(View v) {
		String username = mUsernameView.getText().toString();
		String password = mPasswordView.getText().toString();
		if (username.equals("1") && password.equals("1")) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("username", username);
			startActivity(intent);
			this.finish();
		} else {
			Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
