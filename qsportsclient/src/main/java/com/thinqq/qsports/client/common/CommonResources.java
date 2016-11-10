package com.thinqq.qsports.client.common;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface CommonResources extends ClientBundle {
	@Source("blue-loading.gif")
	ImageResource loadingImage();
	
	@Source("loading_icon_small.gif")
	ImageResource loadingImageSmall();
	
	@Source("warning-icon.png")
	ImageResource warningImage();
	
	@Source("info-icon.png")
	ImageResource infoImage();
	
	@Source("error-icon.png")
	ImageResource errorImage();
	
	@Source("profile_64.png")
	ImageResource profileImage();
	
	@Source("Common.css")
	CommonCss getStyle();

	
	
	interface CommonCss extends CssResource{
		String gbutton();
		String actionButton();
		String errorcell();
		String warningcell();
		String informationcell();
		String input_textbox_error();
		String input_errordialog_box();
		String successMessage();
		String failureMessage();
		String warningMessage();
		String message();
		String ownerLinkStyle();
		String ownerCloseButtonStyle();
	}
}
