package com.jimpitan.hangga.jimpitan.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sayekti on 7/12/18.
 */

public class Utils {
    public static String SpreedsheetId = "1mgHj9W2WEpubMamySgVrDukhJ65ZHQN1fJS9uy7NJrw";

    public static String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1) {
                return parts[0];
            }
        }
        return null;
    }

    public static String getMyPhoneNO(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }

    public static String getWAUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.whatsapp");
        List<String> possiblePhone = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possiblePhone.add(account.name);
            possiblePhone.add(account.type);
        }

        if (!possiblePhone.isEmpty() && possiblePhone.get(0) != null) {
            String email = possiblePhone.get(1);
            return email;
            /*String[] parts = email.split("628");

            if (parts.length > 1)
                return parts[0];*/
        }
        return null;
    }

    public static String rupiah(String s){
        Locale local = new Locale("id", "id");
        String replaceable = String.format("[Rp,.\\s]",
                NumberFormat.getCurrencyInstance().getCurrency()
                        .getSymbol(local));
        String cleanString = s.toString().replaceAll(replaceable,
                "");

        double parsed;
        try {
            parsed = Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            parsed = 0.00;
        }

        NumberFormat formatter = NumberFormat
                .getCurrencyInstance(local);
        formatter.setMaximumFractionDigits(0);
        formatter.setParseIntegerOnly(true);
        String formatted = formatter.format((parsed));

        String replace = String.format("[Rp\\s]",
                NumberFormat.getCurrencyInstance().getCurrency()
                        .getSymbol(local));
        String clean = formatted.replaceAll(replace, "");
        return clean;
    }
}
