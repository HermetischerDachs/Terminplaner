import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminplaner extends Application {
    public static void main(String[] args) {
        launch(Terminplaner.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Terminplaner");
        Scene scene = new Scene(borderPane());
        stage.setScene(scene);
        stage.show();
    }

    public static BorderPane borderPane(){
        BorderPane borderPane = new BorderPane();

        //Untere Schaltflächen
        HBox hBox = new HBox();
        hBox.setSpacing(50);
        Button neu = new Button("Neuer Eintrag");
        Button loeschen = new Button("Eintrag löschen");
        hBox.getChildren().addAll(neu,loeschen);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.BOTTOM_CENTER);

        //Tabelle erstellen und mit den Objekten aus der Liste befüllen
        TableView<Entry> tableView = new TableView();
        borderPane.setCenter(tableView);
        tableView.setEditable(true);
        //Spalten erstellen und Datentyp setzen
        TableColumn<Entry, String> date = createDateColumn();
        TableColumn<Entry, String> entry = createEntryColumn();
        tableView.getColumns().addAll(date, entry);
        //Liste in eine observableList umwandeln und der Tabelle hinzufügen
        ObservableList<Entry> entries = FXCollections.observableArrayList(entryList());
        tableView.setItems(entries);
        borderPane.setCenter(tableView);

        neu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(20);
                    gridPane.setVgap(15);
                    Stage stage = new Stage();
                    stage.setTitle("Neuer Eintrag");

                    Label datum = new Label("Datum: ");
                    Label eintrag = new Label("Eintrag: ");
                    TextField datumFeld = new TextField();
                    TextField eintragFeld = new TextField();
                    Button ok = new Button("ok");

                    gridPane.add(datum,0,0);
                    gridPane.add(eintrag,0,2);
                    gridPane.add(datumFeld,2,0);
                    gridPane.add(eintragFeld,2,2);
                    ok.setAlignment(Pos.CENTER_RIGHT);
                    gridPane.add(ok,2,3);

                    ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            ButtonMethods.neuerEintrag(datumFeld.getText(), eintragFeld.getText());
                            entries.add(new Entry(datumFeld.getText(), eintragFeld.getText()));
                            stage.close();
                        }
                    });

                    stage.setScene(new Scene(gridPane, 300,150));
                    stage.show();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        loeschen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                stage.setResizable(false);
                GridPane gridPane = new GridPane();
                gridPane.setVgap(15);
                gridPane.setHgap(15);

                Label label = new Label("Wirklich löschen?");
                Button ja = new Button("Ja");
                Button nein = new Button("Nein");

                gridPane.add(label, 2,2);
                gridPane.add(ja, 1,4);
                gridPane.add(nein, 3,4);

                ja.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Entry toDelete = tableView.getSelectionModel().getSelectedItem();
                        tableView.getItems().remove(toDelete);
                        ButtonMethods.eintragLoeschen(toDelete);
                        stage.close();
                    }
                });

                Scene scene = new Scene(gridPane, 220,120);
                stage.setScene(scene);
                stage.show();
            }
        });

        return borderPane;
    }

    //Liest die Textdatei aus, speichert die Einträge als Objekte in einer Arraylist ab und gibt am Ende eine Liste von den Objektreferenzen zurück
    public static ArrayList<Entry> entryList(){
        ArrayList<Entry> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] dataset = line.split(", ");
                entries.add(new Entry(dataset[0], dataset[1]));
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return entries;
    }

    private static TableColumn createDateColumn() {
        TableColumn<Entry,String> dateColumn = new TableColumn("Datum");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("date"));
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Entry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Entry, String> t) {
                Entry entryOld = new Entry(t.getRowValue().getDate(),t.getRowValue().getEntry());
                t.getRowValue().setDate(t.getNewValue());
                Entry entryNew = new Entry(t.getRowValue().getDate(),t.getRowValue().getEntry());
                ButtonMethods.eintragAendern(entryOld, entryNew);
            }
        });

        return dateColumn;
    }

    private static TableColumn createEntryColumn() {
        TableColumn<Entry,String> entryColumn = new TableColumn("Eintrag");
        entryColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("entry"));
        entryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        entryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Entry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Entry, String> t) {
                Entry entryOld = new Entry(t.getRowValue().getDate(),t.getRowValue().getEntry());
                t.getRowValue().setEntry(t.getNewValue());
                Entry entryNew = new Entry(t.getRowValue().getDate(),t.getRowValue().getEntry());
                ButtonMethods.eintragAendern(entryOld, entryNew);
            }
        });

        return entryColumn;
    }
}