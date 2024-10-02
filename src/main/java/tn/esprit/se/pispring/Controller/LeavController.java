package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.ILeavService;
import tn.esprit.se.pispring.entities.Leav;
import tn.esprit.se.pispring.entities.Notification;

import java.util.List;

@CrossOrigin(origins ="http://localhost:8089")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leav")
public class LeavController {

private final ILeavService leavService;
    @PostMapping("/addLeav")
    Leav addLeav(@RequestBody Leav leav)
    {
        return leavService.addOrUpdateLeav(leav);
    }
    @PutMapping("/updateLeav")
    Leav updateLeav(@RequestBody Leav leav)
    {
        return leavService.addOrUpdateLeav(leav);
    }
    @GetMapping("/get/{leaveId}")
    Leav getLeav(@PathVariable("leaveId") Long leaveId)
    {
        return leavService.retrieveLeav(leaveId);
    }
    @GetMapping("/allLeavs")
    List<Leav> getAllLeavs() { return leavService.retrieveAllLeaves();}
    @DeleteMapping("/delete/{leaveId}")
    void deleteLeav(@PathVariable("leaveId") Long leaveId)
    {
        leavService.removeLeav(leaveId);
    }

    @PutMapping("/assignLeavToUser/{leaveId}/{id}")
    public ResponseEntity<String> assignLeavToUser(@PathVariable("leaveId") Long leaveId, @PathVariable("id") Long id) {
        try {
            leavService.assignLeavToUser(leaveId, id);
            return ResponseEntity.ok("Leave assigned to user successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign leave to user.");
        }
    }

    @PutMapping("/{leaveId}/accept")
    //@PreAuthorize("hasRole('ROLE_HR_ADMIN')")
    public ResponseEntity<String> acceptLeaveRequest(@PathVariable("leaveId") Long leaveId) {
        try {
            leavService.acceptLeaveRequest(leaveId);
            return ResponseEntity.ok("Leave request accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to accept leave request: " + e.getMessage());

        }
    }
    @PutMapping("/{leaveId}/refuse")
    public ResponseEntity<String> refuseLeaveRequest(@PathVariable("leaveId") Long leaveId) {
        try {
            leavService.refuseLeaveRequest(leaveId);
            return ResponseEntity.ok("Leave request refused successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to refuse leave request: " + e.getMessage());
        }
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = leavService.getNotifications();
        return ResponseEntity.ok(notifications);
    }

}
