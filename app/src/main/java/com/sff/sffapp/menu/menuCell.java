package com.sff.sffapp.menu;

public class menuCell {
    int PROG_NO;
    String title;
    String progType;
    int rootNo;
    String icon;





    public menuCell(int PROG_NO,String title,String progType,int rootNo,String icon)
    {
        this.title = title;
        this.icon = icon;
        this.PROG_NO = PROG_NO;
        this.progType = progType;
        this.rootNo = rootNo;
    }


    public String getTitle() {
        return this.title;
    }


    public String getIcon() {
        return this.icon;
    }

    public int getPROG_NO() {
        return this.PROG_NO;
    }

    public String getProgType() {
        return this.progType;
    }

    public int getRootNo() {
        return this.rootNo;
    }
}