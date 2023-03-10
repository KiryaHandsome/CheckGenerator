package ru.clevertec.Service.Implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.Model.Product;
import ru.clevertec.Repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1, "name", 0.123, true);
    }

    @Test
    void checkCreateShouldCallRepositorySave() {
        when(repository.save(product)).thenReturn(any());
        service.create(product);
        verify(repository).save(eq(product));
    }

    @Test
    void checkFindShouldCallRepositoryGetRefById() {
        int id = anyInt();
        when(repository.getReferenceById(id)).thenReturn(product);
        Product returnedProduct = service.find(id);
        verify(repository).getReferenceById(id);
        assertEquals(product, returnedProduct);
    }

    @Test
    void checkFindAllShouldCallRepositoryFindAll() {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void checkFindAllShouldReturnSameListAsRepository() {
        List<Product> expectedList = List.of(new Product(12, "name", 123.11, true), product);
        when(repository.findAll()).thenReturn(expectedList);
        List<Product> actualList = service.findAll();
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
        service.update(anyInt(), product);
        verify(repository).save(product);
    }

    @Test
    void checkDeleteShouldCallRepositoryDelete() {
        doNothing().when(repository).deleteById(any());
        service.delete(anyInt());
        verify(repository).deleteById(any());
    }
}