package com.example.abnormal.crimereport.model;

import java.io.Serializable;

/**
 * Created by abnormal on 23/11/17.
 */

public class Lacak implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getLacaktitle() {
        return lacaktitle;
    }

    public void setLacaktitle(String lacaktitle) {
        this.lacaktitle = lacaktitle;
    }

    public String getLacakhost() {
        return lacakhost;
    }

    public void setLacakhost(String lacakhost) {
        this.lacakhost = lacakhost;
    }

    public String getLacakFullLink() {
        return lacakFullLink;
    }

    public void setLacakFullLink(String lacakFullLink) {
        this.lacakFullLink = lacakFullLink;
    }

    public String lacaktitle;
    public String lacakhost;
    public String lacakFullLink;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isSelected;

}
