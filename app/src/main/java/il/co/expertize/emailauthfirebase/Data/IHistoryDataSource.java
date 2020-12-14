package il.co.expertize.emailauthfirebase.Data;

import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface IHistoryDataSource {
    public void addTravel(Travel p);

    public void addTravel(List<Travel> travelList);

    public void editTravel(Travel p);

    public void deleteTravel(Travel p);

    public void clearTable();
}
