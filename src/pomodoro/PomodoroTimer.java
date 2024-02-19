package pomodoro;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private PomodoroListener listener;
    private int pomodoroTime; // in seconds
    private int restTime;     // in seconds
    private int sessions;
    private Timer timer;

    public PomodoroTimer(PomodoroListener listener, int pomo, int rest, int sessions){
        this.listener = listener;
        this.pomodoroTime = pomo;
        this.restTime = rest;
        this.sessions = sessions;
        this.timer = new Timer();
    }

    public void start(){
        schedulePomo();
    }

    private void schedulePomo(){
        countDown(pomodoroTime, this::onPomoEnd);
    }

    private void onPomoEnd(){
        sessions--;
        if (sessions>0){
            countDown(restTime, this::schedulePomo);
        }else{
            listener.onSessionsEnd();
        }
    }

    private void countDown(int duration, Runnable onEnd) {
//        start timer
            TimerTask count = new TimerTask() {
                int seconds = duration;
                @Override
                public void run() {
                    if (seconds == 0) {
                        this.cancel();
                        onEnd.run();
                    } else {
                        seconds--;
                        System.out.println(seconds);
                    }
                }
            };
            timer.scheduleAtFixedRate(count, 0, 1000);
    }

    public void endTimer(){
        timer.cancel();
    }
}
