package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.company.web.forum.Auxiliary.*;
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository mockRepository;

    @InjectMocks
    TagServiceImpl service;

    @Test
    void delete_Should_ThrowException_When_UserIsNotAdminOrCreator() {
        // Arrange
        Tag mockTag = createMockTag();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockTag);

        Tag mockTaggy = Mockito.mock(Tag.class);
        User mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(
                AuthorizationException.class,
                () -> service.delete(1, mockUser));
    }

}