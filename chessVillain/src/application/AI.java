package application;
import layout.BoardController;
public class AI {
	private BoardController[][] boardList;
	private boolean[][] block=new boolean[8][8];
	private int[] best={0,0,0,0,-1};
	public int[] working(BoardController[][] tempList) {
		boardList=tempList;
		for(int y=0;y<8;y++){
			for(int x=0;x<8;x++){
				block[y][x]=false;
			}
		}
		for(int y=0;y<8;y++){
			for(int x=0;x<8;x++){
				if(boardList[y][x].team==2){
					switch(boardList[y][x].code){
						case 1:
							whiteRook(x,y);
							break;
						case 2:
							int[][] list2={{x-2,y-1},{x-1,y-2},{x+1,y-2},{x+2,y-1},{x+2,y+1},{x+1,y+2},{x-1,y+2},{x-2,y+1}};
							for(int a=0;a<8;a++) {
								if(list2[a][0]>-1&&list2[a][0]<8&&list2[a][1]>-1&&list2[a][1]<8) {
									block[list2[a][1]][list2[a][0]]=true;
								}
							}
							break;
						case 3:
							whiteBishop(x,y);
							break;
						case 4:
							whiteRook(x,y);
							whiteBishop(x,y);
							break;
						case 5:
							int[][] list5={{x-1,y-1},{x,y-1},{x+1,y-1},{x+1,y},{x+1,y+1},{x,y+1},{x-1,y+1},{x-1,y}};
							for(int a=0;a<8;a++) {
								if(list5[a][0]>-1&&list5[a][0]<8&&list5[a][1]>-1&&list5[a][1]<8) {
									block[list5[a][1]][list5[a][0]]=true;
								}
							}
							break;
						case 6:
							if(x>0&&y!=0) {
								block[y-1][x-1]=true;
							}
							if(x<7&&y!=0) {
								block[y-1][x+1]=true;
							}
							break;
					}
				}
			}
		}
		for(int a=0;a<8;a++){
			for(int b=0;b<8;b++){
				if(block[a][b]){
					System.out.print("1 ");
				}
				else{
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		for(int y=0;y<8;y++){
			for(int x=0;x<8;x++){
				if(boardList[y][x].team==1){
					switch(boardList[y][x].code){
						case 1:
							blackRook(x,y,10);
							break;
						case 2:
							int[][] list2={{x-2,y-1},{x-1,y-2},{x+1,y-2},{x+2,y-1},{x+2,y+1},{x+1,y+2},{x-1,y+2},{x-2,y+1}};
							for(int a=0;a<8;a++) {
								int plus=0,point=-1;
								if(block[y][x]) {
									plus=checkPoint(x,y);
								}
								if(list2[a][0]>-1&&list2[a][0]<8&&list2[a][1]>-1&&list2[a][1]<8&&boardList[list2[a][1]][list2[a][0]].team!=1) {
									point=(checkWhite(x,y,list2[a][0],list2[a][1])+plus)*10;
								}
								if(best[4]<point) {
									best=new int[] {x,y,list2[a][0],list2[a][1],point};
								}
							}
							break;
						case 3:
							blackBishop(x,y,10);
							break;
						case 4:
							blackRook(x,y,10);
							blackBishop(x,y,10);
							break;
						case 5:
							int[][] list5={{x+1,y+1},{x-1,y+1},{x-1,y-1},{x+1,y-1},{x,y-1},{x+1,y},{x,y+1},{x-1,y}};
							for(int a=0;a<8;a++) {
								int plus=0,point=-1;
								if(block[y][x]) {
									plus=checkPoint(x,y);
								}
								if(list5[a][0]>-1&&list5[a][0]<8&&list5[a][1]>-1&&list5[a][1]<8&&boardList[list5[a][1]][list5[a][0]].team!=1) {
									point=checkWhite(x,y,list5[a][0],list5[a][1])+plus;
								}
								if(best[4]<point) {
									best=new int[] {x,y,list5[a][0],list5[a][1],point};
								}
							}
							break;
						case 6:
							int plus=0;
							int[] point={0,0,0,0};
							if(block[y][x]) {
								plus=checkPoint(x,y);
							}
							if(y<7&&x>0&&boardList[y+1][x-1].team==2) {
								point[0]+=(checkWhite(x,y,x-1,y+1)+plus)*10;
							}
							if(y<7&&x<7&&boardList[y+1][x+1].team==2) {
								point[1]+=(checkWhite(x,y,x+1,y+1)+plus)*10;
							}
							if(y<6&&boardList[y+1][x].code==0&&boardList[y+2][x].code==0&&y==1) {
								point[2]+=(checkWhite(x,y,x,y+2)+plus)*10+10;
							}
							if(y<7&&boardList[y+1][x].code==0) {
								point[3]+=(checkWhite(x,y,x,y+1)+plus)*10+y;
							}
							for(int a=0;a<4;a++) {
								if(best[4]<point[a]) {
									switch(a) {
										case 0:
											best=new int[] {x,y,x-1,y+1,point[a]};
											break;
										case 1:
											best=new int[] {x,y,x+1,y+1,point[a]};
											break;
										case 2:
											best=new int[] {x,y,x,y+2,point[a]};
											break;
										case 3:
											best=new int[] {x,y,x,y+1,point[a]};
											break;
									}
								}
							}
							break;
					}
				}
			}
		}
		for(int b=0;b<8;b++) {
			if(boardList[7][b].code==6) {
				boardList[7][b].SetImage(4,1);
			}
		}
		System.out.println(best[4]);
		return best;
	}
	private void whiteRook(int x,int y) {
		boolean b=true,c=true,d=true,e=true;
		for(int a=1;a<8;a++) {
			if(x+a<8&&b) {
				if(boardList[y][x+a].team>0) {
					block[y][x+a]=true;
					b=false;
				}
				else{
					block[y][x+a]=true;
				}
			}
			if(x-a>-1&&c) {
				if(boardList[y][x-a].team>0){
					block[y][x-a]=true;
					c=false;
				}
				else{
					block[y][x-a]=true;
				}
			}
			if(y+a<8&&d) {
				if(boardList[y+a][x].team>0){
					block[y+a][x]=true;
					d=false;
				}
				else{
					block[y+a][x]=true;
				}
			}
			if(y-a>-1&&e) {
				if(boardList[y-a][x].team>0) {
					block[y-a][x]=true;
					e=false;
				}
				else{
					block[y-a][x]=true;
				}
			}
		}
	}
	private void whiteBishop(int x,int y) {
		boolean b=true,c=true,d=true,e=true;
		for(int a=1;a<8;a++) {
			if(x+a<8&&y+a<8&&b) {
				if(boardList[y+a][x+a].team>0) {
					block[y+a][x+a]=true;
					b=false;
				}
				else {
					block[y+a][x+a]=true;
				}
			}
			if(x-a>-1&&y+a<8&&c) {
				if(boardList[y+a][x-a].team>0) {
					block[y+a][x-a]=true;
					c=false;
				}
				else {
					block[y+a][x-a]=true;
				}
			}
			if(x+a<8&&y-a>-1&&d) {
				if(boardList[y-a][x+a].team>0) {
					block[y-a][x+a]=true;
					d=false;
				}
				else {
					block[y-a][x+a]=true;
				}
			}
			if(x-a>-1&&y-a>-1&&e) {
				if(boardList[y-a][x-a].team>0) {
					block[y-a][x-a]=true;
					e=false;
				}
				else {
					block[y-a][x-a]=true;
				}
			}
		}
	}
	private int checkPoint(int x,int y) {
		switch(boardList[y][x].code) {
			case 1:
				return 5;
			case 2:
				return 3;
			case 3:
				return 3;
			case 4:
				return 9;
			case 5:
				return 10000;
			case 6:
				return 1;
		}
		return 0;
	}
	private int checkWhite(int startX,int startY,int x,int y) {
		int point=0;
		if(boardList[y][x].team==2) {
			point+=checkPoint(x,y)+1;
		}
		if(block[y][x]) {
			point-=checkPoint(startX,startY);
		}
		return point;
	}
	private void blackRook(int x,int y,int turnPoint) {
		boolean b=true,c=true,d=true,e=true;
		int plus=0;
		if(block[y][x]) {
			plus=checkPoint(x,y);
		}
		for(int a=1;a<8;a++) {
			if(x+a<8&&b) {
				int[] temp={x,y,x+a,y,0};
				if(boardList[y][x+a].team==2) {
					temp[4]+=(checkWhite(x,y,x+a,y)+plus)*turnPoint;
					b=false;
				}
				else if(boardList[y][x+a].team==1) {
					b=false;
					temp[4]=-1;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
			if(x-a>-1&&c) {
				int[] temp={x,y,x-a,y,0};
				if(boardList[y][x-a].team==2) {
					temp[4]+=(checkWhite(x,y,x-a,y)+plus)*turnPoint;
					c=false;
				}
				else if(boardList[y][x-a].team==1) {
					c=false;
					temp[4]=-1;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
			if(y+a<8&&d) {
				int[] temp={x,y,x,y+a,0};
				if(boardList[y+a][x].team==2) {
					temp[4]+=(checkWhite(x,y,x,y+a)+plus)*turnPoint;
					d=false;
				}
				else if(boardList[y+a][x].team==1) {
					d=false;
					temp[4]=-1;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
			if(y-a>-1&&e) {
				int[] temp={x,y,x,y-a,0};
				if(boardList[y-a][x].team==2) {
					temp[4]+=(checkWhite(x,y,x,y-a)+plus)*turnPoint;
					e=false;
				}
				else if(boardList[y-a][x].team==1) {
					e=false;
					temp[4]=-1;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
		}
	}
	private void blackBishop(int x,int y,int turnPoint) {
		boolean b=true,c=true,d=true,e=true;
		int plus=0;
		if(block[y][x]) {
			plus=checkPoint(x,y);
		}
		for(int a=1;a<8;a++) {
			if(x+a<8&&y+a<8&&b) {
				int[] temp={x,y,x+a,y+a,0};
				if(boardList[y+a][x+a].team==2) {
					temp[4]+=(checkWhite(x,y,x+a,y+a)+plus)*turnPoint;
					b=false;
				}
				else if(boardList[y+a][x+a].team==1){
					temp[4]=-1;
					b=false;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
			if(x-a>-1&&y+a<8&&c) {
				int[] temp={x,y,x-a,y+a,0};
				if(boardList[y+a][x-a].team==2) {
					temp[4]+=(checkWhite(x,y,x-a,y+a)+plus)*turnPoint;
					c=false;
				}
				else if(boardList[y+a][x-a].team==1){
					temp[4]=-1;
					c=false;
				}
			}
			if(x+a<8&&y-a>-1&&d) {
				int[] temp={x,y,x+a,y-a,0};
				if(boardList[y-a][x+a].team==2) {
					temp[4]+=(checkWhite(x,y,x+a,y-a)+plus)*turnPoint;
					d=false;
				}
				else if(boardList[y-a][x+a].team==1){
					temp[4]=-1;
					d=false;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
			if(x-a>-1&&y-a>-1&&e) {
				int[] temp={x,y,x-a,y-a,0};
				if(boardList[y-a][x-a].team==2) {
					temp[4]+=(checkWhite(x,y,x-a,y-a)+plus)*turnPoint;
					e=false;
				}
				else if(boardList[y-a][x-a].team==1){
					temp[4]=-1;
					e=false;
				}
				if(best[4]<temp[4]) {
					best=temp;
				}
			}
		}
	}
}