package il.co.expertize.emailauthfirebase.Data;

import java.util.List;
import androidx.lifecycle.LiveData;
import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface IHistoryDataSource {
    public List<Travel> getTravels();
    public Travel getTravel(String id);
    public void addTravel(Travel p);

    public void addTravel(List<Travel> travelList);

    public void editTravel(Travel p);

    public void deleteTravel(Travel p);

    public void clearTable();
}
