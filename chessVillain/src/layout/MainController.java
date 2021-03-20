package layout;
import application.AI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
public class MainController {
	@FXML
	private GridPane gridBoard;
	private int tempx=0,tempy=0,cnt=0;
	private BoardController[][] boardList=new BoardController[8][8];
	public void alert(String title,String msg,String button,String stat) {
		ButtonType OkButton = new ButtonType(button);
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setContentText(msg);
		dialog.getDialogPane().getButtonTypes().add(OkButton);
		dialog.setGraphic((new ImageView(this.getClass().getResource("/resources/"+stat).toString())));
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/resources/Icon.png").toString()));
		dialog.showAndWait();
	}
	private void clearBoard() {
		int cnt=0;
		for(int a=0;a<8;a++) {
			for(int b=0;b<8;b++) {
				if((a+cnt)%2==0) {
					boardList[a][b].SetBack("444444;");
				}
				else {
					boardList[a][b].SetBack("cccccc;");
				}
				cnt++;
			}
		}
	}
	public void init(int[][][] map) {
		cnt++;
		alert("게임 시작","체스 빌런 : 인간시대의 끝이 "+cnt+"번째 도래했다.","드루와","ChessVillain.png");
		for(int a=0;a<8;a++) {
			for(int b=0;b<8;b++) {
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/layout/BoardLayout.fxml"));
				try {
					AnchorPane ap=loader.load();
					boardList[a][b]=loader.getController();
					gridBoard.add(ap,b,a);
					boardList[a][b].x=b;
					boardList[a][b].y=a;
					boardList[a][b].init(this);
					boardList[a][b].SetImage(map[a][b][0], map[a][b][1]);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		clearBoard();
	}
	private void rook(int x,int y) {
		boolean b=true,c=true,d=true,e=true;
		for(int a=1;a<8;a++) {
			if(x+a<8&&b) {
				if(boardList[y][x+a].team==1) {
					boardList[y][x+a].SetBack("4286f4;");
					b=false;
				}
				else if(boardList[y][x+a].team==2) {
					b=false;
				}
				else{
					boardList[y][x+a].SetBack("4286f4;");
				}
			}
			if(x-a>-1&&c) {
				if(boardList[y][x-a].team==1) {
					boardList[y][x-a].SetBack("4286f4;");
					c=false;
				}
				else if(boardList[y][x-a].team==2) {
					c=false;
				}
				else{
					boardList[y][x-a].SetBack("4286f4;");
				}
			}
			if(y+a<8&&d) {
				if(boardList[y+a][x].team==1) {
					boardList[y+a][x].SetBack("4286f4;");
					d=false;
				}
				else if(boardList[y+a][x].team==2) {
					d=false;
				}
				else{
					boardList[y+a][x].SetBack("4286f4;");
				}
			}
			if(y-a>-1&&e) {
				if(boardList[y-a][x].team==1) {
					boardList[y-a][x].SetBack("4286f4;");
					e=false;
				}
				else if(boardList[y-a][x].team==2) {
					e=false;
				}
				else{
					boardList[y-a][x].SetBack("4286f4;");
				}
			}
		}
	}
	private void bishop(int x,int y) {
		boolean b=true,c=true,d=true,e=true;
		for(int a=1;a<8;a++) {
			if(x+a<8&&y+a<8&&b) {
				if(boardList[y+a][x+a].team==1) {
					boardList[y+a][x+a].SetBack("4286f4;");
					b=false;
				}
				else if(boardList[y+a][x+a].team==2){
					b=false;
				}
				else {
					boardList[y+a][x+a].SetBack("4286f4;");
				}
			}
			if(x-a>-1&&y+a<8&&c) {
				if(boardList[y+a][x-a].team==1) {
					boardList[y+a][x-a].SetBack("4286f4;");
					c=false;
				}
				else if(boardList[y+a][x-a].team==2){
					c=false;
				}
				else {
					boardList[y+a][x-a].SetBack("4286f4;");
				}
			}
			if(x+a<8&&y-a>-1&&d) {
				if(boardList[y-a][x+a].team==1) {
					boardList[y-a][x+a].SetBack("4286f4;");
					d=false;
				}
				else if(boardList[y-a][x+a].team==2){
					d=false;
				}
				else {
					boardList[y-a][x+a].SetBack("4286f4;");
				}
			}
			if(x-a>-1&&y-a>-1&&e) {
				if(boardList[y-a][x-a].team==1) {
					boardList[y-a][x-a].SetBack("4286f4;");
					e=false;
				}
				else if(boardList[y-a][x-a].team==2){
					e=false;
				}
				else {
					boardList[y-a][x-a].SetBack("4286f4;");
				}
			}
		}
	}
	private void waiting(int a) {
		try {
			Thread.sleep(a);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private boolean AiCommand=true;
	public void click(int x, int y,int code,int team) {
		if(boardList[y][x].back=="4286f4;") {
			AiCommand=false;
			clearBoard();
			int tempCode=boardList[tempy][tempx].code;
			boardList[tempy][tempx].SetImage(0, 0);
			boardList[y][x].SetImage(tempCode,2);
			tempx=0;
			tempy=0;
			AI a=new AI();
			int[] work=a.working(boardList);
			String tempS=boardList[work[1]][work[0]].back;
			String tempL=boardList[work[3]][work[2]].back;
			Thread t = new Thread() {
				@Override
				public void run() {
					waiting(500);
					Platform.runLater(()->{
						boardList[work[1]][work[0]].SetBack("bf1a1a;");
					});
					waiting(500);
					Platform.runLater(()->{
						boardList[work[3]][work[2]].SetBack("bf1a1a;");
					});
					waiting(500);
					Platform.runLater(()->{
						work[4]=boardList[work[1]][work[0]].code;
						boardList[work[1]][work[0]].SetBack(tempS);
						boardList[work[1]][work[0]].SetImage(0,0);
					});
					waiting(500);
					Platform.runLater(()->{
						boardList[work[3]][work[2]].SetBack(tempL);
						boardList[work[3]][work[2]].SetImage(work[4],1);
					});
					AiCommand=true;
				}
			};
			t.start();
		}
		clearBoard();
		if(code>0&&team==2&&AiCommand) {
			tempx=x;
			tempy=y;
			switch(code) {
				case 1:
					rook(x,y);
					break;
				case 2:
					int[][] list2={{x-2,y-1},{x-1,y-2},{x+1,y-2},{x+2,y-1},{x+2,y+1},{x+1,y+2},{x-1,y+2},{x-2,y+1}};
					for(int a=0;a<8;a++) {
						if(list2[a][0]>-1&&list2[a][0]<8&&list2[a][1]>-1&&list2[a][1]<8&&boardList[list2[a][1]][list2[a][0]].team!=2) {
							boardList[list2[a][1]][list2[a][0]].SetBack("4286f4;");
						}
					}
					break;
				case 3:
					bishop(x,y);
					break;
				case 4:
					rook(x,y);
					bishop(x,y);
					break;
				case 5:
					int[][] list5={{x-1,y-1},{x,y-1},{x+1,y-1},{x+1,y},{x+1,y+1},{x,y+1},{x-1,y+1},{x-1,y}};
					for(int a=0;a<8;a++) {
						if(list5[a][0]>-1&&list5[a][0]<8&&list5[a][1]>-1&&list5[a][1]<8&&boardList[list5[a][1]][list5[a][0]].team!=2) {
							boardList[list5[a][1]][list5[a][0]].SetBack("4286f4;");
						}
					}
					break;
				case 6:
					if(y>0) {
						if(x>0&&boardList[y-1][x-1].team==1) {
							boardList[y-1][x-1].SetBack("4286f4;");
						}
						if(x<7&&boardList[y-1][x+1].team==1) {
							boardList[y-1][x+1].SetBack("4286f4;");
						}
						if(y>0&&boardList[y-1][x].code==0) {
							if(y>1&&boardList[y-2][x].code==0&&y==6) {
								boardList[y-2][x].SetBack("4286f4;");
							}
							boardList[y-1][x].SetBack("4286f4;");
						}
					}
					break;
			}
		}
		System.out.println(x + ", " + y + ", "+code);
		for(int a=0;a<8;a++) {
			if(boardList[0][a].code==6) {
				boardList[0][a].SetImage(4, 2);
			}
		}
	}
}