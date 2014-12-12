import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class LibraryGUI extends JFrame {

	//-----------Logic Coding--------------
	private static final long serialVersionUID = 1L;
	Library lib = new Library();

	public static void main(String args[]){
		new LibraryGUI();
	}

	//------------GUI coding---------------
	public LibraryGUI(){
		//loading in library data here...
		lib.open();

		//Main window setup
		JPanel window = new JPanel();
		window.setBorder(BorderFactory.createEtchedBorder());
		window.setLayout(new BorderLayout());
		setSize(450, 400);
		setTitle("Library");

		//==============================Save and Exit Coding====================================
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				lib.save();
				System.exit(0);
			}
		});

		//==========================================================================================
		setLocationRelativeTo(null);

		//Text-Java List Code
		JList<String> list = new JList<String>();
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(lib.listAllItems());
		window.add(new JScrollPane(list));
		add(window, BorderLayout.CENTER);

		//operation buttons
		JPanel buttons = new JPanel();
		buttons.setBorder(BorderFactory.createEtchedBorder());
		buttons.setLayout(new FlowLayout());
		JButton add, loan, unloan, delete;
		buttons.add(add = new JButton("Add New Item"));
		buttons.add(loan = new JButton("Loan Item"));
		buttons.add(unloan = new JButton("Return Item"));
		buttons.add(delete = new JButton("Delete Item"));
		add(buttons, BorderLayout.SOUTH);

		//------------Action Listeners----------
		//Add Items (1 of 4)
		ActionListener AddItemListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				lib.addNewItem();
				list.setListData(lib.listAllItems());
			}
		};
		add.addActionListener(AddItemListener);

		//Loan Items (2 of 4)
		ActionListener LoanItemListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Object selected = list.getSelectedValue();
				String s = selected.toString();
				String title = s.substring(0, s.lastIndexOf("("));
				lib.markItemOnLoan(title.trim());
				list.setListData(lib.listAllItems());
			}
		};
		loan.addActionListener(LoanItemListener);

		//Return (unloan button) Items (3 of 4)
		ActionListener ReturnItemListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Object selected = list.getSelectedValue();
				String s = selected.toString();
				String title = s.substring(0, s.lastIndexOf("("));
				lib.markItemReturned(title.trim());
				list.setListData(lib.listAllItems());
			}
		};
		unloan.addActionListener(ReturnItemListener);

		//Delete Items (4 of 4)
		ActionListener DeleteItemListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Object selected = list.getSelectedValue();
				String s = selected.toString();
				String title = s.substring(0, s.lastIndexOf("("));
				lib.deleteItem(title.trim());
				list.setListData(lib.listAllItems());
			}
		};
		delete.addActionListener(DeleteItemListener);

		setVisible(true);
	}
}
