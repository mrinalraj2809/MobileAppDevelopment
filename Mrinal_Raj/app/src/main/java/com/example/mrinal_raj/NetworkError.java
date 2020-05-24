package com.example.mrinal_raj;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkError extends Fragment {


    public RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle,errorMessage;
    private Button btnRetry;
    ProgressDialog loadingBar;

    public NetworkError() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network_error, container, false);
        errorImage = view.findViewById(R.id.errorImage);
        errorTitle = view.findViewById(R.id.errorTitle);
        errorMessage = view.findViewById(R.id.errorMessage);
        btnRetry = view.findViewById(R.id.btnretry);
        errorLayout = view.findViewById(R.id.error_layout);

        showErrorMessage(R.drawable.ic_error_black_24dp,"No Result","Please Check Your Internet Connection!!!");
        return view;
    }

    private void showErrorMessage(int imageView, String title, String message) {

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar = new ProgressDialog(getContext());
                loadingBar.onStart();
                if (isInternetAvailable())
                {
                    loadingBar.dismiss();
                    getActivity().onBackPressed();
                    errorLayout.setVisibility(View.INVISIBLE);
                }
                loadingBar.dismiss();
            }
        });
    }
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}

