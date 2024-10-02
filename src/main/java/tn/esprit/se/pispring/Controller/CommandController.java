package tn.esprit.se.pispring.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Repository.CommandRepository;
import tn.esprit.se.pispring.Service.Command.CommandServiceImpl;
import tn.esprit.se.pispring.entities.Command;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/command")
@CrossOrigin(origins = "http://localhost:4200")
public class CommandController {
    CommandServiceImpl commandService ;
    private final CommandRepository commandRepository;


    @GetMapping("/commandes")
    public List<Command> retrieveCommandList(){
        return commandService.findAll();
    }

    @GetMapping("/retrive_commandes/{commandId}")
    public Command retrieveCommand(@PathVariable("commandId") Long commandId){
        return commandService.retrieveItem(commandId);
    }

    @PostMapping("/addCommande")
    public Command addCommand(@RequestBody Command command){
        return commandService.add(command);
    }

    @PutMapping("/updateCommande")
    public Command updateCommand(@RequestBody Command command){
        return commandService.update(command);
    }

    @DeleteMapping("/deleteCommande/{commandId}")
    public void deleteCommand(@PathVariable("commandId") Long commandId){
        commandService.delete(commandId);
    }
    @PutMapping("/{commandId}/assignCart/{cartId}") // ca marche pas
    public ResponseEntity<Command> assignCartToCommand(@PathVariable Long commandId, @PathVariable Long cartId) {
        Command updatedCommand = commandService.assignCartToCommand(commandId, cartId);
        return ResponseEntity.ok(updatedCommand);
    }

    @GetMapping("/sales")
    public ResponseEntity<Double> getMonthlySalesAmount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        Double salesAmount = commandService.calculateMonthlySalesAmount(year, month);
        return ResponseEntity.ok(salesAmount);
    }
}
