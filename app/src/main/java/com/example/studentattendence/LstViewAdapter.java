package com.example.studentattendence;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LstViewAdapter extends ArrayAdapter<String> {
    int groupid;
    //String[] name;
    ArrayList roll_no,name;

    ArrayList<String> desc;
    Context context;
    customButtonListener customListner;
    public LstViewAdapter(Context context, int vg, int id, ArrayList a,ArrayList b){
        super(context,vg,id,b);
        this.context=context;
        groupid=vg;
        this.name=a;
        this.roll_no=b;

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView name,roll_no;
        public Button button;

    }
    public interface customButtonListener {
        public void onButtonClickListner(int position,String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name= (TextView) rowView.findViewById(R.id.name2);
            viewHolder.roll_no= (TextView) rowView.findViewById(R.id.roll_no2);
            viewHolder.button= (Button) rowView.findViewById(R.id.bt);
            rowView.setTag(viewHolder);

        }

        // Set text to each TextView of ListView item
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.name.setText(name.get(position).toString());
        holder.roll_no.setText(""+roll_no.get(position));
        holder.button.setContentDescription(""+position);

        return rowView;
    }

}

