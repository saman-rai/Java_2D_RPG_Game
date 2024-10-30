package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

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
	
			setup(0,"grass",false);
			setup(1,"wall",true);
			setup(2,"water",true);
			setup(3,"earth",false);
			setup(4,"tree",true);
			setup(5,"sand",false);
			
		
	}
	public void setup(int index, String imgName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tile/"+imgName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}
		catch(IOException e){
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
		for(int worldRow=0; worldRow<gp.maxWorldRow;worldRow++) {			
			for(int worldCol=0; worldCol<gp.maxWorldCol;worldCol++) {
				int tileNum = mapTileNum[worldCol][worldRow];
				
				int worldX=worldCol*gp.tileSize;
				int worldY = worldRow*gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				if(screenX>=(gp.tileSize*-1)&&screenX<gp.screenWidth&&screenY>=(gp.tileSize*-1)&&screenY<gp.screenHeight) { // optimizing for only drawing visible tiles
					
						g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				
				}
			}
		}
	}
}
