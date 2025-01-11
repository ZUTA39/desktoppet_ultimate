package local.desktoppet;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import local.desktoppet.model.AccountRecord;
import local.desktoppet.model.User;
import local.desktoppet.view.AccRecDialogController;
import local.desktoppet.view.AccRecStaticController;
import local.desktoppet.view.AccountRecordController;
import local.desktoppet.view.FuncMenuController;
import local.desktoppet.view.LoginRegiPaneController;
import local.desktoppet.view.PetController;
import local.desktoppet.view.PetFoodController;
import local.desktoppet.view.RegisterPaneController;

/**
 * 程序入口总体控制类
 * 
 * @author 2313040111
 * @version 2.5
 */

public class MainApp extends Application {
    private Stage primaryStage;
    private Stage registerStage;
    private Stage petStage;
    private Stage petFoodStage;
    private Stage dialogStage;

    private BorderPane rootLayout;
    private LoginRegiPaneController logRegiController;
    private RegisterPaneController regiController;
    private FuncMenuController menuController;
    private AccountRecordController accRecController;
    private AccRecDialogController accRecDiaController;
    private PetController petController;
    private PetFoodController petFoodController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("桌面宠物");
        this.primaryStage.getIcons().add(new Image("file:D:/CODE/code-java/desktoppet_ultimate/src/main/resources/local/desktoppet/img/icon.jpg"));

        initRootLayout();
        showLoginRegiPane();
    }

    /**
     * 初始化根界面的函数
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 显示场景（Scene）
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在根界面上展示登录注册界面的函数
     */
    public void showLoginRegiPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/LoginRegiPane.fxml"));
            AnchorPane loginRegiPane = (AnchorPane) loader.load();

            // 在根界面中央展示
            rootLayout.setCenter(loginRegiPane);

            logRegiController = loader.getController();
            logRegiController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showRegisterPane(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/RegisterPane.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建注册页面的Stage，因为这里要搞弹窗
            registerStage = new Stage();
            registerStage.setTitle("注册");
            // 设置为模态窗口（不可以点击上一个页面）
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            registerStage.setScene(scene);

            regiController = loader.getController();
            regiController.setRegisterStage(registerStage);
            regiController.setUser(user);

            registerStage.showAndWait();

            return regiController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showFuncMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/FuncMenu.fxml"));
            AnchorPane funcMenu = (AnchorPane) loader.load();

            rootLayout.setCenter(funcMenu);

            menuController = loader.getController();
            menuController.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAccRecorder() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/AccountRecord.fxml"));
            AnchorPane accRecorder = (AnchorPane) loader.load();

            rootLayout.setCenter(accRecorder);
            accRecController = loader.getController();
            accRecController.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAccRecDialog(AccountRecord record, String sql) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/local/desktoppet/view/AccRecordEditDailog.fxml"));
            AnchorPane accRecDialog = (AnchorPane) loader.load();

            rootLayout.setCenter(accRecDialog);
            accRecDiaController = loader.getController();
            accRecDiaController.setRecord(record);
            accRecDiaController.setSql(sql);
            accRecDiaController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPet(String petname) {
        try {
            FXMLLoader petLoader = new FXMLLoader();
            petLoader.setLocation(this.getClass().getResource("/local/desktoppet/view/Pet.fxml"));
            AnchorPane nian = (AnchorPane) petLoader.load();

            petStage = new Stage();
            petStage.initOwner(primaryStage);

            petStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(nian);
            scene.setFill(Color.TRANSPARENT);

            petStage.setScene(scene);

            petController = petLoader.getController();
            petController.setPetStage(petStage);
            petController.setPetname(petname);
            petController.setPetImage();
            petController.setFuncMenuController(menuController);

            petStage.show();

            menuController.setPetStage(petStage);
            menuController.setPetController(petController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPetFood() {
        try {
            FXMLLoader petFoodloader = new FXMLLoader();
            petFoodloader.setLocation(this.getClass().getResource("/local/desktoppet/view/PetFood.fxml"));
            AnchorPane food = (AnchorPane) petFoodloader.load();

            petFoodStage = new Stage();
            petFoodStage.initOwner(primaryStage);

            petFoodStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(food);
            scene.setFill(Color.TRANSPARENT);

            petFoodStage.setScene(scene);

            petFoodController = petFoodloader.getController();
            petFoodController.setFoodStage(petFoodStage);
            petFoodController.setFoodImage();
            petFoodController.setPetController(petController);
            petFoodController.setPetStage(petStage);
            petFoodController.setFuncMenuController(menuController);

            petFoodStage.show();

            petFoodController.checkFoodCollision();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccRecStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/local/desktoppet/view/AccRecStatic.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            dialogStage = new Stage();
            dialogStage.setTitle("账目统计");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AccRecStaticController controller = loader.getController();
            controller.setAccountRecordController(accRecController);
            controller.setAccData();

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
