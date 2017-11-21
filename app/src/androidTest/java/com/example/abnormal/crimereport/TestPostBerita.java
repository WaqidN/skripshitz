package com.example.abnormal.crimereport;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;

import com.example.abnormal.crimereport.activity.admin.callback.PostCallBack;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abnormal on 16/11/17.
 */

public class TestPostBerita {

    String datanya = "{\"hasil\":[{\"id\":\"17\",\"post_author\":\"2\",\"post_title\":\"Jangan Tertipu, Undian Berhadiah Toyota Sienta Hoax!\",\"post_content\":\"\\r\\n<p>Solopos.com, SOLO &ndash; Penipuan online kembali terjadi. Sebuah akun fanpage di Facebook (FB) mengatasnamakan Toyota dan membuat undian berhadiah mobil MPV Sienta. Tetapi jangan tertipu, sebab undian itu tidak benar alias hoax.<br \\/><br \\/>Dalam posnya, akun Toyota Sienta menjelaskan supaya bisa memenangkan mobil tersebut, netizen wajib melakukan sejumlah syarat barupa me-like halamannya dan men-share undian tersebut serta memberi tag kepada dua orang teman.<br \\/><br \\/>Syarat terakhir, netizen diminta menuliskan warna mobil yang diinginkan di kolom komentar. Dijanjikan ada tiga unit Toyota Sienta untuk tiga orang pemenang. Pengundian akan dilakukan 30 April 2016.<br \\/><br \\/>Undian tersebut tampaknya sukses menyedot perhatian netizen. Terbukti, baru beberapa hari sejak akun itu dibuat, sudah ada lebih dari 52.000 netizen yang tergiur dengan hadiah mobil seharga Rp230 juta-Rp295 juta itu dan memberi like sekaligus menjadi followers.<br \\/><br \\/>Menanggapi hal itu, Public Relation Manager PT Toyota Astra Motor (TAM) Rouli Sijabat menegaskan bahwa fanpage tersebut bukan milik perusahaannya dan Toyota Indonesia tidak pernah mengadakan undian seperti itu.<br \\/><br \\/>&rdquo;Toyota Indonesia tidak pernah mengadakan pengundian baik di diler maupun di media sosial. Kehadiran Toyota Sienta memang membuat animo besar di masyarakat. Melihat penipuan ini tentu kami prihatin dan berharap masyarakat untuk lebih berhati-hati,&rdquo; terang Rouli sebagaimana dikutip dari laman Okezone, Jumat (15\\/4\\/2016).<br \\/><br \\/>Rouli semakin yakin bahwa undian itu adalah penipuan online lantaran akun tersebut menawarkan Toyota Sienta dalam warna biru dan kuning. Padahal dua warna itu tidak tersedia untuk unit yang beredar di Indonesia.<br \\/><br \\/>Berdasarkan pantauan Solopos.com, akun dengan jumlah 52.000 like itu sempat dihapus. Tetapi belakangan muncul akun baru dengan nama serupa dan mempromosikan undian yang sama persis. Sejauh ini akun tersebut sudah diikuti oleh 1.033 netizen.<\\/p>\\r\\n\",\"post_category\":\"berita\",\"post_status\":\"publish\",\"post_date\":\"2016-08-26 11:44:41\",\"post_type\":\"post\"}]}";
    Context context;

    @Before
    public void Test(){

        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useTestPostBerita() throws Exception{

        PostCallBack postB = new PostCallBack(context);

        final SettableFuture<String> ff = SettableFuture.create();

        postB.PostCallBack(new PostCallBack.PostBack() {
            @Override
            public void hasil(String hasil) {

                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi, datanya);
    }
}
