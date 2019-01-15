package com.sff.sffapp.Measurement;


import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.FollowUp.FollowUp;


import com.sff.sffapp.R;


import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class MeasurementListFragment extends Fragment implements View.OnClickListener , ConnectionInterface
{
    public  String baseClass ;
    Context mContext ;

    ListView listView;
    ArrayList<MeasurementListCell> addRoomArray;
    MeasurementListAdapter addRoomListAdapter;
    public JSONObject json ;
    public MeasurementListFragment()
    {
        this.json = new JSONObject();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,   Bundle savedInstanceState)
    {

        mContext = ((FollowUp) this.getActivity());
        ((TextView) ((FollowUp) this.getActivity()).myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Add Room");

         //((FollowUp)this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).requestFocus();

        // Inflate the layout for this fragment
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setOnClickListener(this);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setOnClickListener(this);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setOnClickListener(this);

        View v = inflater.inflate(R.layout.fragment_measurement_list, container, false);

        listView = v.findViewById(R.id.mess_fragment_measurement_list);
        // Inflate the layout for this fragment

        addRoomArray = new ArrayList<MeasurementListCell>();


        addRoomListAdapter = new MeasurementListAdapter(this.getContext(), this.addRoomArray);
        listView.setAdapter(addRoomListAdapter);

        InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(this.getContext().INPUT_METHOD_SERVICE);
        return v ;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FollowUp f1 = new FollowUp();
        FragmentManager fm1 = this.getActivity().getSupportFragmentManager();

        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.VISIBLE);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
    }

    @Override

    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (hidden)
        {
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.INVISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.VISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
        }

        try {
            ((InputMethodManager) this.getActivity().getSystemService(this.getActivity().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    @Override
    public void onClick(View view)
    {

      if (view.getId() == R.id.footer_btn_cancel && baseClass.equals("FollowUpList")  )
        {
            Toast.makeText(mContext, "MeasurementListFragment ", Toast.LENGTH_SHORT).show();
            FragmentManager fm1 = ((FollowUp) this.getActivity()).getSupportFragmentManager() ;
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().remove(fm1.findFragmentByTag("MeasurementListFragment")).commit();
            fm1.beginTransaction().show(fm1.findFragmentByTag("FollowUpList")).commit();
        }

        else if (view.getId() == R.id.footer_btn_save && baseClass.equals("FollowUpList"))
        {

            if ( validateAddroom().equals("T"))
                {
                    //Toast.makeText(mContext, "save", Toast.LENGTH_SHORT).show();
                    JSONArray jsonArrayList = new JSONArray();
                    try
                    {

                        this.json.put("CCS_TEAM_CODE" ,userInfo.getTEAM_NO() ) ;
                        this.json.put("CCS_TEAM_CODE_EMP_NO" ,userInfo.getEMP_NO() ) ;
                        this.json.put("DTL_COUNT" ,this.addRoomArray.size() ) ;
                        for (int i = 0; i < this.addRoomArray.size(); i++) {
                            JSONObject x = new JSONObject();
                            x.put("ROOM_CODE", this.addRoomArray.get(i).v_mess_fragment_txt_room_code);
                            x.put("MGRP_CODE", this.addRoomArray.get(i).v_mess_fragment_txt_mgrp_code);
                            x.put("ITEM_CODE", this.addRoomArray.get(i).v_mess_fragment_txt_item_code);
                            x.put("UNIT_CODE", this.addRoomArray.get(i).v_mess_fragment_UNIT_CODE);
                            x.put("QNTY_TYPE", this.addRoomArray.get(i).v_mess_fragment_QNTY_TYPE);
                            x.put("COLOR_CODE", this.addRoomArray.get(i).v_mess_fragment_txt_color_code);
                            x.put("ROOM_LENGTH", this.addRoomArray.get(i).v_mess_fragment_txt_length);
                            x.put("ROOM_WIDTH", this.addRoomArray.get(i).v_mess_fragment_txt_width);
                            x.put("FRAM_COUNT", this.addRoomArray.get(i).v_mess_fragment_txt_frameQnty);
                            x.put("DIVIDER_COUNT", this.addRoomArray.get(i).v_mess_fragment_txt_dividerQnty);
                            x.put("REDUCER_COUNT", this.addRoomArray.get(i).v_mess_fragment_txt_reducerQnty);

                            jsonArrayList.put(x);
                            x = null;
                        }
                        json.put("DTL_VAL", jsonArrayList);
                        DBconnection dBconnection = new DBconnection(json.toString() , "TICKET_ACTIONS" ,this , mContext , "ADDMEASUREMENT"  ) ;
                        dBconnection.execute();
                        FragmentManager fm1 = ((FollowUp) this.getActivity()).getSupportFragmentManager() ;
                        FragmentTransaction ftrans = fm1.beginTransaction();
                        ftrans.addToBackStack(null);
                        fm1.beginTransaction().remove(fm1.findFragmentByTag("MeasurementListFragment")).commit();
                        fm1.beginTransaction().show(fm1.findFragmentByTag("FollowUpList")).commit();

                    }

                    catch (Exception e)
                    {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
        }

        else if (view.getId() == R.id.footer_btn_add )//&& baseClass.equals("FollowUpList"))
        {
                MeasurementAddRoomFragment f1 = new MeasurementAddRoomFragment();
                f1.baseClass = "MeasurementListFragment" ;

                FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
                fm1.beginTransaction().hide(fm1.findFragmentByTag("MeasurementListFragment")).commit();

                FragmentTransaction ftrans = fm1.beginTransaction();
                ftrans.addToBackStack(null);
                ftrans.add(R.id.TicketFollowUpWContainer, f1, "MeasurementAddRoomFragment").commit();
        }
    }

    public String validateAddroom()
    {
        return "T" ;
    }

    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id)
    {
        SoapObject obj2;
        obj2 = (SoapObject) obj.getProperty(0);
        obj2.getProperty("val1").toString();
        Log.i("result", obj2.getProperty("val1").toString());
        Toast.makeText(this.getContext(), obj2.getProperty("val1").toString(), Toast.LENGTH_SHORT).show();
    }
}
