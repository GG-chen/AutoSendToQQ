package com.example.administrator.autosendtoqq;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener  {
    private Button bind;
    private Button unBind;
    private EditText textView;
    private Intent intent;
    private BroadcastReceiver receiver;
    private MyService bindService = null;
    private Map<String, String> mode;
    private EditText time;
    private CheckBox checkBox;
    static final int SMS_FROM_CLIENT = 1;
    static final int SMS_RESTAR_SERVER = 4;
    private Messenger messenger;
    private  Intent intentHome;


    private class ReceiveCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("MainActivity", "GET !!");
            openServer(intentHome);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        intentHome = new Intent();
         intent = new Intent(this, MyService.class);


    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("MainActivity", "有返回");
            bindService = ((MyService.MyBinder) iBinder).getServer();
            setTime();




        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };




    private void initData() {
        receiver = new ReceiveCast();
        IntentFilter filter = new IntentFilter("com.example.administrator.autosendtoqq");
        registerReceiver(receiver, filter);
       /* //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= getSharedPreferences("history",
                Activity.MODE_PRIVATE);
         mode = (Map<String, String>) mySharedPreferences.getAll();





//实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
//用putString的方法保存数据
        editor.putString("name", "Karl");
        editor.putString("habit", "sleep");
//提交当前数据
        editor.commit();
//使用toast信息提示框提示成功写入数据
        Toast.makeText(this, "数据成功写入SharedPreferences！" , Toast.LENGTH_LONG).show();
*/
    }

    private void initViews() {
        textView = (EditText) findViewById(R.id.tv);
        bind = (Button) findViewById(R.id.bind_bt);
        unBind = (Button) findViewById(R.id.unbind_bt);
        time = (EditText) findViewById(R.id.time);
        checkBox = (CheckBox) findViewById(R.id.check_time);
        bind.setOnClickListener(this);
        unBind.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.bind_bt:
                //复制TextView里面的文字
                openServer(intentHome);
                break;
            case R.id.unbind_bt:
                //关闭服务
                stopService(intent);
                unregisterReceiver(receiver);
                Log.i("Mainactivity", "destory!");
                break;

        }

    }

    private void openServer(Intent intentHome) {
        boolean open = copy();
        setTime();

        if (open && MyService.isFirst) {
            //开启服务
            startService(intent);
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
            intentHome.setAction(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(intentHome);
        } else  {
            startService(intent);
        }


    }

    private void setTime() {
        if (checkBox.isChecked()) {
            String ss = String.valueOf(time.getText());
            if(!TextUtils.isEmpty(ss)) {
                int ii = Integer.valueOf(ss);
                if (bindService != null) {
                    bindService.time = ii;
                }


            }



        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private Boolean copy() {
        Editable msm = textView.getText();

        if (!TextUtils.isEmpty(msm)){
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text",msm);
            clipboard.setPrimaryClip(clip);
            return true;
        }
        Toast.makeText(MainActivity.this, "编辑栏不能为空",Toast.LENGTH_SHORT).show();
        return false;

    }

}
