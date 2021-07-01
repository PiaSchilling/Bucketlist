package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.StatisticListsBox;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticController implements Listener, Initializable {


    private final ListManager listManager;
    private final StatisticsManager statisticManager;
    private final BorderPane borderPane;

    private Category category;

    private static final Logger log = LogManager.getLogger(StatisticController.class);


    public StatisticController(ListManager listManager, BorderPane borderPane) {
        this.listManager = listManager;
        this.borderPane = borderPane;
        this.statisticManager = new StatisticsManager(listManager);

        this.listManager.addListener(this);
    }


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label listsCreatedLabel;

    @FXML
    private Label eventsCompletedLabel;

    @FXML
    private Label listCountEvents;

    @FXML
    private PieChart pieChart;


    /**
     * sets the text to show how many LISTS the user already CREATED
     */
    public void setCreatedListsLabel() {
        log.debug("setCreatedListsLabel() method started");
        this.listsCreatedLabel.setText(statisticManager.countListsAsString());
    }


    /**
     * sets the text to show how many EVENTS the user already COMPLETED
     */
    public void setEventsCompletedLabel() {
        log.debug("setEventsCompletedLabel() method started");
        this.eventsCompletedLabel.setText(statisticManager.countCompletedEventsAsString());
    }


    /**
     * sets the text to show how many EVENTS the user already CREATED
     */
    public void setCountEvents() {
        log.debug("setCountEvents() method started");
        this.listCountEvents.setText(statisticManager.countEventsAString());
    }


    /**
     * sets Pie Chart and displays how many EVENTS the user CREATED in the available categories
     */
    public void setPieChart() {
        log.debug("setPieChart() method started");
        CategoryManager categoryManager = new CategoryManager(this.listManager);

        // Pie Chart gets filled with Data which hasn't got the value 0
        HashMap<Category, Categorylist> map = categoryManager.getFilledCatgeoryLists();
        for (Map.Entry<Category, Categorylist> entry : map.entrySet()) {
            if (entry.getValue().getEvents().size() != 0) {
                this.pieChart.getData().add(new PieChart.Data(entry.getValue().getListCategory().toString(), entry.getValue().getEvents().size()));
            }
        }
        //Styling
        this.pieChart.setLegendVisible(false);
        this.pieChart.setStartAngle(90);
        this.pieChart.setLabelLineLength(30);


        this.pieChart.getData().forEach(data -> {
            // shows the correct formatted percentage values as String
            String percentage = String.format("%.2f%%", (data.getPieValue() / statisticManager.countEvents()) * 100);
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
    }


    /**
     * shows Statistics
     */
    public void showStatistics() {
        log.debug("showStatistics() has started ");
        this.flowPane.getChildren().clear();

        setCreatedListsLabel();
        setEventsCompletedLabel();
        setCountEvents();
        log.debug("Labels set");

        setPieChart();
        log.debug("Pie Chart set");


        for (int i = 0; i < this.listManager.getEventlists().size(); i++) {

            String eventlistname = this.listManager.getEventlists().get(i).getName();
            StatisticListsBox statisticListsBox = new StatisticListsBox(this.listManager, this.borderPane);
            statisticListsBox.getStatisticListsController().setEventlistNameLabel(eventlistname);

            this.flowPane.getChildren().add(statisticListsBox);

            this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        log.debug("lists set");
        log.debug("showStatistics() ended ");
    }

    /**
     * updates the statistic scene if any changes occurs
     */
    @Override
    public void update() {
        log.debug("showStatistics() in update() has started ");
        showStatistics();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("showStatistics() in initialize() has started ");
        showStatistics();
    }


}
