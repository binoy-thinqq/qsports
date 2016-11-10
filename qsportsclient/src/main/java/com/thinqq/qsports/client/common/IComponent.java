package com.thinqq.qsports.client.common;

public interface IComponent<T extends IComponentModel,V extends IComponentResource> {

	void setData(T model);
	void cleanData();
	void refreshdata(T model);
	void initialiseComponent(V style);
	void reInitialiseComponent(V style);
	
}
