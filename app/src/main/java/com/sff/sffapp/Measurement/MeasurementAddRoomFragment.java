package com.sff.sffapp.Measurement;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.FollowUp.FollowUp;
import com.sff.sffapp.R;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;


public class MeasurementAddRoomFragment extends Fragment implements View.OnClickListener
{
    public  String baseClass ;
    Context mContext ;

    SpinnerDialog spinnerDialog ;

    TextView mess_fragment_txt_room_desc ;
    String   mess_fragment_txt_room_code;

    TextView mess_fragment_txt_mgrp_desc ;
    String   mess_fragment_txt_mgrp_code ;

    TextView mess_fragment_txt_item_desc ;
    String   mess_fragment_txt_item_code ;

    TextView mess_fragment_txt_color_desc ;
    String   mess_fragment_txt_color_code ;

    EditText mess_fragment_txt_width ;
    EditText mess_fragment_txt_length ;

    EditText mess_fragment_txt_frameQnty ;
    EditText mess_fragment_txt_dividerQnty ;
    EditText mess_fragment_txt_reducerQnty ;

    JSONObject json ;

    String unit_code ;
    String qnty_type ;

    public MeasurementAddRoomFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        mContext = ((FollowUp) this.getActivity());

        // Inflate the layout for this fragment
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setOnClickListener(this);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setOnClickListener(this);

        View v = inflater.inflate(R.layout.fragment_measurement_add_room, container, false);

        mess_fragment_txt_room_desc = v.findViewById(R.id.mess_fragment_txt_room_desc) ;
        mess_fragment_txt_room_desc.setOnClickListener(this);
        mess_fragment_txt_room_desc.requestFocus() ;

        mess_fragment_txt_mgrp_desc = v.findViewById(R.id.mess_fragment_txt_mgrp_desc) ;
        mess_fragment_txt_mgrp_desc.setOnClickListener(this);

        mess_fragment_txt_item_desc = v.findViewById(R.id.mess_fragment_txt_item_desc) ;
        mess_fragment_txt_item_desc.setOnClickListener(this);

        mess_fragment_txt_color_desc = v.findViewById(R.id.mess_fragment_txt_color_desc);
        mess_fragment_txt_color_desc.setOnClickListener(this);

        mess_fragment_txt_width = v.findViewById(R.id.mess_fragment_txt_area_width_in_meter) ;
        mess_fragment_txt_length = v.findViewById(R.id.mess_fragment_txt_area_length_in_meter) ;

        mess_fragment_txt_frameQnty = v.findViewById(R.id.mess_fragment_txt_frameQnty_add) ;
        mess_fragment_txt_dividerQnty = v.findViewById(R.id.mess_fragment_txt_dividerQnty_cell) ;
        mess_fragment_txt_reducerQnty = v.findViewById(R.id.mess_fragment_txt_reducerQnty_cell) ;
        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.VISIBLE);
        ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);

        if (hidden)
        {
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.INVISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.VISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onClick(View v)
    {

        if ( (v.getId() == R.id.footer_btn_cancel )   && (baseClass.equals("MeasurementListFragment"))  )
        {
            Toast.makeText(mContext, "MeasurementAddRoomFragment ", Toast.LENGTH_SHORT).show();
/*
            FragmentManager fm1 =   ((FollowUp) this.getContext()).getSupportFragmentManager()   ;
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().remove(fm1.findFragmentByTag("MeasurementAddRoomFragment")).commit();
            fm1.beginTransaction().show(fm1.findFragmentByTag("MeasurementListFragment")).commit();
*/

            FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
            fm1.beginTransaction().remove(this).commit();
            fm1.beginTransaction().show(fm1.findFragmentByTag("MeasurementListFragment")).commit();




        }

        else if (v.getId() == R.id.footer_btn_check /* && baseClass.equals("FollowUpList") */ )
        {
            if ( validateAddroom().equals("T"))
            {
                Toast.makeText(mContext, "Check Done", Toast.LENGTH_SHORT).show();

                FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
                MeasurementListCell myCell ;
                myCell = new MeasurementListCell();

                myCell.v_mess_fragment_txt_room_no = (((MeasurementListFragment)fm1.findFragmentByTag("MeasurementListFragment")).addRoomArray.size()+1);

                myCell.v_mess_fragment_txt_room_code = mess_fragment_txt_room_code;
                myCell.v_mess_fragment_txt_room_desc = mess_fragment_txt_room_desc.getText().toString();

                myCell.v_mess_fragment_txt_mgrp_code = mess_fragment_txt_mgrp_code ;
                myCell.v_mess_fragment_txt_mgrp_desc = mess_fragment_txt_mgrp_desc.getText().toString() ;

                myCell.v_mess_fragment_txt_item_code = mess_fragment_txt_item_code ;
                myCell.v_mess_fragment_txt_item_desc = mess_fragment_txt_item_desc.getText().toString() ;

                myCell.v_mess_fragment_txt_color_code = mess_fragment_txt_color_code ;
                myCell.v_mess_fragment_txt_color_desc = mess_fragment_txt_color_desc.getText().toString() ;

                myCell.v_mess_fragment_txt_width = Integer.parseInt( mess_fragment_txt_width.getText().toString() ) ;
                myCell.v_mess_fragment_txt_length = Integer.parseInt( mess_fragment_txt_length.getText().toString() ) ;

                myCell.v_mess_fragment_txt_frameQnty = Integer.parseInt( mess_fragment_txt_frameQnty.getText().toString() ) ;
                myCell.v_mess_fragment_txt_dividerQnty = Integer.parseInt( mess_fragment_txt_dividerQnty.getText().toString() ) ;
                myCell.v_mess_fragment_txt_reducerQnty = Integer.parseInt( mess_fragment_txt_reducerQnty.getText().toString() ) ;
                myCell.v_mess_fragment_UNIT_CODE = unit_code;
                myCell.v_mess_fragment_QNTY_TYPE = qnty_type;

                ((MeasurementListFragment)fm1.findFragmentByTag("MeasurementListFragment")).addRoomArray.add(0,myCell);

                ((MeasurementListFragment)fm1.findFragmentByTag("MeasurementListFragment")).addRoomListAdapter.notifyDataSetChanged();

                fm1.beginTransaction().show(fm1.findFragmentByTag("MeasurementListFragment")).commit();
                ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.INVISIBLE);
                ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
                fm1.beginTransaction().remove(this).commit();

            }
        }

        else if ( v.getId()== R.id.mess_fragment_txt_room_desc)
        {
            json  = new JSONObject() ;
            try
            {
                spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Room", "ROOMS", "val2", this.getContext(), "8001",json.toString());
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position, SoapObject obj) {
                        mess_fragment_txt_room_desc.setText(item);
                        mess_fragment_txt_room_desc.setError(null);
                        mess_fragment_txt_room_code = obj.getProperty("val1").toString();
                        //lblUnitName.setText( obj.getProperty("val6").toString());
                        //((EditText)MeasurementAddRoomFragment.this.getView().findViewById(R.id.p8009_txt_price)).setText(item_price);
                    }
                });
            }
            catch (Exception e)
            {

            }
        }

        else if ( v.getId()== R.id.mess_fragment_txt_mgrp_desc)
        {
            json  = new JSONObject() ;
            try
            {
                spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search MAIN GROUP", "MAINGROUP", "val2", this.getContext(), "8009",json.toString());
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position, SoapObject obj) {
                        mess_fragment_txt_mgrp_desc.setText(item);
                        mess_fragment_txt_mgrp_desc.setError(null);
                        mess_fragment_txt_mgrp_code = obj.getProperty("val1").toString();
                    }
                });

            }
            catch (Exception e)
            {

            }
        }

        else if ( v.getId()== R.id.mess_fragment_txt_item_desc)
        {
            json  = new JSONObject() ;
            try
            {
                json.put("JSON_MGRP_CODE" , mess_fragment_txt_mgrp_code ) ;
                json.put("JSON_STORE_CODE" , userInfo.getUSER_STORE() ) ;
                spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search ITEMS", "ITEMS", "val2", this.getContext(), "8009",json.toString());
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position, SoapObject obj) {
                        mess_fragment_txt_item_desc.setText(item);
                        mess_fragment_txt_item_desc.setError(null);
                        mess_fragment_txt_item_code = obj.getProperty("val1").toString();
                        unit_code = obj.getProperty("val3").toString();
                        qnty_type = obj.getProperty("val4").toString();
                    }
                });
            }
            catch (Exception e)
            {

            }
        }

        else if ( v.getId()== R.id.mess_fragment_txt_color_desc)
        {
            json  = new JSONObject() ;
            try
            {
                json.put("JSON_ITEM_CODE" , mess_fragment_txt_item_code ) ;
                json.put("JSON_UNIT_CODE" , unit_code ) ;
                json.put("JSON_QNTY_TYPE" , qnty_type ) ;
                json.put("JSON_STORE_CODE" , userInfo.getUSER_STORE() ) ;

                spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search COLORS", "COLORS", "val2", this.getContext(), "8009",json.toString());
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position, SoapObject obj) {
                        mess_fragment_txt_color_desc.setText(item);
                        mess_fragment_txt_color_desc.setError(null);
                        mess_fragment_txt_color_code = obj.getProperty("val1").toString();
                    }
                });

            }
            catch (Exception e)
            {

            }
        }
    }

    public String validateAddroom()
    {
        //return "T" ;

        {// BEGIN of setError for all required fields

            if (TextUtils.isEmpty(mess_fragment_txt_room_code))
            {
                mess_fragment_txt_room_desc.setError("");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_mgrp_code))
            {
                mess_fragment_txt_mgrp_desc.setError("");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_item_code))
            {
                mess_fragment_txt_item_desc.setError("");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_color_code))
            {
                mess_fragment_txt_color_desc.setError("");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_width.getText()))
            {
                mess_fragment_txt_width.setError("يجب إدخال عرض الغرفة بالمتر");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_length.getText()))
            {
                mess_fragment_txt_length.setError("يجب إدخال طول الغرفة بالمتر");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_frameQnty.getText()))
            {
                mess_fragment_txt_frameQnty.setError("يجب إدخال عدد الإطارات Frames");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_dividerQnty.getText()))
            {
                mess_fragment_txt_dividerQnty.setError("يجب إدخال عدد الفواصل Dividers");
            }

            if (TextUtils.isEmpty(mess_fragment_txt_reducerQnty.getText()))
            {
                mess_fragment_txt_reducerQnty.setError("يجب إدخال عدد الفواصل Reducers");
            }
        }// END of setError for all required fields

        {// BEGIN of validate fields
            if (TextUtils.isEmpty(mess_fragment_txt_room_code)) {
                Toast.makeText(mContext, " يجب اختيار نوع الغرفة ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_room_desc.setError("");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_mgrp_code)) {
                Toast.makeText(mContext, " يجب اختيار النشاط ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_mgrp_desc.setError("");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_item_code)) {
                Toast.makeText(mContext, " يجب اختيار الصنف ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_item_desc.setError("");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_color_code)) {
                Toast.makeText(mContext, " يجب اختيار اللون ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_color_desc.setError("");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_width.getText())) {
                Toast.makeText(mContext, " يجب إدخال عرض الغرفة بالمتر ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_width.requestFocus();
                mess_fragment_txt_width.setError("يجب إدخال عرض الغرفة بالمتر");
                return "F";
            } else if (Double.parseDouble(mess_fragment_txt_width.getText().toString()) <= 0) {
                Toast.makeText(mContext, " عرض الغرفة يجب أن يكون أكبر من 0 ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_width.requestFocus();
                mess_fragment_txt_width.setError("عرض الغرفة يجب أن يكون أكبر من 0");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_length.getText())) {
                Toast.makeText(mContext, " يجب إدخال طول الغرفة بالمتر ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_length.requestFocus();
                mess_fragment_txt_length.setError("يجب إدخال طول الغرفة بالمتر");
                return "F";
            } else if (Double.parseDouble(mess_fragment_txt_length.getText().toString()) <= 0) {
                Toast.makeText(mContext, " طول الغرفة يجب أن يكون أكبر من 0 ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_length.requestFocus();
                mess_fragment_txt_length.setError("طول الغرفة يجب أن يكون أكبر من 0");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_frameQnty.getText())) {
                Toast.makeText(mContext, " يجب إدخال عدد الإطارات Frames ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_frameQnty.requestFocus();
                mess_fragment_txt_frameQnty.setError("يجب إدخال عدد الإطارات Frames");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_dividerQnty.getText())) {
                Toast.makeText(mContext, " يجب إدخال عدد الفواصل Dividers ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_dividerQnty.requestFocus();
                mess_fragment_txt_dividerQnty.setError("يجب إدخال عدد الفواصل Dividers");
                return "F";
            } else if (TextUtils.isEmpty(mess_fragment_txt_reducerQnty.getText())) {
                Toast.makeText(mContext, " يجب إدخال عدد الفواصل Reducers ", Toast.LENGTH_SHORT).show();
                mess_fragment_txt_reducerQnty.requestFocus();
                mess_fragment_txt_reducerQnty.setError("يجب إدخال عدد الفواصل Reducers");
                return "F";
            }
        }// BEGIN of validate fields

        return "T" ;

    }
}
