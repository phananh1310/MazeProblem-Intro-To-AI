package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;  
import javafx.scene.*;


public class Main extends Application{
    
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Scene.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Maze");
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
