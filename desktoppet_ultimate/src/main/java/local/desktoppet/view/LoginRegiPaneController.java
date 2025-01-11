package local.desktoppet.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import local.desktoppet.MainApp;
import local.desktoppet.model.User;

/**
 * 初始登录页面总体控制类
 * 
 * @author 2313040111
 * @version 1.5
 */

public class LoginRegiPaneController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private MainApp mainApp;

    @FXML
    private void handleRegister() {
        User user = new User();
        mainApp.showRegisterPane(user);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            mainApp.showFuncMenu();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText("用户名或密码错误");
            alert.setContentText("请检查您的用户名和密码，然后重试。");
            alert.showAndWait();
        }
    }

    private boolean isValidLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/pet_account_record";
        String dbUsername = "root"; // 替换为数据库用户名
        String dbPassword = "MySQLpassword"; // 替换为数据库密码

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("登录错误");
            alert.setHeaderText("用户名或密码错误！！");
            alert.setContentText("请重新输入！");
            alert.showAndWait();
            return false;
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
