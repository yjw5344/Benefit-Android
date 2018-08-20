package com.project.graduate.neartheplace.SignIn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.graduate.neartheplace.MainActivity.MainActivity;
import com.project.graduate.neartheplace.SignUp.RegistActivity;
import com.project.graduate.neartheplace.SignUp.SignUp_CustomDialog;
import com.project.graduate.neartheplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    // 사용자 인증 변수
    private UserLoginTask mAuthTask = null;

    // UI 변수.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SignUp_CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // login form 설정
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    // 비밀번호에서 로그인 누른 경우
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        // Button 설정
        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        Button mSignUpButton = (Button) findViewById(R.id.sign_up_button);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    // Sign Up - Dialog
    public void Dialog() {
        dialog = new SignUp_CustomDialog(LoginActivity.this, registListener, naverListener, cancleListener);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();


    }

    // Dialog - 회원가입 버튼 클릭 이벤트
    private View.OnClickListener registListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
            Intent connectRegistActicity = new Intent(LoginActivity.this, RegistActivity.class);
            startActivity(connectRegistActicity);
        }
    };

    // Dialog - Naver 버튼 클릭 이벤트
    private View.OnClickListener naverListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(LoginActivity.this, "네이버", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };

    // Dialog - cancle 버튼 클릭 이벤트
    private View.OnClickListener cancleListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };


    // todo : 이메일 입력_자동완성
    // 설명 : 자주 사용하는 사용자의 ID 2개 정도를 캐시데이터로 입력 받고 AutoComplete으로 채워준다
//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }

    // 로그인 형식 확인 구현
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            // ID & PW 로그인 형식 성공시 작동!
            mAuthTask = new UserLoginTask(email, password);


            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: 이메일 로그인 형식
        // 현재 '@' 안 들어가면 에러
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: 비밀번호 조건
        // 현재 조건 : 6자 미만 입력시 에러
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    // 로그인 : 아이디와 비밀번호 매칭 확인!
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String mFlag;
        private String tokenValue;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            mFlag = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            // okHttp 설정
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("email", mEmail)
                    .add("password", mPassword)
                    .build();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/auth/signin")
                    .post(formBody)
                    .build();

            // async를 사용하는 경우
            Response responseClient = null;
            try {
                responseClient = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(responseClient.body().string());
                mFlag = jsonObject.getString("stat");
                tokenValue = jsonObject.getString("token");
                saveToken(tokenValue);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("Json","JsonError");
            }

            // Callback 함수를 사용하는 경우 => 비동기 처리 방식으로 okhttp 처리하기 !
//            client.newCall(request).enqueue(callback);

            // 프로그래스바 시간을 좀 더 늘이고 싶을 때 사용
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }


            // 파싱한 Json으로 판별
            if (mFlag.equals("success")) {
                return true;
            } else if (mFlag.equals("fail")) {

                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            showProgress(false);

            if (success) {

                Toast.makeText(LoginActivity.this, mAuthTask.mEmail, Toast.LENGTH_SHORT).show();

                // 로그인 성공
                Intent connectMainActicity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(connectMainActicity);
                mEmailView.setText("");
                mPasswordView.setText("");
                mAuthTask = null;

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                mAuthTask = null;
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
            mAuthTask = null;
        }

    }

    private void saveToken(String tokenValue){
        SharedPreferences preferences = getSharedPreferences("userToken", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", tokenValue);
        editor.commit();
    }


//    public Callback callback = new Callback() {
//        @Override
//        public void onFailure(okhttp3.Call call, IOException e) {
//            Log.d("TEST", "ERROR Message : " + e.getMessage());
//        }
//
//        @Override
//        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//            String responseData = response.body().string();
//            Log.d("TEST", "responseData : " + responseData);

//        }
//    };

}

