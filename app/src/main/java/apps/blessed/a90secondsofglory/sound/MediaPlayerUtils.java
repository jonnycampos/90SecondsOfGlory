package apps.blessed.a90secondsofglory.sound;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import apps.blessed.a90secondsofglory.R;

/**
 * Created by jacam on 19/02/2019.
 */

public final class MediaPlayerUtils {

    public static void releaseSound(MediaPlayer mediaPlayer) {
        mediaPlayer.reset();
        mediaPlayer.release();
    }


    public static void playSound(Context context, int sound) {

            MediaPlayer media = MediaPlayer.create(context, sound);
            media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer media) {
                    releaseSound(media);
                    media = null;
                }
            });
            //media.prepareAsync();
            media.start();


    }
}
