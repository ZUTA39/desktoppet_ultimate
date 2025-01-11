package local.desktoppet.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import local.desktoppet.model.User;

/**
 * 注册页面总体控制类
 * 
 * @author 2313040111
 * @version 1.5
 */

public class RegisterPaneController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Stage registerStage;
    private User user;
    private boolean okClicked = false;

    /**
     * 初始化注册界面，这个函数会在fxml文件被加载后自动调用
     */
    @FXML
    private void initialize() {

    }

    /**
     * 为注册界面设置Stage
     * 
     * @param registerStage
     */
    public void setRegisterStage(Stage registerStage) {
        this.registerStage = registerStage;
    }

    /**
     * 为注册界面设置可编辑的User
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;

        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * 对确定键的响应函数
     */
    @FXML
    private void handleOK() {
        if (isInputValid()) {
            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getText());

            okClicked = true;
            saveUserDataToDatabase(user);
            registerStage.close();
        }
    }
    
    /**
     * 对返回键的响应
     */
    @FXML
    private void handleBack() {
        registerStage.close();
    }

    private boolean isInputValid() {
        String errorMsg = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMsg += "无效的用户名！！";
        }

        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMsg += "无效的密码！！";
        }

        if (errorMsg.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("无效项");
            alert.setHeaderText("请更正无效项！！");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false;
        }
    }

    private void saveUserDataToDatabase(User user) {
        String url = "jdbc:mysql://localhost:3306/pet_account_record";
        String username = "root"; // 替换为数据库用户名
        String password = "MySQLpassword"; // 替换为数据库密码

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("数据库错误");
            alert.setHeaderText("数据保存错误！！");
            alert.setContentText("无法保存数据到数据库");
            alert.showAndWait();
        }
    }
}
