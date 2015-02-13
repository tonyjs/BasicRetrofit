package com.tonyjs.basicretrofit;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by tonyjs on 15. 2. 11..
 */
public class ParameterBuilder {
    private HashMap<String, String> mParameterMap;
    public ParameterBuilder() {
        mParameterMap = new HashMap<>();
    }

    public ParameterBuilder addParameter(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return this;
        }
        if (!mParameterMap.containsKey(key)) {
            mParameterMap.put(key, value);
        }
        return this;
    }

    public HashMap<String, String> build() {
        return mParameterMap;
    }
}