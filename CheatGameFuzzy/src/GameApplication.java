
public class GameApplication {

	public static void main(String[] args) {
		
		//Construct all the components
		  GameLogic      model      = new GameLogic();
		  GameController controller = new GameController();
		  MainGameFrame  view       = new MainGameFrame();  
		  
		  //Notify each component of the other components it needs
		  
		  model.addView(view);
		  
		  controller.addModel(model);
		  controller.addView(view);
		  
		  //the view is affected by both the model and controller
		  view.addModel(model); 
		  view.addController(controller);
		  
		  //Build the application, then show it on the screen
		  view.createAndShowGUI();
	}

}
