package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.utils.security.SecurityUtils;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.facade.UserFacade;

@Named("currentUser")
@SessionScoped
public class CurrentUserHolderBean extends AWritableValueHolder<User>
	implements Serializable, CurrentUserHolder {
    private static final long serialVersionUID = 3813022087120135731L;

    private static final String DEFAULT_REMOTE_USER = "Guest";

    @Inject
    private UserFacade userFacade;

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public void reset() {
	Principal userPrincipal = SecurityUtils.getUserPrincipal();

	String principalName = null;

	if (principalName == null && userPrincipal != null)
	    principalName = userPrincipal.getName();

	if (principalName == null)
	    principalName = SecurityUtils.getRemoteUser();

	if (principalName == null)
	    principalName = DEFAULT_REMOTE_USER;

	value = userFacade.findOrCreate(principalName);
    }

    @Override
    public boolean isCanView() {
	return SecurityUtils.isInRole(InsuranceRoleGroup.VIEWERS);
    }

    @Override
    public boolean isCanChange() {
	return SecurityUtils.isInRole(InsuranceRoleGroup.CHANGERS);
    }

    @Override
    public boolean isCanClose() {
	return SecurityUtils.isInRole(InsuranceRoleGroup.CLOSERS);
    }


}
