package com.sff.sffapp.FollowUp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.sff.sffapp.R;
import com.sff.sffapp.database.ConnectionInterface;
import com.sff.sffapp.database.DBconnection;
import com.sff.sffapp.menu.userInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowUpList extends Fragment implements ConnectionInterface,SwipeRefreshLayout.OnRefreshListener {
    ListView listView ;

    SwipeRefreshLayout Ref_obj ;
    JSONObject json ;
    ArrayList<FollowUpListCell> orginalArrayList ;
    public FollowUpList()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_ticket_follow_up_list, container, false);
        Ref_obj = new SwipeRefreshLayout(this.getContext());
        Ref_obj = v.findViewById(R.id.swiperefresh) ;
        Ref_obj.setOnRefreshListener( this) ;
        listView = v.findViewById(R.id.p8002_ticket_follow_up_list);
        orginalArrayList = new ArrayList<FollowUpListCell>();
        json  = new JSONObject() ;
        try
        {
            json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
        }   catch (JSONException e) {
            e.printStackTrace();
        }
        DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this, this.getContext() , "");
        dBconnection.execute();

        return v;
    }
    @Override
    public void onHiddenChanged(boolean hidden)
    {
            super.onHiddenChanged(hidden);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_save).setVisibility(View.INVISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_cancel).setVisibility(View.INVISIBLE);
            ((FollowUp) this.getActivity()).myFooterToolbar.findViewById(R.id.footer_btn_add).setVisibility(View.INVISIBLE);
            if (!hidden)
            onRefresh();
    }
    @Override
    public void onRefresh() {
        orginalArrayList = new ArrayList<FollowUpListCell>();
        json  = new JSONObject() ;
        try
        {
            json.put("USER_EMP_NO" , userInfo.getEMP_NO() ) ;
        }   catch (JSONException e) {
            e.printStackTrace();
        }
        DBconnection dBconnection = new DBconnection(json.toString(),"GET_TICKETS",this, this.getContext() , "");
        dBconnection.execute();
        Ref_obj.setRefreshing(false);

    }
    @Override
    public void getResult(SoapObject obj, String status, String status_msg, String methodName  , String method_id ) {
        SoapObject obj2;
        orginalArrayList = new ArrayList<FollowUpListCell>();
        /*************************************************/
        for ( int j=0 ; j < obj.getPropertyCount() - 1 ; j++ )
        {
            obj2 = (SoapObject) obj.getProperty(j);
            FollowUpListCell cell = new FollowUpListCell( );
            cell.ticketNo_desc = obj2.getPrimitiveProperty("val1").toString();
            cell.custName_desc = obj2.getPrimitiveProperty("val2").toString();
            cell.ticketDate_desc = obj2.getPrimitiveProperty("val3").toString();
            cell.address_desc =   obj2.getPrimitiveProperty("val4").toString(); //obj2.getProperty("val4")==    ? " " : obj2.getProperty("val4").toString();
            cell.mobileNo_desc = obj2.getPrimitiveProperty("val5").toString();
            cell.rem_val_desc = obj2.getPrimitiveProperty("val6").toString();
            cell.assign_ccs_code = obj2.getPrimitiveProperty("val7").toString();
            cell.measur_assign_ccs_team_code = obj2.getPrimitiveProperty("val8").toString();
            cell.measur_assign_ccs_team_desc = obj2.getPrimitiveProperty("val9").toString();
            cell.measurement_appt = obj2.getPrimitiveProperty("val10").toString();
            cell.install_assign_ccs_team_code = obj2.getPrimitiveProperty("val11").toString();
            cell.install_assign_ccs_team_desc = obj2.getPrimitiveProperty("val12").toString();
            cell.install_assign_date_appt = obj2.getPrimitiveProperty("val13").toString();
            cell.isnew_flag = obj2.getPrimitiveProperty("val14").toString();
            cell.ismeasurementdone = obj2.getPrimitiveProperty("val15").toString();
            orginalArrayList.add(cell);
        }
        FollowUpListAdapter TicketFollowUpListAd = new FollowUpListAdapter(this.getContext(), orginalArrayList);
        listView.setAdapter(TicketFollowUpListAd);
    }



}
