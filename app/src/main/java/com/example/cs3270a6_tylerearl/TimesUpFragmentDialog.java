package com.example.cs3270a6_tylerearl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class TimesUpFragmentDialog extends DialogFragment {

    public TimesUpFragmentDialog(){}

    private TimesUpDialogDismissed listener;

    public interface TimesUpDialogDismissed{
        void TimesUpDialogClosed();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TimesUpDialogDismissed){
            listener = (TimesUpDialogDismissed) context;
        }else{
            throw new ClassCastException(context + " Must implement TimesUpDialogDismissed");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.TimesUpDialogClosed();
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Times Up!").setMessage("Try Again")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
