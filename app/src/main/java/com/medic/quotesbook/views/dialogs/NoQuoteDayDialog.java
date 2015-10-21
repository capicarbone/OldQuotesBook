package com.medic.quotesbook.views.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.medic.quotesbook.R;


/**
 * Created by capi on 20/10/15.
 */
public class NoQuoteDayDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.no_dayquote_available);

        dialogBuilder.setPositiveButton("Entiendo", null);

        return dialogBuilder.create();
    }
}
