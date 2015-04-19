package com.whijj.xmlpull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/4/19 0019.
 */
public class InfoAdapter extends BaseAdapter {

    private Context context;
    private List<Information> infoList;
    public InfoAdapter(Context context,List<Information> infoList){
        this.context=context;
        this.infoList=infoList;
    }
    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.xml_item,null);
        }
        TextView id= (TextView) convertView.findViewById(R.id.xml_id);
        TextView name= (TextView) convertView.findViewById(R.id.xml_name);
        TextView version= (TextView) convertView.findViewById(R.id.xml_version);
        Information info=infoList.get(position);
        id.setText(info.getId());
        name.setText(info.getName());
        version.setText(info.getVersion());
        return convertView;


    }
}
