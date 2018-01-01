package com.example.windkts.proj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RvAdapter mAdapter;
    private List<Person> mData = new ArrayList<>();
    private HeroOp heroOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.s_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.S_recycleView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置icon

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //设置监听.必须在setSupportActionBar()之后调用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("exit","finish");
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //写一个menu的资源文件.然后创建就行了.
        getMenuInflater().inflate(R.menu.search_menu,menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.expandActionView();
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("搜索英雄");
        searchItem.setVisible(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Person> newData = new ArrayList<>();
                if(!TextUtils.isEmpty(newText)){
                    newData = Queryindb(newText);

                }
                mData.clear();
                mData.addAll(newData);
                setmAdapter();
                return false;
            }
        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener(){
//
//            @Override
//            public boolean onClose() {
//                Log.e("exit","finish 0n Close");
//                finish();
//                return false;
//            }
//        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override

            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("exit","finish 0n Focus");
                finish();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    private List<Person> Queryindb(String newText){
        heroOp = new HeroOp(this);
        List<Person> p;
        p = heroOp.getAllLikeName(newText);
        return p;
    }
    private void setmAdapter(){
        if(mAdapter==null){
            mAdapter = new RvAdapter <Person>(this, R.layout.list_item, mData)  {
                @Override
                public void convert(ViewHolder holder, Person p) {
                    ImageView imageView=holder.getView(R.id.pic);
                    TextView name = holder.getView(R.id.hero_name);
                    name.setText(p.getName());
                    if(p.getPath().equals("")){
                        Glide.with(imageView.getContext())
                                .load(p.getImageid())
                                .into(imageView);
                    }
                    else{
                        Bitmap bt = BitmapFactory.decodeFile(p.getPath());
                        if (bt != null) {
                            imageView.setImageBitmap(bt);
                        }
                    }
                }

            };

            mAdapter.setOnItemClickListener (new RvAdapter.OnItemClickListener(){
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                    Person p = mData.get(position);
                    intent.putExtra("person",p);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);

                }
                @Override
                public void onLongClick(final int position) {

                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }
}
