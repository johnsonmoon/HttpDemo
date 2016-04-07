package com.xuyihao.httpdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xuyihao.httpdemo.R;
import com.xuyihao.httpdemo.tools.WebConnect;

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText txtAcc_name;
    private EditText txtAcc_pwd;
    private EditText txtAcc_sex;
    private EditText txtAcc_loc;
    private EditText txtResult;
    private Button btnRegist;
    private Button btnLogin;

    private JSONObject jsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAcc_name = (EditText)findViewById(R.id.mainactivity_editText_Acc_name);
        txtAcc_pwd = (EditText)findViewById(R.id.mainactivity_editText_Acc_pwd);
        txtAcc_sex = (EditText)findViewById(R.id.mainactivity_editText_Acc_sex);
        txtAcc_loc = (EditText)findViewById(R.id.mainactivity_editText_Acc_loc);
        txtResult = (EditText)findViewById(R.id.mainactivity_editText_result);
        btnRegist = (Button)findViewById(R.id.mainactivity_button_regist);
        btnLogin = (Button)findViewById(R.id.mainactivity_button_login);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "regist");
                map.put("Acc_name", txtAcc_name.getText().toString().trim());
                map.put("Acc_pwd", txtAcc_pwd.getText().toString().trim());
                map.put("Acc_sex", txtAcc_sex.getText().toString().trim());
                map.put("Acc_loc", txtAcc_loc.getText().toString().trim());
                WebConnect.post("Accounts", new RequestParams(map), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        MainActivity.this.jsonObject = response;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        MainActivity.this.jsonObject = errorResponse;
                    }
                });
                MainActivity.this.txtResult.setText(MainActivity.this.jsonObject.toString());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "login");
                map.put("Acc_name", txtAcc_name.getText().toString().trim());
                map.put("Acc_pwd", txtAcc_pwd.getText().toString().trim());
                WebConnect.post("Accounts", new RequestParams(map), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        MainActivity.this.jsonObject = response;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        MainActivity.this.jsonObject = errorResponse;
                    }
                });
                MainActivity.this.txtResult.setText(MainActivity.this.jsonObject.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
