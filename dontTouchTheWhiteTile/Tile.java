package dontTouchTheWhiteTile;


public abstract class Tile{

	public int x, y, tileLength;

	//public int animateY;

	public boolean black;

	public boolean clicked;

	public Tile(int x, int y, boolean black, int tileLength)
	{
		this.x = x;
		this.y = y;
		this.black = black;
		this.tileLength = tileLength;
		this.clicked = false;
	}

	public abstract boolean pointInTile(int x, int y);

}