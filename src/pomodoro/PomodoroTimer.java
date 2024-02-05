package pomodoro;

import javax.swing.*;
import java.awt.event.*;

public class PomodoroTimer {
    private int pomodoroTime; // in seconds
    private int restTime;     // in seconds
    private int sessions;

    public PomodoroTimer(int pomo, int rest, int sessions){
        this.pomodoroTime = pomo;
        this.restTime = rest;
        this.sessions = sessions;
    }
}
