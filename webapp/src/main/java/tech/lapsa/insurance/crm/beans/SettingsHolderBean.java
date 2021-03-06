package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.insurance.crm.beans.i.SettingsHolder;
import tech.lapsa.insurance.dao.RequestFilter;

@Named("settings")
@ViewScoped
public class SettingsHolderBean implements Serializable, SettingsHolder {

    private static final long serialVersionUID = 1L;

    private boolean advanced = false;
    private boolean autoRefresh = true;
    private int autoRefreshInterval = 30;

    private RequestFilter requestFilter;

    private RequestType requestType;

    @Override
    @PostConstruct
    public void resetFilters() {
	requestFilter = new RequestFilter();
	requestType = RequestType.INSURANCE_REQUEST;
    }

    // GENERATED

    @Override
    public boolean isAdvanced() {
	return advanced;
    }

    public void setAdvanced(boolean advanced) {
	this.advanced = advanced;
    }

    @Override
    public boolean isAutoRefresh() {
	return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
	this.autoRefresh = autoRefresh;
    }

    @Override
    public int getAutoRefreshInterval() {
	return autoRefreshInterval;
    }

    public void setAutoRefreshInterval(int autoRefreshInterval) {
	this.autoRefreshInterval = autoRefreshInterval;
    }

    @Override
    public RequestFilter getRequestFilter() {
	return requestFilter;
    }

    @Override
    public RequestType getRequestType() {
	return requestType;
    }

    public void setRequestType(RequestType requestType) {
	this.requestType = requestType;
    }

}
