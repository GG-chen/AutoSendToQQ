package com.example.administrator.autosendtoqq;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MyService extends Service {
    public static final String TAG = "serivce";
    private boolean flag;
    public static boolean isFirst = true;
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;
    public  int time = -1;
   private Button mFloatView;
    private boolean opoenThread = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public MyService getServer() {
            return  MyService.this;
        }
    }




    @Override
    public void onCreate() {
        super.onCreate();
        flag = true;
        createFloatView();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //得到时间
        final String[][] list = OrderList.add();

        Thread thread = new Thread() {
            @Override
            public void run() {
                while(flag) {
                    for (int i = 0; i < list.length; i++) {
                        if (flag && list[i] != null) {
                            Log.i(TAG, "第" + i + "步");
                            try {
                                sleep(1000*2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //如果是点击粘贴键就多点一次
                            if (i == 4 || i == 1) {
                                doXue(list[i]);
                            }
                            doXue(list[i]);

                        }

                    }
                        Log.i(TAG,time + "");

                        if (time != -1) {
                            Log.i(TAG, time + "");
                            scheduleTime();

                        }else {
                            Log.i(TAG, "没有计时关闭");
                            Looper.prepare();
                            close();

                        }

                }




            }
        };
            if (isFirst ) {
                thread.start();
                isFirst = false;
            }




    }

    private void scheduleTime() {
        long timeMillis = time  * 1000;//得到毫秒数
        Intent timeIntent = new Intent("com.example.administrator.autosendtoqq");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, timeIntent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);
        Log.i(TAG, "广播发送成功");


    }

    void doXue(String[] orders){
        Runtime runtime = Runtime.getRuntime();
        DataOutputStream dataOut;
        try {
            //执行su是向系统请求root权限，process是返回执行su的这个独立进程
            Process process = runtime.exec("su ");
            InputStream in = process.getInputStream();
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(in));
            BufferedReader err=new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            dataOut = new DataOutputStream(process.getOutputStream());
            //点击发送按钮
            for(String order : orders){
                dataOut.writeBytes(order + ";");
            }
            dataOut.flush();

            dataOut.close();
            process.waitFor();
            while ((line = err.readLine()) != null) {
                Log.i(TAG,line);
            }
            while ((line = bufferReader.readLine()) != null) {
                Log.i(TAG,line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,e.getMessage());
        }
    }
    private void createFloatView()
    {

        //获取的是WindowManagerImpl.CompatModeWrapper
        if ( mWindowManager == null) {
            mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        }
        wmParams = new WindowManager.LayoutParams();
        Log.i(TAG, "mWindowManager--->" + mWindowManager);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为右侧置顶
        wmParams.gravity = Gravity.END | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

         /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_button, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = (Button)mFloatLayout.findViewById(R.id.float_bt);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
        mFloatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                flag = false;
                time = -1;
                isFirst = true;
                Toast.makeText(MyService.this, "发送结束1", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void close() {
        Toast.makeText(MyService.this, "发送结束2", Toast.LENGTH_SHORT).show();
        if (mWindowManager != null) {
            mWindowManager.removeView(mFloatLayout);

        }
        flag = false;
        time = -1;
        isFirst = true;
        stopSelf();
    }


    @Override
    public void onDestroy() {
       flag = false;
        super.onDestroy();


    }

}
