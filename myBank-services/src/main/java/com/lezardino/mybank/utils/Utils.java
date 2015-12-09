package com.lezardino.mybank.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Utils {

    public static String bigDecimalToString(BigDecimal bigDecimal){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        return decimalFormat.format(bigDecimal);
    }
}
