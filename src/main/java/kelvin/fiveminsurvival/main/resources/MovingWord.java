package kelvin.fiveminsurvival.main.resources;

import java.awt.Point;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class MovingWord {
	public ArrayList<MovingText> mtext = new ArrayList<>();
	
	public float startX, startY;
	public float x, y;
	public float endX, endY;
	public float lerp;
	public String text;
	
	public int I = 0;
	public boolean out = true;
	public boolean in = false;
	
	public MovingWord(String text, float startX,float startY, float endX, float endY, float lerp) {
		this.text = text;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.lerp = lerp;
		this.x = startX + 0;
		this.y = startY + 0;
		int i = 0;
		int posX = 0;
		int width = 0;
		for (char c : text.toCharArray()) {
			width += Minecraft.getInstance().fontRenderer.getStringWidth(""+c);
		}
		for (char c : text.toCharArray()) {
			mtext.add(new MovingText(""+c, startX, startY, endX + posX - (width / 2.0F), endY, lerp));
			posX += Minecraft.getInstance().fontRenderer.getStringWidth(""+c);
			i++;
		}
	}
	
	public void tick(boolean in) {
		
		if (in) {
			
			if (I >= 0 && I < mtext.size()) {
				
				if (Point.distance(mtext.get(I).x, mtext.get(I).y, mtext.get(I).endX, mtext.get(I).endY) > 0.01) {
					mtext.get(I).tick(in);
				} else {
					I++;
				}
			} else {
				this.in = true;
				this.out = false;
				I = 0;
			}
		} else {
			if (I >= 0 && I < mtext.size()) {
				if (Point.distance(mtext.get(I).x, mtext.get(I).y, mtext.get(I).startX, mtext.get(I).startY) > 0.01) {
					mtext.get(I).tick(in);
				} else {
					I++;
				}
			} else {
				out = true;
			}
		}
		
	}
}
