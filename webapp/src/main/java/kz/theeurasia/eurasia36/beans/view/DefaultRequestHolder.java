package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.casco.CascoVehicle;

import kz.theeurasia.eurasia36.beans.api.RequestHolder;
import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.RequestTypeService;

@Named("rqst")
@ViewScoped
public class DefaultRequestHolder extends DefaultWritableValueHolder<Request>
	implements Serializable, RequestHolder {
    private static final long serialVersionUID = -2574434730269891652L;

    @Inject
    private RequestTypeService requestTypeService;

    @Override
    public List<CascoVehicle> getCascoVehiclesAsList() {
	if (value == null)
	    return null;
	if (!(value instanceof CascoRequest))
	    return null;
	CascoRequest cascoRequest = (CascoRequest) value;
	Casco casco = cascoRequest.getCasco();
	CascoVehicle vehicle = casco.getInsuredVehicle();
	return Collections.unmodifiableList(Arrays.asList(vehicle));
    }

    @Override
    public RequestType getType() {
	return requestTypeService.type(value);
    }

    @Override
    public String getDisplayName() {
	return requestTypeService.displayName(value);
    }

    @Override
    public void reset() {
	this.value = null;
    }
}
