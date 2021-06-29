package dontTouchTheWhiteTile;

public class ShortTile extends Tile {

    public ShortTile(int x, int y, boolean black){
        super(x, y, black, 1);
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
    @Override
    public boolean pointInTile(int x, int y)
    {
		int width = DontTouchTheWhiteTile.TILE_WIDTH;
		int height = DontTouchTheWhiteTile.TILE_HEIGHT;
		// System.out.println(x + " " + y + " " + this.x + " " + this.y);
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
}