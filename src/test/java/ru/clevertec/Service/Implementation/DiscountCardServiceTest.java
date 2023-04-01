package ru.clevertec.Service.Implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Repository.DiscountCardRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {
    @InjectMocks
    private DiscountCardService service;

    @Mock
    private DiscountCardRepository repository;

    @Captor
    private ArgumentCaptor<DiscountCard> discountCardCaptor;

    private DiscountCard discountCard;

    @BeforeEach
    public void setup(){
        discountCard = new DiscountCard();
    }

    @Test
    void checkCreateShouldSaveCardInRepositoryWithSameArgument() {
        service.create(discountCard);
        verify(repository).save(discountCardCaptor.capture());
        assertThat(discountCardCaptor.getValue()).isEqualTo(discountCard);
    }

    @Test
    void checkFindByIdShouldReturnCard() {
        int id = anyInt();
        when(repository.getReferenceById(id)).thenReturn(discountCard);
        DiscountCard returnedCard = service.find(id);
        verify(repository).getReferenceById(id);
        assertThat(returnedCard).isEqualTo(discountCard);
    }

    @Test
    void checkFindByIdShouldHasSameIdAsArgument() {
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        int id = anyInt();
        service.find(id);
        verify(repository).getReferenceById(idCaptor.capture());
        assertThat(idCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void checkFindAllShouldCallRepositoryFindAll() {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void checkFindAllShouldReturnSameListAsService() {
        List<DiscountCard> expectedList = List.of(new DiscountCard());
        when(repository.findAll()).thenReturn(expectedList);
        List<DiscountCard> actualList = service.findAll();
        verify(repository).findAll();
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void checkDeleteAllShouldCallRepositoryDeleteAll() {
        doNothing().when(repository).deleteAll();
        service.deleteAll();
        verify(repository).deleteAll();
    }

    @Test
    void checkUpdateShouldCallRepositorySave() {
        service.update(anyInt(), discountCard);
        verify(repository).save(discountCard);
    }

    @Test
    void checkDeleteShouldCallRepositoryDelete() {
        doNothing().when(repository).deleteById(any());
        service.delete(anyInt());
        verify(repository).deleteById(any());
    }
}