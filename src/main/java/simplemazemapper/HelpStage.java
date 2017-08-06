package main.java.simplemazemapper;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class HelpStage {

    static Stage buildStage() {
        Stage primaryStage = new Stage();
        AnchorPane root = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10);
        AnchorPane.setRightAnchor(scrollPane, 10.0);
        AnchorPane.setTopAnchor(scrollPane, 10.0);
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setBottomAnchor(scrollPane, 10.0);

        Label colours = new Label("Colours:");
        Label green = new Label("Green");
        Text greenText = new Text("Green marks the starting block.");
        Label royalBlue = new Label("Royal Blue (the darker shade of blue)");
        Text royalBlueText = new Text("Royal blue marks blocks that have been explored.");
        Label skyBlue = new Label("Sky Blue (the lighter shade of blue)");
        Text skyBlueText = new Text("Sky blue marks the start of paths that you haven't explored yet.");
        Label yellow = new Label("Yellow");
        Text yellowText = new Text("Yellow marks explored blocks that you added a note to (example: button).");
        Label khaki = new Label("Khaki (the lighter shade of yellow)");
        Text khakiText = new Text("Khaki marks unexplored blocks that you added a note to.");
        Label white = new Label("White");
        Text whiteText = new Text("White (inside black squares) marks blocks that you added by mistake.");
        Label controls = new Label("Controls:");
        Text controlsText = new Text("Upon starting the program, add the coordinates of the start block in the text fields " +
                "below, the press Confirm. The block will appear in the middle of the window. \n\n" +
                "The program has 5 modes: add blocks, add notes, read notes, mark mistakes and remove notes. \n\n" +
                "Add blocks lets you add explored blocks (those that you have been to) with left click and unexplored " +
                "blocks (paths you have seen but didn't explore yet) with right click. \n" +
                "You can also turn unexplored blocks into explored blocks by clicking on them while in this mode. " +
                "Blocks marked as mistake can also be readded in this mode if you marked them by mistake. \n" +
                "This mode is default and active along with the other modes, allowing you to add new blocks at any point.\n\n" +
                "Add notes mode lets you add notes to blocks by writing the text first " +
                "in the text field below and then clicking on the block you want to add a note too (except the start block). " +
                "Adding a note to a block that has one already will simply update it. \n\nRead notes mode lets you read the " +
                "notes you left by clicking on blocks that have notes. \n\nMark mistakes mode lets you mark blocks " +
                "that you added by mistake accordingly (because I haven't implemented yet a way to remove blocks).\n\n" +
                "Finally, remove notes lets you remove notes added by mistake with a click.");

        content.getChildren().addAll(controls, controlsText, colours, green, greenText, royalBlue, royalBlueText,
                skyBlue, skyBlueText, yellow, yellowText, khaki, khakiText, white, whiteText);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 600, 600);

        controls.getStyleClass().addAll("help", "help-big-label");
        colours.getStyleClass().addAll("help", "help-big-label");
        green.getStyleClass().addAll("help", "help-colour-label");
        royalBlue.getStyleClass().addAll("help", "help-colour-label");
        skyBlue.getStyleClass().addAll("help", "help-colour-label");
        yellow.getStyleClass().addAll("help", "help-colour-label");
        khaki.getStyleClass().addAll("help", "help-colour-label");
        white.getStyleClass().addAll("help", "help-colour-label");
        controlsText.getStyleClass().add("help");
        green.setId("green");
        greenText.getStyleClass().add("help");
        royalBlue.setId("royal-blue");
        royalBlueText.getStyleClass().add("help");
        skyBlue.setId("sky-blue");
        skyBlueText.getStyleClass().add("help");
        yellow.setId("yellow");
        yellowText.getStyleClass().add("help");
        khaki.setId("khaki");
        khakiText.getStyleClass().add("help");
        white.setId("white");
        whiteText.getStyleClass().add("help");
        scene.getStylesheets().add("main/resources/stilizare.css");

        primaryStage.setTitle("Help");
        primaryStage.setScene(scene);
        return primaryStage;
    }
}
