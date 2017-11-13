package jinhua_yan_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * A simulate application that will keep track of the social media sites that
 * user has accounts with.
 *
 * @author Jessica
 */
public class Jinhua_Yan_3 extends Application {

    //create three input text fields
    TextField tfName;
    TextField tfID;
    TextField tfNo;

    //create a list view
    ListView listView = new ListView<>();

    //Constructs a list containing social media
    ArrayList<SocialMedia> socialMedias = new ArrayList<>();

    /**
     *
     * Simulate the process of an application storing user's informations of
     * social media.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is using to build a stage
     *
     * @param primaryStage is the name of stage
     * @throws Exception is when user inputs a number is not positive, the
     * program won't crush
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane pane = new BorderPane();

        pane.setPadding(new Insets(15));

        BorderPane.setAlignment(listView, Pos.TOP_LEFT);
        BorderPane.setMargin(listView, new Insets(0, 12, 0, 0));
        listView.setPrefWidth(200);

        GridPane inputPane = new GridPane();

        VBox vBox = new VBox(10);

        HBox hBox = new HBox(10);

        BorderPane.setAlignment(hBox, Pos.TOP_RIGHT);

        Label lblName = new Label("Name");
        tfName = new TextField();
        tfName.setEditable(false);

        Label lblID = new Label("User Id:");
        tfID = new TextField();
        tfID.setEditable(true);

        Label lblNo = new Label("No.Contacts:");
        tfNo = new TextField();
        tfNo.setEditable(true);

        Button btnLoad = new Button("Load");
        Button btnSave = new Button("Save");

        btnLoad.setOnAction(e -> loadFile(primaryStage));

        //when user inputs a number is not positive, the program won't crush 
        btnSave.setOnAction(e -> {
            try {

                int value = Integer.parseInt(tfNo.getText());

                if (value < 0) {
                    throw new Exception();
                }
                saveFile(primaryStage);

                //catching the contact number, it has to be a positive number   
            } catch (Exception ex) {

                Alert alert = new Alert(AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Contact number has to be a positive number");

                Optional<ButtonType> result = alert.showAndWait();

            }
        });

        hBox.getChildren().addAll(btnLoad, btnSave);

        hBox.setAlignment(Pos.CENTER);

        inputPane.addRow(0, lblName, tfName);
        inputPane.addRow(1, lblID, tfID);
        inputPane.addRow(2, lblNo, tfNo);

        vBox.getChildren().addAll(inputPane, hBox);

        inputPane.setHgap(15);
        inputPane.setVgap(15);

        pane.setLeft(listView);
        pane.setRight(vBox);

        Scene scene = new Scene(pane, 550, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Assignment3");
        primaryStage.show();

    }

    /**
     *
     * A method is using to load the file
     *
     * @param primaryStage is a name of the stage
     */
    private void loadFile(Stage primaryStage) {

        listView.getItems().clear();
        socialMedias.clear();

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Load File");

        chooser.setInitialDirectory(new File("."));

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = chooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            readFile(selectedFile);
        }

        //Add the social media to list view
        listView.getItems().addAll(socialMedias);

        //addListener is using to change the values of social media
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<SocialMedia>() {
            @Override
            public void changed(ObservableValue<? extends SocialMedia> observable, SocialMedia oldValue, SocialMedia newValue) {

                try {

                    if (oldValue != null) {

                        int value = Integer.parseInt(tfNo.getText());
                        if (value < 0) {
                            throw new Exception();
                        }

                        oldValue.setNo(value);
                        oldValue.setId(tfID.getText());

                    }

                    tfName.clear();
                    tfID.clear();
                    tfNo.clear();

                    if (newValue != null) {

                        tfName.setText(newValue.getName());
                        tfID.setText(newValue.getId());
                        tfNo.setText(Integer.toString(newValue.getNo()));

                    }

                    //catching the contact number, it has to be a positive number
                } catch (Exception ex) {

                    Alert alert = new Alert(AlertType.ERROR);

                    alert.setTitle("Error");
                    alert.setHeaderText("Contact number has to be a positive number");

                    Optional<ButtonType> result = alert.showAndWait();

                    tfName.setText(newValue.getName());
                    tfID.setText(newValue.getId());
                    tfNo.setText(Integer.toString(newValue.getNo()));
                }
            }

        }
        );

        listView.getSelectionModel().select(0);

    }

    /**
     *
     * A method to read the file
     *
     * @param file is a file the user chooses
     */
    private void readFile(File file) {

        try {

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {

                String lineIn = input.nextLine();

                String[] lineInSplit = lineIn.split(",");

                SocialMedia socialMediass = new SocialMedia(
                        lineInSplit[0],
                        lineInSplit[1],
                        Integer.parseInt(lineInSplit[2]));

                socialMedias.add(socialMediass);

            }
        } catch (FileNotFoundException ex) {

            System.out.println(ex.toString());

        }

    }

    /**
     *
     * A method to save file the user wants
     *
     * @param primaryStage is a name of stage
     */
    private void saveFile(Stage primaryStage) {

        int index = listView.getSelectionModel().getSelectedIndex();

        socialMedias.set(index, new SocialMedia(tfName.getText(), tfID.getText(), Integer.parseInt(tfNo.getText())));

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Save File Chooser");

        chooser.setInitialDirectory(new File("."));

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = chooser.showSaveDialog(primaryStage);

        if (selectedFile != null) {

            writeFile(selectedFile);

        }

        listView.getItems().clear();

        listView.getItems().addAll(socialMedias);

    }

    /**
     *
     * A method to write file the user wants
     *
     * @param file is a new file the user inputs
     */
    private void writeFile(File file) {

        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (SocialMedia socialMedia : socialMedias) {

                printWriter.printf("%s,%s,%s", socialMedia.getName(), socialMedia.getId(), socialMedia.getNo());
                printWriter.println();

            }
        } catch (FileNotFoundException e) {

            System.out.println(e.toString());

        }

    }

}
