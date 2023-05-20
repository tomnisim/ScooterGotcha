package gotcha.server.Domain.CitiesModule;
import javax.persistence.*;
import java.util.*;

@Entity()
@Table(name = "cities")
public class City {

    @Id
    @Column(name = "place_id")
    private int placeId = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "city_names", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "name")
    private Set<String> names;

    public City() {
        this.names = new HashSet<>();
    }

    public void addName(String name) {
        this.names.add(name);
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public Set<String> getNames() {
        return names;
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }
}
