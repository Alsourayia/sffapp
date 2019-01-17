package com.sff.sffapp.database;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
public class DBconnection extends AsyncTask<Void, Void, Void> {
    String         methodName;
    Context        app ;
    String         jason;
    ProgressDialog pdialog;
    SoapObject     returngetResponseobj;
    String method_id ;
   public ConnectionInterface delegateConnection = null ;
   public DBconnection(String jason ,String methodName , ConnectionInterface connectionInterface ,Context app_p , String method_id   )
    {
        this.jason              = jason;
        this.delegateConnection = connectionInterface ;
        this.app                = app_p ;
        this.methodName         = methodName;
        this.method_id = method_id ;
    }
    public DBconnection(String jason ,String methodName , Context app_p  )
    {
        this.jason              = jason ;
        this.delegateConnection = (ConnectionInterface)app_p ;
        this.app                = app_p ;
        this.methodName         = methodName ;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String SOAP_ACTION = "http://tempuri.org/"+ this.methodName;
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://172.16.0.50/erp/erp.asmx" ;
        SoapObject Request = new SoapObject(NAMESPACE, this.methodName);
        if ( jason.isEmpty() )
        {
        }
       else
        {
            Request.addProperty("JSON_IN",this.jason);
        }
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(Request);
        HttpTransportSE transport = new HttpTransportSE(URL);
        try
        {
            transport.call(SOAP_ACTION, soapEnvelope);
            returngetResponseobj = (SoapObject) soapEnvelope.getResponse();
        }
        catch (Exception ex)
        {
            ex.getMessage() ;
            delegateConnection.getResult( null , "ERROR" , "error in Do Background",this.methodName , this.method_id);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        try
        {
            super.onPostExecute(aVoid);
            pdialog.dismiss();
            delegateConnection.getResult(returngetResponseobj , "OK" , "OK",this.methodName , this.method_id );
        }   catch (Exception ex)
        {
            ex.getMessage();
            delegateConnection.getResult( null , "ERROR" , "error in onPostExecute",this.methodName , this.method_id  );
        }
    }
    @Override
    protected void onPreExecute() {
        try
        {
            super.onPreExecute();
            pdialog = new ProgressDialog( app);
            if (this.methodName == "android_login ")
            {
                pdialog.setTitle("Check Username and Password!");
            }
            else
            {
                pdialog.setTitle("Fetching Data!");
            }
            pdialog.setDismissMessage(null);
            pdialog.setMessage("Connecting...");
            pdialog.show();
            pdialog.setCancelable(false);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            delegateConnection.getResult( null , "ERROR" , "error in onPreExecute",this.methodName , this.method_id  );
        }
    }
}
