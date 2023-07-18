package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/rest")
@RestController
public class PetClinicRestController {

    @Autowired
    private PetClinicService perClinicService;

    @RequestMapping(method = RequestMethod.GET, value="/owners")
    public ResponseEntity<List<Owner>> getOwners(){
        List<Owner> owners = perClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }

    public String welcome() {
        return "Welcome to PetClinic World!";
    }
}