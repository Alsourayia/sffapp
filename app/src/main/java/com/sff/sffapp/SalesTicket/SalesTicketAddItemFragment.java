package com.sff.sffapp.SalesTicket;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;
import com.sff.sffapp.database.GeneralReference;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
/**
 * A simple {@link Fragment} subclass.
 */
public class SalesTicketAddItemFragment extends Fragment  implements View.OnClickListener {
    SpinnerDialog spinnerDialog ;
    TextView p8009_txt_mgrp_desc ;
    String p8009_txt_mgrp_desc_val;
    TextView p8009_txt_item_desc;
    String p8009_txt_item_desc_val  ;
    TextView p8009_txt_color_name;
    String p8009_txt_color_name_val ;
    String item_price ;
    EditText p8009_txt_price;
    JSONObject json ;
    public SalesTicketAddItemFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setOnClickListener(this);
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setOnClickListener(this);
        View v = inflater.inflate(R.layout.fragment_sales_ticket_add_item_fragment, container, false);
        p8009_txt_mgrp_desc = v.findViewById(R.id.mess_fragment_txt_room_desc) ;
        p8009_txt_mgrp_desc.setOnClickListener(this);
        p8009_txt_mgrp_desc.requestFocus() ;
        p8009_txt_item_desc = v.findViewById(R.id.p8009_txt_item_desc) ;
        p8009_txt_item_desc.setOnClickListener(this);
        p8009_txt_color_name = v.findViewById(R.id.p8009_txt_color_name) ;
        p8009_txt_color_name.setOnClickListener(this);
        p8009_txt_price = v.findViewById(R.id.p8009_txt_price) ;
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.VISIBLE);
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
        ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
        ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.VISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    public void onClick(View v){

        if (v.getId() == R.id.footer_btn_check) {
            FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
            SalesTicketAddItemCell mItemCell ;
            mItemCell = new SalesTicketAddItemCell();
            mItemCell.v_ser_no = (((SalesTicketListFragment)fm1.findFragmentByTag("SalesTicketListFragment")).addItemArray.size()+1);
            mItemCell.v_mgrp_desc = p8009_txt_mgrp_desc.getText().toString();
            mItemCell.v_mgrp_code = p8009_txt_mgrp_desc_val;
            mItemCell.v_item_desc = p8009_txt_item_desc.getText().toString();
            mItemCell.v_item_code = p8009_txt_item_desc_val ;
            mItemCell.v_color_name = p8009_txt_color_name.getText().toString();
            mItemCell.v_color_code = p8009_txt_color_name_val;
            mItemCell.v_note = ((EditText)fm1.findFragmentByTag("SalesTicketAddItemFragment").getView().findViewById(R.id.p8009_txt_item_note)).getText().toString();
            mItemCell.v_item_price= ((EditText)fm1.findFragmentByTag("SalesTicketAddItemFragment").getView().findViewById(R.id.p8009_txt_price)).getText().toString();
            ((SalesTicketListFragment)fm1.findFragmentByTag("SalesTicketListFragment")).addItemArray.add(0,mItemCell);
            ((SalesTicketListFragment)fm1.findFragmentByTag("SalesTicketListFragment")).addItemListAdapter.notifyDataSetChanged();
            fm1.beginTransaction().show(fm1.findFragmentByTag("SalesTicketListFragment")).commit();
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
            fm1.beginTransaction().remove(this).commit();
        }
        else
            if (v.getId() == R.id.footer_btn_cancel)
            {
            FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
            fm1.beginTransaction().show(fm1.findFragmentByTag("SalesTicketListFragment")).commit();
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_check).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
            fm1.beginTransaction().remove(this).commit();
            }
        else if ( v.getId()== R.id.mess_fragment_txt_room_desc)
        {
            //Toast.makeText(this, "toolbar_btn_prev", Toast.LENGTH_SHORT).show();
            spinnerDialog = new SpinnerDialog(this.getActivity(),getString(R.string.spainner_headeTitle_for_activity),"MAINGROUP","val2",this.getContext() , "8009" , "" );
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_mgrp_desc.setText( item );
                    p8009_txt_mgrp_desc_val = obj.getProperty("val1").toString();    }
            });
        }
        else if ( v.getId()== R.id.p8009_txt_item_desc)
        {
            //Toast.makeText(this, "toolbar_btn_prev", Toast.LENGTH_SHORT).show();
            json  = new JSONObject() ;
            try
            {
                json.put("JSON_MGRP_CODE" , p8009_txt_mgrp_desc_val ) ;
                json.put("JSON_STORE_CODE" , userInfo.getUSER_STORE() ) ;
                spinnerDialog = new SpinnerDialog(this.getActivity(), getString(R.string.spainner_headerTitle_for_item), "ITEMS", "val2", this.getContext(), "8009",json.toString());
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position, SoapObject obj) {
                        p8009_txt_item_desc.setText(item);
                        p8009_txt_item_desc_val = obj.getProperty("val1").toString();
                        item_price = obj.getProperty("val5").toString();
                        ((EditText)SalesTicketAddItemFragment.this.getView().findViewById(R.id.p8009_txt_price)).setText(item_price);

                    }
                });
            } catch (Exception e)

            {

            }
        }
        else if ( v.getId()== R.id.p8009_txt_color_name) {
            //Toast.makeText(this, "toolbar_btn_prev", Toast.LENGTH_SHORT).show();
            if (p8009_txt_item_desc_val == null)
            {
                Toast.makeText(this.getContext(), "Must Select An item",Toast.LENGTH_SHORT).show();
            }
            else
            {
                json  = new JSONObject() ;
                try
                {
                    json.put("JSON_ITEM_CODE" , p8009_txt_item_desc_val ) ;
                    json.put("JSON_STORE_CODE" , userInfo.getUSER_STORE() ) ;

                    spinnerDialog = new SpinnerDialog(this.getActivity(), getString(R.string.spainner_headerTitle_for_color), "COLORS", "val2", this.getContext(), "8009",json.toString());
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position, SoapObject obj) {
                            p8009_txt_color_name.setText(item);

                            p8009_txt_color_name_val = obj.getProperty("val1").toString();

                            item_price = obj.getProperty("val3").toString();
                            ((EditText)SalesTicketAddItemFragment.this.getView().findViewById(R.id.p8009_txt_price)).setText(item_price.substring(1));
                            if (item_price.startsWith("F")) {
                                ((EditText)SalesTicketAddItemFragment.this.getView().findViewById(R.id.p8009_txt_price)).setEnabled(false);
                            }
                        }
                    });
                } catch (Exception e)
                {
                }
            }
        }
    }
}
