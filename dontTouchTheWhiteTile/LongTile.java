package dontTouchTheWhiteTile;

public class LongTile extends Tile {


    public boolean isOnCombo;

    public boolean hasBeenClick;

    public LongTile(int x, int y, int tileLength){
        super(x, y, true, tileLength);
        this.hasBeenClick = false;
        this.isOnCombo = false;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }



    @Override
    public boolean pointInTile(int x, int y)
    {
		int width = DontTouchTheWhiteTile.TILE_WIDTH;
		int height = DontTouchTheWhiteTile.TILE_HEIGHT * this.tileLength;
		// System.out.println(x + " " + y + " " + this.x + " " + this.y);
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
}