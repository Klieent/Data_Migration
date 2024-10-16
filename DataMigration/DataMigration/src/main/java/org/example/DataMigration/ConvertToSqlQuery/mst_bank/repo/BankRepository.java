package org.example.DataMigration.ConvertToSqlQuery.mst_bank.repo;

import org.example.DataMigration.Tables.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
}