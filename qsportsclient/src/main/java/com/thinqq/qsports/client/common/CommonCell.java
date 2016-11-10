package com.thinqq.qsports.client.common;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**

 */
	public interface CommonCell extends SafeHtmlTemplates {

		@SafeHtmlTemplates.Template("<table style=\"text-align:center\" width=\"100%\" height=\"{0}px\" class=\"{1}\"><tr><td width=\"30px\"><img src=\"images\\error-icon.png\"></img>&nbsp;</td><td style=\"padding-bottom:4px\">{2}</td></tr></table>")
		SafeHtml error(int height, String errorClass,String errorMessage);
		@SafeHtmlTemplates.Template("<table style=\"text-align:center\" width=\"100%\" height=\"{0}px\" class=\"{1}\"><tr><td width=\"30px\"><img src=\"images\\info-icon.png\"></img>&nbsp;</td><td style=\"padding-bottom:4px\">{2}</td></tr></table>")
		SafeHtml message(int height, String errorClass,String errorMessage);
		@SafeHtmlTemplates.Template("<table style=\"text-align:center\" width=\"100%\" height=\"{0}px\" class=\"{1}\"><tr><td width=\"30px\"><img src=\"images\\warning-icon.png\"></img>&nbsp;</td><td style=\"padding-bottom:4px\">{2}</td></tr></table>")
		SafeHtml warning(int height, String errorClass,String errorMessage);

	}
