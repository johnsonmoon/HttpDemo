package com.xuyihao.httpdemo.activity;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xuyihao.httpdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseFileOrPathActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnConfirm;
    private Button btnClose;
    private TextView txtDisplay;
    private TextView txtChosenPath;
    private ArrayList<HashMap<String, Object>> mapList;
    private String bootPath = "/sdcard";
    private String fomalFilePath = bootPath;
    private String chosenPath = bootPath;
    private String currentPath = bootPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file_or_path);
        this.init();
        this.initEvent();
    }

    private void getFileDir(String filePath){
        try{
            this.currentPath = filePath;
            File f = new File(filePath);
            if(f.isDirectory()){
                this.mapList.clear();
                this.txtDisplay.setText("Current Path: " + filePath);
                //Set the formal path as the parent of filepath
                if(!filePath.equals(bootPath)){
                    this.fomalFilePath = f.getParent();
                }
                //List all the files in the filePath.
                File[] files = f.listFiles();
                if(files != null){
                    HashMap<String, Object> m = new HashMap<>();
                    m.put("FileName", "back to the last directory...");
                    m.put("FilePath", this.fomalFilePath);
                    m.put("FileTypeIcon", R.mipmap.icon_back);
                    this.mapList.add(m);
                    for(int i = 0; i < files.length; i++){
                        HashMap<String, Object> map = new HashMap<>();
                        File file = files[i];
                        if (file.isDirectory()){
                            map.put("FileName", file.getName());
                            map.put("FilePath", file.getPath());
                            map.put("FileTypeIcon", R.mipmap.icon_folder);
                        }else {
                            map.put("FileName", file.getName());
                            map.put("FilePath", file.getPath());
                            map.put("FileTypeIcon", this.getIconType(file.getName()));
                        }
                        this.mapList.add(map);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(this, mapList, R.layout.activity_choose_file_or_path_item, new String[]{"FileTypeIcon" ,"FileName"}, new int[]{R.id.choosePath_item_imageView ,R.id.choosePath_item_name});
                this.listView.setAdapter(adapter);
            }else{
                Toast.makeText(ChooseFileOrPathActivity.this, filePath + "       is not a directory!!!", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int getIconType(String filename){
        int flag = 0;
        String lastName = filename.substring(filename.indexOf(".") + 1);
        switch (lastName){
            case "avi":
                flag = R.mipmap.icon_avi;
                break;
            case "bin":
                flag = R.mipmap.icon_bin;
                break;
            case "bmp":
                flag = R.mipmap.icon_bmp;
                break;
            case "dll":
                flag = R.mipmap.icon_dll;
                break;
            case "doc":
                flag = R.mipmap.icon_doc;
                break;
            case "docx":
                flag = R.mipmap.icon_docx;
                break;
            case "dvd":
                flag = R.mipmap.icon_dvd;
                break;
            case "gif":
                flag = R.mipmap.icon_gif;
                break;
            case "html":
                flag = R.mipmap.icon_html;
                break;
            case "java":
                flag = R.mipmap.icon_java;
                break;
            case "jpg":
                flag = R.mipmap.icon_jpg;
                break;
            case "log":
                flag = R.mipmap.icon_log;
                break;
            case "mp3":
                flag = R.mipmap.icon_mp3;
                break;
            case "mp4":
                flag = R.mipmap.icon_mp4;
                break;
            case "mpeg":
                flag = R.mipmap.icon_mpeg;
                break;
            case "pdf":
                flag = R.mipmap.icon_pdf;
                break;
            case "png":
                flag = R.mipmap.icon_png;
                break;
            case "pptx":
                flag = R.mipmap.icon_pptx;
                break;
            case "rar":
                flag = R.mipmap.icon_rar;
                break;
            case "txt":
                flag = R.mipmap.icon_txt;
                break;
            case "wav":
                flag = R.mipmap.icon_wav;
                break;
            case "xls":
                flag = R.mipmap.icon_xls;
                break;
            case "xlsx":
                flag = R.mipmap.icon_xlsx;
                break;
            case "xml":
                flag = R.mipmap.icon_xml;
                break;
            default:
                flag = R.mipmap.icon_bat;
                break;
        }
        return flag;
    }

    private void init(){
        this.txtDisplay = (TextView)this.findViewById(R.id.activity_choose_path_text_display);
        this.txtChosenPath = (TextView)this.findViewById(R.id.ChoosePath_text_display_chosenPath);
        this.listView = (ListView)this.findViewById(R.id.ChoosePath_ListView);
        this.btnConfirm = (Button)this.findViewById(R.id.ChoosePath_button_Confirm);
        this.btnClose = (Button)this.findViewById(R.id.ChoosePath_button_Close);
        this.mapList = new ArrayList<HashMap<String, Object>>();
        this.getFileDir(bootPath);
    }

    private void initEvent(){
        this.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChooseFileOrPathActivity.this.getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("path", ChooseFileOrPathActivity.this.chosenPath);
                intent.putExtras(bundle);
                ChooseFileOrPathActivity.this.setResult(0x11, intent);
                ChooseFileOrPathActivity.this.finish();
            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFileOrPathActivity.this.finish();
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseFileOrPathActivity.this.into(position);
            }
        });
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator vibrator = (Vibrator) ChooseFileOrPathActivity.this.getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{0, 1000}, -1);
                ChooseFileOrPathActivity.this.choose(position);
                return true;
            }
        });
    }

    public void choose(int position){
        String path = this.mapList.get(position).get("FilePath").toString();
        this.chosenPath = path;
        this.txtChosenPath.setText("Chosen PathName:  " + this.chosenPath);
    }

    public void into(int position){
        String path = this.mapList.get(position).get("FilePath").toString();
        this.getFileDir(path);
    }

    public void back(){
        this.getFileDir(this.fomalFilePath);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(this.currentPath.equals(bootPath)){
                this.finish();
            }else {
                this.back();
            }
        }
        return false;
    }
}
