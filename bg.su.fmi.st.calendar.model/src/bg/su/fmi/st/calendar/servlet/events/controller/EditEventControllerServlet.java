package bg.su.fmi.st.calendar.servlet.events.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.su.fmi.st.calendar.model.entities.Event;
import bg.su.fmi.st.calendar.servlet.events.EventUtils;

@SuppressWarnings("serial")
public class EditEventControllerServlet extends AbstractEventControllerServlet {

	/**
	 * For updating the data in the DB
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {

		Event event = getEvent(req, eventDao);

		String title = req.getParameter(EventUtils.PARAMETER_EVENT_TITLE);
		event.setTitle(title);

		String details = req.getParameter(EventUtils.PARAMETER_EVENT_DETAILS);
		event.setDetails(details);

		String place = req.getParameter(EventUtils.PARAMETER_EVENT_PLACE);
		event.setPlace(place);

		String startDateStr = req.getParameter(EventUtils.PARAMETER_EVENT_START_DATE);
		event.setStartDate(startDateStr);

		String endDateStr = req.getParameter(EventUtils.PARAMETER_EVENT_END_DATE);
		event.setEndDate(endDateStr);

		// type
		String typeStr = req.getParameter(EventUtils.PARAMETER_EVENT_TYPE);
		event.setType(typeStr);

		eventDao.updateEvent(event);

		// we display the view page for an event to check out the change
		RequestDispatcher view = req.getRequestDispatcher(EventUtils.buildViewEventString(event
				.getId()));
		view.forward(req, resp);
	}
}
