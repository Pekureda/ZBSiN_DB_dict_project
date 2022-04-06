package put.projdbdict.dbdictproj;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class MainWindowController extends Stage{
    static Stage stage;
    public TextArea usageTextBox;
    public CheckBox usageCheckBox;
    public TextField addKanjiCharacterTextBox;
    public TextField addKanjiReadingTextBox;
    public TextField addKanjiMeaningTextBox;
    public Tab addKanjiTab;
    public Tab addUsageTab;
    public Tab addAdditionalNotesTab;
    public TabPane mainWindowTabPane;
    public TextField addUsageCharacterTextBox;
    public TextArea addUsageUsageTextBox;
    public ChoiceBox addAdditionalNotesTypeChoiceBox;
    public TextField addAdditionalNotesCharacterTextBox;
    public TextArea addAdditionalNotesAdditionalNotesTextBox;
    Stage DBStage;
    Stage FlashcardStage;

    @FXML
    private void initialize() {
        usageTextBox.disableProperty().set(true);
        addAdditionalNotesTypeChoiceBox.getItems().add("Common mistake");
        addAdditionalNotesTypeChoiceBox.getItems().add("Trivia");
        addAdditionalNotesTypeChoiceBox.getItems().add("Exception");

        stage.setResizable(false);
    }

    @FXML
    protected void onExitButtonClick() {
        DBUtil.obj.close();
        Platform.exit();
    }

    public void onAcceptButtonClick(ActionEvent actionEvent) {
        SingleSelectionModel<Tab> selectionModel = mainWindowTabPane.getSelectionModel();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);

        switch (selectionModel.getSelectedIndex()) {
            case 0:
                if (addKanjiCharacterTextBox.getText().matches("^[\\p{script=Han}]$")) {
                    String[] readings = addKanjiReadingTextBox.getText().split("\\p{javaWhitespace}*,\\p{javaWhitespace}*");
                    String[] meanings = addKanjiMeaningTextBox.getText().split("\\p{javaWhitespace}*,\\p{javaWhitespace}*");
                    if (! Arrays.stream(readings).allMatch(elem -> elem.matches("[\\p{Alpha}\\p{javaWhitespace}]+")) || ! Arrays.stream(meanings).allMatch(elem -> elem.matches("[\\p{Alpha}\\p{javaWhitespace}]+"))) {
                        alert.setContentText("At least 1 reading/meaning must be added.\nReadings/meanings must be separated by commas.");
                        alert.showAndWait();
                        return;
                    }
                    CallableStatement readingStmt = DBUtil.prepareCallProcedure("IntReading.addReading", 2);
                    CallableStatement meaningStmt = DBUtil.prepareCallProcedure("IntMeaning.addMeaning", 2);
                    CallableStatement kanjiStmt = DBUtil.prepareCallProcedure("IntKanji.addKanji", 1);
                    // Try adding kanji Character
                    try {
                        kanjiStmt.setString(1, addKanjiCharacterTextBox.getText());
                        kanjiStmt.execute();
                        kanjiStmt.close();
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 1) alert.setContentText("SQL Error: Entered character is already in the database.");
                        alert.showAndWait();
                        return;
                    }
                    // Try adding readings
                    try {
                        for (String elem : readings) {
                            readingStmt.setString(1, addKanjiCharacterTextBox.getText());
                            readingStmt.setString(2, elem);
                            readingStmt.addBatch();
                        }
                        readingStmt.executeBatch();
                        readingStmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    // Try adding meanings
                    try {
                        for (String elem : meanings) {
                            meaningStmt.setString(1, addKanjiCharacterTextBox.getText());
                            meaningStmt.setString(2, elem);
                            meaningStmt.addBatch();
                        }
                        meaningStmt.executeBatch();
                        meaningStmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (usageCheckBox.selectedProperty().get()) {
                        String[] usages = usageTextBox.getText().split("\\p{javaWhitespace}*,\\p{javaWhitespace}*");
                        CallableStatement usageStmt = DBUtil.prepareCallProcedure("IntUsage.addExample", 2);
                        try {
                            for (String elem : usages) {
                                usageStmt.setString(1, addKanjiCharacterTextBox.getText());
                                usageStmt.setString(2, elem);
                                usageStmt.addBatch();
                            }
                            usageStmt.executeBatch();
                            usageStmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    alert.setContentText("Character text box does not contain valid Kanji character.");
                    alert.showAndWait();
                }
                break;
            case 1:
                if (addUsageCharacterTextBox.getText().matches("^[\\p{script=Han}]$")) {
                    String[] examples = addUsageUsageTextBox.getText().split("\\p{javaWhitespace}*,\\p{javaWhitespace}*");
                    if (! Arrays.stream(examples).allMatch(elem -> elem.matches(".+"))) {
                        alert.setContentText("At least 1 example must be added.\nExamples must be separated by commas.");
                        alert.showAndWait();
                        return;
                    }
                    CallableStatement usageStmt = DBUtil.prepareCallProcedure("IntUsage.addExample", 2);
                    //CallableStatement kanjiStmt = DBUtil.prepareCallProcedure("IntKanji.addKanji", 1);
                    try {
                        for (String elem : examples) {
                            usageStmt.setString(1, addUsageCharacterTextBox.getText());
                            usageStmt.setString(2, elem);
                            usageStmt.addBatch();
                        }
                        usageStmt.executeBatch();
                        usageStmt.close();
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2291) {
                            alert.setContentText("SQL Error: Entered character does not exist.\nUse \"Add kanji\" tab to add new character.");
                            alert.showAndWait();
                        }
                        else e.printStackTrace();
                    }
                }
                else {
                    alert.setContentText("Character text box does not contain valid Kanji character.");
                    alert.showAndWait();
                }
                break;
            case 2:
                if (addAdditionalNotesCharacterTextBox.getText().matches("^[\\p{script=Han}]$")) {
                    if (addAdditionalNotesAdditionalNotesTextBox.getText().isEmpty()) {
                        alert.setContentText("Additional notes text box is empty.");
                        alert.showAndWait();
                        return;
                    }
                    if (addAdditionalNotesTypeChoiceBox.getValue() == null) {
                        alert.setContentText("Type is not selected.");
                        alert.showAndWait();
                        return;
                    }
                    CallableStatement additionalNotesStmt = DBUtil.prepareCallProcedure("IntAdditionalNotes.addNote", 3);

                    try {
                        additionalNotesStmt.setString(1, addAdditionalNotesCharacterTextBox.getText());
                        additionalNotesStmt.setString(2, (String)addAdditionalNotesTypeChoiceBox.getValue());
                        additionalNotesStmt.setString(3, addAdditionalNotesAdditionalNotesTextBox.getText());

                        additionalNotesStmt.execute();
                        additionalNotesStmt.close();
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2291) {
                            alert.setContentText("SQL Error: Entered character does not exist.\nUse \"Add kanji\" tab to add new character, then try again.");
                            alert.showAndWait();
                        }
                        else e.printStackTrace();
                    }

                }
                else {
                    alert.setContentText("Character text box does not contain valid Kanji character.");
                    alert.showAndWait();
                }
                break;
        }
    }

    public void onReset_tabButtonClick(ActionEvent actionEvent) {
        SingleSelectionModel<Tab> selectionModel = mainWindowTabPane.getSelectionModel();
        switch (selectionModel.getSelectedIndex()) {
            case 0:
                addKanjiReadingTextBox.clear();
                addKanjiMeaningTextBox.clear();
                addKanjiCharacterTextBox.clear();
                usageTextBox.clear();
                break;
            case 1:
                addUsageUsageTextBox.clear();
                addUsageCharacterTextBox.clear();
                break;
            case 2:
                addAdditionalNotesCharacterTextBox.clear();
                addAdditionalNotesAdditionalNotesTextBox.clear();
                break;
        }
    }

    public void onOpen_DBButtonClick(ActionEvent actionEvent) throws IOException {
        if (this.DBStage == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(DBApplication.class.getResource("DBWindow.fxml"));
            this.DBStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            this.DBStage.setTitle("DB window");
            this.DBStage.setScene(scene);
            this.DBStage.initOwner(stage);

            this.DBStage.show();
        }
        else if (this.DBStage.isShowing()) {
            this.DBStage.toFront();
        }
        else {
            this.DBStage.show();
        }
        DBWindowController.stage = DBStage;
    }

    public void onTest_yourselfButtonClick(ActionEvent actionEvent) throws IOException {
        if (this.FlashcardStage == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(DBApplication.class.getResource("StatsWindow.fxml"));
            this.FlashcardStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            this.FlashcardStage.setTitle("Stats window");
            this.FlashcardStage.setScene(scene);
            this.FlashcardStage.initOwner(stage);
            this.FlashcardStage.setResizable(false);
            this.FlashcardStage.show();
        }
        else if (this.FlashcardStage.isShowing()) {
            this.FlashcardStage.toFront();
        }
        else {
            this.FlashcardStage.show();
        }
    }

    public void onWindowMousePressed(MouseEvent mouseEvent) {
        stage.toFront();
    }

    public void onActionUsageCheckBox(ActionEvent actionEvent) {
        usageTextBox.disableProperty().bind(usageCheckBox.selectedProperty().not());
    }

}