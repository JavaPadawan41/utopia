package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.FlightFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Flight;
import com.ss.utopia.model.User;


public class FlightRepository extends RepositoryBase<Flight>
{
	private static final String basicFlightQuery = "WITH origin(route_id, origin_id, origin_city)" + 
			"AS (" + 
			"SELECT route.id, route.origin_id, airport.city" + 
			"FROM route" + 
			"JOIN airport ON route.origin_id = airport.iata_id" + 
			")," + 
			"destination(route_id, destination_id, destination_city)" + 
			"AS (" + 
			"SELECT route.id, route.destination_id, airport.city" + 
			"FROM route" + 
			"JOIN airport ON route.origin_id = airport.iata_id" + 
			")," + 
			"rt (route_id, origin_id, origin_city, destination_id, destination_city)" + 
			"AS (" + 
			"SELECT origin.route_id, origin.origin_id, origin.origin_city,  " + 
			"destination.destination_id, destination.destination_city" + 
			"FROM origin" + 
			"JOIN destination on origin.route_id = destination.route_id" + 
			")" + 
			"SELECT rt.route_id, rt.origin_id, rt.origin_city, rt.destination_id, rt.destination_city FROM rt " + 
			"JOIN flight on rt.route_id = flight.route_id " + 
			"JOIN flight_bookings ON flight.id = flight_bookings.flight_id " + 
			"JOIN booking ON flight_bookings.booking_id = booking.id " + 
			"JOIN booking_user ON booking.id = booking_user.booking_id " + 
			"WHERE booking_user.user_id = ?";
	
	private static final String selectAllQuery = "WITH aircraft(aircraft_id, max_capacity) " + 
			"AS( " + 
			"SELECT airplane.id, airplane_type.max_capacity " + 
			"FROM airplane " + 
			"JOIN airplane_type ON airplane.type_id = airplane_type.id " + 
			"), " + 
			"origin(route_id, origin_id, origin_city)  " + 
			"AS( " + 
			"SELECT route.id, route.origin_id, airport.city " + 
			"FROM route " + 
			"JOIN airport ON route.origin_id = airport.iata_id " + 
			"), " + 
			"destination(route_id, destination_id, destination_city)  " + 
			"AS( " + 
			"SELECT route.id, route.destination_id, airport.city " + 
			"FROM route " + 
			"JOIN airport ON route.origin_id = airport.iata_id " + 
			"), " + 
			"rt (route_id, origin_id, origin_city, destination_id, destination_city) " + 
			"AS( " + 
			"SELECT origin.route_id, origin.origin_id, origin.origin_city,  " + 
			"destination.destination_id, destination.destination_city " + 
			"FROM origin  " + 
			"JOIN destination on origin.route_id = destination.route_id " + 
			") " + 
			"SELECT t0.id, t0.route_id, t0.airplane_id, t0.departure_time, t0.reserved_seats, t0.seat_price, " + 
			"t1.max_capacity - t0.reserved_seats AS available_seats, " + 
			"t2.origin_id, t2.origin_city, t2.destination_id, t2.destination_city " + 
			"FROM flight t0 " + 
			"LEFT JOIN aircraft t1 ON t0.airplane_id = t1.aircraft_id " + 
			"LEFT JOIN rt t2 ON t0.route_id = t2.route_id ";
	
	private static final String byUserQuery = selectAllQuery + "";
	private static final String getQuery = selectAllQuery + " WHERE id = ?";
	private static final String insertQuery = "INSERT INTO flight (id, route_id, airplane_id, departure_time, reserved_seats, seat_price) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String updateQuery = "UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, reserved_seats = ?, seat_price = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM flight WHERE id = ?";
	
	/***
	 * Creates a new instance of FlightRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public FlightRepository(Connection c, FlightFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Flight get(Object id, Object...args) throws SQLException
	{
		return super.get(getQuery, id);

	}
	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public Flight insert(Flight entity) throws SQLException
	{
		Flight inserted = new Flight();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getId(), entity.getRoute().getId(), entity.getPlane().getId(), entity.getDepartureTime(), entity.getReservedSeats(),
				entity.getSeatPrice());
		inserted.setId(newId);	
		
		return inserted;
	}
	
	/***
	 * Gets list of flights managed by bookingUser
	 * @param bookingUser the user whose flights are to be retrieved
	 * @return
	 * @throws SQLException 
	 */
	public List<Flight> getManagedFlights(User bookingUser) throws SQLException
	{
		String byUserQuery = selectAllQuery + 
				"JOIN flight_bookings ON t0.id = flight_bookings.flight_id " + 
				"JOIN booking ON flight_bookings.booking_id = booking.id " + 
				"JOIN booking_user ON booking.id = booking_user.booking_id " + 
				"WHERE booking_user.user_id = ?";
		return super.getAll(byUserQuery, bookingUser.getId());
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(Flight entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getRoute().getId(), entity.getPlane().getId(), entity.getDepartureTime(), entity.getReservedSeats(),
				entity.getSeatPrice(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Flight entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Flight>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Flight> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<Flight> mapMany(ResultSet r) throws SQLException
	{
		List<Flight> results = new ArrayList<Flight>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Flight mapResult(ResultSet r) throws SQLException
	{
		Flight at = this.factory.fromResultSet(r);
		
		return at;
	}
}
