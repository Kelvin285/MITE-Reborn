package kelvin.fiveminsurvival.main.resources;

public class MovingText {
	public float startX, startY;
	public float x, y;
	public float endX, endY;
	public float lerp;
	public String text;
	
	public MovingText(String text, float startX,float startY, float endX, float endY, float lerp) {
		this.text = text;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.lerp = lerp;
		this.x = startX + 0;
		this.y = startY + 0;
		
	}
	
	public void tick(boolean in) {
		if (in) {
			x = Resources.lerp(x, endX, lerp);
			y = Resources.lerp(y, endY, lerp);
			
		} else {
			x = Resources.lerp(x, startX, lerp);
			y = Resources.lerp(y, startY, lerp);
		}
		
		
	}
}
