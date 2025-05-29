package com.PruebaTecnica.PruebaTecnica_PedroMLopez.initializer;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Dependencia;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Hospital;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.DependenciaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.HospitalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final HospitalRepository hospitalRepository;
    private final DependenciaRepository dependenciaRepository;

    public DataInitializer(HospitalRepository hospitalRepository, DependenciaRepository dependenciaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.dependenciaRepository = dependenciaRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if(hospitalRepository.count() > 0){
            return;
        }

        Hospital h1 = new Hospital();
        h1.setNombre("Hospital Universitario Virgen de la Arrixaca");
        h1.setDireccion("Carretera Madrid-Cartagena, s/n, El Palmar, Murcia");

        Hospital h2 = new Hospital();
        h2.setNombre("Hospital General Universitario Morales Meseguer");
        h2.setDireccion("Av. Marqués de los Vélez, s/n, Murcia");

        Hospital h3 = new Hospital();
        h3.setNombre("Hospital General Universitario Los Arcos del Mar Menor");
        h3.setDireccion("Paraje Torre Octavio, 54, 30739 Pozo Aledo, Murcia");

        hospitalRepository.saveAll(List.of(h1, h2, h3));

        Dependencia d1 = new Dependencia();
        d1.setNombre("Urgencias");
        d1.setHospital(h1);

        Dependencia d2 = new Dependencia();
        d2.setNombre("UCI");
        d2.setHospital(h1);

        Dependencia d3 = new Dependencia();
        d3.setNombre("Psquiatría");
        d3.setHospital(h1);

        Dependencia d4 = new Dependencia();
        d4.setNombre("Pediatría");
        d4.setHospital(h1);

        Dependencia d5 = new Dependencia();
        d5.setNombre("Ginecología");
        d5.setHospital(h1);

        Dependencia d6 = new Dependencia();
        d6.setNombre("Urgencias");
        d6.setHospital(h2);

        Dependencia d7 = new Dependencia();
        d7.setNombre("UCI");
        d7.setHospital(h2);

        Dependencia d8 = new Dependencia();
        d8.setNombre("Psquiatría");
        d8.setHospital(h2);

        Dependencia d9= new Dependencia();
        d9.setNombre("Pediatría");
        d9.setHospital(h2);

        Dependencia d10= new Dependencia();
        d10.setNombre("Ginecología");
        d10.setHospital(h2);

        Dependencia d11 = new Dependencia();
        d11.setNombre("Urgencias");
        d11.setHospital(h3);

        Dependencia d12 = new Dependencia();
        d12.setNombre("UCI");
        d12.setHospital(h3);

        Dependencia d13 = new Dependencia();
        d13.setNombre("Psquiatría");
        d13.setHospital(h3);

        Dependencia d14= new Dependencia();
        d14.setNombre("Pediatría");
        d14.setHospital(h3);

        Dependencia d15= new Dependencia();
        d15.setNombre("Ginecología");
        d15.setHospital(h3);

        dependenciaRepository.saveAll(List.of(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15));

    }
}
