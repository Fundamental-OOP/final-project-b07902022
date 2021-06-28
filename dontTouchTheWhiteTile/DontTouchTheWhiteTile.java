package dontTouchTheWhiteTile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class DontTouchTheWhiteTile implements ActionListener, MouseListener, KeyListener
{

	public final static int COLUMNS = 3, ROWS = 3, TILE_WIDTH = 150, TILE_HEIGHT = 200;

	public final static int[] velocity = {40, 20, 10, 2};

	public final static int[] keyCodeList = {83, 68, 70, 32, 74, 75, 76};
	public Map<Integer, Integer> keyCodeToX = new HashMap<Integer, Integer>();
	
	public static DontTouchTheWhiteTile dttwt;

	public ArrayList<Tile> tiles;

	public Renderer renderer;

	public Random random;

	public int score, milSecDelay;

	public boolean gameOver;

	public int timescnt = 0;

	public int combo = 0;

	public DontTouchTheWhiteTile()
	{
		for (int i = 0; i < COLUMNS; i++) {
			keyCodeToX.put(keyCodeList[i+2], (int) (TILE_WIDTH * (i+0.5)));
		}
		JFrame frame = new JFrame("Don't Touch The White Tile!");
		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		random = new Random();

		frame.setSize(TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS);
		frame.add(renderer);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setResizable(false);

		start();

		timer.start();
	}

	public void start()
	{
		score = 0;
		gameOver = false;
		tiles = new ArrayList<Tile>();


		for(int y = -1; y < ROWS; y++){

			int blackTile = random.nextInt(COLUMNS);

			for(int x = 0; x < ROWS; x++){
				Tile newTile = new Tile(x * TILE_WIDTH, y * TILE_HEIGHT, x == blackTile);
				tiles.add(newTile);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		renderer.repaint();
		timescnt++;
		boolean getNewTile = false;
		int cnt = 0;

		int speed = velocity[0];

		if(timescnt > 200) speed = velocity[1];
		if(timescnt > 400) speed = velocity[2];

		for(Tile tile : tiles){
			//System.out.println("Origin: " + tile.x + ", " + tile.y);
			tile.y = tile.y + TILE_HEIGHT / speed;
			//System.out.println("After: " + tile.x + ", " + tile.y);
		}

		for (int i = 0; i < tiles.size(); i++)
		{
			Tile tile = tiles.get(i);
			//tile.y += TILE_HEIGHT / 10;
			if(tile.y == TILE_HEIGHT * ROWS){
				if (tile.black && !tile.clicked) combo = 0;
				tiles.remove(i);
				getNewTile = true;
				cnt++;
				i--;
			}
		}
		// System.out.println("-------------------------------cnt: " + cnt + "-------------------------------");

		if(getNewTile){
			// System.out.println("Time : " + timescnt);
			int blackTile = random.nextInt(COLUMNS);
			for(int j = 0; j < COLUMNS; j++){
				Tile newTile = new Tile(j * TILE_WIDTH, -TILE_HEIGHT, j == blackTile);
				tiles.add(newTile);
			}
		}

		milSecDelay++;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS);

		g.setFont(new Font("Arial", 1, 100));

		if (!gameOver)
		{
			for (Tile tile : tiles)
			{
				g.setColor(tile.black ? Color.BLACK : Color.WHITE);
				g.fillRect(tile.x, tile.y, TILE_WIDTH, TILE_HEIGHT);
				g.setColor(tile.black ? Color.WHITE : Color.BLACK);
				g.drawRect(tile.x, tile.y, TILE_WIDTH, TILE_HEIGHT);
			}

			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(combo), 0, 100);
			g.setColor(Color.RED);
			g.drawString(String.valueOf(score), TILE_WIDTH, 100);
			g.setColor(Color.RED);
			g.drawLine(0, TILE_HEIGHT * (ROWS - 1), TILE_WIDTH * COLUMNS, TILE_HEIGHT * (ROWS - 1));
		}
		else
		{
			g.setColor(Color.BLACK);
			g.drawString("Game Over!", 100, TILE_HEIGHT);
		}
	}

	public static void main(String[] args)
	{
		dttwt = new DontTouchTheWhiteTile();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		/*boolean clicked = false;

		if (!gameOver)
		{
			for (int i = 0; i < tiles.size(); i++)
			{
				Tile tile = tiles.get(i);

				if (tile.pointInTile(e.getX(), e.getY()) && !clicked)
				{
					if (e.getY() > TILE_HEIGHT * (ROWS - 1))
					{
						if (tile.black)
						{
							for (int j = 0; j < tiles.size(); j++)
							{
								if (tiles.get(j).y == ROWS)
								{
									tiles.remove(j);
								}

								tiles.get(j).y++;
								tiles.get(j).animateY -= TILE_HEIGHT;
							}

							score += Math.max(100 - milSecDelay, 10);

							System.out.println("You've scored " + Math.max(100 - milSecDelay, 10) + " points!");

							milSecDelay = 0;

							boolean canBeBlack = true;

							for (int x = 0; x < COLUMNS; x++)
							{
								boolean black = random.nextInt(2) == 0 || x == COLUMNS - 1;

								Tile newTile = null;

								if (canBeBlack && black)
								{
									newTile = new Tile(x, 0, true);
									canBeBlack = false;
								}
								else
								{
									newTile = new Tile(x, 0, false);
								}

								newTile.animateY -= TILE_HEIGHT;

								tiles.add(newTile);
							}
						}
						else
						{
							gameOver = true;
						}

						clicked = true;
					}
					else
					{
						gameOver = true;
					}
				}
			}
		}
		else
		{
			start();
		}*/
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (!keyCodeToX.containsKey(keyCode)) {
			return;
		}
		// System.out.println(keyCode);
		int x = keyCodeToX.get(keyCode);
		int y = TILE_HEIGHT * (ROWS - 1);
		// System.out.println("fuck1 " + x + " " + y);
		if (!gameOver) {
			for (int i = 0; i < tiles.size(); i++) {
				Tile tile = tiles.get(i);
				// System.out.println(tile.pointInTile(x, y));
				if (tile.pointInTile(x, y) && !tile.clicked) {
					// System.out.println("fuck2 " + tile.x + " " + tile.y + " " + tile.black);
					if (tile.black) {
						score += Math.max(100 - milSecDelay, 10);
						System.out.println("You've scored " + Math.max(100 - milSecDelay, 10) + " points!");
						milSecDelay = 0;
						tile.clicked = true;
						combo += 1;
					} else {
						combo = 0;
					}
				}
			}
		}
		else start();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
