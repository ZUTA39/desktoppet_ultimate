package local.desktoppet.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import local.desktoppet.MainApp;
import local.desktoppet.model.AccountRecord;

/**
 * 新建与编辑弹窗总体控制类
 * 
 * @author 2313040111
 * @version 1.5
 */

public class AccRecDialogController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField incomeField;
    @FXML
    private TextField outcomeField;

    private AccountRecord record = new AccountRecord();
    private MainApp mainApp;
    private String sql = "";

    @FXML
    private void initialize() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setRecord(AccountRecord record) {
        this.record = record;

        usernameField.setText(record.getUsername());
        dateField.setText(record.getDate() != null ? record.getDate().toString() : "");
        incomeField.setText(Double.toString(record.getIncome()));
        outcomeField.setText(Double.toString(record.getOutcome()));
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            record.setUsername(usernameField.getText());
            record.setDate(LocalDate.parse(dateField.getText()));
            record.setIncome(Double.parseDouble(incomeField.getText()));
            record.setOutcome(Double.parseDouble(outcomeField.getText()));

            String url = "jdbc:mysql://localhost:3306/pet_account_record";
            String sqlusername = "root"; // 替换为数据库用户名
            String sqlpassword = "MySQLpassword"; // 替换为数据库密码

            try (Connection conn = DriverManager.getConnection(url, sqlusername, sqlpassword)) {
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, Double.toString(record.getIncome()));
                pstmt.setString(2, Double.toString(record.getOutcome()));
                pstmt.setString(3, record.getUsername());
                pstmt.setString(4, record.getDate().toString());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("数据库错误");
                alert.setHeaderText("数据保存错误！！");
                alert.setContentText("无法保存数据到数据库");
                alert.showAndWait();
            }

            mainApp.showAccRecorder();
        }

    }

    @FXML
    private void handleBack() {
        mainApp.showAccRecorder();
    }

    private boolean isInputValid() {
        String errormsg = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errormsg += "无效的用户名";
        }
        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errormsg += "无效的日期";
        } else if (!isValidDate(dateField.getText())) {
            errormsg += "无效的日期， 请将格式输入为yyyy-mm-dd";
        }

        if (errormsg.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("无效项");
            alert.setHeaderText("请更正无效项！！");
            alert.setContentText(errormsg);
            alert.showAndWait();
            return false;
        }

    }

    public static boolean isValidDate(String dateStr) {
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
