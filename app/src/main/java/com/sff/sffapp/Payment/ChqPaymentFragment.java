package com.sff.sffapp.Payment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 */
public class ChqPaymentFragment extends Fragment implements View.OnClickListener ,  DatePickerDialog.OnDateSetListener{
    SpinnerDialog spinnerDialog ;
    TextView payment_fragment_txt_chq_bank_desc  ;
    public String payment_fragment_txt_chq_bank_code ;
    public TextView payment_fragment_txt_chq_date ;
    public EditText payment_fragment_txt_chq_no;
    final Calendar calendar= Calendar.getInstance();
    public ChqPaymentFragment()
    {
        payment_fragment_txt_chq_bank_code = "";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_chq, container, false);
        payment_fragment_txt_chq_bank_desc = v.findViewById(R.id.payment_fragment_txt_chq_bank_desc) ;
        payment_fragment_txt_chq_bank_desc.setOnClickListener(this);
        payment_fragment_txt_chq_date = v.findViewById(R.id.payment_fragment_txt_chq_date) ;
        payment_fragment_txt_chq_date.setOnClickListener(this);
        payment_fragment_txt_chq_no = v.findViewById(R.id.payment_fragment_txt_chq_no) ;
        return v;
    }
    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.payment_fragment_txt_chq_bank_desc)
        {
        spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Item", "CHQBANK", "val2", this.getContext(), "9999","");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position, SoapObject obj) {
                payment_fragment_txt_chq_bank_desc.setText(item);
                payment_fragment_txt_chq_bank_code = obj.getProperty("val1").toString();

            }
        });
        }
        else if (v.getId()== R.id.payment_fragment_txt_chq_date) {
            DatePickerDialog dpd = new DatePickerDialog(this.getContext(), R.style.datepickerstyle ,this ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setId(payment_fragment_txt_chq_date.getId());
            dpd.getDatePicker().setMinDate(System.currentTimeMillis()  );
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000*60*60*24*10);
            dpd.show();
        }
    }
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          payment_fragment_txt_chq_date.setText(dayOfMonth+ "-" + (month+1) +"-" + year  );
    }

}
