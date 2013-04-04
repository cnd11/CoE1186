public class TestTrainMotion {

	public static void main(String[] args) {
		TrainMotion test = new TrainMotion();	
		

		test.setPower(120000);
		test.setGrade(-1);
		for(int i = 0; i < 10000000; i++) {
			if(i % 10000 == 0 && i < 200000) {
				test.printState();
			}
			test.motionStep();
		}	
		
		test.setPower(0);
		test.setEmergencyBrake(true);
		
		for(int i = 0; i < 100000; i++) {
			if(i % 10000 == 0) {
				test.printState();
			}
			test.motionStep();
		}	
	}
	
}