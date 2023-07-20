package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rest")
@RestController
public class PetClinicRestController {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.GET, value="/owners")
    public ResponseEntity<List<Owner>> getOwners(){
        List<Owner> owners = petClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value="/owner")
    public ResponseEntity<List<Owner>> getOwner(@RequestParam("ln") String lastname){
        List<Owner> owners = this.petClinicService.findOwners(lastname);
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value="/owner/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Long id){
        try {
            Owner owner = this.petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }
}