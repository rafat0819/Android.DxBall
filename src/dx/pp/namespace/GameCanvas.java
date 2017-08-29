package dx.pp.namespace;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import dx.pp.namespace.R;

public class GameCanvas extends View{

	Paint paint;
	float x=0,y=0;
	boolean FirstTime=true;
	int dy=1,dx=1,i=0,score=0,life=3;
	float a=0,space=0;
	float right=250,left=50;
	int BrickHeight=70;
	float BrickX;
    float BrickY;
    int Color1;
    int Number=5;
    //final MediaPlayer mediaPlayer = MediaPlayer.create(this,pp.namespace.main.R.raw.hit);;
	 
    ArrayList<Brick>bricks =new ArrayList<Brick>();
    
   void DrawBrick(Canvas canvas,ArrayList<Brick>bricks){
	   
	BrickX= 70;
   	BrickY= 10;
   	float BrickWidth = (canvas.getWidth()/ Number);
   	//float BrickWidth = 100;
       for (int i = 0; i < 7; i++) {
           for (int j = 0; j < 2; j++) {
           		Color1 =Color.YELLOW;
           	
           	bricks.add(new Brick(BrickX, BrickX + 70, BrickY,BrickY + 60, Color1));
           	BrickX += BrickWidth;
           }
           BrickY += BrickHeight;
           BrickX = 70;
       } 
   }
   
   public void ballbricksickCollision(){
	   for(int i=0;i<bricks.size();i++) {
           if (((y - 20) <= bricks.get(i).getDown()) && ((y + 20) >= bricks.get(i).getUp()) && ((x) >= bricks.get(i).getLeft()) && ((x) <= bricks.get(i).getRight())) {
               bricks.remove(i); 
               score ++;
               dy*=-1;
           }
           else if(((y) <= bricks.get(i).getDown()) && ((y) >= bricks.get(i).getUp()) && ((x + 20) >= bricks.get(i).getLeft()) && ((x - 20) <= bricks.get(i).getRight())) {
               bricks.remove(i);
               score++;
               dx*=-1;
           }
	   }
   }

    
  
	protected void calculateNextPos(Canvas canvas){
		if(FirstTime)
		{
			FirstTime=false;
			x=canvas.getWidth() / 2;
			y=(canvas.getHeight() / 2)+110;
		}
		y+=1*dy;
		x+=1*dx;
		if(x<=0+20) dx*=-1;
		if(x>=canvas.getWidth()-20) dx*=-1;
		if(y<=0+20) dy*=-1;
	}
	protected void DrawCircle(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(x,y, 20, paint);
	}
	
	protected void DrawBar(Canvas canvas){
		float posy=canvas.getHeight();
		float posx=canvas.getWidth();
		paint.setColor(Color.CYAN);
		paint.setStyle(Style.FILL);
	    canvas.drawRect(left, posy-30, right, posy, paint);
	}
	protected void onDraw(Canvas canvas) {
		if(bricks.size()<=0)
		DrawBrick( canvas,bricks);
		calculateNextPos(canvas);
		canvas.drawRGB(0, 0, 0);
		DrawCircle(canvas);
		
		if(y+20>=canvas.getHeight()-30) {
			if(x+20>=left && x+20<=right){
				dx*=1;
				dy*=-1;
			}
		}
		
		if(y>=canvas.getHeight()){
			life--;
			x=canvas.getWidth() / 2;
			y=canvas.getHeight() / 2;
			dx=1;
			dy=1;
			if(life!=0){
				DrawCircle(canvas);
			}
			else if(life==0) {
				System.exit(0);
			}
		}
		
		DrawBar(canvas);
		ballbricksickCollision();
		
		 
		 for(int i=0;i<bricks.size();i++)
		 {
			 Brick b=bricks.get(i);
	         canvas.drawRect(bricks.get(i).getLeft(), bricks.get(i).getUp(), bricks.get(i).getRight(), bricks.get(i).getDown(), bricks.get(i).getPaint());
		 }
		
		paint.setTextSize(30);
	    canvas.drawText("Score: "+score,10,30,paint);
	    canvas.drawText("Life: "+life,150,30,paint);
	    canvas.drawText("DXBALL",canvas.getWidth()-100,30,paint);
		invalidate();
	}
	
	public GameCanvas(Context context) {
		super(context);
		paint = new Paint();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float TouchX=event.getX();
		if(TouchX>getWidth()/2){
			right+=70;
			left+=70;
		}
		if(TouchX<getWidth()/2){
			right-=70;
			left-=70;
			}
		if(right>=getWidth()){
			right=getWidth();
			left=getWidth()-200;
		}
		if(left<=0){
			right=200;
			left=0;
		}
		return super.onTouchEvent(event);
	}

}




