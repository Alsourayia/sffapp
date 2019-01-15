package com.sff.sffapp.database;

import org.ksoap2.serialization.SoapObject;



public interface ConnectionInterface {
    public  void  getResult (SoapObject obj , String status , String status_msg ,String methodName , String method_id    );

}
