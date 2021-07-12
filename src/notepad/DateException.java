package notepad;

public class DateException extends Exception {
	public String toString() {
		return "The date can't less current date.";
	}
}
