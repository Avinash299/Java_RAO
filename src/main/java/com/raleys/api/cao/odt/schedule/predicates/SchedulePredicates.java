package com.raleys.api.cao.odt.schedule.predicates;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.Schedule;

@Component
public class SchedulePredicates {
	Logger logger = LoggerFactory.getLogger(SchedulePredicates.class);

	public List<Schedule> checkAllAdvancedFilters(List<Schedule> ws, AdvancedFilters af) {
		if (af.getSource() != null && !af.getSource().isEmpty()) {
			ws = applySource(ws, af);
		}
		if (af.getMinorDept() != null && !af.getMinorDept().isEmpty()) {
			ws = applyMinorDept(ws, af);
		}
		if (af.getVendorId() > 0) {
			ws = applyVendor(ws, af);
		}
		if (af.getOrderDay() != null && !af.getOrderDay().isEmpty()) {
			ws = applyOrderDay(ws, af);
		}
		if (af.getDeliveryDay() != null && !af.getDeliveryDay().isEmpty()) {
			ws = applyDeliveryDay(ws, af);
		}
		if (af.getEffectiveDateFrom() != null && !af.getEffectiveDateFrom().isEmpty() && af.getEffectiveDateTo() != null
				&& !af.getEffectiveDateTo().isEmpty()) {
			ws = applyEffectiveDate(ws, af);
		}
		if (af.getOrderCutoffTime() != null && !af.getOrderCutoffTime().isEmpty()) {
			ws = applyOrderCutOfTime(ws, af);
		}
		if (af.getWindowBegin() != null && !af.getWindowBegin().isEmpty()) {
			ws = applyWindowBegin(ws, af);
		}
		if (af.getWindowEnd() != null && !af.getWindowEnd().isEmpty()) {
			ws = applyWindowEnd(ws, af);
		}
		return ws;
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applySource(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> sourceFilter = s -> s.getSearchSource().equals(af.getSource());
		return ws.stream().filter(sourceFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyOrderCutOfTime(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> cutOfTimeFilter = s -> s.getSearchOrderCutoffTime().equals(af.getOrderCutoffTime());
		return ws.stream().filter(cutOfTimeFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyEffectiveDate(List<Schedule> ws, AdvancedFilters af) {
		DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);
		Predicate<Schedule> effectiveDateFilter = s -> {
			try {
				if (s.getSearchEffectiveDate() != null) {
					String strDate = simpleDateFormat.format(s.getSearchEffectiveDate());
					return (simpleDateFormat.parse(strDate).equals(simpleDateFormat.parse(af.getEffectiveDateFrom()))
							|| simpleDateFormat.parse(strDate).after(simpleDateFormat.parse(af.getEffectiveDateFrom())))
							&& (simpleDateFormat.parse(strDate).equals(simpleDateFormat.parse(af.getEffectiveDateTo()))
									|| simpleDateFormat.parse(strDate)
											.before(simpleDateFormat.parse(af.getEffectiveDateTo())));
				} else {
					return false;
				}

			} catch (Exception e) {
				return false;
			}
		};
		return ws.stream().filter(effectiveDateFilter).collect(Collectors.toList());

	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyDeliveryDay(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> delieveryDayFilter = s -> s.getSearchDeliveryDay().equals(af.getDeliveryDay());
		return ws.stream().filter(delieveryDayFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyWindowBegin(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> beginFilter = s -> s.getSearchDeliveryBegin().equals(af.getWindowBegin());
		return ws.stream().filter(beginFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyWindowEnd(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> endFilter = s -> s.getSearchDeliveryEnd().equals(af.getWindowEnd());
		return ws.stream().filter(endFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyOrderDay(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> orderDayFilter = s -> s.getSearchOrderDay().equals(af.getOrderDay());
		return ws.stream().filter(orderDayFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyVendor(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> vendorFilter = s -> s.getSearchVendorId() == (af.getVendorId());
		return ws.stream().filter(vendorFilter).collect(Collectors.toList());
	}

	/**
	 * @param ws
	 * @param af
	 * @return
	 */
	private List<Schedule> applyMinorDept(List<Schedule> ws, AdvancedFilters af) {
		Predicate<Schedule> minorDeptFilter = s -> s.getSearchMinorDept().equals(af.getMinorDept());
		return ws.stream().filter(minorDeptFilter).collect(Collectors.toList());
	}

}
