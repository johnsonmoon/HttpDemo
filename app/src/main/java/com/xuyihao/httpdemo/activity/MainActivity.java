package com.xuyihao.httpdemo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xuyihao.httpdemo.R;
import com.xuyihao.url.connectors.HttpUtil;
import com.xuyihao.url.enums.Platform;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private EditText textMainInformationDisplay;
    private EditText textURL;
    private EditText textParameterKey;
    private EditText textParameterValue;
    private Button btnAddParameter;
    private Button btnSendRequest;
    private Button btnGetSessionID;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPost;
    private RadioButton radioButtonGet;

    private HttpUtil httpUtil = new HttpUtil(Platform.LINUX);
    private String UseMethod = "POST";
    private HashMap<String, String> parameters = new HashMap<>();
    private String actionURL = "http://115.28.192.61:8088/EBwebTest/Accounts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textMainInformationDisplay = (EditText)this.findViewById(R.id.MainActivity_text_MainInformation_display);
        this.textURL = (EditText)this.findViewById(R.id.mainActivity_EditText_URL);
        this.textParameterKey = (EditText)this.findViewById(R.id.mainActivity_EditText_Parameter_Key);
        this.textParameterValue = (EditText)this.findViewById(R.id.mainActivity_EditText_Parameter_Value);
        this.btnAddParameter = (Button)this.findViewById(R.id.mainActivity_button_add_parameter);
        this.btnSendRequest = (Button)this.findViewById(R.id.mainActivity_button_send_request);
        this.btnGetSessionID = (Button)this.findViewById(R.id.mainActivity_button_Get_sessionID);
        this.radioGroup = (RadioGroup)this.findViewById(R.id.mainActivity_RadioGroup);
        this.radioButtonPost = (RadioButton)this.findViewById(R.id.mainActivity_radioButton_POST);
        this.radioButtonGet = (RadioButton)this.findViewById(R.id.mainActivity_radioButton_GET);
        this.textURL.setText("http://115.28.192.61:8088/EBwebTest/Accounts");
        this.initEvent();
    }

    public void initEvent(){
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MainActivity.this.textMainInformationDisplay.setText("");
                if(checkedId == radioButtonGet.getId()){
                    MainActivity.this.UseMethod = "GET";
                    MainActivity.this.textMainInformationDisplay.setText("Use Get");
                }else if(checkedId == radioButtonPost.getId()){
                    MainActivity.this.UseMethod = "POST";
                    MainActivity.this.textMainInformationDisplay.setText("Use Post");
                }else {
                }
            }
        });
        this.btnAddParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.textMainInformationDisplay.setText("");
                String key = "", value = "";
                key = MainActivity.this.textParameterKey.getText().toString().trim();
                value = MainActivity.this.textParameterValue.getText().toString().trim();
                if ((key.equals("")) || (value.equals(""))) {
                    MainActivity.this.textMainInformationDisplay.setText("Key or value wrong!!!! please input again!!!");
                } else {
                    MainActivity.this.parameters.put(key, value);
                    MainActivity.this.textMainInformationDisplay.setText(key + "=" + value + "  -->added!!!");
                }
                MainActivity.this.textParameterKey.setText("");
                MainActivity.this.textParameterValue.setText("");
            }
        });
        this.btnGetSessionID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.textMainInformationDisplay.setText("");
                MainActivity.this.actionURL = MainActivity.this.textURL.getText().toString().trim();
                if(MainActivity.this.actionURL.substring(0, 7).equals("http://") || MainActivity.this.actionURL.substring(0, 7).equals("https:/")){
                    new AsyncTask(){
                        @Override
                        protected Object doInBackground(Object[] params) {
                            return MainActivity.this.httpUtil.getSessionIDFromCookie(MainActivity.this.actionURL);
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            if(o == true){
                                MainActivity.this.textMainInformationDisplay.setText("SessionID get successfully!!!");
                            }else{
                                MainActivity.this.textMainInformationDisplay.setText("Sorry, error occurred while getting session id!!!");
                            }
                        }
                    }.execute();
                }else {
                    MainActivity.this.textMainInformationDisplay.setText("URL entered incorrect!!! Please enter it correctly!!!");
                }
            }
        });
        this.btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.textMainInformationDisplay.setText("");
                MainActivity.this.actionURL = MainActivity.this.textURL.getText().toString().trim();
                if(MainActivity.this.actionURL.substring(0, 7).equals("http://") || MainActivity.this.actionURL.substring(0, 7).equals("https:/")){
                    new AsyncTask(){
                        @Override
                        protected Object doInBackground(Object[] params) {
                            if(MainActivity.this.UseMethod.equals("POST")){
                                return MainActivity.this.httpUtil.executePost(MainActivity.this.actionURL, MainActivity.this.parameters);
                            }else if(MainActivity.this.UseMethod.equals("GET")){
                                return MainActivity.this.httpUtil.executeGet(MainActivity.this.actionURL, MainActivity.this.parameters);
                            }else{
                                return "error!!!";
                            }
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            MainActivity.this.textMainInformationDisplay.setText(o.toString());
                        }
                    }.execute();
                }else {
                    MainActivity.this.textMainInformationDisplay.setText("URL entered incorrect!!! Please enter it correctly!!!");
                }
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
