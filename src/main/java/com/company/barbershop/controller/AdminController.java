package com.company.barbershop.controller;

import com.company.barbershop.model.Turno;
import com.company.barbershop.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    TurnoService turnoService;

    @Autowired
    public AdminController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/turnos-reservados")
    public String turnosReservados(Model model){

        List<Turno> turnos = turnoService.findAll();

        model.addAttribute("turnos",turnos);

        return "panel-turnos";
    }

}
