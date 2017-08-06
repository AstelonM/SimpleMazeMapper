package main.java.simplemazemapper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import main.java.simplemazemapper.date.Bloc;
import main.java.simplemazemapper.date.Cheie;
import main.java.simplemazemapper.utile.Cardinal;
import main.java.simplemazemapper.utile.TipMarcaj;

import java.util.HashMap;

public class SimpleMazeMapper extends Application {

    private static final int SIZE = 50;

    private HashMap<Cheie, Bloc> harta;
    private boolean adaugaNota;
    private boolean citesteNota;
    private boolean adaugaGreseli;
    private boolean stergeNota;

    private Canvas canvas;
    private TextField xCoord;
    private TextField zCoord;
    private Button confirm;
    private Label mesaj;
    private HBox bottom;
    private Label xLabel;
    private Label zLabel;
    private Stage help;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        harta = new HashMap<>();
        ScrollPane scrollPane = new ScrollPane();

        AnchorPane top = new AnchorPane();
        MenuBar meniu = new MenuBar();
        Menu meniuMode = new Menu("Add Blocks");
        MenuItem meniuModeAddBlock = new MenuItem("Add Blocks");
        meniuModeAddBlock.setOnAction(event -> {
            adaugaNota = false;
            citesteNota = false;
            adaugaGreseli = false;
            stergeNota = false;
            meniuMode.setText("Add Blocks");
            if (!(xCoord.isDisabled()))
                xCoord.setDisable(true);
            mesaj.setText("Add explored and unexplored blocks to the map.");
        });
        MenuItem meniuModeAddNote = new MenuItem("Add Notes");
        meniuModeAddNote.setOnAction(event -> {
            adaugaNota = true;
            citesteNota = false;
            adaugaGreseli = false;
            stergeNota = false;
            meniuMode.setText("Add Notes");
            if (xCoord.isDisabled())
                xCoord.setDisable(false);
            mesaj.setText("Write the note in the text field before clicking on a block.");
        });
        MenuItem meniuModeReadNote = new MenuItem("Read Notes");
        meniuModeReadNote.setOnAction(event -> {
            adaugaNota = false;
            citesteNota = true;
            adaugaGreseli = false;
            stergeNota = false;
            meniuMode.setText("Read Notes");
            if (!(xCoord.isDisabled()))
                xCoord.setDisable(true);
            mesaj.setText("Click a block with a note and it will appear here.");
        });
        MenuItem meniuModeMarkMistake = new MenuItem("Mark Mistakes");
        meniuModeMarkMistake.setOnAction(event -> {
            adaugaNota = false;
            citesteNota = false;
            adaugaGreseli = true;
            stergeNota = false;
            meniuMode.setText("Mark Mistakes");
            if (!(xCoord.isDisabled()))
                xCoord.setDisable(true);
            mesaj.setText("Click on blocks you added by mistake to mark them accordingly.");
        });
        MenuItem meniuModeRemoveNote = new MenuItem("Remove Notes");
        meniuModeRemoveNote.setOnAction(event -> {
            adaugaNota = false;
            citesteNota = false;
            adaugaGreseli = false;
            stergeNota = true;
            meniuMode.setText("Remove Notes");
            if (!(xCoord.isDisabled()))
                xCoord.setDisable(true);
            mesaj.setText("Click on blocks with notes to remove their note.");
        });
        meniuMode.getItems().addAll(meniuModeAddBlock, meniuModeAddNote, meniuModeReadNote, meniuModeMarkMistake,
                meniuModeRemoveNote);
        meniuModeAddBlock.setDisable(true);
        meniuModeAddNote.setDisable(true);
        meniuModeReadNote.setDisable(true);
        meniuModeMarkMistake.setDisable(true);
        meniuModeRemoveNote.setDisable(true);

        Menu meniuTools = new Menu("Tools");
        MenuItem meniuToolsHelp = new MenuItem("Help");
        meniuToolsHelp.setOnAction(event -> {
            if (help == null) {
                help = HelpStage.buildStage();
                help.showAndWait();
                help = null;
            }
        });
        MenuItem meniuToolsCenter = new MenuItem("Go to the center");
        meniuToolsCenter.setOnAction(event ->  {
            scrollPane.setVvalue(0.5);
            scrollPane.setHvalue(0.5);
        });
        meniuTools.getItems().addAll(meniuToolsHelp, meniuToolsCenter);
        meniu.getMenus().addAll(meniuMode, meniuTools);
        AnchorPane.setTopAnchor(meniu, 0.0);
        AnchorPane.setLeftAnchor(meniu, 0.0);
        AnchorPane.setRightAnchor(meniu, 0.0);
        top.getChildren().addAll(meniu);

        AnchorPane middle = new AnchorPane();
        canvas = new Canvas(129 * SIZE, 129 * SIZE);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(canvas);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
        middle.getChildren().add(scrollPane);

        bottom = new HBox(8);
        bottom.getStyleClass().add("bottom");
        xLabel = new Label("X:");
        xCoord = new TextField();
        zLabel = new Label("Z:");
        zCoord = new TextField();
        confirm = new Button("Confirm");
        mesaj = new Label("Give the coordinates of the entrance.");
        confirm.setOnAction(ev -> {
            if (xCoord.getText() == null || zCoord.getText() == null)
                return;
            int coordX, coordZ;
            try {
                coordX = Integer.parseInt(xCoord.getText());
                coordZ = Integer.parseInt(zCoord.getText());
            } catch (NumberFormatException e) {
                mesaj.setText("The coordinates must be numbers!");
                return;
            }
            canvas.setOnMouseClicked(event -> {
                MouseButton buton = event.getButton();
                double x = event.getX();
                double y = event.getY();
                int i = (Double.valueOf(x / SIZE)).intValue();
                int j = (Double.valueOf(y / SIZE)).intValue();
                GraphicsContext context = canvas.getGraphicsContext2D();
                double xRect = Math.floor(x / SIZE) * SIZE;
                double yRect = Math.floor(y / SIZE) * SIZE;
                Cheie cheie = new Cheie(i, j);
                if (harta.containsKey(cheie)) {
                    Bloc bloc = harta.get(cheie);
                    if (adaugaNota) {
                        String text = xCoord.getText();
                        bloc.setNota(text);
                        if (bloc.getTip() == TipMarcaj.EXPLORAT) {
                            bloc.setTip(TipMarcaj.SPECIAL_EXP);
                            draw(context, TipMarcaj.SPECIAL_EXP, xRect, yRect, bloc.getX(), bloc.getZ());
                        } else if (bloc.getTip() == TipMarcaj.NEEXPLORAT) {
                            bloc.setTip(TipMarcaj.SPECIAL_NEEXP);
                            draw(context, TipMarcaj.SPECIAL_NEEXP, xRect, yRect, bloc.getX(), bloc.getZ());
                        }
                    } else if (citesteNota) {
                        if (bloc.getTip() == TipMarcaj.SPECIAL_EXP || bloc.getTip() == TipMarcaj.SPECIAL_NEEXP)
                            mesaj.setText(bloc.getNota() + " at X: " + bloc.getX() + " Z: " + bloc.getZ());
                    } else if (adaugaGreseli) {
                        if (bloc.getTip() != TipMarcaj.START && bloc.getTip() != TipMarcaj.GRESEALA) {
                            bloc.setTip(TipMarcaj.GRESEALA);
                            draw(context, TipMarcaj.GRESEALA, xRect, yRect, bloc.getX(), bloc.getZ());
                        }
                    } else if (stergeNota) {
                        if (bloc.getTip() == TipMarcaj.SPECIAL_EXP) {
                            bloc.setNota(null);
                            bloc.setTip(TipMarcaj.EXPLORAT);
                            draw(context, TipMarcaj.EXPLORAT, xRect, yRect, bloc.getX(), bloc.getZ());
                        } else if (bloc.getTip() == TipMarcaj.SPECIAL_NEEXP) {
                            bloc.setNota(null);
                            bloc.setTip(TipMarcaj.NEEXPLORAT);
                            draw(context, TipMarcaj.NEEXPLORAT, xRect, yRect, bloc.getX(), bloc.getZ());
                        }
                    } else {
                        TipMarcaj tip = bloc.getTip();
                        switch (tip) {
                            case NEEXPLORAT: {
                                if (buton == MouseButton.PRIMARY) {
                                    draw(context, TipMarcaj.EXPLORAT, xRect, yRect, bloc.getX(), bloc.getZ());
                                    bloc.setTip(TipMarcaj.EXPLORAT);
                                }
                                break;
                            }
                            case SPECIAL_NEEXP: {
                                if (buton == MouseButton.PRIMARY) {
                                    draw(context, TipMarcaj.SPECIAL_EXP, xRect, yRect, bloc.getX(), bloc.getZ());
                                    bloc.setTip(TipMarcaj.SPECIAL_EXP);
                                }
                                break;
                            }
                            case GRESEALA: {
                                if (buton == MouseButton.PRIMARY) {
                                    if(bloc.getNota() == null) {
                                        draw(context, TipMarcaj.EXPLORAT, xRect, yRect, bloc.getX(), bloc.getZ());
                                        bloc.setTip(TipMarcaj.EXPLORAT);
                                    } else {
                                        draw(context, TipMarcaj.SPECIAL_EXP, xRect, yRect, bloc.getX(), bloc.getZ());
                                        bloc.setTip(TipMarcaj.SPECIAL_EXP);
                                    }
                                }
                                else if (buton == MouseButton.SECONDARY) {
                                    if(bloc.getNota() == null) {
                                        draw(context, TipMarcaj.NEEXPLORAT, xRect, yRect, bloc.getX(), bloc.getZ());
                                        bloc.setTip(TipMarcaj.NEEXPLORAT);
                                    } else {
                                        draw(context, TipMarcaj.SPECIAL_NEEXP, xRect, yRect, bloc.getX(), bloc.getZ());
                                        bloc.setTip(TipMarcaj.SPECIAL_NEEXP);
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {
                    Cheie cheieTemp;
                    /* Nota: coordonatele in nod sunt inversate, i pentru coloane, j pentru linii
                    De aceea cardinalele nu sunt aceleasi cu directia unde cautam */
                    if (i > 0) { // cauta la nord de punct daca e posibil
                        if (harta.containsKey(cheieTemp = new Cheie(i - 1, j))) {
                            gasit(cheie, context, buton, harta.get(cheieTemp), Cardinal.VEST, xRect, yRect);
                            return;
                        }
                    }
                    if (j < 128) { // cauta la est
                        if (harta.containsKey(cheieTemp = new Cheie(i, j + 1))) {
                            gasit(cheie, context, buton, harta.get(cheieTemp), Cardinal.SUD, xRect, yRect);
                            return;
                        }
                    }
                    if (i < 128) { //cauta la sud
                        if (harta.containsKey(cheieTemp = new Cheie(i + 1, j))) {
                            gasit(cheie, context, buton, harta.get(cheieTemp), Cardinal.EST, xRect, yRect);
                            return;
                        }
                    }
                    if (j > 0) { // cauta la vest
                        if (harta.containsKey(cheieTemp = new Cheie(i, j - 1))) {
                            gasit(cheie, context, buton, harta.get(cheieTemp), Cardinal.NORD, xRect, yRect);
                        }
                    }
                }
            });
            confirm.setDisable(true);
            xCoord.setDisable(true);
            bottom.getChildren().remove(zCoord);
            bottom.getChildren().remove(zLabel);
            zCoord = null;
            zLabel = null;
            xLabel.setText("Note:");
            harta.put(new Cheie(65, 65), new Bloc(coordX, coordZ, TipMarcaj.START));
            draw(canvas.getGraphicsContext2D(), TipMarcaj.START, 65 * SIZE, 65 * SIZE, coordX, coordZ);
            meniuModeAddBlock.setDisable(false);
            meniuModeAddNote.setDisable(false);
            meniuModeReadNote.setDisable(false);
            meniuModeMarkMistake.setDisable(false);
            meniuModeRemoveNote.setDisable(false);
            mesaj.setText("Press the add blocks button for more modes!");
            xCoord.setText("");
        });
        bottom.getChildren().addAll(xLabel, xCoord, zLabel, zCoord, confirm, mesaj);

        root.getChildren().addAll(top, middle, bottom);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("main/resources/stilizare.css");
        primaryStage.setTitle("Simple Maze Mapper");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void gasit(Cheie cheie, GraphicsContext context, MouseButton buton, Bloc bloc, Cardinal punct, double x, double y) {
        double coordX = bloc.getX(), coordZ = bloc.getZ();
        TipMarcaj tip;
        if (buton == MouseButton.PRIMARY) {
            tip = TipMarcaj.EXPLORAT;
        }
        else if (buton == MouseButton.SECONDARY) {
            tip = TipMarcaj.NEEXPLORAT;
        }
        else
            return;
        switch (punct) {
            case NORD: {
                harta.put(cheie, new Bloc(coordX, coordZ + 1, tip));
                draw(context, tip, x, y, coordX, coordZ + 1);
                break;
            }
            case EST: {
                harta.put(cheie, new Bloc(coordX - 1, coordZ, tip));
                draw(context, tip, x, y, coordX - 1, coordZ);
                break;
            }
            case SUD: {
                harta.put(cheie, new Bloc(coordX, coordZ - 1, tip));
                draw(context, tip, x, y, coordX, coordZ - 1);
                break;
            }
            case VEST: {
                harta.put(cheie, new Bloc(coordX + 1, coordZ, tip));
                draw(context, tip, x, y, coordX + 1, coordZ);
                break;
            }
        }
    }

    private void draw(GraphicsContext context, TipMarcaj tip, double x, double y, double coordX, double coordZ) {
        context.setStroke(Color.BLACK);
        switch (tip) {
            case START: {
                context.setFill(Color.GREEN);
                break;
            }
            case EXPLORAT: {
                context.setFill(Color.ROYALBLUE);
                break;
            }
            case NEEXPLORAT: {
                context.setFill(Color.SKYBLUE);
                break;
            }
            case SPECIAL_EXP: {
                context.setFill(Color.YELLOW);
                break;
            }
            case SPECIAL_NEEXP: {
                context.setFill(Color.KHAKI);
                break;
            }
            case GRESEALA: {
                context.setFill(Color.RED);
                break;
            }
        }
        context.fillRect(x, y, SIZE, SIZE);
        context.strokeRect(x, y, SIZE, SIZE);
        String textX = "X:" + (Double.valueOf(coordX)).intValue();
        String textZ = "Z:" + (Double.valueOf(coordZ)).intValue();
        context.strokeText(textX, x, y + 13, SIZE);
        context.strokeText(textZ, x, y + 38, SIZE);
    }
}
