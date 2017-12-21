package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.DisimpanCallBack;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 20/12/17.
 */

public class TestDisimpan {

    String datanya = "{\"hasil\":[{\"id\":\"36\",\"si_host\":\"source.android.com\\/\",\"si_full_url\":\"https:\\/\\/source.android.com\\/\",\"si_title\":\"Android Open Source Project\",\"si_status\":\"disimpan\",\"si_date\":\"2017-12-19 16:17:10\"},{\"id\":\"35\",\"si_host\":\"theguardian.com\\/australia-news\\/joko-widodo\",\"si_full_url\":\"https:\\/\\/www.theguardian.com\\/australia-news\\/joko-widodo\",\"si_title\":\"Joko Widodo | Australia-news | The Guardian\",\"si_status\":\"disimpan\",\"si_date\":\"2017-12-19 14:55:08\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestDisimpan() throws Exception{

        DisimpanCallBack masuk = new DisimpanCallBack (context);

        final SettableFuture<String> ff = SettableFuture.create();

        masuk.DisimpanCallBack(new DisimpanCallBack.WebBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
