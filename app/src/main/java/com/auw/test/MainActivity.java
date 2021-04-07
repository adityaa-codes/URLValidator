package com.auw.test;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout mUrlParentLayout;
    TextInputEditText mUrlEditText;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        clickListener();
    }


    private void init() {
        mUrlParentLayout = findViewById(R.id.UrlParentLayout);
        mUrlEditText = findViewById(R.id.UrlET);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
    }

    private void clickListener() {
        mUrlEditText.setOnEditorActionListener((TextView.OnEditorActionListener) (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                validateUrl();
                return true;
            }
            return false;


        });
    }

    private void validateUrl() {
        String Url = mUrlEditText.getText() != null ? mUrlEditText.getText().toString().trim() : null;
        String pattern = "^(?:http(s)?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:\\/?#[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$]";

        if (Url != null) {
            if (Url.matches(pattern)) {
                checkUrl(Url);
            } else {
                mUrlParentLayout.setError("Please Enter Valid Url");
            }
        } else {

            mUrlParentLayout.setError("Url cannot be null");
        }

    }

    private void checkUrl(String Url) {
        StringRequest checkUrl = new StringRequest(
                Request.Method.GET,
                Url,
                response -> {
                    mUrlParentLayout.setError(null);
                    mUrlParentLayout.setHelperText("URL Working Fine");
                }, error -> {
            mUrlParentLayout.setError("URL Error");
            mUrlParentLayout.setHelperText(null);


        });
        requestQueue.add(checkUrl);
    }
}



