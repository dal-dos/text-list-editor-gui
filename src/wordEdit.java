import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class wordEdit extends JFrame {

	private JPanel contentPane;
	private JTextField txtWord;
	private JScrollPane scrollPane;
	private JList<Object> list;
	private JButton btnRemove;
	private JButton btnAdd;
	private JLabel lblWord;
	DefaultListModel<Object> dlm = new DefaultListModel<>();
	DefaultListModel<Object> sortdlm = new DefaultListModel<>();
	ArrayList<String> words = new ArrayList();
	Boolean dlmShow = true;
	private JButton btnSearch;
	private JButton btnSortBy;
	private JComboBox sortcbox;
	private JTextField txtLetter;
	private JTextField txtPosition;
	private JLabel lblLetter;
	private JButton btnLetterInPosition;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wordEdit frame = new wordEdit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public wordEdit() {
		setUp();
		load();
		createEvents();
	}

	private void load() {
		File myFile = new File("list.txt");
		String data;
		String arr[] = null;
		try {
		Scanner myData = new Scanner(myFile);
		data = myData.nextLine();
		arr = data.split(",");
		dlm.clear();
		words.clear();
		list.removeAll();
		for(int i = 0;i < arr.length;i++) {
			dlm.addElement(arr[i]);
			words.add(arr[i]);
			list.setModel(dlm);
		}
		lblWord.setText(arr.length + " words");
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void createEvents() {
		btnLetterInPosition.addActionListener(new ActionListener() {//letter in pos
			public void actionPerformed(ActionEvent e) {
				try {
				String letters = txtLetter.getText();
				char letter = letters.trim().charAt(0);
				int pos = 0;
				pos = Integer.parseInt(txtPosition.getText()) -1;
				if(sortcbox.getSelectedIndex() == 0) {
					sortdlm.clear();
					for(int i = 0;i < dlm.size();i++) {
						if(dlm.elementAt(i).toString().length() > pos) {
							if(dlm.elementAt(i).toString().charAt(pos) == letter){
								sortdlm.addElement(dlm.elementAt(i));
							}
							list.setModel(sortdlm);
						}
					}
				}else {
					ArrayList sortWord = new ArrayList();
					for(int i = 0;i < sortdlm.size();i++) {
						if(sortdlm.elementAt(i).toString().length() > pos) {
							if(sortdlm.elementAt(i).toString().charAt(pos) == letter){
								sortWord.add(sortdlm.elementAt(i));
							}
						}
					list.setModel(sortdlm);
					}
					sortdlm.clear();
					for(int i = 0;i<sortWord.size();i++) {
						sortdlm.addElement(sortWord.get(i));
					}
				}
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
			}
		});
		
		btnSortBy.addActionListener(new ActionListener() { //sort
			public void actionPerformed(ActionEvent e) {
				if(sortcbox.getSelectedIndex() == 0) {
					dlmShow =true;
					load();
				}else {
					dlmShow = false;
					sortdlm.clear();
					int letters = sortcbox.getSelectedIndex() + 1;
					for(int i = 0;i < words.size();i++) {
						if(words.get(i).length() == (letters)) {
							sortdlm.addElement(words.get(i));
						}
					}
					list.setModel(sortdlm);
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {//search
			public void actionPerformed(ActionEvent e) {
				load();
				sortcbox.setSelectedIndex(0);
				dlmShow=false;
				list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				int arr[]=null;
				String txt = txtWord.getText();
				sortdlm.clear();
				for(int i = 0;i < words.size();i++) {
					if(words.get(i).contains(txt)) {
					sortdlm.addElement(words.get(i));
					System.out.println(i);
					}
				}
				list.removeAll();
				list.setModel(sortdlm);
			}
		});

		btnAdd.addActionListener(new ActionListener() { //add
			public void actionPerformed(ActionEvent e) {
				String txt = txtWord.getText();
				txtWord.setText("");
				dlm.addElement(txt);
				list.removeAll();
				list.setModel(dlm);
				words.add(txt);
			try {
				FileWriter pFile = new FileWriter("list.txt");
				PrintWriter outFile = new PrintWriter(pFile);
				for(int i = 0;i<words.size();i++) {
					outFile.print(words.get(i));
					if(i != words.size() -1) {
						outFile.print(",");
					}
				}
				pFile.close();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			}
		});
		
		btnRemove.addActionListener(new ActionListener() { //remove
			public void actionPerformed(ActionEvent e) {
				String val = (String)list.getSelectedValue();
				int index = words.indexOf(val);
				dlm.remove(index);
				list.setModel(dlm);
				list.removeAll();
				words.remove(index);
			try {
				FileWriter pFile = new FileWriter("list.txt");
				PrintWriter outFile = new PrintWriter(pFile);
				for(int i = 0;i<words.size();i++) {
					outFile.print(words.get(i));
					if(i != words.size() -1) {
						outFile.print(",");
					}
				}
				pFile.close();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			}
			
		});
	}

	private void setUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		
		txtWord = new JTextField();
		txtWord.setColumns(10);
		
		btnAdd = new JButton("Add");

		lblWord = new JLabel("Text");
		
		btnRemove = new JButton("Remove");
		
		btnSearch = new JButton("Search");
		
		btnSortBy = new JButton("Sort By");
		
		sortcbox = new JComboBox();
		sortcbox.setModel(new DefaultComboBoxModel(new String[] {"None", "2 Letters", "3 Letters", "4 Letters", "5 Letters", "6 Letters", "7 Letters", "8 Letters", "9 Letters", "10 Letters", "11 Letters", "12 Letters", "13 Letters", "14 Letters", "15 Letters", "16 Letters", "17 Letters", "18 Letters", "19 Letters", "20 Letters"}));
		sortcbox.setFont(new Font("Courier New", Font.PLAIN, 11));
		
		btnLetterInPosition = new JButton("Letter in position");

		lblLetter = new JLabel("Letter");
		
		JLabel lblPosition = new JLabel("Position");
		
		txtLetter = new JTextField();
		txtLetter.setColumns(10);
		
		txtPosition = new JTextField();
		txtPosition.setColumns(10);
		

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAdd))
						.addComponent(scrollPane, 0, 0, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnLetterInPosition)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtLetter, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLetter))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPosition, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtPosition, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(52, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnSortBy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblWord))
								.addComponent(sortcbox, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(54, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd)
						.addComponent(btnRemove)
						.addComponent(btnSearch)
						.addComponent(lblWord))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSortBy)
								.addComponent(sortcbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLetterInPosition)
								.addComponent(txtLetter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLetter)
								.addComponent(lblPosition))
							.addContainerGap())))
		);
		
		list = new JList();
		list.setFont(new Font("Courier New", Font.PLAIN, 11));
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
	}
}
