import com.javaegitimleri.petclinic.model.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        System.out.println(firstNames);
    }
}
