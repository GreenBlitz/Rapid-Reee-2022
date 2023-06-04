package edu.greenblitz.pegasus.utils.vectors;

import edu.greenblitz.pegasus.utils.cords.Point;

public class Vector {
	private double angle, magnitude;
	private Point originPoint;
	
	public Vector(double direction, double magnitude, Point a) {
		this.angle = direction;
		this.magnitude = magnitude;
		this.originPoint = a;
	}
	
	public Vector(double direction, double magnitude) {
		this.angle = direction;
		this.magnitude = magnitude;
		this.originPoint = new Point(0, 0);
	}
	
	public Vector() {
	}
	
	public Vector(Vector a) {
		this.angle = a.getAngle();
		this.magnitude = a.getMagnitude();
		this.originPoint = a.getOriginPoint();
	}
	
	public Vector(Point a) {
		this.originPoint = a;
	}
	
	public Vector(Point a, Point b) {
		this.originPoint = a;
		headTo(b);
	}
	
	public void headTo(Point b) {
		angle = Math.toDegrees(Math.atan2(b.getY() - originPoint.getY(), b.getX() - originPoint.getX()));
		magnitude = Math.sqrt(Math.pow(b.getX() - originPoint.getX(), 2) + Math.pow(b.getY() - originPoint.getY(), 2));
	}
	
	public Point calculateHeadingPoint() {
		return new Point(Math.cos(Math.toRadians(angle)) * magnitude + originPoint.getX(), Math.sin(Math.toRadians(angle)) * magnitude + originPoint.getY());
	}
	
	public Vector add(Vector vector) {
		Vector tmp1 = new Vector(vector);
		Vector tmp2 = new Vector(this);
		tmp2.setOriginPoint(tmp1.calculateHeadingPoint());
		Point finalPoint = tmp2.calculateHeadingPoint();
		return new Vector(tmp1.getOriginPoint(), finalPoint);
	}
	public Vector subtract (Vector vector){
		Vector tmp = new Vector(vector);
		tmp.setMagnitude(-tmp.getMagnitude());
		return add(tmp);
	}
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getMagnitude() {
		return magnitude;
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
	public Point getOriginPoint() {
		return originPoint;
	}
	
	public void setOriginPoint(Point originPoint) {
		this.originPoint = originPoint;
	}
	
	
	@Override
	public String toString() {
		return "Vector{" +
				"direction=" + angle +
				", magnitude=" + magnitude +
				", pos=" + originPoint.toString() +
				'}';
	}
	
	
}
