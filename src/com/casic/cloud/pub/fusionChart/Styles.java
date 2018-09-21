/**
 * 
 */
package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Fusionchart的styles参数
 * @author Administrator
 *
 */
public class Styles {
	private List<Definition> definition = new ArrayList<Definition>();
	private List<Application> application = new ArrayList<Application>();
	
	public List<Definition> getDefinition() {
		return definition;
	}

	public void setDefinition(List<Definition> definition) {
		this.definition = definition;
	}

	public List<Application> getApplication() {
		return application;
	}

	public void setApplication(List<Application> application) {
		this.application = application;
	}

	public class Definition{
		public String name = "CaptionFont";
		public String type = "font";
		public String size = "12";
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
	}
	
	public class Application{
		public String toobject = "CAPTION";
		public String styles = "CaptionFont";
		
		public String getToobject() {
			return toobject;
		}
		public void setToobject(String toobject) {
			this.toobject = toobject;
		}
		public String getStyles() {
			return styles;
		}
		public void setStyles(String styles) {
			this.styles = styles;
		}
	}
}
