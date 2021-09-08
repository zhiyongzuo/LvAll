package com.epro.lvall.gsontestbean;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2021/6/25
 */
public class GsonTestBean implements Serializable {

    /**
     * timestamp : 11.00
     * status : 400
     * error : 22.10
     */

    private String timestamp;
    private String status;
    private String error;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
