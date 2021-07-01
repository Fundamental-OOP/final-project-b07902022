package src;
import java.awt.*;
import java.util.*;

public class LongTile extends Tile {

    public boolean specialTile = true;

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

    public void setReleased(boolean released) {
        this.released = released;
    }



    @Override
    public boolean pointInTile(int x, int y)
    {
		int width = DontTouchTheWhiteTile.TILE_WIDTH;
		int height = DontTouchTheWhiteTile.TILE_HEIGHT * this.tileLength;
		// System.out.println(x + " " + y + " " + this.x + " " + this.y);
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

    @Override
    public void renderTile(Graphics g, int TILE_WIDTH, int TILE_HEIGHT) {
        if(!this.clicked){
            g.setColor(Color.black);
            g.fillRect(this.x, this.y, TILE_WIDTH, this.tileLength * TILE_HEIGHT);
            g.setColor(Color.white);
            g.drawRect(this.x, this.y, TILE_WIDTH, this.tileLength * TILE_HEIGHT);
        }
        else if(!this.released){
            g.setColor(Color.GRAY);
            System.out.println("Gray length " + (this.tileLength * TILE_HEIGHT - this.lastClickPos));
            g.fillRect(this.x, this.y + this.lastClickPos, TILE_WIDTH, this.tileLength * TILE_HEIGHT - this.lastClickPos);
            g.setColor(Color.BLACK);
            g.fillRect(this.x, this.y, TILE_WIDTH, this.lastClickPos);
        }
        else{
            g.setColor(Color.GRAY);
            g.fillRect(this.x, this.y, TILE_WIDTH, this.tileLength * TILE_HEIGHT);
            g.setColor(Color.black);
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
    public int handleScore(int combo) {
        // TODO Auto-generated method stub
        return 10 + 10 * combo;
    }

    @Override
    public boolean handleTracking(int x, int y, Iterator<Integer> it) {
        // TODO Auto-generated method stub
        this.setClicked(true);
        return false;
    }

    @Override
    public boolean getReleased() {
        // TODO Auto-generated method stub
        return released;
    }

    @Override
    public void setLastClickPos(int lcp) {
        // TODO Auto-generated method stub
        if(this.clicked && !this.released && this.lastClickPos > 0){
            this.lastClickPos = lcp;
        }
        
    }

    @Override
    public boolean isSpecialTile() {
        // TODO Auto-generated method stub
        return specialTile;
    }

    @Override
    public Tile generateTile(boolean hasSpecialTile, int COLUMNS, int TILE_WIDTH, int TILE_HEIGHT, Random random, ArrayList<Boolean> hasTileColumns, ArrayList<Tile> tiles) {
        // TODO Auto-generated method stub
        boolean createLongTail = (random.nextInt(10) == 0);
        if(!hasSpecialTile && createLongTail){
            int longTileColumn = random.nextInt(COLUMNS);
            while (hasTileColumns.get(longTileColumn) == true)
                longTileColumn = random.nextInt(COLUMNS);
            hasTileColumns.set(longTileColumn, true);
            int longTileRound = random.nextInt(3) + 5;
            LongTile newTile = new LongTile(longTileColumn * TILE_WIDTH, -longTileRound * TILE_HEIGHT, longTileRound);
            tiles.add(newTile);
            if (newTile.isSpecialTile()) hasSpecialTile = true;
            System.out.println(newTile.tileLength);
            return newTile;
        }
        return null;
    }
}