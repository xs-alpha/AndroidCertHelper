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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiaosheng.androidcerthelper.adapter.CertAdapter;
import com.xiaosheng.androidcerthelper.constants.Constants;
import com.xiaosheng.androidcerthelper.entiy.CertInfo;
import com.xiaosheng.androidcerthelper.utils.CertUtils;
import com.xiaosheng.androidcerthelper.utils.LogUtils;
import com.xiaosheng.androidcerthelper.utils.thread.ThreadPoolUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button freshBtn;
    private CertAdapter certAdapter;

    // 渲染列表使用
    private List<CertInfo> certInfos;
    // 用来做搜索使用
    private List<CertInfo> totalCertInfos;
    private Switch isExpireSwitch;
    private ThreadPoolUtils threadPool;
    private TextView certPath;
    private SearchView searchView;
    private Button searchBtn;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadPool.shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        loadCerts();
    }

    private void initViews(){
        freshBtn = findViewById(R.id.refresh);
        freshBtn.setOnClickListener(this);
        isExpireSwitch = findViewById(R.id.is_expire_show_only);
        isExpireSwitch.setOnCheckedChangeListener(this);
        certPath = findViewById(R.id.cert_path);
        certPath.setText(Constants.DEFAULT_CERT_PATH_PREFIX+Constants.DEFAULT_CERT_PATH);
        searchView = findViewById(R.id.search_view);
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
    }

    public void loadCerts(){
        threadPool = ThreadPoolUtils.getNewInstance();
        threadPool.executor(new LoadCertTask());
//        threadPool.shutdown();
    }

    public void renderingListView(List<CertInfo> certInfos){
        runOnUiThread(new RenderingListView());
    }

    private void doRenderingListView(List<CertInfo> certInfos) {
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
        // 由于jdk17 变更,导致这里不能用switch
        if (id==R.id.refresh){
            ToastUtils.showLong("正在加载...");
            loadCerts();
            ToastUtils.showLong("刷新完成");
        }else if (id==R.id.search_btn){
            CharSequence query = searchView.getQuery();
            String queryMsg = query.toString();
            LogUtils.log("搜索:"+queryMsg);
            if (StringUtils.isEmpty(queryMsg)){
                return;
            }
            threadPool.executor(()->{
                certInfos = totalCertInfos.stream().filter(c -> c.getIssuer().toLowerCase().indexOf(queryMsg.toLowerCase()) > 0).collect(Collectors.toList());
                renderingListView(certInfos);
            });

            ToastUtils.showLong("搜索完成");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        LogUtils.log("切换状态:"+b);
        Constants.IS_EXPIRE_SHOW_ONLY  = !b;
    }

    class LoadCertTask implements Runnable{
        @Override
        public void run() {
            File file = FileUtils.getFileByPath(Constants.DEFAULT_CERT_PATH);
            File[] files = file.listFiles();

            certInfos = CertUtils.loadCerts(files);
            totalCertInfos = certInfos;
            renderingListView(certInfos);
        }
    }

    class RenderingListView implements Runnable{

        @Override
        public void run() {
            certAdapter = new CertAdapter(MainActivity.this, R.layout.activity_item_layout, certInfos);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(certAdapter);
            //设置listview点击事件
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                CertInfo tvInfo = certInfos.get(i);
                Toast.makeText(MainActivity.this, tvInfo.toString(), Toast.LENGTH_LONG).show();
            });
        }
    }
}