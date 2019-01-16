package com.sff.sffapp.lovs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;

import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */
public class SpinnerDialog  implements ConnectionInterface{
    JSONObject json ;
    JSONObject json2 ;
    ArrayList<String> items;
    String items_obj;
    Activity context;
    String dTitle,closeTitle="Close";
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int pos;
    int style;
    SoapObject obj1;
    String IdOfLbl ;
    Context mcontext ;
    String lovName ;

    String page_no ;
    String json_in ;


    public SpinnerDialog(Activity activity,  String dialogTitle , String lovName ,String IdOfLbl ,Context mcontext , String page_no , String json_in) {

        json  = new JSONObject() ;
        try {

        json.put("lov_name" , lovName) ;

        json.put("page_no" , page_no) ;

            json2 = new JSONObject(json_in) ;

            Iterator<String> jsonArry = json2.keys() ;

            while(jsonArry.hasNext()) {
                String nameJson = (String)jsonArry.next();
                json.put(nameJson , json2.getString(nameJson)) ;
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        this.lovName = lovName ;
        this.page_no = page_no ;
        this.json_in = json_in ;

        this.IdOfLbl   = IdOfLbl ;
        this.context = activity;
        this.dTitle  = dialogTitle;
        this.mcontext= mcontext ;
        this.items = new ArrayList<String>() ;

        DBconnection dBconnection = new DBconnection(json.toString(),"android_get_lov",this,this.mcontext,"");

        dBconnection.execute();


    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }
    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle=closeTitle;
    }
    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }
    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle=closeTitle;
    }
    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }
    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        View v = context.getLayoutInflater().inflate(R.layout.dialog_layout, null);
        v.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        rippleViewClose.setTextDirection(View.TEXT_DIRECTION_RTL);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        title.setTextDirection(View.TEXT_DIRECTION_RTL);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        final ListView listView = (ListView) v.findViewById(R.id.list);
        listView.setTextDirection(View.TEXT_DIRECTION_RTL);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        searchBox.setTextDirection(View.TEXT_DIRECTION_RTL);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.items_view, items);
        listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;

        alertDialog.setCancelable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView) view.findViewById(R.id.text1);
                t.setTextDirection(View.TEXT_DIRECTION_RTL);
                for (int j = 0; j < items.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(items.get(j).toString())) {
                        pos = j;
                    }
                }
                if (obj1 != null)
                {
                    onSpinerItemClick.onClick(t.getText().toString(), pos,(SoapObject) obj1.getProperty(pos));
                }
                else
                {
                    onSpinerItemClick.onClick(t.getText().toString(), pos,null);
                }

                alertDialog.dismiss();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(searchBox.getText().toString());
            }
        });
        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {


        SoapObject obj2;
        obj1=obj;
        if (obj1 != null) {
            /*************************************************/
            for (int j = 0; j < obj.getPropertyCount() - 1; j++) {
                obj2 = (SoapObject) obj.getProperty(j);
                //this.items.add(obj2.getProperty("val2").toString());
                this.items.add(obj2.getPrimitiveProperty(this.IdOfLbl).toString());
           }
            this.showSpinerDialog();
        }
        else{
            Toast.makeText(mcontext, "No data found", Toast.LENGTH_SHORT).show();
        }






    }




}
