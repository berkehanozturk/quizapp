package com.sagancompany.denemesinif;

import java.io.Serializable;

public class film implements Serializable {

    String title;
    String  cevapsSiklari[];

    public String[] getCevapsSiklari() {
        return cevapsSiklari;
    }

    public void setCevapsSiklari(String[] cevapsSiklari) {
        this.cevapsSiklari = cevapsSiklari;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
