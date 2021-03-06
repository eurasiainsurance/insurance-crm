package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.Request;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.MainFacade;
import tech.lapsa.insurance.crm.beans.i.SettingsHolder;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.insurance.dao.RequestFilter.ShowMode;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyOptionals;

@Named("mainFacade")
@RequestScoped
public class MainFacadeBean implements MainFacade, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String doInitialize() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	initFilter();
	return null;
    }

    @Override
    public String doResetFilter() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	resetFilter();
	return null;
    }

    public String doFilterCreatedClear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setClear(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedToday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setToday(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedYesterday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setYesterday(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedThisWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisWeek(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedLastWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastWeek(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedThisMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisMonth(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedLastMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastMonth(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedThisYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisYear(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedLastYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastYear(f::setCreatedAfter, f::setCreatedBefore);
	return null;
    }

    public String doFilterCreatedMinusDay() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.minusDays(1));
	return null;
    }

    public String doFilterCreatedMinusWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.minusWeeks(1));
	return null;
    }

    public String doFilterCreatedMinusMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.minusMonths(1));
	return null;
    }

    public String doFilterCreatedMinusYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.minusYears(1));
	return null;
    }

    public String doFilterCreatedPlusDay() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.plusDays(1));
	return null;
    }

    public String doFilterCreatedPlusWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.plusWeeks(1));
	return null;
    }

    public String doFilterCreatedPlusMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.plusMonths(1));
	return null;
    }

    public String doFilterCreatedPlusYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCreated(c -> c.plusYears(1));
	return null;
    }

    public String doFilterCompletedClear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setClear(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedToday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setToday(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedYesterday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setYesterday(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedThisWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisWeek(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedLastWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastWeek(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedThisMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisMonth(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedLastMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastMonth(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedThisYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setThisYear(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedLastYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	final RequestFilter f = settingsHolder.getRequestFilter();
	setLastYear(f::setCompletedAfter, f::setCompletedBefore);
	return null;
    }

    public String doFilterCompletedMinusDay() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.minusDays(1));
	return null;
    }

    public String doFilterCompletedMinusWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.minusWeeks(1));
	return null;
    }

    public String doFilterCompletedMinusMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.minusMonths(1));
	return null;
    }

    public String doFilterCompletedMinusYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.minusYears(1));
	return null;
    }

    public String doFilterCompletedPlusDay() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.plusDays(1));
	return null;
    }

    public String doFilterCompletedPlusWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.plusWeeks(1));
	return null;
    }

    public String doFilterCompletedPlusMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.plusMonths(1));
	return null;
    }

    public String doFilterCompletedPlusYear() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	modifyCompleted(c -> c.plusYears(1));
	return null;
    }

    // PRIVATE

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    // epayment-facade (remote)

    @EJB
    private EpaymentFacadeRemote epayments;

    // CDIs

    // local

    @Inject
    private SettingsHolder settingsHolder;

    private void initFilter() {
	resetFilter();
	settingsHolder.getRequestFilter().letShowInbox();
    }

    private void resetFilter() {
	final ShowMode currentShowMode = settingsHolder.getRequestFilter().getShowMode();
	settingsHolder.resetFilters();
	settingsHolder.getRequestFilter().setShowMode(currentShowMode);
    }

    @FunctionalInterface
    interface RequestsFinder {
	List<? extends Request> find() throws IllegalArgument;
    }

    private void modifyCreated(final UnaryOperator<LocalDateTime> modifier) {
	final RequestFilter f = settingsHolder.getRequestFilter();
	modifyDates(modifier, f::getCreatedAfter, f::setCreatedAfter, f::getCreatedBefore, f::setCreatedBefore);
    }

    private void modifyCompleted(final UnaryOperator<LocalDateTime> modifier) {
	final RequestFilter f = settingsHolder.getRequestFilter();
	modifyDates(modifier, f::getCompletedAfter, f::setCompletedAfter, f::getCompletedBefore, f::setCompletedBefore);
    }

    private void setToday(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.atStartOfDay();
	final LocalDateTime before = after
		.plusDays(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setClear(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	afterSet.accept(null);
	beforeSet.accept(null);
    }

    private void setYesterday(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.atStartOfDay()
		.minusDays(1);
	final LocalDateTime before = after
		.plusDays(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setThisWeek(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue())
		.atStartOfDay();
	final LocalDateTime before = after
		.plusWeeks(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setLastWeek(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue())
		.atStartOfDay()
		.minusWeeks(1);
	final LocalDateTime before = after
		.plusWeeks(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setThisMonth(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.withDayOfMonth(1)
		.atStartOfDay();
	final LocalDateTime before = after
		.plusMonths(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setLastMonth(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.withDayOfMonth(1)
		.atStartOfDay()
		.minusMonths(1);
	final LocalDateTime before = after
		.plusMonths(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setThisYear(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.atStartOfDay()
		.withDayOfYear(1);
	final LocalDateTime before = after
		.plusYears(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void setLastYear(final Consumer<LocalDateTime> afterSet,
	    final Consumer<LocalDateTime> beforeSet) {
	final LocalDateTime after = LocalDate.now()
		.atStartOfDay()
		.withDayOfYear(1)
		.minusYears(1);
	final LocalDateTime before = after
		.plusYears(1)
		.minusSeconds(1);
	afterSet.accept(after);
	beforeSet.accept(before);
    }

    private void modifyDates(final UnaryOperator<LocalDateTime> modifier,
	    final Supplier<LocalDateTime> afterGet,
	    final Consumer<LocalDateTime> afterSet,
	    final Supplier<LocalDateTime> beforeGet,
	    final Consumer<LocalDateTime> beforeSet) {
	MyOptionals.of(afterGet.get())
		.map(modifier)
		.ifPresent(afterSet);
	MyOptionals.of(beforeGet.get())
		.map(modifier)
		.ifPresent(beforeSet);
    }
}
