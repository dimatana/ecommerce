package com.training.ecommerce.Service;

import com.training.ecommerce.Company;
import com.training.ecommerce.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    //register a new company
    public Company registerCompany(Company company) {
        return companyRepository.save(company);
    }
    //get all companies
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    //get a company by id
    public Optional<Company> getCompanyById(Long id){
        return companyRepository.findById(id);
    }
    //update company details
    public Company companyUpdate(Long id, Company companyDetails){
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("company not found for this id ::" + id));
        company.setAddress(companyDetails.getAddress());
        company.setName(companyDetails.getName());
        company.setContactInfo(companyDetails.getContactInfo());
        return companyRepository.save(company);
    }
    //relete a company
    public void  deleteCompany(Long id){
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("company not found for this id ::" + id));
        companyRepository.delete(company);
    }
}
