package cmpe.smartalertapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.IBinder;

import java.util.Random;

public class AlarmService extends Service {

    /*
    boolean m_stop = false;
    AudioTrack m_audioTrack;
    Thread m_noiseThread;

    Runnable m_noiseGenerator = new Runnable()
    {
        public void run()
        {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

            // 8000 bytes per second, 1000 bytes = 125 ms
            byte [] noiseData = new byte[1000];
            Random rnd = new Random();

            while(!m_stop)
            {
                rnd.nextBytes(noiseData);
                m_audioTrack.write(noiseData, 0, noiseData.length);
                audioTrack.write(generatedSnd, 0, generatedSnd.length);
            }
        }
    };

    void start()
    {
        m_stop = false;

        // 8000 bytes per second
        m_audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT, 8000 // 1 second buffer
                AudioTrack.MODE_STREAM);

        m_audioTrack.play();


        m_noiseThread = new Thread(m_noiseGenerator);
        m_noiseThread.start();
    }

    void stop() {
        m_stop = true;
        m_audioTrack.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        stop();
    }

    */
    private final int duration = 3; // a whole day (excessive? sorry, can't hear you overBEEEEEEEEEEEEEEP)
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 1600; // hz
    private AudioTrack alarm;
    private AudioManager mAudioManager;
    private int originalVolume;

    private final byte generatedSnd[] = new byte[2 * numSamples];

    Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        genTone();
        alarm = genTrack();
        alarm.setLoopPoints(0, generatedSnd.length / 2, -1);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        /*
        running = true;
        thread = new Thread(new Runnable() {
            public void run() {

                while (running){
                    alarm.play();
                }


                handler.post(new Runnable() {

                    public void run() {
                        while (running){
                            alarm.play();
                        }
                    }
                });

            }
        });
        thread.start();
        */
        alarm.play();

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        alarm.stop();
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
    }

    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    public AudioTrack genTrack(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        return audioTrack;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
