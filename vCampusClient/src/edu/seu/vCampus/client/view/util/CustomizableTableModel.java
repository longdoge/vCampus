package edu.seu.vCampus.client.view.util;

import javax.swing.table.AbstractTableModel;

public class CustomizableTableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	final Class<T> typeParameterClass;
	T[] feeds;

	public CustomizableTableModel(T[] feeds, Class<T> typeParameterClass) {
		this.feeds = feeds;
		this.typeParameterClass = typeParameterClass;
	}

	public Class getColumnClass(int columnIndex) {
		return typeParameterClass;
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int columnIndex) {
		return null;
	}

	public int getRowCount() {
		return (feeds == null) ? 0 : feeds.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return (feeds == null) ? null : feeds[rowIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}
