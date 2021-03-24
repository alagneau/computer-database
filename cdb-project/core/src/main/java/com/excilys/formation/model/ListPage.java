package com.excilys.formation.model;

import java.io.Serializable;

import org.springframework.data.domain.Sort.Direction;

import com.excilys.formation.logger.CDBLogger;

public class ListPage implements Serializable {
	private static final long serialVersionUID = 3459132960105476945L;

	public enum OrderByValues {
		ID("id"), COMPUTER("name"), INTRODUCED("introduced"),
		DISCONTINUED("discontinued"), COMPANY("company.name");

		private String sqlRequest;

		OrderByValues(String value) {
			this.sqlRequest = value;
		}

		public String getRequest() {
			return sqlRequest;
		}
	}

	private int index, numberOfValues, maxPageValue;
	private long maxComputers;
	private String searchValue = "";
	private OrderByValues orderByValue = OrderByValues.ID;
	private Direction orderByDirection = Direction.ASC;

	private transient CDBLogger logger = new CDBLogger(ListPage.class);

	private ListPage(ListPageBuilder builder) {
		this.index = builder.index;
		this.numberOfValues = builder.numberOfValues;
		this.maxComputers = builder.maxComputers;
		this.searchValue = builder.searchValue;
		updateMaxPageValue();
	}

	public int getIndex() {
		return index;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public OrderByValues getOrderByValue() {
		return orderByValue;
	}

	public Direction getOrderByDirection() {
		return orderByDirection;
	}

	public int getMaxPageValue() {
		updateMaxPageValue();
		return maxPageValue;
	}

	public int getNumberOfValues() {
		return numberOfValues;
	}

	public long getMaxComputers() {
		return this.maxComputers;
	}

	public int getOffset() {
		return (index - 1) * numberOfValues;
	}

	public void changePage(int pageIndex) {
		if (pageIndex > 0) {
			if (pageIndex > maxPageValue) {
				index = 1;
			} else {
				index = pageIndex;
			}
		} else {
			index = maxPageValue;
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

	public void changeOrderByValue(OrderByValues newOrder) {
		changePage(1);
		if (this.orderByValue == newOrder) {
			orderByDirection = (orderByDirection == Direction.ASC) ? Direction.DESC
					: Direction.ASC;
		} else {
			this.orderByValue = newOrder;
			this.orderByDirection = Direction.ASC;
		}
	}

	public void setMaxComputers(long maxComputers) {
		this.maxComputers = maxComputers;
		updateMaxPageValue();
	}

	public static class ListPageBuilder {
		private int index, numberOfValues, maxComputers;
		private String searchValue = "";

		public ListPageBuilder() {
		}

		public ListPageBuilder index(int index) {
			this.index = index;
			return this;
		}

		public ListPageBuilder numberOfValues(int numberOfValues) {
			this.numberOfValues = numberOfValues;
			return this;
		}

		public ListPage build() {
			ListPage page = new ListPage(this);

			return page;
		}
	}

	private void updateMaxPageValue() {
		try {
			maxPageValue = ((int) maxComputers - 1) / numberOfValues + 1;
		} catch (ArithmeticException exception) {
			logger.info("ListPage.updateMaxPageValue" + exception.getMessage());
			maxPageValue = 1;
		}
	}

}
