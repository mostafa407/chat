package com.mostafamohamed.Chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private CountryCodePicker ccp;
    private EditText phoneText;
    private EditText codeText;
    private Button continueAndNextBtn;
    private String checker="", phoneNumber="";
    private RelativeLayout relativeLayout;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth auth;
    private String mVerficationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        auth=FirebaseAuth.getInstance ();
        loadingBar=new ProgressDialog ( this );
        phoneText=findViewById ( R.id.phoneText );
        codeText=findViewById ( R.id.codeText );
        continueAndNextBtn=findViewById ( R.id.continueNextButton );
        relativeLayout=findViewById ( R.id.phoneAuth );
        ccp =(CountryCodePicker)findViewById ( R.id.ccp );
        ccp.registerCarrierNumberEditText ( phoneText );
        continueAndNextBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (continueAndNextBtn.getText ().equals ( "Submit" )||checker.equals ( "Code Sent" )){

                    String verficationCode=codeText.getText ().toString ();
                    if (verficationCode.equals ( "" )){
                        Toast.makeText ( LoginActivity.this, "please write verification code first.. ", Toast.LENGTH_SHORT ).show ( );
                    }else {
                        loadingBar.setTitle ( "Code Verification" );
                        loadingBar.setMessage ( "please wait , while we are verifying your code .. " );
                        loadingBar.setCanceledOnTouchOutside ( false );
                        loadingBar.show ();
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential ( mVerficationId,verficationCode );
                        signInWithPhoneAuthCredential ( credential );

                    }
                }else {

                    phoneNumber=ccp.getFullNumberWithPlus ();
                    if (!phoneNumber.equals ( "" )){
                        loadingBar.setTitle ( "Phone Number Verification" );
                        loadingBar.setMessage ( "please wait , while we are verifying your phone number " );
                        loadingBar.setCanceledOnTouchOutside ( false );
                        loadingBar.show ();
                        PhoneAuthProvider.getInstance ().verifyPhoneNumber (
                                phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                LoginActivity.this,
                                callbacks
                        );

                    }else {
                        Toast.makeText ( LoginActivity.this, "please write your valid", Toast.LENGTH_SHORT ).show ( );
                    }
                }
            }
        } );

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks ( ) {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential ( phoneAuthCredential );
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText ( LoginActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT ).show ( );
                loadingBar.dismiss ();
                relativeLayout.setVisibility ( View.VISIBLE );
                continueAndNextBtn.setText ( "Continue" );
                codeText.setVisibility ( View.GONE );
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent ( s, forceResendingToken );
                mVerficationId=s;
                mResendToken=forceResendingToken;
                relativeLayout.setVisibility ( View.GONE);
                checker="Code Sent";
                continueAndNextBtn.setText ( "Submit" );
                codeText.setVisibility ( View.VISIBLE );
                loadingBar.dismiss ();
                Toast.makeText ( LoginActivity.this, "Code has been sent, please check", Toast.LENGTH_SHORT ).show ( );
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart ( );
        FirebaseUser firebaseUser=FirebaseAuth.getInstance ().getCurrentUser ();
        if (firebaseUser!=null){

            Intent homeIntent=new Intent ( LoginActivity.this, MainpageActivity.class );
            startActivity ( homeIntent );
            finish ();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss ();
                            Toast.makeText ( LoginActivity.this, "Congratulations you are login successfully ", Toast.LENGTH_SHORT ).show ( );
                            sendUserToMain ();
                        } else {
                           loadingBar.dismiss ();
                           String e=task.getException ().toString ();
                            Toast.makeText ( LoginActivity.this, "Error:"+e, Toast.LENGTH_SHORT ).show ( );
                        }
                    }
                });
    }
    private void sendUserToMain(){

        Intent intent=new Intent ( LoginActivity.this, MainpageActivity.class );
        startActivity ( intent );
        finish ();
    }
}


