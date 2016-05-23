package com.xuyihao.httpdemo.activity;

import android.content.Intent;
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
import android.widget.TextView;

import com.xuyihao.httpdemo.R;
import com.xuyihao.url.connectors.DownUtil;
import com.xuyihao.url.connectors.HttpUtil;
import com.xuyihao.url.enums.MIME_FileType;
import com.xuyihao.url.enums.Platform;

import java.io.File;
import java.util.HashMap;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private EditText textMainInformationDisplay;
    private EditText textShowParameters;
    private EditText textURL;
    private EditText textParameterKey;
    private EditText textParameterValue;
    private TextView txtShowChosenFilePath;
    private Button btnAddParameter;
    private Button btnClearParameters;
    private Button btnSendRequest;
    private Button btnGetSessionID;
    private Button btnDeleteSession;
    private Button btnChooseFile;
    private Button btnSendRequestAndFile;
    private Button btnChooseSavePath;
    private Button btnDownloadFile;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPost;
    private RadioButton radioButtonGet;

    private String filePathName = "/";
    private HttpUtil httpUtil = new HttpUtil(Platform.LINUX);
    private DownUtil downUtil = new DownUtil();
    private String UseMethod = "POST";
    private HashMap<String, String> parameters = new HashMap<>();
    private String actionURL = "http://115.28.192.61:8088/EBwebTest/Accounts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textMainInformationDisplay = (EditText)this.findViewById(R.id.MainActivity_text_MainInformation_display);
        this.textShowParameters = (EditText)this.findViewById(R.id.MainActivity_editText_ShowParameters);
        this.textURL = (EditText)this.findViewById(R.id.mainActivity_EditText_URL);
        this.textParameterKey = (EditText)this.findViewById(R.id.mainActivity_EditText_Parameter_Key);
        this.textParameterValue = (EditText)this.findViewById(R.id.mainActivity_EditText_Parameter_Value);
        this.txtShowChosenFilePath = (TextView)this.findViewById(R.id.MainActivity_textView_Chosen_File_Path);
        this.btnAddParameter = (Button)this.findViewById(R.id.mainActivity_button_add_parameter);
        this.btnClearParameters = (Button)this.findViewById(R.id.Mainactivity_button_Clear_Parameters);
        this.btnSendRequest = (Button)this.findViewById(R.id.mainActivity_button_send_request);
        this.btnGetSessionID = (Button)this.findViewById(R.id.mainActivity_button_Get_sessionID);
        this.btnDeleteSession = (Button)this.findViewById(R.id.Mainactivity_button_delete_session);
        this.btnChooseFile = (Button)this.findViewById(R.id.MainActivity_button_chooseFile);
        this.btnSendRequestAndFile = (Button)this.findViewById(R.id.Mainactivity_button_send_RequestAndFile);
        this.btnChooseSavePath = (Button)this.findViewById(R.id.MainActivity_button_getDownloadPath);
        this.btnDownloadFile = (Button)this.findViewById(R.id.MainActivity_button_begin_download);
        this.radioGroup = (RadioGroup)this.findViewById(R.id.mainActivity_RadioGroup);
        this.radioButtonPost = (RadioButton)this.findViewById(R.id.mainActivity_radioButton_POST);
        this.radioButtonGet = (RadioButton)this.findViewById(R.id.mainActivity_radioButton_GET);
        this.textURL.setText("http://");
        this.txtShowChosenFilePath.setText(this.filePathName);
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
                    MainActivity.this.textShowParameters.setText(MainActivity.this.getParameterFormat(MainActivity.this.parameters));
                    MainActivity.this.textMainInformationDisplay.setText(key + "=" + value + "  -->added!!!");
                }
                MainActivity.this.textParameterKey.setText("");
                MainActivity.this.textParameterValue.setText("");
            }
        });
        this.btnClearParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.parameters.clear();
                MainActivity.this.textShowParameters.setText("");
                MainActivity.this.textParameterKey.setText("");
                MainActivity.this.textParameterValue.setText("");
                MainActivity.this.textMainInformationDisplay.setText("Parameters clear successfully!");
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
        this.btnDeleteSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.httpUtil.invalidateSessionID();
                MainActivity.this.textMainInformationDisplay.setText("Session invalidated!!!");
            }
        });
        this.btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.textMainInformationDisplay.setText("Sending.....");
                MainActivity.this.actionURL = MainActivity.this.textURL.getText().toString().trim();
                if(MainActivity.this.actionURL.substring(0, 7).equals("http://") || MainActivity.this.actionURL.substring(0, 7).equals("https:/")){
                    new AsyncTask(){
                        @Override
                        protected Object doInBackground(Object[] params) {
                            if(MainActivity.this.UseMethod.equals("POST")){
                                return MainActivity.this.httpUtil.executePostByUsual(MainActivity.this.actionURL, MainActivity.this.parameters);
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
                    MainActivity.this.textParameterKey.setText("");
                    MainActivity.this.textParameterValue.setText("");
                }else {
                    MainActivity.this.textMainInformationDisplay.setText("URL entered incorrect!!! Please enter it correctly!!!");
                }
            }
        });
        this.btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseFileOrPathActivity.class);
                startActivityForResult(intent, 0x11);
                MainActivity.this.txtShowChosenFilePath.setText(MainActivity.this.filePathName);
            }
        });
        this.btnSendRequestAndFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(MainActivity.this.filePathName);
                boolean isDir = file.isDirectory();
                if(MainActivity.this.filePathName.equals("") || isDir){
                    MainActivity.this.textMainInformationDisplay.setText("Please choose a file to be sent!!!");
                }else{
                    MainActivity.this.textMainInformationDisplay.setText("Sending.....");
                    MainActivity.this.actionURL = MainActivity.this.textURL.getText().toString().trim();
                    if(MainActivity.this.actionURL.substring(0, 7).equals("http://") || MainActivity.this.actionURL.substring(0, 7).equals("https:/")){
                        new AsyncTask(){
                            @Override
                            protected Object doInBackground(Object[] params) {
                                return MainActivity.this.httpUtil.singleFileUploadWithParameters(MainActivity.this.actionURL, MainActivity.this.filePathName, MIME_FileType.Application_bin, MainActivity.this.parameters);
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                MainActivity.this.textMainInformationDisplay.setText(o.toString());
                            }
                        }.execute();
                        MainActivity.this.textParameterKey.setText("");
                        MainActivity.this.textParameterValue.setText("");
                    }else {
                        MainActivity.this.textMainInformationDisplay.setText("URL entered incorrect!!! Please enter it correctly!!!");
                    }
                }
            }
        });
        this.btnChooseSavePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseFileOrPathActivity.class);
                startActivityForResult(intent, 0x11);
                MainActivity.this.txtShowChosenFilePath.setText(MainActivity.this.filePathName);
            }
        });
        this.btnDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(MainActivity.this.filePathName);
                boolean isDir = file.isDirectory();
                if(!isDir){
                    MainActivity.this.textMainInformationDisplay.setText("Please choose a director to be save downloaded file!!!");
                }else{
                    MainActivity.this.textMainInformationDisplay.setText("Downloading.....");
                    MainActivity.this.actionURL = MainActivity.this.textURL.getText().toString().trim();
                    if(MainActivity.this.actionURL.substring(0, 7).equals("http://") || MainActivity.this.actionURL.substring(0, 7).equals("https:/")){
                        new AsyncTask(){
                            @Override
                            protected Object doInBackground(Object[] params) {
                                new Thread(){
                                    @Override
                                    public void run(){
                                        while(true) {
                                            try {
                                                double rawRate = MainActivity.this.downUtil.getCompleteRate();
                                                if (rawRate >= 1.0 || rawRate == -1.0){
                                                    break;
                                                }else{
                                                    publishProgress(rawRate);
                                                }
                                                Thread.sleep(1000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }.start();
                                if(MainActivity.this.parameters.isEmpty()){//parameter empty, download by URL directly
                                    if(MainActivity.this.downUtil.downloadByGetSaveToPath(MainActivity.this.filePathName + "/", MainActivity.this.actionURL)){
                                        return "Download successfully!!!";
                                    }else{
                                        if (MainActivity.this.downUtil.downloadByGet(MainActivity.this.filePathName + "/" + "newFile", MainActivity.this.actionURL)) {
                                            return "Download successfully. Can not get the file name from the server, file saved as "+MainActivity.this.filePathName+"/newFile, please check it later by yourself!";
                                        }else {
                                            return "Download failed!!!";
                                        }
                                    }
                                }else{
                                    if(MainActivity.this.downUtil.downloadByPostSaveToPath(MainActivity.this.filePathName + "/", MainActivity.this.actionURL, MainActivity.this.parameters)){
                                        return "Download successfully!!!";
                                    }else {
                                        if (MainActivity.this.downUtil.downloadByPost(MainActivity.this.filePathName + "/" + "newFile", MainActivity.this.actionURL, MainActivity.this.parameters)) {
                                            return "Download successfully. Can not get the file name from the server, file saved as "+MainActivity.this.filePathName+"/newFile, please check it later by yourself!";
                                        }else {
                                            return "Download failed!!!";
                                        }
                                    }
                                }
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                MainActivity.this.textMainInformationDisplay.setText(o.toString());
                            }

                            @Override
                            protected void onProgressUpdate(Object[] values) {
                                super.onProgressUpdate(values);
                                String rate = String.valueOf(Double.valueOf(values[0].toString()) * 100.0D);
                                rate = rate.substring(0, rate.indexOf(".") + 2) + "%";
                                MainActivity.this.textMainInformationDisplay.setText("Downloading........  " + rate);
                            }
                        }.execute();
                        MainActivity.this.textParameterKey.setText("");
                        MainActivity.this.textParameterValue.setText("");
                    }else {
                        MainActivity.this.textMainInformationDisplay.setText("URL entered incorrect!!! Please enter it correctly!!!");
                    }
                }
            }
        });
    }

    private String getParameterFormat(HashMap<String, String> parameters){
        String show = "";
        Set<String> keys = parameters.keySet();
        for(String key : keys){
            show = show + key + "=" + parameters.get(key) + "&";
        }
        show = show.substring(0, show.lastIndexOf("&"));
        return show;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x11 && resultCode == 0x11){
            Bundle bundle = data.getExtras();
            this.filePathName = bundle.getString("path");
            this.txtShowChosenFilePath.setText(this.filePathName);
        }
    }
}
