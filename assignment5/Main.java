package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Ronghao Zhang
 * rz4453
 * 15505
 * Wenxuan Wu
 * ww6726
 * 15505
 * Slip days used: <0>
 * Spring 2018
 */

import javafx.application.*;	// start()
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;		// Stage
import javafx.scene.layout.*;	// FlowPane
import javafx.scene.paint.Color;	
import javafx.scene.control.*;	// Button
import javafx.scene.Scene;		
import javafx.scene.Group;
import javafx.geometry.Insets;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Main extends Application{
	static HBox top_layer = new HBox();
	static GridPane function_grid = new GridPane();
	static GridPane critters_grid = new GridPane();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Critter Simulator");
		Scene scene = new Scene(new Group(), Params.world_width*40 + 280, Params.world_height*40);
		
		// Defining the drop down for types of critters
		final ComboBox critterType = new ComboBox();
		critterType.getItems().addAll(
            "Algae",
            "Craig",
            "Critter1",
            "Critter2",
            "Critter3",
            "Critter4"
        );
		critterType.setPromptText("Critter type");
		
		// Defining the Text Field for the amount of critter to add
		final TextField amount = new TextField();
		amount.setPromptText("Amount");
		amount.setPrefColumnCount(5);
		amount.getText();
		
		// Defining the Add button to add critters to the world 
		Button addBtn = new Button ("Add");
		
		
		// Defining the Text Field for the number of steps to go
		final TextField steps = new TextField();
		steps.setPromptText("Steps");
		steps.setPrefColumnCount(5);
		steps.getText();
		
		// Defining the Go button to add critters to the world 
		Button goBtn = new Button ("Go");
		
		// Defining the Go button to add critters to the world 
		Button stepBtn = new Button ("Step");
		
		// Defining the Text Field for the seed
		final TextField seed = new TextField();
		seed.setPromptText("Seed");
		seed.setPrefColumnCount(5);
		seed.getText();
		
		// Defining the Add button to add critters to the world 
		Button setBtn = new Button ("Set");
		
		// Defining the drop down for stats of critters
		final ComboBox statsType = new ComboBox();
		statsType.getItems().addAll(
            "Algae",
            "Craig",
            "Critter1",
            "Critter2",
            "Critter3",
            "Critter4",
            "All"
        );
		statsType.setPromptText("Stats type  ");
		
		// Defining the Add button to show status
		Button showBtn = new Button ("Show");
		
		// Defining the Add button to hide status 
		Button hideBtn = new Button ("Hide");
		
		// Defining labels to display status 
		final Label label1 = new Label("Algae: 16");
		final Label label2 = new Label("Craig: 12");
		final Label label3 = new Label("Critter1: 4");
		final Label label4 = new Label("Critter2: 17");
		final Label label5 = new Label("Critter3: 5");
		final Label label6 = new Label("Critter4: 1");
		final Label warningTxt = new Label("");
		warningTxt.setFont(new Font("Arial", 15));
		
		// Defining the Add button play slow animation
		Button slowBtn = new Button ("Slow");
		// Defining the Add button play animation
		Button playBtn = new Button ("Play");
		// Defining the Add button play stop animation
		Button stopBtn = new Button ("Stop");
		// Defining the Add button play fast animation
		Button fastBtn = new Button ("Fast");
		
		// Defining the Add button play stop animation
		Button exitBtn = new Button (" Exit ");
		// Defining the Add button play fast animation
		Button resetBtn = new Button ("Reset");
		
		// instantiate the function_grid
		function_grid.setVgap(15);
		function_grid.setHgap(8);
		function_grid.setPadding(new Insets(5, 5, 5, 5));
		function_grid.add(critterType, 0, 0);
		function_grid.add(amount, 1, 0);
		function_grid.add(addBtn, 2, 0);
		function_grid.add(steps, 0, 1);
		function_grid.add(goBtn, 1, 1);
		function_grid.add(stepBtn, 2, 1);
		function_grid.add(seed, 0, 2);
		function_grid.add(setBtn, 1, 2);
		function_grid.add(statsType, 0, 3);
		function_grid.add(showBtn, 1, 3);
		function_grid.add(hideBtn, 2, 3);
		function_grid.add(label1, 0, 4);
		function_grid.add(label2, 0, 5);
		function_grid.add(label3, 0, 6);
		function_grid.add(label4, 0, 7);
		function_grid.add(label5, 0, 8);
		function_grid.add(label6, 0, 9);
		HBox hb1 = new HBox();
		hb1.setPadding(new Insets(5, 5, 5, 5));
		hb1.setSpacing(8);
		hb1.getChildren().add(slowBtn);
		hb1.getChildren().add(playBtn);
		hb1.getChildren().add(stopBtn);
		hb1.getChildren().add(fastBtn);
		HBox hb2 = new HBox();
		hb2.setPadding(new Insets(5, 5, 5, 5));
		hb2.setSpacing(8);
		hb2.getChildren().add(exitBtn);
		hb2.getChildren().add(resetBtn);
		function_grid.add(hb1, 0, 10, 3, 1);
		function_grid.add(hb2, 0, 11);
		function_grid.add(warningTxt, 0, 12, 3, 1);

		// instantiate the critters_grid
		drawGrid(critters_grid);
		
		top_layer.getChildren().add(function_grid);
		top_layer.getChildren().add(critters_grid);
		Group root = (Group)scene.getRoot();
		root.getChildren().add(top_layer);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Implement buttons
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				String type = (String) critterType.getValue();
				String count = amount.getText();
				int count_int = 0;
				if(!count.isEmpty()) {
					count_int = Integer.parseInt(count);
				}
				if(count_int == 0) {
					warningTxt.setText("Enter the amount of critters to add");
					warningTxt.setTextFill(Color.RED);
				}
				else if(type == null) {
					warningTxt.setText("Select the type of Critter to add");
					warningTxt.setTextFill(Color.RED);
				}
				else {
					for(int i = 0; i < count_int; i++) {
						try {
							Critter.makeCritter(type);
						} catch (InvalidCritterException e1) {
							warningTxt.setText("Undefined Critter");
							warningTxt.setTextFill(Color.RED);
						}
					}
					Critter.displayWorld(critters_grid);
					warningTxt.setText("Added " + count_int + " " + type + "s");
					warningTxt.setTextFill(Color.GREEN);
				}
			}
			
		});
		
		goBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				String count = steps.getText();
				int count_int = 0;
				if(!count.isEmpty()) {
					count_int = Integer.parseInt(count);
				}
				if(count_int == 0) {
					warningTxt.setText("Enter the steps to go");
					warningTxt.setTextFill(Color.RED);
				}
				else {
					for(int i = 0; i < count_int; i++) {
						Critter.worldTimeStep();
					}
					critters_grid.getChildren().removeIf(Shape.class::isInstance);	// clear the grid_pane and redraw it
					Critter.displayWorld(critters_grid);
					warningTxt.setText(count_int + " steps were processed");
					warningTxt.setTextFill(Color.GREEN);
				}
			}
			
		});
		
		stepBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	critters_grid.getChildren().removeIf(Shape.class::isInstance);	// clear the grid_pane and redraw it
		        Critter.worldTimeStep();
		        Critter.displayWorld(critters_grid);
		        warningTxt.setText("Stepped Once");
				warningTxt.setTextFill(Color.GREEN);
		    }
		});
		
		setBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				String count = seed.getText();
				int count_int = 0;
				if(!count.isEmpty()) {
					count_int = Integer.parseInt(count);
				}
				if(count_int == 0) {
					warningTxt.setText("Enter a valid seed");
					warningTxt.setTextFill(Color.RED);
				}
				else {
					Critter.setSeed(count_int);
					warningTxt.setText("New seed: " + count_int);
					warningTxt.setTextFill(Color.GREEN);
				}
			}
			
		});
	}
	
	protected void drawGrid(GridPane gp) {
		for(int i = 0; i < Params.world_height; i++) {
            ColumnConstraints column = new ColumnConstraints(40);
            gp.getColumnConstraints().add(column);
        }
		for(int i = 0; i < Params.world_width; i++) {
            RowConstraints row = new RowConstraints(40);
            gp.getRowConstraints().add(row);
        }	
		gp.setStyle("-fx-background-color: azure; -fx-grid-lines-visible: true");
	}
}