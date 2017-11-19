package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.LapSelesaiCallBack;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 16/11/17.
 */

public class TestLapSelesai {

    String datanya = "";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestLapSelesai() throws Exception{

        LapSelesaiCallBack selesai = new LapSelesaiCallBack(context);

        final SettableFuture<String> ff = SettableFuture.create();

        selesai.LapSelesaiCallBack(new LapSelesaiCallBack.LapSelesaiBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
