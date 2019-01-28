package com.wangzijiapeterlong.wzkminidouyin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import java.io.InputStream;
import java.util.List;
import com.wangzijiapeterlong.wzkminidouyin.model.PullParser;
import com.wangzijiapeterlong.wzkminidouyin.model.Message;

public class MessageActivity extends AppCompatActivity implements MessageAdapter.ListItemClickListener {

    private MessageAdapter mAdapter;
    private List<Message> messages;
    private static final String TAG = "ItemViews";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        //取得需要展示的数据
        try {
            InputStream assetInput = getAssets().open("data.xml");
            messages = PullParser.pull2xml(assetInput);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //定义recycleView
        RecyclerView recycleView = findViewById(R.id.rv_list);

        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setHasFixedSize(true);

        //创建Adapter,将数据传入
        mAdapter = new MessageAdapter(messages, this);

        //设置Adapter
        recycleView.setAdapter(mAdapter);
    }


    @Override
    public void onListItemClick(String chat_target) {
//        System.out.println(chat_target);
        Intent it = new Intent(this, ChatRoom.class);
        it.putExtra("chat_target", chat_target);
        startActivity(it);
    }


}
