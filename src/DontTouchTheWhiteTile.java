package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import java.io.File;

public class DontTouchTheWhiteTile implements ActionListener, KeyListener
{

	public final static int ROWS = 3, TILE_WIDTH = 250, TILE_HEIGHT = 300;

	public int COLUMNS = 3;

	public final static int[] velocity = {30, 15, 5, 3};

	public int[] keyCodeList = {83, 68, 70, 32, 74, 75, 76, 82};
	public Map<Integer, Integer> keyCodeToX = new HashMap<Integer, Integer>();
	
	public static DontTouchTheWhiteTile dttwt;

	public ArrayList<Tile> tiles;

	public Renderer renderer;

	public Random random;

	public int score, milSecDelay;

	public boolean gameOver;

	public String song_Name;

	public boolean hasSpecialTile = false;

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
			new Music("Fur Elise", "./music/garbage.mid", 20, 92.6F),
			new Music("Ballade pour Adeline", "./music/water.mid", 20, 92F)
			};



	public DontTouchTheWhiteTile(String username, int column, String speed, String songName)
	{
		if (username.equals("")) this.username = "player";
		else this.username = username;
		if (column == 0) this.COLUMNS = 3;
		else this.COLUMNS = column;
		if (songName == null) this.song_Name = "Canon";
		else this.song_Name = songName;
		this.speed_String = speed;
		if (speed_String == null) {
			speed_String = "slow";
		}
		if (speed == null) {
			this.speed = velocity[0];
		} else if (speed.equals("slow")) {
			this.speed = velocity[0];
		} else if (speed.equals("fast")) {
			this.speed = velocity[1];
		} else if (speed.equals("super fast")) {
			this.speed = velocity[2];
		} else if (speed.equals("hell mode")) {
			this.speed = velocity[3];
		} else {
			this.speed = velocity[0];
		}
		if (this.COLUMNS == 3) {
			keyCodeList = new int[] {70, 32, 74, 82};
		} else if (this.COLUMNS == 4) {
			keyCodeList = new int[] {68, 70, 74, 75, 82};
		}
		for (int i = 0; i <= COLUMNS; i++) {
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
		} catch (InterruptedException err) {
			err.printStackTrace();
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
				
				tile.setLastClickPos(tile.lastClickPos - TILE_HEIGHT / speed);
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
			if(tile.y == TILE_HEIGHT * ROWS) {
				if (tile.black && !tile.clicked) combo = 0;
				if (tile.isSpecialTile()) hasSpecialTile = false;
				tiles.remove(i);
				getNewTile = true;
				i--;
			}
		}

		if(getNewTile){
			boolean createLongTail = (random.nextInt(10) == 0);
			if(!hasSpecialTile && createLongTail){
				longTileColumn = random.nextInt(COLUMNS);
				longTileRound = random.nextInt(3) + 5;
				LongTile newTile = new LongTile(longTileColumn * TILE_WIDTH, -longTileRound * TILE_HEIGHT, longTileRound);
				longTileRound = (longTileRound + 1) * TILE_HEIGHT;
				tiles.add(newTile);
				hasSpecialTile = true;
				//System.out.println("long");
			}
			int blackTile = random.nextInt(COLUMNS);
			for(int j = 0; j < COLUMNS; j++){
				if(j == longTileColumn) continue;
				ShortTile newTile = new ShortTile(j * TILE_WIDTH, -TILE_HEIGHT, j == blackTile);
				tiles.add(newTile);
			}
		}
		KeepTrackTheKeyboard();
		if(!music.CheckRunning() || timescnt > 3150){
			music.stop();
			gameOver = true;
		}
	}

	public void render(Graphics g)
	{
		g.setFont(new Font("Arial", 1, 100));

		if (!gameOver)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS);

			for (Tile tile : tiles)
			{
				tile.renderTile(g, TILE_WIDTH, TILE_HEIGHT);
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

			g.setColor(Color.BLACK);
			g.setFont(new Font("Time", Font.BOLD, 50));
			g.drawString("Time",COLUMNS * TILE_WIDTH + 100, 100);
			if(timescnt > 150 && timescnt <= 3150){
				int nowTime = 63 - ((timescnt - 1) / 50);
				int dx = 0;
				if(timescnt <= 650) dx = 1;
				g.drawString(String.valueOf(nowTime), COLUMNS * TILE_WIDTH + 155 - 15 * dx, 160);
			}

			g.setColor(Color.BLUE);
			g.setFont(new Font(String.valueOf(combo), Font.BOLD, 50));
			g.drawString("Combo" , COLUMNS * TILE_WIDTH + 80, 250);
			g.drawString(String.valueOf(combo), COLUMNS * TILE_WIDTH + 155, 310);
			g.setColor(Color.RED);
			int score_shift = 0;
			int cnt = Math.abs(score);
			while(cnt / 10 > 0){
				cnt = cnt / 10;
				score_shift++;
			}
			if(score < 0) score_shift = score_shift + 1;
			//System.out.println("shift:" + score_shift);

			g.setFont(new Font(String.valueOf(score), Font.BOLD, 50));
			g.drawString("Score", COLUMNS * TILE_WIDTH + 100, 400);
			g.drawString(String.valueOf(score), COLUMNS * TILE_WIDTH + 155 - 16 * score_shift, 460);
			g.setColor(Color.RED);
			g.drawLine(0, TILE_HEIGHT * (ROWS - 1), TILE_WIDTH * COLUMNS, TILE_HEIGHT * (ROWS - 1));

		}
		else
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, TILE_WIDTH * COLUMNS + 350, TILE_HEIGHT * ROWS);
			g.setColor(Color.BLACK);
			g.drawString("Game Over!", 230, TILE_HEIGHT - 150);
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
			g.drawString("Scoreboard" , 230, TILE_HEIGHT);
			g.setFont(new Font(String.valueOf(score), Font.BOLD, 20));
			g.drawString("Username" , 230, TILE_HEIGHT + 100);
			g.drawString("Score" , 580, TILE_HEIGHT + 100);
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
					g.drawString(now_name, 230, height);
					g.drawString(score, 580, height);
					height += 20;
				}
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Press R to restart the game...", Font.BOLD, 30));
			g.drawString("Press R to restart game...", 320, (ROWS - 1) * TILE_HEIGHT + 100);
		}
	}

	public static void main(String[] args)
	{
		new File("./data").mkdirs();
		ComponentInWindow win = new ComponentInWindow();
        win.setBounds(100, 100, 370, 400);
        win.setTitle("Set up before game!");
		// dttwt = new DontTouchTheWhiteTile();
		// dttwt.music.run();
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
				if (tile.handleKeyReleased(x, y)) {
					milSecDelay = 0;
					combo += 1;
				}
				// System.out.println(tile.pointInTile(x, y));
				// if (tile.pointInTile(x, y) && !tile.released) {
				// 	// System.out.println("fuck2 " + tile.x + " " + tile.y + " " + tile.black);
				// 	if (tile.black) {
				// 		tile.setReleased(true);
				// 		milSecDelay = 0;
				// 		combo += 1;
				// 	}
				// }
			}
		}
		else{
			System.out.println("keyCode : " + keyCode);
			if(keyCode == 82){
				start();
			}
		}
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
						if (tile.pointInTile(x, y) && !tile.getReleased()) {
							// System.out.println("fuck2 " + tile.x + " " + tile.y + " " + tile.black);
							if (tile.black) {
								score += tile.handleScore(combo);
								if (tile.handleTracking(x, y, it)) combo += 1;
								// System.out.println("You've scored " + (100 + 10 * combo) + " points!");
								milSecDelay = 0;
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