import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int rows, int columns) {
        map = new int[rows][columns];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;

            }
        }
        brickWidth = 540 / columns;
        brickHeight = 150 / rows;

    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]>0){
                    graphics2D.setColor(Color.WHITE);
                    graphics2D.fillRect(j*brickWidth+80, i*brickHeight + 50, brickWidth,brickHeight);
                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j*brickWidth+80, i*brickHeight + 50, brickWidth,brickHeight);
                }

            }

        }
    }

    public void setBrickValue(int value, int row, int column){
        map[row][column]=value;
    }
}

