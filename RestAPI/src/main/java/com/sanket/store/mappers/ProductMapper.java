package com.sanket.store.mappers;

import com.sanket.store.dtos.ProductDto;
import com.sanket.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
    @Mapping(target="categoryId", source="category.id")//we are doing cutomized mapping by using @Mapping Annotation
                                                       //source object is product entity, so we are mapping category.id in product to the categoryId field of the ProductDto.
    ProductDto toDto(Product product);

}
