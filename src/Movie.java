public class Movie {
    public String id;
    public String rank;
    public String rankUpDown;
    public String title;
    public String fullTitle;
    public String year;
    public String image;
    public String crew;
    public String imDbRating;
    public String imDbRatingCount;

    public Movie () {

    }

    public Movie 
(String id, String rank, String rankUpDown, String title, String fullTitle, String year, String image, String crew, String imDbRating, String imDbRatingCount) {
        this.id = id;
        this.rank = rank;
        this.rankUpDown = rankUpDown;
        this.title = title;
        this.fullTitle = fullTitle;
        this.year = year;
        this.image = image;
        this.crew = crew;
        this.imDbRating = imDbRating;
        this.imDbRatingCount = imDbRatingCount;
    }

    @Override
    public String toString() {
        return id + ": " + fullTitle;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankUpDown() {
        return this.rankUpDown;
    }

    public void setRankUpDown(String rankUpDown) {
        this.rankUpDown = rankUpDown;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return this.fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCrew() {
        return this.crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getImDbRating() {
        return this.imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingCount() {
        return this.imDbRatingCount;
    }

    public void setImDbRatingCount(String imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
    }
}
