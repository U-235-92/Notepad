package notepad;

import java.util.GregorianCalendar;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Notepad notepad = new Notepad();
//		notepad.writeDownOnToday("Test");
//		notepad.writeDownOnDate("Test", new GregorianCalendar(2020, 10, 22));
//		notepad.getNotes();
//		notepad.showNotes();
		Notepad copy = notepad.clone();
	}
}
