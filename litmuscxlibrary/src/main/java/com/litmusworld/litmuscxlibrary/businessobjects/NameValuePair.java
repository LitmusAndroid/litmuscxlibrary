package com.litmusworld.litmuscxlibrary.businessobjects;

/**
 *
 * This class holds Name and Value pair
 *
 *  Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016

 */
public class NameValuePair {

    private String strName;
    private String strValue;

    public NameValuePair(String name, String value) {
        this.strName = name;
        this.strValue = value;
    }

    public String getName() {
        return strName;
    }

    public String getValue() {
        return strValue;
    }


}
