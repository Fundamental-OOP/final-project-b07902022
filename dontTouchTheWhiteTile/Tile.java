package dontTouchTheWhiteTile;


public abstract class Tile{

	public int x, y, tileLength;

	//public int animateY;

	public boolean black;

	public boolean clicked;
	public boolean released;
	public int lastClickPos;

	public Tile(int x, int y, boolean black, int tileLength)
	{
		this.x = x;
		this.y = y;
		this.black = black;
		this.tileLength = tileLength;
		this.clicked = false;
		this.released = false;
		this.lastClickPos = tileLength * DontTouchTheWhiteTile.TILE_HEIGHT; //relative to up-left corner
	}

	public abstract void setClicked(boolean clicked);

	public abstract boolean pointInTile(int x, int y);

}