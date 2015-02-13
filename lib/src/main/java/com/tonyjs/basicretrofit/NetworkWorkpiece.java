package com.tonyjs.basicretrofit;

import java.io.Serializable;

/**
 * Created by tonyjs on 15. 2. 13..
 */
public class NetworkWorkpiece<T> implements Serializable {
    private static final long serialVersionUID = -19881222L;

    private T data;
    private String error;
    private boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "NetworkWorkpiece{" +
                "data=" + data +
                ", error='" + error + '\'' +
                ", success=" + success +
                '}';
    }

}
