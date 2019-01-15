package com.sff.sffapp.menu;
import org.ksoap2.serialization.SoapObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public   class userInfo {
    static private   String USER_NO ;
    static private   String USER_NAME_A ;
    static private   String TEAM_NO;
    static private   String CASHIER_CODE;
    static private   String BRA_NAME ;
    static private   String SALES_REGION ;
    static private   String BRANCH_TYPE_CODE;
    static private   String ACTIVITY_CODE ;
    static private   String USER_STORE ;
    static private   String USER_DEPT ;
    static private   String VERSION_NO ;
    static private   String DEPO_FLAG ;
    static private   String USER_LEVEL ;
    static private   String COST_ALLOWED ;
    static private   String EMP_NO ;
    static private   String STORE_NAME ;
    static private   String PREF_LANG ;
    static private   String IPHONE_USER_TYPE ;
    static private   String DefaultVatCode  ;
    static private   Double DefaultVatRate  ;



    public   userInfo (SoapObject obj, int index)
    {
        SoapObject detailObj = (SoapObject) obj.getProperty(index);

        USER_NO             = detailObj.getProperty("val1").toString();
        USER_NAME_A         = detailObj.getProperty("val2").toString();
        CASHIER_CODE        = detailObj.getProperty("val4").toString();
        BRA_NAME            = detailObj.getProperty("val5").toString();
        SALES_REGION        = detailObj.getProperty("val6").toString();
        ACTIVITY_CODE       = detailObj.getProperty("val7").toString();
        BRANCH_TYPE_CODE    = detailObj.getProperty("val8").toString();
        USER_STORE          = detailObj.getProperty("val9").toString();
        USER_DEPT           = detailObj.getProperty("val10").toString();
        VERSION_NO          = detailObj.getProperty("val11").toString();
        DEPO_FLAG           = detailObj.getProperty("val12").toString();
        USER_LEVEL          = detailObj.getProperty("val13").toString();
        COST_ALLOWED        = detailObj.getProperty("val14").toString();
        EMP_NO              = detailObj.getProperty("val15").toString();
        STORE_NAME          = detailObj.getProperty("val17").toString();
        PREF_LANG           = detailObj.getProperty("val18").toString();
        IPHONE_USER_TYPE    = detailObj.getProperty("val19").toString();
        TEAM_NO             = detailObj.getProperty("val20").toString();
        DefaultVatCode      = detailObj.getProperty("val21").toString();
        DefaultVatRate      = Double.parseDouble(detailObj.getProperty("val22").toString());
    }
    static public String getUSER_NO() {
        return USER_NO;
    }
    static public String getUSER_NAME_A() {
        return USER_NAME_A;
    }
    static public String getCASHIER_CODE() {
        return CASHIER_CODE;
    }
    static public String getBRA_NAME() {
        return BRA_NAME;
    }
    static public String getSALES_REGION() {
        return SALES_REGION;
    }
    static public String getBRANCH_TYPE_CODE() {
        return BRANCH_TYPE_CODE;
    }
    static public String getACTIVITY_CODE() {
        return ACTIVITY_CODE;
    }
    static public String getUSER_STORE() {
        return USER_STORE;
    }
    static public String getUSER_DEPT() {
        return USER_DEPT;
    }
    static public String getVERSION_NO() {
        return VERSION_NO;
    }
    static public String getDEPO_FLAG() {
        return DEPO_FLAG;
    }
    static public String getUSER_LEVEL() {
        return USER_LEVEL;
    }
    static public String getCOST_ALLOWED() {
        return COST_ALLOWED;
    }
    static public String getTEAM_NO() {
        return TEAM_NO;
    }
    static public String getEMP_NO() {
        return EMP_NO;
    }
    static public String getSTORE_NAME() {
        return STORE_NAME;
    }
    static public String getPREF_LANG() {
        return PREF_LANG;
    }
    static public String getIPHONE_USER_TYPE() {
        return IPHONE_USER_TYPE;
    }
    static public String getDefaultVatCode() {
        return DefaultVatCode;
    }
    public static Double getDefaultVatRate() {       return DefaultVatRate;  }



}