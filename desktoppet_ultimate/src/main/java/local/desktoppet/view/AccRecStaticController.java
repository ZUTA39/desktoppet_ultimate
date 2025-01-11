package local.desktoppet.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import local.desktoppet.model.AccountRecord;

/**
 * 数据统计控制类
 * 
 * @author 2313040111
 * @version 1.5
 */

public class AccRecStaticController {
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    private AccountRecordController accountRecordController;

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.CHINESE).getMonths();
        monthNames.addAll(Arrays.asList(months));

        xAxis.setCategories(monthNames);

        barChart.setLegendSide(Side.TOP);
    }

    public void setAccountRecordController(AccountRecordController accountRecordController) {
        this.accountRecordController = accountRecordController;
    }

    public void setAccData() {
        ObservableList<AccountRecord> records = accountRecordController.getAccData();
        double[] monthIncome = new double[12];
        double[] monthOutcome = new double[12];
        for (AccountRecord rec : records) {
            int month = rec.getDate().getMonthValue() - 1;
            monthIncome[month] += rec.getIncome();
            monthOutcome[month] += rec.getOutcome();
        }

        XYChart.Series<String, Double> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("收入");

        XYChart.Series<String, Double> outcomeSeries = new XYChart.Series<>();
        outcomeSeries.setName("支出");

        for (int i = 0; i < 12; i++) {
            incomeSeries.getData().add(new XYChart.Data<>(monthNames.get(i), monthIncome[i]));
            outcomeSeries.getData().add(new XYChart.Data<>(monthNames.get(i), monthOutcome[i]));
        }

        ObservableList<XYChart.Series<String, Double>> seriesList = FXCollections.observableArrayList();
        seriesList.add(incomeSeries);
        seriesList.add(outcomeSeries);

        barChart.getData().addAll(seriesList);
    }
}
