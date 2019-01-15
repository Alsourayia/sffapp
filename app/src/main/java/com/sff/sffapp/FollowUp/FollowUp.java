package com.sff.sffapp.FollowUp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sff.sffapp.R;
import com.sff.sffapp.SalesTicket.SalesTicket;
import com.sff.sffapp.SalesTicket.SalesTicketAddItemCell;
import com.sff.sffapp.SalesTicket.SalesTicketListFragment;
import com.sff.sffapp.SalesTicket.SalesTicketStartFragment;
import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class FollowUp extends AppCompatActivity implements ConnectionInterface, View.OnClickListener
{

    private FrameLayout frameLayoutfram1 ;
    String currFragment ;
    public    Toolbar myToolbar;
    public Toolbar myFooterToolbar ;

    TextView rem_val_desc ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_follow_up);


        myToolbar = (Toolbar) findViewById(R.id.sff_toolbar);
        setSupportActionBar(myToolbar);

        myFooterToolbar = (Toolbar) findViewById(R.id.sff_footerBar);
        setSupportActionBar(myToolbar);

        myToolbar.findViewById(R.id.toolbar_txt_title).setVisibility(View.VISIBLE);

        ((TextView)myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Tickets Follow Up");

        //myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.VISIBLE);
        //myToolbar.findViewById(R.id.toolbar_btn_next).setOnClickListener(this);
        myToolbar.findViewById(R.id.toolbar_btn_prev).setOnClickListener(this);

        frameLayoutfram1  = (FrameLayout)findViewById(R.id.TicketFollowUpWContainer);


        FollowUpList f1 = new FollowUpList();
        FragmentManager fm1 = getSupportFragmentManager();
        FragmentTransaction ftrans = fm1.beginTransaction();
        ftrans.addToBackStack(null);
        ftrans.add(R.id.TicketFollowUpWContainer,f1,"FollowUpList").commit();
        currFragment = "FollowUpList" ;

       // rem_val_desc = findViewById(R.id.p8002_txt_rem_val_desc);
     //   rem_val_desc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        /*if ( v.getId()== R.id.p8002_txt_rem_val_desc )
        {
            //|| v.getId() == R.id.p8002_btn_rem_val
            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().hide(fm1.findFragmentByTag(currFragment)).commit();

            MainPaymentFragment f2 = new MainPaymentFragment();
            f2.baseClass = "FollowUp" ;
            ftrans.add(R.id.FragmentContainer, f2, "MainPaymentFragment").commit();
            currFragment = "MainPaymentFragment";

        }*/
    }

    public void dbSave()
    {}

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {

    }
}
