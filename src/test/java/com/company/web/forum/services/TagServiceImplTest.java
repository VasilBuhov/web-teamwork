//package com.company.web.forum.services;
//
//import com.company.web.forum.exceptions.AuthorizationException;
//import com.company.web.forum.models.Tag;
//import com.company.web.forum.models.User;
//import com.company.web.forum.repositories.TagRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static com.company.web.forum.Auxiliary.*;
//@ExtendWith(MockitoExtension.class)
//class TagServiceImplTest {
//
//    @Mock
//    TagRepository mockRepository;
//
//    @InjectMocks
//    TagServiceImpl service;
//
//    @Test
//    void delete_Should_ThrowException_When_UserIsNotAdminOrCreator() {
//        // Arrange
//        Tag mockTag = createMockTag();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt()))
//                .thenReturn(mockTag);
//
//        Tag mockTaggy = Mockito.mock(Tag.class);
//        User mockUser = createMockUser();
//
//        // Act, Assert
//        Assertions.assertThrows(
//                AuthorizationException.class,
//                () -> service.delete(1, mockUser));
//    }
//
//    @Test
//    void delete_Should_CallRepository_When_UserIsAdmin() {
//        // Arrange
//        User mockUserAdmin = createMockAdmin();
//        Tag mockTag = createMockTag();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt()))
//                .thenReturn(mockTag);
//
//        // Act
//        service.delete(1, mockUserAdmin);
//
//        // Assert
//        Mockito.verify(mockRepository, Mockito.times(1))
//                .delete(1);
//    }
//    @Test
//    void delete_Should_CallRepository_When_UserIsCreator() {
//        // Arrange
//        Tag mockTag = createMockTag();
//        User mockUserCreator = mockTag.getBelongs_to();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt()))
//                .thenReturn(mockTag);
//
//        // Act
//        service.delete(1, mockUserCreator);
//
//        // Assert
//        Mockito.verify(mockRepository, Mockito.times(1))
//                .delete(1);
//    }
//
//
//    @Test
//    public void get_Should_ReturnBeer_When_MatchByIdExist() {
//        // Arrange
//        Tag mockTag = createMockTag();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt()))
//                .thenReturn(mockTag);
//
//        // Act
//        Tag result = service.getTagById(mockTag.getId());
//
//        // Assert
//        Assertions.assertEquals(mockTag, result);
//    }
//
//
//}