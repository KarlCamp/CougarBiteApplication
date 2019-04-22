package com.capstone.kcamp.cougarbiteapplication.Common;

import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;

public class Common {
    public static AppUser currentUser;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Update";
    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On My Way";
        else return "Shipped";
    }
}
