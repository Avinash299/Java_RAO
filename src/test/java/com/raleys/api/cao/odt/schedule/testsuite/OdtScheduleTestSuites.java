package com.raleys.api.cao.odt.schedule.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.raleys.api.cao.odt.schedule.config.RestApiAuthenticationFilterTest;
import com.raleys.api.cao.odt.schedule.controller.CurrentWeekScheduleControllerTest;
import com.raleys.api.cao.odt.schedule.controller.FutureScheduleControllerTest;
import com.raleys.api.cao.odt.schedule.controller.NamedScheduleControllerTest;
import com.raleys.api.cao.odt.schedule.controller.NewScheduleControllerTest;
import com.raleys.api.cao.odt.schedule.controller.StoreVendorInfoControllerTest;
import com.raleys.api.cao.odt.schedule.exception.OdtScheduleExceptionHandlerTest;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleServiceTest;
import com.raleys.api.cao.odt.schedule.service.FutureScheduleServiceTest;
import com.raleys.api.cao.odt.schedule.service.NamedScheduleServiceTest;
import com.raleys.api.cao.odt.schedule.service.NewScheduleServiceTest;
import com.raleys.api.cao.odt.schedule.service.StoreInfoServiceTest;
import com.raleys.api.cao.odt.schedule.service.VendorInfoServiceTest;
import com.raleys.api.cao.odt.schedule.utils.CustomValidatorTest;

/**
 * @author swathi.kompella
 * 
 *         Test suite class for all CAO-ODT microservice testcases
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ StoreVendorInfoControllerTest.class, OdtScheduleExceptionHandlerTest.class,
		CurrentWeekScheduleControllerTest.class, CurrentWeekScheduleServiceTest.class, StoreInfoServiceTest.class,
		VendorInfoServiceTest.class, NewScheduleControllerTest.class, NewScheduleServiceTest.class,
		FutureScheduleControllerTest.class, FutureScheduleServiceTest.class, CustomValidatorTest.class,
		RestApiAuthenticationFilterTest.class,NamedScheduleControllerTest.class,
		NamedScheduleServiceTest.class})
public class OdtScheduleTestSuites {

}
