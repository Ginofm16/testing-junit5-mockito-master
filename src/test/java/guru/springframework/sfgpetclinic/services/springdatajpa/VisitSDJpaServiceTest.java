package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        Visit visit = new Visit();

        Set<Visit> visits = new HashSet<>();
        visits.add(visit);

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> foundVisits = service.findAll();

        verify(visitRepository).findAll();

        assertThat(foundVisits).hasSize(1);
    }

    @Test
    void findById() {

        Visit visit = new Visit();

        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(1L);

        assertThat(foundVisit).isNotNull();

        verify(visitRepository).findById(1L);
    }

    @Test
    void save() {
        Visit visit = new Visit();

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = service.save(visit);

        verify(visitRepository).save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
    }

    @Test
    void testDeleteByObject() {
        Visit visit = new Visit();

        service.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        Visit visit = new Visit();

        service.deleteById(1L);

        verify(visitRepository).deleteById(anyLong());
    }

    //=============BDD=========================

    @Test
    void findAllBDD() {
        //*given
        Visit visit = new Visit();
        visit.setId(1L);
        Visit visit2 = new Visit();
        visit2.setId(2L);
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        visits.add(visit2);
        given(visitRepository.findAll()).willReturn(visits);

        //*when
        Set<Visit> foundVisits = service.findAll();

        for (Visit v: foundVisits) {
            System.out.println(v.getId());
        }

        //*Then
        then(visitRepository).should().findAll();
        assertThat(foundVisits).hasSize(3);
    }

    @Test
    void findByIdBdd() {
        //*given
        Visit visit = new Visit();
        given(visitRepository.findById(1L)).willReturn(Optional.of(visit));

        //when
        Visit foundVisit = service.findById(1L);

        //then
        assertThat(foundVisit).isNotNull();
        then(visitRepository).should().findById(1L);
    }

    @Test
    void saveBdd() {
        //*given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //*when
        Visit visitSaved = service.save(visit);

        //*then
        then(visitRepository).should().save(any(Visit.class));
    }

    @Test
    void testDeleteByObjectBdd() {
        //*given
        Visit visit = new Visit();

        //*when
        service.delete(visit);

        //*then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteByIdBdd() {

        //*when
        service.deleteById(1L);
        //*then
        then(visitRepository).should().deleteById(anyLong());
    }


}