package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Leav;
import tn.esprit.se.pispring.entities.Notification;

import java.util.List;

public interface ILeavService {

    Leav retrieveLeav(Long leaveId);
    List<Leav> retrieveAllLeaves();
    Leav addOrUpdateLeav(Leav L);
    void removeLeav(Long leaveId);

    Leav assignLeavToUser(Long leavId, Long id);
    Leav acceptLeaveRequest(Long leaveId);

    Leav refuseLeaveRequest(Long leaveId);

    List<Notification> getNotifications();
}
