
public class GameApplication {

	public static void main(String[] args) {
		
		//Construir los componentes
		  GameLogic      model      = new GameLogic();
		  GameController controller = new GameController();
		  MainGameFrame  view       = new MainGameFrame();  
		  
		  	  
		  model.addView(view);
		  
		  controller.addModel(model);
		  controller.addView(view);
		  
		  //La vista es afectada por el modelo y el controlador
		  view.addModel(model); 
		  view.addController(controller);
		  
		  //Contruyendo la app
		  view.createAndShowGUI();
	}

}
