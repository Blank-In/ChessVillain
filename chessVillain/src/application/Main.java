package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import layout.MainController;
/*
1 룩
2 나이트
3 비숍
4 퀸
5 킹
6 폰
말의 가치	킹∞	퀸9	룩5	비숍3	  나이트3	폰1
[y][x][0] 말속성
[y][x][1] 말색깔
*/
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			int[][][] map=new int[8][8][2];
			for(int a=0;a<8;a++) {
				//흑말설정
				map[1][a][0]=6;
				map[1][a][1]=1;
				if(a<5) {
					map[0][a][0]=a+1;
				}
				else {
					map[0][a][0]=8-a;
				}
				map[0][a][1]=1;
				//흰말설정
				map[6][a][0]=6;
				map[6][a][1]=2;
				if(a<5) {
					map[7][a][0]=a+1;
				}
				else {
					map[7][a][0]=8-a;
				}
				map[7][a][1]=2;
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/layout/MainLayout.fxml"));
			AnchorPane ap = loader.load();
			Scene scene = new Scene(ap);
			MainController mc = loader.getController();
			mc.init(map);
			primaryStage.getIcons().add(new Image(this.getClass().getResource("/resources/Icon.png").toString()));
			primaryStage.setTitle("체스 빌런");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}