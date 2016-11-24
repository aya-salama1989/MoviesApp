package com.mymovies.launchpad.moviesapp.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by LeGenD on 4/10/2015.
 */
public class Utilities extends Activity {


    /*----------------------------------------------
       ---------------------static mail Validation-----
       ---------------------------------------------- */
    public static boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public void makeSpinner(Context context, Spinner spinner, int dataResource){
        String[] dataArr  = context.getResources().getStringArray(dataResource);
        ArrayAdapter<String> kindArrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dataArr);
        kindArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(kindArrayAdapter);
    }

    public static boolean isPasswordMatching(Context context, String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);
        if (matcher.matches() == true) {
            //DO nothing
        } else {

            if (password.equals("")) {
                Logging.Toast(context, "Please enter passwords");

            } else {
                Logging.Toast(context, "Passwords do not match");

            }
        }

        return matcher.matches();
    }


/*    public static Boolean isFieldEmpty(EditText edtTxt, *//*TextInputLayout txtInput,*//* String errorMsg) {

        if (edtTxt.getText().toString().trim().isEmpty()) {
            txtInput.setError(errorMsg);
            // requestFocus(edtTxt);
            return false;
        } else {
            txtInput.setErrorEnabled(false);
        }

        return true;

    }*/

    public static void creatAlertDialogue(Context context, String Title, String Message, String ButtonText) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(Message);
        alertDialog.setButton(ButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();

    }


    public String loadJSONFromAsset(String path) {
        String json = null;
        Context context;
        try {

            InputStream is = getApplicationContext().getAssets().open(path);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
