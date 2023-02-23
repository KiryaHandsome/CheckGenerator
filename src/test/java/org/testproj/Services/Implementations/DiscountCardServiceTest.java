package org.testproj.Services.Implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testproj.Models.DiscountCard;
import org.testproj.Repositories.DiscountCardRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        discountCard = new DiscountCard(1, 0.5f);
    }

    @Test
    void checkCreateShouldSaveCardInRepositoryWithSameArgument() {
        service.create(discountCard);
        verify(repository).save(discountCardCaptor.capture());
        assertEquals(discountCard, discountCardCaptor.getValue());
    }

    @Test
    void checkFindByIdShouldReturnCard() {
        int id = anyInt();
        when(repository.getReferenceById(id)).thenReturn(discountCard);
        DiscountCard returnedCard = service.find(id);
        verify(repository).getReferenceById(id);
        assertEquals(discountCard, returnedCard);
    }

    @Test
    void checkFindByIdShouldHasSameIdAsArgument() {
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        int id = anyInt();
        service.find(id);
        verify(repository).getReferenceById(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
    }

    @Test
    void checkFindAllShouldCallRepositoryFindAll() {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void checkFindAllShouldReturnSameListAsService() {
        List<DiscountCard> expectedList = List.of(new DiscountCard(12, 44));
        when(repository.findAll()).thenReturn(expectedList);
        List<DiscountCard> actualList = service.findAll();
        verify(repository).findAll();
        assertEquals(expectedList, actualList);
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
        doNothing().when(repository).delete(any(DiscountCard.class));
        service.delete(anyInt());
        verify(repository).delete(any(DiscountCard.class));
    }
}