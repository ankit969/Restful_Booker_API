package reporting;

public class ExtentLogger {
	
	public static void logRequest(String message) {
        ExtentTestManager.getTest().info("REQUEST: " + message);
    }

    public static void logResponse(String message) {
        ExtentTestManager.getTest().info("RESPONSE: " + message);
    }

}
