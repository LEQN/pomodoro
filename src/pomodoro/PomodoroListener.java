package pomodoro;

public interface PomodoroListener {
    void onSessionsEnd();

    void timeDisplayUpdate(int min, int sec);

    void sessionDisplayUpdate(int session);
}
