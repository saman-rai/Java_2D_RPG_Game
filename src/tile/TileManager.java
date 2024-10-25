package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	public void getTileImage() {
		try {
			tile[0]=new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass.png"));
			
			tile[1]=new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));
			tile[1].collision = true;
			
			tile[2]=new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/water.png"));
			tile[2].collision = true;
			
			tile[3]=new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tile/earth.png"));
			
			tile[4]=new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tile/tree.png"));
			tile[4].collision = true;
			
			tile[5]=new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tile/sand.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for(int row =0; row<gp.maxWorldRow; row++) {
				String line = br.readLine();
				
				String numbers[]= line.split(" ");
				for(int col = 0; col<gp.maxWorldCol; col++) {
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row]= num;
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void draw(Graphics2D g2) {
//		int col = 0,row =0, x=0, y=0;
//		while (col<gp.maxScreenCol && row < gp.maxScreenRow) {
//			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
//			col++;
//			x+=gp.tileSize;
//			if(col==gp.maxScreenCol) {
//				col=0;
//				x=0;
//				row++;
//				y+=gp.tileSize;
//			}
//		}
		for(int worldRow=0; worldRow<gp.maxWorldRow;worldRow++) {			
			for(int worldCol=0; worldCol<gp.maxWorldCol;worldCol++) {
				int tileNum = mapTileNum[worldCol][worldRow];
				
				int worldX=worldCol*gp.tileSize;
				int worldY = worldRow*gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				if(screenX>=(gp.tileSize*-1)&&screenX<gp.screenWidth&&screenY>=(gp.tileSize*-1)&&screenY<gp.screenHeight) { // optimizing for only drawing visible tiles
					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
			}
		}
	}
}
