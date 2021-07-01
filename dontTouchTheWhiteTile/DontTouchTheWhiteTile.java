package dontTouchTheWhiteTile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.Timer;

public class DontTouchTheWhiteTile implements ActionListener, MouseListener, KeyListener
{

	public final static int ROWS = 3, TILE_WIDTH = 250, TILE_HEIGHT = 300;

	public int COLUMNS = 3;

	public final static int[] velocity = {30, 15, 5, 3};

	public int[] keyCodeList = {83, 68, 70, 32, 74, 75, 76};
	public Map<Integer, Integer> keyCodeToX = new HashMap<Integer, Integer>();
	
	public static DontTouchTheWhiteTile dttwt;

	public ArrayList<Tile> tiles;

	public Renderer renderer;

	public Random random;

	public int score, milSecDelay;

	public boolean gameOver;

	public String song_Name;

	public boolean hasLongTile = false;

	public int longTileColumn = -1;

	public int longTileRound = 0;

	public int timescnt = 0;

	public int combo = 0;

	private int first = 1;

	public int speed;

	public Float factor = 1F;

	public MidiSound music;

	public Set<Integer> pressedKeys = new HashSet<>();

	public int countStart = 3;

	public String username;

	public String speed_String;

	public boolean write = false;

	public Music[] musics = {new Music("Canon", "./music/music.mid", 20, 175F),
			new Music("Turkish March", "./music/turkey.mid", 20, 80F),
			new Music("Für Elise", "./music/garbage.mid", 20, 92.6F),
			new Music("Ballade pour Adeline", "./music/water.mid", 22, 85F)
			};



	public DontTouchTheWhiteTile(String username, int column, String speed, String songName)
	{
		if (username == null) this.username = "player";
		else this.username = username;
		if (column == 0) this.COLUMNS = 3;
		else this.COLUMNS = column;
		if (songName == null) this.song_Name = "Canon";
		else this.song_Name = songName;
		this.speed_String = speed;
		if (speed == null) {
			this.speed = velocity[0];
		} else if (speed.equals("slow")) {
			this.speed = velocity[0];
		} else if (speed.equals("fast")) {
			this.speed = velocity[1];
		} else if (speed.equals("super fast")) {
			this.speed = velocity[2];
		} else if (speed.equals("hell")) {
			this.speed = velocity[3];
		} else {
			this.speed = velocity[0];
		}
		if (this.COLUMNS == 3) {
			keyCodeList = new int[] {70, 32, 74};
		} else if (this.COLUMNS == 4) {
			keyCodeList = new int[] {68, 70, 74, 75};
		}
		for (int i = 0; i < COLUMNS; i++) {
			keyCodeToX.put(keyCodeList[i], (int) (TILE_WIDTH * (i+0.5)));
		}
		int nowTimer = musics[0].getTimerNumber();
		Float nowSpeed = musics[0].getSpeed();
		String sonDir = musics[0].getDirection();
		for (Music value : musics) {
			if (value.getName().equals(songName)) {
				nowTimer = value.getTimerNumber();
				nowSpeed = value.getSpeed();
				sonDir = value.getDirection();
			}
		}


		JFrame frame = new JFrame("Don't Touch The White Tile!");
		Timer timer = new Timer(nowTimer, this);
		music = new MidiSound(sonDir, nowSpeed);
		renderer = new Renderer();
		random = new Random();

		frame.setSize(TILE_WIDTH * COLUMNS + 350, TILE_HEIGHT * ROWS);
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
		timescnt = 0;
		countStart = 3;
		write = false;
		tiles = new ArrayList<Tile>();
		try {
			music.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(int y = -1; y < ROWS; y++){

			int blackTile = random.nextInt(COLUMNS);

			if(y >= 0) blackTile = -1;

			for(int x = 0; x < ROWS; x++){
				ShortTile newTile = new ShortTile(x * TILE_WIDTH, y * TILE_HEIGHT, x == blackTile);
				tiles.add(newTile);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		renderer.repaint();
		timescnt++;
		// int speed = velocity[2];
		if(longTileRound > 0) longTileRound = longTileRound - TILE_HEIGHT / speed;
		if(longTileRound == 0) longTileColumn = -1;
		boolean getNewTile = false;

		if(timescnt > 150){
			for(Tile tile : tiles){
				tile.y = tile.y + TILE_HEIGHT / speed;
				if(tile.tileLength > 1){
					if(tile.clicked && !tile.released && tile.lastClickPos > 0){
						tile.lastClickPos = tile.lastClickPos - TILE_HEIGHT / speed;
					}
				}
			}
		}
		else{
			if(timescnt % 50 == 0){
				countStart = 3 - (timescnt / 50);
			}
		}

		for (int i = 0; i < tiles.size(); i++)
		{
			Tile tile = tiles.get(i);
			//tile.y += TILE_HEIGHT / 10;
			if(tile.y == TILE_HEIGHT * ROWS){
				if (tile.tileLength == 1 && tile.black && !tile.clicked) combo = 0;
				if (tile.tileLength > 1){
					hasLongTile = false;
				}
				tiles.remove(i);
				getNewTile = true;
				i--;
			}
		}

		if(getNewTile){
			boolean createLongTail = (random.nextInt(10) == 0);
			if(!hasLongTile && createLongTail){
				longTileColumn = random.nextInt(COLUMNS);
				longTileRound = random.nextInt(3) + 5;
				LongTile newTile = new LongTile(longTileColumn * TILE_WIDTH, -longTileRound * TILE_HEIGHT, longTileRound);
				longTileRound = (longTileRound + 1) * TILE_HEIGHT;
				tiles.add(newTile);
				hasLongTile = true;
				System.out.println("long");
			}
			int blackTile = random.nextInt(COLUMNS);
			for(int j = 0; j < COLUMNS; j++){
				if(j == longTileColumn) continue;
				ShortTile newTile = new ShortTile(j * TILE_WIDTH, -TILE_HEIGHT, j == blackTile);
				tiles.add(newTile);
			}
		}
		KeepTrackTheKeyboard();
		if(!music.CheckRunning() || timescnt > 3000){
			music.stop();
			gameOver = true;
		}
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
				if(tile.tileLength == 1){
					if(tile.clicked && tile.black){
						g.setColor(Color.GRAY);
						g.fillRect(tile.x, tile.y, TILE_WIDTH, TILE_HEIGHT);
					}
					else{
						g.setColor(tile.black ? Color.BLACK : Color.WHITE);
						g.fillRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
						g.setColor(tile.black ? Color.WHITE : Color.BLACK);
						g.drawRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
					}
				}
				else{
					if(!tile.clicked){
						g.setColor(Color.black);
						g.fillRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
						g.setColor(Color.white);
						g.drawRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
					}
					else if(!tile.released){
						g.setColor(Color.GRAY);
						System.out.println("Gray length " + (tile.tileLength * TILE_HEIGHT - tile.lastClickPos));
						g.fillRect(tile.x, tile.y + tile.lastClickPos, TILE_WIDTH, tile.tileLength * TILE_HEIGHT - tile.lastClickPos);
						g.setColor(Color.BLACK);
						g.fillRect(tile.x, tile.y, TILE_WIDTH, tile.lastClickPos);
					}
					else{
						g.setColor(Color.GRAY);
						g.fillRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
						g.setColor(Color.black);
						g.drawRect(tile.x, tile.y, TILE_WIDTH, tile.tileLength * TILE_HEIGHT);
					}
				}
			}

			if(countStart >= -10){
				if(countStart <= 0){
					countStart--;
					g.setColor(Color.RED);
					g.setFont(new Font("Start!", Font.BOLD, 150));
					g.drawString("Start!", TILE_WIDTH, ROWS * TILE_HEIGHT / 2);
				}
				else{
					g.setColor(Color.RED);
					g.setFont(new Font(String.valueOf(countStart), Font.BOLD, 150));
					g.drawString(String.valueOf(countStart), TILE_WIDTH * COLUMNS / 2, ROWS * TILE_HEIGHT / 2);
				}
				
			}

			g.setColor(Color.BLUE);
			g.setFont(new Font(String.valueOf(combo), Font.BOLD, 50));
			g.drawString("Combo" , COLUMNS * TILE_WIDTH + 80, 100);
			g.drawString(String.valueOf(combo), COLUMNS * TILE_WIDTH + 155, 160);
			g.setColor(Color.RED);
			int score_shift = 0;
			int cnt = Math.abs(score);
			while(cnt / 10 > 0){
				cnt = cnt / 10;
				score_shift++;
			}
			if(score < 0) score_shift = score_shift + 1;
			System.out.println("shift:" + score_shift);

			g.setFont(new Font(String.valueOf(score), Font.BOLD, 50));
			g.drawString("Score", COLUMNS * TILE_WIDTH + 100, 250);
			g.drawString(String.valueOf(score), COLUMNS * TILE_WIDTH + 155 - 16 * score_shift, 310);
			g.setColor(Color.RED);
			g.drawLine(0, TILE_HEIGHT * (ROWS - 1), TILE_WIDTH * COLUMNS, TILE_HEIGHT * (ROWS - 1));

		}
		else
		{
			g.setColor(Color.BLACK);
			g.drawString("Game Over!", 100, TILE_HEIGHT - 150);
			String DataName =  "data/" + song_Name + "_" + speed_String + "_" + COLUMNS + ".txt";
			if(!write) {
				write = true;
				FileWriter fw = null;

				try {
					fw = new FileWriter(DataName, true);
					fw.append(this.username + " " + String.valueOf(score) + "\r\n");
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			g.setFont(new Font(String.valueOf(score), Font.BOLD, 60));
			g.drawString("Scoreboard" , 100, TILE_HEIGHT);
			g.setFont(new Font(String.valueOf(score), Font.BOLD, 20));
			g.drawString("Username" , 100, TILE_HEIGHT + 100);
			g.drawString("Score" , 450, TILE_HEIGHT + 100);
			g.setColor(Color.DARK_GRAY);
			try {
				ArrayList<Player> scores = new ArrayList<>();
				FileReader fr = new FileReader(DataName);
				BufferedReader br = new BufferedReader(fr);
				int num = 0;
				while (br.ready()) {
					String each_data = br.readLine();
					num++;
					//System.out.println(Integer.parseInt(each_data.split(" ")[1]));
					scores.add(new Player(each_data.split(" ")[0], Integer.parseInt(each_data.split(" ")[1])));
				}
				Collections.sort(scores);
				int height = TILE_HEIGHT + 130;
				for(int i = 0; i < Math.min(5,num) ; i++){
					String now_name = scores.get(i).name;
					String score = String.valueOf(scores.get(i).score);
					g.drawString(now_name, 100, height);
					g.drawString(score, 450, height);
					height += 20;
				}
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}





		}
	}

	public static void main(String[] args)
	{
		ComponentInWindow win = new ComponentInWindow();
        win.setBounds(100, 100, 370, 400);
        win.setTitle("Set up before game!");
		// dttwt = new DontTouchTheWhiteTile();
		// dttwt.music.run();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{

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
	public synchronized void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		pressedKeys.add(e.getKeyCode());
		int keyCode = e.getKeyCode();

		System.out.println(pressedKeys.size());
		if (!keyCodeToX.containsKey(keyCode)) {
			return;
		}
		System.out.println("aaaa");

	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
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
				if (tile.pointInTile(x, y) && !tile.released) {
					// System.out.println("fuck2 " + tile.x + " " + tile.y + " " + tile.black);
					if (tile.black) {
						tile.setReleased(true);
						milSecDelay = 0;
						combo += 1;
					}
				}
			}
		}
		else start();
		// System.out.println("________________");
		// TODO Auto-generated method stub
		System.out.println("bbbb");
	}
	public void KeepTrackTheKeyboard(){
		if (!pressedKeys.isEmpty()) {
			for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
				int a = it.next();

				if (!keyCodeToX.containsKey(a)) {
					continue;
				}
				int x = keyCodeToX.get(a);
				int y = TILE_HEIGHT * (ROWS - 1);
				if (!gameOver) {
					for (int i = 0; i < tiles.size(); i++) {
						Tile tile = tiles.get(i);
						// System.out.println(tile.pointInTile(x, y));
						if (tile.pointInTile(x, y) && !tile.released) {
							// System.out.println("fuck2 " + tile.x + " " + tile.y + " " + tile.black);
							if (tile.black) {
								if (tile.tileLength == 1) {
									tile.setClicked(true);
									tile.setReleased(true);
									it.remove();
									score += (100 + 10 * combo);
									System.out.println("You've scored " + (100 + 10 * combo) + " points!");
									milSecDelay = 0;
									combo += 1;
								} else {
									if(!tile.clicked){
										System.out.println("long tile in");
										tile.setClicked(true);
										tile.lastClickPos = y - tile.y;
										System.out.println("long tile lastClickPos = " + tile.lastClickPos);
									}
									score += (10 + 10 * combo);
									System.out.println("You've scored " + (10 + 10 * combo) + " points!");
									milSecDelay = 0;
								}
							} else {
								score -= 200;
								it.remove();
								combo = 0;
							}
						}
					}
				}
			}
		}
	}
}