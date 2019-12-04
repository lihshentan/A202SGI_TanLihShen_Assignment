package com.example.lihsh.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText username, password;
    Button login;
    SQlitehelper db;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_login,null);

        db = new SQlitehelper(getActivity());
        username = (EditText)root.findViewById(R.id.txt_name);
        password = (EditText)root.findViewById(R.id.txt_password);
        login = (Button)root.findViewById(R.id.btn_login);
        checkBox = (CheckBox)root.findViewById(R.id.checkBox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        checkSharedPreferences();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString();
                String mPassword = password.getText().toString();
                Boolean validator = db.validateUser(mUsername,mPassword);

                //shared preference
                if(checkBox.isChecked()){
                    editor.putString(getString(R.string.checkbox), "True");
                    editor.commit();

                    String name = username.getText().toString();
                    editor.putString(getString(R.string.name), name);
                    editor.commit();

                    String password1 = password.getText().toString();
                    editor.putString(getString(R.string.password), password1);
                    editor.commit();
                }else{
                    editor.putString(getString(R.string.checkbox), "False");
                    editor.commit();

                    editor.putString(getString(R.string.name), "");
                    editor.commit();

                    editor.putString(getString(R.string.password), "");
                    editor.commit();
                }


                //end of shared preference
                if(validator == true){
                    Toast.makeText(getActivity(),"Login successful, welcome "+mUsername,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), MainMenu.class);
                    i.putExtra("username", ""+mUsername);
                    startActivity(i);
                }else{
                    Toast.makeText(getActivity(),"Wrong email or password!",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false);
        return root;
    }

    public void checkSharedPreferences(){
        String mcheckbox = sharedPreferences.getString(getString(R.string.checkbox),"False");
        String mname = sharedPreferences.getString(getString(R.string.name),"");
        String mpassword = sharedPreferences.getString(getString(R.string.password),"");

        username.setText(mname);
        password.setText(mpassword);

        if(checkBox.equals("True")){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
