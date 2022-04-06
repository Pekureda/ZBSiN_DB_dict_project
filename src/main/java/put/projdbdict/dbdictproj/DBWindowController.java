package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class DBWindowController extends Stage{
    static Stage stage;
    // Reading tab
    public TableView readingTable;
    public TableColumn<Reading, String>     readingKanjiCharacterCol;
    public TableColumn<Reading, Integer>    readingIDCol;
    public TableColumn<Reading, String>     readingReadingCol;
    // Meaning tab
    public TableView meaningTable;
    public TableColumn<Meaning, String>     meaningKanjiCharacterCol;
    public TableColumn<Meaning, Integer>    meaningIDCol;
    public TableColumn<Meaning, String>     meaningMeaningCol;
    // Usage tab
    public TableView usageTable;
    public TableColumn<Usage, String>   usageKanjiCharacterCol;
    public TableColumn<Usage, Integer>  usageIDCol;
    public TableColumn<Usage, String>   usageExampleCol;
    // Additional notes tab
    public TableView additionalNotesTable;
    public TableColumn<AdditionalNotes, String>     AdditionalNotesKanjiCharacterCol;
    public TableColumn<AdditionalNotes, Integer>    AdditionalNotesIDCol;
    public TableColumn<AdditionalNotes, String>     AdditionalNotesTypeCol;
    public TableColumn<AdditionalNotes, Date>       AdditionalNotesDateAddedCol;
    public TableColumn<AdditionalNotes, String>     AdditionalNotesNoteCol;
    // Kanji tab
    public TableView kanjiTable;
    public TableColumn<Kanji, String>   kanjiCharacterCol;
    public TableColumn<Kanji, Date>     kanjiDate_addedCol;
    public TableColumn<Kanji, Integer>  kanjiProficiencyCol;
    public TableColumn<Kanji, Date>     kanjiLast_testedCol;
    public TableColumn<Kanji, Integer>  kanjiJLPT_classificationCol;
    public Button deleteButton;
    public TabPane DBWindowTabPane;
    public TextField filterTextBox;
    public Button filterButton;


    private Executor executor;
    private List<Service> tableViewRefreshList;

    @FXML
    private void initialize() throws SQLException {
        executor = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        System.out.println("Init ran");
        kanjiTable.setEditable(true);
        kanjiCharacterCol.setCellValueFactory(cellData -> cellData.getValue().kanjiCharacterProperty());
        kanjiDate_addedCol.setCellValueFactory(cellData -> cellData.getValue().kanjiDate_addedProperty());
        kanjiProficiencyCol.setCellValueFactory(cellData -> cellData.getValue().kanjiProficiencyProperty().asObject());
        kanjiLast_testedCol.setCellValueFactory(cellData -> cellData.getValue().kanjiLast_testedProperty());
        kanjiJLPT_classificationCol.setCellValueFactory(cellData -> cellData.getValue().kanjiJLPT_classificationProperty().asObject());
        kanjiJLPT_classificationCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return integer == null ? "" : integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                int retval;
                try {
                    retval = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Entered value is not an integer.");
                    alert.showAndWait();
                    return 0; // 0 is  out of range for jlpt
                }
                return (s == null) ? null : retval;
            }
        }));

        additionalNotesTable.setEditable(true);
        AdditionalNotesKanjiCharacterCol.setCellValueFactory(cellData -> cellData.getValue().kanjiCharacterProperty());
        AdditionalNotesIDCol.setCellValueFactory(cellData -> cellData.getValue().noteIdProperty().asObject());
        AdditionalNotesTypeCol.setCellValueFactory(cellData -> cellData.getValue().noteTypeProperty());
        //AdditionalNotesTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        AdditionalNotesDateAddedCol.setCellValueFactory(cellData -> cellData.getValue().noteDate_addedProperty());
        AdditionalNotesNoteCol.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        AdditionalNotesNoteCol.setCellFactory(TextFieldTableCell.forTableColumn());

        usageTable.setEditable(true);
        usageKanjiCharacterCol.setCellValueFactory(cellData -> cellData.getValue().kanjiCharacterProperty());
        usageIDCol.setCellValueFactory(cellData -> cellData.getValue().exampleIdProperty().asObject());
        usageExampleCol.setCellValueFactory(cellData -> cellData.getValue().usageExampleProperty());
        usageExampleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        meaningTable.setEditable(true);
        meaningKanjiCharacterCol.setCellValueFactory(cellData -> cellData.getValue().kanjiCharacterProperty());
        //meaningKanjiCharacterCol.setCellFactory(TextFieldTableCell.forTableColumn());
        meaningIDCol.setCellValueFactory(cellData -> cellData.getValue().meaningIdProperty().asObject());
        meaningMeaningCol.setCellValueFactory(cellData -> cellData.getValue().meaningMeaningProperty());
        meaningMeaningCol.setCellFactory(TextFieldTableCell.forTableColumn());

        readingTable.setEditable(true);
        readingKanjiCharacterCol.setCellValueFactory(cellData -> cellData.getValue().kanjiCharacterProperty());
        //readingKanjiCharacterCol.setCellFactory(TextFieldTableCell.forTableColumn());
        readingIDCol.setCellValueFactory(cellData -> cellData.getValue().readingIdProperty().asObject());
        readingReadingCol.setCellValueFactory(cellData -> cellData.getValue().readingReadingProperty());
        readingReadingCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Populate table
        Service<List<Kanji>> kanjiService = new Service<List<Kanji>>() {
            @Override
            protected Task<List<Kanji>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Kanji> call() throws Exception {
                        return KanjiDAO.getAllKanji();
                    }
                };
            }
        };

        Service<List<AdditionalNotes>> anService = new Service<>() {
            @Override
            protected Task<List<AdditionalNotes>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<AdditionalNotes> call() throws Exception {
                        return AdditionalNotesDAO.getAllAdditionalNotes();
                    }
                };
            }
        };

        Service<List<Usage>> usageService = new Service<>() {
            @Override
            protected Task<List<Usage>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Usage> call() throws Exception {
                        return UsageDAO.getAllUsage();
                    }
                };
            }
        };

        Service<List<Meaning>> meaningService = new Service<>() {
            @Override
            protected Task<List<Meaning>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Meaning> call() throws Exception {
                        return MeaningDAO.getAllMeanings();
                    }
                };
            }
        };

        Service<List<Reading>> readingService = new Service<>() {
            @Override
            protected Task<List<Reading>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Reading> call() throws Exception {
                        return ReadingDAO.getAllReadings();
                    }
                };
            }
        };

        kanjiService.setOnFailed(e -> kanjiService.getException().printStackTrace());
        kanjiService.setOnSucceeded(e -> kanjiTable.setItems((ObservableList<Kanji>) kanjiService.getValue()));

        anService.setOnFailed(e -> anService.getException().printStackTrace());
        anService.setOnSucceeded(e -> additionalNotesTable.setItems((ObservableList<AdditionalNotes>) anService.getValue()));

        usageService.setOnFailed(e -> usageService.getException().printStackTrace());
        usageService.setOnSucceeded(e -> usageTable.setItems((ObservableList<Usage>) usageService.getValue()));

        meaningService.setOnFailed(e -> meaningService.getException().printStackTrace());
        meaningService.setOnSucceeded(e -> meaningTable.setItems((ObservableList<Meaning>) meaningService.getValue()));

        readingService.setOnFailed(e -> readingService.getException().printStackTrace());
        readingService.setOnSucceeded(e -> readingTable.setItems((ObservableList<Reading>) readingService.getValue()));

        tableViewRefreshList = new ArrayList<Service>();
        tableViewRefreshList.add(kanjiService);
        tableViewRefreshList.add(anService);
        tableViewRefreshList.add(usageService);
        tableViewRefreshList.add(meaningService);
        tableViewRefreshList.add(readingService);

        onActionRefresh(null);
    }

    @FXML
    public void onActionRefresh(ActionEvent actionEvent) throws SQLException {
        for (Service service : tableViewRefreshList) {
            if (! service.getState().equals(Worker.State.RUNNING)) {
                service.reset();
                service.start();
            }
        }
    }

    public void onEditCommitJLPT(TableColumn.CellEditEvent<Kanji, Integer> kanjiIntegerCellEditEvent) throws SQLException {
        TablePosition pos = (TablePosition) kanjiTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Kanji kanji = (Kanji) kanjiTable.getItems().get(row);

        if (kanjiIntegerCellEditEvent.getNewValue() > 5 || kanjiIntegerCellEditEvent.getNewValue() < 1) {
            kanjiTable.getItems().set(row, kanji);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("JLPT classification must be an integer from [1; 5] range.");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        CallableStatement stmt = DBUtil.prepareCallProcedure("intkanji.updateJLPT", 2);
        stmt.setString(1, kanji.getKanjiCharacter());
        stmt.setInt(2, kanjiIntegerCellEditEvent.getNewValue());
        stmt.execute();

        kanji.setKanjiJLPT_classification(kanjiIntegerCellEditEvent.getNewValue());
        kanjiTable.getItems().set(row, kanji);
    }

    public void onEditCommitReading(TableColumn.CellEditEvent<Reading, String> readingStringCellEditEvent) throws SQLException {
        TablePosition pos = (TablePosition) readingTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Reading reading = (Reading) readingTable.getItems().get(row);
        CallableStatement stmt = DBUtil.prepareCallProcedure("intReading.updateReading", 2);
        stmt.setInt(1, reading.getReadingId());
        stmt.setString(2, readingStringCellEditEvent.getNewValue());
        stmt.execute();

        reading.setReadingReading(readingStringCellEditEvent.getNewValue());
        readingTable.getItems().set(row, reading);
        //ObservableList<Reading> readingData = ReadingDAO.getAllReadings();
        //readingTable.setItems(readingData);
    }

    public void onEditCommitMeaning(TableColumn.CellEditEvent<Meaning, String> meaningStringCellEditEvent) throws SQLException {
        TablePosition pos = (TablePosition) meaningTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Meaning meaning = (Meaning) meaningTable.getItems().get(row);
        CallableStatement stmt = DBUtil.prepareCallProcedure("intmeaning.updateMeaning", 2);
        stmt.setInt(1, meaning.getMeaningId());
        stmt.setString(2, meaningStringCellEditEvent.getNewValue());
        stmt.execute();

        meaning.setMeaningMeaning(meaningStringCellEditEvent.getNewValue());
        meaningTable.getItems().set(row, meaning);
    }

    public void onEditCommitType(TableColumn.CellEditEvent<AdditionalNotes, String> additionalNotesStringCellEditEvent) throws SQLException {
        TablePosition pos = (TablePosition) additionalNotesTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        AdditionalNotes an = (AdditionalNotes) additionalNotesTable.getItems().get(row);
        CallableStatement stmt = DBUtil.prepareCallProcedure("intadditionalnotes.updateNoteType", 2);
        stmt.setInt(1, an.getNoteId());
        stmt.setString(2, additionalNotesStringCellEditEvent.getNewValue());
        stmt.execute();

        an.setNoteType(additionalNotesStringCellEditEvent.getNewValue());
        additionalNotesTable.getItems().set(row, an);
    }

    public void onEditCommitNote(TableColumn.CellEditEvent<AdditionalNotes, String> additionalNotesStringCellEditEvent) throws SQLException {
        TablePosition pos = (TablePosition) additionalNotesTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        AdditionalNotes an = (AdditionalNotes) additionalNotesTable.getItems().get(row);
        CallableStatement stmt = DBUtil.prepareCallProcedure("intadditionalnotes.updateNote", 2);
        stmt.setInt(1, an.getNoteId());
        stmt.setString(2, additionalNotesStringCellEditEvent.getNewValue());
        stmt.execute();

        an.setNote(additionalNotesStringCellEditEvent.getNewValue());
        additionalNotesTable.getItems().set(row, an);
    }

    public void onActionDelete(ActionEvent actionEvent) throws SQLException {
        int selectedIndex;
        CallableStatement stmt;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        Optional<ButtonType> result;

        switch (DBWindowTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Kanji":
                alert.setContentText("Removing the character will remove all data associated with that character.");
                result = alert.showAndWait();
                if (result.get() == ButtonType.CANCEL) {
                    return;
                }
                selectedIndex = kanjiTable.getSelectionModel().getSelectedIndex();
                Kanji kanji = (Kanji) kanjiTable.getItems().get(selectedIndex);
                kanjiTable.getItems().remove(selectedIndex);
                stmt = DBUtil.prepareCallProcedure("intkanji.deleteByCharacter", 1);
                stmt.setString(1, kanji.getKanjiCharacter());
                stmt.execute();
                break;
            case "Reading":
                selectedIndex = readingTable.getSelectionModel().getSelectedIndex();
                Reading reading = (Reading) readingTable.getItems().get(selectedIndex);
                readingTable.getItems().remove(selectedIndex);
                stmt = DBUtil.prepareCallProcedure("intreading.deleteById", 1);
                stmt.setInt(1, reading.getReadingId());
                stmt.execute();
                break;
            case "Meaning":
                selectedIndex = meaningTable.getSelectionModel().getSelectedIndex();
                Meaning meaning = (Meaning) meaningTable.getItems().get(selectedIndex);
                meaningTable.getItems().remove(selectedIndex);
                stmt = DBUtil.prepareCallProcedure("intmeaning.deleteById", 1);
                stmt.setInt(1, meaning.getMeaningId());
                stmt.execute();
                break;
            case "Usage":
                selectedIndex = usageTable.getSelectionModel().getSelectedIndex();
                Usage usage = (Usage) usageTable.getItems().get(selectedIndex);
                usageTable.getItems().remove(selectedIndex);
                stmt = DBUtil.prepareCallProcedure("intusage.deleteById", 1);
                stmt.setInt(1, usage.getExampleId());
                stmt.execute();
                break;
            case "Additional notes":
                selectedIndex = additionalNotesTable.getSelectionModel().getSelectedIndex();
                AdditionalNotes additionalNotes = (AdditionalNotes) additionalNotesTable.getItems().get(selectedIndex);
                additionalNotesTable.getItems().remove(selectedIndex);
                stmt = DBUtil.prepareCallProcedure("intadditionalnotes.deleteById", 1);
                stmt.setInt(1, additionalNotes.getNoteId());
                stmt.execute();
                break;
        }
    }

    public void onMouseClickedTabPane(MouseEvent mouseEvent) {
//        if (kanjiTable.getSelectionModel().getSelectedCells().isEmpty() ||
//                readingTable.getSelectionModel().getSelectedCells().isEmpty() &&
//                meaningTable.getSelectionModel().getSelectedCells().isEmpty() &&
//                usageTable.getSelectionModel().getSelectedCells().isEmpty() &&
//                additionalNotesTable.getSelectionModel().getSelectedCells().isEmpty()) {
//            deleteButton.setDisable(true);
//        }
//        else {
//            deleteButton.setDisable(false);
//        }
    }

    public void onActionFilter(ActionEvent actionEvent) {

        switch (DBWindowTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Kanji" -> {
                FilteredList<Kanji> filteredKanji = new FilteredList<>((ObservableList<Kanji>) tableViewRefreshList.get(0).getValue());
                filteredKanji.setPredicate(new Predicate<Kanji>() {
                    @Override
                    public boolean test(Kanji kanji) {
                        return Objects.equals(kanji.getKanjiCharacter(), filterTextBox.getText()) || filterTextBox.getText().isEmpty();
                    }
                });
                ObservableList<Kanji> tempkanji = FXCollections.observableArrayList();
                tempkanji.setAll(filteredKanji);
                kanjiTable.setItems(tempkanji);
            }
            case "Reading" -> {
                FilteredList<Reading> filteredReading = new FilteredList<>((ObservableList<Reading>) tableViewRefreshList.get(4).getValue());
                filteredReading.setPredicate(new Predicate<Reading>() {
                    @Override
                    public boolean test(Reading reading) {
                        return String.valueOf(reading.getReadingId()).contains(filterTextBox.getText()) ||
                                reading.getReadingReading().contains(filterTextBox.getText()) ||
                                reading.getKanjiCharacter().contains(filterTextBox.getText()) ||
                                filterTextBox.getText().isEmpty();
                    }
                });
                ObservableList<Reading> tempread = FXCollections.observableArrayList();
                tempread.setAll(filteredReading);
                readingTable.setItems(tempread);
            }
            case "Meaning" -> {
                FilteredList<Meaning> filteredMeaning = new FilteredList<>((ObservableList<Meaning>) tableViewRefreshList.get(3).getValue());
                filteredMeaning.setPredicate(new Predicate<Meaning>() {
                    @Override
                    public boolean test(Meaning meaning) {
                        return String.valueOf(meaning.getMeaningId()).contains(filterTextBox.getText()) ||
                                meaning.getMeaningMeaning().contains(filterTextBox.getText()) ||
                                meaning.getKanjiCharacter().contains(filterTextBox.getText()) ||
                                filterTextBox.getText().isEmpty();
                    }
                });
                ObservableList<Meaning> tempmean = FXCollections.observableArrayList();
                tempmean.setAll(filteredMeaning);
                meaningTable.setItems(tempmean);
            }
            case "Usage" -> {
                FilteredList<Usage> filteredUsage = new FilteredList<>((ObservableList<Usage>) tableViewRefreshList.get(2).getValue());
                filteredUsage.setPredicate(new Predicate<Usage>() {
                    @Override
                    public boolean test(Usage usage) {
                        return String.valueOf(usage.getExampleId()).contains(filterTextBox.getText()) ||
                                usage.getKanjiCharacter().contains(filterTextBox.getText()) ||
                                usage.getUsageExample().contains(filterTextBox.getText()) ||
                                filterTextBox.getText().isEmpty();
                    }
                });
                ObservableList<Usage> tempus = FXCollections.observableArrayList();
                tempus.setAll(filteredUsage);
                usageTable.setItems(tempus);
            }
            case "Additional notes" -> {
                FilteredList<AdditionalNotes> filteredAn = new FilteredList<>((ObservableList<AdditionalNotes>) tableViewRefreshList.get(1).getValue());
                filteredAn.setPredicate(new Predicate<AdditionalNotes>() {
                    @Override
                    public boolean test(AdditionalNotes additionalNotes) {
                        return String.valueOf(additionalNotes.getNoteId()).contains(filterTextBox.getText()) ||
                                additionalNotes.getNote().contains(filterTextBox.getText()) ||
                                additionalNotes.getKanjiCharacter().contains(filterTextBox.getText()) ||
                                additionalNotes.getNoteType().contains(filterTextBox.getText()) ||
                                filterTextBox.getText().isEmpty();
                    }
                });
                ObservableList<AdditionalNotes> tempan = FXCollections.observableArrayList();
                tempan.setAll(filteredAn);
                additionalNotesTable.setItems(tempan);
            }
        }
    }

}
