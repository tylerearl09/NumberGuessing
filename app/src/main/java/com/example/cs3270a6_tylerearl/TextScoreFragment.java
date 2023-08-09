package com.example.cs3270a6_tylerearl;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class TextScoreFragment extends Fragment {

    View view;
    private EditText phoneNumber;

    private TextScore listener;
    String phone;

    int correctGuesses;

    public interface TextScore{
        void OpenText(String phone);

        void TextScoreFragmentDismissed();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TextScore){
            listener = (TextScore) context;
        }else{
            throw new ClassCastException(context + " Must implement PhoneNumberChanged");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_text_score, container, false);
        phoneNumber = view.findViewById(R.id.textPhoneNumber);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        (view.findViewById(R.id.btnSendText)).setOnClickListener(v -> {

            phone = String.valueOf(phoneNumber.getText());

            listener.OpenText(phone);

            listener.TextScoreFragmentDismissed();



        });
    }
}