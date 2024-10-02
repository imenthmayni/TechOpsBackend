package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.LeavRepository;
import tn.esprit.se.pispring.Repository.NotificationRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Leav;
import tn.esprit.se.pispring.entities.LeaveStatus;
import tn.esprit.se.pispring.entities.Notification;
import tn.esprit.se.pispring.entities.User;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeavService implements ILeavService {
    private final UserRepository userRepository;

    private final LeavRepository leavRepository;
    private  final NotificationRepository notificationRepository;

    @Override
    public Leav retrieveLeav(Long leaveId) {
        return leavRepository.findById(leaveId).orElse(null);
    }
    @Override
    public List<Leav> retrieveAllLeaves() {
        return leavRepository.findAll();
    }

    @Override
    public Leav addOrUpdateLeav(Leav L) {
        return leavRepository.save(L);
    }
    @Override
    public void removeLeav(Long leaveId) {
    leavRepository.deleteById(leaveId);
    }
    @Override
    @Transactional
    public Leav assignLeavToUser(Long leaveId, Long id) {
        Leav leav = leavRepository.findById(leaveId).orElse(null);
        User user = userRepository.findById(id).orElse(null);

        if (leav != null && user != null) {
            // verif q le statut du congé est "approved"
            if (leav.getLeaveStatus() == LeaveStatus.APPROVED) {
                leav.setUser(user);
                leavRepository.save(leav);
                log.info("Leave with ID {} has been assigned to user ({} {}).", leaveId, user.getFirstName(), user.getLastName());
                Notification notification = new Notification();
                notification.setMessage("Your leave request has been assigned.");
                notification.setRecipient(user);
                notificationRepository.save(notification);
                return leav;
            } else {
                // sinon n pas affecter le congé
                log.error("Leave with ID {} cannot be assigned to user with ID {}. Leave status is not approved.", leaveId, id);
                throw new IllegalStateException("Leave with ID " + leaveId + " cannot be assigned. Leave status is not approved.");
            }
        } else {
            log.error("Failed to assign leave to user. Leave or user not found.");
            throw new IllegalArgumentException("Failed to assign leave to user. Leave or user not found.");
        }
    }


    //    @Scheduled(fixedRate = 60000)
//    public void sendScheduledNotifications() {
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        // Convert LocalDateTime to Date
//        Date currentDateTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
//        List<Leav> upcomingLeave = leavRepository.findUpcomingLeave(currentDateTime);
//
//        for (Leav leav : upcomingLeave) {
//            String notificationMessage = "Your leave is scheduled for " + leav.getLeaveStartdate() + ".";
//            Notification notification = new Notification();
//            notification.setMessage(notificationMessage);
//            notification.setRecipient(leav.getUser());
//            notificationRepository.save(notification);
//        }
//    // Methode pour calculer el duree mtaa el congé entre jour début et jour fin
//    private int calculateLeaveDurationInDays(Date leaveStartDate, Date leaveEnddate) {
//        LocalDate startLocalDate = leaveStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endLocalDate = leaveEnddate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        return (int) ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
//    }
//accepter le congé par l'admin hr
    @Override
    @Transactional
    public Leav acceptLeaveRequest(Long leaveId) {
        try {
            Leav leav = leavRepository.findById(leaveId).orElse(null);

            // Calculate leave duration in days
            Integer leaveDurationInDays = calculateLeaveDurationInDays(leav.getLeaveStartdate(), leav.getLeaveEnddate());

            // Check if leave duration exceeds available leave days left
            if (leav.getLeaveDaysLeft() < leaveDurationInDays) {
                throw new IllegalArgumentException("Leave duration exceeds available leave days left.");
            }

            // Set leave status to APPROVED
            leav.setLeaveStatus(LeaveStatus.APPROVED);

            return leavRepository.save(leav);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            log.error("Failed to accept leave request. " + e.getMessage());
            throw e; // Rethrow the exception
        } catch (Exception e) {
            log.error("Failed to accept leave request.", e);
            throw new RuntimeException("Failed to accept leave request.", e);
        }
    }

    // Method to calculate the leave duration in days

    private int calculateLeaveDurationInDays(Date leaveStartDate, Date leaveEndDate) {
        if (leaveStartDate == null || leaveEndDate == null) {
            throw new IllegalArgumentException("Leave start date or end date cannot be null.");
        }

        // Get the timestamps for the start and end dates
        long startTime = leaveStartDate.getTime();
        long endTime = leaveEndDate.getTime();

        // Calculate the difference in milliseconds
        long durationInMillis = endTime - startTime;

        // Convert milliseconds to days (1 day = 24 * 60 * 60 * 1000 milliseconds)
        int durationInDays = (int) (durationInMillis / (24 * 60 * 60 * 1000));

        return durationInDays;
    }





    //refuser le congé par l'admin hr si le nbr de jours restants exceeds el nbr de jours demandés.
    @Override
    public Leav refuseLeaveRequest(Long leaveId) {
        Leav leav = leavRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave not found with ID: " + leaveId));

        int leaveDurationInDays = calculateLeaveDurationInDays(leav.getLeaveStartdate(), leav.getLeaveEnddate());

        if (leav.getLeaveDaysLeft() < leaveDurationInDays) {
            throw new IllegalArgumentException("Leave duration exceeds available leave days left.");
        }

        leav.setLeaveStatus(LeaveStatus.REFUSED);
        return leavRepository.save(leav);
    }


    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }
    }

