package com.example.prateek.weatherapplication.activities;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prateek.weatherapplication.R;
import com.example.prateek.weatherapplication.utility.Constant;
import com.example.prateek.weatherapplication.utility.Prefs;
import com.example.prateek.weatherapplication.utility.Utility;
import com.example.prateek.weatherapplication.utility.WeatherApplication;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;



/**
 * This is a LoginActivity
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{
    public static final String TAG = "LoginActivity";
    private ProgressDialog progressDialog;
    public static final int FACEBOOK_AUTH_CODE = 10002;
    public static final int GOOGLE_AUTH_CODE = 10003;
    public static final int TWITTER_AUTH_CODE = 10004;
    private String registerData = "";


    private EditText editTextPassword;
    private EditText editTextEmail;
    private Button butLogin;
    private Button mExplore_as_guest;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;


    private boolean mPasswordType = false;


    private ImageView mIVfacebook, mIVtwitter, mIVgooglepluse;


    //============== facebook variables initialize =============//
    private CallbackManager callbackManager;


    /**
     * This function is used to call function initView
     * and call setTypeface function
     * and call twitterButtonCallback function
     * and call facebookInitialize function
     * and call googlePlusInitialization function
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //====== facebook creation integration ========//

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        if(Prefs.isLogin(getApplicationContext())){
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("EMAIL", Prefs.getStringValueFromPreferences(getApplicationContext(), Constant.USER_LOGIN_TOKEN_PREF));
            startActivity(intent);
            finish();
        }else {
            facebookInitialize();


            initView();

        }

    }

    /**
     * This function is used to initialize facebook sdk
     */
    private void facebookInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserInformation(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("ex facebook", exception.getMessage());
                    }
                });


    }

    /**
     * This function is used to get user details from facebook result
     *
     * @param accessToken
     */
    private void getUserInformation(final AccessToken accessToken) {
        final GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            JSONObject jsonObjectData = null;
                            JSONArray jsonArray = null;
                            JSONObject jsonObject = response.getJSONObject();
                            if (response.getJSONObject() != null && response.getJSONObject().toString().length() > 2) {
                                JSONObject jsonRegistrationData = new JSONObject();
                                jsonRegistrationData.put("provider", "facebook");
                                jsonRegistrationData.put("id", jsonObject.optString("id"));
                                jsonRegistrationData.put("name", jsonObject.optString("name"));
                                jsonRegistrationData.put("email", jsonObject.optString("email"));
                                jsonRegistrationData.put("birthday", jsonObject.optString("birthday"));
                                jsonRegistrationData.put("image", jsonObject.getJSONObject("picture").getJSONObject("data").optString("url"));

                                registerData = jsonRegistrationData.toString();

                                jsonObjectData = new JSONObject();

                                String url = jsonObject.getJSONObject("picture").getJSONObject("data").optString("url");
                                String email = jsonObject.optString("email");
                                String name = jsonObject.optString("name");
                                jsonObjectData.put("email", email);

                                newActivityCall(email, name, url);
                                Log.e("email", email);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void newActivityCall(String email, String name, String url) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("EMAIL", email);
        intent.putExtra("NAME", name);
        intent.putExtra("URL", url);
        Prefs.setStringValueToPreferences(getApplicationContext(), Constant.USER_LOGIN_TOKEN_PREF, email);
        startActivity(intent);
        finish();

    }

    /**
     * This function is used to call waiting dialog
     */
    private void showProgressDialog() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            progressDialog = new ProgressDialog(this, R.style.AppCompatProgressDialogStyle);
        }else{
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * This function is used to call dismiss dialog
     */
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }



    /**
     * This function is used to initilizing All the views like Button , EditView , ImageView and social view
     */
    private void initView() {
        mIVfacebook = (ImageView) findViewById(R.id.iv_facebook);
        mIVfacebook.setOnClickListener(this);

    }


    /**
     * This function is used to call click events which is Calling for Views like Button , forgotpassword and social views
     * @param v
     */
    @Override
    public void onClick(View v) {


        switch (v.getId() /*to get clicked view id**/) {

            case R.id.iv_facebook:
                if (Utility.isConnectingToInternet(LoginActivity.this)) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_birthday", "user_location", "email"));
                } else {
                    Toast.makeText(LoginActivity.this, R.string.internat_connection_error, Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }

    /**
     * This funciton is used to call Result Activity for result which is coming from facebook, google and twitter
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);//facebook
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * This funciton is used to call resume funciton of activity (call settextchangelistener)
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This function is called when Backpressed is tap
     */
    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();


    }



}
