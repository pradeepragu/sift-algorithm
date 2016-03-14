package project;

public class Line {

Integer x1=null;
public Integer getX1() {
	return x1;
}

public void setX1(Integer x1) {
	this.x1 = x1;
}

public Integer getX2() {
	return x2;
}

public void setX2(Integer x2) {
	this.x2 = x2;
}

public Integer getY1() {
	return y1;
}

public void setY1(Integer y1) {
	this.y1 = y1;
}

public Integer getY2() {
	return y2;
}

public void setY2(Integer y2) {
	this.y2 = y2;
}

Integer x2=null;
Integer y1=null;
Integer y2=null;
public Line findPoint(Integer x1,Integer y1, int theta, int height)
{
	Line l = new Line();
	l.setX1(x1);
	l.setX2((int)(x1+(height*Math.cos(theta))));
	l.setY1(y1);
	l.setY2((int)(y1+(height*Math.sin(theta))));

	
	return l;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
