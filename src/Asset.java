import java.time.LocalDate;

public abstract class Asset {
    private String assetName;
    private String renter;
    private boolean isRented;
    private LocalDate dateRented;
    private LocalDate dateDue;

    public Asset(String assetName) {
        this.assetName = assetName;
        this.isRented = false;
    }

    public Asset(String assetName, String renter) {
        this(assetName);
        this.renter = renter;
        this.isRented = true;
        this.dateRented = LocalDate.now();
        this.dateDue = LocalDate.now().plusWeeks(1);
    }

    public Asset(String assetName, String renter, Boolean isRented, LocalDate dateRented, LocalDate dateDue) {
        this(assetName, renter);
        this.isRented = isRented;
        this.dateRented = dateRented;
        this.dateDue = dateDue;
    }

    public void setName(String assetName) {
        this.assetName = assetName;
    }

    public String getName() {
        return assetName;
    }

    public String getRenter() {
        return renter;
    }

    public boolean isRented() {
        return isRented;
    }

    public LocalDate getDueDate() {
        return dateDue;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    /** rents the asset, logs when it was rented, and sets the date due to 1 week in the future
     *
     * @param renterName
     */
    public void rent(String renterName) {
        this.renter = renterName;
        this.isRented = true;
        this.dateRented = LocalDate.now();
        this.dateDue = LocalDate.now().plusWeeks(1);
    }

    public boolean canRent(int age) {
        return true;
    }

    /** returns the asset to the library.
     *
     */
    public void returnToLibrary() {
        this.isRented = false;
        this.renter = null;
        this.dateRented = null;
        this.dateDue = null;
    }

    /** Overrides the equals method to allow objects to be compared to asset if they have the same title
     *
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Asset) {
            var assetObj = (Asset)obj; //upcast assetObj to ensure it's compiled as a asset
            return assetObj.getName()
                    .equals(this.getName()); //Add in ISBN if you ever implement that
        }
        return false;

    }

    public String getAssetName() {
        return assetName;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public String[] getAssetAsArray(){
        String[] assetArray = new String[] {
                this.toString(), getAssetName(),getRenter(), String.valueOf(isRented()), String.valueOf(getDateRented()), String.valueOf(getDateDue()), ""
        };

        return assetArray;
    }
}
