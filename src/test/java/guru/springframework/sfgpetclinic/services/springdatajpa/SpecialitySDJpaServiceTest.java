package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    //Create an instance of this and inject it
    @InjectMocks
    SpecialitySDJpaService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        service.deleteById(1L);

        /*we can do there is using the verify method inside a mockito and specify the number of times*/
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLest() {
        service.deleteById(1L);
        service.deleteById(1L);

        /*The method must be used at least once*/
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1L);
        service.deleteById(1L);

        /*The method must be used at least once*/
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1L);
        service.deleteById(1L);

        /*The method must be used at least once*/
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);

        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void testDelete() {
        service.delete(new Speciality());
    }

    //----------------------RETURN VALUE

    @Test
    void findByIdTest() {
        //Speciality object that is gonna be the object that our mock is going to return back
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpecialty = service.findById(1L);

        assertThat(foundSpecialty).isNotNull();

        //verify(specialtyRepository).findById(1L);
        //using matcher
        verify(specialtyRepository).findById(anyLong());
    }
    @Test
    void testDeleteByObject() {

        Speciality speciality = new Speciality();


        service.delete(speciality);

        /*argument matcher, with any, any will mean anything but I wanna make sure that
        the delete method is called with any object that has the speciality class*/
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    //----------BDD Mockito

    @Test
    void findByIdBddTest() {
        //give: block where we're setting up the Speciality pbject and configuring the mock
        Speciality speciality = new Speciality();
        //here is where i'm gonna set up the mock
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpecialty = service.findById(1L);

        //then
        assertThat(foundSpecialty).isNotNull();
        //then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        //to make sure that the specialityRespository does not have any further interactions.
        then(specialtyRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void testDeleteBddByObject() {
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteBddById() {
        //*given - none

        //*when
        service.deleteById(1L);
        service.deleteById(1L);

        //*then
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void deleteBddByIdAtLest() {
        //*given

        //*when
        service.deleteById(1L);
        service.deleteById(1L);

        //*then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteBddByIdAtMost() {
        //*given

        //*when
        service.deleteById(1L);
        service.deleteById(1L);

        //*then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteBddByIdNever() {
        //*when
        service.deleteById(1L);
        service.deleteById(1L);

        //*then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void testDeleteBdd() {
        //*when
        service.delete(new Speciality());

        //then
        then(specialtyRepository).should().delete(any());
    }
}