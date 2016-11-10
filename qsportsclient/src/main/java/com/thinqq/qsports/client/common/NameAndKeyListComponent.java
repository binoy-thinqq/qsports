package com.thinqq.qsports.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.shared.IdUtil;
import com.thinqq.qsports.shared.NameAndKey;

public class NameAndKeyListComponent extends FlowPanel implements ICleanable {
	
	private Map<String, List<Widget>> map  = new HashMap<String, List<Widget>>();
	
	private boolean showRemoveOption; 
	
	CommonResources RESOURCES = GWT.create(CommonResources.class);
	
	private NameAndKeyListComponent(List<NameAndKey> listData, boolean showRemoveOption){
		this.showRemoveOption = showRemoveOption;
		setData(listData);
	}
	
	public static NameAndKeyListComponent getInstance(boolean showRemoveOption){
		return new NameAndKeyListComponent(new ArrayList<NameAndKey>(), showRemoveOption);
	}
	
	public static NameAndKeyListComponent getInstance(List<NameAndKey> listData, boolean showRemoveOption){
		return new NameAndKeyListComponent(listData, showRemoveOption);
	}

	@Override
	public void clean() {
		map.clear();
		clear();
	}
	
	public void setData(List<NameAndKey> listData){
		map.clear();
		if(listData != null){
			for(NameAndKey item : listData){
				add(item);
			}
		}
	}
	
	public void add(NameAndKey item){
		if(map.containsKey(item) || item.getDisplayName() == null || item.getDisplayName().isEmpty()){
			return;
		}
		List<Widget> widgetList = new ArrayList<Widget>();
		if(item.getKey() == null){
			item.setKey(item.getDisplayName());
		}
		if(item.getDisplayName() != null && IdUtil.isJustName(item.getDisplayName())){
			String displayName = IdUtil.removeNamePrefix(item.getDisplayName());
			InlineLabel label = new InlineLabel(displayName);
			add(label);
			widgetList.add(label);
		} else{
			InlineHyperlink link = new InlineHyperlink(item.getDisplayName(), UserProfilePlace.TOKENIZER.getToken(new UserProfilePlace(item.getKey())));
			add(link);
			widgetList.add(link);
		}
		if(showRemoveOption){
			Button removeButton = new Button("X");
			removeButton.setStyleName(RESOURCES.getStyle().ownerCloseButtonStyle(), true);
			removeButton.addClickHandler(new ItemRemoveHandler(item.getKey(), this));
			add(removeButton);
			widgetList.add(removeButton);
		}
		InlineLabel seperator = new InlineLabel("  |  ");
		add(seperator);
		widgetList.add(seperator);
		map.put(item.getKey(), widgetList);
	}
	
	public void remove(NameAndKey item){
		removeByKey(item.getKey());
	}
	
	public void removeByKey(String key){
		List<Widget> widgetList = map.get(key);
		if(widgetList != null){
			for(Widget widget: widgetList){
				remove(widget);
			}
		}
		map.remove(key);
	}
	
	public boolean contains(NameAndKey item){
		return map.containsKey(item.getKey());
	}
	
	public boolean contains(String key){
		return map.containsKey(key);
	}
	
	public Set<String> getKeys(){
		return map.keySet();
	}
	
	private static class ItemRemoveHandler implements ClickHandler{

		private String key;
		private NameAndKeyListComponent parentComponent;

		public ItemRemoveHandler(String key, NameAndKeyListComponent parentComponent ) {
			super();
			this.key = key;
			this.parentComponent = parentComponent;
		}

		@Override
		public void onClick(ClickEvent event) {
			ConfirmationPopUp popUp = new ConfirmationPopUp(
					"Remove Player", 
					"Are you sure you want to remove the player?", 
					new ClickHandler() {								
						@Override
						public void onClick(ClickEvent event) {
							parentComponent.removeByKey(key);
						}
					});
			popUp.show();
			popUp.center();
		}
	}
}
