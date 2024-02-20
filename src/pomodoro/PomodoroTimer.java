package pomodoro;

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

    public PomodoroTimer(PomodoroListener listener, int pomo, int rest, int sessions){
        this.listener = listener;
        this.pomodoroTime = pomo;
        this.restTime = rest;
        this.sessions = sessions;
        this.curSession = sessions;
        this.timer = new Timer();
    }

    public void start(){
        schedulePomo();
    }

    private void schedulePomo(){
        countDown(pomodoroTime, this::onPomoEnd);
        listener.sessionDisplayUpdate((sessions - curSession)+1);
    }

    private void onPomoEnd(){
        curSession--;
        if (curSession>0){
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
                    if (!paused) {
                        if (seconds == 0) {
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

    private void timeToDisplay(int seconds){
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;
        listener.timeDisplayUpdate(displayMinutes, displaySeconds);
    }

    public void pauseTimer(){
        paused = true;
    }

    public void continueTimer(){
        paused = false;
    }

    public void endTimer(){
        timer.cancel();
    }
}
