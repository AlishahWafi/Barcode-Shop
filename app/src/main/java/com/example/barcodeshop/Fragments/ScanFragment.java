package com.example.barcodeshop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodeshop.ProductDetail;
import com.example.barcodeshop.R;
import com.example.barcodeshop.Session;
import com.example.barcodeshop.camera;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class ScanFragment extends Fragment {

    TextView txtWelcome;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        txtWelcome = (TextView) view.findViewById(R.id.txtHomeWelcome);
        Session session = new Session(getActivity());
        txtWelcome.setText("Welcome "+session.getUserName());

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),camera.class);
                startActivityForResult(intent,0);
                //startActivity(intent);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if(requestCode == 0)
        {
            if(resultCode == CommonStatusCodes.SUCCESS)
            {
                if(data!=null)
                {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    String number = barcode.displayValue;

                    if(number!=null) {

                        /*ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                        fragmentTransaction.replace(R.id.scanFrame, productDetailFragment);
                        fragmentTransaction.addToBackStack(null);*/



                        Intent intent = new Intent(getActivity(), ProductDetail.class);
                        intent.putExtra("code",number);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(),"No Barcode Found",Toast.LENGTH_SHORT).show();
                    }

                    //fragmentTransaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"No Barcode Found",Toast.LENGTH_SHORT).show();
                }
            }
        }

        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
