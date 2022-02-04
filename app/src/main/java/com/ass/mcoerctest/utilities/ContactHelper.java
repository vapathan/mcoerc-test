package com.ass.mcoerctest.utilities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

public class ContactHelper {

    public static void openContactToSave(Context context, String personName, String phoneNumber, String email) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, personName);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        // intent.putExtra(ContactsContract.Intents.Insert.EMAIL, person.email);

        context.startActivity(intent);
    }

    public static boolean isContactExists(Context context, String phoneNumber) {
        /// number is the phone number

        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }

        return false;
    }

    public static String addCountryCode(String phoneNumber){
       phoneNumber = phoneNumber.trim();
       if(TextUtils.isDigitsOnly(phoneNumber)){
           phoneNumber = "+91 " + phoneNumber;
       }
       return  phoneNumber;
    }
}
