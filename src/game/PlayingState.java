package game;

public class PlayingState {

	//testing the framework
	double x;
	double y;
	
	public PlayingState() {
		x = 0;
		y = 0;
	}
	
	public void Update(double dx, double dy) {
		x+=dx;
		y+=dy;
	}

}
