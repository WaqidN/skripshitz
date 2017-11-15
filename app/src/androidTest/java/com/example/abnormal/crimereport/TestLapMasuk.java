package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;
import android.support.test.runner.AndroidJUnit4;

import com.example.abnormal.crimereport.activity.admin.callback.LapMasukCallBack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 15/11/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestLapMasuk {

    String datanya = "{\"hasil\":[{\"user_username\":\"wch15\",\"id\":\"54\",\"l_nama\":\"waqid nugroho\",\"l_email\":\"waqid.nugroho@gmail.com\",\"l_website\":\"https:\\/\\/pemenangundianmkiostelkomsel.wordpress.com\\/\",\"l_nohp\":\"081329521151\",\"l_title\":\"Pemenang Undian M-kios 2017\",\"l_des\":\"Pemenang Undian M-kios 2017\\n\",\"l_date\":\"20:05:34\",\"l_status\":\"masuk\",\"l_file\":\"http:\\/\\/localhost\\/pelaporan\\/content\\/uploads\\/2017\\/10\\/20171025195921-P70813-204944-1(1).jpg|http:\\/\\/localhost\\/pelaporan\\/content\\/uploads\\/2017\\/10\\/20171025200525-P70813-204944-1(1).jpg\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestLapmasuk() throws Exception{

        LapMasukCallBack masuk = new LapMasukCallBack(context);

        final SettableFuture<String> ff = SettableFuture.create();

        masuk.LapMasukCallBack(new LapMasukCallBack.LapMasukBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
