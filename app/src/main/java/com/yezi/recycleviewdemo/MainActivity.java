package com.yezi.recycleviewdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mStringList = new ArrayList<>();
    private Toolbar toolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.refreshView);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recycleview);
        for (int i=0;i<40;i++){
            mStringList.add("itemview" + i);
        }

        final ListAdapter listAdapter = new ListAdapter(mStringList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MyDivider(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"item " + pos + " is clicked",Toast.LENGTH_SHORT).show();
            }
        });

        /***
         * 拖动，侧滑事件辅助类
         */
        ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchHelper(listAdapter, mStringList));
        helper.attachToRecyclerView(mRecyclerView);

        /***
         * 下拉刷新事件
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("test","onRefresh()");
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mStringList.add(1,"new one");
                        listAdapter.notifyItemRangeInserted(1,1);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },2000);

            }
        });

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int last = linearLayoutManager.findLastVisibleItemPosition();
//                if(last + 1 == mStringList.size()){
//                    mStringList.add(mStringList.size(),"more one");
//                    listAdapter.notifyItemRangeInserted(mStringList.size(),1);
//                }
//            }
//        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int last = linearLayoutManager.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE && last + 1 == mStringList.size()){
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mStringList.add(mStringList.size(),"more one");
                            listAdapter.notifyItemRangeInserted(mStringList.size(),1);
                        }
                    },2000);

                }
            }
        });

        /***
         * 工具栏事件监听
         */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_action:
                        Toast.makeText(MainActivity.this, "ADD !", Toast.LENGTH_SHORT).show();
                        mStringList.add(10,"new one");
                        listAdapter.notifyItemRangeInserted(10,1);
                        break;
                    case R.id.delete_acton:
                        Toast.makeText(MainActivity.this, "DEL !", Toast.LENGTH_SHORT).show();
                        mStringList.remove(15);
                        listAdapter.notifyItemRangeRemoved(15,1);
                        break;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

}
