package com.thinqq.qsports.client.cricket.widgets;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * A {@link Cell} used to render a drop-down list.
 */
public class SelectionCell extends AbstractInputCell<String, String> {

  interface Template extends SafeHtmlTemplates {
    @Template("<option value=\"{0}\">{1}</option>")
    SafeHtml deselected(String option,String value);

    @Template("<option value=\"{0}\" selected=\"selected\">{1}</option>")
    SafeHtml selected(String option,String value);
  }

  private static Template template;

  private HashMap<String, Integer> indexForOption = new HashMap<String, Integer>();

  private final Map<String,String> options;
  
  private final List<String> profileSet;
  /**
   * Construct a new {@link SelectionCell} with the specified options.
   *
   * @param options the options in the cell
   */
  public SelectionCell(Map<String,String> options) {
    super("change");
    if (template == null) {
      template = GWT.create(Template.class);
    }
    this.options = options;
    profileSet = new ArrayList<String>(options.keySet());
    int index = 0;
    for (String option : profileSet) {
      indexForOption.put(option, index++);
    }
  }

  @Override
  public void onBrowserEvent(Context context, Element parent, String value,
      NativeEvent event, ValueUpdater<String> valueUpdater) {
    super.onBrowserEvent(context, parent, value, event, valueUpdater);
    String type = event.getType();
    if ("change".equals(type)) {
      Object key = context.getKey();
      SelectElement select = parent.getFirstChild().cast();
      String keyValue = profileSet.get(select.getSelectedIndex());
      setViewData(key, keyValue);
      finishEditing(parent, keyValue, key, valueUpdater);
      if (valueUpdater != null) {
        valueUpdater.update(keyValue);
      }
    }
  }

  @Override
  public void render(Context context, String value, SafeHtmlBuilder sb) {
    // Get the view data.
    Object key = context.getKey();
    String viewData = getViewData(key);
    if (viewData != null && viewData.equals(value)) {
      clearViewData(key);
      viewData = null;
    }

    int selectedIndex = getSelectedIndex(viewData == null ? value : viewData);
    sb.appendHtmlConstant("<select tabindex=\"-1\" style=\"min-width=180px;\">");
    int index = 0;
    sb.append(template.selected("Entry","Select an entry"));
    for (String option : options.keySet()) {
    	String optionValue = options.get(option);
      if (index++ == selectedIndex) {
        sb.append(template.selected(option,optionValue));
      } else {
        sb.append(template.deselected(option,optionValue));
      }
    }
    sb.appendHtmlConstant("</select>");
  }

  private int getSelectedIndex(String value) {
    Integer index = indexForOption.get(value);
    if (index == null) {
      return -1;
    }
    return index.intValue();
  }
}
