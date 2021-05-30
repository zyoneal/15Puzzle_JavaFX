package game.logic;

import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    public static int TILE_SIZE;
    private final Text text;
    private final Rectangle rectangle;
    protected int x, y;

    public Tile(String number) {
        rectangle = new Rectangle(TILE_SIZE - 1, TILE_SIZE - 1);

        rectangle.setStroke(Color.BLACK);
        rectangle.setArcWidth(40);
        rectangle.setArcHeight(40);
        rectangle.setFill(Color.RED);
        rectangle.setStrokeWidth(1.5);

        setAlignment(Pos.CENTER);

        text = new Text();
        text.setText(number);
        text.setFont(Font.font(50));

        setOnMouseClicked(this::onClicked);

        getChildren().addAll(rectangle, text);
    }

    public void setColor(Color color) {
        this.rectangle.setFill(color);
    }

    private void onClicked(MouseEvent mouseEvent) {
        GameField field = (GameField) getParent();
        field.move(this);
    }

    public String getText() {
        return text.getText();
    }

    public void setPosition(int x, int y) {
        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);

        this.x = x;
        this.y = y;
    }
}
