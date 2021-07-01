package src;

public class Music {
    private final String name;
    private final String direction;
    private final int timerNumber;
    private final Float speed;
    public Music (String name, String direction, int timerNumber, Float speed){
        this.name = name;
        this.direction = direction;
        this.timerNumber = timerNumber;
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public int getTimerNumber() {
        return timerNumber;
    }

    public Float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }
}
