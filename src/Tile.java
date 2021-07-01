package dontTouchTheWhiteTile;
import java.awt.*;
import java.util.*;

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

	public abstract void setLastClickPos(int lcp);

	public abstract void renderTile(Graphics g, int TILE_WIDTH, int TILE_HEIGHT);

	public abstract boolean handleKeyReleased(int x, int y);

	public abstract boolean handleTracking(int x, int y, Iterator<Integer> it);

	public abstract int handleScore(int combo);

	public abstract void setClicked(boolean clicked);

	public abstract void setReleased(boolean released);

	public abstract boolean getReleased();

	public abstract boolean pointInTile(int x, int y);

	public abstract boolean isSpecialTile();

	public abstract Tile generateTile(boolean hasSpecialTile, int COLUMNS, int TILE_WIDTH, int TILE_HEIGHT, Random random, ArrayList<Boolean> hasTileColumns, ArrayList<Tile> tiles);
}