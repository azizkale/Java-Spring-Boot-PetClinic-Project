package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.exception.InternelServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/rest")
@RestController
public class PetClinicRestController {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.POST, value="/owner")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner){
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

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

    //HATEOAS -> request ile api nin dokumantasyonu saglanmis oluyor
    //client tarafindan json tipinde dönüs istenirse otomatik Hateoas metohodu invoke edilir
    @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id){
        try {
            Owner owner = this.petClinicService.findOwner(id);
            Link self = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner" + id).withSelfRel();
            Link create = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create");
            Link update = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("update");
            Link delete = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("delete");

            Resource<Owner> resource = new Resource<Owner>(owner,self,create,update,delete);
            return ResponseEntity.ok(resource);

        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
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

    @RequestMapping(method = RequestMethod.PUT, value="/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id,  @RequestBody Owner ownerRequest){
        try{
            Owner owner = petClinicService.findOwner(id);
            owner.setFirstName(ownerRequest.getFirstName());
            owner.setLastName(ownerRequest.getLastName());
            petClinicService.updateOwner(owner);

            return ResponseEntity.ok().build(); // Body si olmayan ResponsEntity olusturup döndürüyüruz
        } catch(OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value="/owner/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id){
        try{
            petClinicService.deleteOwner(id);
        }catch (OwnerNotFoundException ex){
            throw ex;
        }catch (InternelServerException ex){
            throw new InternelServerException(ex);
        }
        return null;
    }
}