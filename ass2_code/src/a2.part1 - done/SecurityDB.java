public class SecurityDB extends SecurityDBBase {

    /* Implement all the necessary methods here */

    private SecHashTable passengers;
    /**
     * Creates an empty hashtable and a variable to count non-empty elements.
     *
     * @param numPlanes             number of planes per day
     * @param numPassengersPerPlane number of passengers per plane
     */
    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
        this.passengers = new SecHashTable(numPassengersPerPlane * numPlanes, MAX_CAPACITY);
    }

    /**
     * Calculates the hash code based on the given key.
     *
     * @param key string to calculate hash code of
     * @return hash code of key
     */
    @Override
    public int calculateHashCode(String key) {
        int result = 0;
        char[] passport = key.toCharArray();
        int temp = 0;
        int count = 0;
        for (char i : passport) {
            temp = temp + (int) i;
            result = result + temp;
            count ++;
        }
        return result + count;
    }

    /**
     * Returns the actual size of the hashtable, including the empty buckets.
     *
     * @return the size of the hashtable
     */
    @Override
    public int size() {
        return this.passengers.getSize();
    }

    /**
     * Finds a passenger's name by their passport ID.
     *
     * @param passportId passenger's passport ID
     * @return the name of the person if they are in the system, otherwise null
     */
    @Override
    public String get(String passportId) {
        Passenger result = this.passengers.getPassenger(calculateHashCode(passportId));
        if (result != null) {
            return result.getPassportID();
        } else {
            return null;
        }
    }

    /**
     * Removes a passenger from the system.
     *
     * @param passportId passenger's passport ID
     * @return true if the passenger was deleted, false if they could not be found
     */
    @Override
    public boolean remove(String passportId) {
        Passenger result = this.passengers.removePassenger(calculateHashCode(passportId));
        return result != null;
    }

    /**
     * Adds a passenger to the hashtable.
     *
     * @param name       passenger's full name
     * @param passportId passenger's passport ID
     * @return true if the passenger was added successfully, false otherwise
     */
    @Override
    public boolean addPassenger(String name, String passportId) {
        try {
            this.passengers.addPassenger(new Passenger(name, passportId));
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }



    /**
     * Counts the number of passengers in the hashtable.
     *
     * @return the number of passengers
     */
    @Override
    public int count() {
        return this.passengers.getPassengerCount();
    }

    /**
     * Returns the bucket index of the passenger in the hashtable.
     *
     * @param passportId passenger's passport ID
     * @return bucket index of passenger in hashtable
     */
    @Override
    public int getIndex(String passportId) {
        return this.passengers.getIndex(calculateHashCode(passportId));
    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of SecurityDB's run configuration
     */
//    public static void main(String[] args) {
//        SecurityDB db = new SecurityDB(3, 2);
//
//        // add
//        db.addPassenger("Rob Bekker", "Asb23f");
//        db.addPassenger("Kira Adams", "MKSD23");
//        db.addPassenger("Kira Adams", "MKSD24");
//        assert db.contains("Asb23f");
//
//        // count
//        assert db.count() == 3;
//
//        // del
//        db.remove("MKSD23");
//        assert !db.contains("MKSD23");
//        assert db.contains("Asb23f");
//
//        // hashcodes
//        assert db.calculateHashCode("Asb23f") == 1717;
//
//        // suspicious
//        db = new SecurityDB(3, 2);
//        db.addPassenger("Rob Bekker", "Asb23f");
//        db.addPassenger("Robert Bekker", "Asb23f");
//        // Should print a warning to stderr
//
//        System.out.println("Test success!");
//    }
}

/* Add any additional helper classes here */
class SecHashTable {

    private int size;
    private int maxCapacity;
    private Passenger[] array;
    private int passengerCount;

    public SecHashTable (int potentialSize, int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.size = setSize(potentialSize);
        this.array = new Passenger[this.size];
        this.passengerCount = 0;
    }

    public int getSize() {
        return this.size;
    }

    public int getPassengerCount() {
        return this.passengerCount;
    }

    public int getIndex (int hashCode) {
        int slot = hashCode % this.size;
        Passenger temp = this.array[slot];
        if (temp == null || temp.hashCode() == hashCode) {
            return slot;
        } else {
            int newSlot = (slot + 1) % this.size;
            Passenger newTemp = this.array[newSlot];

            for (int i = 0; i < this.size; i++) {
                if (newTemp.hashCode() == hashCode) {
                    return newSlot;
                } else {
                    newSlot = (newSlot + 1) % this.size;
                    newTemp = this.array[newSlot];
                }
            }
        }
        return this.maxCapacity + 1000;
    }

    public Passenger getPassenger (int hashCode) {
        int index = getIndex(hashCode);
        if (index == this.maxCapacity + 1000) {
            return null;
        } else {
            return this.array[index];
        }
    }

    public Passenger removePassenger (int hashCode) {
        int index = getIndex(hashCode);
        if (index == this.maxCapacity + 1000) {
            return null;
        } else {
            Passenger result = this.array[index];
            this.array[index] = null;
            this.passengerCount -= 1;
            return result;
        }
    }

    public void addPassenger (Passenger passenger) throws Exception  {
        if (this.passengerCount == this.size) {
            tableResize();
        }
        int hashCode = passenger.hashCode();
        int slot = hashCode % this.size;
        try {
            if (getPassenger(hashCode).hashCode() == hashCode) {
                throw new Exception("Passenger with this passportID already exist!");
            }
        } catch (NullPointerException ignored) {
            ;
        }

        if (this.array[slot] == null) {
            this.array[slot] = passenger;
        } else {
            int newSlot = (slot + 1) % this.size;
            for (int i = 0; i < this.size; i++) {
                if (this.array[newSlot] == null) {
                    this.array[newSlot] = passenger;
                    break;
                } else {
                    newSlot = (newSlot + 1) % this.size;
                }
            }
        }
        this.passengerCount += 1;
    }


    private void tableResize () throws Exception {
        if (this.size == this.maxCapacity) {
            throw new Exception("The table could no longer be expanded!");
        }
        int newSize = setSize(this.size + 1);
        Passenger[] newArray = new Passenger[newSize];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
        this.size = newSize;
    }

    private boolean checkPrime (int number) {
        boolean prime = true;
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }


    private int setSize (int potentialSize) {
        int temp = potentialSize + 1;
        boolean prime = checkPrime(temp);

        if (prime) {
            return temp;
        } else {
            for (int i = temp + 1; i < this.maxCapacity; i++) {
                if (checkPrime(i)) {
                    return i;
                }
            }
            return this.maxCapacity;
        }
    }
}

class Passenger {
    private String name;
    private String passportID;

    public Passenger (String name, String passportID) {
        this.name = name;
        this.passportID = passportID;
    }

    @Override
    public int hashCode() {
        int result = 0;
        char[] passport = this.passportID.toCharArray();
        int temp = 0;
        int count = 0;
        for (char i : passport) {
            temp = temp + (int) i;
            result = result + temp;
            count ++;
        }
        return result + count;
    }

    public String getName() {
        return this.name;
    }

    public String getPassportID() {
        return this.passportID;
    }
}
