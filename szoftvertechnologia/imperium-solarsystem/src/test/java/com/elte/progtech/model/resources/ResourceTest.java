package com.elte.progtech.model.resources;

import hu.elte.progtech.exceptions.ResourceTypeNotMatchException;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ResourceTest {

    @Mock
    Resource resource;

    @BeforeEach
    void init() {
        resource = new Resource(100, ResourceType.DEUTERIUM);
    }

    @Test
    @DisplayName("add() should be equal if we add 50")
    void add50DeuteriumWithNumber() {
        resource.add(50);
        assertEquals(resource.getQuantity(), 150);
    }

    @Test
    @DisplayName("add() should be equal if we add a resource with 50 quantity")
    void add50DeuteriumWithResource() {
        resource.add(new Resource(50, ResourceType.DEUTERIUM));
        assertEquals(resource.getQuantity(), 150);
    }

    @Test
    @DisplayName("add() should throw ResourceTypeNotMatchException when we try to add different type of resource")
    void addDifferentResourceAndThrowResourceTypeNotMatchException() {
        assertThrows(ResourceTypeNotMatchException.class, () -> resource.add(new Resource(50, ResourceType.ENERGY)));
    }

    @Test
    @DisplayName("remove() should return false if new quantity is less than 0")
    void removeQuantityReturnFalse() {
        int resourceQuantity = resource.getQuantity();
        assertFalse(resource.remove(resourceQuantity+1));
    }

    @Test
    @DisplayName("remove() should return true if new quantity is more than 0")
    void removeQuantityReturnTrue() {
        int resourceQuantity = resource.getQuantity();
        assertTrue(resource.remove(resourceQuantity - 1 ));
    }

    @Test
    @DisplayName("remove() should return false if we remove more resource quantity than we have")
    void removeResourceReturnFalse() {
        assertFalse(resource.remove(new Resource(resource.getQuantity()+1, ResourceType.DEUTERIUM)));
    }

    @Test
    @DisplayName("remove() should return true if we remove less resource quantity than we have")
    void removeResourceReturnTrue() {
        assertTrue(resource.remove(new Resource(resource.getQuantity()-1, ResourceType.DEUTERIUM)));
    }

    @Test
    @DisplayName("remove() should throw ResourceTypeNotMatchException when we try to remove different type of resource")
    void removeDifferentResourceAndThrowResourceTypeNotMatchException() {
        assertThrows(ResourceTypeNotMatchException.class, () -> resource.remove(new Resource(50, ResourceType.ENERGY)));
    }

}
