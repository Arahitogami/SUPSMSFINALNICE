package com.ceria.amat.diclemente;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by CERIA Xavier on 04/02/2015.
 */
public class Contact {

            // Respect des  convention de nommage

    public Integer _ID;
    public String DNAME;
    public String EMAIL;
    public String PNUMBER;

    public Contact(Integer _ID, String DNAME, String PNUMBER, String EMAIL) {
        this._ID = _ID;
        //this.DNAME = DNAME;
        setDNAME(DNAME);
        this.PNUMBER = PNUMBER;
        this.EMAIL = EMAIL;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
      //  this.DNAME = DNAME;
        try {
            this.DNAME = Base64.encodeToString(DNAME.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            this.DNAME = DNAME;

            e.printStackTrace();
        }
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPNUMBER() {
        return PNUMBER;
    }

    public void setPNUMBER(String PNUMBER) {
        this.PNUMBER = PNUMBER;
    }
}
