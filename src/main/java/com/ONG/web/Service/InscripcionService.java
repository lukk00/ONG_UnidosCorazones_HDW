package com.ONG.web.Service;

import com.ONG.web.Model.Administrador;
import com.ONG.web.Model.Campania;
import com.ONG.web.Model.Inscripcion;
import com.ONG.web.Model.Voluntario;
import com.ONG.web.Repository.CampaniaRepository;
import com.ONG.web.Repository.InscripcionRepository;
import com.ONG.web.Repository.AdministradorRepository;
import com.ONG.web.Repository.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private CampaniaRepository campaniaRepository;

    public void inscribirse(String correoVoluntario, Integer idCampania) throws  Exception {

        Voluntario voluntario = voluntarioRepository.findByCorreo(correoVoluntario)
                .orElseThrow(() -> new RuntimeException("No existe voluntario con el correo: " + correoVoluntario));

        Campania campania = campaniaRepository.findByIdCampania(idCampania)
                .orElseThrow(() -> new Exception("Campaña no encontrada"));

        //Validar duplicadpos
        if (inscripcionRepository.existsByVoluntarioAndCampania(voluntario, campania)) {
            throw  new Exception("Ya estas inscrito a esta Campaña");
        }

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setVoluntario(voluntario);
        inscripcion.setCampania(campania);
        inscripcion.setFecha_inscripcion(LocalDateTime.now());

        inscripcionRepository.save(inscripcion);
    }

    public List<Integer> obtenerIdsInscripciones(String correoVoluntario) {
        Voluntario v = voluntarioRepository.findByCorreo(correoVoluntario).orElse(null);
        if(v == null) return new ArrayList<>();

        return inscripcionRepository.findByVoluntario(v).stream()
                .map(i -> i.getCampania().getIdCampania()) // Asumiendo que tu ID en campaña es id_campania
                .collect(Collectors.toList());
    }



}

