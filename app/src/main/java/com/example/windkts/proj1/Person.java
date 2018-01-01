package com.example.windkts.proj1;

import java.io.Serializable;

/**
 * Created by fwaa2 on 2017.11.23.
 */

public class Person implements Serializable {
    private int id;
    private String name;
    private int imageid;
    private String sex;
    private String birthAnddeath;
    private String hometown;
    private String force;
    private int is_liked;
    private String comment;
    private String path;
    public Person(){
        this.id = -1;
        this.name = "NULL";
        this.imageid = 0;
        this.sex = "NULL";
        this.birthAnddeath = "NULL";
        this.hometown = "NULL";
        this.force = "NULL";
        this.is_liked = 0;
        this.comment = "NULL";
        this.path = "";
    }
    public Person(int id, String name, int imageid, String sex, String birthAnddeath, String hometown,
                  String force,int is_liked, String comment, String path){
        this.id = id;
        this.name = name;
        this.imageid = imageid;
        this.sex = sex;
        this.birthAnddeath = birthAnddeath;
        this.hometown = hometown;
        this.force = force;
        this.is_liked = is_liked;
        this.comment = comment;
        this.path = path;
    }

    public int getId(){
        return id;
    }
    public int getImageid(){
        return imageid;
    }
    public String getName(){
        return name;
    }

    public String getBirthAnddeath() {
        return birthAnddeath;
    }

    public String getSex() {
        return sex;
    }

    public String getForce() {
        return force;
    }

    public String getHometown() {
        return hometown;
    }

    public String getComment() {
        return comment;
    }

    public int isIs_liked() {
        return is_liked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setBirthAnddeath(String birthAnddeath) {
        this.birthAnddeath = birthAnddeath;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIs_liked(int is_liked) {
        this.is_liked = is_liked;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
