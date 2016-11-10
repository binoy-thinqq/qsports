package com.thinqq.qsports.client.common.fieldvalidators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.ErrorMessageDialog;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.IValidator;

public abstract class AbstractInputFieldValidator {
	protected abstract String getStringValue();
	private Widget widget;
	private IValidator validator;
	private Image markerImage;
	private ErrorMessageDialog message;
	private String errorStyleName;
	private String oldStyleName;
	private String dialogBoxStyleName;
	private List<ErrorVo> errors = new ArrayList<ErrorVo>();

	protected Widget getWidget(){
		return widget;
	}
	public List<ErrorVo> valdiate(){
		if(!markerImage.isVisible()){
			errors.clear();
			errors = validator.validate(getStringValue());
			if(errors!=null&& errors.size()>0){
				if(errorStyleName!=null){
					oldStyleName = widget.getStyleName();
					widget.setStyleName(errorStyleName);
				}
				markerImage.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						message = new ErrorMessageDialog(errors,dialogBoxStyleName);
						message.setPopupPosition(markerImage.getAbsoluteLeft()+markerImage.getOffsetWidth(), markerImage.getAbsoluteTop());
						message.show();
					}
				});
				markerImage.addMouseOutHandler(new MouseOutHandler() {
					@Override
					public void onMouseOut(MouseOutEvent arg0) {
						if(message!=null){
							message.hide();
							message = null;
						}

					}
				});
				markerImage.setVisible(true);
			}
		}
		return errors;
	}

	public AbstractInputFieldValidator(Widget inputWidet, IValidator validator,
			Image image,String errorStyleName, String dialogBoxStyleName) {
		super();
		this.widget = inputWidet;
		this.validator = validator;
		this.markerImage = image;
		this.errorStyleName = errorStyleName;
		this.dialogBoxStyleName = dialogBoxStyleName;
		if(widget instanceof FocusWidget){
			((FocusWidget)widget).addFocusHandler(new FocusHandler() {

				@Override
				public void onFocus(FocusEvent arg0) {
					markerImage.setVisible(false);
					if(oldStyleName!=null){
						widget.setStyleName(oldStyleName);
					}
				}
			});
		}else if(widget instanceof DateBox){
			((DateBox)widget).addValueChangeHandler(new ValueChangeHandler<Date>() {

				@Override
				public void onValueChange(ValueChangeEvent<Date> arg0) {
					markerImage.setVisible(false);
					if(oldStyleName!=null){
						widget.setStyleName(oldStyleName);
					}
				}
			}); 
		}
	}
	public AbstractInputFieldValidator(Widget inputWidet, IValidator validator,
			Image image){
		super();
		CommonResources commonResources = GWT.create(CommonResources.class);
		this.widget = inputWidet;
		this.validator = validator;
		this.markerImage = image;
		this.errorStyleName = commonResources.getStyle().input_textbox_error();
		this.dialogBoxStyleName = commonResources.getStyle().input_errordialog_box();
		if(widget instanceof FocusWidget){
			((FocusWidget)widget).addFocusHandler(new FocusHandler() {

				@Override
				public void onFocus(FocusEvent arg0) {
					markerImage.setVisible(false);
					if(oldStyleName!=null){
						widget.setStyleName(oldStyleName);
					}
				}
			});
		}else if(widget instanceof DateBox){
			((DateBox)widget).addValueChangeHandler(new ValueChangeHandler<Date>() {

				@Override
				public void onValueChange(ValueChangeEvent<Date> arg0) {
					markerImage.setVisible(false);
					if(oldStyleName!=null){
						widget.setStyleName(oldStyleName);
					}
				}
			}); 
		}
	}
}
