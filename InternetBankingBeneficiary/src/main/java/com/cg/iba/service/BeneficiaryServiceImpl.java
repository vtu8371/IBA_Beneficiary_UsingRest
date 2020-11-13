package com.cg.iba.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cg.iba.entities.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.BeneficiaryRepository;

@Component
public class BeneficiaryServiceImpl implements IBeneficiaryService {

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
  
	@Transactional
	public Beneficiary addBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException {
		/*
		 * storing beneficiary details into database if beneficiary details are valid then added and it
		 * return beneficiary object or else throw InvalidDetailsException
		 */
		if (beneficiary.getBeneficiaryId()<=0) {
			throw new InvalidDetailsException("Adding beneficiary to database failed.");
		} else {
			return beneficiaryRepository.save(beneficiary);
		}
	}

	@Transactional
	public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException {
		/*
		 * updating beneficiary details if valid details are provided it will update, else throw Invalid Details exception
		 *
		 */
		Beneficiary existingBeneficiary = beneficiaryRepository.findById(beneficiary.getBeneficiaryId()).orElse(new Beneficiary());
		if (existingBeneficiary.getBeneficiaryId()!=beneficiary.getBeneficiaryId()) {
			throw new InvalidDetailsException("updation of beneficiary details to database failed");
		} else {
			return beneficiaryRepository.save(beneficiary);
		}
	}

	public Beneficiary findBeneficiaryById(long beneficiaryId) throws DetailsNotFoundException {
		/*
		 * fetch beneficiary details by beneficiaryId and return beneficiary details if found else
		 * throw exception
		 *
		 */
		Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(new Beneficiary());
		if (beneficiary.getBeneficiaryId()!=beneficiaryId) {
			throw new DetailsNotFoundException("No beneficiary found with id " + beneficiaryId + " to fetch");
		} else {
			return beneficiary;
		}
	}

	public boolean deleteBeneficiary(long beneficiaryId) throws DetailsNotFoundException {
		/*
		 * deleting beneficiary details based on beneficiaryId If details deleted it returns true  , else
		 * throw exception
		 *
		 */
		boolean isDeleted = false;
		Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(new Beneficiary());
		if (beneficiary.getBeneficiaryId()!=beneficiaryId ) {
			throw new DetailsNotFoundException("Invalid beneficiary details deletion failed");
		} else {
			beneficiaryRepository.delete(beneficiary);
			isDeleted = true;
		}
		return isDeleted;

	}

	public Set<Beneficiary> listAllBeneficiaries(long accountId) throws InvalidAccountException, EmptyListException {
		/*
		 * need to verify accountId, if account id is valid then get all beneficiaries
		 * else throw InvalidAccountException. (use findAccountById() method in
		 * IAccountService)
		 */
		// if accountId is valid then
		List<Beneficiary> allBeneficiariesList = new ArrayList<Beneficiary>();
		allBeneficiariesList = beneficiaryRepository.listAllBeneficiaries(accountId);
		if (allBeneficiariesList.isEmpty()) {
			throw new EmptyListException("No beneficiaries found for this accountId " + accountId);
		} else {
			Set<Beneficiary> allBeneficiarySet = new HashSet<Beneficiary>();
		      allBeneficiarySet.addAll(allBeneficiariesList);
			
			return allBeneficiarySet;

		}
	}

}
