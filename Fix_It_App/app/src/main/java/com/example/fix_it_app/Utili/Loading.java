package com.example.fix_it_app.Utili;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.fix_it_app.R;

public class Loading {
    private Activity activity;

    public Loading(Activity activity) {
        this.activity = activity;
    }

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public void loadingAlertDialog(){
        if(dialog == null){
            builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.loading_dialog, null));
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
        }
    }

    public void dismissDialog(){
        dialog.dismiss();;
        resetDialog();
    }

    private void resetDialog() {
        dialog = null;
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
