public class TrainMotion {
	
	//train state
	private double power = 0; //in Watts
	private double position = 0; //in m
	private double velocity = 0; //in m/s
	private double acceleration = 0;//in m/s^2
	
	private double time = 0; //in s
	
	private double mass = 51437; //loaded train mass in kg
	
	private double trLength = 32.2; //in m
	private double trWidth = 2.65;
	private double trHeight = 3.42;
	
	private boolean trainBrakeOn = false;
	private boolean trainEmergencyBrakeOn = false;
	
	private final double time_step = .001; //in s
	
	private final double maxPower = 120000.0; //in W (120kW)
	private final double maxSpeed = 70000/3600.0; //in m/s (70km/hr)
	
	private final double trainFriction = .001; //coefficient of friction of rolling steel wheels
	
	private final double trainBrake = 1.2; //in m/s^2
	private final double trainEmergencyBrake = 2.73; //in m/s^2
	
	private final double gravity = 9.81; //in m/s^2

	private int accelRegime = 0;
	
	public TrainMotion() {
	}
	
	public void setPower(double pow) {
		power = pow;
		if(pow < 0) {
			setBrake(true);
		} else {
			setBrake(false);
		}
	}
	
	public void setAccel(double accel) {
		acceleration = accel;
	}
	
	public boolean setBrake(boolean brake) {
		return trainBrakeOn = brake;
	}
	
	public boolean setEmergencyBrake(boolean brake) {
		return trainEmergencyBrakeOn = brake;
	}
	
	public double getPosition() {
		return position;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public double getAccel() {
		return acceleration;
	}
	
	public double getTime() {
		return time;
	}
	
	public void printState() {
		System.out.format("t (s): %.3f, p (m): %.6f, v (m/s): %.6f, a (m/s^2): %.3f %d%n", time, position, velocity, acceleration, accelRegime);
	}
	
	public void motionStep() {
		double rollingFriction;
		double endPosition;
		double endVelocity;
		double endAccel;
		
		if(power > maxPower) {
			power = maxPower;
		}
		
		rollingFriction = trainFriction * (gravity * mass);
		
		endPosition = position + velocity*time_step + .5*acceleration*time_step*time_step;
		
		if(Math.abs(endPosition - position) > (time_step/5)) {
			endAccel = ((power*time_step / (endPosition - position)) - rollingFriction)/mass;
			accelRegime = 1;
		} else {
			endAccel = ((power*time_step / (.1)) - rollingFriction)/mass;
			accelRegime = 0;
		}
		
		//apply brakes over train power
		if(trainBrakeOn) {
			endAccel = -trainBrake; 
		}
		if(trainEmergencyBrakeOn) {
			endAccel = -trainEmergencyBrake;
		}
		
		endVelocity = velocity + .5*(acceleration + endAccel)*time_step;
		
		if(endVelocity < 0) { //train cannot go backwards
			endVelocity = 0;
		}
		
		if(endVelocity > maxSpeed) {
			endVelocity = maxSpeed;
			endAccel = 0; 
		}
		
		position = endPosition;
		velocity = endVelocity;
		acceleration = endAccel;
		
		time += time_step;
	}
	
}