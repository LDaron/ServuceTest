package com.ldl.servucetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity2 extends AppCompatActivity {
    @BindView(R.id.intent_service)
    Button intentService;
    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @OnClick({R.id.start_service, R.id.stop_service, R.id.bind_service, R.id.unbind_service,R.id.intent_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.stop_service:
                Intent intent1 = new Intent(this, MyService.class);
                stopService(intent1);
                break;
            case R.id.bind_service:
                Intent intent2 = new Intent(this, MyService.class);
                bindService(intent2, connection, BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.unbind_service:
                unbindService(connection);//解除绑定
                break ;
            case R.id.intent_service:
                Log.d("MainActivity", "This id is  "+Thread.currentThread().getId());
                Intent intent3 = new Intent(this , MyIntentService.class);
                startService(intent3);
                break;
        }
    }

    @OnClick(R.id.intent_service)
    public void onViewClicked() {
    }
}
