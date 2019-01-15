package com.sff.sffapp.menu;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sff.sffapp.SalesTicket.SalesTicket;

import com.sff.sffapp.FollowUp.FollowUp;
import com.sff.sffapp.database.ConnectionInterface;

import com.sff.sffapp.R;


import org.json.JSONObject;

import java.util.ArrayList;
public class menuAdapter extends BaseAdapter {
    //variables
    Context mContext;
    LayoutInflater inflater;
    ArrayList<menuCell> displayArrayList;
    ArrayList<menuCell> orginalArrayList ;
    JSONObject json ;
    ConnectionInterface connectionInterface ;
    menuAdapter mm;
    //constructor
    public menuAdapter(Context context ) {
        orginalArrayList = login.menuArrayList ;
        mContext = context;
        this.displayArrayList = new ArrayList<>() ;

        this.displayArrayList = getProgList(0);
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder{
        TextView mTitleTv;
        ImageView mIconIv;
    }
    @Override
    public int getCount() {
        return displayArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return displayArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition,  View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.mune_cell, null);
            //locate the views in row.xml
            holder.mTitleTv = view.findViewById(R.id.txt_prog);
            holder.mIconIv = view.findViewById(R.id.image_prog);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(displayArrayList.get(postition).getTitle());
        //set the result in imageview
        if (displayArrayList.get(postition).getIcon().equals("menu.png"))
        {
            holder.mIconIv.setImageResource(R.drawable.img_1);
        }
        else if (displayArrayList.get(postition).getIcon().equals("prog.png"))
        {
            holder.mIconIv.setImageResource(R.drawable.img_2);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mb*/
                if (displayArrayList.get(postition).getProgType().equals("P") )
                {
                    CallProg( displayArrayList.get(postition).getPROG_NO() , displayArrayList.get(postition).getTitle());
                }

                else if (displayArrayList.get(postition).getProgType().equals("R"))

                {
                    displayArrayList = getProgList(displayArrayList.get(postition).PROG_NO) ;
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    public ArrayList<menuCell> getProgList(int root) {


        ArrayList<menuCell> result  = new ArrayList<>();
        displayArrayList.clear();
            for (menuCell cell : orginalArrayList) {
                if (cell.getRootNo() == root) {
                    result.add(cell);
                }
            }
           return  result ;

    }

    public int getProgRoot () {
        int result = 0 ;
        int currRoot = displayArrayList.get(0).rootNo;

        for (menuCell cell : orginalArrayList) {
            if (cell.getPROG_NO() == currRoot) {
                result = cell.getRootNo() ;

            }
        }

        return  result ;


    }

    public void BackPressed () {
        Log.i("asasasasasasasasas", "onBackPressed: " + getProgRoot()   );
        displayArrayList = getProgList(getProgRoot()) ;
        notifyDataSetChanged();



    }

    public  void CallProg( int ProgNo , String ProgTitle )
    {
         if ( ProgNo == 8002)
    {
        Intent intent = new Intent(mContext, FollowUp.class);
        mContext.startActivity(intent);
    }

        if ( ProgNo == 8009)
        {
            Intent intent = new Intent(mContext, SalesTicket.class);
            mContext.startActivity(intent);
        }

    }
}