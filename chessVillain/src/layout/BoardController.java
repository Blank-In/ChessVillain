package layout;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
public class BoardController {
	@FXML
	private ImageView Image;
	@FXML
	private AnchorPane BackGround;
	private MainController parent;
	public int x,y,code=0,team=0;
	public int[][][] map=new int[8][8][2];
	public String back="";
	public void init(MainController main) {
		this.parent = main;
		for(int a=0;a<8;a++) {
			//�渻����
			map[1][a][0]=6;
			map[1][a][1]=1;
			if(a<5) {
				map[0][a][0]=a+1;
			}
			else {
				map[0][a][0]=8-a;
			}
			map[0][a][1]=1;
			//�򸻼���
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
	}
	public void SetBack(String a) {
		back=a;
		BackGround.setStyle("-fx-background-color: #"+a);
	}
	public void SetImage(int a,int b) {
		Image temp = null;
		try {
			temp = new Image(getClass().getResource("/resources/"+a+"-"+b+".png").toURI().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Image.setImage(temp);
		if(a!=0&&code==5&&team==1) {
			parent.alert("���� ����","ü�� ���� : Chess joggachi doone...","ü�� �� ���γ�.","AngryVillain.png");
			parent.init(map);
		}
		else if(a!=0&&code==5) {
			parent.alert("���� ����","ü�� ���� : Saram Yeesaeyo?","���ɷ��� ������;;;","HappyVillain.png");
			parent.init(map);
		}
		code=a;
		team=b;
	}
	public void clickBoard() {
		parent.click(x, y, code, team);
	}
}