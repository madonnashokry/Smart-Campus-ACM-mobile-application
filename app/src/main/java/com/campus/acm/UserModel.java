package com.campus.acm;
import com.google.gson.annotations.SerializedName;

    public class UserModel {

        @SerializedName("name")
        public String name;



        @SerializedName("id")
        public int id;



public UserModel(String name,int id){
            this.name = name;
            this.id = id;
        }
    }


