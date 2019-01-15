package com.sff.sffapp.Payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.R;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;

import org.ksoap2.serialization.SoapObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardPaymentFragment extends Fragment implements View.OnClickListener {
    SpinnerDialog spinnerDialog ;
    TextView payment_fragment_txt_card_bank_desc  ;
    public String payment_fragment_txt_card_bank_code ;
    public EditText payment_fragment_txt_card_no ;
    EditText payment_fragment_txt_card_trans_no;



    public CardPaymentFragment() {
        // Required empty public constructor
        payment_fragment_txt_card_bank_code = "" ;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment_card, container, false);
        payment_fragment_txt_card_bank_desc = v.findViewById(R.id.payment_fragment_txt_card_bank_desc) ;
        payment_fragment_txt_card_bank_desc.setOnClickListener(this);
        payment_fragment_txt_card_no = v.findViewById(R.id.payment_fragment_txt_card_no) ;
        payment_fragment_txt_card_trans_no = v.findViewById(R.id.payment_fragment_txt_card_trans_no) ;

        return v;

    }

    @Override
    public void onClick(View v) {
        spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Item", "CARDBANK", "val2", this.getContext(), "9999","");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position, SoapObject obj) {
                payment_fragment_txt_card_bank_desc.setText(item);
                payment_fragment_txt_card_bank_code = obj.getProperty("val1").toString();


            }
        });
    }
}
