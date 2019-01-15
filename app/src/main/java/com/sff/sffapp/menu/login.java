package com.sff.sffapp.menu;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class login extends AppCompatActivity implements ConnectionInterface {


    EditText txtinp_username ;
    EditText txtinp_password ;
    Button btn_login ;
    Button btn2 ;
    Button zft ;
    static   ArrayList<menuCell> menuArrayList ;
    //ArrayList<menuCell> arrayList ;
    static com.sff.sffapp.menu.userInfo userInfo;
    Intent listviewintent  ;

    JSONObject json ;

    @Override
    public void onBackPressed()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtinp_username = (EditText)findViewById(R.id.txtinp_username);
        txtinp_password = (EditText)findViewById(R.id.txtinp_password);
        btn_login       = (Button)  findViewById(R.id.btn_login);
        txtinp_username.setText("xxt");
        txtinp_password.setText("0");
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                json  = new JSONObject() ;

                try
                {
                    json.put("USERNAME" , txtinp_username.getText().toString() ) ;
                    json.put("PASSWORD" , txtinp_password.getText().toString() ) ;

                } catch (JSONException e)

                {
                    e.printStackTrace();
                }

                DBconnection dBconnection = new DBconnection(json.toString(),"android_login",  login.this,login.this , "" );
                dBconnection.execute();

            }
        }
        );

    }

    @Override
    public void getResult (SoapObject obj , String status , String status_msg ,String methodName , String method_id   )
    {
        SoapObject obj2  ;

         if (methodName == "android_login" )
         {
            SoapObject detailObj = (SoapObject) obj.getProperty(0);
            if (detailObj.getProperty("val1").toString().equals("F") )
            {
                Log.i("msg", "getResult: "+detailObj.getProperty("val2").toString());
            }
            else
            {
                userInfo = new userInfo(obj, 0);
                json = null;
                json = new JSONObject();
                try {
                    json.put("USERNNO", userInfo.getUSER_NO());
                    json.put("ROOT_NO", "0");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                menuArrayList = new ArrayList<menuCell>();
                for (int i = 1; i< obj.getPropertyCount() -1 ; i++)
                {
                    obj2 = (SoapObject) obj.getProperty(i) ;
                    //int PROG_NO,String title,String progType,int rootNo,int icon)
                    menuCell cell = new menuCell(
                            Integer.parseInt(obj2.getProperty("val1").toString()),
                            obj2.getProperty("val2").toString(),
                            obj2.getProperty("val3").toString(),
                            Integer.parseInt(obj2.getProperty("val4").toString()),
                            obj2.getProperty("val5").toString()
                            );
                    menuArrayList.add(cell);
                    //menuCell cell = new menuCell(obj2.getProperty("val2").toString(),Integer.parseInt(obj2.getProperty("val3").toString()) );
                }
                listviewintent = new Intent( getApplicationContext(), menu.class);
                //listviewintent.putExtra("myarr" , arrayList) ;

                 startActivity(listviewintent);
            }
         }
    }
}

