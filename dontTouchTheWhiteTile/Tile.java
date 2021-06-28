package dontTouchTheWhiteTile;


public class Tile
{

	public int x, y;

	//public int animateY;

	public boolean black;

	public boolean clicked;

	public Tile(int x, int y, boolean black)
	{
		this.x = x;
		this.y = y;
		this.black = black;
		this.clicked = false;
	}

	public boolean pointInTile(int x, int y)
	{
		int width = DontTouchTheWhiteTile.TILE_WIDTH;
		int height = DontTouchTheWhiteTile.TILE_HEIGHT;
		// System.out.println(x + " " + y + " " + this.x + " " + this.y);
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

}