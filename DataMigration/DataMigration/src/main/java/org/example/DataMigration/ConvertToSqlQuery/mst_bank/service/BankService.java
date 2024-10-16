package org.example.DataMigration.ConvertToSqlQuery.mst_bank.service;

import org.example.DataMigration.ConvertToSqlQuery.mst_bank.repo.BankRepository;
import org.example.DataMigration.Tables.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public List<Bank> getBankById() {
        return bankRepository.findAll();
    }
}