package com.sff.sffapp.lovs;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public interface OnSpinerItemClick
{
    public void onClick(String item, int position, SoapObject obj);
}
