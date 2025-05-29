package com.PruebaTecnica.PruebaTecnica_PedroMLopez.initializer;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Dependencia;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Hospital;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.DependenciaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.HospitalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.invoke.CallSite;
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

        List<String> dependencias = List.of("Urgencias", "Medico de Familia", "Ginecología", "Psicología", "UCI", "Psiquiatría");

        crearDependencias(h1, dependencias);
        crearDependencias(h2, dependencias);
        crearDependencias(h3, dependencias);

    }

    private void crearDependencias(Hospital hospital, List<String> nombreDependencias){

        List<Dependencia> listaDependencias = nombreDependencias.stream().map(nombre ->{
            Dependencia dependencia = new Dependencia();
            dependencia.setNombre(nombre);
            dependencia.setHospital(hospital);
            return dependencia;
        }).toList();

        dependenciaRepository.saveAll(listaDependencias);
    }

}
