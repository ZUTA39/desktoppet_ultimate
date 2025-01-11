package local.desktoppet.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import local.desktoppet.MainApp;

/**
 * 菜单总体控制类
 * 
 * @author 2313040111
 * @version 2.0
 */

public class FuncMenuController {
    @FXML
    private Label currentPetname;
    @FXML
    private Label favorability;
    @FXML
    private Label currentMusic;

    private MainApp mainApp;
    private Stage petStage;
    private PetController petController;
    private MediaPlayer musicPlayer;
    private List<String> musicFiles;
    private final String[] musicName = { "蓝色多瑙河 - Johann Strauss II", "小夜曲 - Franz Schubert" };
    private int currentMusicIndex = 0;

    @FXML
    private void initialize() {
        musicFiles = new ArrayList<>();
        for (String element : musicName) {
            musicFiles
                    .add("src/main/resources/local/desktoppet/music/" + element + ".mp3");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setPetStage(Stage petStage) {
        this.petStage = petStage;
    }

    public PetController getPetController() {
        return petController;
    }

    public void setPetController(PetController petController) {
        this.petController = petController;
    }

    @FXML
    private void handleAccREcorder() {
        mainApp.showAccRecorder();
    }

    @FXML
    private void handleTakePet() {
        mainApp.showPet("年");
        currentPetname.setText("年");
    }

    @FXML
    private void handleBye() {
        if (petController != null) {
            petController.showGoodbyeImage();
        }

        // 延迟2秒后关闭窗口
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if (petStage != null) {
                petStage.close();
            }
        }));
        timeline.play();
    }

    @FXML
    private void handleSwitch() {
        if (petStage != null) {
            petStage.close();
        }
        mainApp.showPet("夕");
        currentPetname.setText("夕");

    }

    @FXML
    private void handleMusicPlay() {
        handleStopMusic();
        currentMusicIndex = (currentMusicIndex + 1) % musicFiles.size();
        playMusic(musicFiles.get(currentMusicIndex));
    }

    private void playMusic(String musicFile) {
        File file = new File(musicFile);
        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            musicPlayer = new MediaPlayer(media);
            musicPlayer.play();
            currentMusic.setText("正在播放: " + file.getName());
        } else {
            currentMusic.setText("文件不存在: " + file.getName());
        }
    }

    @FXML
    private void handleStopMusic() {
        if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            musicPlayer.stop();
            currentMusic.setText("未播放");
        }
    }

    @FXML
    private void handleFeedPet() {
        mainApp.showPetFood();
    }

    public void updateFavorabilityLabel(int favorability) {
        this.favorability.setText(String.valueOf(favorability));
    }
}
