package com.thinqq.qsports.client.wireframe;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface QSportsWireFrameResources extends ClientBundle {
	
	@Source("qsportswireframe.css")
	public ThinqQWireFrameStyle style();
	
	
	public interface ThinqQWireFrameStyle extends CssResource{ 
		
	}

}
