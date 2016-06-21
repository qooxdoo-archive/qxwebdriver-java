/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2012-2013 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
 * Daniel Wagner (danielwagner)

 ************************************************************************ */

package org.oneandone.qxwebdriver.ui.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.core.WidgetImpl;
import org.oneandone.qxwebdriver.ui.table.pane.Scroller;
import org.openqa.selenium.WebElement;

public class Table extends WidgetImpl implements Scrollable {

	public Table(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public List<String> getHeaderLabels() {
		List<WebElement> children = getHeaderCells();
		
		List<String> labels = new ArrayList<String>();

		Iterator<WebElement> itr = children.iterator();
		while (itr.hasNext()) {
			WebElement child = itr.next();
			WebElement label = child.findElement(By.xpath("div[not(contains(@style, 'background-image'))]"));
			Widget labelWidget = driver.getWidgetForElement(label);
			labels.add((String) labelWidget.getPropertyValue("value"));
			//labels.add(label.getText());
		}

		return labels;
	}
	
	protected List<WebElement> getHeaderCells() {
		Widget header = findWidget(By.qxh("*/qx.ui.table.pane.Header"));
		List<WebElement> cells = header
				.getContentElement()
				.findElements(
						By.xpath("div[starts-with(@class, 'qx-table-header-cell')]"));
		
		return cells;
	}
	
	public Widget getHeaderCell(String label) {
		List<WebElement> children = getHeaderCells();
		
		Iterator<WebElement> itr = children.iterator();
		while (itr.hasNext()) {
			WebElement child = itr.next();
			if (label.equals(child.getText())) {
				return driver.getWidgetForElement(child);
			}
		}
		
		return null;
	}
	
	public Widget getHeaderCell(int index) {
		List<WebElement> children = getHeaderCells();
		
		int i = -1;
		Iterator<WebElement> itr = children.iterator();
		while (itr.hasNext()) {
			i++;
			WebElement child = itr.next();
			if (i == index) {
				return driver.getWidgetForElement(child);
			}
		}
		
		return null;
	}
	
	public Widget getColumnMenuButton() {
		Widget scroller = getScroller();
		WebElement button = scroller.getContentElement().findElement(By.xpath("div/div[contains(@class, 'qx-table-header-column-button')]"));
		return driver.getWidgetForElement(button);
	}

	public Scroller getScroller() {
		return (Scroller) findWidget(By.qxh("*/qx.ui.table.pane.Scroller"));
	}

	@Override
	public void scrollTo(String direction, Integer position) {
		getScroller().scrollTo(direction, position);
	}

	@Override
	public Widget scrollToChild(String direction, org.openqa.selenium.By locator) {
		return getScroller().scrollToChild(direction, locator);
	}

	@Override
	public Long getMaximum(String direction) {
		return getScroller().getMaximum(direction);
	}

	@Override
	public Long getScrollPosition(String direction) {
		return getScroller().getScrollPosition(direction);
	}
	
	public WebElement scrollToRow(Integer rowIndex) {
		return getScroller().scrollToRow(rowIndex);
	}
	
	public WebElement getCellByText(String text) {
		String cellPath = "//div[contains(@class, 'qooxdoo-table-cell') and text()='" + text + "']";
		scrollToChild("y", org.openqa.selenium.By.xpath(cellPath));
		return findElement(org.openqa.selenium.By.xpath(cellPath));
	}
	
	/**
	 * Return the text in the given cell of the table.
	 * 
	 * @param rowIdx Row index (from 0)
	 * @param colIdx Column index (from 0)
	 * @return Text in cell
	 */
	public String getCellText(long rowIdx, long colIdx) {
		return getCellElement(rowIdx, colIdx).getText();
	}
	
	public WebElement getCellElement(long rowIdx, long colIdx) {
		String cellPath;
		if (getClassname().equals("qx.ui.treevirtual.TreeVirtual")) {
			// 
			// Hierarchy: 
			// * TreeVirtual div (content element)
			//   * composite div
			//     * scroller div (tree column)
			//       * composite div (for header)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//     * scroller (other columns)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//
			// TODO: handle meta columns: this code assumes [1, -1] for metaColumntCounts
			//System.out.println(this.getPropertyValueAsJson("metaColumnCounts"));
			if (colIdx == 0) {
				cellPath = "./div[1]/div[1]/div[2]//div[contains(@class, 'qooxdoo-table-cell')]/" + 
						"parent::div[count(preceding-sibling::div) = " + (rowIdx) + "]/" +
						"div";
			} else {
				cellPath = "./div[1]/div[2]/div[2]//div[contains(@class, 'qooxdoo-table-cell')]/" + 
						"parent::div[count(preceding-sibling::div) = " + (rowIdx) + "]/" +
						"div[position() = " + (colIdx) + "]";
			}
		} else {
			cellPath = ".//div[contains(@class, 'qooxdoo-table-cell')]/" + 
					"parent::div[count(preceding-sibling::div) = " + (rowIdx) + "]/" +
					"div[position() = " + (colIdx + 1) + "]";
		}
		return findElement(org.openqa.selenium.By.xpath(cellPath));
	}
	
	/**
	 * Return the index of the row containing the supplied text <code>text</code>
	 * at column <code>colIdx</code>.
	 *  
	 * @param colIdx Index of column (from 0) that should contain the text
	 * @param text Text to search for
	 * @return The row index (from 0) or -1 if the text was not found
	 */
	public long getRowIndexForCellText(long colIdx, String text) {
		String cellPath;
		if (getClassname().equals("qx.ui.treevirtual.TreeVirtual")) {
			// Hierarchy: 
			// * TreeVirtual div (content element)
			//   * composite div
			//     * scroller div (tree column)
			//       * composite div (for header)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//     * scroller (other columns)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//
			// TODO: handle meta columns: this code assumes [1, -1] for metaColumnCounts
			//System.out.println(this.getPropertyValueAsJson("metaColumnCounts"));
			if (colIdx == 0) {
				cellPath = "./div[1]/div[1]/div[2]//div[contains(@class, 'qooxdoo-table-cell')]";
			} else {
				cellPath = "./div[1]/div[2]/div[2]//div[contains(@class, 'qooxdoo-table-cell') and position() = " + colIdx + "]";
			}
		} else {
			cellPath = ".//div[contains(@class, 'qooxdoo-table-cell') and position() = " + (colIdx + 1) + "]";
		}
		List<WebElement> els = findElements(org.openqa.selenium.By.xpath(cellPath));

		for (int rowIdx = 0; rowIdx < els.size(); rowIdx++) {
			String s = els.get(rowIdx).getText().trim();
			if (text.equals(s))
				return rowIdx;
		}
		return -1L;
	}
	
	
	/**
	 * Return a list of indexes of rows containing the supplied text <code>text</code>
	 * at column <code>colIdx</code>.
	 *  
	 * @param colIdx Index of column (from 0) that should contain the text
	 * @param text Text to search for
	 * @return The a list of row indexes containing the text
	 */
	public List<Long> getRowIndexesForCellText(long colIdx, String text) {
		String cellPath;
		if (getClassname().equals("qx.ui.treevirtual.TreeVirtual")) {
			// Hierarchy: 
			// * TreeVirtual div (content element)
			//   * composite div
			//     * scroller div (tree column)
			//       * composite div (for header)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//     * scroller (other columns)
			//       * clipper div
			//         * pane div
			//           * anonymous div
			//             * row div
			//               * div.qooxdoo-table-cell
			//
			// TODO: handle meta columns: this code assumes [1, -1] for metaColumnCounts
			//System.out.println(this.getPropertyValueAsJson("metaColumnCounts"));
			if (colIdx == 0) {
				cellPath = "./div[1]/div[1]/div[2]//div[contains(@class, 'qooxdoo-table-cell')]";
			} else {
				cellPath = "./div[1]/div[2]/div[2]//div[contains(@class, 'qooxdoo-table-cell') and position() = " + colIdx + "]";
			}
		} else {
			cellPath = ".//div[contains(@class, 'qooxdoo-table-cell') and position() = " + (colIdx + 1) + "]";
		}
		List<WebElement> els = findElements(org.openqa.selenium.By.xpath(cellPath));
		List<Long> rowIdxs = new ArrayList<Long>();
		for (int rowIdx = 0; rowIdx < els.size(); rowIdx++) {
			String s = els.get(rowIdx).getText().trim();
			if (text.equals(s))
				rowIdxs.add((long) rowIdx);
		}
		return rowIdxs;
	}
	
	public List<HashMap> getSelectedRanges() {
		String json = (String) jsRunner.runScript("getTableSelectedRanges", contentElement);
		JSONParser parser = new JSONParser();
		List<HashMap> ranges = null;
		
		Object obj;
		try {
			obj = parser.parse(json);
			JSONArray array = (JSONArray) obj;
			ranges = new ArrayList<HashMap>();

			Iterator<JSONObject> itr = array.iterator();
			while (itr.hasNext()) {
				JSONObject rangeMap = itr.next();
				HashMap<String, Long> range = new HashMap<String, Long>();
				range.put("minIndex", (Long) rangeMap.get("minIndex"));
				range.put("maxIndex", (Long) rangeMap.get("maxIndex"));
				ranges.add(range);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ranges;
	}
	
	public Widget getCellEditor() {
		Widget focusIndicator = getScroller().findWidget(By.qxh("qx.ui.table.pane.Clipper/qx.ui.table.pane.FocusIndicator"));
		Widget editor = focusIndicator.findWidget(By.qxh("child[0]"));
		if (editor.getClassname().equals("qx.ui.container.Composite")) {
			editor = editor.findWidget(By.qxh("child[0]"));
		}
		
		return editor;
	}
	
	public Long getRowCount() {
		Long result = (Long) jsRunner.runScript("getRowCount",
				contentElement);
		return result;
	}
	
	/**
	 * Select the table row at position <code>rowIdx</code>.
	 * 
	 * @param rowIdx the index of the row to select
	 */
	public void selectRow(Long rowIdx) {
		jsRunner.runScript("selectTableRow", contentElement, rowIdx);
	}
	
	public Long getColumnCount() {
		Long result = (Long) jsRunner.runScript("getColumnCount",
				contentElement);
		return result;
	}

	/**
	 * Select the table row at position <code>rowIdx</code>.
	 * 
	 * @param rowIdx the index of the row to select
	 */
	public void setNodeOpened(Long rowIdx, Boolean opened) {
		Long result = (Long) jsRunner.runScript("setTreeNodeOpened",
				contentElement, rowIdx, opened);
	}	
}
