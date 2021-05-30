package game;

import game.logic.GameField;
import game.logic.Tile;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Fifteenth extends Application {
    private static final int WINDOW_WIDTH;
    private static final int WINDOW_HEIGHT;
    private static final int WIDTH;
    private static final int HEIGHT;

    static {
        WINDOW_WIDTH = 800;
        WINDOW_HEIGHT = 800;
        WIDTH = 4;
        HEIGHT = 4;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent create() {
        Pane root = new Pane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Tile.TILE_SIZE = WINDOW_WIDTH / WIDTH;
        GameField gameField = new GameField(WIDTH, HEIGHT);
        root.getChildren().add(gameField);
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Пятнашки");
        primaryStage.setScene(new Scene(create()));
        primaryStage.show();
    }
}
