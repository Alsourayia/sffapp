package com.sff.sffapp.Payment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sff.sffapp.FollowUp.FollowUpListAdapter;
import com.sff.sffapp.R;
import com.sff.sffapp.SalesTicket.SalesTicket;
import com.sff.sffapp.FollowUp.FollowUp;
import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.lovs.OnSpinerItemClick;
import com.sff.sffapp.lovs.SpinnerDialog;
import com.sff.sffapp.menu.userInfo;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainPaymentFragment extends Fragment implements View.OnClickListener  , TextWatcher,ConnectionInterface {

    public  String baseClass ;
    TextView p8009_txt_payment_type ;
    String p8009_txt_payment_type_val ;
    String currSubFragment ;
    public Double maxVal  ;
    public JSONObject json;
    Context mContext ;
    EditText payment_fragment_txt_amount ;
    EditText payment_fragment_txt_app_amount ;
    String validatePayment_check;
    SpinnerDialog spinnerDialog ;
    ChqPaymentFragment chqPaymentFragment    ;
    TtPaymentFragment  ttPaymentFragment     ;
    CardPaymentFragment cardPaymentFragment  ;


    public MainPaymentFragment()
    {
        this.maxVal = 0.0 ;
        this.json = new JSONObject();
        this.p8009_txt_payment_type_val = "";
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
       {
                if (baseClass.equals("SalesTicket") )
                {
                    ((TextView) ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Paymnets");
                    mContext = ((SalesTicket) this.getActivity());
                }
                else if (baseClass.equals("FollowUpList") )
                {
                    ((TextView) ((FollowUp) this.getActivity()).myToolbar.findViewById(R.id.toolbar_txt_title)).setText("Paymnets");
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setOnClickListener(this);
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setOnClickListener(this);
                    mContext = ((FollowUp) this.getActivity());
                }
                View v =inflater.inflate(R.layout.fragment_payment_main, container, false);
                p8009_txt_payment_type = v.findViewById(R.id.payment_fragment_txt_payment_type) ;
                p8009_txt_payment_type.setOnClickListener(this);
                payment_fragment_txt_amount = v.findViewById(R.id.payment_fragment_txt_amount) ;
                payment_fragment_txt_amount.addTextChangedListener(this);
                payment_fragment_txt_app_amount = v.findViewById(R.id.payment_fragment_txt_app_amount) ;
                payment_fragment_txt_app_amount.addTextChangedListener(this);
                currSubFragment= "" ;


                chqPaymentFragment   = new ChqPaymentFragment();
                ttPaymentFragment    = new  TtPaymentFragment() ;
                cardPaymentFragment  = new  CardPaymentFragment() ;

                return  v ;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (baseClass.equals("SalesTicket") ) {
                ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
                ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.VISIBLE);
            }
            else if (baseClass.equals("FollowUpList") ) {
                ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
                ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onHiddenChanged(boolean hidden)
        {
            super.onHiddenChanged(hidden);
            if (baseClass.equals("SalesTicket") )
            {
                if (hidden) {
                    ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
                    ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.INVISIBLE);
                } else {
                    ((SalesTicket) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
                    ((SalesTicket) this.getActivity()).myToolbar.findViewById(R.id.toolbar_btn_prev).setVisibility(View.VISIBLE);
                }
            }
            else if (baseClass.equals("FollowUpList") )
            {
                if (hidden) {
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
                } else {
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.VISIBLE);
                    ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.VISIBLE);
                }
            }

        }


        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.footer_btn_save && baseClass.equals("FollowUpList")  )
            {
               if ( validatePayment().equals("T"))
               {
                   try {
                       this.json.put("PAYMENT_TYPE" ,p8009_txt_payment_type_val  ) ;
                       this.json.put("AMT" ,payment_fragment_txt_app_amount.getText().toString() ) ;
                       FragmentManager fm1 = this.getChildFragmentManager();
                       ChqPaymentFragment f1 = ((ChqPaymentFragment)fm1.findFragmentByTag("ChqPaymentFragment"));
                       if (f1 == null || !p8009_txt_payment_type_val.equals("02") ) {
                           this.json.put("CHQ_BANK_CODE" ,""  ) ;
                           this.json.put("CHQ_DATE" ,""  ) ;
                           this.json.put("CHQ_NO" ,""  ) ;
                       }
                       else {
                           this.json.put("CHQ_BANK_CODE" ,f1.payment_fragment_txt_chq_bank_code  ) ;
                           this.json.put("CHQ_DATE" ,f1.payment_fragment_txt_chq_date.getText().toString()  ) ;
                           this.json.put("CHQ_NO" ,f1.payment_fragment_txt_chq_no.getText().toString() ) ;
                       }
                       CardPaymentFragment f2 = ((CardPaymentFragment)fm1.findFragmentByTag("CardPaymentFragment"));
                       if (f2 == null || (!p8009_txt_payment_type_val.equals("03") && !p8009_txt_payment_type_val.equals("04"))) {
                           this.json.put("CARD_BANK_CODE" ,""  ) ;
                           this.json.put("CARD_NO" ,""  ) ;
                           this.json.put("CARD_TRANS_NO" ,""  ) ;
                       }
                       else {
                           this.json.put("CARD_BANK_CODE" ,f2.payment_fragment_txt_card_bank_code ) ;
                           this.json.put("CARD_NO" ,f2.payment_fragment_txt_card_no.getText().toString()  ) ;
                           this.json.put("CARD_TRANS_NO" ,f2.payment_fragment_txt_card_trans_no.getText().toString() ) ;
                       }
                       TtPaymentFragment f3 = ((TtPaymentFragment)fm1.findFragmentByTag("TtPaymentFragment"));
                       if (f3 == null || !p8009_txt_payment_type_val.equals("09")) {
                           this.json.put("TT_BANK_CODE" ,""  ) ;
                           this.json.put("TT_DATE" ,""  ) ;
                           this.json.put("TT_DEPO_BY_DESC" ,""  ) ;
                           this.json.put("TT_DEPO_BANK_CODE" ,""  ) ;
                       }
                       else {
                           this.json.put("TT_BANK_CODE" ,f3.payment_fragment_tt_bank_code ) ;
                           this.json.put("TT_DATE" ,f3.payment_fragment_tt_date.getText().toString()  ) ;
                           this.json.put("TT_DEPO_BY_DESC" ,f3.payment_fragment_txt_tt_deposit_by_desc.getText().toString()) ;
                           this.json.put("TT_DEPO_BANK_CODE" ,f3.payment_fragment_txt_tt_bank_deposit_code ) ;
                       }
                       /********************/
                       this.json.put("ACTION_TYPE" ,"ADDPAYMENT" ) ;
                       DBconnection dBconnection = new DBconnection(json.toString() , "TICKET_ACTIONS" ,this , mContext , "ADDPAYMENT"  ) ;
                       dBconnection.execute();

                   }catch (Exception e)
                   {
                       Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
               else if ( validatePayment().equals("F"))
               {
                   //Toast.makeText(mContext, validatePayment(), Toast.LENGTH_LONG).show();
               }
            }

        if (v.getId() == R.id.footer_btn_cancel && baseClass.equals("FollowUpList")  )
        {
            FragmentManager fm1 = ((FollowUp) this.getActivity()).getSupportFragmentManager() ;
            FragmentTransaction ftrans = fm1.beginTransaction();
            ftrans.addToBackStack(null);
            fm1.beginTransaction().remove(fm1.findFragmentByTag("MainPaymentFragment")).commit();
            fm1.beginTransaction().show(fm1.findFragmentByTag("FollowUpList")).commit();
        }

        else if ( v.getId()== R.id.payment_fragment_txt_payment_type)
        {
            spinnerDialog = new SpinnerDialog(this.getActivity(), "Select or Search Item", "PAYTYPE", "val2", this.getContext(), "8009","");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position, SoapObject obj) {
                    p8009_txt_payment_type.setText(item);
                    p8009_txt_payment_type_val = obj.getProperty("val1").toString();

                    FragmentManager fm1 = MainPaymentFragment.this.getChildFragmentManager();
                    FragmentTransaction ftrans = fm1.beginTransaction();
                    ftrans.addToBackStack(null);

                    if (!currSubFragment.equals("") ) {
                        Toast.makeText(mContext, currSubFragment, Toast.LENGTH_SHORT).show();
                        fm1.beginTransaction().remove(fm1.findFragmentByTag(currSubFragment)).commit();
                        currSubFragment= "" ;
                    }
                    ftrans.addToBackStack(null);

                    if (p8009_txt_payment_type_val.equals("02")) // chq
                    {
                        ftrans.add(R.id.PaymentChildFragmentContainer, chqPaymentFragment, "ChqPaymentFragment").commit();
                        currSubFragment = "ChqPaymentFragment" ;

                    }
                    else if (p8009_txt_payment_type_val.equals("03") || p8009_txt_payment_type_val.equals("04") )
                    {
                        ftrans.add(R.id.PaymentChildFragmentContainer, cardPaymentFragment, "CardPaymentFragment").commit();
                        currSubFragment = "CardPaymentFragment" ;
                    }
                    else if (p8009_txt_payment_type_val.equals("09") )
                    {
                        ftrans.add(R.id.PaymentChildFragmentContainer, ttPaymentFragment, "FragmentTtPayment").commit();
                        currSubFragment = "FragmentTtPayment" ;
                    }
                    else if (p8009_txt_payment_type_val.equals("01"))
                    {
                        currSubFragment = "" ;
                    }
                }
            });
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }
    @Override
    public void afterTextChanged(Editable s)
    {

    }

    public String validatePayment() {
        if (TextUtils.isEmpty(payment_fragment_txt_app_amount.getText()) ||  TextUtils.isEmpty(payment_fragment_txt_amount.getText() ))
        {
            Toast.makeText(mContext, "يجب ادخال المبلغ!  ", Toast.LENGTH_SHORT).show();
            return "F" ;
        }
        else
        {
            if (Double.parseDouble(payment_fragment_txt_app_amount.getText().toString()) >  maxVal )
            {//(! payment_fragment_txt_amount.getText().toString().equals(payment_fragment_txt_app_amount.getText().toString())){
                Toast.makeText(mContext, "المبلغ المؤكد يتجاوز المتبقي  ", Toast.LENGTH_SHORT).show();
                return "F" ;
            }
            else if (Double.parseDouble(payment_fragment_txt_amount.getText().toString()) != Double.parseDouble(payment_fragment_txt_app_amount.getText().toString()))
            {//(! payment_fragment_txt_amount.getText().toString().equals(payment_fragment_txt_app_amount.getText().toString())){
                Toast.makeText(mContext, "المبلغ و تأكيد المبلغ غير متساوي   ", Toast.LENGTH_SHORT).show();
                return "F" ;
            }
        }
        if ( TextUtils.isEmpty(p8009_txt_payment_type_val))
        {
            Toast.makeText(mContext, "يجب اختيار نوع الدفعة  ", Toast.LENGTH_SHORT).show();
            return "F" ;
        }
        else if ( p8009_txt_payment_type_val.equals("01"))
        {
            //nothing
        }
        else if ( p8009_txt_payment_type_val.equals("02"))//chq
        {
            FragmentManager fm1 = this.getChildFragmentManager();
            ChqPaymentFragment f1 = ((ChqPaymentFragment)fm1.findFragmentByTag("ChqPaymentFragment"));
            if (TextUtils.isEmpty(f1.payment_fragment_txt_chq_bank_desc.getText()) )
            {
                Toast.makeText(mContext, "     الرجاء اختيار البنك    ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_chq_bank_desc.requestFocus() ;
                return "F" ;
            }
            else if (TextUtils.isEmpty(f1.payment_fragment_txt_chq_date.getText())  )
            {
                Toast.makeText(mContext, "  الرجاء ادخال التاريخ   ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_chq_date.requestFocus() ;
                return "F" ;
            }
            else if  ( TextUtils.isEmpty(f1.payment_fragment_txt_chq_no.getText()) )
            //(f1.payment_fragment_txt_card_no_p.toString().isEmpty()    )
            {
                Toast.makeText(mContext, "       الرجاء ادخال رقم الشيك     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_chq_no.requestFocus() ;
                return "F" ;
            }

        }
        else if ( p8009_txt_payment_type_val.equals("03") || p8009_txt_payment_type_val.equals("04")  )//cards
        {
            FragmentManager fm1 = this.getChildFragmentManager();
            CardPaymentFragment f1 = ((CardPaymentFragment)fm1.findFragmentByTag("CardPaymentFragment"));

            if (TextUtils.isEmpty(f1.payment_fragment_txt_card_bank_code) )
            {
                Toast.makeText(mContext, "     الرجاء اختيار بنك البطاقة    ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_card_bank_desc.requestFocus() ;
                return "F" ;
            }

            else if  ( TextUtils.isEmpty(f1.payment_fragment_txt_card_no.getText()))
                    //(f1.payment_fragment_txt_card_no_p.toString().isEmpty()    )
            {
                Toast.makeText(mContext, "       الرجاء ادخال رقم البطاقة     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_card_no.requestFocus() ;
                return "F" ;
            }
            else if  ( TextUtils.isEmpty(f1.payment_fragment_txt_card_trans_no.getText()))
            {
                Toast.makeText(mContext, "       الرجاء ادخال رقم العملية     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_card_trans_no.requestFocus() ;
                return "F" ;
            }

        }

        else if (  p8009_txt_payment_type_val.equals("09")  )//T.T
        {
            FragmentManager fm1 = this.getChildFragmentManager();
            TtPaymentFragment f1 = ((TtPaymentFragment)fm1.findFragmentByTag("FragmentTtPayment"));
            if (TextUtils.isEmpty(f1.payment_fragment_tt_bank_desc.getText()))
            {
                Toast.makeText(mContext, "     الرجاء اختيار البنك المحولة منه    ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_tt_bank_desc.requestFocus() ;
                return "F" ;
            }

            else if  ( TextUtils.isEmpty(f1.payment_fragment_tt_date.getText()))
            //(f1.payment_fragment_txt_card_no_p.toString().isEmpty()    )
            {
                Toast.makeText(mContext, "       الرجاء ادخال تاريخ الحوالة     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_tt_date.requestFocus() ;
                return "F" ;
            }
            else if  ( TextUtils.isEmpty(f1.payment_fragment_txt_tt_deposit_by_desc.getText()))
            //(f1.payment_fragment_txt_card_no_p.toString().isEmpty()    )
            {
                Toast.makeText(mContext, "       الرجاء ادخال المودع     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_tt_deposit_by_desc.requestFocus() ;
                return "F" ;
            }
            else if  ( TextUtils.isEmpty(f1.payment_fragment_txt_tt_bank_deposit_desc.getText()))
            //(f1.payment_fragment_txt_card_no_p.toString().isEmpty()    )
            {
                Toast.makeText(mContext, "       الرجاء ادخال البنك المحولة اليه     ", Toast.LENGTH_SHORT).show();
                f1.payment_fragment_txt_tt_bank_deposit_desc.requestFocus() ;
                return "F" ;
            }


        }
        return "T" ;

    }


    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName, String method_id) {
        if (baseClass.equals("FollowUpList")){
            SoapObject obj1;
            SoapObject obj2;
            obj1=obj;
            String val1_JSON_OUT = null ,val2_JSON_OUT=null;
            if (method_id== "ADDPAYMENT" ) {
                if (obj1 != null)
                {
                    obj2 = (SoapObject) obj.getProperty(0);
                    val1_JSON_OUT = obj2.getPrimitiveProperty("val1").toString();
                    val2_JSON_OUT = obj2.getPrimitiveProperty("val2").toString();
                    Toast.makeText(mContext, val2_JSON_OUT, Toast.LENGTH_SHORT).show();
                    if (val1_JSON_OUT.equals("T")){
                        FragmentManager fm1 = ((FollowUp) this.getActivity()).getSupportFragmentManager() ;
                        FragmentTransaction ftrans = fm1.beginTransaction();
                        ftrans.addToBackStack(null);
                        fm1.beginTransaction().remove(fm1.findFragmentByTag("MainPaymentFragment")).commit();
                        fm1.beginTransaction().show(fm1.findFragmentByTag("FollowUpList")).commit();
                    }
                }
                else
                {
                    Toast.makeText(mContext, "No data found-ADDPAYMENT", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }
}
