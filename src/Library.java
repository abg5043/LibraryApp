import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class Library {

    // the names of assets in stock in the library
    private static final ArrayList<Asset> assets = new ArrayList<Asset>();
    private static final ArrayList<Asset> testArray = new ArrayList<>();
    /** determines whether or not an asset is stocked by the library and what it's index is.
     *
     * @param assetName
     * @return -1 for not in stock or index of asset if in stock
     */


    public static Asset getAsset(String assetName) throws NoSuchElementException {

        for(int i=0; i<assets.size(); i++) {
            if(assets.get(i).getName().equals(assetName))
                return assets.get(i);
        }
        throw new NoSuchElementException("The asset does not exist");
    }

    public static void saveAssetsToFile(File file) throws Exception {
        try (PrintWriter outputfile = new PrintWriter(file);) {

            // add data to csv
            for(Asset asset : assets) {
                String[] assetArray = asset.getAssetAsArray();
                for(String string : assetArray) {
                    outputfile.print(string);
                    outputfile.print(",");
                }
                outputfile.println();
            }

        }

    }


    public static void addAssetsFromFile(File file)
    {
        try {
            Scanner in = new Scanner(file);

            while(in.hasNextLine()) {
                String line = in.nextLine();
                String[] nextLine = line.split(",");
                String type = nextLine[0];
                String assetName = nextLine[1];
                Boolean isRented = Boolean.valueOf(nextLine[3]);


                if(isRented) {
                    String renter = nextLine[2];
                    LocalDate dateRented = LocalDate.parse(nextLine[4]);
                    LocalDate dateDue = LocalDate.parse(nextLine[5]);
                    if(type.equals("Video")) {
                        String rating = nextLine[6];
                        if(rating.equals("")) {
                            assets.add(new Video(assetName, renter, isRented, dateRented, dateDue));
                        } else {
                            assets.add(new Video(assetName, renter, isRented, dateRented, dateDue, Rating.valueOf(rating)));
                        }
                    } else {
                        assets.add(new Book(assetName, renter, isRented, dateRented, dateDue));
                    }
                } else {
                    if(type.equals("Video")) {
                        String rating = nextLine[6];
                        if(rating.equals("")) {
                            assets.add(new Video(assetName));
                        } else {
                            assets.add(new Video(assetName, Rating.valueOf(rating)));
                        }
                    } else {
                        assets.add(new Book(assetName));
                    }
                }
            }
        } catch (java.io.IOException ex) {
            System.out.println("I/O Error: no such file");
        }

    }



    public static void main(String[] args) throws Exception {

        var in = new Scanner(System.in);
        while(true) { // run menu indefinitely
            System.out.println("\nWelcome to the CSC 184 Library App!");
            System.out.println("");
            System.out.println("Menu: ");
            System.out.println(" 1) List Inventory ");
            System.out.println(" 2) List Anything on Loan ");
            System.out.println(" 3) Rent Something ");
            System.out.println(" 4) Return Something ");
            System.out.println(" 5) Save ");
            System.out.println(" 6) Load ");
            System.out.println(" 7) Quit ");
            System.out.println("");
            System.out.print("Please make a selection: ");

            var choice = in.nextLine();
            System.out.println("");
            switch (choice) {
                case "1": // list inventory
                    for(int i=0; i<assets.size(); i++)
                        System.out.println((i + 1) + ". " + assets.get(i).getName() + " (" + assets.get(i).getClass().getSimpleName() + ")");
                    break;
                case "2": // list assets on loan
                    var anyRenters = false;
                    for(int i=0; i<assets.size(); i++) //run through assets to see which are rented and print them
                        if(assets.get(i).isRented()) {
                            anyRenters = true;
                            System.out.println(assets.get(i).getName());
                            System.out.println("type: " + assets.get(i).getClass().getSimpleName());
                            System.out.println("rented by: " + assets.get(i).getRenter());
                            System.out.println("due on: " + assets.get(i).getDueDate());
                            System.out.println("");
                        }
                    if(!anyRenters) System.out.println("Nothing is on loan. ");
                    break;
                case "3": // rent an asset
                    System.out.print("What will be rented: "); //Who is renting?
                    var assetName = in.nextLine();
                    System.out.println("");

                    try{
                        if(getAsset(assetName).isRented()) {
                            System.out.println("This is currently on loan"); //Prints if asset is currently out on loan
                            break;
                        } else {
                            System.out.print("Who is the renter?: "); //Ask and log who wants to rent the asset
                            var renter = in.nextLine();
                            System.out.print("How old is the renter?: "); //How old is the renter?
                            var renterAge = Integer.parseInt(in.nextLine());
                            System.out.println("");

                            if(getAsset(assetName).canRent(renterAge)) {
                                getAsset(assetName).rent(renter);
                                break;
                            } else {
                                System.out.println("Sorry, the renter is too young to rent this!");
                                break;
                            }
                        }
                    } catch(Exception ex) {
                        System.out.println(ex.getMessage()); //Prints if asset is not something we carry
                        break;
                    }


                case "4": // return an asset
                    System.out.print("What do you want to return?: ");
                    var returnedAssetName = in.nextLine();
                    System.out.println("");

                    try{
                        if(!getAsset(returnedAssetName).isRented()) {
                            System.out.println("This is not currently lent out"); //Prints if asset is not currently out on loan
                            break;
                        } else {
                            getAsset(returnedAssetName).returnToLibrary(); //return the asset
                            break;
                        }
                    } catch(Exception ex) {
                        System.out.println(ex.getMessage()); //Prints if asset is not something we carry
                        break;
                    }

                case "5": // save selected
                    System.out.println("What file would you like to save this as?");
                    String saveFile = in.nextLine();
                    File saveBookList = new File(saveFile);
                    saveAssetsToFile(saveBookList);
                    break;


                case "6": // load selected
                    System.out.println("What file would you like to load?");
                    String loadFile = in.nextLine();
                    File loadBookList = new File(loadFile);
                    addAssetsFromFile(loadBookList);
                    break;

                case "7": // quit selected
                    System.out.println("Thank you for using the library!");
                    System.exit(0);  // user exit program
                    break;


                default:
                    System.out.println(choice + " is not a valid selection, try again");
                    break;
            }
        }

    }
}

