package com.sff.sffapp.Measurement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sff.sffapp.R;


import java.util.ArrayList;

public class MeasurementListAdapter extends BaseAdapter
{

    Context mContext;
    LayoutInflater inflater;
    ArrayList<MeasurementListCell> MeasurementArrayList;

    public MeasurementListAdapter(Context context , ArrayList<MeasurementListCell> MeasurementArrayList )
    {
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        this.MeasurementArrayList = MeasurementArrayList ;
    }

    public class ViewHolder
    {
        TextView mess_fragment_txt_room_no;
        TextView mess_fragment_txt_room_desc;
        TextView mess_fragment_txt_mgrp_desc;
        TextView mess_fragment_txt_item_desc;
        TextView mess_fragment_txt_color_desc;

        TextView mess_fragment_txt_area_width;
        TextView mess_fragment_txt_area_length;

        TextView mess_fragment_txt_frameQnty;
        TextView mess_fragment_txt_dividerQnty ;
        TextView mess_fragment_txt_reducerQnty ;
    }


    @Override
    public int getCount()
    {
        return this.MeasurementArrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.MeasurementArrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        final MeasurementListAdapter.ViewHolder holder;
        if (view==null)
        {

            holder = new MeasurementListAdapter.ViewHolder();

            view = inflater.inflate(R.layout.measurement_list_cell, null);

            holder.mess_fragment_txt_room_no = view.findViewById(R.id.mess_fragment_txt_room_no);
            holder.mess_fragment_txt_room_desc = view.findViewById(R.id.mess_fragment_txt_room_desc);
            holder.mess_fragment_txt_mgrp_desc = view.findViewById(R.id.mess_fragment_txt_mgrp_desc);
            holder.mess_fragment_txt_item_desc = view.findViewById(R.id.mess_fragment_txt_item_desc);
            holder.mess_fragment_txt_color_desc = view.findViewById(R.id.mess_fragment_txt_color_desc);

           holder.mess_fragment_txt_area_width = view.findViewById(R.id.mess_fragment_txt_area_width_cell);
           holder.mess_fragment_txt_area_length = view.findViewById(R.id.mess_fragment_txt_area_length_cell);

            holder.mess_fragment_txt_frameQnty = view.findViewById(R.id.mess_fragment_txt_frameQnty_cell);
            holder.mess_fragment_txt_dividerQnty = view.findViewById(R.id.mess_fragment_txt_dividerQnty_cell);
            holder.mess_fragment_txt_reducerQnty = view.findViewById(R.id.mess_fragment_txt_reducerQnty_cell);

            view.setTag(holder);
        }
        else
        {
            holder = (MeasurementListAdapter.ViewHolder)view.getTag();
        }

        holder.mess_fragment_txt_room_no.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_room_no));
        holder.mess_fragment_txt_room_desc.setText(this.MeasurementArrayList.get(position).v_mess_fragment_txt_room_desc);
        holder.mess_fragment_txt_mgrp_desc.setText(this.MeasurementArrayList.get(position).v_mess_fragment_txt_mgrp_desc);
        holder.mess_fragment_txt_item_desc.setText(this.MeasurementArrayList.get(position).v_mess_fragment_txt_item_desc);
        holder.mess_fragment_txt_color_desc.setText(this.MeasurementArrayList.get(position).v_mess_fragment_txt_color_desc);

       holder.mess_fragment_txt_area_width.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_width));
       holder.mess_fragment_txt_area_length.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_length));

        holder.mess_fragment_txt_frameQnty.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_frameQnty));
        holder.mess_fragment_txt_dividerQnty.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_dividerQnty));
        holder.mess_fragment_txt_reducerQnty.setText(String.valueOf(this.MeasurementArrayList.get(position).v_mess_fragment_txt_reducerQnty));

        return view;
    }

    public void BackPressed () {
        notifyDataSetChanged();
    }

}
