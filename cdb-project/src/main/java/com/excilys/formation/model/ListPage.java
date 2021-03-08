package com.excilys.formation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.logger.CDBLogger;


public class ListPage<T> implements Serializable {
	private static final long serialVersionUID = 3459132960105476945L;
	
	private int index, numberOfValues, maxPageValue, maxComputers;
	private String searchValue = "";
	private transient List<T> values;
	
	private transient CDBLogger logger = new CDBLogger(ListPage.class);

	private ListPage(ListPageBuilder<T> builder) {
		this.index = builder.index;
		this.numberOfValues = builder.numberOfValues;
		this.maxComputers = builder.maxComputers;
		this.searchValue = builder.searchValue;
		updateMaxPageValue();
		setValues(new ArrayList<>());
	}

	public int getIndex() {
		return index;
	}

	public List<T> getValues() {
		return values;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public int getMaxPageValue() {
		updateMaxPageValue();
		return maxPageValue;
	}
	
	public int getNumberOfValues() {
		return numberOfValues;
	}

	public int getMaxComputers() {
		return this.maxComputers;
	}
	
	public int getOffset() {
		return (index-1) * numberOfValues;
	}

	public void changePage(int pageIndex) {
		if (pageIndex > 0) {
			this.index = pageIndex;
		}
	}

	public void changeNumberOfValues(int numberOfValues) {
		if (numberOfValues > 0) {
			int offset = getOffset();
			this.numberOfValues = numberOfValues;
			updateMaxPageValue();
			
			if (getOffset() > maxComputers) {
				index = 1;
			} else {
				index = (offset / numberOfValues) + 1;
			}
		}
	}

	public void changeSearchValue(String searchValue) {
		if (searchValue != null) {
			if (searchValue != this.searchValue) {
				index = 1;
			}
	
			this.searchValue = searchValue;
		}
	}

	public void setValues(List<T> values) {
		this.values = values;
	}

	public void setMaxComputers(int maxComputers) {
		this.maxComputers = maxComputers;
		updateMaxPageValue();
	}

	public static class ListPageBuilder<T> {
		private int index, numberOfValues, maxComputers;
		private String searchValue = "";

		public ListPageBuilder() {
		}
		
		public ListPageBuilder<T> index(int index) {
			this.index = index;
			return this;
		}

		public ListPageBuilder<T> numberOfValues(int numberOfValues) {
			this.numberOfValues = numberOfValues;
			return this;
		}

		public ListPage<T> build() {
			ListPage<T> page = new ListPage<T>(this);

			return page;
		}
	}

	private void updateMaxPageValue() {
		try {
			maxPageValue = (maxComputers - 1) / numberOfValues + 1;
		} catch (ArithmeticException exception) {
			logger.info("ListPage.updateMaxPageValue" + exception.getMessage());
			maxPageValue = 1;
		}
	}

}
