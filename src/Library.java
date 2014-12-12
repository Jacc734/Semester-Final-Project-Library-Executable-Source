import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Library {
	int numberOfItems = 0;
	ArrayList<MediaItem> items = new ArrayList<MediaItem>();
	Scanner input = new Scanner(System.in);

	public String[] listAllItems() {
		String[] temp = new String[numberOfItems];
		for (int i = 0; i < numberOfItems; i++){
			if(items.get(i).getOnLoan() == true) {
				temp[i] = items.get(i).getTitle() + " (" + items.get(i).getFormat() + ")" + " loaned to " 
						+ items.get(i).getLoanedTo() + " on " + items.get(i).getDateLoaned();
			}
			else if (items.get(i).getOnLoan() == false){
				temp[i] = items.get(i).getTitle() + " (" + items.get(i).getFormat() + ")";
			}
		}
		return temp;
	}

	public void addNewItem() {
		String title = JOptionPane.showInputDialog("Ready to add a new item to the library!\nWhat is the title? (Enter title): ", null);
		//Cancel Button
		if(title == null){
			return;
		}
		//Blank Title
		if(title.equals("")){
			JOptionPane.showMessageDialog(null, "You didn't enter a title.\nPlease try adding the item again.");
			return;
		}
		//Boolean to get logical response from inside for loop
		//and detect if title already exists.
		for (int i = 0; i < numberOfItems; i++){
			if (items.get(i).getTitle().equalsIgnoreCase(title)){
				JOptionPane.showMessageDialog(null, title + " already exists in your current library data base.\n"
						+ "If this was a mistake you can add it again with a minor change to the title." 
						+ " Otherwise re-enter the correct title.");
				return;
			}
		}
		String format = JOptionPane.showInputDialog("What is the format? (Enter format): ", null);

		//Blank format
		if(format.equals("")){
			JOptionPane.showMessageDialog(null, "You didn't enter a format.\nPlease try adding the item again.");
			return;			
		}
		//cancel button
		if(format.equals(null)){
			return;
		}
		//No errors continue adding
		MediaItem newItem = new MediaItem (title, format);
		newItem.setTitle(title);
		newItem.setFormat(format);
		items.add(numberOfItems, newItem);
		JOptionPane.showMessageDialog(null, items.get(numberOfItems).getTitle() + " (" + items.get(numberOfItems).getFormat() + ")" + " has been added to the library.");
		numberOfItems++;
		return;
	}

	public void markItemOnLoan(String titleToLoan){
		for (int i = 0; i < numberOfItems; i++){

			if(items.get(i).getTitle().equalsIgnoreCase(titleToLoan)){
				if (items.get(i).getOnLoan() == true){
					JOptionPane.showMessageDialog(null, "Sorry " + items.get(i).getTitle() + " is already out on loan."
							+ " It was loaned to " + items.get(i).getLoanedTo() + " on " + items.get(i).getDateLoaned());
					return;
				}

				else if(items.get(i).getOnLoan() == false ){
					String name2 = JOptionPane.showInputDialog("Who are you loaning it to? (Enter name): ", null);
					if (name2 == null){
						return;
					}
					String date2 = JOptionPane.showInputDialog("When did you loan it? (Enter date): ", null);
					if(date2 == null){
						return;
					}
					items.get(i).markOnLoan(name2, date2);
					JOptionPane.showMessageDialog(null, items.get(i).getTitle() + " has been loaned to " 
							+ items.get(i).getLoanedTo() + " on " + items.get(i).getDateLoaned() + ".");
					return;
				}
			}
		}
	}

	public void markItemReturned(String titleToReturn){
		for (int i = 0; i < numberOfItems; i++){

			if(items.get(i).getTitle().equalsIgnoreCase(titleToReturn)){

				if(items.get(i).getOnLoan() == true){
					items.get(i).markReturned();
					JOptionPane.showMessageDialog(null, items.get(i).getTitle() + " has been returned.");
					return;
				}
				else if (items.get(i).getOnLoan() == false){
					JOptionPane.showMessageDialog(null, items.get(i).getTitle() + " is not currently out on loan.");
					return;
				}
			}
		}
	}

	public void deleteItem(String deleteTitle){
		for (int i = 0; i < numberOfItems; i++){
			if(items.get(i).getTitle().equalsIgnoreCase(deleteTitle)){
				JOptionPane.showMessageDialog(null, items.get(i).getTitle() + " has been deleted from your library.");
				items.remove(i);
				items.trimToSize();
				numberOfItems--;
				return;
			}
		}

		for (int i = 0; i < numberOfItems; i++){
			if(!items.get(i).getTitle().equalsIgnoreCase(deleteTitle)){
				JOptionPane.showMessageDialog(null, "Sorry couldn't locate in your library " 
						+ deleteTitle + " and thus could not delete it.");
				return;
			}
		}
	}

	public void save(){
		JOptionPane.showMessageDialog(null, "Data saved!\nGoodbye.");
		try(PrintWriter export = new PrintWriter(new BufferedWriter(new FileWriter("library.txt", false)))) {
			for (int i = 0; i < numberOfItems; i++){
				if(items.get(i).getOnLoan() == true){
					export.println(items.get(i).getTitle() + "*" + items.get(i).getFormat() + "*" 
							+ items.get(i).getLoanedTo() + "*" + items.get(i).getDateLoaned() + "*");
				}
				else if (items.get(i).getOnLoan() == false){
					export.println(items.get(i).getTitle() + "*" + items.get(i).getFormat() + "*");
				}
			}
			//HERE IS INFO PRINTING TO THE DOCUMENT
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem saving the file.");
		}catch (NullPointerException e){
			JOptionPane.showMessageDialog(null, "Could not locate where to save the file data.");
		}
	}

	public void open(){
		try {
			String s = null;
			FileReader rF = new FileReader("library.txt");
			BufferedReader rL = new BufferedReader(rF);

			while((s = rL.readLine()) != null){
				for (String temp: s.split(System.lineSeparator())){

					StringTokenizer star = new StringTokenizer(temp, "*");

					if (star.countTokens() == 2){
						String title = star.nextToken();
						String format = star.nextToken();

						MediaItem newItem = new MediaItem (title, format);
						newItem.setTitle(title);
						newItem.setFormat(format);

						items.add(numberOfItems, newItem);
						numberOfItems++;
					} 

					else if (star.countTokens() == 4){
						String title = star.nextToken();
						String format = star.nextToken();

						MediaItem newItem = new MediaItem (title, format);
						newItem.setTitle(title);
						newItem.setFormat(format);
						newItem.setOnLoan(true);
						newItem.setLoanedTo(star.nextToken());
						newItem.setDateLoaned(star.nextToken());

						items.add(numberOfItems, newItem);
						numberOfItems++;
					}
				}
			}
			rL.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The file that contians the long term library memory could not be found. Will attempt to create one in current directly upon closing.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem reading the file.");
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, "There was an issue loading saved data.");			
		}
	}

}