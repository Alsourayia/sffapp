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

import com.sff.sffapp.R;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TtPaymentFragment extends Fragment implements View.OnClickListener ,  DatePickerDialog.OnDateSetListener{
    SpinnerDialog spinnerDialog ;
    TextView payment_fragment_tt_bank_desc  ;
    String payment_fragment_tt_bank_code ;
    TextView payment_fragment_tt_date ;
    TextView payment_fragment_txt_tt_bank_deposit_desc;
    String payment_fragment_txt_tt_bank_deposit_code ;
    EditText payment_fragment_txt_tt_deposit_by_desc ;

    final Calendar calendar= Calendar.getInstance();


    public TtPaymentFragment() {
        // Required empty public constructor
        payment_fragment_tt_bank_code = "" ;
        payment_fragment_txt_tt_bank_deposit_code = "" ;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment_tt, container, false);

        payment_fragment_tt_bank_desc = v.findViewById(R.id.payment_fragment_tt_bank_desc) ;
        payment_fragment_tt_bank_desc.setOnClickListener(this);

        payment_fragment_tt_date = v.findViewById(R.id.payment_fragment_tt_date) ;
        payment_fragment_tt_date.setOnClickListener(this);

        payment_fragment_txt_tt_bank_deposit_desc = v.findViewById(R.id.payment_fragment_txt_tt_bank_deposit_desc) ;
        payment_fragment_txt_tt_bank_deposit_desc.setOnClickListener(this);


        payment_fragment_txt_tt_deposit_by_desc = v.findViewById(R.id.payment_fragment_txt_tt_deposit_by_desc) ;



        return v;

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.payment_fragment_tt_bank_desc) {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Item", "TTBANK", "val2", this.getContext(), "9999","");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    payment_fragment_tt_bank_desc.setText(item);
                    payment_fragment_tt_bank_code = obj.getProperty("val1").toString();

                }
            });
        }
        else if (v.getId()== R.id.payment_fragment_tt_date) {
            DatePickerDialog dpd = new DatePickerDialog(this.getContext(), R.style.datepickerstyle ,this ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setId(payment_fragment_tt_date.getId());
            dpd.getDatePicker().setMinDate(System.currentTimeMillis()  );
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000*60*60*24*10);
            dpd.show();
        }
        else  if (v.getId()== R.id.payment_fragment_txt_tt_bank_deposit_desc) {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Item", "TTDEPOBANK", "val2", this.getContext(), "9999","");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    payment_fragment_txt_tt_bank_deposit_desc.setText(item);
                    payment_fragment_txt_tt_bank_deposit_code = obj.getProperty("val1").toString();

                }
            });
        }


    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        payment_fragment_tt_date.setText(dayOfMonth+ "-" + (month+1) +"-" + year  );

    }

}
