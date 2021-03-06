/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.base.core.presentation.validation.components.samples;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;
import org.base.utils.messages.MessageFactory;
 
/**
 *
 * @author yrodriguez B
 */
public class ValidatorDateConstrained extends AbstractComponentValidator {
    
    //the message to show
    private Object agaistDateSource;
    
    public static int DATE_BEFORE = 1;
    public static int DATE_AFTER = 2;
    private int datePositionType = DATE_BEFORE;
    private String formatoFecha = "dd-MM-yyyy";

    
    public  ValidatorDateConstrained(Object agaistDateSource) {
        this.agaistDateSource = agaistDateSource;
    }
   
    public  ValidatorDateConstrained( Object agaistDateSource, int placeRange) {
        this(agaistDateSource);
        this.datePositionType = placeRange;
    }
    
    //should be passed a JTextComponent
    @Override
    public boolean validateComponent(JComponent objComponent) {
        //obtain date against
        String dateAgainst = "";
        if(agaistDateSource instanceof JTextComponent) {
            dateAgainst = ((JTextComponent)agaistDateSource).getText();
        } else if(agaistDateSource instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(this.formatoFecha);
            dateAgainst = sdf.format((Date)agaistDateSource);
        } else {
            dateAgainst = agaistDateSource.toString();
        }
        if(!validateRangAgainst(objComponent, dateAgainst)) {
            message = MessageFactory.getMessage("msg_validator_incorrect_date_range", objComponent.getName());
            return false;           
        }        
        return true;
        
    }
    
    //valida un rango de fecha
    private  boolean validateRangAgainst(JComponent objComponentRange, String fecha) {
        String fechaInterna = ((JTextComponent)objComponentRange).getText();
	Date dateToValidate = null;
        Date dateAgainst = null;
	// formato desde el formulario 
	SimpleDateFormat sdf = new SimpleDateFormat(this.formatoFecha);
	try {
            dateToValidate = sdf.parse(fechaInterna);
            dateAgainst = sdf.parse(fecha);
    	} catch (Exception e) {
            return false;
	}        

        if (this.datePositionType == DATE_BEFORE && dateToValidate.after(dateAgainst)) {
            return false;
        }
        else if (this.datePositionType == DATE_AFTER && dateAgainst.after(dateToValidate)) {
            return false;
        }
	return true;
    }      
}
