package com.sff.sffapp.SalesTicket;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


import com.sff.sffapp.database.ConnectionInterface;



/**
 * A simple {@link Fragment} subclass.
 */
public class SalesTicketListFragment extends Fragment implements View.OnClickListener , ConnectionInterface {

    ListView listView;
    ArrayList<SalesTicketAddItemCell> addItemArray;
    SalesTicketListAdapter addItemListAdapter;

    public SalesTicketListFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((TextView) ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Sales Ticket - Item   ");

        // ((SalesTicket)this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).requestFocus();


        // Inflate the layout for this fragment
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setOnClickListener(this);
        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setOnClickListener(this);

        View v = inflater.inflate(R.layout.fragment_sales_ticket_list_fragment, container, false);

        listView = v.findViewById(R.id.p8009_list_items);
        addItemArray = new ArrayList<SalesTicketAddItemCell>();


        addItemListAdapter = new SalesTicketListAdapter(this.getContext(), this.addItemArray);
        listView.setAdapter(addItemListAdapter);

        InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(this.getContext().INPUT_METHOD_SERVICE);


        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        SalesTicket f1 = new SalesTicket();
        FragmentManager fm1 = this.getActivity().getSupportFragmentManager();

        ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.VISIBLE);
        ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.VISIBLE);
        if ( ((SalesTicket) this.getActivity()).p8009_txt_ticket_type_desc_val.equals("003") )
        {
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (hidden) {

            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.VISIBLE);
            ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.VISIBLE);
            if ( ((SalesTicket) this.getActivity()).p8009_txt_ticket_type_desc_val.equals("003") )
            {
                ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.VISIBLE);
                ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
            }
            else {
                ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_next).setVisibility(View.INVISIBLE);
                ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
            }


        }
/*
hide keyboard long version
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(this.getActivity().INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this.getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
*/

        try {
            ((InputMethodManager) this.getActivity().getSystemService(this.getActivity().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public void onClick(View v) {


        if (v.getId() == R.id.footer_btn_add) {
            SalesTicketAddItemFragment f1 = new SalesTicketAddItemFragment();
            FragmentManager fm1 = this.getActivity().getSupportFragmentManager();
            fm1.beginTransaction().hide(fm1.findFragmentByTag("SalesTicketListFragment")).commit();
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            ftrans.add(R.id.FragmentContainer, f1, "SalesTicketAddItemFragment").commit();
        }
        else if (v.getId() == R.id.footer_btn_save) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            ((SalesTicket)SalesTicketListFragment.this.getActivity()).dbSave();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }



    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {

        SoapObject obj2;
        obj2 = (SoapObject) obj.getProperty(0);
        obj2.getProperty("val1").toString();
        Log.i("result", obj2.getProperty("val1").toString());
        Toast.makeText(this.getContext(), obj2.getProperty("val1").toString(), Toast.LENGTH_SHORT).show();


    }
}