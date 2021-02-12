package pro.patrykkrawczyk.showcase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.showcase.domain.BankAccount;
import pro.patrykkrawczyk.showcase.repository.BankAccountRepository;
import pro.patrykkrawczyk.showcase.service.dto.BankAccountDTO;
import pro.patrykkrawczyk.showcase.service.mapper.BankAccountMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BankAccount}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    /**
     * Save a bankAccount.
     *
     * @param bankAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        log.debug("Request to save BankAccount : {}", bankAccountDTO);

        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount result = bankAccountRepository.save(bankAccount);

        return bankAccountMapper.toDto(result);
    }

    /**
     * Get all the bankAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankAccounts");

        return bankAccountRepository.findAll(pageable).map(bankAccountMapper::toDto);
    }

    /**
     * Get one bankAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankAccountDTO> findOne(Long id) {
        log.debug("Request to get BankAccount : {}", id);

        return bankAccountRepository.findById(id).map(bankAccountMapper::toDto);
    }

    /**
     * Delete the bankAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankAccount : {}", id);

        bankAccountRepository.deleteById(id);
    }
}
