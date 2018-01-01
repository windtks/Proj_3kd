package com.example.windkts.proj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by fwaa2 on 2017.11.23.
 */

public class DetailActivity extends AppCompatActivity {
    public static final String HERO_NAME = "PERSON_NAME";
    public static final String HERO_IMAGE_ID= "PERSON_IMAGE_ID";
    public static final String HERO_ID= "PERSON_ID";
    private String heroName;
    private int imageID;
    private String sex;
    private String birthAnddeath;
    private String hometown;
    private String force;
    private int is_liked;
    private String comment;
    private Toolbar tool_bar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView t_birth;
    private TextView t_sex;
    private TextView t_hometown;
    private TextView t_force;
    private TextView t_comment;
    private ImageView heroImage;
    private FloatingActionButton floatingActionButton;
    private HeroOp heroOp;
    private Person person = null;
    private List<Person> personList = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_detail);
        heroOp = new HeroOp(this);
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("person");
        is_liked = person.isIs_liked();
        comment = person.getComment();
        force = person.getForce();
        hometown = person.getHometown();
        birthAnddeath = person.getBirthAnddeath();
        heroName = person.getName();
        imageID = person.getImageid();
        sex = person.getSex();




        t_birth = findViewById(R.id.text_birthAnddeath);
        t_sex = findViewById(R.id.text_sex);
        t_hometown = findViewById(R.id.text_hometown);
        t_force = findViewById(R.id.text_force);
        t_comment = findViewById(R.id.text_comment);

        heroImage = findViewById(R.id.back_drop);

        floatingActionButton = findViewById(R.id.isliked);

        if(is_liked == 1){
            floatingActionButton.setImageResource(R.drawable.ic_star_full_24dp);
        }else{
            floatingActionButton.setImageResource(R.drawable.ic_star_empty_24dp);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLike();

            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tool_bar = (Toolbar)findViewById(R.id.detail_tool_bar);
        setSupportActionBar(tool_bar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(heroName);
        if(person.getPath().equals("")){
            Glide.with(this)
                    .load(imageID)
                    .into(heroImage);
        }
        else{
            Bitmap bt = BitmapFactory.decodeFile(person.getPath());
            if (bt != null) {
                heroImage.setImageBitmap(bt);
            }
        }        t_sex.setText(sex);
        t_birth.setText(birthAnddeath);
        t_hometown.setText(hometown);
        t_force.setText(force);
        t_comment.setText("\t\t\t\t"+comment);


    }
    private void isLike(){
        if(is_liked == 1){
            floatingActionButton.setImageResource(R.drawable.ic_star_empty_24dp);

            upData_isLike(0);

        }else{
            floatingActionButton.setImageResource(R.drawable.ic_star_full_24dp);

            upData_isLike(1);
        }
    }
    private void upData_isLike(int i){
        Person p = person;
        p.setIs_liked(i);
        is_liked = i;
        heroOp.upDataData(p);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_btn:
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                intent.putExtra("edit", heroName);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_tool_bar, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        personList = heroOp.getDataById(person.getId());
        person = personList.get(0);

        is_liked = person.isIs_liked();
        comment = person.getComment();
        force = person.getForce();
        hometown = person.getHometown();
        birthAnddeath = person.getBirthAnddeath();
        heroName = person.getName();
        imageID = person.getImageid();
        sex = person.getSex();

        collapsingToolbarLayout.setTitle(heroName);
        t_sex.setText(sex);
        t_birth.setText(birthAnddeath);
        t_hometown.setText(hometown);
        t_force.setText(force);
        t_comment.setText(comment);
        if(is_liked == 1){
            floatingActionButton.setImageResource(R.drawable.ic_star_full_24dp);
        }else{
            floatingActionButton.setImageResource(R.drawable.ic_star_empty_24dp);
        }
        if(person.getPath().equals("")){
            Glide.with(this)
                    .load(imageID)
                    .into(heroImage);
        }
        else{
            Bitmap bt = BitmapFactory.decodeFile(person.getPath());
            if (bt != null) {
                heroImage.setImageBitmap(bt);
            }
        }

    }


}
