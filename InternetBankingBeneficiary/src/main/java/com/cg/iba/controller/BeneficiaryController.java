package com.cg.iba.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.BeneficiaryServiceImpl;
import com.cg.iba.service.IBeneficiaryService;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    @Autowired
    BeneficiaryServiceImpl beneficiaryServiceImpl;

    /***
     * addBeneficiaryDetails
     * <p>
     * adding beneficiary details into database if it was valid then added to
     * database else throw Invalid Details Exception
     * </p>
     * 
     * @param beneficiary
     * @return ResponseEntity<Beneficiary>
     * @throws InvalidDetailsException
     */

    @PostMapping("/addBeneficiary")
    public ResponseEntity<Beneficiary> addBeneficiaryDetails(@RequestBody Beneficiary beneficiary) throws InvalidDetailsException {
        Beneficiary addedBeneficiary = beneficiaryServiceImpl.addBeneficiary(beneficiary);
        return new ResponseEntity<Beneficiary>(addedBeneficiary, HttpStatus.OK);
    }

    /***
     * updateBeneficiaryDetails
     * <p>
     * updating beneficiary details into database if it was valid then updated else
     * throw Invalid Details Exception
     * </p>
     * 
     * @param beneficiary
     * @return ResponseEntity<Beneficiary>
     * @throws InvalidDetailsException
     */
    @PutMapping("/updateBeneficiary")
    public ResponseEntity<Beneficiary> updateBeneficiary(@RequestBody Beneficiary beneficiary) throws InvalidDetailsException {
        Beneficiary updatedBeneficiary = beneficiaryServiceImpl.updateBeneficiary(beneficiary);
        return new ResponseEntity<Beneficiary>(updatedBeneficiary, HttpStatus.OK);
    }

    /***
     * findingBeneficiaryById
     * <p>
     * finding beneficiaryById If it was fetched then return details else throw
     * DetailsNotFoundException
     * </p>
     * 
     * @param beneficiaryId
     * @return ResponseEntity<Beneficiary>
     * @throws DetailsNotFoundException
     */

    @GetMapping("/findById/{beneficiaryId}")
    public ResponseEntity<Beneficiary> findBeneficiaryById(@PathVariable long beneficiaryId) throws DetailsNotFoundException {
        Beneficiary fetchedBeneficiary = beneficiaryServiceImpl.findBeneficiaryById(beneficiaryId);
        return new ResponseEntity<Beneficiary>(fetchedBeneficiary, HttpStatus.OK);

    }

    /***
     * 
     * <p>
     * deleting beneficiary in the database else throw DetailsNotfoundException
     * </p>
     * 
     * @param beneficiaryId
     * @return isDeleted
     * @throws DetailsNotFoundException
     */

    @DeleteMapping("/deleteBeneficiary/{beneficiaryId}")
    public boolean delete(@PathVariable long beneficiaryId) throws DetailsNotFoundException {
        boolean isDeleted = false;
        isDeleted = beneficiaryServiceImpl.deleteBeneficiary(beneficiaryId);
        return isDeleted;
    }

    /***
     * <p>
     * List Beneficiaries by accountId
     * </P>
     * 
     * @param accountid
     * @return allBeneficiariesSet
     * @throws EmptyListException
     * @throws InvalidAccountException
     */

    @GetMapping("/listAllBeneficiaries/{accountid}")
    public Set<Beneficiary> listAllbeneficiaries(@PathVariable long accountid) throws EmptyListException, InvalidAccountException {
        Set<Beneficiary> allBeneficiariesSet = new HashSet<Beneficiary>();
        allBeneficiariesSet = beneficiaryServiceImpl.listAllBeneficiaries(accountid);
        return allBeneficiariesSet;

    }
}
