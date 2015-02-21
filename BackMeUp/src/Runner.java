import controller.BackupOperationsController;
import views.MainView;


public class Runner {
  
  static BackupOperationsController _controller = new BackupOperationsController();

  public static void main(String[] args) {
    _controller.displayView();
  }

}
