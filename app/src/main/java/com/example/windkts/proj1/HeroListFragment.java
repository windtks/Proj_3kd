package com.example.windkts.proj1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by windtksLin on 2017/11/23 0023.
 */

public class HeroListFragment extends Fragment {
    //private List<Map<String, Object>> mdata =new ArrayList<>();
    private  List<Person> heros = new ArrayList<>();        //从数据读取到的人物信息
    private HeroOp heroOp;
    private  RvAdapter mAdapter ;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        heroOp = new HeroOp(getActivity());
        heros = heroOp.getAllData();

        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_hero_list, container, false);

        setupRecyclerView(recyclerView);

        return recyclerView;

    }
    @Override
    public void onResume() {
        super.onResume();
        heroOp = new HeroOp(getActivity());
        List<Person> newData = heroOp.getAllData();
        heros.clear();
        heros.addAll(newData);

        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();

        }

    }




    private void setupRecyclerView(final RecyclerView recyclerView) {

        mAdapter = new RvAdapter <Person>(getActivity(), R.layout.list_item, heros) {
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
                builder.setTitle("删除人物？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface DialogInterface, int i) {
                                heroOp.deleteDataByName(heros.get(position).getName());
                                heros.remove(position);
                                Log.e("heros","when delete: "+String.valueOf(heros.size()));
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


