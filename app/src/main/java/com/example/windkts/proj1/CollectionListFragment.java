package com.example.windkts.proj1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windtksLin on 2017/11/24 0024.
 */

public class CollectionListFragment extends Fragment {
    private  List<Person> heros = new ArrayList<>();        //从数据读取到的人物信息
    private HeroOp heroOp;
    private  RvAdapter mAdapter =null;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        heroOp = new HeroOp(getActivity());
        heros = heroOp.getAllLiked();

        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_hero_list, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.onHiddenChanged(isVisibleToUser);
        if (isVisibleToUser) {   // 在最前端显示 相当于调用了onResume();
            update();
        }
    }
    private void update(){
        heroOp = new HeroOp(getActivity());

        List<Person> newData = heroOp.getAllLiked();
        heros.clear();
        heros.addAll(newData);
        Log.e("Frag","hero: "+ String.valueOf(heros.size()));
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {

        mAdapter = new RvAdapter <Person>(getActivity(), R.layout.collectionlist_item, heros) {
            @Override
            public void convert(ViewHolder holder, Person p) {
                ImageView imageView=holder.getView(R.id.collection_pic);
                TextView name = holder.getView(R.id.c_name);
                TextView force = holder.getView(R.id.c_force);
                TextView comment = holder.getView(R.id.c_comment);
                TextView birth = holder.getView(R.id.c_birth);


                name.setText(p.getName().toString());
                force.setText(p.getForce().toString());
                switch (p.getForce().toString()){
                    case "蜀":
                        force.setTextColor(Color.RED);
                        break;
                    case "吴":
                        force.setTextColor(Color.rgb(0,100,0));
                        break;
                    case "魏":
                        force.setTextColor(android.graphics.Color.BLUE);
                        break;
                    case "群":
                        force.setTextColor(Color.GRAY);
                        break;
                }
                birth.setText(p.getBirthAnddeath().toString());
                comment.setText("\t\t"+p.getComment().toString());
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
        mAdapter.setOnItemClickListener (new RvAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);

                Person p = heros.get(position);
                intent.putExtra("person",p);
                getContext().startActivity(intent);

            }
            @Override
            public void onLongClick(final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
                builder.setTitle("取消收藏该人物？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface DialogInterface, int i) {
                                Person p = heros.get(position);
                                p.setIs_liked(0);
                                heroOp.upDataData(p);
                                heros.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                builder.create().show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(mAdapter);
    }
}
