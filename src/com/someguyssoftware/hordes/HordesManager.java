package com.someguyssoftware.hordes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.someguyssoftware.dungeons.db.DungeonsDao;
import com.someguyssoftware.plans.IPlan;
import com.someguyssoftware.plans.PlansManager;
import com.someguyssoftware.plans.db.PlanDao;

/**
 * Manages Dungeons database calls
 * @author Mark Gottschling on Aug 13, 2015
 *
 */
public class HordesManager {
	// logger
	public static Logger log = LogManager.getLogger("Dungeons");
	
	private Connection connection;
	private DungeonsDao dungeonsDao;

	/**
	 * 
	 * @param connection
	 */
	public HordesManager(Connection connection) throws ClassNotFoundException, SQLException {
		log.info("Initializing HordesManager...");
		if (connection == null) {
			log.warn("Provided connection is null - creating a new connection.");
	        // load the driver class
			Class.forName("org.h2.Driver");
			// create the connection
			connection = DriverManager.getConnection("jdbc:h2:mem:plans;DB_CLOSE_DELAY=-1");
		}		
		setConnection(connection);
		
		// create the dao
		setDungeonsDao(new DungeonsDao(connection));
		log.debug("...Complete.");
	}
	
	/**
	 * 
	 * @param plansManager
	 */
	public HordesManager(PlansManager plansManager) {
		setConnection(plansManager.getConnection());
	}
	
	/**
	 * 
	 * @param category
	 * @return
	 */
	public List<String> getGroupNameByCategory(String category) {
		List<String> names = new ArrayList<>();
		
		if (category == null) {
			log.warn("category is NULL");
		}
		if (getDungeonsDao() == null) {
			log.warn("dao is NULL");
		}
		
		// else get from the database
		ResultSet rs = getDungeonsDao().findGroupNameByCategory(category);
		
		// for all results get the Plan from the registry
		try {
			while (rs.next()) {
				String name = rs.getString(1);
				names.add(name);
			}
		} catch (SQLException e) {
			log.error("Error traversing result set:", e);
			e.printStackTrace();
		}
		return names;
	}
	
	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the dungeonsDao
	 */
	public DungeonsDao getDungeonsDao() {
		return dungeonsDao;
	}

	/**
	 * @param dungeonsDao the dungeonsDao to set
	 */
	public void setDungeonsDao(DungeonsDao dungeonsDao) {
		this.dungeonsDao = dungeonsDao;
	}
}
