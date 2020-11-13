package com.cg.iba;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.AccountType;
import com.cg.iba.entities.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.BeneficiaryRepository;
import com.cg.iba.service.BeneficiaryServiceImpl;

@SpringBootTest
public class Sprint2ApplicationTests {

	@Autowired
	BeneficiaryServiceImpl beneficiaryServiceImpl;

	@MockBean
	BeneficiaryRepository beneficiaryRepository;
	Account account = new Account(1, 50000.0, 5.2,
			LocalDate.parse("2011-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));
	Beneficiary beneficiary = new Beneficiary(1, "Lakshmi", 121458, "ANDB00158", AccountType.SAVINGS, account);
	Beneficiary beneficiary1 = new Beneficiary(2, "Rama", 125487, "ANDB00654", AccountType.SAVINGS, account);

	@Test
	public void addBeneficiaryTest() throws InvalidDetailsException {
		when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);
		Beneficiary addedBeneficiary = null;
		addedBeneficiary = beneficiaryServiceImpl.addBeneficiary(beneficiary);
		assertNotNull(addedBeneficiary);
		assertEquals(beneficiary, addedBeneficiary);
	}

	@Test
	public void testFindBeneficiary() {
		when(beneficiaryRepository.findById((long) 1)).thenReturn(Optional.of(beneficiary));
		Beneficiary fetchedBeneficiary = beneficiaryServiceImpl.findBeneficiaryById(1);
		assertNotNull(fetchedBeneficiary);
		assertEquals(beneficiary, fetchedBeneficiary);
	}
   @Test
   public void testDeleteBeneficiary() {
	   when(beneficiaryRepository.findById((long) 1)).thenReturn(Optional.of(beneficiary));
		boolean deleteBeneficiary = beneficiaryServiceImpl.deleteBeneficiary(1);
		assertNotNull(deleteBeneficiary);
		assertEquals(true, deleteBeneficiary);
   }
 
   @Test
   public void testUpdateBeneficiary() {
       when(beneficiaryRepository.findById((long) 1)).thenReturn(Optional.of(beneficiary));
       when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);
       Beneficiary updateBeneficiary=beneficiaryServiceImpl.updateBeneficiary(beneficiary);
       assertNotNull(updateBeneficiary);
       assertEquals(beneficiary, updateBeneficiary);
   }
    @Test
    public void testListAllBeneficiaries() throws InvalidAccountException,EmptyListException {
	   
	  
	   List<Beneficiary> allListBeneficiaries = new ArrayList<Beneficiary>();
	   allListBeneficiaries.add(beneficiary);
	   allListBeneficiaries.add(beneficiary1);
	   when(beneficiaryRepository.listAllBeneficiaries(1)).thenReturn(allListBeneficiaries);
	   Set<Beneficiary> allBeneficiarySet = beneficiaryServiceImpl.listAllBeneficiaries(1);
	   assertNotNull(allBeneficiarySet);
	   assertEquals(allListBeneficiaries.size(),allBeneficiarySet.size());
   }

@Test
public void testAddBeneficiaryThrowsInvalidDetailsException() {
    Beneficiary beneficiary = new Beneficiary(-1, "Lakshmi", 121458, "ANDB00158", AccountType.SAVINGS, account);
    when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);
    Assertions.assertThrows(InvalidDetailsException.class, () -> {
        beneficiaryServiceImpl.addBeneficiary(beneficiary);
    });
}
@Test
public void testUpdateBeneficiaryThrowsInvalidDetailsException() {
    Beneficiary beneficiary = new Beneficiary(-1, "Lakshmi", 121458, "ANDB00158", AccountType.SAVINGS, account);
    when(beneficiaryRepository.findById((long) 10)).thenReturn(Optional.of(new Beneficiary()));
    Assertions.assertThrows(InvalidDetailsException.class, () -> {
        beneficiaryServiceImpl.updateBeneficiary(beneficiary);
    });
}
@Test
public void testFindBeneficiaryThrowsDetailsNotFoundException() {
    Beneficiary beneficiary = new Beneficiary(1, "Lakshmi", 121458, "ANDB00158", AccountType.SAVINGS, account);
    when(beneficiaryRepository.findById((long) 1)).thenReturn(Optional.of(beneficiary));
    Assertions.assertThrows(DetailsNotFoundException.class, () -> {
        beneficiaryServiceImpl.findBeneficiaryById(4);
    });

}
@Test
public void testDeleteBeneficiaryThrowsDetailsNotFoundException() {
    Beneficiary beneficiary = new Beneficiary(1, "Lakshmi", 121458, "ANDB00158", AccountType.SAVINGS, account);
    when(beneficiaryRepository.findById((long) 2)).thenReturn(Optional.of(beneficiary));
    Assertions.assertThrows(DetailsNotFoundException.class, () -> {
        beneficiaryServiceImpl.deleteBeneficiary(4);
    });

}
@Test
public void testListAllBeneficiaries1() throws EmptyListException {
   List<Beneficiary> allListBeneficiaries = new ArrayList<Beneficiary>();
   when(beneficiaryRepository.listAllBeneficiaries(1)).thenReturn(allListBeneficiaries);
   Assertions.assertThrows(EmptyListException.class, () -> {
       beneficiaryServiceImpl.listAllBeneficiaries(1);
   });
}
}















	 
