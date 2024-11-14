package com.training.ecommerce.Controller;

import com.training.ecommerce.Company;
import com.training.ecommerce.Service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyCotroller {
    private final CompanyService companyService;

    public CompanyCotroller(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/register")
    public Company registerCompany(@RequestBody Company company){
        return companyService.registerCompany(company);
    }
    @GetMapping
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }
    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }
    @PutMapping("/{id}")
    public Company updateCopmany(@PathVariable Long id, @RequestBody Company companyDetails){
        return companyService.companyUpdate(id, companyDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteCompany(Long id){
        companyService.deleteCompany(id);
    }
}
