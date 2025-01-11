package local.desktoppet.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import local.desktoppet.MainApp;
import local.desktoppet.model.AccountRecord;

/**
 * 记账本总体控制类
 * 
 * @author 2313040111
 * @version 2.0
 */

public class AccountRecordController {
    @FXML
    private TableView<AccountRecord> accTable;
    @FXML
    private TableColumn<AccountRecord, LocalDate> dateColumn;
    @FXML
    private TableColumn<AccountRecord, String> usernameColumn;

    @FXML
    private Label usernamLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label outcomeLabel;

    private MainApp mainApp;
    private ObservableList<AccountRecord> accData = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        accTable.setItems(accData);
    }

    @FXML
    public void initialize() {
        loadAccData();

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        showAccRecordDetails(null);

        accTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue, newValue) -> showAccRecordDetails(newValue));
    }

    @FXML
    private void handleNew() {
        AccountRecord tempRec = new AccountRecord();
        String temSql = "INSERT INTO account (income, outcome, username, date) VALUES (?, ?, ?, ?)";
        mainApp.showAccRecDialog(tempRec, temSql);
    }

    @FXML
    private void handleEdit() {
        AccountRecord selectedRecord = accTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            String temSql = "UPDATE account SET income = ?, outcome = ? WHERE username = ? AND date = ?";
            mainApp.showAccRecDialog(selectedRecord, temSql);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("加载错误");
            alert.setHeaderText("未选中任何记录");
            alert.setContentText("请选择一条记录！");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete() {
        AccountRecord selectedRecord = accTable.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            accTable.getItems().remove(selectedRecord);

            String url = "jdbc:mysql://localhost:3306/pet_account_record";
            String sqlusername = "root";
            String sqlpassword = "MySQLpassword";

            try (Connection conn = DriverManager.getConnection(url, sqlusername, sqlpassword)) {
                String sql = "DELETE FROM account WHERE username = ? AND date = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, selectedRecord.getUsername());
                pstmt.setString(2, selectedRecord.getDate().toString());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("删除错误");
            alert.setHeaderText("数据删除错误！！");
            alert.setContentText("无法从数据库中删除数据");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleBack() {
        mainApp.showFuncMenu();
    }

    private void loadAccData() {
        String url = "jdbc:mysql://localhost:3306/pet_account_record";
        String sqlusername = "root";
        String sqlpassword = "MySQLpassword";

        try (Connection conn = DriverManager.getConnection(url, sqlusername, sqlpassword)) {
            String sql = "SELECT * FROM account";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                LocalDate date = rs.getDate("date").toLocalDate();
                double income = rs.getDouble("income");
                double outcome = rs.getDouble("outcome");

                AccountRecord record = new AccountRecord(username, date, income, outcome);
                accData.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("加载错误");
            alert.setHeaderText("数据加载错误！！");
            alert.setContentText("无法从数据库中加载数据");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleShowAccRecStatistics() {
        mainApp.showAccRecStatistics();
    }

    private void showAccRecordDetails(AccountRecord record) {
        if (record != null) {
            usernamLabel.setText(record.getUsername());
            dateLabel.setText(record.getDate().toString());
            incomeLabel.setText(Double.toString(record.getIncome()));
            outcomeLabel.setText(Double.toString(record.getOutcome()));
        } else {
            usernamLabel.setText("null");
            dateLabel.setText("null");
            incomeLabel.setText("null");
            outcomeLabel.setText("null");
        }
    }

    public ObservableList<AccountRecord> getAccData() {
        return accData;
    }

}
