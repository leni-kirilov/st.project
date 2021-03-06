package bg.su.fmi.st.calendar.model.manager;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bg.su.fmi.st.calendar.model.entities.Event;
import bg.su.fmi.st.calendar.model.entities.EventInvitation;
import bg.su.fmi.st.calendar.model.entities.EventInvitation.InvitationResponse;
import bg.su.fmi.st.calendar.utils.NotificationService;

@Stateless
public class EventInvitationDAO {

	@PersistenceContext(unitName = "sport-events-organizer-unit")
	private EntityManager entityManager;
	
	@EJB
	private NotificationService notificationService;

	public void addEventInvitation(EventInvitation eventInvitation) {
		entityManager.persist(eventInvitation);
	}

	public void deleteEvent(EventInvitation eventInvitation) {
		entityManager.remove(eventInvitation);
	}

	@SuppressWarnings("unchecked")
	public List<EventInvitation> getAllEventInvitations() {
		Query query = entityManager
				.createQuery("SELECT e from EventInvitation as e");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<EventInvitation> getEventInvitations(Event event) {
		Query query = entityManager
				.createQuery("SELECT e from EventInvitation as e where e.event=?1");
		query.setParameter(1, event);
		return query.getResultList();
	}

	public EventInvitation getEventInvitation(long id) {
		Query query = entityManager.createQuery("SELECT e from EventInvitation as e where e.id=?1");
		query.setParameter(1, id);
		return (EventInvitation) query.getResultList().get(0);
	}
	
	public void invite(Event event) {
		notificationService.notifyUserForInvitation(event);
	}
	
	public void acceptInvitation(EventInvitation eventInvitation) {
		EventInvitation invitation = entityManager.find(EventInvitation.class, eventInvitation.getId());
		invitation.setResponse(InvitationResponse.YES);
	}
	
	public void declineInvitation(EventInvitation eventInvitation, String comment) {
		EventInvitation invitation = entityManager.find(EventInvitation.class, eventInvitation.getId());
		invitation.setResponse(InvitationResponse.NO);
		invitation.setComment(comment);
	}

}
