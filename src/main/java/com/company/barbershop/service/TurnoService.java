package com.company.barbershop.service;

import com.company.barbershop.model.Turno;
import com.company.barbershop.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {

    TurnoRepository turnoRepository;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public void save(Turno turno){

        turnoRepository.save(turno);
    }

    public List<Turno> findAll(){

        // ordena de fecha mas reciente primero,
        Sort sort = Sort.by(Sort.Order.desc("dia"));

        return turnoRepository.findAll(sort);
    }
}
