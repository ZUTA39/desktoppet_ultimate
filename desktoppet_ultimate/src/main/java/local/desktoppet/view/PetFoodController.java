package local.desktoppet.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 宠物食物控制类
 * 
 * @author 2313040111
 * @version 1.0
 */

public class PetFoodController {
    @FXML
    private AnchorPane foodPane;
    @FXML
    private ImageView imageView;

    private Stage foodStage;
    private Stage petStage;
    private FuncMenuController funcMenuController;
    private PetController petController;
    private double xOffset = 0;
    private double yOffset = 0;
    private String imagePath = "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/合成玉.png";

    @FXML
    private void initialize() {
        foodPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        checkFoodCollision();
    }

    public void setFoodImage() {
        imageView.setImage(new Image(imagePath));
    }

    public void setFoodStage(Stage foodStage) {
        this.foodStage = foodStage;
    }

    public void setPetStage(Stage petStage) {
        this.petStage = petStage;
    }

    public void setPetController(PetController petController) {
        this.petController = petController;
    }

    public void setFuncMenuController(FuncMenuController funcMenuController) {
        this.funcMenuController = funcMenuController;
    }

    public void closeFoodStage() {
        if (foodStage != null) {
            foodStage.close();
        }
    }

    public void checkFoodCollision() {
        foodPane.setOnMouseDragged(event -> {
            foodStage.setX(event.getScreenX() - xOffset);
            foodStage.setY(event.getScreenY() - yOffset);
            if (foodStage.getX() + 70 > petStage.getX() + 100 && foodStage.getX() < petStage.getX() + 200
                    && foodStage.getY() + 70 > petStage.getY() + 140 && foodStage.getY() < petStage.getY() + 185) {
                petController.increaseFavorability(5);
                if (funcMenuController != null) {
                    funcMenuController.updateFavorabilityLabel(petController.getFavorability());
                }
                foodStage.close();
            }
        });
    }
}
