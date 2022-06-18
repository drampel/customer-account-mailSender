package customer_account_mailSender.msCustomer.mapper;

import customer_account_mailSender.msCustomer.dao.CustomerEntity;
import customer_account_mailSender.msCustomer.model.CustomerDto;

public class CustomerMapper {
    public static CustomerDto toDto(CustomerEntity entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .birthdate(entity.getBirthdate())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static CustomerEntity toEntity(CustomerDto dto) {
        return CustomerEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .birthdate(dto.getBirthdate())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }

    public static void toEntity(CustomerEntity entity, CustomerDto dto) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getSurname() != null) entity.setSurname(dto.getSurname());
        if (dto.getBirthdate() != null) entity.setBirthdate(dto.getBirthdate());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
    }
}