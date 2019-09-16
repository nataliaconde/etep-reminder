package com.etep.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignUpTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.sign_up_tab, container, false);
        Button btnLogin = (Button) view.findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        getActivity(),
                        "AQ",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        return view;
    }


    public void goToActivity(Class<?> view) {
        Intent i = new Intent(getActivity(), view);
        startActivity(i);
    }

}
