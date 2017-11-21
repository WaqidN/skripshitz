package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;
import android.support.test.runner.AndroidJUnit4;

import com.example.abnormal.crimereport.activity.admin.callback.LapProsesCallBack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 16/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class TestLapProses {

    String datanya = "{\"hasil\":[{\"user_username\":\"adirafairuz\",\"id\":\"9\",\"l_nama\":\"avelina\",\"l_email\":\"avelina_sitombling@gmail.con\",\"l_website\":\"http:\\/\\/indosatplus.blogspot.com\\/\",\"l_nohp\":\"085693253645\",\"l_title\":\"penipuan mengatasnamakan indosat\",\"l_des\":\"mohon untuk di tindak lanjuti dengan nomor dan situs yang saya berika\\n\",\"l_date\":\"2017-11-16 23:56:32\",\"l_status\":\"proses\",\"l_file\":\"\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestLapProses() throws Exception{

        LapProsesCallBack proses = new LapProsesCallBack(context);

        final SettableFuture<String> ff = SettableFuture.create();

        proses.LapProsesCallBack(new LapProsesCallBack.LapProsesBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
