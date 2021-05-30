package game.logic;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

import static game.logic.Tile.TILE_SIZE;

public class GameField extends StackPane {

    private final Tile[][] cells;
    private final int width;
    private final int height;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;

        cells = new Tile[width][height];

        int emptyX = width - 2;
        int emptyY = height - 1;

        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 1; i < 16; i++) numbers.add(Integer.toString(i));
        Collections.shuffle(numbers);

        int counter = 0;

        int size = width * height;
        for (int i = 0; i < size; i++) {
            int x = i % width;
            int y = i / height;

            if (isExtracted(emptyX, emptyY, x, y)) continue;

            counter = getCounter(numbers, counter, x, y);
        }

        checkWin();
    }

    private int getCounter(ArrayList<String> numbers, int counter, int x, int y) {
        String number = numbers.get(counter);
        Tile tile = new Tile(number);
        counter++;

        tile.setPosition(x, y);
        cells[x][y] = tile;

        getChildren().add(tile);
        return counter;
    }

    private boolean isExtracted(int emptyX, int emptyY, int x, int y) {
        return extracted(emptyX, emptyY, x, y);
    }

    private boolean extracted(int emptyX, int emptyY, int x, int y) {
        if (x == emptyX && y == emptyY) {
            cells[x][y] = null;
            return true;
        }
        return false;
    }

    public void move(Tile tile) {
        int emptyX = -1;
        int emptyY = -1;

        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                if (cells[i][j] == null) {
                    emptyX = i;
                    emptyY = j;
                }

        if (isaBoolean(emptyX, emptyY)) return;

        if (isaBoolean(tile, emptyX, emptyY)) return;

        int shiftX = emptyX - tile.x;
        int shiftY = emptyY - tile.y;

        ArrayList<Tile> tiles = new ArrayList<>();

        int dirX;
        if (shiftX == 0) {
            dirX = 0;
        } else {
            dirX = shiftX / Math.abs(shiftX);
        }
        int dirY;
        if (shiftY == 0) {
            dirY = 0;
        } else {
            dirY = shiftY / Math.abs(shiftY);
        }
        int howMany;
        if (dirY == 0) {
            howMany = Math.abs(shiftX);
        } else {
            howMany = Math.abs(shiftY);
        }

        for (int i = 0; i != howMany; i++) {
            int x = i * dirX + tile.x;
            int y = i * dirY + tile.y;

            Tile temp = cells[x][y];

            tiles.add(0, temp);
        }

        MoveXY(tiles, dirX, dirY);
    }

    private boolean isaBoolean(Tile tile, int emptyX, int emptyY) {
        return emptyX != tile.x && emptyY != tile.y;
    }

    private boolean isaBoolean(int emptyX, int emptyY) {
        return emptyX == -1 || emptyY == -1;
    }

    private void MoveXY(ArrayList<Tile> tiles, int dirX, int dirY) {
        int count = tiles.size();
        int counter = 0;

        for (Tile t : tiles) {
            int prevX = t.x;
            int newX = t.x + dirX;

            int prevY = t.y;
            int newY = t.y + dirY;

            TranslateTransition anim = new TranslateTransition();
            anim.setNode(t);
            anim.setToX(newX * TILE_SIZE);
            anim.setToY(newY * TILE_SIZE);
            anim.setInterpolator(Interpolator.LINEAR);
            anim.setDuration(new Duration(70));
            anim.play();

            int finalCounter = ++counter;

            anim.setOnFinished(e -> {
                t.setPosition(newX, newY);
                cells[prevX][prevY] = null;
                cells[newX][newY] = t;
                if (count == finalCounter) checkWin();
            });
        }
    }

    public void checkWin() {
        int counter = 1;

        int size = width * height;
        for (int i = 0; i < size; i++) {
            int x = i % width;
            int y = i / height;
            Tile t = cells[x][y];

            if (t == null) {
                extracted(size, i);
                break;
            } else {
                int value = Integer.parseInt(t.getText());
                if (value != counter) {
                    t.setColor(Color.RED);
                    break;
                } else {
                    t.setColor(Color.GREEN);
                    if (counter != (size - 1)) counter++;
                    else {
                        extracted();
                        break;
                    }
                }
            }
        }
    }

    private void extracted() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(400);
        dialog.setTitle("Победа!");
        dialogVbox.getChildren().add(new Text("Поздравляем! Вы выиграли!"));
        Scene dialogScene = new Scene(dialogVbox, 200, 50);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void extracted(int size, int i) {
        for (int j = i; j < size; j++) {
            int xt = j % width;
            int yt = j / height;
            Tile tl = cells[xt][yt];
            if (tl != null) tl.setColor(Color.RED);
        }
    }
}