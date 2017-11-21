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

    String datanya = "{\"hasil\":[{\"user_username\":\"adirafairuz\",\"id\":\"8\",\"l_nama\":\"danu\",\"l_email\":\"danu@gmail.con\",\"l_website\":\"http:\\/\\/gebyarpoin555.blogspot.com\",\"l_nohp\":\"02140885777\",\"l_title\":\"penipuan mengatasnamakan indosat\",\"l_des\":\"penipuan dengan modus gebyar poin 555 \",\"l_date\":\"2017-11-16 23:56:41\",\"l_status\":\"selesai\",\"l_file\":\"\"}]}";
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
