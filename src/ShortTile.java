package src;
import java.awt.*;
import java.util.*;

public class ShortTile extends Tile {

    public boolean specialTile = true;

    public ShortTile(int x, int y, boolean black){
        super(x, y, black, 1);
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    @Override
    public boolean pointInTile(int x, int y)
    {
		int width = DontTouchTheWhiteTile.TILE_WIDTH;
		int height = DontTouchTheWhiteTile.TILE_HEIGHT;
		// System.out.println(x + " " + y + " " + this.x + " " + this.y);
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

    @Override
    public void renderTile(Graphics g, int TILE_WIDTH, int TILE_HEIGHT) {
        if(this.clicked && this.black){
            g.setColor(Color.GRAY);
            g.fillRect(this.x, this.y, TILE_WIDTH, TILE_HEIGHT);
        }
        else{
            g.setColor(this.black ? Color.BLACK : Color.WHITE);
            g.fillRect(this.x, this.y, TILE_WIDTH, this.tileLength * TILE_HEIGHT);
            g.setColor(this.black ? Color.WHITE : Color.BLACK);
            g.drawRect(this.x, this.y, TILE_WIDTH, this.tileLength * TILE_HEIGHT);
        }
    }

    @Override
    public boolean handleKeyReleased(int x, int y) {
        if (this.pointInTile(x, y) && !this.released) {
            // System.out.println("fuck2 " + this.x + " " + this.y + " " + this.black);
            if (this.black) {
                this.setReleased(true);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean handleTracking(int x, int y, Iterator<Integer> it) {
        // TODO Auto-generated method stub
        this.setClicked(true);
        this.setReleased(true);
        it.remove();
        return true;
    }

    @Override
    public int handleScore(int combo) {
        // TODO Auto-generated method stub
        return 100 + 10 * combo;
    }

    @Override
    public boolean getReleased() {
        // TODO Auto-generated method stub
        return released;
    }

    @Override
    public void setLastClickPos(int lcp) {
        // TODO Auto-generated method stub
        return;
        
    }

    @Override
    public boolean isSpecialTile() {
        // TODO Auto-generated method stub
        return specialTile;
    }

    @Override
    public Tile generateTile(boolean hasSpecialTile, int COLUMNS, int TILE_WIDTH, int TILE_HEIGHT, Random random,
            ArrayList<Boolean> hasTileColumns, ArrayList<Tile> tiles) {
        // TODO Auto-generated method stub
        int blackTile = random.nextInt(COLUMNS);
        for(int j = 0; j < COLUMNS; j++){
            if (hasTileColumns.get(j)) continue;
            ShortTile newTile = new ShortTile(j * TILE_WIDTH, -TILE_HEIGHT, j == blackTile);
            tiles.add(newTile);
        }
        
        return null;
    }
}