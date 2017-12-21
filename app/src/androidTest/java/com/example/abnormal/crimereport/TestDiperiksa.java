package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.DiperiksaCallBack;
import com.example.abnormal.crimereport.activity.admin.callback.DisimpanCallBack;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 20/12/17.
 */

public class TestDiperiksa {

    String datanya = "{\"hasil\":[{\"id\":\"4\",\"si_host\":\"hadiah-tam-serbu.blogspot.com\",\"si_full_url\":\"http:\\/\\/hadiah-tam-serbu.blogspot.com\\/\",\"si_title\":\"TAM\",\"si_status\":\"diperiksa\",\"si_date\":\"2016-10-05 13:54:33\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestDisimpan() throws Exception{

        DiperiksaCallBack masuk = new DiperiksaCallBack (context);

        final SettableFuture<String> ff = SettableFuture.create();

        masuk.DiperiksaCallBack(new DiperiksaCallBack.DiperiksaBack() {

            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
