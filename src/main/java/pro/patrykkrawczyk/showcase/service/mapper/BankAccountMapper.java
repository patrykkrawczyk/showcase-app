package pro.patrykkrawczyk.showcase.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.patrykkrawczyk.showcase.domain.BankAccount;
import pro.patrykkrawczyk.showcase.service.dto.BankAccountDTO;

/**
 * Mapper for the entity {@link BankAccount} and its DTO {@link BankAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {

    @Mapping(source = "person.id", target = "personId")
    BankAccountDTO toDto(BankAccount bankAccount);

    @Mapping(source = "personId", target = "person")
    BankAccount toEntity(BankAccountDTO bankAccountDTO);

    default BankAccount fromId(Long id) {
        if (id == null) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        return bankAccount;
    }
}
