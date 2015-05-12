import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class AddTab<T> extends JPanel{
	
	private ContactBook<T> cb;
	private JButton addPerson;
	private TextField name, whereYouMet, location, companySchool, email, phone, lastContacted;
	private TextArea notes;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	
	public AddTab(ContactBook<T> cb) {
		this.cb = cb;
		setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
		add(new JLabel("Add a new contact..."));
		name = new TextField("Name");
		add(name);
		whereYouMet = new TextField("Where you met");
		add(whereYouMet);
		location = new TextField("Location");
		add(location);
		companySchool = new TextField("Company/School");
		add(companySchool);
		email = new TextField("Email address");
		add(email);
		phone = new TextField("Phone number");
		add(phone);
		model = new UtilDateModel();
		model.setDate(2015, 5, 11); //change this to everyday's today date
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.day", "Day");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		add(datePicker);
		lastContacted = new TextField("Last contacted");
		add(lastContacted);
		notes = new TextArea("Notes");
		add(notes);
		addPerson = new JButton("Add to database");
		add(addPerson);
	}
	//from http://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
	  private class ButtonListener implements ActionListener {
		    public void actionPerformed(ActionEvent event){
		      if (event.getSource() == addPerson){
		    	  //or add instance of?
		    	  Contact addedContact = new Contact(name.getSelectedText());
		    	  addedContact.setCompanyOrSchool(companySchool.getSelectedText());
		    	  addedContact.setEmail(email.getSelectedText());
		    	  //addedContact.setLastContacted(null);
		    	  addedContact.setLocation(location.getSelectedText());
		    	  addedContact.setMeetingLoc(whereYouMet.getSelectedText());
		    	  //addedContact.setOtherContact(phone.getSelectedText();
		    	  addedContact.setNotes(notes.getSelectedText());  
		    	  
		    	  Calendar selectedValue = (Calendar) datePicker.getModel().getValue();
		    	  //Date selectedDate = selectedValue.getTime();
		    	  addedContact.setLastContacted(selectedValue);
		    	  
		      }
		    }
		  }
	  //from http://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
	  private class DateLabelFormatter extends AbstractFormatter {
		  
		    private String datePattern = "yyyy-MM-dd";
		    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		     
		    @Override
		    public Object stringToValue(String text) throws ParseException {
		        return dateFormatter.parseObject(text);
		    }
		 
		    @Override
		    public String valueToString(Object value) throws ParseException {
		        if (value != null) {
		            Calendar cal = (Calendar) value;
		            return dateFormatter.format(cal.getTime());
		        }
		         
		        return "";
		    }
		 
		}
	  
}
