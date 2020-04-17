package casino.idfactory;

import models.*;

/**
 * Factory for creation of GeneralID objects.
 * Creation of the right object is done by specifying the type to create.
 * The specified type is case insensitive.
 * When the type is not present, null is returned.
 * <p>
 * Requirements:
 * 3. A generalID consists of a unique ID (using the UUID type of java), together with a
 * TimeStamp. Both parts are created during generalID construction.
 * Specific ID types (CardID, BetID, BettingRoundID, GamingMachineID) are derived from generalID.
 * All types of ID need to implement the Comparable interface.
 * <p>
 * 4. All ID objects need to be created by an IDFactory, using the Factory pattern.
 * Create tests for the IDFactory as well.
 */
public class IDFactory {

    public GeneralID createId(String type) {

        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase("GeneralID")) {
            return new GeneralID();
        }
        if (type.equalsIgnoreCase("CardID")) {
            return new CardID();
        }
        if (type.equalsIgnoreCase("BetID")) {
            return new BetID();
        }
        if (type.equalsIgnoreCase("BettingRoundID")) {
            return new BettingRoundID();
        }
        if (type.equalsIgnoreCase("GamingMachineID")) {
            return new GamingMachineID();
        }

        return null;

    }
}

