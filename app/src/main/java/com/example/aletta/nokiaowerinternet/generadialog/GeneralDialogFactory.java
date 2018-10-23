package com.example.aletta.nokiaowerinternet.generadialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.aletta.nokiaowerinternet.R;

public class GeneralDialogFactory {

    public static Dialog createGeneralDialog(Context context, String message, final DialogPositiveListener dialogPositiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogPositiveListener != null) {
                            dialogPositiveListener.onPositiveResponse();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        if (dialogPositiveListener != null) {
                            dialogPositiveListener.onNegativeResponse();
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface DialogPositiveListener {

        void onPositiveResponse();

        void onNegativeResponse();

    }

}
