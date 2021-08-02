package com.ss.utopia.test.dal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TestAirplaneRepository.class,
	TestAirplaneTypeRepository.class,
	TestAirportRepository.class,
	TestBookingAgentRepository.class,
	TestBookingGuestRepository.class,
	TestBookingPaymentRepository.class,
	TestBookingRepository.class,
	TestBookingUserRepository.class,
	TestFlightBookingRepository.class,
	TestFlightRepository.class,
	TestPassengerRepository.class,
	TestRouteRepository.class,
	TestUserRepository.class,
	TestUserRoleRepository.class
})
public class TestSuite
{

}
