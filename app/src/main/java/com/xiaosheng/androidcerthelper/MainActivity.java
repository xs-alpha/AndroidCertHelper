package com.xiaosheng.androidcerthelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiaosheng.androidcerthelper.adapter.CertAdapter;
import com.xiaosheng.androidcerthelper.constants.Constants;
import com.xiaosheng.androidcerthelper.entiy.CertInfo;
import com.xiaosheng.androidcerthelper.utils.CertUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button freshBtn;
    private CertAdapter certAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        freshBtn = findViewById(R.id.refresh);
        freshBtn.setOnClickListener(this);

        loadCerts();
    }

    public void loadCerts(){
        File file = FileUtils.getFileByPath(Constants.DEFAULT_CERT_PATH);
        File[] files = file.listFiles();

        List<CertInfo> certInfos = CertUtils.loadCerts(files);
        renderingListView(certInfos);
    }

    public void renderingListView(List<CertInfo> certInfos){
        certAdapter = new CertAdapter(MainActivity.this, R.layout.activity_item_layout, certInfos);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(certAdapter);
        //设置listview点击事件
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            CertInfo tvInfo = certInfos.get(i);
            Toast.makeText(MainActivity.this, tvInfo.toString(), Toast.LENGTH_LONG).show();

        });
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.refresh){
            loadCerts();
            ToastUtils.showLong("刷新完成");
        }
    }
}