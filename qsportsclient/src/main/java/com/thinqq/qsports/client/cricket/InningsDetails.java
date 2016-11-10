package com.thinqq.qsports.client.cricket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.thinqq.qsports.client.cricket.widgets.SelectionCell;
import com.thinqq.qsports.client.cricket.widgets.TextCell;
import com.thinqq.qsports.shared.OutTypeEnum;
import com.thinqq.qsports.shared.ScoreCardBattingModel;
import com.thinqq.qsports.shared.ScoreCardBowlingModel;

public class InningsDetails extends Composite implements HasText {

	private static InningsDetailsUiBinder uiBinder = GWT
			.create(InningsDetailsUiBinder.class);

	interface InningsDetailsUiBinder extends UiBinder<Widget, InningsDetails> {
	}

	public InningsDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel inningsPanel;

	@UiField
	CellTable<ScoreCardBattingModel> battingTable;

	@UiField
	Label bowlingCaption;

	@UiField
	DataGrid<ScoreCardBowlingModel> bowlingTable;

	@UiField
	Button addBowler;

	@UiField
	TextBox byes;

	@UiField
	TextBox legbyes;

	private Map<String, String> battingOption;

	private Map<String, String> bowlingOption;

	CricketMatchMessages messages = (CricketMatchMessages) GWT.create(CricketMatchMessages.class);
	ListDataProvider<ScoreCardBattingModel> battingDataProvider = new ListDataProvider<ScoreCardBattingModel>();
	ListDataProvider<ScoreCardBowlingModel> bowlingDataProvider = new ListDataProvider<ScoreCardBowlingModel>();
	static Map<String, String> outTypes = new HashMap<String, String>();
	static {

		for (OutTypeEnum outType : OutTypeEnum.values()) {
			outTypes.put(Integer.toString(outType.getOutTypeId()), outType.getOutTypeName());
		}
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

	public void initializeInnings(Map<String, String> battingOption, Map<String, String> bowlingOption) {
		this.battingOption = battingOption;
		this.bowlingOption = bowlingOption;
		createBattingScoreCard();
		bowlingCaption.setText("Bowling");
		createBowlingPanel();
		addBowler.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<ScoreCardBowlingModel> bowlingList = bowlingDataProvider.getList();
				ScoreCardBowlingModel model = new ScoreCardBowlingModel();
				bowlingList.add(model);
				bowlingTable.setRowData(0, bowlingList);
			}
		});
		// refresh();
	}

	public void refresh() {

	}

	private void createBattingScoreCard() {

		SelectionCell battingNamesCell = new SelectionCell(battingOption);
		Column<ScoreCardBattingModel, String> battingNameColumn = new Column<ScoreCardBattingModel, String>(
				battingNamesCell) {
			@Override
			public String getValue(ScoreCardBattingModel object) {
				return object.getBatsman();
			}
		};
		battingNameColumn
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						// Called when the user changes the value.
						object.setBatsman(battingOption.get(value));
						object.setProfileId(value);
					}
				});

		battingTable.addColumn(battingNameColumn, messages.batsman());
		SelectionCell bowlingNamesCell = new SelectionCell(bowlingOption);
		Column<ScoreCardBattingModel, String> bowlingColumn = new Column<ScoreCardBattingModel, String>(
				bowlingNamesCell) {
			@Override
			public String getValue(ScoreCardBattingModel object) {
				return object.getBowledBy();
			}
		};
		bowlingColumn
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						// Called when the user changes the value.
						object.setBowledBy(bowlingOption.get(value));
						object.setBowledByProfileID(value);
					}
				});
		battingTable.addColumn(bowlingColumn, messages.bowledBy());

		SelectionCell caughtByNamesCell = new SelectionCell(bowlingOption);
		Column<ScoreCardBattingModel, String> caughtByColumn = new Column<ScoreCardBattingModel, String>(
				caughtByNamesCell) {
			@Override
			public String getValue(ScoreCardBattingModel object) {
				return object.getCaughtBy();
			}
		};
		caughtByColumn
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						object.setCaughtBy(bowlingOption.get(value));
						object.setCaughtByProfileID(value);
					}
				});
		battingTable.addColumn(caughtByColumn, messages.caughtBy());

		SelectionCell miscOut = new SelectionCell(outTypes);
		Column<ScoreCardBattingModel, String> miscOutColumn = new Column<ScoreCardBattingModel, String>(
				miscOut) {
			@Override
			public String getValue(ScoreCardBattingModel object) {
				return Integer.toString(object.getOutType());
			}
		};
		miscOutColumn
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						// Called when the user changes the value.
						object.setOutType(Integer.parseInt(value));
					}
				});
		battingTable.addColumn(miscOutColumn, "Out Type");

		// Create name column.
		TextCell runScoredCell = new TextCell(5);
		Column<ScoreCardBattingModel, String> runsScored = new Column<ScoreCardBattingModel, String>(
				runScoredCell) {
			@Override
			public String getValue(ScoreCardBattingModel battingModel) {
				return Integer.toString(battingModel.getRunsScored());

			}
		};
		runsScored
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						// Called when the user changes the value.
						object.setRunsScored(getIntValue(value));
					}
				});
		battingTable.addColumn(runsScored, messages.runs());
		TextCell ballsFacedCell = new TextCell(5);
		Column<ScoreCardBattingModel, String> ballsFaced = new Column<ScoreCardBattingModel, String>(
				ballsFacedCell) {
			@Override
			public String getValue(ScoreCardBattingModel battingModel) {

				return Integer.toString(battingModel.getBallsFaced());

			}
		};
		ballsFaced
				.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
					public void update(int index, ScoreCardBattingModel object,
							String value) {
						// Called when the user changes the value.
						object.setBallsFaced(getIntValue(value));
					}
				});
		battingTable.addColumn(ballsFaced, messages.ballsFaced());
		TextCell fourCell = new TextCell(5);
		Column<ScoreCardBattingModel, String> fours = new Column<ScoreCardBattingModel, String>(
				fourCell) {
			@Override
			public String getValue(ScoreCardBattingModel battingModel) {

				return Integer.toString(battingModel.getFours());

			}
		};
		fours.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
			public void update(int index, ScoreCardBattingModel object,
					String value) {
				// Called when the user changes the value.
				object.setFours(getIntValue(value));
			}
		});
		battingTable.addColumn(fours, messages.fours());
		TextCell sixesCell = new TextCell(5);
		Column<ScoreCardBattingModel, String> sixes = new Column<ScoreCardBattingModel, String>(
				sixesCell) {
			@Override
			public String getValue(ScoreCardBattingModel battingModel) {

				return Integer.toString(battingModel.getSixes());

			}
		};
		sixes.setFieldUpdater(new FieldUpdater<ScoreCardBattingModel, String>() {
			public void update(int index, ScoreCardBattingModel object,
					String value) {
				// Called when the user changes the value.
				object.setSixes(getIntValue(value));
			}
		});
		battingTable.addColumn(sixes, messages.sixes());
		battingTable.setColumnWidth(battingNameColumn, 3, Unit.PCT);
		battingTable.setColumnWidth(bowlingColumn, 3, Unit.PCT);
		battingTable.setColumnWidth(caughtByColumn, 3, Unit.PCT);
		battingTable.setColumnWidth(miscOutColumn, 3, Unit.PCT);
		battingTable.setColumnWidth(runsScored, 1, Unit.PCT);
		battingTable.setColumnWidth(ballsFaced, 1, Unit.PCT);
		battingTable.setColumnWidth(fours, 1, Unit.PCT);
		battingTable.setColumnWidth(sixes, 1, Unit.PCT);
		battingTable.setTableLayoutFixed(true);

		// Connect the table to the data provider.
		battingDataProvider.addDataDisplay(battingTable);
		List<ScoreCardBattingModel> list = battingDataProvider.getList();
		for (int i = 1; i < 12; i++) {
			ScoreCardBattingModel model = new ScoreCardBattingModel();
			list.add(model);
		}
		battingTable.setWidth("100%");
	}

	private void createBowlingPanel() {

		SelectionCell bowlingNamesCell = new SelectionCell(bowlingOption);
		Column<ScoreCardBowlingModel, String> bowlingColumn = new Column<ScoreCardBowlingModel, String>(
				bowlingNamesCell) {
			@Override
			public String getValue(ScoreCardBowlingModel object) {
				return object.getBowlerName();
			}
		};
		bowlingColumn.setFieldUpdater(new FieldUpdater<ScoreCardBowlingModel, String>() {

			@Override
			public void update(int index, ScoreCardBowlingModel object, String value) {
				object.setBowlerName(bowlingOption.get(value));
				if (!value.contains("#NOPROFILE#")) {
					object.setBowlerProfileId(Long.valueOf(value));
				}

			}
		});
		bowlingTable.addColumn(bowlingColumn, "Bowler");

		TextCell wicketsCell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> wicketsColumn = new Column<ScoreCardBowlingModel, String>(
				wicketsCell) {
			@Override
			public String getValue(ScoreCardBowlingModel battingModel) {

				return Integer.toString(battingModel.getWickets());

			}
		};

		bowlingTable.addColumn(wicketsColumn, "Wicktes");
		TextCell overBowledCell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> oversBowledColumn = new Column<ScoreCardBowlingModel, String>(
				overBowledCell) {
			@Override

			public String getValue(ScoreCardBowlingModel bowlingModel) {
				return Float.toString(bowlingModel.getOversBowled());

			}
		};
		bowlingTable.addColumn(oversBowledColumn, "Overs");
		TextCell runConcievedcell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> runConColumn = new Column<ScoreCardBowlingModel, String>(
				runConcievedcell) {
			@Override
			public String getValue(ScoreCardBowlingModel battingModel) {
				return Integer.toString(battingModel.getRunsConcieved());
			}
		};
		bowlingTable.addColumn(runConColumn, "Runs");
		TextCell maidenCell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> maidenColumn = new Column<ScoreCardBowlingModel, String>(
				maidenCell) {
			@Override
			public String getValue(ScoreCardBowlingModel battingModel) {
				return Integer.toString(battingModel.getMaiden());

			}
		};
		bowlingTable.addColumn(maidenColumn, "Maiden");

		TextCell wideCell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> wides = new Column<ScoreCardBowlingModel, String>(
				wideCell) {
			@Override
			public String getValue(ScoreCardBowlingModel battingModel) {
				return Integer.toString(battingModel.getWides());

			}
		};
		bowlingTable.addColumn(wides, "Wides");

		TextCell noBallCell = new TextCell(5);
		Column<ScoreCardBowlingModel, String> noBalls = new Column<ScoreCardBowlingModel, String>(
				noBallCell) {
			@Override
			public String getValue(ScoreCardBowlingModel battingModel) {
				return Integer.toString(battingModel.getNoBalls());

			}
		};
		bowlingTable.addColumn(noBalls, "No Balls");

		bowlingTable.setColumnWidth(bowlingColumn, 20, Unit.PCT);
		bowlingTable.setColumnWidth(wicketsColumn, 10, Unit.PCT);
		bowlingTable.setColumnWidth(oversBowledColumn, 10, Unit.PCT);
		bowlingTable.setColumnWidth(runConColumn, 10, Unit.PCT);
		bowlingTable.setColumnWidth(maidenColumn, 10, Unit.PCT);
		bowlingTable.setColumnWidth(wides, 10, Unit.PCT);
		bowlingTable.setColumnWidth(noBalls, 10, Unit.PCT);
		bowlingTable.setWidth("100%");
		bowlingTable.setHeight("200px");
		// Connect the table to the data provider.
		bowlingDataProvider.addDataDisplay(bowlingTable);
		// Add the data to the data provider, which automatically pushes it to
		// the
		// widget.
		List<ScoreCardBowlingModel> list = bowlingDataProvider.getList();
		for (int i = 1; i < 3; i++) {
			ScoreCardBowlingModel model = new ScoreCardBowlingModel();
			list.add(model);
		}
		bowlingTable.setWidth("100%");
		bowlingTable.setRowData(0, list);
	}

	public List<ScoreCardBattingModel> getBattingModelList() {
		return new ArrayList<ScoreCardBattingModel>(battingDataProvider.getList());
	}

	public List<ScoreCardBowlingModel> getBowlingModelList() {

		return new ArrayList<ScoreCardBowlingModel>(bowlingDataProvider.getList());
	}

	public void setBattingModelList(List<ScoreCardBattingModel> battingModel) {
		battingTable.setRowCount(battingModel.size());
		battingTable.setRowData(0, battingModel);
	}

	public void setBowlingModelList(List<ScoreCardBowlingModel> bowlingModel) {
		bowlingTable.setRowData(0, bowlingModel);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private int getIntValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nme) {
			return 0;
		}
	}

	public int getBye() {
		return getIntValue(byes.getText());
	}

	public int getLegBye() {
		return getIntValue(legbyes.getText());
	}
	
	public void displayBattingErrors(List<ScoreCardBattingModel> battingModel){
		int index = 0;
		for(ScoreCardBattingModel model : battingModel) {
			if(model.getErrors() != null && !model.getErrors().isEmpty()) {
				//battingTable.getRowElement(index).addClassName("errorStyle");
			} else {
				//battingTable.getRowElement(index).removeClassName("errorStyle");
			}
			index++;
		}
		
	}
}
