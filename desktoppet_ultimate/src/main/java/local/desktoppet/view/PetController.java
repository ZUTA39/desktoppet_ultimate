package local.desktoppet.view;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 桌面宠物控制类
 * 
 * @author 2313040111
 * @version 1.5
 */

public class PetController {
    @FXML
    private AnchorPane petPane;
    @FXML
    private ImageView imageView;

    private FuncMenuController funcMenuController;

    private String petname;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage petStage;

    private List<String> imagePaths;
    private String dragImagePath;
    private String initialImagePath;
    private String goodbyeImagePath;
    private Random random = new Random();
    private int favorability = 0;

    @FXML
    public void initialize() {
        // 添加鼠标事件处理程序
        petPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        petPane.setOnMouseDragged(event -> {
            // 设置拖拽图片
            imageView.setImage(new Image(dragImagePath));
            petStage.setX(event.getScreenX() - xOffset);
            petStage.setY(event.getScreenY() - yOffset);

        });

        petPane.setOnMouseReleased(event -> {
            // 恢复初始图片
            imageView.setImage(new Image(initialImagePath));
        });

        petPane.setOnMouseClicked(event -> {
            switchImage();
            increaseFavorability(1);
            if (funcMenuController != null) {
                funcMenuController.updateFavorabilityLabel(favorability);
            }
        });

        // 添加拖放文件处理
        petPane.setOnDragOver(event -> {
            if (event.getGestureSource() != petPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        petPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    moveToTrash(file);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void setPetStage(Stage stage) {
        this.petStage = stage;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public void setPetImage() {
        this.imagePaths = Arrays.asList(
                "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/" + petname
                        + "-初始.gif",
                "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/" + petname
                        + "-戳1.gif",
                "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/" + petname
                        + "-戳2.gif");
        this.dragImagePath = "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/"
                + petname + "-拎起.gif";
        this.initialImagePath = "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/"
                + petname + "-初始.gif";
        this.goodbyeImagePath = "file:/D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/"
                + petname + "-睡了.gif";
        imageView.setImage(new Image(initialImagePath));

        petStage.setX(javafx.stage.Screen.getPrimary().getVisualBounds().getMaxX() - 200);
        petStage.setY(javafx.stage.Screen.getPrimary().getVisualBounds().getMaxY() - 200);
    }

    public String getPetname() {
        return petname;
    }

    public int getFavorability() {
        return favorability;
    }

    public void increaseFavorability(int num) {
        favorability += num;
    }

    public void setFuncMenuController(FuncMenuController funcMenuController) {
        this.funcMenuController = funcMenuController;
    }

    private void switchImage() {
        String randomImagePath = imagePaths.get(random.nextInt(imagePaths.size()));
        imageView.setImage(new Image(randomImagePath));
    }

    public void showGoodbyeImage() {
        imageView.setImage(new Image(goodbyeImagePath));
    }

    private void moveToTrash(File file) {
        try {
            // 使用 Windows Shell 命令将文件移动到特定文件夹
            String targetFolder = "C:\\Users\\ZUTA\\Desktop\\temp_documents";
            String cmd = "cmd /c move \"" + file.getAbsolutePath() + "\" \"" + targetFolder + "\"";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
