public class SearchResult implements ISearchResult {
    private String Id;
    private int Rank;

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    @Override
    public int getRank() {
        return Rank;
    }

    @Override
    public void setRank(int rank) {
        Rank = rank;
    }

}
