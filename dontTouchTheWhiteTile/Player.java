package dontTouchTheWhiteTile;

public class Player implements Comparable<Player>{
    public String name;
    public int score;
    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Player o) {
        if (o.score < this.score)
            return 1 ;
        else if (o.score > this.score)
            return - 1 ;
        else
            return 0 ;
    }
}
