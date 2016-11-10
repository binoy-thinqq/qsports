package com.thinqq.qsports.client.cricket.widgets;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**

 */
	public interface CricketCell extends SafeHtmlTemplates {

		@SafeHtmlTemplates.Template("<table style=\"width:100%\">"
				+"<tr>"
				+"<td width=\"20%\">"
				+"<img width=\"60px\" height=\"60px\" src=\"{0}\"></img>"
				+"</td>"
				+"<td width=\"80%\">"
				+"<div class=\"subtitle\">{1}</div>"
				+"<div class=\"greyInfo\">{3}</div>"
				+"<div class=\"greyInfo\">{4}</div>"
				+"<div class=\"greyInfo\">Mat: {5}  Runs: {6}  Wkts: {7}</div>"
				+"</td>"
				+"</tr>"
				+"</table>")
		SafeHtml playerCell(String pictureURL, String name, String link, String batting, String bowling, int matches, int runs, int wkts);
		
		@SafeHtmlTemplates.Template("<table style=\"width:100%;border-bottom:1px solid #EEEEEE; padding:2px 2px 2px 2px\">"
				+"<tr>"
				+"<td width=\"30%\">"
				+"<div class=\"dark\"><a href=\"{2}\">{1}</a></div>"
				+"</td>"
				+"<td width=\"30%\">"				
				+"<div class=\"greyInfo\">{3}, {4}</div>"
				+"</td>"
				+"<td width=\"25%\">"	
				+"<div class=\"greyInfo\">Mat: {5}  Won: {6}  Lost: {7}</div>"
				+"</td>"
				+"<td width=\"15%\">"
				+"<div class=\"greyInfo\">Win % - {8}</div>"
				+"</td>"
				+"</tr>"
				+"</table>")
		SafeHtml teamCell(String pictureURL, String name, String link, String cityState, String country, int matches, int won, int lost, String winPercent);
		
		@SafeHtmlTemplates.Template("<table style=\"width:100%;border-bottom:1px solid #EEEEEE\">"
				+"<tr>"
				+"<td width=\"30%\">"
				+"<div class=\"dark\">Team Name</div>"
				+"</td>"
				+"<td width=\"30%\">"				
				+"<div class=\"dark\">Location</div>"
				+"</td>"
				+"<td width=\"25%\">"	
				+"<div class=\"dark\">Quick Statistics</div>"
				+"</td>"
				+"<td width=\"15%\">"
				+"<div class=\"dark\">Win Percentage</div>"
				+"</td>"
				+"</tr>"
				+"</table>")
		SafeHtml teamHeaderCell();
		
		@SafeHtmlTemplates.Template("<table>"
				+"<tr>"
				+"<td>"
				+"<div class=\"dark\">{0}</div>"
				+"</td>"
				+"<td>"				
				+"- vs -"
				+"</td>"
				+"<td>"	
				+"<div class=\"dark\">{1}</div>"
				+"</td>"
				+"</tr>"
				+"</table>")
		SafeHtml matchTitleCell(String team1, String team2);
		
		@SafeHtmlTemplates.Template("<a href=\"{1}\">{0}</a>")
		SafeHtml link(String name, String link);
		
		@SafeHtmlTemplates.Template("<div height=\"{0}\" class=\"{1}\">{2}"+
				"{3}</br>"+
				"<span class=\"{11}\">{4}</span>&nbsp;vs&nbsp;<span class=\"{11}\">{5}</span></br>"+
				"{6}</br> "+
				"{7}</br>"+
				"{8}</br>"+
				"{9}</br>"+
				"{10}"+
				"</div>")
		SafeHtml matchCellWithPlayerData(int height_0, String matchCellClass_1,String battingString_2, String bowlingString_3, String team1_4, String team2_5, 
				String matchData_6,String wonBy_7, String innings1_8,String innings2_9, String venue_10, String teamNameClass_11);
		
		@SafeHtmlTemplates.Template("<div height=\"{0}\" class=\"{1}\">"+
				"<span class=\"{9}\">{2}</span>&nbsp;vs&nbsp;<span class=\"{9}\">{3}</span></br>"+
				"{4}</br> "+
				"{5}</br>"+
				"{6}</br>"+
				"{7}</br>"+
				"{8}"+
				"</div>")
		SafeHtml matchCell(int height_0, String matchCellClass_1, String team1_2, String team2_3, 
				String matchData_4,String wonBy_5, String innings1_6,String innings2_7, String venue_8, String teamNameClass_9);
	}
