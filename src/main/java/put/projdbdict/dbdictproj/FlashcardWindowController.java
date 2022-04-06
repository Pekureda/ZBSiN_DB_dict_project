package put.projdbdict.dbdictproj;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.Struct;
import java.util.List;

public class FlashcardWindowController {
    public Text flashcardCharacterText;
    public Text flashcardReadingText;
    public Text flashcardMeaningText;
    public Text flashcardExamplesText;
    public Button flashcardGoodButton;
    public Button flashcardMediumButton;
    public Button flashcardBadButton;
    public Button flashcardExitButton;
    public Button flashcardRevealButton;

    public Flashcard flashcard;

    @FXML
    private void initialize() {
        Object[] attributes = {  };
        //Struct mySTRUCT = DBUtil.getConn().createStruct("flashcard", );

        Service<Flashcard> flashcardService = new Service<>() {
            @Override
            protected Task<Flashcard> createTask() {
                return new Task<>() {
                    @Override
                    protected Flashcard call() throws Exception {
                        return FlashcardDAO.getFlashcard("日");
                    }
                };
            }
        };

        flashcardService.setOnFailed(e -> flashcardService.getException().printStackTrace());
        flashcardService.setOnSucceeded(e -> flashcard = (flashcardService.getValue()));

        flashcardService.start();
    }

    public void onActionGoodButton(ActionEvent actionEvent) {
    }

    public void onActionMediumButton(ActionEvent actionEvent) {
    }

    public void onActionBadButton(ActionEvent actionEvent) {
    }

    public void onActionExitButton(ActionEvent actionEvent) {
    }

    public void onActionRevealButton(ActionEvent actionEvent) {
    }
}
