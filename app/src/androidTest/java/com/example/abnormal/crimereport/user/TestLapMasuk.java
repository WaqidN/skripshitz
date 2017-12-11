package com.example.abnormal.crimereport.user;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.LapMasukCallBack;
import com.example.abnormal.crimereport.activity.user.callback.CallBackLapMasuk;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 09/12/17.
 */

public class TestLapMasuk {

    String data= "{\"hasil\":[{\"user_username\":\"nfk05\",\"id\":\"41\",\"l_nama\":\"Alkantara Dewa Alfiansyah\",\"l_email\":\"alfiansyah.dewa@gmail.com\",\"l_website\":\"putingbeliungbritama.blogspot.com\",\"l_nohp\":\"081329521151\",\"l_title\":\"Penipuan PutingBeliung Britama\",\"l_des\":\"lacak penipuan\",\"l_date\":\"2017-12-09 02:36:58\",\"l_file\":\"\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void TestLapmasuk() throws Exception{

        CallBackLapMasuk masuk = new CallBackLapMasuk(context);

        final SettableFuture<String> ff = SettableFuture.create();

        masuk.CallBackLapMasuk("5", new CallBackLapMasuk.LapMasukBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, data);
    }
}


