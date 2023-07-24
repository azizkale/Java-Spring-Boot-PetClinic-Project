import com.javaegitimleri.petclinic.model.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestControllerTest {

    private RestTemplate restTemplate;

    @Before
    public void SetUp(){
        restTemplate = new RestTemplate();
    }

    @Test
    public void testCreateOwner(){
        Owner owner = new Owner();
        owner.setFirstName("Aziz");
        owner.setLastName("KALE");

        URI location = this.restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);

        Owner owner2 = restTemplate.getForObject(location,Owner.class);

        MatcherAssert.assertThat(owner2.getFirstName(),Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(owner2.getLastName(),Matchers.equalTo(owner.getLastName()));
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Kenan"));
    }

    @Test
    public void testGetOwnersByLastName(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Sevindik",List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(),Matchers.equalTo(200));

        List<Map<String,String>> body = response.getBody();
        List<String> firstNames = body.stream().map(e-> e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames,Matchers.containsInAnyOrder("Kenan","Hümeyra","Salim"));
    }

    @Test
    public void testGetOwners(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners",List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(),Matchers.equalTo(200));
        List<Map<String,String>> body = response.getBody();

        List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan", "Hümeyra", "Salim", "Muammer"));
    }

    @Test
    public void testUpdateOwner(){
        //at first getting an owner from Server
        Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4",Owner.class);

        //testing that wether it is expecting owner  before update
        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Salim"));

        //update
        owner.setFirstName("Aziz");
        owner.setLastName("KALE");
        restTemplate.put("http://localhost:8080/rest/owner/4",owner);

        //testing now updated owner
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Aziz"));
        MatcherAssert.assertThat(owner.getLastName(), Matchers.equalTo("KALE"));
    }

    @Test
    public void testDeleteOwner(){
        restTemplate.delete("http://localhost:8080/rest/owner/1");
        try {
            restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
            Assert.fail("Should have not returned owner");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(),Matchers.equalTo(404));
        }
    }
}
