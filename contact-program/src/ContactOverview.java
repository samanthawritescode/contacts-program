import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

/**
 * Created by amitsuzawa on 5/14/15.
 */
public class ContactOverview extends JPanel{

    private ContactBook<Contact> cb;
    private JLabel searchBy, searchResult;
    private JComboBox<String> searchCriteria;
    protected DefaultComboBoxModel<String> searchResultData;
    private String[] searchCriteriaData = {"All Contacts", "Name", "City", "Company/School", "Meeting Location", "Email", "Phone", "Notes"};
    private JButton findButton;
    private JComboBox<String> contactSearch;
    private JPanel northPanel, westPanel, eastPanel, southPanel;
    protected JLabel name, city, company, meetingLoc, email, phone, notes, lastContacted;
    private JTextField searchDetail;
    private JButton newButton, editButton, deleteButton;
    private ContactOverview mainPanel;

    public ContactOverview(ContactBook program){
        mainPanel = this;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        cb = program;
        northPanel = new JPanel();
        searchBy = new JLabel("Search by");
        northPanel.add(searchBy);
        searchCriteria = new JComboBox<>(searchCriteriaData);
        northPanel.add(searchCriteria);
        searchDetail = new JTextField("Search Details", 12);
        northPanel.add(searchDetail);
        findButton = new JButton("Find");
        findButton.addActionListener(new ButtonListener());
        northPanel.add(findButton);
        add(northPanel);

        westPanel = new JPanel();
        searchResult = new JLabel("Search results");
        westPanel.add(searchResult);
        searchResultData = new DefaultComboBoxModel<>();
        contactSearch = new JComboBox<>(searchResultData);
        contactSearch.setSelectedItem(null);
        contactSearch.addItemListener(new ComboBoxListener());
        westPanel.add(contactSearch);
        add(westPanel);

        eastPanel = new JPanel();
        GridLayout grid = new GridLayout(9,2);
        grid.setVgap(10);
        //grid.setHgap(20);
        eastPanel.setLayout(grid);
        eastPanel.add(new JLabel("Contact information"));
        eastPanel.add(new JLabel());
        eastPanel.add(new JLabel("Name:"));
        name = new JLabel("");
        eastPanel.add(name);
        eastPanel.add(new JLabel("City:"));
        city = new JLabel("");
        eastPanel.add(city);
        eastPanel.add(new JLabel("Company:"));
        company = new JLabel("");
        eastPanel.add(company);
        eastPanel.add(new JLabel("Meeting Location:"));
        meetingLoc = new JLabel("");
        eastPanel.add(meetingLoc);
        eastPanel.add(new JLabel("Email:"));
        email = new JLabel("");
        eastPanel.add(email);
        eastPanel.add(new JLabel("Phone:"));
        phone = new JLabel("");
        eastPanel.add(phone);
        eastPanel.add(new JLabel("Notes:"));
        notes = new JLabel("");
        eastPanel.add(notes);
        eastPanel.add(new JLabel("Last Contacted:"));
        lastContacted = new JLabel("");
        eastPanel.add(lastContacted);
        add(eastPanel);

        add(Box.createVerticalStrut(10));

        southPanel = new JPanel();
        newButton = new JButton("New");
        southPanel.add(newButton);
        newButton.addActionListener(new ButtonListener());
        editButton = new JButton("Edit");
        southPanel.add(editButton);
        editButton.addActionListener(new ButtonListener());
        deleteButton = new JButton("Delete");
        southPanel.add(deleteButton);
        deleteButton.addActionListener(new ButtonListener());
        add(southPanel);
    }
    private class ComboBoxListener implements ItemListener {
        private String selectedContactName;

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedContactName = (String) e.getItem();
                Contact selectedContact = cb.getContactByName(selectedContactName);
                name.setText(selectedContact.getName());
                city.setText(selectedContact.getLocation());
                company.setText(selectedContact.getCompanyOrSchool());
                meetingLoc.setText(selectedContact.getMeetingLoc());
                email.setText(selectedContact.getEmail());
                phone.setText(selectedContact.getOtherContact());
                notes.setText(selectedContact.getNotes());
                // converts Calendar to String
                SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
                String currentDate = formatter.format(selectedContact.getLastContacted().getTime());
                lastContacted.setText(currentDate);
            }
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==newButton) {
            	Contact stub = new Contact("New Contact");
                cb.addContact(stub);
                JFrame popup = new ContactChanges(mainPanel, cb, stub);
                searchResultData.addElement(stub.getName());
            }
            if (e.getSource()==deleteButton) {
                cb.deleteContactByName(name.getText());
                searchResultData.removeElement(name.getText());
                contactSearch.setSelectedItem(null);
                //check if there's better way to do this!!!
                name.setText("");
                city.setText("");
                company.setText("");
                meetingLoc.setText("");
                email.setText("");
                phone.setText("");
                notes.setText("");
                lastContacted.setText("");
            }
            if (e.getSource()==findButton) {
            	String detail = searchDetail.getText();
            	String category = searchCriteria.getSelectedItem().toString();
                searchResultData.removeAllElements();
                LinkedList<Contact> results = new LinkedList<Contact>();
                if (category.equals("All Contacts")) {
                    results = cb.getAllContacts();
                } else if (category.equals("Name")) {
                	results = cb.searchByName(detail);
                } else if (category.equals("City")) { 
                	results = cb.searchByLocation(detail);
                } else if (category.equals("Company/School")) { 
                	results = cb.searchByCompanyOrSchool(detail);
                } else if (category.equals("Meeting Location")) { 
                	results = cb.searchByMeetingLoc(detail);
                } else if (category.equals("Email")) { 
                	results = cb.searchByEmail(detail);
                } else if (category.equals("Phone")) { 
                	results = cb.searchByOtherContact(detail);
                } else if (category.equals("Notes")) { 
                	results = cb.searchByNotes(detail);
                }

                if(results.isEmpty()) {
                    name.setText("");
                    city.setText("");
                    company.setText("");
                    meetingLoc.setText("");
                    email.setText("");
                    phone.setText("");
                    notes.setText("");
                    lastContacted.setText("");
                } else {
                    for (int i = 0; i < results.size(); i++) {
                        searchResultData.addElement(results.get(i).getName());
                    }
                }
            }
            if (e.getSource()==editButton) {
                String selectedContactName = (String) contactSearch.getSelectedItem();
                Contact selectedContact = cb.getContactByName(selectedContactName);
                JFrame popup = new ContactChanges(mainPanel, cb, selectedContact);
            }
        }
    }
}
