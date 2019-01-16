package com.sff.sffapp.SalesTicket;
import android.support.v4.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
/**
 * A simple {@link Fragment} subclass.
 */
public class SalesTicketStartFragment extends Fragment   implements View.OnClickListener   {
    SpinnerDialog spinnerDialog ;
    TextView p8009_txt_ticket_type_desc,
             p8009_txt_cust_type  ,
             p8009_txt_loc_type ,
             p8009_txt_channel  ,
             p8009_txt_cntry  ,
             p8009_txt_city  ,
             p8009_txt_district  ;
    JSONObject json ;
        public SalesTicketStartFragment()
        {
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((TextView)((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_txt_title)).setText(getString(R.string.headerTitle_STARTSALESTICKET));
        View v = inflater.inflate(R.layout.fragment_sales_ticket_start_fragment, container, false);
        v.findViewById(R.id.p8009_txt_customer_name).requestFocus();
        p8009_txt_ticket_type_desc =  ((TextView)v.findViewById(R.id.p8009_txt_ticket_type_desc)) ;
        p8009_txt_cust_type = ((TextView)v.findViewById(R.id.p8009_txt_cust_type)) ;
        p8009_txt_loc_type = ((TextView)v.findViewById(R.id.p8009_txt_loc_type)) ;
        p8009_txt_channel = ((TextView)v.findViewById(R.id.p8009_txt_channel)) ;
        p8009_txt_cntry = ((TextView)v.findViewById(R.id.p8009_txt_cntry)) ;
        p8009_txt_city = ((TextView)v.findViewById(R.id.p8009_txt_city)) ;
        p8009_txt_district = ((TextView)v.findViewById(R.id.p8009_txt_district)) ;
        p8009_txt_ticket_type_desc.setOnClickListener(this);
        p8009_txt_cust_type.setOnClickListener(this) ;
        p8009_txt_loc_type.setOnClickListener(this) ;
        p8009_txt_channel.setOnClickListener(this) ;
        p8009_txt_cntry.setOnClickListener(this) ;
        p8009_txt_city.setOnClickListener(this) ;
        p8009_txt_district.setOnClickListener(this) ;
        return v ;
    }
    public void onClick(View v) {
        if (v.getId() == R.id.p8009_txt_ticket_type_desc) {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "نوع التذكرة", "TICKETTYPE", "val2", this.getContext(), "8009", "");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                                   @Override
                                                   public void onClick(String item, int position, SoapObject obj) {
           p8009_txt_ticket_type_desc.setText(item);
           ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_ticket_type_desc_val = obj.getProperty("val1").toString();

                                                   }
                                               }
            );
        }else if (v.getId() == R.id.p8009_txt_cust_type) {

            spinnerDialog = new SpinnerDialog(this.getActivity(), "نوع العميل", "CUSTTYPE", "val2", this.getContext(), "8009", "");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_cust_type.setText(item);
                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_cust_type_val = obj.getProperty("val1").toString();

                }
            }
            );
        } else if (v.getId() == R.id.p8009_txt_loc_type) {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "نوع الموقع", "LOCATIONTYPE", "val2", this.getContext(), "8009", "");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_loc_type.setText(item);

                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_loc_type_val = obj.getProperty("val1").toString();

                }
            });
        }
        else if (v.getId() == R.id.p8009_txt_channel) {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "نوع قناة الاتصال", "CONNECTIONTYPE", "val2", this.getContext(), "8009", "");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_channel.setText(item);
                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_channel_val = obj.getProperty("val1").toString();
                }
            });
        }
        else if (v.getId() == R.id.p8009_txt_cntry) {

            spinnerDialog = new SpinnerDialog(this.getActivity(), "البلد", "CNTRY", "val2", this.getContext(), "8009", "");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_cntry.setText(item);
                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_cntry_val = obj.getProperty("val1").toString();
                    p8009_txt_city.setText("المدينة");
                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_city_val = null;
                    p8009_txt_district.setText("الحي");
                    ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_district_val = null;
                }

            });
        }

        else if ( v.getId() == R.id.p8009_txt_city ) {
            if (((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_cntry_val == null) {
                Toast.makeText(this.getContext(), "يجب اختيار البلد", Toast.LENGTH_SHORT).show();
                p8009_txt_cntry.requestFocus();
            } else {
                json = new JSONObject();
                try {
                    json.put("JSON_CNTRY_CODE", ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_cntry_val);
                    spinnerDialog = new SpinnerDialog(this.getActivity(), "المدينة", "CITY", "val2", this.getContext(), "8009", json.toString());

                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position, SoapObject obj) {
                            p8009_txt_city.setText(item);
                            ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_city_val = obj.getProperty("val1").toString();
                            p8009_txt_district.setText("الحي");
                            ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_district_val = null;
                        }
                    });

                } catch (Exception e)

                {

                }
            }
        }
        else if (v.getId() == R.id.p8009_txt_district)
        {
            if (((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_cntry_val ==null  ){
                Toast.makeText(this.getContext(), "يجب اختيار البلد والمدينة", Toast.LENGTH_SHORT).show();
                p8009_txt_cntry.requestFocus();
            }

            if ( ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_city_val == null ){
                Toast.makeText(this.getContext(), "يجب اختيار البلد والمدينة", Toast.LENGTH_SHORT).show();
                p8009_txt_city.requestFocus();
            } else {
                json  = new JSONObject() ;
                try
                {
                    json.put("JSON_CNTRY_CODE" , p8009_txt_cntry ) ;
                    json.put("JSON_CITY_CODE" , p8009_txt_city ) ;
                    spinnerDialog = new SpinnerDialog(this.getActivity(), "اختيار الحي", "DISTRICTS", "val2", this.getContext(), "8009", json.toString());

                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position, SoapObject obj) {
                            p8009_txt_district.setText(item);
                            ((SalesTicket) SalesTicketStartFragment.this.getActivity()).p8009_txt_district_val = obj.getProperty("val1").toString();


                            //Toast.makeText(SalesTicket_old.this, CITY_code +"" , Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e)

                {
                    e.printStackTrace();
                }

            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
        ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
        }
        else
        {

            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
        }
    }

}
