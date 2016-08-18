/**
Copyright (C) 2016  Rodrigo Ramos Rosa

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
**/

package readernotes.src.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import readernotes.src.core.ReadingFile;
import readernotes.src.core.Library;
import readernotes.src.exceptions.EmptyTitleException;
import readernotes.src.exceptions.DoubleEntryException;

public class NewReadingFileForm extends JFrame {
	private static NewReadingFileForm _instance;
	private JScrollPane _titleArea;
	private JScrollPane _bookTitleArea;
	private JScrollPane _subjectArea;
	private JScrollPane _contentArea;

	public NewReadingFileForm() {
		this.initUI();
		this.setVisible(true);
	}

	private void setTitleArea(JScrollPane titleArea) {
		_titleArea = titleArea;
	}

	private JScrollPane getTitleArea() {
		return _titleArea;
	}

	private void setBookTitleArea(JScrollPane bookTitleArea) {
		_bookTitleArea = bookTitleArea;
	}

	private JScrollPane getBookTitleArea() {
		return _bookTitleArea;
	}

	private void setSubjectArea(JScrollPane subjectArea) {
		_subjectArea = subjectArea;
	}

	private JScrollPane getSubjectArea() {
		return _subjectArea;
	}

	private void setContentArea(JScrollPane contentArea) {
		_contentArea = contentArea;
	}

	private JScrollPane getContentArea() {
		return _contentArea;
	}

	private JScrollPane createTitleArea() {
		JTextArea titleArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(titleArea);

		titleArea.setLineWrap(true);
		titleArea.setWrapStyleWord(true);

		scrollPane.setPreferredSize(new Dimension(300,20));
		scrollPane.setMaximumSize(new Dimension(2000,20));

		return scrollPane;
	}

	private JScrollPane createBookTitleArea() {
		JTextArea bookTitleArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(bookTitleArea);

		bookTitleArea.setLineWrap(true);
		bookTitleArea.setWrapStyleWord(true);

		scrollPane.setPreferredSize(new Dimension(250,20));
		scrollPane.setMaximumSize(new Dimension(2000,20));

		return scrollPane;
	}

	private JScrollPane createSubjectArea() {
		JTextArea subjectArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(subjectArea);

		subjectArea.setLineWrap(true);
		subjectArea.setWrapStyleWord(true);

		scrollPane.setPreferredSize(new Dimension(250,20));
		scrollPane.setMaximumSize(new Dimension(2000,20));

		return scrollPane;
	}

	private JScrollPane createContentArea() {
		JTextArea contentArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(contentArea);

		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);

		scrollPane.setPreferredSize(new Dimension(300,65));

		return scrollPane;
	}

	private JLabel createNewLabel(String labelValue) {
		JLabel label = new JLabel(labelValue);

		return label;
	}

	private JButton createSaveButton() {
		JButton saveButton = new JButton("Save");

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//Some are for NewReadingFileForm class.
				try {
					Library library = Library.getInstance();

					JViewport titleViewport = getTitleArea().getViewport();
					JViewport bookTitleViewport = getBookTitleArea().getViewport();
					JViewport subjectViewport = getSubjectArea().getViewport();
					JViewport contentViewport = getContentArea().getViewport();

					JTextArea title = (JTextArea) titleViewport.getView();
					JTextArea bookTitle = (JTextArea) bookTitleViewport.getView();
					JTextArea subject = (JTextArea) subjectViewport.getView();
					JTextArea content = (JTextArea) contentViewport.getView();

					library.addReadingFile(new ReadingFile(title.getText().trim(),
												 			bookTitle.getText().trim(),
															subject.getText().trim(),
												 			content.getText().trim()));
					//Update List on main window
					MainWindow.getInstance().updateReadingFileList();
					dispose();
				} catch (EmptyTitleException
						 | DoubleEntryException exception) {
					System.err.print(exception.getMessage());
				}
			}
		});
		return saveButton;
	}

	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		JLabel label = this.createNewLabel("Title");

		this.setTitleArea(this.createTitleArea());

		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.add(label);
		titlePanel.add(Box.createRigidArea(new Dimension(48,0)));
		titlePanel.add(this.getTitleArea());

		return titlePanel;
	}

	private JPanel createBookTitlePanel() {
		JPanel bookTitlePanel = new JPanel();
		JLabel label = this.createNewLabel("Book Title");

		this.setBookTitleArea(this.createBookTitleArea());

		bookTitlePanel.setLayout(new BoxLayout(bookTitlePanel, BoxLayout.X_AXIS));
		bookTitlePanel.add(label);
		bookTitlePanel.add(Box.createRigidArea(new Dimension(10,0)));
		bookTitlePanel.add(this.getBookTitleArea());

		return bookTitlePanel;
	}

	private JPanel createSubjectPanel() {
		JPanel subjectPanel = new JPanel();
		JLabel label = this.createNewLabel("Subject");

		this.setSubjectArea(this.createSubjectArea());

		subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.X_AXIS));
		subjectPanel.add(label);
		subjectPanel.add(Box.createRigidArea(new Dimension(27,0)));
		subjectPanel.add(this.getSubjectArea());

		return subjectPanel;
	}

	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		JPanel auxPanel = new JPanel();
		JLabel label = this.createNewLabel("Content");

		this.setContentArea(this.createContentArea());

		auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.X_AXIS));
		auxPanel.add(label);
		auxPanel.add(Box.createHorizontalGlue());

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(auxPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0,10)));
		contentPanel.add(this.getContentArea());

		return contentPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(this.createSaveButton());

		return buttonPanel;
	}

	private JPanel createLayout() {
		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(this.createTitlePanel());
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(this.createBookTitlePanel());
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(this.createSubjectPanel());
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(this.createContentPanel());
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(this.createButtonPanel());

		return panel;
	}

	public void initUI() {
		this.add(this.createLayout());
		this.setTitle("New Reading File");
		this.setSize(400,510);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
