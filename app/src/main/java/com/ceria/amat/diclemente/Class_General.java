package com.ceria.amat.diclemente;

import android.app.Application;
/**
 * Created by CERIA Xavier on 04/02/2015.
 */
public class Class_General extends Application {

    private static Class_General mInstance= null;
    public String userLogin;
    public String userPassword;

    public static synchronized Class_General getInstance(){
        if(null == mInstance){
            mInstance = new Class_General();
        }
        return mInstance;
    }


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
