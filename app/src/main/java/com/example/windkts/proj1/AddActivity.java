package com.example.windkts.proj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fwaa2 on 2017.11.24.
 */
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private  Toolbar toolbar;
    private HeroOp heroOp = new HeroOp(this);
    private EditText mName;
    private ImageView mPhoto;
    private EditText mTime;

    private RadioGroup r1;
    private RadioGroup r2;

    private RadioButton shu;
    private RadioButton wei;
    private RadioButton wu;
    private RadioButton qun;

    private TextInputLayout name_Input;
    private TextInputLayout time_Input;
    private TextInputLayout info_Input;
    private TextInputLayout place_Input;

    private RadioButton male;
    private RadioButton female;

    private EditText mPlace;
    private EditText mInfo;

    private List<Person> l = null;
    private int id;
    private Bitmap head;
    private static String path = "/storage/emulated/0/H/";
    private String newPath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        id = (int)System.currentTimeMillis();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置icon
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //设置监听.必须在setSupportActionBar()之后调用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();

        if(getIntent().getStringExtra("edit") != null) {
            String name = getIntent().getStringExtra("edit");
            Edit(name);
        }
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    CutPhoto(data.getData());
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);
                        mPhoto.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setPicToView(Bitmap head) {
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();
        String fileName = path + "head"+String.valueOf(id)+".jpg";//图片+id
        try {
            b = new FileOutputStream(fileName);
            head.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把压缩数据写入文件
            newPath = fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void CutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, 3);
    }

    private void initView() {
        mName = findViewById(R.id.EditName);
        mPhoto = findViewById(R.id.photo);
        mTime = findViewById(R.id.time);

        shu = findViewById(R.id.shu);
        wei = findViewById(R.id.wei);
        wu = findViewById(R.id.wu);
        qun = findViewById(R.id.qun);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        mPlace = findViewById(R.id.place);

        mInfo = findViewById(R.id.info);

        name_Input = findViewById(R.id.name);
        time_Input = findViewById(R.id.born_time);
        place_Input = findViewById(R.id.nativeplace);
        info_Input = findViewById(R.id.otherinfo);

        r1= findViewById(R.id.gender);
        r2= findViewById(R.id.force);
    }

    private void Edit(String name) {
        l = heroOp.getDataByName(name);
        if (l !=null) {
            Person h = l.get(0);
            id = h.getId();
            mName.setText(h.getName());
            mTime.setText(h.getBirthAnddeath());
            mPlace.setText(h.getHometown());
            mInfo.setText(h.getComment());
            if (h.getPath().equals("")) {
                mPhoto.setImageResource(h.getImageid());
                newPath = "";
            }
            else {
                Bitmap bt = BitmapFactory.decodeFile(h.getPath());
                if (bt != null) {
                    mPhoto.setImageBitmap(bt);
                }
                newPath = h.getPath();
            }
            switch(h.getForce()){
                case "蜀":
                    shu.setChecked(true);
                    break;
                case "魏":
                    wei.setChecked(true);
                    break;
                case "吴":
                    wu.setChecked(true);
                    break;
                case "群":
                    qun.setChecked(true);
                    break;
                default:
                    break;
            }

            switch(h.getSex()){
                case "男":
                    male.setChecked(true);
                    break;
                case "女":
                    female.setChecked(true);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //写一个menu的资源文件.然后创建就行了.
        getMenuInflater().inflate(R.menu.add_toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        CheckInput();

        return super.onOptionsItemSelected(item);

    }

    private void CheckInput() {
        String name = String.valueOf(mName.getText());
        String time = String.valueOf(mTime.getText());
        String place = String.valueOf(mPlace.getText());
        String info = String.valueOf(mInfo.getText());
        String genger = null;
        String force = null;
        int imgID;
        int liked;
        if(l == null) {
            liked = 0;
            imgID = 0;
        }
        else{
            imgID = l.get(0).getImageid();
            liked = l.get(0).isIs_liked();
        }

        if(!male.isChecked()&&!female.isChecked()){
            Toast.makeText(this,"性别不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!wei.isChecked()&&!shu.isChecked()&&!wu.isChecked()&&!qun.isChecked()){
            Toast.makeText(this,"势力不能为空",Toast.LENGTH_SHORT).show();
        }

        if(name.length() == 0){
            Toast.makeText(this,"请检查输入",Toast.LENGTH_SHORT).show();
            return;
        }
        if(time.length() == 0){
            Toast.makeText(this,"请检查输入",Toast.LENGTH_SHORT).show();
            return;
        }
        if (place.length() == 0){
            Toast.makeText(this,"请检查输入",Toast.LENGTH_SHORT).show();
            return;
        }
        if(r1.getCheckedRadioButtonId()==R.id.male) genger="男"; else genger="女";
        switch(r2.getCheckedRadioButtonId()){
            case R.id.wei:
                force = "魏";
                break;
            case R.id.wu:
                force = "吴";
                break;
            case R.id.shu:
                force = "蜀";
                break;
            case R.id.qun:
                force = "群";
                break;
        }
        if(newPath==null){
            Toast.makeText(this,"未选择头像",Toast.LENGTH_SHORT).show();
            return;
        }
        Person n = new Person(id,name,imgID,genger,time,place,force,liked,info,newPath);
        heroOp.upDataData(n);
        finish();
    }


}
