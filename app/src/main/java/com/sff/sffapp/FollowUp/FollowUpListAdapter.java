package com.sff.sffapp.FollowUp;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.Payment.MainPaymentFragment;
import com.sff.sffapp.Measurement.MeasurementListFragment  ;

import com.sff.sffapp.R;
import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.Calendar;

public class FollowUpListAdapter extends BaseAdapter implements ConnectionInterface, View.OnClickListener , DatePickerDialog.OnDateSetListener
{
    String currFragment ;
    SpinnerDialog spinnerDialog ;
    Context mContext;
    LayoutInflater inflater;
    ArrayList<FollowUpListCell> ticketFollowUpArrayList;
    final Calendar calendar= Calendar.getInstance();
  //  ArrayList<OrderListCell> custOrderArrayList;

public FollowUpListAdapter(Context context , ArrayList<FollowUpListCell> TicketListCellsArray )
{   mContext = context;
    inflater = LayoutInflater.from(mContext);
    ticketFollowUpArrayList = TicketListCellsArray ;
}
    @Override
    public int getCount() {
        return ticketFollowUpArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return ticketFollowUpArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder
    {
        TextView ticketNo_desc ;
        TextView ticketDate_desc ;
        TextView custName_desc ;
        TextView address_desc ;
        TextView mobileNo_desc ;
        TextView rem_val_desc ;
        TextView measur_assign_ccs_team_desc ;
        TextView measurement_appt ;
        TextView install_assign_ccs_team_desc ;
        TextView install_date_app ;
        ImageView p8002_btn_measure_date ;
        ImageView p8002_btn_measure_team ;
        ImageView p8002_btn_rem_val ;
        ImageView p8002_btn_measurement ;
        ImageView p8002_btn_install_date ;
        ImageView p8002_btn_install_team ;
        ImageView p8002_isnew_flag  ;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if (view==null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.ticket_follow_list_cell, null);
            //locate the views in row.xml
            holder.ticketNo_desc = view.findViewById(R.id.p8002_txt_ticketNo_desc);
            holder.ticketDate_desc = view.findViewById(R.id.p8002_txt_ticketDate_desc);
            holder.custName_desc = view.findViewById(R.id.p8002_txt_custName_desc);
            holder.address_desc = view.findViewById(R.id.p8002_txt_address_desc);
            holder.mobileNo_desc = view.findViewById(R.id.p8002_txt_mobileNo_desc);
            holder.rem_val_desc = view.findViewById(R.id.p8002_txt_rem_val_desc);
            holder.measur_assign_ccs_team_desc = view.findViewById(R.id.p8002_txt_measur_assign_ccs_team_desc);
            holder.measurement_appt = view.findViewById(R.id.p8002_txt_measurement_appt);
            holder.install_assign_ccs_team_desc = view.findViewById(R.id.p8002_txt_install_assign_ccs_team_desc);
            holder.install_date_app = view.findViewById(R.id.p8002_txt_install_appt);
            holder.p8002_btn_measure_date = view.findViewById(R.id.p8002_btn_measure_date) ;
            holder.p8002_btn_measure_team = view.findViewById(R.id.p8002_btn_measure_team) ;
            holder.p8002_btn_install_team = view.findViewById(R.id.p8002_btn_install_team) ;
            holder.p8002_btn_install_date = view.findViewById(R.id.p8002_btn_install_date) ;
            holder.p8002_btn_rem_val =  view.findViewById(R.id.p8002_btn_rem_val) ;
            holder.p8002_btn_measurement =  view.findViewById(R.id.p8002_btn_measurement) ;
            holder.p8002_isnew_flag = view.findViewById(R.id.p8002_isnew_flag) ;
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        holder.ticketNo_desc.setText(ticketFollowUpArrayList.get(position).ticketNo_desc);
        holder.ticketDate_desc.setText(ticketFollowUpArrayList.get(position).ticketDate_desc);
        holder.custName_desc.setText(ticketFollowUpArrayList.get(position).custName_desc);
        holder.address_desc.setText(ticketFollowUpArrayList.get(position).address_desc);
        holder.mobileNo_desc.setText(ticketFollowUpArrayList.get(position).mobileNo_desc);
        holder.rem_val_desc.setText(ticketFollowUpArrayList.get(position).rem_val_desc);
        holder.rem_val_desc.setOnClickListener(this);
        holder.rem_val_desc.setTag(position);
        holder.p8002_btn_measure_team.setOnClickListener(this);
        holder.p8002_btn_measure_team.setTag(position);
        holder.measur_assign_ccs_team_desc.setText(ticketFollowUpArrayList.get(position).measur_assign_ccs_team_desc);
        holder.measur_assign_ccs_team_desc.setOnClickListener(this);
        holder.measur_assign_ccs_team_desc.setTag(position);
        holder.measurement_appt.setText(ticketFollowUpArrayList.get(position).measurement_appt);
        holder.measurement_appt.setOnClickListener(this);
        holder.measurement_appt.setTag(position);

        holder.p8002_btn_rem_val.setOnClickListener(this);
        holder.p8002_btn_rem_val.setTag(position);

        holder.p8002_btn_measurement.setOnClickListener(this);
        holder.p8002_btn_measurement.setTag(position);

        holder.p8002_btn_measure_date.setOnClickListener(this);
        holder.p8002_btn_measure_date.setTag(position);
        holder.p8002_btn_install_date.setOnClickListener(this);
        holder.p8002_btn_install_date.setTag(position);
        holder.install_assign_ccs_team_desc.setText(ticketFollowUpArrayList.get(position).install_assign_ccs_team_desc);
        holder.install_assign_ccs_team_desc.setOnClickListener(this);
        holder.install_assign_ccs_team_desc.setTag(position);
        holder.p8002_btn_install_team.setOnClickListener(this);
        holder.p8002_btn_install_team.setTag(position);
        holder.install_date_app.setText(ticketFollowUpArrayList.get(position).install_assign_date_appt);
        holder.install_date_app.setOnClickListener(this);
        holder.install_date_app.setTag(position);
        holder.p8002_isnew_flag.setTag(position);


        if (ticketFollowUpArrayList.get(position).ismeasurementdone.equals("0") )
        {
            holder.p8002_btn_measurement.setImageResource(R.drawable.ic_installation_1);
        }
        else
        {
            holder.p8002_btn_measurement.setImageResource(R.drawable.installation_done);
        }

        if (ticketFollowUpArrayList.get(position).isnew_flag.equals("T") )
        {
            //holder.p8002_isnew_flag.cu;
            holder.p8002_isnew_flag.setVisibility(View.VISIBLE);

        }
        else
         {
            holder.p8002_isnew_flag.setVisibility(View.INVISIBLE);

         }
        return view;
    }
    @Override
    public void onClick( View v) {
    if ( v.getId()== R.id.p8002_txt_install_appt || v.getId() == R.id.p8002_btn_install_date)
    {
            DatePickerDialog dpd =
            new DatePickerDialog(mContext, R.style.datepickerstyle ,this ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setTag((Integer) v.getTag());
            dpd.getDatePicker().setId(R.id.p8002_txt_install_appt);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis()  );
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000*60*60*24*10);
            dpd.show();
    }
    else if ( v.getId()== R.id.p8002_txt_measurement_appt || v.getId() == R.id.p8002_btn_measure_date)  {
        DatePickerDialog dpd =
        new DatePickerDialog(mContext, R.style.datepickerstyle ,this ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setTag((Integer) v.getTag());
        dpd.getDatePicker().setId(R.id.p8002_txt_measurement_appt);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis()  );
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000*60*60*24*10);
        dpd.show();
    }
    /***********go to payment************/
   else if ( v.getId()== R.id.p8002_txt_rem_val_desc || v.getId() == R.id.p8002_btn_rem_val)
   {
        //int remValue =  Integer.parseInt(ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).rem_val_desc) - 2500; // String.valueOf(remValue)
        currFragment = "FollowUpList" ;
        FragmentManager fm1 = ((FollowUp)mContext).getSupportFragmentManager();
        FragmentTransaction ftrans = fm1.beginTransaction();
        ftrans.addToBackStack(null);
        fm1.beginTransaction().hide(fm1.findFragmentByTag(currFragment)).commit();
        MainPaymentFragment f2 = new MainPaymentFragment();
        f2.baseClass = "FollowUpList" ;
        f2.maxVal    = Double.parseDouble(ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).rem_val_desc) ;
        try {
            f2.json.put("TICKETPK",ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).ticketNo_desc);
            f2.json.put("CASHIER_CODE",userInfo.getCASHIER_CODE());
            f2.json.put("USER_CODE",userInfo.getUSER_NO());


        }catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ftrans.add(R.id.TicketFollowUpWContainer, f2, "MainPaymentFragment").commit();
    }
    /***********go to measurement fragment************/
    else if ( v.getId() == R.id.p8002_btn_measurement)
    {
        currFragment = "FollowUpList" ;
        FragmentManager fm1 = ((FollowUp)mContext).getSupportFragmentManager();
        FragmentTransaction ftrans = fm1.beginTransaction();
        ftrans.addToBackStack(null);
        fm1.beginTransaction().hide(fm1.findFragmentByTag(currFragment)).commit();
        MeasurementListFragment f2 = new MeasurementListFragment();
        f2.baseClass = "FollowUpList" ;
        try {

            f2.json.put("ACTION_TYPE","ADDMEASUREMENT");
            f2.json.put("TICKETPK",ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).ticketNo_desc);
            f2.json.put("USER_CODE",userInfo.getUSER_NO());
        }catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        ftrans.add(R.id.TicketFollowUpWContainer, f2, "MeasurementListFragment").commit();
    }
    /***********************/
    else if ( v.getId()== R.id.p8002_txt_measur_assign_ccs_team_desc || v.getId() == R.id.p8002_btn_measure_team)  {
        JSONObject json;
        final View currView = v;
        try
        {   json  = new JSONObject() ; //
            json.put("ASSIGN_CCS_CODE" , ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).assign_ccs_code ) ;
            spinnerDialog = new SpinnerDialog((FollowUp)mContext,"Select or Search Team","MEASUREMENT_TEAM","val2",mContext, "8002" , json.toString() );
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).measur_assign_ccs_team_desc =  obj.getProperty("val2").toString();
                    ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).measur_assign_ccs_team_code =  obj.getProperty("val1").toString();
                    /********************************************/
                    try
                    {
                        JSONObject json2;
                        json2  = new JSONObject() ;
                        json2.put("ACTION_TYPE" , "UPD_MES_TEAM_CODE" ) ;
                        json2.put("TICKETPK" , ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).ticketNo_desc) ;
                        json2.put("USER_CODE" , userInfo.getUSER_NO()) ;
                        json2.put("CCS_TEAM_CODE" , obj.getProperty("val1").toString()) ;
                        DBconnection dBconnection = new DBconnection(json2.toString() , "TICKET_ACTIONS" ,FollowUpListAdapter.this , mContext , "UPD_MES_TEAM_CODE"  ) ;
                        dBconnection.execute();
                        FollowUpListAdapter.this.notifyDataSetChanged();
                    }
                    catch ( Exception e )
                    {

                        Toast.makeText(mContext, "CCS_TEAM_CODE!", Toast.LENGTH_LONG).show();
                    }
                    /********************************************/
                    FollowUpListAdapter.this.notifyDataSetChanged();
                }});
        }
        catch ( Exception e )
        {
            Toast.makeText(mContext, "ASSIGN_CCS_CODE", Toast.LENGTH_LONG).show();
        }
    }
    else if ( v.getId()== R.id.p8002_txt_install_assign_ccs_team_desc || v.getId() == R.id.p8002_btn_install_team)  {
        JSONObject json;
        final View currView = v;
        try
        {   json  = new JSONObject() ; //
            json.put("ASSIGN_CCS_CODE" , ticketFollowUpArrayList.get(Integer.parseInt(v.getTag().toString())).assign_ccs_code ) ;
            spinnerDialog = new SpinnerDialog((FollowUp)mContext,"Select or Search Team","INSTALL_TEAM","val2",mContext, "8002" , json.toString() );
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).install_assign_ccs_team_desc =  obj.getProperty("val2").toString();
                    ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).install_assign_ccs_team_code =  obj.getProperty("val1").toString();
                    /********************************************/
                    try
                    {
                        JSONObject json2;
                        json2  = new JSONObject() ;
                        json2.put("ACTION_TYPE" , "UPD_INSTALL_TEAM_CODE" ) ;
                        json2.put("TICKETPK" , ticketFollowUpArrayList.get(Integer.parseInt(currView.getTag().toString())).ticketNo_desc) ;
                        json2.put("USER_CODE" , userInfo.getUSER_NO()) ;
                        json2.put("CCS_TEAM_CODE" , obj.getProperty("val1").toString()) ;
                        DBconnection dBconnection = new DBconnection(json2.toString() , "TICKET_ACTIONS" ,FollowUpListAdapter.this , mContext , "UPD_INSTALL_TEAM_CODE"  ) ;
                        dBconnection.execute();
                        FollowUpListAdapter.this.notifyDataSetChanged();
                    }
                    catch ( Exception e )
                    {
                        Toast.makeText(mContext, "CCS_TEAM_CODE!", Toast.LENGTH_LONG).show();
                    }
                    /********************************************/
                    FollowUpListAdapter.this.notifyDataSetChanged();
                }});
        }
        catch ( Exception e )
        {
            Toast.makeText(mContext, "ERR", Toast.LENGTH_LONG).show();
        }
    }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if ( view.getId()== R.id.p8002_txt_measurement_appt || view.getId() == R.id.p8002_btn_measure_date)  {
            JSONObject json;
            try
            {
                json  = new JSONObject() ; ;
                json.put("ACTION_TYPE" , "MAPPT" ) ;
                json.put("TICKETPK" , ticketFollowUpArrayList.get(Integer.parseInt(view.getTag().toString())).ticketNo_desc) ;
                json.put("USER_CODE" , userInfo.getUSER_NO()) ;
                json.put("APPT_DATE" ,dayOfMonth+ "-" + (month+1) +"-" + year  ) ;
                DBconnection dBconnection = new DBconnection(json.toString() , "TICKET_ACTIONS" , this , mContext , "UPDATE_MAPPT"  ) ;
                dBconnection.execute();
                ticketFollowUpArrayList.get(Integer.parseInt(view.getTag().toString())).measurement_appt =dayOfMonth+ "-" + (month+1) +"-" + year  ;
                this.notifyDataSetChanged();
            }
            catch ( Exception e )
            {
                Toast.makeText(mContext, "!!ORDER_INFO!!", Toast.LENGTH_LONG).show();
            }
        }
        else if ( view.getId()== R.id.p8002_txt_install_appt || view.getId() == R.id.p8002_btn_install_date)  {
            JSONObject json;
            try
            {
                json  = new JSONObject() ; ;
                json.put("ACTION_TYPE" , "IAPPT" ) ;
                json.put("TICKETPK" , ticketFollowUpArrayList.get(Integer.parseInt(view.getTag().toString())).ticketNo_desc) ;
                json.put("USER_CODE" , userInfo.getUSER_NO()) ;
                json.put("APPT_DATE" ,dayOfMonth+ "-" + (month+1) +"-" + year  ) ;
                DBconnection dBconnection = new DBconnection(json.toString() , "TICKET_ACTIONS" , this , mContext , "UPDATE_IAPPT"  ) ;
                dBconnection.execute();
                ticketFollowUpArrayList.get(Integer.parseInt(view.getTag().toString())).install_assign_date_appt =dayOfMonth+ "-" + (month+1) +"-" + year  ;
                this.notifyDataSetChanged();
            }
            catch ( Exception e )
            {
                Toast.makeText(mContext, "!!ORDER_INFO!!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(mContext, "TAG=ingal3", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {
        SoapObject obj1;
        SoapObject obj2;
        obj1=obj;
        JSONObject json ;
        String val1_JSON_OUT = null ,val2_JSON_OUT=null;
        if (method_id== "UPD_INSTALL_TEAM_CODE" ) {
            if (obj1 != null)
            {
                obj2 = (SoapObject) obj.getProperty(0);
                val1_JSON_OUT = obj2.getPrimitiveProperty("val1").toString();
                val2_JSON_OUT = obj2.getPrimitiveProperty("val2").toString();
                Toast.makeText(mContext, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
                json  = new JSONObject() ;
                try
                {
                    json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
                }   catch (JSONException e) {
                    e.printStackTrace();
                }
                DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this,mContext , "");
                dBconnection.execute();
                this.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(mContext, "No data found-CCS_TEAM_CODE", Toast.LENGTH_SHORT).show();
            }
        }

        else if (method_id== "UPD_MES_TEAM_CODE" ) {
            if (obj1 != null)
            {
                obj2 = (SoapObject) obj.getProperty(0);
                val1_JSON_OUT = obj2.getPrimitiveProperty("val1").toString();
                val2_JSON_OUT = obj2.getPrimitiveProperty("val2").toString();
                Toast.makeText(mContext, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
                json  = new JSONObject() ;
                try
                {
                    json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
                }   catch (JSONException e) {
                    e.printStackTrace();
                }
                DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this,mContext , "");
                dBconnection.execute();
                this.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(mContext, "No data found-CCS_TEAM_CODE", Toast.LENGTH_SHORT).show();
            }
        }else if (method_id == "UPDATE_MAPPT" ) {
            if (obj1 != null)
            {
                obj2 = (SoapObject) obj.getProperty(0);
                val1_JSON_OUT = obj2.getPrimitiveProperty("val1").toString();
                val2_JSON_OUT = obj2.getPrimitiveProperty("val2").toString();
                Toast.makeText(mContext, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
                json  = new JSONObject() ;
                try
                {
                    json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
                }   catch (JSONException e) {
                    e.printStackTrace();
                }
                DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this,mContext , "");
                dBconnection.execute();
                this.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(mContext, "No data found-UPDATE_MAPPT", Toast.LENGTH_SHORT).show();
            }

        }
        else if (method_id== "UPDATE_IAPPT" ) {
            if (obj1 != null)
            {
                obj2 = (SoapObject) obj.getProperty(0);
                val1_JSON_OUT = obj2.getPrimitiveProperty("val1").toString();
                val2_JSON_OUT = obj2.getPrimitiveProperty("val2").toString();
                Toast.makeText(mContext, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
                json  = new JSONObject() ;
                try
                {
                    json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
                }   catch (JSONException e) {
                    e.printStackTrace();
                }
                DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this,mContext , "");
                dBconnection.execute();
                this.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(mContext, "No data found-UPDATE_MAPPT", Toast.LENGTH_SHORT).show();
            }

        }








    }




}