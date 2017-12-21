package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.DiperiksaCallBack;
import com.example.abnormal.crimereport.activity.admin.callback.PenipuCallBack;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 20/12/17.
 */

public class TestPenipu {

    String datanya = "{\"hasil\":[{\"id\":\"3\",\"si_host\":\"indosatplus.blogspot.com\",\"si_full_url\":\"http:\\/\\/indosatplus.blogspot.com\\/p\\/semarak-poin-plus-plus-indosat-daftar.html\",\"si_title\":\"INDOSAT: DAFTAR PEMENANG\",\"si_status\":\"penipu\",\"si_date\":\"2016-10-05 13:54:33\"},{\"id\":\"2\",\"si_host\":\"hadiahtri.blogspot.com\",\"si_full_url\":\"http:\\/\\/hadiahtri.blogspot.com\\/\",\"si_title\":\"Tri\",\"si_status\":\"penipu\",\"si_date\":\"2016-10-05 13:54:33\"},{\"id\":\"1\",\"si_host\":\"kejutantelkomsel.blogspot.com\",\"si_full_url\":\"http:\\/\\/kejutantelkomsel.blogspot.com\\/p\\/blog-page.html\",\"si_title\":\"TELKOMSEL: DAFTAR PEMENANG HADIAH\",\"si_status\":\"penipu\",\"si_date\":\"2016-10-05 13:54:33\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestDisimpan() throws Exception{

        PenipuCallBack masuk = new PenipuCallBack (context);

        final SettableFuture<String> ff = SettableFuture.create();

        masuk.PenipuCallBack(new PenipuCallBack.PenipuBack() {

            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
