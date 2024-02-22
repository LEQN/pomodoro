package pomodoro;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private PomodoroListener listener;
    private int pomodoroTime; // in seconds
    private int restTime;     // in seconds
    private int sessions;
    private int curSession;
    private boolean paused = false;
    private Timer timer;
    private Clip clip; //audio clip

    public PomodoroTimer(PomodoroListener listener, int pomo, int rest, int sessions) {
        this.listener = listener;
        this.pomodoroTime = pomo;
        this.restTime = rest;
        this.sessions = sessions;
        this.curSession = sessions;
        this.timer = new Timer();
        loadAudio();
    }

    public void start() {
        schedulePomo();
    }

    private void schedulePomo() {
        countDown(pomodoroTime, this::onPomoEnd);
        listener.sessionDisplayUpdate((sessions - curSession) + 1);
    }

    private void onPomoEnd() {
//        playAudio();
        curSession--;
        if (curSession > 0) {
            countDown(restTime, this::schedulePomo);
        } else {
            listener.onSessionsEnd();
        }
    }

    private void countDown(int duration, Runnable onEnd) {
//        start timer
        TimerTask count = new TimerTask() {
            int seconds = duration;

            @Override
            public void run() {
                if (!paused) {
                    if (seconds == 0) {
                        playAudio();
                        this.cancel();
                        onEnd.run();
                    } else {
                        seconds--;
                        timeToDisplay(seconds);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(count, 0, 1000);
    }

    private void timeToDisplay(int seconds) {
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;
        listener.timeDisplayUpdate(displayMinutes, displaySeconds);
    }

    public void pauseTimer() {
        paused = true;
    }

    public void resumeTimer() {
        paused = false;
    }

    public void endTimer() {
        clip.stop();
        timer.cancel();
    }

    private void loadAudio() {
        try {
            File file = new File("src/pomodoro/alarm.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }

    private void playAudio(){
        if (clip != null){
            clip.setFramePosition(0);
            clip.start();
            try{
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}