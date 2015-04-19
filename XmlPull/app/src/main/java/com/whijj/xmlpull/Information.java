package com.whijj.xmlpull;

/**
 * Created by Administrator on 2015/4/19 0019.
 */

import android.content.Context;

/**
 * android studio 怎么快捷  构造函数  get set
 *
 */
public class Information {
    private String id;
    private String name;
    private String version;
    public Information(String id, String name, String version){
        setId(id);
        setName(name);
        setVersion(version);
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getVersion(){
        return version;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setVersion(String verison){
        this.version=verison;
    }


}
