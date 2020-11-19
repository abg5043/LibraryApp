import java.time.LocalDate;

public class Video extends Asset {
    private Rating rating;

    public Video(String videoName) {
        super(videoName);
    }

    public Video(String videoName, String renter) {
        super(videoName, renter);
    }

    public Video(String videoName, String renter, Rating rating) {
        super(videoName, renter);
        this.rating = rating;
    }

    public Video(String assetName, String renter, Boolean isRented, LocalDate dateRented, LocalDate dateDue) {
        super(assetName, renter, isRented, dateRented, dateDue);
    }

    public Video(String assetName, String renter, Boolean isRented, LocalDate dateRented, LocalDate dateDue, Rating rating) {
        super(assetName, renter, isRented, dateRented, dateDue);
        this.rating = rating;
    }

    public Video(String videoName, Rating rating) {
        super(videoName);
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


    /** Checks to see if the video can be rented
     *
     * @Param: age is the age of the renter
     * @Return: Boolean true (yes) or false (no)
     */
    @Override
    public boolean canRent(int age) {
        if(this.rating.equals(Rating.NC17)) return age > 17;
        else if(this.rating.equals(Rating.R)) return age >= 17;
        else if(this.rating.equals(Rating.PG13)) return age >= 13;
        else if(this.rating.equals(Rating.PG)) return age >= 8;
        else return true;
    }

    @Override
    public String[] getAssetAsArray() {
        String[] assetArray = new String[] {
                this.toString(), getAssetName(),getRenter(), String.valueOf(isRented()), String.valueOf(getDateRented()), String.valueOf(getDateDue()), String.valueOf(getRating())
        };
        return assetArray;
    }

    @Override
    public String toString() {
        return "Video";
    }
}
