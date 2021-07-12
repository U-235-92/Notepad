package notepad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Notepad implements Cloneable {
	private class Note implements Cloneable {
		private String note = "";
		private Calendar date = new GregorianCalendar();
		private Note(String info) {
			note = info;
		}
		private void writeNote(String note, int day, int month, int year) {
			this.date.set(Calendar.YEAR, year);
			this.date.set(Calendar.MONTH, month);
			this.date.set(Calendar.DAY_OF_MONTH, day);
			try {
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String date = "+++ " + df.format(this.date.getTime())+ " +++" + "#";
				String str = note + "\n";
				this.note = date + str;
				String path = "tasks/chapter_5/task_1/Note.txt";
				File fNote = new File(path);
				FileWriter fw;
				BufferedWriter bw;
				if(fNote.exists()) {
					fw = new FileWriter(fNote, true);
					bw = new BufferedWriter(fw);
				} else {
					fw = new FileWriter(fNote);
					bw = new BufferedWriter(fw);
				}
				bw.write(this.note);
				bw.close();
			} catch(IOException exc) {
				System.out.println(exc);
			}
		}
		public Note clone() {
			Note note = null;
			try {
				note = (Note) super.clone();
				note.date = (Calendar) date.clone();
			} catch(CloneNotSupportedException exc) {
				System.out.println(exc);
			}
			return note;
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(note);
			return sb.toString();
		}
	}
	private Note note;
	private Note[] notes = new Note[0];
	private int index;
	public void writeDownOnDate(String info, Calendar calendar) {
		note = new Note(info);
		try {
			if(checkDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.YEAR))) {
				note.writeNote(info, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.YEAR));
			}
		} catch(DateException exc) {
			System.out.println(exc);
		}
	}
	public void writeDownOnToday(String info) {
		Calendar calendar = Calendar.getInstance();
		note = new Note(info);
		note.writeNote(info, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.YEAR));
	}
	public void showNotes() {
		getNotes();
		for(Note note : notes) {
			System.out.println(note);
		}
	}
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Notepad notepad = (Notepad) obj;
		if(notepad.getClass().getSimpleName() != this.getClass().getSimpleName()) {
			return false;
		}
		return true;
	}
	public int hashCode() {
		return 31 * this.getClass().hashCode();
	}
	public Notepad clone() {
		Notepad notepad = null;
		try {
			notepad = (Notepad) super.clone();
			notepad.notes = (Note[]) notes.clone();
		} catch(CloneNotSupportedException exc) {
			System.out.println(exc);
		}
		return notepad;
	}
	private Note[] getNotes() {
		try {
			String tmp = "", path = "tasks/chapter_5/task_1/Note.txt";
			File fNote = new File(path);
			FileReader fr = new FileReader(fNote);
			BufferedReader br = new BufferedReader(fr);
			while((tmp = br.readLine()) != null) {
				makeNote(tmp);
			}
			br.close();
		} catch(IOException exc) {
			System.out.println(exc);
		}
		return notes;
	}
	private void makeNote(String strToParse) {
		String[] result = strToParse.split("#");
		String info = "";
		for(int i = 0; i < result.length; i++) {
			info += result[i] + "\n";
		}
		Note note = new Note(info);
		add(note);
	}
	private void add(Note note) {
		if(index < notes.length) {
			notes[index] = note;
			index++;
		} else {
			int load = 1;
			Note[] tmp = new Note[notes.length + load];
			for(int i = 0; i < notes.length; i++) {
				tmp[i] = notes[i];
			}
			notes = tmp;
			tmp[index] = note;
			index++;
		}
	}
	private boolean checkDate(int day, int month, int year) throws DateException {
		Calendar current = Calendar.getInstance();
		int currentDay = current.get(Calendar.DAY_OF_MONTH);
		int currentMonth = current.get(Calendar.MONTH);
		int currentYear = current.get(Calendar.YEAR);
		if(year < currentYear) {
			throw new DateException();
		} else if(month < currentMonth) {
			throw new DateException();
		} else if(day < currentDay) {
			throw new DateException();
		}
		return true;
	}
}
