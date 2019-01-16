package com.sff.sffapp.SalesTicket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.Payment.MainPaymentFragment;
import com.sff.sffapp.R;
import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class SalesTicket extends AppCompatActivity implements ConnectionInterface,View.OnClickListener {
    private FrameLayout  frameLayoutfram1 , FrameAddItem;
    String currFragment ;
    public    Toolbar myToolbar;
    public Toolbar myFooterToolbar ;

    SpinnerDialog spinnerDialog ;
    EditText txt_contact_person_name ;
    EditText txt_cust_mob_no;
    EditText txt_cust_email ;
    TextView txtcusttype ;
    String cust_type ;
    TextView txt_loc_type ;
    String location_type;
    TextView txt_channel;
    String channel_type;
    EditText txt_notes ;

    JSONObject save_josn ;



    public  String
            trans_date,
            p8009_txt_ticket_type_desc_val ,
            p8009_txt_cust_type_val ,
            p8009_txt_loc_type_val ,
            p8009_txt_channel_val  ,
            p8009_txt_cntry_val  ,
            p8009_txt_city_val  ,
            p8009_txt_district_val   ;
            TextView  p8009_txt_ticket_type_desc ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_ticket);

        myToolbar = (Toolbar) findViewById(R.id.sff_toolbar);
        setSupportActionBar(myToolbar);
        myFooterToolbar = (Toolbar) findViewById(R.id.sff_footerBar);
        setSupportActionBar(myToolbar);
        myToolbar.findViewById(R.id.toolbar_txt_title).setVisibility(View.VISIBLE);
        ((TextView)myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Sales Ticket");
        myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
        myToolbar.findViewById(R.id.toolbar_btn_next).setOnClickListener(this);
        myToolbar.findViewById(R.id.toolbar_btn_prev).setOnClickListener(this);
        myFooterToolbar.findViewById(R.id.footer_btn_save).setOnClickListener(this);
        frameLayoutfram1  = (FrameLayout)findViewById(R.id.FragmentContainer);
        SalesTicketStartFragment f1 = new SalesTicketStartFragment();
        FragmentManager fm1 = getSupportFragmentManager();
        FragmentTransaction ftrans = fm1.beginTransaction();
        ftrans.addToBackStack(null);
        ftrans.add(R.id.FragmentContainer,f1,"SalesTicketStartFragment").commit();
        currFragment = "SalesTicketStartFragment" ;
    }
    @Override
    public void onClick(View v)
    {

        if ( v.getId()== R.id.toolbar_btn_next )
        {

            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().hide(fm1.findFragmentByTag(currFragment)).commit();

            if (currFragment.equals("SalesTicketStartFragment") )
            {
                /**/
                p8009_txt_ticket_type_desc =  ((TextView)v.findViewById(R.id.p8009_txt_ticket_type_desc)) ;


                /**/

                if ( validateAddroom().equals("T"))
                {
                    if ( fm1.findFragmentByTag("SalesTicketListFragment")== null )
                    {
                        SalesTicketListFragment f2 = new SalesTicketListFragment();
                        ftrans.add(R.id.FragmentContainer, f2, "SalesTicketListFragment").commit();
                        currFragment = "SalesTicketListFragment";

                    }
                    else
                    {
                        fm1.beginTransaction().show(fm1.findFragmentByTag("SalesTicketListFragment")).commit();
                        currFragment = "SalesTicketListFragment";
                    }
                }
            }

            else if (currFragment.equals("SalesTicketListFragment") )
            {

                if (fm1.findFragmentByTag("MainPaymentFragment")== null )
                {
                    MainPaymentFragment f2 = new MainPaymentFragment();
                    f2.baseClass = "SalesTicket" ;
                    ftrans.add(R.id.FragmentContainer, f2, "MainPaymentFragment").commit();
                    currFragment = "MainPaymentFragment";
                }
                else
                {
                    fm1.beginTransaction().show(fm1.findFragmentByTag("MainPaymentFragment")).commit();
                    currFragment = "MainPaymentFragment";
                }

            }
        }
        else  if ( v.getId()== R.id.toolbar_btn_prev )
        {

            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().hide(fm1.findFragmentByTag(currFragment)).commit();
           // myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
            //myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
            if (currFragment.equals("SalesTicketListFragment"  ) )
            {
                fm1.beginTransaction().show(fm1.findFragmentByTag("SalesTicketStartFragment")).commit();
                currFragment = "SalesTicketStartFragment";

            }
            else  if (currFragment.equals("MainPaymentFragment"  ) )
            {
                fm1.beginTransaction().show(fm1.findFragmentByTag("SalesTicketListFragment")).commit();
                currFragment = "SalesTicketListFragment";

            }
        }
        else  if ( v.getId()== R.id.p8009_txt_cust_type)
        {
            Toast.makeText(this, "يجب ادخال اسم العميل", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.footer_btn_save)
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //Toast.makeText(SalesTicket.this, "saved", Toast.LENGTH_SHORT).show();
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            dbSave();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(SalesTicket.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }

    }
    @Override
    public void onBackPressed()
    {

    }
    public void dbSave()
    {
        //((SalesTicket)this.getActivity()).save_josn = new JSONObject( );
        FragmentManager fm1 = getSupportFragmentManager();
        JSONObject j = new JSONObject();
        JSONArray jsonArrayList = new JSONArray();
        ArrayList<SalesTicketAddItemCell> addItemArray;
        addItemArray = ((SalesTicketListFragment)fm1.findFragmentByTag("SalesTicketListFragment")).addItemArray;
        try
        {
            j.put("USER_STORE", userInfo.getUSER_STORE());
            j.put("TRANS_DATE", trans_date == null ? "" : trans_date) ;
            j.put("USER_CODE", userInfo.getUSER_NO());
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            j.put("TICKET_TYPE_CODE", this.p8009_txt_ticket_type_desc_val == null ? "" : this.p8009_txt_ticket_type_desc_val);
            j.put("CUSTOMER_NAME", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_customer_name)).getText().toString());
            j.put("CUSTOMER_MOBILE_NO", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_cust_mob_no)).getText().toString());
            j.put("CUSTOMER_EMAIL", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_cust_email)).getText().toString());
            j.put("CUST_TYPE_CODE", this.p8009_txt_cust_type_val == null ? "" : this.p8009_txt_cust_type_val);
            j.put("CUST_LOC_TYPE", this.p8009_txt_loc_type_val == null ? "" : this.p8009_txt_loc_type_val);
            j.put("CUST_CHANNEL_TYPE", this.p8009_txt_channel_val == null ? "" : this.p8009_txt_channel_val);
            j.put("CUSTOMER_NOTE", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_notes)).getText().toString());
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            j.put("CUST_CONTACT_NAME", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_contact_name)).getText().toString());
            j.put("CUST_CONTACT_MOBILE_NO", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_contact_mob_no)).getText().toString());
            j.put("CUST_CONTACT_EMAIL", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_contact_email)).getText().toString());
            j.put("CUST_CONTACT_RELATIONSHIP", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_relationship)).getText().toString());
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            j.put("CUST_CNTRY_CODE", this.p8009_txt_cntry_val == null ? "" : this.p8009_txt_cntry_val);
            j.put("CUST_CITY_CODE", this.p8009_txt_city_val == null ? "" : this.p8009_txt_city_val);
            j.put("CUST_DISTRICT_CODE", this.p8009_txt_district_val == null ? "" : this.p8009_txt_district_val);
            j.put("CUST_ADDRESS", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_address)).getText().toString());
            j.put("STREET_NAME", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_street)).getText().toString());
            j.put("NEAREST_SIGN", ((EditText) ((SalesTicketStartFragment) fm1.findFragmentByTag("SalesTicketStartFragment")).getView().findViewById(R.id.p8009_txt_nearest_sign)).getText().toString());
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
            j.put("DTL_COUNT", addItemArray.size());


            for (int i = 0; i < addItemArray.size(); i++) {
                JSONObject x = new JSONObject();
                x.put("SER_NO", addItemArray.get(i).v_ser_no);
                x.put("MGRP_CODE", addItemArray.get(i).v_mgrp_code);
                x.put("UNIT_CODE", addItemArray.get(i).v_unit_code);
                x.put("QNTY_TYPE", addItemArray.get(i).v_qnty_type);
                x.put("ITEM_CODE", addItemArray.get(i).v_item_code);
                x.put("COLOR_CODE", addItemArray.get(i).v_color_code);
                x.put("NOTE", addItemArray.get(i).v_note);
                //x.put("FROM_QNTY" , addItemArray.get(i).v_from_qnty) ;
                //x.put("TO_QNTY" , addItemArray.get(i).v_to_qnty) ;
                x.put("QNTY", addItemArray.get(i).v_qnty);
                x.put("ITEM_PRICE", addItemArray.get(i).v_item_price);

                jsonArrayList.put(x);
                x = null;
            }
            j.put("DTLLIST", jsonArrayList);
            Log.i("json", j.toString());*/
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        DBconnection dBconnection = new DBconnection(j.toString(), "INS_TICKET_8009", this, this , "");
        dBconnection.execute();
    }

    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {
        //Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        SoapObject obj1;
        SoapObject obj2;
        obj1=obj;
        String val1_JSON_OUT=null ,
               val2_JSON_OUT=null;
        if (obj1 != null)
        {
            obj2 = (SoapObject) obj.getProperty(0);
            val1_JSON_OUT = obj2.getProperty("val1").toString();
            val2_JSON_OUT = obj2.getProperty("val2").toString();
            Toast.makeText(SalesTicket.this, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(SalesTicket.this, "لا توجد بيانات", Toast.LENGTH_SHORT).show();
        }

        if (val1_JSON_OUT.equals("T"))
        {
            SalesTicket.this.finish();
        }
    }

    public String validateAddroom()
    {

        {// BEGIN of setError for all required fields

            if (TextUtils.isEmpty(p8009_txt_ticket_type_desc_val) )
            {
                p8009_txt_ticket_type_desc.setError("");

            }
        }// END of setError for all required fields

        {// BEGIN of validate fields
            if (TextUtils.isEmpty(p8009_txt_ticket_type_desc_val))
            {
                Toast.makeText(SalesTicket.this, " يجب اختيار نوع الغرفة ", Toast.LENGTH_SHORT).show();
                p8009_txt_ticket_type_desc.setError("");
                return "F";
            }
        }// BEGIN of validate fields

        return "T" ;

    }
}

