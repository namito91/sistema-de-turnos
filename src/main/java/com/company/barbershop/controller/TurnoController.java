package com.company.barbershop.controller;

import com.company.barbershop.model.Turno;
import com.company.barbershop.service.TurnoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/barber-shop")
public class TurnoController {

    // inject values from app properties file
    @Value("${dias}")
    private List<String> dias;

    @Value("${horarios}")
    private List<String> horarios;

    List<LocalDate> fechas;

    TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/turnos")
    public String formularioDeTurno(Model model,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    @RequestParam(name = "reset", defaultValue = "false") boolean reset){

        // Verificar si ya hay fechas en la sesión
        fechas = (List<LocalDate>) session.getAttribute("fechas");

        // Si no hay fechas, o si se desea reiniciar, generar nuevas fechas
        if (fechas == null || request.getParameter("reset") != null) {

            fechas = generarFechas();

            // Almacenar las fechas en la sesión
            session.setAttribute("fechas", fechas);

        } else {

            // Obtener la última fecha en la lista
            LocalDate primerFecha = fechas.get(0);

            // Obtener la fecha actual
            LocalDate hoy = LocalDate.now();

            // Comparar si ha pasado al menos un día desde la última fecha
            if (hoy.isAfter(primerFecha)) {

                // Agregar un día adicional a la lista de fechas
                LocalDate nuevaFecha = primerFecha.plusDays(1);

                // Verificar si la nueva fecha es un día de semana (martes a sábado)
                while (nuevaFecha.getDayOfWeek() == DayOfWeek.SUNDAY || nuevaFecha.getDayOfWeek() == DayOfWeek.MONDAY) {

                    nuevaFecha = nuevaFecha.plusDays(1);
                }

                fechas.add(nuevaFecha);

                fechas.remove(primerFecha);
            }
        }

        model.addAttribute("turno",new Turno());

        model.addAttribute("dias",dias);

        model.addAttribute("horarios",horarios);

        model.addAttribute("fechas",fechas);

        return "formulario-de-turno";
    }


    private List<LocalDate> generarFechas(){

        List<LocalDate> fechas = new ArrayList<>();

        // Obtener fechas desde hoy en adelante
        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < 20; i++) {  // Puedes ajustar el número de fechas que deseas mostrar

            LocalDate fecha = hoy.plusDays(i);

            if (fecha.getDayOfWeek() != DayOfWeek.SUNDAY && fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
                fechas.add(fecha);
            }
        }

        return fechas;
    }


    @PostMapping("/procesar-formulario-turno")
    public String procesarFormularioTurno(@Valid @ModelAttribute("turno") Turno turno,
                                          BindingResult bindingResult,
                                          Model model,
                                          HttpServletRequest request,
                                          HttpSession session){

        if (bindingResult.hasErrors()){

            // arregla el bug de no mostrar fechas la primer pasada,en caso que alla error en input de formulario
            if (fechas == null || request.getParameter("reset") != null) {

                fechas = generarFechas();

                // Almacenar las fechas en la sesión
                session.setAttribute("fechas", fechas);
            }

            model.addAttribute("dias",dias);

            model.addAttribute("horarios",horarios);

            model.addAttribute("fechas",fechas);

            return "formulario-de-turno";

        }else {

            String horario = turno.getHorario();

            horarios.remove(horario);

            System.out.println(horarios);

            turnoService.save(turno);

            return "home";
        }
    }
}
